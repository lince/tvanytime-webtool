package br.ufscar.dc.lince.tvanytime.core;

import java.io.Serializable;

/**
 *
 * @author lince
 */
public class ProgramInformationTableResult implements Serializable{

    private String tableName="";
    private int id=0;
    private long timestamp=0;

    public ProgramInformationTableResult() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    public void setTime(long time)
    {
        timestamp=time;
    }

    public long getTime()
    {
        return timestamp;
    }

}
