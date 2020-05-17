package com.example.faceregonatio.data.database;



import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseHelper {

    //object authenticate
    private static FirebaseAuth auth;
    public static FirebaseAuth getAuth(){
        if (auth==null){
            auth=FirebaseAuth.getInstance();
        }
        return auth;
    }


    // object database references
    private static DatabaseReference databaseReference;
    public static DatabaseReference getFirebaseDatabase(){
        if (databaseReference==null){ databaseReference=FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;
    }







}
