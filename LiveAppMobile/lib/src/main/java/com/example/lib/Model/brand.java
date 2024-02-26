package com.example.lib.Model;

public class brand {
    private String brandImg;

    private String createdAt;

    private String brandName;

    private String brandId;

    private String updatedAt;

    public String getBrandImg ()
    {
        return brandImg;
    }

    public void setBrandImg (String brandImg)
    {
        this.brandImg = brandImg;
    }

    public String getCreatedAt ()
    {
        return createdAt;
    }

    public void setCreatedAt (String createdAt)
    {
        this.createdAt = createdAt;
    }

    public String getBrandName ()
    {
        return brandName;
    }

    public void setBrandName (String brandName)
    {
        this.brandName = brandName;
    }

    public String getBrandId ()
    {
        return brandId;
    }

    public void setBrandId (String brandId)
    {
        this.brandId = brandId;
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
        return "ClassPojo [brandImg = "+brandImg+", createdAt = "+createdAt+", brandName = "+brandName+", brandId = "+brandId+", updatedAt = "+updatedAt+"]";
    }
}
