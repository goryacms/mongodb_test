package ru.bellintegrator.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
public class RedisVO implements Serializable {
    private static final long serialVersionUID = 2259943519977343907L;

    @Id
    private Integer keyID;
    private String dbname;
    private String dbversion;

    public Integer getKeyID() {
        return keyID;
    }

    public void setKeyID(Integer keyID) {
        this.keyID = keyID;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getDbversion() {
        return dbversion;
    }

    public void setDbversion(String dbversion) {
        this.dbversion = dbversion;
    }
}
