package com.example.lib.Model;

public class productName
{
    private String productName;

    public String getProductName ()
    {
        return productName;
    }

    public void setProductName (String productName)
    {
        this.productName = productName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [productName = "+productName+"]";
    }
}

