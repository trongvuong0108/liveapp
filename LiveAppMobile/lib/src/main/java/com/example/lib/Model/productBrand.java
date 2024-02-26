package com.example.lib.Model;

public class productBrand {
    private String brandId;

    public String getBrandId ()
    {
        return brandId;
    }

    public void setBrandId (String brandId)
    {
        this.brandId = brandId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [brandId = "+brandId+"]";
    }

}
