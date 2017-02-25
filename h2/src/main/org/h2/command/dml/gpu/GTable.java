package org.h2.command.dml.gpu;

import org.h2.engine.Session;
import org.h2.mvstore.type.DataType;
import org.h2.table.Column;
import org.h2.table.Table;
import org.h2.table.TableFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mirage on 1/16/17.
 */
class GTable {
    private String tableName;
    private Map<String, GColumn> tableColumns = new HashMap<>();

    GTable(String tableName, TableFilter tableFilter, Session session) {
        this.tableName = tableName;

        Column[] columns = tableFilter.getTable().getColumns();

        for (Column column : columns) {
            //// TODO: 1/16/17 find data types
            if (column.getType() == 8) {
                String colName = column.getName().toUpperCase();
                int colId = column.getColumnId();
                long rowCount = tableFilter.getTable().getRowCount(session);

                if (rowCount < Integer.MAX_VALUE) {
                    float[] values = new float[(int) rowCount];

                    int i = 0;
                    while (tableFilter.next()) {
                        values[i] = tableFilter.get().getValue(colId).getFloat();
                        i++;
                    }
                    tableFilter.reset();

                    tableColumns.put(colName, new GColumn(colName, colId, values));
                }
            }
        }
    }

    GColumn getColumn(String colName){
        return tableColumns.get(colName);
    }


}
