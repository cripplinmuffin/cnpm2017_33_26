package com.example.ocr_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class OCRActivity extends AppCompatActivity {
    EditText tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);
        tv=(EditText) findViewById(R.id.tv);
        Intent i=getIntent();
        tv.setText(i.getStringExtra("text"));
    }
}
