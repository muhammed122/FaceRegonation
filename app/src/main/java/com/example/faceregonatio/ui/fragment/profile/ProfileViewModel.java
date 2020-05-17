package com.example.faceregonatio.ui.fragment.profile;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.faceregonatio.data.model.UserModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileViewModel extends AndroidViewModel{

   public ObservableField<String> userName = new ObservableField<>("");
   public ObservableField<String> userEmail = new ObservableField<>("");
   public ObservableField<String> userPhone = new ObservableField<>("");

   public MutableLiveData<Boolean>logout=new MutableLiveData<>();


    private static final String TAG = "ProfileViewModel";

    public ProfileViewModel(@NonNull Application application) {
        super(application);
    }


    public void getUserProfile(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        String userId=auth.getUid();
        Log.d(TAG, "dola getUserProfile: "+userId);
        if (userId!=null)
        reference.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel user = dataSnapshot.getValue(UserModel.class);
                if (user!=null){
                    userName.set(user.getUserName());
                    userEmail.set(user.getUserEmail());
                    userPhone.set(user.getUserPhone());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


    public void logOut(){
        SharedPreferences preferences =getApplication().getSharedPreferences("local_file", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =preferences.edit();
        editor.putString("userId",null);
        editor.putBoolean("login",false);
        editor.apply();

        logout.setValue(true);


    }
}
