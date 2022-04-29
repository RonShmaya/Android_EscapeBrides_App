package com.example.escapebrides.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.escapebrides.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Activity_main extends AppCompatActivity {
    public static final String EXTRA_USER_NAME = "EXTRA_USER_NAME";

    private TextInputEditText main_EDT_user_name;
    private MaterialButton main_BTN_sign_in;
    private TextInputLayout main_LAY_user_name;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        initViews();
        }

    private void initViews() {
        bundle = new Bundle();
        main_BTN_sign_in.setOnClickListener(view -> sign_in());

    }

    private void findViews() {
        main_EDT_user_name = findViewById(R.id.main_EDT_user_name);
        main_BTN_sign_in = findViewById(R.id.main_BTN_sign_in);
        main_LAY_user_name = findViewById(R.id.main_LAY_user_name);
    }

    private void sign_in() {
        main_LAY_user_name.setError(null);
        String regex = "^[A-Za-z]\\w{5,29}$";
        Pattern p = Pattern.compile(regex);
        String userName = main_EDT_user_name.getText().toString();
        Matcher m = p.matcher(userName);
        if(m.matches()){
            login(userName);
        }
        else{
            raise_input_error();
        }

    }

    private void raise_input_error() {
        main_LAY_user_name.setError("user name input error");
    }

    private void login(String userName) {
        Intent intent = new Intent(this, Activity_Menu.class);
        bundle.putString(EXTRA_USER_NAME, userName); // new type
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
