package org.example.common.model;

import java.io.Serializable;

/**
 * 用户
 */
public class User implements Serializable {

    private String name;
    private String Messages;

    public String getName() {
        return name;
    }

    public String getMessage() {
        return Messages;
    }
    public void setName(String name) {
        this.name = name;
    }
}
