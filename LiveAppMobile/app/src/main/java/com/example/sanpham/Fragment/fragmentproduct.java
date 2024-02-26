package com.example.sanpham.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lib.Interface.IChuyenData;
import com.example.lib.Model.UserInfo;
import com.example.lib.Model.brand;
import com.example.lib.Model.product;
import com.example.lib.Model.productBrand;
import com.example.lib.Model.productName;
import com.example.lib.Repository.Methods;
import com.example.lib.retrofitClient;
import com.example.sanpham.Adapter.HangDTAdapter;
import com.example.sanpham.Adapter.ProductAdapter;
import com.example.sanpham.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;


public class fragmentproduct extends Fragment  {
    public fragmentproduct(UserInfo userInfo){
        this.userInfo = userInfo;
    }

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    IChuyenData iChuyenData;
    ListView listView;
    ArrayList<product> listProduct;
    ArrayList<brand> listBrand;
    RecyclerView recyclerView;
    ImageButton imageButton,btn_find;
    UserInfo userInfo;
    TextView txt_find;

    private String mParam1;
    private String mParam2;

    public fragmentproduct() {
    }


    public static fragmentproduct newInstance(String param1, String param2) {
        fragmentproduct fragment = new fragmentproduct();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    View result;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iChuyenData = (IChuyenData) getActivity();
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
        result = inflater.inflate(R.layout.fragment_product, container, false);


        anhXa();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);


        open_cart();
        itemCLick();
        return result;

    }

    private void open_cart() {
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Fragment();
                if(userInfo ==  null)
                    // nếu không có tài khoản thì
                    fragment = new NonUserProfileFragment();
                    // ngược lại thì
                else
                    fragment = new CartFragment(userInfo);

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
                fragmentTransaction.addToBackStack("Fragment home");
                fragmentTransaction.commit();

            }
        });
    }



    protected void itemCLick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chuyenData(listProduct.get(position));
            }
        });

    }

    public void chuyenData(product product) {
        iChuyenData.ChuyenData(product);
    }

    private void anhXa() {
        listView = result.findViewById(R.id.listview);
        recyclerView = result.findViewById(R.id.list_hangsx);
        imageButton = result.findViewById(R.id.product_btn_cart);
        btn_find = result.findViewById(R.id.button_find);
        txt_find = result.findViewById(R.id.txt_find);
        listProduct = new ArrayList<>();
        listBrand = new ArrayList<>();

        Methods methods = retrofitClient.getRetrofit().create(Methods.class);
        Call<product[]> call = methods.getProduct();
        Call<brand[]> callBrand = methods.getBrand();

        call.enqueue(new Callback<product[]>() {
            @Override
            public void onResponse(Call<product[]> call, Response<product[]> response) {
                product[] currency = response.body();
                    for (product product : currency) {
                        //Log.v("du lieu", product.getProductName());
                        listProduct.add(product);
                    }
                ProductAdapter productAdapter = new ProductAdapter(getActivity(), R.layout.san_pham, listProduct);
                listView.setAdapter(productAdapter);
            }

            @Override
            public void onFailure(Call<product[]> call, Throwable t) {

            }
        });




        callBrand.enqueue(new Callback<brand[]>() {
            @Override
            public void onResponse(Call<brand[]> call, Response<brand[]> response) {
                brand[] currency = response.body();
                for (brand brand : currency) {
                    listBrand.add(brand);
                }

                HangDTAdapter hanDTAdapter = new HangDTAdapter(listBrand, getActivity(), R.layout.hang_sx, new HangDTAdapter.OnNoteListener() {
                    @Override
                    public void onNoteClick(brand position) {
                        productBrand brand = new productBrand();
                        brand.setBrandId(position.getBrandId());
                        Call<product[]> findBrand = methods.findByBrand(brand);
                        findBrand.enqueue(new Callback<product[]>() {
                            @Override
                            public void onResponse(Call<product[]> call, Response<product[]> response) {
                                product[] currency = response.body();
                                listProduct.clear();
                                for (product product : currency) { ;
                                    listProduct.add(product);
                                }
                                ProductAdapter productAdapter = new ProductAdapter(getActivity(), R.layout.san_pham, listProduct);
                                listView.setAdapter(productAdapter);
                            }

                            @Override
                            public void onFailure(Call<product[]> call, Throwable t) {

                            }
                        });
                    }
                });
                recyclerView.setAdapter(hanDTAdapter);
            }

            @Override
            public void onFailure(Call<brand[]> call, Throwable t) {

            }
        });


        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productName pro  = new productName();
                pro.setProductName(txt_find.getText().toString());
                Call<product[]> find = methods.findByName(pro);
                find.enqueue(new Callback<product[]>() {
                    @Override
                    public void onResponse(Call<product[]> call, Response<product[]> response) {
                        product[] currency = response.body();
                        listProduct.clear();
                        for (product product : currency) {
                            //Log.v("du lieu", product.getProductName());
                            listProduct.add(product);
                        }
                        ProductAdapter productAdapter = new ProductAdapter(getActivity(), R.layout.san_pham, listProduct);
                        listView.setAdapter(productAdapter);
                    }

                    @Override
                    public void onFailure(Call<product[]> call, Throwable t) {

                    }
                });
            }
        });
    }


}