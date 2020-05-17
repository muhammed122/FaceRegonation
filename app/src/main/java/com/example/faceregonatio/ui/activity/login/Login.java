package com.example.faceregonatio.ui.activity.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import com.example.faceregonatio.R;
import com.example.faceregonatio.databinding.ActivityLoginBinding;
import com.example.faceregonatio.ui.activity.MainActivity;
import com.example.faceregonatio.ui.activity.signup.SignUp;


public class Login extends AppCompatActivity {



    ActivityLoginBinding binding;
    LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setLifecycleOwner(this);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        binding.setViewModel(loginViewModel);

        loginViewModel.message.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(Login.this, ""+s, Toast.LENGTH_SHORT).show();
            }
        });

        loginViewModel.intent.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean intent) {
                if (intent){
                    startActivity(new Intent(Login.this,MainActivity.class));
                    finish();
                }
            }
        });
        loginViewModel.signUpIntent.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean intent) {
                if (intent){
                    startActivity(new Intent(Login.this, SignUp.class));
                    finish();
                }
            }
        });



    }
}
