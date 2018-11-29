package de.dashup.model.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import de.dashup.shared.DatabaseObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class provides a persistent connection a MySQL Database via JDBC.
 *
 * @author Raphael Müßeler
 */
public class Database {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    private static String HOST;
    private static DatabaseName DB_NAME;

    private static String DB_USER;
    private static String DB_PASSWORD;

    private Connection connection;

    private static Database INSTANCE = null;

    public enum Table {
        USERS("users"),
        USERS_TOKENS("users_tokens"),
        PANELS("de.dashup.model.builder/panels"),
        USERS_PANELS("users_panels"),
        USER_SECTIONS("user_sections"),
        SECTIONS_PANELS("sections_panels");

        private String tableName;

        Table(String tableName) {
            this.tableName = tableName;
        }

        public String getTableName() {
            return tableName;
        }

        @Override
        public String toString() {
            return this.getTableName();
        }
    }

    public enum DatabaseName {
        DEV("dashup_dev"),
        TEST("dashup_test"),
        PROD("dashup_prod");

        private final String name;

        DatabaseName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static DatabaseName getByShortCut(String shortcut) {
            switch (shortcut) {
                case "dev":
                    return DEV;
                case "test":
                    return TEST;
                case "prod":
                    return PROD;
            }
            return null;
        }
    }

    private Database() throws SQLException {
        try {
            // loading database driver
            Class.forName(JDBC_DRIVER);

            if (HOST == null) {
                throw new IllegalArgumentException("Database: No host is defined!");
            }

            if (DB_USER == null || DB_PASSWORD == null) {
                throw new IllegalArgumentException("Database: No user or password is defined!");
            }

            // initializing DB access
            this.connection = DriverManager.getConnection("jdbc:mysql://" + HOST + ":3306/" + DB_NAME.getName() +
                            "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&" +
                            "serverTimezone=UTC&autoReconnect=true",
                    DB_USER, DB_PASSWORD);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the static host. This method has to be called before the first call of getInstance.
     *
     * @param local sets the host to 'localhost' if true, else the database config file will be read.
     */
    public static void setHost(boolean local) {
        if (local) {
            HOST = "localhost";
        } else {
            try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(
                    Database.class.getResourceAsStream("config/database.conf")))) {
                HOST = fileReader.readLine();
                DB_USER = fileReader.readLine();
                DB_PASSWORD = fileReader.readLine();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sets the name of the database
     *
     * @param databaseName DatabaseName Object
     */
    public static void setDbName(DatabaseName databaseName) {
        DB_NAME = databaseName;
    }

    /**
     * Returns the current instance of the database
     *
     * @return database object
     */
    public static Database getInstance() throws SQLException {
        if (INSTANCE == null) {
            INSTANCE = new Database();
        }
        return INSTANCE;
    }

    /**
     * Fetches data from the database and parses via the Gson library it to an object.
     *
     * @param table           Database table to fetch from
     * @param resultType      Type of object, which will be returned
     * @param whereParameters Where parameters which will be concatenated by AND
     * @return Object with fetched data
     * @throws SQLException thrown, when something went wrong executing the SQL statement
     */
    public List<? extends DatabaseObject> getObject(Table table, Type resultType, Map<String, Object> whereParameters) throws SQLException, JsonParseException {
        Gson gson = new GsonBuilder().create();
        JSONArray jsonArray = this.get(table, whereParameters);
        List<DatabaseObject> result = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            result.add(gson.fromJson(jsonObject.toString(), resultType));
        }
        return result;
    }

    /**
     * {@code orderByClause} set to null by default.
     *
     * @see Database#get(Table, Map, String)
     */
    public JSONArray get(Table table, Map<String, Object> whereParameters) throws SQLException {
        return this.get(table, whereParameters, null);
    }

    /**
     * @param tableName       database table to fetch from
     * @param whereParameters where parameters which will be concatenated by AND
     * @param orderByClause   order by clause which will be appended on the sql statement
     * @return JSONArray that contains
     * @throws SQLException thrown, when something went wrong executing the SQL statement
     */
    // TODO select specific fields
    // TODO escape strings
    public JSONArray get(Table tableName, Map<String, Object> whereParameters, String orderByClause) throws SQLException {
        PreparedStatement statement;
        String query = "SELECT * FROM " + tableName.getTableName() +
                this.getClause(whereParameters, "WHERE", " AND ") +
                this.getOrderByClause(orderByClause);

        statement = connection.prepareStatement(query);
        this.preparedStatement(statement, whereParameters);

        // execute query
        ResultSet result = statement.executeQuery();
        return Converter.convertResultSetIntoJSON(result);
    }

    /**
     * Inserts one data row into database.
     *
     * @param tableName table to insert
     * @param values    this map contains the all the corresponding column values that will be inserted
     * @throws SQLException thrown, when something went wrong executing the SQL statement
     */
    public void insert(Table tableName, Map<String, Object> values) throws SQLException {
        PreparedStatement statement;
        StringBuilder query = new StringBuilder("INSERT INTO " + tableName + " ")
                .append(values.keySet().toString().replace("[", "(").replace("]", ")"))
                .append(" values (");

        boolean first = true;
        for (int i = 0; i < values.size(); i++) {
            if (first) {
                first = false;
            } else {
                query.append(", ");
            }
            query.append("?");
        }
        query.append(")");
        statement = this.preparedStatement(connection.prepareStatement(query.toString()), values);

        statement.execute();
    }

    /**
     * Updates one or multiple data rows that matches the where condition.
     *
     * @param tableName       database table to be updated
     * @param whereParameters where condition to update only specific data rows
     * @param values          values that will be updated
     * @throws SQLException thrown, when something went wrong executing the SQL statement
     */
    public void update(Table tableName, Map<String, Object> whereParameters, Map<String, Object> values) throws SQLException {
        PreparedStatement statement;
        String query = "UPDATE " + tableName.getTableName() +
                this.getClause(values, "SET", ", ") +
                this.getClause(whereParameters, "WHERE", " AND ");

        statement = connection.prepareStatement(query);
        this.preparedStatement(statement, values);
        this.preparedStatement(statement, whereParameters, values.size() + 1);
        statement.execute();

    }

    /**
     * Deletes one or multiple data rows that matches the where condition.
     *
     * @param table           database table to be updated
     * @param whereParameters where condition to delete specific data rows
     * @throws SQLException thrown, when something went wrong executing the SQL statement
     */
    public void delete(Table table, Map<String, Object> whereParameters) throws SQLException {
        PreparedStatement statement;
        String query = "DELETE FROM " + table.getTableName() + " " +
                this.getClause(whereParameters, "WHERE", " AND ");
        statement = this.preparedStatement(connection.prepareStatement(query), whereParameters);
        statement.execute();
    }

    /**
     * Returns the latest id that was inserted into the table.
     *
     * @param table database table that will be requested
     * @return the latest id in the table
     * @throws SQLException thrown, when something went wrong executing the SQL statement
     */
    public int getLatestId(Table table) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT id FROM " +
                table.getTableName() +
                " ORDER BY id DESC LIMIT 1");
        ResultSet result = statement.executeQuery();
        return Converter.convertResultSetIntoJSON(result).getJSONObject(0).getInt("id");
    }

    private String getClause(Map<String, Object> values, String operation, String separator) {
        StringBuilder whereClauseString = new StringBuilder();
        if (values.size() > 0) {
            whereClauseString.append(" ").append(operation).append(" ");
            boolean first = true;
            for (Map.Entry<String, Object> entry : values.entrySet()) {
                if (first) {
                    first = false;
                } else {
                    whereClauseString.append(separator);
                }
                whereClauseString.append(entry.getKey()).append(" = ?");
            }
        }
        return whereClauseString.toString();
    }

    private PreparedStatement preparedStatement(PreparedStatement statement, Map<String, Object> values) throws SQLException {
        return this.preparedStatement(statement, values, 1);
    }

    private PreparedStatement preparedStatement(PreparedStatement statement, Map<String, Object> values, int index) throws SQLException {
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            if (entry.getValue() instanceof LocalDate) {
                statement.setObject(index, Date.valueOf((LocalDate) entry.getValue()).toString());
            } else {
                statement.setObject(index, entry.getValue());
            }
            index++;
        }
        return statement;
    }

    private String getOrderByClause(String field) {
        StringBuilder orderByClauseString = new StringBuilder();
        if (field != null) {
            orderByClauseString.append(" ORDER BY ").append(field);
        }
        return orderByClauseString.toString();
    }
}
