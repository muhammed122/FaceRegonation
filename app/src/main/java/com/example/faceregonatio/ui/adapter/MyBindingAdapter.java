package com.example.faceregonatio.ui.adapter;

import androidx.databinding.BindingAdapter;

import com.google.android.material.textfield.TextInputEditText;

public class MyBindingAdapter {

    @BindingAdapter("error")
    public static void setErrorOnInputLayout(TextInputEditText tx, String message){
        if (message==null||message.isEmpty()){
            tx.setError(null);
        }
        else
            tx.setError(message);
    }
}
