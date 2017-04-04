package com.example.chen.exp7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Chen on 2016/11/14.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static int MODE = MODE_PRIVATE;
    public static final String PREFERENCE_NAME = "SavaPassword";
    private boolean isFirst;
    SharedPreferences sharedPreferences;

    Button OkButton, ClearButton;
    EditText ConfirmPassword, Password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE);
        FindViews();
        CheckIsFirst();
        AddClickListeners();
    }

    private void FindViews() {
        OkButton = (Button) findViewById(R.id.Ok);
        ClearButton = (Button) findViewById(R.id.Clear);
        ConfirmPassword = (EditText) findViewById(R.id.ConfirmPassword);
        Password = (EditText) findViewById(R.id.Password);
    }

    private void CheckIsFirst() {
        if (sharedPreferences.contains("password")) {
            ConfirmPassword.setVisibility(View.INVISIBLE);
            Password.setHint("Password");
            isFirst = false;
        } else {
            ConfirmPassword.setVisibility(View.VISIBLE);
            Password.setHint("New Password");
            isFirst = true;
        }
    }

    private void AddClickListeners() {
        OkButton.setOnClickListener(this);
        ClearButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Ok:
                if (isFirst)
                    CheckConfirmPassword();
                else
                    CheckPassword();
                break;
            case R.id.Clear:
                Password.setText("");
                ConfirmPassword.setText("");
                break;
        }
    }

    private void CheckConfirmPassword() {
        String hint;
        String password = Password.getText().toString();
        if (password.equals("")) {
            Toast.makeText(this, "Password cannont be empty", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(ConfirmPassword.getText().toString())) {
            Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("password", password);
            editor.commit();
            Intent intent = new Intent(this, FileEditorActivity.class);
            startActivity(intent);
        }
    }

    private void CheckPassword() {
        String password = sharedPreferences.getString("password", "");
        if (password != "") {
            if (!Password.getText().toString().equals(password)) {
                Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, FileEditorActivity.class);
                startActivity(intent);
            }
        } else {
                Toast.makeText(this, "Password Missing", Toast.LENGTH_SHORT).show();
        }
    }
}
