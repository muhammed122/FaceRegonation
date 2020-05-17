package com.example.faceregonatio.ui.activity.signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Toast;

import com.example.faceregonatio.R;
import com.example.faceregonatio.databinding.ActivitySignUpBinding;
import com.example.faceregonatio.ui.activity.login.Login;


public class SignUp extends AppCompatActivity {

    SignUpViewModel viewModel;
    ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);

        viewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);

        viewModel.message.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(SignUp.this, "" + s, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.intent.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean intent) {
                if (intent) {
                    startActivity(new Intent(SignUp.this, Login.class));
                    finish();
                }

            }
        });



    }
}
