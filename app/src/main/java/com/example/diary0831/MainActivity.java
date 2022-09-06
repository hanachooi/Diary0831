package com.example.diary0831;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnDiary = (Button) findViewById(R.id.btnDiary);

        btnDiary.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(View view) {
                Intent intent = new Intent().setClass(getApplicationContext(), WriteDiaryActivity.class);
                startActivity(intent);
            }
        });
    }
}