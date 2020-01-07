package com.example.ocr_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class OCRActivity extends AppCompatActivity {
    private EditText result;
    private Toolbar tbOCR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);

        tbOCR = findViewById(R.id.toolbarOCR);
        tbOCR.setTitle("Result");
        setSupportActionBar(tbOCR);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        result = findViewById(R.id.tv);
        Intent i = getIntent();
        result.setText(i.getStringExtra("text"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
