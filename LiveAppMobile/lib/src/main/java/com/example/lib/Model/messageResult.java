package com.example.lib.Model;

public class messageResult {
    private String Message;

    public String getMessage ()
    {
        return Message;
    }

    public void setMessage (String Message)
    {
        this.Message = Message;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Message = "+Message+"]";
    }
}
