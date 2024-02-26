package com.example.sanpham.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.lib.Model.Bill;
import com.example.lib.Model.BillResult;
import com.example.lib.Model.UserInfo;
import com.example.lib.Model.brand;
import com.example.lib.Model.product;
import com.example.lib.Repository.Methods;
import com.example.lib.retrofitClient;
import com.example.sanpham.Adapter.ProductAdapter;
import com.example.sanpham.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LiveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LiveFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Socket msocket;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    UserInfo userInfo ;

    public LiveFragment(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public LiveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LiveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LiveFragment newInstance(String param1, String param2) {
        LiveFragment fragment = new LiveFragment();
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
        View view =  inflater.inflate(R.layout.fragment_live, container, false);
        TextView name = view.findViewById(R.id.ten_sanpham);
        TextView detail = view.findViewById(R.id.mota_sanpham);
        TextView price = view.findViewById(R.id.gia_sanpham);
        ImageView img = view.findViewById(R.id.img_sanpham);
        TextView quality = view.findViewById(R.id.text_quality);
        Button btn = view.findViewById(R.id.button_buy);
        try {
            msocket = IO.socket("http://192.168.1.7:2224");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        msocket.connect();
        msocket.emit("get-live", "phat", false, null, null);


        Emitter.Listener onlive = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        boolean obj = (boolean) args[0];
                        Log.v("on stream ", String.valueOf(obj));
                        if(obj) {
                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            Fragment fragment = new NoLiveFragment();
                            fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
                            fragmentTransaction.addToBackStack("Fragment home");
                            fragmentTransaction.commit();
                        }
                    }
                });
            }
        };
        msocket.on("admin-close-live", onlive);

        Emitter.Listener live = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject obj = (JSONObject) args[0];
                        try {
                            String url = obj.getString("urlServer");
                            if (url.equals("null"))
                            {

                            }
                            else {
                                String []res = url.split("=");
                                YouTubePlayerView youTubePlayerView = view.findViewById(R.id.youtube_player_view);
                                getLifecycle().addObserver(youTubePlayerView);

                                youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                                    @Override
                                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                                        String videoId = res[1];
                                        youTubePlayer.loadVideo(videoId, 0);
                                    }
                                });
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        };
        msocket.on("receive-live", live);

        Emitter.Listener onclose = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        boolean obj = (boolean) args[0];
                        if(obj){
                            Emitter.Listener getPro = new Emitter.Listener() {
                                @Override
                                public void call(Object... args) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            JSONObject obj = (JSONObject) args[0];
                                            try {
                                                name.setText(obj.getString("productId"));
                                                detail.setText(obj.getString("CPU"));
                                                Picasso.get().load(obj.getString("img")).into(img);
                                                price.setText(obj.getString("outPrice"));
                                                quality.setText(obj.getString("productNumber"));
                                                if(Integer.parseInt(quality.getText().toString()) <= 0) btn.setText("Hết Hàng");
                                                else {
                                                    btn.setText("mua");
                                                    btn.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            if (userInfo == null )
                                                            {
                                                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                                                builder.setCancelable(true);
                                                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                        dialog.dismiss();
                                                                    }
                                                                });
                                                                builder.setMessage("Vui lòng đăng nhập");
                                                                builder.show();
                                                            }
                                                            else {

                                                                String[][] data = new String[1][3] ;
                                                                try {
                                                                    data[0][0] = obj.getString("productId");
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }
                                                                data[0][1] = "1";
                                                                try {
                                                                    data[0][2] = obj.getString("outPrice");
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }

                                                                int res = Integer.parseInt(data[0][2]);

                                                                Bill x = new Bill();
                                                                x.setArr(data);
                                                                x.setUsername(userInfo.getUsername());
                                                                x.setTotalMoney(String.valueOf(res));
                                                                Methods methods = retrofitClient.getRetrofit().create(Methods.class);
                                                                Call<BillResult> createbill = methods.bill(x);
                                                                createbill.enqueue(new Callback<BillResult>() {
                                                                    @Override
                                                                    public void onResponse(Call<BillResult> call, Response<BillResult> response) {
                                                                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                                                        builder.setCancelable(true);
                                                                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(DialogInterface dialog, int which) {
                                                                                dialog.dismiss();
                                                                            }
                                                                        });
                                                                        builder.setMessage("Mua thanh công");
                                                                        builder.show();
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<BillResult> call, Throwable t) {
                                                                        Log.v("","false");
                                                                    }
                                                                });
                                                                Emitter.Listener getQuality = new Emitter.Listener() {
                                                                    @Override
                                                                    public void call(Object... args) {
                                                                        getActivity().runOnUiThread(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                int num = (int) args[0];
                                                                                quality.setText(String.valueOf(num));
                                                                                if(num <= 0) btn.setText("Hết hàng");
                                                                                else {
                                                                                    btn.setText("Mua");
                                                                                }
                                                                            }
                                                                        });
                                                                    }
                                                                };
                                                                msocket.on("product-number", getQuality);
                                                            }
                                                        }
                                                    });
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }
                            };
                            msocket.on("send-new-product", getPro);
                        }
                        else {
                            name.setText("không có sản phẩm");
                            detail.setText("không có sản phẩm");
                            img.setImageResource(R.drawable.cua);
                            price.setText("???");
                            quality.setText("không có sản phẩm");
                            btn.setText("không có sản phẩm");
                            Log.v("pro", String.valueOf(obj));
                        }
                    }
                });
            }
        };
        msocket.on("enable-buy-client", onclose);



        return view ;
    }
}