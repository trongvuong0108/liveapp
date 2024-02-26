package com.example.sanpham.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.lib.Model.login;
import com.example.lib.Model.loginResult;
import com.example.lib.Model.messageResult;
import com.example.lib.Model.register;
import com.example.lib.Repository.Methods;
import com.example.lib.retrofitClient;
import com.example.sanpham.MainActivity;
import com.example.sanpham.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DangKiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DangKiFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    TextView username, name, phone;
    EditText password;
    private View result;
    public DangKiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DangKiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DangKiFragment newInstance(String param1, String param2) {
        DangKiFragment fragment = new DangKiFragment();
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
        result = inflater.inflate(R.layout.fragment_dangki, container, false);;
        anhxa();
        Button btn = result.findViewById(R.id.dangki);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solve();
            }
        });
        // Inflate the layout for this fragment
        return result ;
    }

    public void anhxa(){
        username = result.findViewById(R.id.username);
        name = result.findViewById(R.id.fullname);
        phone = result.findViewById(R.id.phone);
        password = result.findViewById(R.id.password);
    }

    public void solve(){
        register temp = new register();
        temp.setUsername(username.getText().toString());
        temp.setPassword(password.getText().toString());
        temp.setFullName(name.getText().toString());
        temp.setPhone(phone.getText().toString());

        login tempLogin = new login();
        tempLogin.setUsername(username.getText().toString());
        tempLogin.setPassword(password.getText().toString());
        Methods methods = retrofitClient.getRetrofit().create(Methods.class);
        Call<messageResult> call = methods.signUp(temp);
        call.enqueue(new Callback<messageResult>() {
            @Override
            public void onResponse(Call<messageResult> call, Response<messageResult> response) {

                Call<loginResult> callLogin = methods.login(tempLogin);
                callLogin.enqueue(new Callback<loginResult>() {
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
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("data",res.getUserInfo());
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
            }

            @Override
            public void onFailure(Call<messageResult> call, Throwable t) {

            }
        });
    }
}