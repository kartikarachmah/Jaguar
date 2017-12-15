package jp.tubes.pbp.jaguar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import jp.tubes.pbp.jaguar.Anak.Login_Anak;
import jp.tubes.pbp.jaguar.OrangTua.Activity.LoginOrtuActivity;
import jp.albasyir.jundana.familytracer.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginanak = findViewById(R.id.loginanak);
        Button loginortu = findViewById(R.id.loginortu);

        loginanak.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent loginanak = new Intent(MainActivity.this, Login_Anak.class);
                startActivity(loginanak);
            }
        });

        loginortu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent loginanak = new Intent(MainActivity.this, LoginOrtuActivity.class);
                startActivity(loginanak);
            }
        });
    }
}
