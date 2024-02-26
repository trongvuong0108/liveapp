package com.example.lib.Model;

public class Bill
{
    private String[][] arr;

    private String totalMoney;

    private String username;

    public String[][] getArr ()
    {
        return arr;
    }

    public void setArr (String[][] arr)
    {
        this.arr = arr;
    }

    public String getTotalMoney ()
    {
        return totalMoney;
    }

    public void setTotalMoney (String totalMoney)
    {
        this.totalMoney = totalMoney;
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
        return "ClassPojo [arr = "+arr+", totalMoney = "+totalMoney+", username = "+username+"]";
    }
}

