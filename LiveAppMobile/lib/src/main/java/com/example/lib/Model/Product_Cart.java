package com.example.lib.Model;

public class Product_Cart {
    private String number;

    private String createdAt;

    private product product;

    private String productId;

    private String username;

    private String updatedAt;

    public String getNumber ()
    {
        return number;
    }

    public void setNumber (String number)
    {
        this.number = number;
    }

    public String getCreatedAt ()
    {
        return createdAt;
    }

    public void setCreatedAt (String createdAt)
    {
        this.createdAt = createdAt;
    }

    public product getProduct ()
    {
        return product;
    }

    public void setProduct (product product)
    {
        this.product = product;
    }

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

    public String getUpdatedAt ()
    {
        return updatedAt;
    }

    public void setUpdatedAt (String updatedAt)
    {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [number = "+number+", createdAt = "+createdAt+", product = "+product+", productId = "+productId+", username = "+username+", updatedAt = "+updatedAt+"]";
    }
}
