package com.example.sanpham.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.lib.Model.Bill;
import com.example.lib.Model.BillResult;
import com.example.lib.Model.GetListUserName;
import com.example.lib.Model.Product_Cart;
import com.example.lib.Model.UserInfo;
import com.example.lib.Repository.Methods;
import com.example.lib.retrofitClient;
import com.example.sanpham.Adapter.CartProductAdapter;
import com.example.sanpham.R;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    public CartFragment(UserInfo userInfo){
        this.userInfo =userInfo;
    }
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public UserInfo userInfo;
    View view;
    ImageButton btn_back_product;
    ListView cart_list;
    private  int TongTien;
    TextView TongTien1;
    Button button  ;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cart, container, false);

        AnhXa();
        InitList();



        btn_back_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Methods methods = retrofitClient.getRetrofit().create(Methods.class);
                GetListUserName userName = new GetListUserName();
                userName.setUsername(userInfo.getUsername());
                Call<Product_Cart[]> call = methods.GetCart(userName);
                call.enqueue(new Callback<Product_Cart[]>() {
                    @Override
                    public void onResponse(Call<Product_Cart[]> call, Response<Product_Cart[]> response) {
                        Product_Cart[] temp = response.body();
                        if(response.body()!=null){
                            int res = 0;
                            String[][] data = new String[temp.length][3] ;
                            for (int i = 0; i < temp.length; i++) {
                                data[i][0] = temp[i].getProductId();
                                data[i][1] = temp[i].getNumber();
                                data[i][2] = temp[i].getProduct().getOutPrice();
                                res+= Integer.parseInt(temp[i].getProduct().getOutPrice()) * Integer.parseInt(temp[i].getNumber());
                            }
                            Bill x = new Bill();
                            x.setArr(data);
                            x.setUsername(userInfo.getUsername());
                            x.setTotalMoney(String.valueOf(res));
                            Call<BillResult> createbill = methods.bill(x);
                            createbill.enqueue(new Callback<BillResult>() {
                                @Override
                                public void onResponse(Call<BillResult> call, Response<BillResult> response) {
                                    Log.v("","success");
                                }

                                @Override
                                public void onFailure(Call<BillResult> call, Throwable t) {
                                    Log.v("","false");
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<Product_Cart[]> call, Throwable t) {

                    }
                });
            }
        });
        return view;
    }

    private void AnhXa() {

        btn_back_product= view.findViewById(R.id.btn_back_product);
        TongTien1= view.findViewById(R.id.TongTien);
        cart_list = view.findViewById(R.id.cart_list);

        button = view.findViewById(R.id.checkout);
    }

    private void InitList() {

        Methods methods = retrofitClient.getRetrofit().create(Methods.class);

        GetListUserName userName = new GetListUserName();
        userName.setUsername(userInfo.getUsername());
        Call<Product_Cart[]> call = methods.GetCart(userName);
        call.enqueue(new Callback<Product_Cart[]>() {
            @Override
            public void onResponse(Call<Product_Cart[]> call, Response<Product_Cart[]> response) {
                int res = 0;
                CartProductAdapter productAdapter = new CartProductAdapter(getActivity(), R.layout.cart_product, Arrays.asList(response.body()),userInfo);
                cart_list.setAdapter(productAdapter);
                for (Product_Cart i: response.body()) {
                    res+= Integer.parseInt(i.getProduct().getOutPrice()) * Integer.parseInt(i.getNumber());
                }
                TongTien1.setText("$ "+String.valueOf(res));
            }

            @Override
            public void onFailure(Call<Product_Cart[]> call, Throwable t) {
                Log.v("","");
            }
        });

    }


}