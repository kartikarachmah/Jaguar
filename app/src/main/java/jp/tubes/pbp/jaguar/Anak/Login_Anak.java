package jp.tubes.pbp.jaguar.Anak;

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

import static jp.tubes.pbp.jaguar.UrlLink.url_login;

public class Login_Anak extends AppCompatActivity {
    EditText Username, Password;
    Button Login;
    RequestQueue requestQueue;
    String UsernameHolder, PasswordHolder;
    ProgressDialog progressDialog;
    Boolean PeriksaEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anak_activity_login_anak);

        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);

        Login = findViewById(R.id.LoginButton);

        requestQueue = Volley.newRequestQueue(Login_Anak.this);
        progressDialog = new ProgressDialog(Login_Anak.this);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PeriksaEditTextKosongAtauTidak();
                if (PeriksaEditText) {
                    userLogin();
                } else {
                    Toast.makeText(Login_Anak.this, "Mohon Masukkan Username dan Password Anda", Toast.LENGTH_SHORT).show();
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(ServerResponse);
                            if (jsonObject.getBoolean("status-login")){
                                //Session Login
                                SharedPreferences sharedPreferences = getSharedPreferences("familytracer_user", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("id_user", jsonObject.getString("id_user"));
                                editor.apply();
                                //Session Login

                                Toast.makeText(Login_Anak.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                                finish();

                                Intent intent = new Intent(Login_Anak.this, Profile_Anak.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(Login_Anak.this, jsonObject.getString("pesan-error"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                Toast.makeText(Login_Anak.this, volleyError.toString(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(Login_Anak.this);
        requestQueue.add(stringRequest);
    }
}