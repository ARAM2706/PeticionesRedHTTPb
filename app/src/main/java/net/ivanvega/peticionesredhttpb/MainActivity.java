package net.ivanvega.peticionesredhttpb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView txt;
    RequestQueue queue;
    ImageView imgview;
    ImageView imgview2;
    ImageView imgview3;
    ImageView imgview4;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = findViewById(R.id.txtString);
        imgview = findViewById(R.id.Spiderman);
        imgview2 = findViewById(R.id.IronMan);
        imgview3 = findViewById(R.id.Thor);
        imgview4 = findViewById(R.id.Wolverine);

        queue = Volley.newRequestQueue(this);

        findViewById(R.id.btnStringReq).setOnClickListener(
                v -> stringRequest()
        );

        findViewById(R.id.btnJsonReq).setOnClickListener(
                v -> jsonRequest()
        );

        findViewById(R.id.btnImageReq).setOnClickListener(
                v -> imageRequestMethd()
        );
    }

    private void imageRequestMethd() {
        MiHeroes miheroes = new MiHeroes();
        ImageRequest spiderman = new ImageRequest(
                "https://simplifiedcoding.net/demos/view-flipper/images/spiderman.jpg",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {

                        imgview.setImageBitmap(response);
                    }
                },
                300, 300,
                ImageView.ScaleType.CENTER,
                Bitmap.Config.ALPHA_8,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

        );
        ImageRequest ironman = new ImageRequest(
                "https://simplifiedcoding.net/demos/view-flipper/images/ironman.jpg",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {

                        imgview2.setImageBitmap(response);
                    }
                },
                300, 300,
                ImageView.ScaleType.CENTER,
                Bitmap.Config.ALPHA_8,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

        );
        ImageRequest thor = new ImageRequest(
                "https://simplifiedcoding.net/demos/view-flipper/images/wolverine.jpeg",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {

                        imgview4.setImageBitmap(response);
                    }
                },
                300, 300,
                ImageView.ScaleType.CENTER,
                Bitmap.Config.ALPHA_8,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

        );
        ImageRequest wolverine = new ImageRequest(
                "https://simplifiedcoding.net/demos/view-flipper/images/thor.jpg",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {

                        imgview3.setImageBitmap(response);
                    }
                },
                300, 300,
                ImageView.ScaleType.CENTER,
                Bitmap.Config.ALPHA_8,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

        );

        MySingleton.getInstance(this).
                addToRequestQueue(spiderman);
        MySingleton.getInstance(this).
                addToRequestQueue(ironman);
        MySingleton.getInstance(this).
                addToRequestQueue(thor);
        MySingleton.getInstance(this).
                addToRequestQueue(wolverine);

    }

    private void jsonRequest() {
        JsonRequest jsonRequest = new JsonArrayRequest(
                Request.Method.GET,
                "https://simplifiedcoding.net/demos/view-flipper/heroes.php",
                null,
                response -> {

                    for(int i=0; i<response.length(); i++){
                        try {
                            JSONObject   jsonObject = response.getJSONObject(i);
                            String album ="name: "+jsonObject.getString("name")+ "imageurl: "+jsonObject.getString("imageurl");
                            Log.d("JSONRequestGIVO", album );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Gson gson = new Gson();

                    Type lsMiherosType = new TypeToken<List<MiHeroes>>(){}.getType();

                    List<MiHeroes> lsMiHeros =
                            gson.fromJson(response.toString(), lsMiherosType);

                    for(MiHeroes item: lsMiHeros){
                        String album = "";
                        album += "name: "+item.getName()+"imageurl: "+item.getImageUrl();
                        ImageRequest imageRequest = new ImageRequest(
                                item.getImageUrl(),
                                new Response.Listener<Bitmap>() {
                                    @Override
                                    public void onResponse(Bitmap response) {

                                        imgview.setImageBitmap(response);
                                    }
                                },
                                300, 300,
                                ImageView.ScaleType.CENTER,
                                Bitmap.Config.ALPHA_8,
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }

                        );
                    }



                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }


        );

        queue.add(jsonRequest);
    }

    private void stringRequest() {
        // Instantiate the RequestQueue

        String url ="http://www.google.com";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                txt.setText("Response is: "+ response.substring(0,500));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txt.setText("That didn't work!" +  error.toString());
            }
        }
        );
        // Add the request to the RequestQueue
        queue.add(stringRequest);
    }
}