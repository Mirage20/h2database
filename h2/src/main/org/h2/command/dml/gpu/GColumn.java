package org.h2.command.dml.gpu;

/**
 * Created by mirage on 1/16/17.
 */
class GColumn {

    String colName;
    int colID;
    float[] values;

    GColumn(String colName, int colID, float[] values){
        this.colName = colName;
        this.colID = colID;
        this.values = values;
    }

}
