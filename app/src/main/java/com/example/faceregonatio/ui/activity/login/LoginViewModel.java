package com.example.faceregonatio.ui.activity.login;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.faceregonatio.data.database.DatabaseHelper;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LoginViewModel extends AndroidViewModel {

    public ObservableField<String> email = new ObservableField<>("");
    public ObservableField<String> password = new ObservableField<>("");
    public ObservableField<Boolean> visibility = new ObservableField<>();
    public ObservableField<Boolean>checked=new ObservableField<>(false);


    MutableLiveData<String> message = new MutableLiveData<>();
    MutableLiveData<Boolean> intent = new MutableLiveData<>(false);
    MutableLiveData<Boolean>signUpIntent=new MutableLiveData<>(false);

    public LoginViewModel(@NonNull Application application) {
        super(application);
        checkLogin();
    }

    public void setSignUpIntent(){
        signUpIntent.setValue(true);
    }
    public void signIn() {
        String mail = email.get().trim();
        String pass = password.get().trim();
        if (mail.equals("") || pass.equals(""))
            message.setValue("Please check your data");
        else {
            visibility.set(true);
            DatabaseHelper.getAuth().signInWithEmailAndPassword(mail, pass).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                if (checked.get())
                                    saveLogin(task.getResult().getUser().getUid());
                                intent.setValue(true);
                                message.setValue("successfully login");

                            } else {
                                message.setValue("Authentication failed. " +
                                        task.getException().getLocalizedMessage());
                                visibility.set(false);
                            }
                        }
                    });
        }
    }
    private void saveLogin(String userId){
            SharedPreferences preferences =getApplication().getSharedPreferences("local_file", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor =preferences.edit();
            editor.putString("userId",userId);
            editor.putBoolean("login",true);
            editor.apply();
    }
    private void checkLogin(){
        SharedPreferences preferences =getApplication().getSharedPreferences("local_file", Context.MODE_PRIVATE);
       boolean flag= preferences.getBoolean("login",false);
       if (flag){
           String userId=preferences.getString("userId","");
           if (userId.equals(""))
           {
               message.setValue("Error data");
           }
           else{

               intent.setValue(true);
           }
       }
    }


}
