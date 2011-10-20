package br.ufscar.dc.lince.tvanytime.core;

import java.io.Serializable;

/**
 *
 * @author lince
 */
public class ContentReferencingTableResult implements Serializable{

    private String name=null;
    private int id=0;
    private long timestamp=0;

    public ContentReferencingTableResult() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
