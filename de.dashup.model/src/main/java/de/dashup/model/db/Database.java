package de.dashup.model.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class provides a persistent connection a MySQL Database via JDBC.
 *
 * @author Raphael Müßeler
 */
public class Database {

    /**
     * Literals
     */
    private static final String WHERE = "WHERE";
    private static final String AND = " AND ";
    private static final String SELECT_FROM = "SELECT * FROM ";

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final Logger logger = Logger.getLogger("Dashup");

    private static String host;
    private static DatabaseName databaseName;

    private static String user;
    private static String password;

    private Connection connection;


    private static Database instance = null;

    public enum Table {
        USERS("users"),
        USERS_TOKENS("users_tokens"),
        USERS_SETTINGS("users_settings"),
        PANELS("panels"),
        USER_SECTIONS("users_sections"),
        SECTIONS_PANELS("sections_panels"),
        TODOS("todos"),
        FINANCES("finances"),
        PROPERTIES("properties"),
        USERS_PROPERTIES("users_properties"),
        TAGS("tags"),
        RATINGS("ratings"),
        PANELS_TAGS("panels_tags");

        private final String tableName;

        Table(String tableName) {
            this.tableName = tableName;
        }

        String getTableName() {
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
        PROD("dashup_prod"),
        JENKINS("dashup_jenkins");

        private final String name;

        DatabaseName(String name) {
            this.name = name;
        }

        String getName() {
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
                case "jenkins":
                    return JENKINS;
                default:
                    return null;
            }
        }
    }

    private Database() throws SQLException {
        try {
            // loading database driver
            Class.forName(JDBC_DRIVER);

            if (host == null) {
                throw new IllegalArgumentException("Database: No host is defined!");
            }

            if (user == null || password == null) {
                throw new IllegalArgumentException("Database: No user or password is defined!");
            }

            // initializing DB access

            this.connection = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + databaseName.getName() +
                            "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&" +
                            "serverTimezone=UTC&autoReconnect=true",
                    user, password);

        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * Sets the static host. This method has to be called before the first call of getInstance.
     *
     * @param local sets the host to 'localhost' if true, else the database config file will be read.
     */
    public static void setHost(boolean local) {
        if (local) {
            host = "localhost";
            user = "root";
            password = "";
        } else {
            try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(
                    Database.class.getResourceAsStream("config/database.conf")))) {
                host = fileReader.readLine();
                user = fileReader.readLine();
                password = fileReader.readLine();
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    /**
     * Sets the name of the database
     *
     * @param databaseName DatabaseName Object
     */
    public static void setDatabaseName(DatabaseName databaseName) {
        Database.databaseName = databaseName;
    }

    /**
     * Returns the current instance of the database
     *
     * @return database object
     */
    public static Database getInstance() throws SQLException {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /**
     * Fetches data from the database and parses via the Gson library it to an object.
     *
     * @param table           Database table to fetch from
     * @param resultType      Type of object, which will be returned
     * @param whereParameters Where parameters which will be concatenated by AND
     * @param orderByClause   order by clause which will be appended on the sql statement
     * @return Object with fetched data
     * @throws SQLException thrown, when something went wrong executing the SQL statement
     */
    public List<DatabaseObject> getObject(Table table, Type resultType, Map<String, Object> whereParameters,
                                                    String orderByClause) throws SQLException {
        return this.getObject(table, null, resultType, null, whereParameters, orderByClause);
    }

    /**
     * @see Database#getObject(Table, Type, Map, String)
     */
    public List<DatabaseObject> getObject(Table table, Type resultType, Map<String, Object> whereParameters) throws SQLException {
        return this.getObject(table, resultType, whereParameters, null);
    }

    public List<DatabaseObject> getObject(Table table, Table joinOn, Type resultType, Map<String, String> onParameters, Map<String, Object> whereParameters, String orderByClause) throws SQLException {
        Gson gson = new GsonBuilder().create();
        JSONArray jsonArray;
        if (joinOn == null && onParameters == null) {
            jsonArray = this.get(table, whereParameters, orderByClause);
        } else {
            jsonArray = this.get(table, joinOn, onParameters, whereParameters, orderByClause);
        }

        return getDatabaseObjects(resultType,gson,jsonArray);
    }

    private List<DatabaseObject> getDatabaseObjects(Type resultType, Gson gson, JSONArray jsonArray) {
        List<DatabaseObject> result = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            result.add(gson.fromJson(jsonObject.toString(), resultType));
        }
        return result;
    }

    public List<DatabaseObject> getObject(Table table, Table joinOn, Type resultType, Map<String, String> onParameters, Map<String, Object> whereParameters) throws SQLException {
        return getObject(table, joinOn, resultType, onParameters, whereParameters, null);
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
     * Fetches data from database by joining two tables.
     *
     * @param table           right table for the join
     * @param joinOn          left table for the join
     * @param onParameters    columns that are used for the join. It is important that the column,which is passed as key is
     *                        in the right table and the column that is passed as value is in the left table of the join
     * @param whereParameters parameters that are used in the where clause to select data
     * @return result of the database query
     */
    public JSONArray get(Table table, Table joinOn, Map<String, String> onParameters, Map<String, Object> whereParameters, String orderByClause) throws SQLException {
        PreparedStatement statement;
        StringBuilder query = new StringBuilder(SELECT_FROM + table.getTableName() + " INNER JOIN " + joinOn.getTableName() +
                " ON ");
        for (Map.Entry<String, String> entry : onParameters.entrySet()) {
            query.append(table.getTableName()).append(".").append(entry.getKey()).append(" = ").append(joinOn.getTableName()).append(".").append(entry.getValue());
        }
        query.append(this.getClause(whereParameters, WHERE, AND));
        query.append(this.getOrderByClause(orderByClause));

        statement = connection.prepareStatement(query.toString());
        this.preparedStatement(statement, whereParameters);

        // execute query
        ResultSet result = statement.executeQuery();
        return Converter.convertResultSetIntoJSON(result);
    }

    public JSONArray get(Table table, Table joinOn, Map<String, String> onParameters, Map<String, Object> whereParameters) throws SQLException {
        return get(table, joinOn, onParameters, whereParameters, null);
    }

    /**
     * @param tableName       database table to fetch from
     * @param whereParameters where parameters which will be concatenated by AND
     * @param orderByClause   order by clause which will be appended on the sql statement
     * @return JSONArray that contains
     * @throws SQLException thrown, when something went wrong executing the SQL statement
     */
    public JSONArray get(Table tableName, Map<String, Object> whereParameters, String orderByClause) throws SQLException {
        PreparedStatement statement;
        String query = SELECT_FROM + tableName.getTableName() +
                this.getClause(whereParameters, WHERE, AND) +
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
                this.getClause(whereParameters, WHERE, AND);

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
                this.getClause(whereParameters, WHERE, AND);
        statement = this.preparedStatement(connection.prepareStatement(query), whereParameters);
        statement.execute();
    }

    /**
     * Clears all data in the database. Only possible if server is in test mode.
     *
     * @throws SQLException thrown, when something went wrong executing the SQL statement
     */
    public void clearDatabase() throws SQLException {
        if (databaseName == DatabaseName.TEST || databaseName == DatabaseName.JENKINS) {
            try (PreparedStatement disableForeignKeyChecksStatement = this.connection.prepareStatement("SET FOREIGN_KEY_CHECKS = 0")) {
                disableForeignKeyChecksStatement.execute();
            }
            for (Table table : Table.values()) {
                try (PreparedStatement truncateTableStatement = this.connection.prepareStatement("TRUNCATE TABLE " + table.getTableName())) {
                    truncateTableStatement.execute();
                }
            }
            try (PreparedStatement enableForeignKeyChecksStatement = this.connection.prepareStatement("SET FOREIGN_KEY_CHECKS = 1")) {
                enableForeignKeyChecksStatement.execute();
            }
        }
    }

    /**
     * Returns the latest id that was inserted into the table.
     *
     * @param table database table that will be requested
     * @return the latest id in the table
     * @throws SQLException thrown, when something went wrong executing the SQL statement
     */
    public int getLatestId(Table table) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT id FROM " +
                table.getTableName() +
                " ORDER BY id DESC LIMIT 1")) {
            ResultSet result = statement.executeQuery();
            return Converter.convertResultSetIntoJSON(result).getJSONObject(0).getInt("id");
        }
    }

    public List<DatabaseObject> findByRange(Table tableName,Map<String, Object> whereParameters, Type resultType,Map<String, Object> operatorList) throws SQLException{
        Gson gson = new GsonBuilder().create();

        PreparedStatement statement;
        String query = SELECT_FROM + tableName.getTableName() +
                this.getClause(whereParameters, WHERE, " " + AND + " ", operatorList);

        statement = this.preparedStatement(connection.prepareStatement(query),whereParameters);

        // execute
        JSONArray jsonArray = Converter.convertResultSetIntoJSON(statement.executeQuery());
        return getDatabaseObjects(resultType, gson, jsonArray);
    }

    public List<DatabaseObject> findByRange(List<Table> tableList, Map<String, Object> whereParameters, Map<String, Object> onParameters, Type resultType, Map<String, Object>  operatorList) throws SQLException {
        Gson gson = new GsonBuilder().create();

        StringBuilder query = new StringBuilder("SELECT " + tableList.get(0).getTableName() + ".* FROM ");

        boolean first = true;
        for (Table table : tableList) {
            if (first) {
                first = false;
                query.append(table.getTableName());
            }
            else{
                query.append(", ")
                        .append(table.getTableName());
            }
        }

        query.append(this.getClause(whereParameters, WHERE, " " + AND + " ", operatorList));

        for (Map.Entry<String, Object> entry : onParameters.entrySet()) {
            query.append(" AND ").append(entry.getKey()).append(" = ").append(entry.getValue());
        }

        // execute
        PreparedStatement statement;
        statement = this.preparedStatement(connection.prepareStatement(query.toString()), whereParameters);
        JSONArray jsonArray = Converter.convertResultSetIntoJSON(statement.executeQuery());
        return getDatabaseObjects(resultType, gson, jsonArray);
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

    private String getClause(Map<String, Object> values, String operation, String separator,Map<String, Object> operators) {
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
                whereClauseString.append(entry.getKey()).append(" ")
                        .append(operators.get(entry.getKey()))
                        .append(" ?");
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
            } else if (entry.getValue() instanceof Boolean) {
                statement.setObject(index, (boolean) entry.getValue() ? 1 : 0);
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
