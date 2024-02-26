package com.example.sanpham;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lib.Model.live_model;
import com.example.lib.Model.login;
import com.example.lib.Model.loginResult;
import com.example.lib.Model.product;
import com.example.lib.Repository.Methods;
import com.example.lib.retrofitClient;
import com.example.sanpham.Adapter.ProductAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity {
    Socket msocket,productSoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        try {
            msocket = IO.socket("http://192.168.1.7:2224");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        msocket.connect();
        msocket.emit("get-live", "phat", false, null, null);

        Emitter.Listener live = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject obj = (JSONObject) args[0];
                        try {
                            String url = obj.getString("urlServer");
                            if (url == "null" )
                            {
                                Log.v("Khong live", "");
                                YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
                                getLifecycle().addObserver(youTubePlayerView);

                                youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                                    @Override
                                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                                        String videoId = "-vKIdF-JHSU";
                                        youTubePlayer.loadVideo(videoId, 0);
                                    }
                                });
                            }
                            else {
                                Log.v("url", url);
                                String []res = url.split("=");
                                YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
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

                        /*try {
                            JSONObject res = obj.getJSONObject("productItem");
                            Gson gson = new Gson();
                            JsonParser parser = new JsonParser();
                            JsonObject object = (JsonObject) parser.parse(String.valueOf(res));// response will be the json String
                            product emp = gson.fromJson(object, product.class);
                            Log.v("url", emp.getProductName());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            String streamer = obj.getString("liver");
                            Log.v("url", streamer);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                    }
                });
            }
        };
        msocket.on("receive-live", live);

        Emitter.Listener onclose = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        boolean obj = (boolean) args[0];
                        if(obj){
                            Emitter.Listener getPro = new Emitter.Listener() {
                                @Override
                                public void call(Object... args) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            JSONObject obj = (JSONObject) args[0];
                                            try {
                                                /*product res = new product();
                                                res.setProductId(obj.getString("productId"));
                                                res.setProductName(obj.getString("productName"));
                                                res.setImg(obj.getString("img"));
                                                res.setOutPrice(obj.getString("outPrice"));
                                                Log.v("pro", res.getProductName());*/
                                                TextView name = findViewById(R.id.ten_sanpham);
                                                TextView detail = findViewById(R.id.mota_sanpham);
                                                TextView price = findViewById(R.id.gia_sanpham);
                                                ImageView img = findViewById(R.id.img_sanpham);
                                                name.setText(obj.getString("productId"));
                                                detail.setText(obj.getString("CPU"));
                                                Picasso.get().load(obj.getString("img")).into(img);
                                                price.setText(obj.getString("outPrice"));
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
                            Log.v("pro", String.valueOf(obj));
                        }
                    }
                });
            }
        };
        msocket.on("enable-buy-client", onclose);

        Emitter.Listener getQuality = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int obj = (int) args[0];
                        Log.v("pro", String.valueOf(obj));
                    }
                });
            }
        };
        msocket.on("product-number", getQuality);
    }


}