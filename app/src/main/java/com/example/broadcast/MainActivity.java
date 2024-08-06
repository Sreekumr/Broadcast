package com.example.broadcast;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.view.View;
import android.content.IntentFilter;

public class MainActivity extends AppCompatActivity {
    MyReceiver receiver;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        configureReceiver();
    }

    public void broadCast(View v) {
        Intent intent = new Intent();
        intent.setAction("com.example.broadcast");
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void configureReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.broadcast");
        receiver = new MyReceiver();
        registerReceiver(receiver, filter);

    }
    protected  void onDestroy(){
        super.onDestroy();
        unregisterReceiver(receiver);
    }

}