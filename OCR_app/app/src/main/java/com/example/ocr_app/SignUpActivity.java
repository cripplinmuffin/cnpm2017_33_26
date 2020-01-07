package com.example.ocr_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

public class SignUpActivity extends AppCompatActivity {
    private Toolbar tbSignUp;
    private EditText etUsername, etPassword, etConfPassword;
    private Button btSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tbSignUp = findViewById(R.id.toolbarSignUp);
        tbSignUp.setTitle("Sign Up");
        setSupportActionBar(tbSignUp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        etUsername = findViewById(R.id.etUserName_SignUp);
        etPassword = findViewById(R.id.etPassword_SignUp);
        etConfPassword = findViewById(R.id.etConfPassword_SignUp);
        btSignUp = findViewById(R.id.btSignUp);

        Global.loadData(SignUpActivity.this);

        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean cancel = false, flag = false;

                if(TextUtils.isEmpty(etUsername.getText())) {
                    etUsername.setError(getString(R.string.username_error_1));
                    cancel = true;
                }
                if (TextUtils.isEmpty(etPassword.getText())) {
                    etPassword.setError(getString(R.string.password_error_1));
                    cancel = true;
                }
                if (TextUtils.isEmpty(etConfPassword.getText())) {
                    etConfPassword.setError(getString(R.string.password_error_1));
                    cancel = true;
                }
                if(!TextUtils.isEmpty(etPassword.getText())
                        && !TextUtils.isEmpty(etConfPassword.getText())
                        && !TextUtils.equals(etPassword.getText(), etConfPassword.getText())) {
                    etPassword.setError(getString(R.string.password_error_2));
                    etConfPassword.setError(getString(R.string.password_error_2));
                    cancel = true;
                }

                if (!cancel)
                {
                    String username = etUsername.getText().toString(), password = etPassword.getText().toString();

                    for (Account acc : Global.users) {
                        if(TextUtils.equals(username, acc.getUsername())) {
                            etUsername.setError(getString(R.string.username_error_2));
                            flag = true;
                            break;
                        }
                    }

                    if(!flag) {
                        Global.users.add(new Account(username, password));
                        Global.saveData(SignUpActivity.this);
                        Toast.makeText(SignUpActivity.this, "Signed up!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
