package com.example.lib.Repository;


import com.example.lib.Model.Bill;
import com.example.lib.Model.BillResult;
import com.example.lib.Model.GetListUserName;
import com.example.lib.Model.Product_Cart;
import com.example.lib.Model.addQuality;
import com.example.lib.Model.addToCartResult;
import com.example.lib.Model.brand;
import com.example.lib.Model.delete;
import com.example.lib.Model.login;
import com.example.lib.Model.loginResult;
import com.example.lib.Model.product;
import com.example.lib.Model.productBrand;
import com.example.lib.Model.productName;
import com.example.lib.Model.register;
import com.example.lib.Model.messageResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface Methods {
    @GET("/router/product")
    Call<product[]> getProduct();

    @GET("/router/brand")
    Call<brand[]> getBrand();

    @POST("/router/auth/login")
    Call<loginResult> login(@Body login data);

    @POST("/router/user")
    Call<messageResult> signUp(@Body register s);

    @POST("/router/cart/getList")
    Call<Product_Cart[]> GetCart(@Body GetListUserName s);

    @PUT("router/cart/checkexits")
    Call<messageResult> UpdateQuality(@Body addQuality s);

    @POST("router/cart/")
    Call<addToCartResult> addToCart(@Body addQuality s);

    @POST("router/cart/del")
    Call<messageResult> Del(@Body delete s);

    @POST("/router/invoice")
    Call<BillResult> bill(@Body Bill s);

    @POST("/router/product/findByName")
    Call<product[]> findByName(@Body productName s);
    @POST("/router/product/findByBrand")
    Call<product[]> findByBrand(@Body productBrand s);
}
