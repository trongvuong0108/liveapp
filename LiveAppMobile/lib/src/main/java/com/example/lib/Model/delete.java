package com.example.lib.Model;

public class delete {
    private String productId;

    private String username;

    public String getProductId ()
    {
        return productId;
    }

    public void setProductId (String productId)
    {
        this.productId = productId;
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
        return "ClassPojo [productId = "+productId+", username = "+username+"]";
    }
}
