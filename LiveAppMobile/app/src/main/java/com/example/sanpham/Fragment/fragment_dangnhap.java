package com.example.sanpham.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.lib.Interface.IChuyenData;
import com.example.lib.Model.UserInfo;
import com.example.lib.Model.login;
import com.example.lib.Model.loginResult;
import com.example.lib.Repository.Methods;
import com.example.lib.retrofitClient;
import com.example.sanpham.MainActivity;
import com.example.sanpham.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_dangnhap#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_dangnhap extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btn_dn;
    private IChuyenData iChuyenData;
    private View view ;
    private EditText dn_UserName;
    private EditText dn_Password;
    public UserInfo userInfo ;
    public login loginAccount;
    public fragment_dangnhap() {

    }



    public static fragment_dangnhap newInstance(String param1, String param2) {
        fragment_dangnhap fragment = new fragment_dangnhap();
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
        view =  inflater.inflate(R.layout.fragment_dangnhap, container, false);
        anhXa();
        btn_dn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                startActivity(intent);
                login temp = new login();
                temp.setUsername(dn_UserName.getText().toString());
                temp.setPassword(dn_Password.getText().toString());


                Methods methods = retrofitClient.getRetrofit().create(Methods.class);
                Call<loginResult> call = methods.login(temp);
                call.enqueue(new Callback<loginResult>() {
                    @Override
                    public void onResponse(Call<loginResult> call, Response<loginResult> response) {
                        loginResult res = response.body();

                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        // thông báo đăng nhập thành công
                        if( res!= null){
                            Toast.makeText(getContext(), res.getMessage() ,Toast.LENGTH_SHORT).show();
                            fragmentTransaction.replace(R.id.fragmentContainerView, new Profile_Fragment(res.getUserInfo()));
                            fragmentTransaction.addToBackStack("Fragment Detail");
                            fragmentTransaction.commit();
                            userInfo = res.getUserInfo();
                            Intent intent = new Intent(getActivity(),MainActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("data",userInfo);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            getActivity().finish();
                        }
                        else
                            Toast.makeText(getContext(), "Đăng nhập sai" ,Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<loginResult> call, Throwable t) {

                    }
                });

                UserInfo x= userInfo;

            }
        });
        return view;
    }
    private void anhXa(){
        btn_dn=view.findViewById(R.id.btn_dn);
        iChuyenData = (IChuyenData) getActivity();
        dn_UserName = view.findViewById(R.id.dn_UserName);
        dn_Password = view.findViewById(R.id.dn_Password);
   }


}