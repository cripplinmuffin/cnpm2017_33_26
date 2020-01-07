package com.example.ocr_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btSignIn;
    private TextView tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btSignIn = findViewById(R.id.btSignIn);
        tvSignUp = findViewById(R.id.tvAskSignUp);

        Global.loadData(SignInActivity.this);

        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean cancel = false, flag = false;

                if (TextUtils.isEmpty(etUsername.getText())) {
                    etUsername.setError(getString(R.string.username_error_1));
                    cancel = true;
                }
                if (TextUtils.isEmpty(etPassword.getText())) {
                    etPassword.setError(getString(R.string.password_error_1));
                    cancel = true;
                }

                if (!cancel) {
                    String usrnm = etUsername.getText().toString(), psswrd = etPassword.getText().toString();

                    for(Account acc : Global.users) {
                        if (TextUtils.equals(acc.getUsername(), usrnm) && TextUtils.equals(acc.getPassword(), psswrd)) {
                            flag = true;
                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            Toast.makeText(SignInActivity.this, "Signed in!", Toast.LENGTH_LONG).show();
                            startActivity(intent);
                        }
                    }
                    if(!flag) {
                        etUsername.setError(getString(R.string.sign_in_error_1));
                        etPassword.setError(getString(R.string.sign_in_error_1));
                    }
                }
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
