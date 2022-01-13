package com.example.lib.Model;

public class loginResult {
    private UserInfo userInfo;

    private String Message;

    private String token;

    public UserInfo getUserInfo ()
    {
        return userInfo;
    }

    public void setUserInfo (UserInfo userInfo)
    {
        this.userInfo = userInfo;
    }

    public String getMessage ()
    {
        return Message;
    }

    public void setMessage (String Message)
    {
        this.Message = Message;
    }

    public String getToken ()
    {
        return token;
    }

    public void setToken (String token)
    {
        this.token = token;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [userInfo = "+userInfo+", Message = "+Message+", token = "+token+"]";
    }
}
