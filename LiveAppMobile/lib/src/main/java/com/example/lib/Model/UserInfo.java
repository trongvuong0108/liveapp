package com.example.lib.Model;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private String img;

    private String role;

    private String phone;

    private String fullname;

    private String username;

    public String getImg ()
    {
        return img;
    }

    public void setImg (String img)
    {
        this.img = img;
    }

    public String getRole ()
    {
        return role;
    }

    public void setRole (String role)
    {
        this.role = role;
    }

    public String getPhone ()
    {
        return phone;
    }

    public void setPhone (String phone)
    {
        this.phone = phone;
    }

    public String getFullname ()
    {
        return fullname;
    }

    public void setFullname (String fullname)
    {
        this.fullname = fullname;
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
        return "ClassPojo [img = "+img+", role = "+role+", phone = "+phone+", fullname = "+fullname+", username = "+username+"]";
    }
}
