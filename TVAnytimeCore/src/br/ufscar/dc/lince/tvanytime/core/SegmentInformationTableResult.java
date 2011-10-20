package br.ufscar.dc.lince.tvanytime.core;

import java.io.Serializable;

/**
 *
 * @author lince
 */
public class SegmentInformationTableResult implements Serializable{

    private String filename="";
    private String tableName="";    
    private int id=0;
    private long timestamp=0;


    public SegmentInformationTableResult() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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
