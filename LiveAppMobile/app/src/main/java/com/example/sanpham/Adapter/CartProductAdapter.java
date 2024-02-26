package com.example.sanpham.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.lib.Model.GetListUserName;
import com.example.lib.Model.Product_Cart;
import com.example.lib.Model.UserInfo;
import com.example.lib.Model.addQuality;
import com.example.lib.Model.delete;
import com.example.lib.Model.messageResult;
import com.example.lib.Repository.Methods;
import com.example.lib.retrofitClient;
import com.example.sanpham.Fragment.CartFragment;
import com.example.sanpham.MainActivity;
import com.example.sanpham.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartProductAdapter extends BaseAdapter {

    private Context context;
    private int layOut;
    private List<Product_Cart> cartProducts;
    private UserInfo userInfo ;
    //private IChuyenData iChuyenData;
    private View view;

    public CartProductAdapter(Context context, int layOut, List<Product_Cart> cartProducts, UserInfo userInfo) {
        //iChuyenData = (IChuyenData) context;
        this.context = context;
        this.layOut = layOut;
        this.cartProducts = cartProducts;
        this.userInfo = userInfo;
    }

    @Override
    public int getCount() {
        return cartProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView Name;
        ImageView Img;
        TextView gia;
        ImageButton Cart_Product_btnTru;
        ImageButton Cart_Product_btnCong;
        ImageButton Cart_Product_remove;
        EditText SL;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layOut, null);
            viewHolder.Img = (ImageView) convertView.findViewById(R.id.Cart_Product_IMG);
            viewHolder.gia = (TextView) convertView.findViewById(R.id.Cart_Product_price);
            viewHolder.Name = (TextView) convertView.findViewById(R.id.Cart_Product_Name);
            viewHolder.SL = (EditText) convertView.findViewById(R.id.Cart_Product_SL);
            viewHolder.Cart_Product_btnTru = convertView.findViewById(R.id.Cart_Product_btnTru);
            viewHolder.Cart_Product_btnCong = convertView.findViewById(R.id.Cart_Product_btnCong);
            viewHolder.Cart_Product_remove = convertView.findViewById(R.id.Cart_Product_remove);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Product_Cart cartProduct = cartProducts.get(position);

        viewHolder.gia.setText(String.valueOf(Integer.parseInt(cartProduct.getProduct().getOutPrice()) * Integer.parseInt(cartProduct.getNumber())));
        Picasso.get().load(cartProduct.getProduct().getImg()).into(viewHolder.Img);
        viewHolder.SL.setText(cartProduct.getNumber() + "");
        viewHolder.Name.setText(cartProduct.getProduct().getProductName());
        XuLyClick(viewHolder, convertView, cartProduct, position);
        notifyDataSetChanged();
        return convertView;
    }

    private void capNhatTien() {
        int TongTien = 0;
        for (Product_Cart temp : cartProducts) {
            TongTien += Integer.parseInt(temp.getProduct().getOutPrice()) * Integer.parseInt(temp.getNumber());
        }
        //iChuyenData.Chuyen_TongTien(TongTien);
    }

    private void XuLyClick(ViewHolder viewHolder, View convertView, Product_Cart cart_product,int position) {
        Methods methods = retrofitClient.getRetrofit().create(Methods.class);
        viewHolder.Cart_Product_btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call api

                addQuality temp = new addQuality();
                temp.setUsername(userInfo.getUsername());
                temp.setNumber("1");
                temp.setProductId(cart_product.getProductId());
                Call<messageResult> addNew = methods.UpdateQuality(temp);
                addNew.enqueue(new Callback<messageResult>() {
                    @Override
                    public void onResponse(Call<messageResult> call, Response<messageResult> response) {
                        GetListUserName tmp = new GetListUserName();
                        tmp.setUsername(userInfo.getUsername());
                        Call<Product_Cart[]> callList = methods.GetCart(tmp);
                        callList.enqueue(new Callback<Product_Cart[]>() {
                            @Override
                            public void onResponse(Call<Product_Cart[]> call, Response<Product_Cart[]> response) {
                                for (Product_Cart i:response.body()) {
                                    if(i.getProduct().getProductId().equals(cart_product.getProductId())) viewHolder.SL.setText(i.getNumber());
                                }
                            }

                            @Override
                            public void onFailure(Call<Product_Cart[]> call, Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<messageResult> call, Throwable t) {

                    }
                });
            }
        });


        viewHolder.Cart_Product_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call api del
                delete temp = new delete();
                temp.setProductId(cart_product.getProductId());
                temp.setUsername(userInfo.getUsername());
                Call<messageResult> call = methods.Del(temp);
                call.enqueue(new Callback<messageResult>() {
                    @Override
                    public void onResponse(Call<messageResult> call, Response<messageResult> response) {


                    }

                    @Override
                    public void onFailure(Call<messageResult> call, Throwable t) {

                    }
                });
            }
        });

        if(Integer.parseInt(cart_product.getNumber()) >0 )
        {
            viewHolder.Cart_Product_btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                    GetListUserName tmp = new GetListUserName();
                    tmp.setUsername(userInfo.getUsername());
                    Call<Product_Cart[]> callList = methods.GetCart(tmp);
                    callList.enqueue(new Callback<Product_Cart[]>() {
                        @Override
                        public void onResponse(Call<Product_Cart[]> call, Response<Product_Cart[]> response) {
                            for (Product_Cart i:response.body()) {
                                if(Integer.parseInt(i.getNumber()) > 0 ) {
                                    addQuality temp =  new addQuality();
                                    temp.setUsername(userInfo.getUsername());
                                    temp.setNumber("-1");
                                    temp.setProductId(cart_product.getProductId());
                                    Call<messageResult> addNew = methods.UpdateQuality(temp);
                                    addNew.enqueue(new Callback<messageResult>() {
                                        @Override
                                        public void onResponse(Call<messageResult> call, Response<messageResult> response) {
                                            GetListUserName tmp = new GetListUserName();
                                            tmp.setUsername(userInfo.getUsername());
                                            Call<Product_Cart[]> callList = methods.GetCart(tmp);
                                            callList.enqueue(new Callback<Product_Cart[]>() {
                                                @Override
                                                public void onResponse(Call<Product_Cart[]> call, Response<Product_Cart[]> response) {
                                                    for (Product_Cart i:response.body()) {
                                                        if(i.getProduct().getProductId().equals(cart_product.getProductId())) viewHolder.SL.setText(i.getNumber());
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Product_Cart[]> call, Throwable t) {

                                                }
                                            });
                                        }

                                        @Override
                                        public void onFailure(Call<messageResult> call, Throwable t) {

                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Product_Cart[]> call, Throwable t) {

                        }
                    });

                }
            });
        }


    }
}