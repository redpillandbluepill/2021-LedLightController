package com.example.led_control_client;

import androidx.appcompat.app.AppCompatActivity;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private Handler mHandler;

    // HttpURLConnection 참조 변수.
    URL url;
    HttpURLConnection con;
    ToggleButton toggle_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();
        toggle_btn = (ToggleButton) findViewById(R.id.toggle_btn);


        toggle_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            if(toggle_btn.isChecked()) {
                                mHandler.post(new Change_btn(true));
                                url = new URL("http://000.000.000.000/gpio0/1");
                                con = (HttpURLConnection) url.openConnection();
                                Log.d("LOG", "응답코드 : " + con.getResponseCode());
                                Log.d("LOG", "응답메세지 : " + con.getResponseMessage());

                            }
                            else{
                                mHandler.post(new Change_btn(false));
                                url = new URL("http://000.000.000.000/gpio0/0");
                                con = (HttpURLConnection) url.openConnection();
                                Log.d("LOG", "응답코드 : " + con.getResponseCode());
                                Log.d("LOG", "응답메세지 : " + con.getResponseMessage());

                            }
                            //Toast.makeText(MainActivity.this, send_message, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("오류", "데이터 송신 오류");
                        }
                    }
                }.start();
            }
        });
    }

    class Change_btn implements Runnable{
        private boolean check;
        public Change_btn(boolean check) {this.check=check;}

        @Override
        public void run() {
            if(check) {
                toggle_btn.setChecked(true);
                toggle_btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.sun_on));
            }
            else{
                toggle_btn.setChecked(false);
                toggle_btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.sun_off));
            }
        }
    }

    //하단 내비게이션바 투명하게
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            applyColors();
        }
    }
    // Apply the title/navigation bar color
    private void applyColors() {
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
    }

}