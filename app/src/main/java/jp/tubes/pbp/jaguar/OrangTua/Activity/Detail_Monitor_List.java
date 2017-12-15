package jp.tubes.pbp.jaguar.OrangTua.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import jp.albasyir.jundana.familytracer.R;

public class Detail_Monitor_List extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orangtua_activity_detail_monitor_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Detail Anak");


    }
}