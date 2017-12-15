package jp.tubes.pbp.jaguar.Anak;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import jp.albasyir.jundana.familytracer.R;
import jp.tubes.pbp.jaguar.UrlLink;

public class Profile_Anak extends AppCompatActivity {

    Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anak_activity_profile);

        final TextView namauser = findViewById(R.id.id_user1);
        final TextView latitude = findViewById(R.id.latitude1);
        final TextView longitude = findViewById(R.id.longitude1);

        final Button logout = findViewById(R.id.logout);
        final Button simpan = findViewById(R.id.simpan);


        SharedPreferences sharedPreferences = getSharedPreferences("familytracer_user", MODE_PRIVATE);
        final String id_user = sharedPreferences.getString("id_user", null);

        String url_profile = UrlLink.url_profile_anak + "?id_user=" + id_user;

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url_profile,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        try {
                            JSONObject jsonObject = new JSONObject(ServerResponse);
                            if (jsonObject.getBoolean("status-get")) {
                                namauser.setText(jsonObject.getString("id_user"));
                                latitude.setText(jsonObject.getString("lat"));
                                longitude.setText(jsonObject.getString("lng"));
                            } else {
                                Toast.makeText(Profile_Anak.this, jsonObject.getString("pesan-error"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(Profile_Anak.this, volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        final RequestQueue requestQueue = Volley.newRequestQueue(Profile_Anak.this);
        requestQueue.add(stringRequest);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("familytracer_user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                finish();
            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String url = UrlLink.url_update_profile + "?id_user=" + id_user + "&lat=" + latitude.getText() + "&lng" + longitude.getText();
                StringRequest stringRequestUpdate = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String ServerResponse) {
                                try {
                                    JSONObject jsonObject = new JSONObject(ServerResponse);
                                    if (jsonObject.getBoolean("status-get")) {
                                        Toast.makeText(Profile_Anak.this, "Berhasil Menyimpan", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Profile_Anak.this, jsonObject.getString("pesar-error"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(Profile_Anak.this, volleyError.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue.add(stringRequestUpdate);
            }
        });

    }
}