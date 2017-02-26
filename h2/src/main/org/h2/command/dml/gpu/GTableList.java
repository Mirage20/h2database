package org.h2.command.dml.gpu;

import org.h2.engine.Session;
import org.h2.table.Table;
import org.h2.table.TableFilter;
import org.h2.value.Value;
import org.h2.value.ValueFloat;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mirage on 1/16/17.
 */
public class GTableList {
    private static Map<String, GTable> tableMap = new HashMap<>();

    static boolean hasTable(String tableName) {
        return tableMap.containsKey(tableName);
    }

    public static void addTable(TableFilter tableFilter, Session session) {

        String tableName = tableFilter.getTable().getName().toUpperCase();
        if (hasTable(tableName)) {
            return;
        }
        tableMap.put(tableName, new GTable(tableName, tableFilter, session));

    }

    public static boolean hasFiltering(String sql) {
        // TODO: 1/16/17 double check
        if (sql.toLowerCase().contains("where")) {
            return true;
        }
        return false;
    }

    public static Value execute(String sql, TableFilter tableFilter, Session session) {
        String tableName = sql.substring(sql.indexOf("FROM") + 5);
        String operation = sql.substring(sql.indexOf("SELECT") + 7, sql.indexOf("FROM") - 1);
        String colName = operation.substring(operation.indexOf("(") + 1, operation.indexOf(")"));

        GTable table = tableMap.get(tableName.toUpperCase());

        String operator = operation.substring(0,operation.indexOf("("));

        Value value = ValueFloat.get(0.0f);
        if (table != null) {
            GColumn col = table.getColumn(colName.toUpperCase());
            if (col != null) {
                switch (operator.toUpperCase()){
                    case "SUM":
                        value = ValueFloat.get(GMath.sum(col.values));
                        break;
                    case "MAX":
                        value = ValueFloat.get(GMath.max(col.values));
                        break;
                }

            }
        }

        return value;
    }
}
