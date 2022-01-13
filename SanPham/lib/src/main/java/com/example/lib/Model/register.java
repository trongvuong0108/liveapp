package com.example.lib.Model;

public class register {
    private String password;

    private String phone;

    private String fullname;

    private String username;

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    public String getPhone ()
    {
        return phone;
    }

    public void setPhone (String phone)
    {
        this.phone = phone;
    }

    public String getFullName ()
    {
        return fullname;
    }

    public void setFullName (String fullName)
    {
        this.fullname = fullName;
    }

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
        return "ClassPojo [password = "+password+", phone = "+phone+", fullName = "+fullname+", username = "+username+"]";
    }
}
