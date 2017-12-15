package jp.tubes.pbp.jaguar.OrangTua.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import jp.albasyir.jundana.familytracer.R;

import static jp.tubes.pbp.jaguar.UrlLink.url_loginOrtu;

public class LoginOrtuActivity extends AppCompatActivity {
    EditText Username, Password;
    Button Login;
    RequestQueue requestQueue;
    String UsernameHolder, PasswordHolder;
    ProgressDialog progressDialog;
    Boolean PeriksaEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ortu);

        Username = findViewById(R.id.usernameOrtu);
        Password = findViewById(R.id.passwordOrtu);

        Login = findViewById(R.id.LoginOrtu);

        requestQueue = Volley.newRequestQueue(LoginOrtuActivity.this);
        progressDialog = new ProgressDialog(LoginOrtuActivity.this);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PeriksaEditTextKosongAtauTidak();
                if (PeriksaEditText) {
                    userLogin();
                } else {
                    Toast.makeText(LoginOrtuActivity.this, "Mohon Masukkan Username dan Password Anda", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void PeriksaEditTextKosongAtauTidak() {

        UsernameHolder = Username.getText().toString().trim();
        PasswordHolder = Password.getText().toString().trim();

        PeriksaEditText = !(TextUtils.isEmpty(UsernameHolder) || TextUtils.isEmpty(PasswordHolder));
    }

    public void userLogin() {
        progressDialog.setMessage("Mohon Tunggu");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_loginOrtu,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(ServerResponse);
                            if (jsonObject.getBoolean("status-login")){
                                //Session Login
                                SharedPreferences sharedPreferences = getSharedPreferences("familytracer_ortu", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("id_ortu", jsonObject.getString("id_ortu"));
                                editor.apply();
                                //Session Login

                                Toast.makeText(LoginOrtuActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                                finish();

                                Intent intent = new Intent(LoginOrtuActivity.this, MainOrangtua.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(LoginOrtuActivity.this, jsonObject.getString("pesan-error"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                Toast.makeText(LoginOrtuActivity.this, volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", UsernameHolder);
                params.put("password", PasswordHolder);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(LoginOrtuActivity.this);
        requestQueue.add(stringRequest);
    }
}