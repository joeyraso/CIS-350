package com.parse.starter;

import com.parse.ParseObject;

import java.io.Serializable;

/**
 * Created by Anjali on 4/14/15.
 */
public class SerializableJob implements Serializable {

    private static final int serialVersionUID = 46548;
    private ParseObject obj;

    public void setObj(ParseObject o) {
        obj = o;
    }

    public ParseObject getObj() {
        return obj;
    }

}
