package com.example.lib.Model;

import java.io.Serializable;

public class product implements Serializable {
    private String outPrice;

    private String createdAt;

    private String img;

    private String productId;

    private String inPrice;

    private String brandId;

    private String CPU;

    private String productNumber;

    private String productName;

    private String RAM;

    private String updatedAt;

    public String getOutPrice ()
    {
        return outPrice;
    }

    public void setOutPrice (String outPrice)
    {
        this.outPrice = outPrice;
    }

    public String getCreatedAt ()
    {
        return createdAt;
    }

    public void setCreatedAt (String createdAt)
    {
        this.createdAt = createdAt;
    }

    public String getImg ()
    {
        return img;
    }

    public void setImg (String img)
    {
        this.img = img;
    }

    public String getProductId ()
    {
        return productId;
    }

    public void setProductId (String productId)
    {
        this.productId = productId;
    }

    public String getInPrice ()
    {
        return inPrice;
    }

    public void setInPrice (String inPrice)
    {
        this.inPrice = inPrice;
    }

    public String getBrandId ()
    {
        return brandId;
    }

    public void setBrandId (String brandId)
    {
        this.brandId = brandId;
    }

    public String getCPU ()
    {
        return CPU;
    }

    public void setCPU (String CPU)
    {
        this.CPU = CPU;
    }

    public String getProductNumber ()
    {
        return productNumber;
    }

    public void setProductNumber (String productNumber)
    {
        this.productNumber = productNumber;
    }

    public String getProductName ()
    {
        return productName;
    }

    public void setProductName (String productName)
    {
        this.productName = productName;
    }

    public String getRAM ()
    {
        return RAM;
    }

    public void setRAM (String RAM)
    {
        this.RAM = RAM;
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
        return "ClassPojo [outPrice = "+outPrice+", createdAt = "+createdAt+", img = "+img+", productId = "+productId+", inPrice = "+inPrice+", brandId = "+brandId+", CPU = "+CPU+", productNumber = "+productNumber+", productName = "+productName+", RAM = "+RAM+", updatedAt = "+updatedAt+"]";
    }
}
