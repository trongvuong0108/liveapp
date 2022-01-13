package com.example.lib.Model;

public class GetListUserName {
    private String username;

    public String getUsername ()
    {
        return username;
    }

    public void setUsername (String username)
    {
        this.username = username;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [username = "+username+"]";
    }
}
