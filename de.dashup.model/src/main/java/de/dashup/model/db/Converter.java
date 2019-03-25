package de.dashup.model.db;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

class Converter {
    /**
     * Convert a result set into a JSON Array
     *
     * @param resultSet result set that is returned by a MySQL GET command
     * @return a JSONArray
     * @throws SQLException thrown, if something went wrong with the result set
     */
    static JSONArray convertResultSetIntoJSON(ResultSet resultSet) throws SQLException {
        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            int total_rows = resultSet.getMetaData().getColumnCount();
            JSONObject obj = new JSONObject();
            for (int i = 0; i < total_rows; i++) {
                String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
                Object columnValue = resultSet.getObject(i + 1);
                // if value in DB is null, then we set it to default value
                if (columnValue == null) {
                    columnValue = "null";
                }
                /* Next if block is a hack. In case when in db we have values like price and price1 there's a bug in jdbc -
                 * both this names are getting stored as price in ResulSet. Therefore when we store second column value,
                 * we overwrite original value of price. To avoid that, i simply add 1 to be consistent with DB.
                 */
                if (obj.has(columnName)) {
                    columnName += "1";
                }
                obj.put(columnName, columnValue);
            }
            jsonArray.put(obj);
        }
        return jsonArray;
    }

}