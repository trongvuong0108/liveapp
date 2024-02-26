package com.example.lib.Model;

public class BillResult
{
    private String createdAt;

    private String totalMoney;

    private String invoiceId;

    private String username;

    private String updatedAt;

    public String getCreatedAt ()
    {
        return createdAt;
    }

    public void setCreatedAt (String createdAt)
    {
        this.createdAt = createdAt;
    }

    public String getTotalMoney ()
    {
        return totalMoney;
    }

    public void setTotalMoney (String totalMoney)
    {
        this.totalMoney = totalMoney;
    }

    public String getInvoiceId ()
    {
        return invoiceId;
    }

    public void setInvoiceId (String invoiceId)
    {
        this.invoiceId = invoiceId;
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
        return "ClassPojo [createdAt = "+createdAt+", totalMoney = "+totalMoney+", invoiceId = "+invoiceId+", username = "+username+", updatedAt = "+updatedAt+"]";
    }
}
