package com.example.sanpham.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.lib.Interface.IChuyenData;
import com.example.lib.Model.GetListUserName;
import com.example.lib.Model.Product_Cart;
import com.example.lib.Model.UserInfo;
import com.example.lib.Model.addQuality;
import com.example.lib.Model.addToCartResult;
import com.example.lib.Model.messageResult;
import com.example.lib.Model.product;
import com.example.lib.Repository.Methods;
import com.example.lib.retrofitClient;
import com.example.sanpham.MainActivity;
import com.example.sanpham.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_detail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_detail extends Fragment {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view;
    int sl ;
    product p;
    ImageView img;
    UserInfo userInfo;
    TextView Name,Price,description;
    ImageButton btn_cart, detail_sl_cong, detail_sl_tru ,btn_back_product;
    EditText detail_sl;
    IChuyenData iChuyenData;
    private Button detail_btnThemSP;


    public Fragment_detail(UserInfo userInfo){
        this.userInfo = userInfo;
    }
    public Fragment_detail() {
    }


    public static Fragment_detail newInstance(String param1, String param2) {
        Fragment_detail fragment = new Fragment_detail();
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
        MainActivity mainActivity = (MainActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_detail, container, false);


        anhXa();
        Bundle bundle = this.getArguments();
        p= (product) bundle.getSerializable("product");
        /// gán giá trị cho từng view
        SetData(p);
        btn_back_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        xuLy();
        open_cart();
        return view;
    }

    private void xuLy() {
        detail_sl_cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = Integer.parseInt(detail_sl.getText().toString());
                temp++;
                String s = String.valueOf(temp);
                detail_sl.setText(s);
            }
        });

        detail_sl_tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = Integer.parseInt(detail_sl.getText().toString());
                if(temp == 1 )
                {
                    Toast.makeText(getContext(),"Sl > 0",Toast.LENGTH_LONG);
                }
                else{

                    temp--;
                    String s = String.valueOf(temp);
                    detail_sl.setText(s);
                }
            }
        });

        detail_btnThemSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userInfo ==  null)
                {
                    Fragment fragment = new NonUserProfileFragment();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
                    fragmentTransaction.addToBackStack("Fragment home");
                    fragmentTransaction.commit();
                }
                else{
                    Methods methods = retrofitClient.getRetrofit().create(Methods.class);


                    GetListUserName userName = new GetListUserName();
                    userName.setUsername(userInfo.getUsername());
                    Call<Product_Cart[]> call = methods.GetCart(userName);

                    addQuality temp =  new addQuality();
                    temp.setUsername(userInfo.getUsername());
                    temp.setNumber(detail_sl.getText().toString());
                    temp.setProductId(p.getProductId());
                    call.enqueue(new Callback<Product_Cart[]>() {
                        @Override
                        public void onResponse(Call<Product_Cart[]> call, Response<Product_Cart[]> response) {
                            int res = 0;
                            Product_Cart[] res1 = response.body();
                            if(res1.length == 0) {
                                Call<addToCartResult> addNew = methods.addToCart(temp);
                                addNew.enqueue(new Callback<addToCartResult>() {
                                    @Override
                                    public void onResponse(Call<addToCartResult> call, Response<addToCartResult> response) {
                                        Toast.makeText(getContext(),"Thêm thành công",Toast.LENGTH_LONG);
                                    }

                                    @Override
                                    public void onFailure(Call<addToCartResult> call, Throwable t) {

                                    }
                                });
                            }
                            for (Product_Cart i: response.body()) {
                                if( !i.getProduct().getProductId().equals(p.getProductId()))
                                {
                                    //call api add new
                                    Call<addToCartResult> addNew = methods.addToCart(temp);
                                    addNew.enqueue(new Callback<addToCartResult>() {
                                        @Override
                                        public void onResponse(Call<addToCartResult> call, Response<addToCartResult> response) {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                                builder.setCancelable(true);
                                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                                builder.setMessage("Thêm mới thành công");
                                                builder.show();
                                        }

                                        @Override
                                        public void onFailure(Call<addToCartResult> call, Throwable t) {

                                        }
                                    });

                                }
                                else
                                {
                                    //call api checkexits
                                    Call<messageResult> checkExits = methods.UpdateQuality(temp);
                                    checkExits.enqueue(new Callback<messageResult>() {
                                        @Override
                                        public void onResponse(Call<messageResult> call, Response<messageResult> response) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                            builder.setCancelable(true);
                                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                            builder.setMessage("Thêm thành công");
                                            builder.show();
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


            }
        });

    }


    private void CheckSL() {
        sl =Integer.parseInt(detail_sl.getText().toString());
        if(sl <0)
        {
        }
    }

    private void open_cart() {

        btn_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerView, new CartFragment(userInfo));
                    fragmentTransaction.addToBackStack("fragment cart ");
                    fragmentTransaction.commit();

                }
            });

    }




    private void anhXa() {
        iChuyenData = (IChuyenData) getActivity();
        detail_btnThemSP = view.findViewById(R.id.detail_btnThemSP);
        detail_sl= view.findViewById(R.id.detail_sl);
        btn_cart = view.findViewById(R.id.btn_cart);
        detail_sl_cong = view.findViewById(R.id.detail_sl_cong);
        detail_sl_tru = view.findViewById(R.id.detail_sl_tru);
        this.img = view.findViewById(R.id.detail_img);
        btn_back_product = view.findViewById(R.id.btn_back_product);
        this.Name = view.findViewById(R.id.detail_ten);
        this.Price = view.findViewById(R.id.detail_gia);
        this.description = view.findViewById(R.id.detail_MoTa);
    }

    private void SetData(product product_in) {
        Picasso.get().load(product_in.getImg()).into(this.img);
        Name.setText(product_in.getProductName());
        Price.setText(product_in.getOutPrice()+" ");
        description.setText(product_in.getCPU());

    }
}