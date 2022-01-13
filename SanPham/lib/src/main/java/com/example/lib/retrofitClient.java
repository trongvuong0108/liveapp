package com.example.lib;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class retrofitClient {
    private static Retrofit retrofit ;
    private static String Base_URL= "http://10.0.2.2:2222/";


    public static String getBase_URL() {
        return Base_URL;
    }

    public static void setBase_URL(String base_URL) {
        Base_URL = base_URL;
    }

    public static Retrofit getRetrofit(){
        if(retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(Base_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
