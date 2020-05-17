package com.example.faceregonatio.ui.activity.signup;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.faceregonatio.data.database.DatabaseHelper;
import com.example.faceregonatio.data.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.Objects;

public class SignUpViewModel extends ViewModel {


    //----------------------------------------------------------------------------------
    public ObservableField<String> username = new ObservableField<>("");
    public ObservableField<String> email = new ObservableField<>("");
    public ObservableField<String> phone = new ObservableField<>("");
    public ObservableField<String> password = new ObservableField<>("");
    public ObservableField<Boolean> Visibility = new ObservableField<>(true);
    //---------------------------------------------------------------------------------
    public ObservableField<String> usernameError = new ObservableField<>();
    public ObservableField<String> passwordError = new ObservableField<>();
    public ObservableField<String> phoneError = new ObservableField<>();
    public ObservableField<String> emailError = new ObservableField<>();

    protected MutableLiveData<String> message = new MutableLiveData<>();
    public MutableLiveData<Boolean> intent = new MutableLiveData<>(false);

    public void setSignInIntent(){
        intent.setValue(true);
    }
    private boolean isValidData() {

        boolean isValid = true;
        if (username.get().trim().isEmpty()) {
            usernameError.set("required");
            isValid = false;
        } else {
            usernameError.set(null);
            isValid = true;
        }
        if (email.get().trim().isEmpty()) {
            emailError.set("required");
            isValid = false;
        } else {
            emailError.set(null);
            isValid = true;
        }
        if (password.get().trim().isEmpty()) {
            this.passwordError.set("required");
            isValid = false;
        } else if (password.get().length() < 6) {
            this.passwordError.set("password should be > 6 chars");
            isValid = false;
        } else {
            this.passwordError.set(null);
            isValid = true;
        }
        if (phone.get().trim().isEmpty()) {
            this.phoneError.set("required");
            isValid = false;

        } else {
            this.phoneError.set(null);
            isValid = true;
        }
        return isValid;

    }

    public void signUp() {
        if (isValidData()) {
            Visibility.set(false);
            DatabaseHelper.getAuth().
                    createUserWithEmailAndPassword(email.get().trim(), password.get().trim()).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful() && task.getResult()!=null ) {

                               String uid= Objects.requireNonNull(task.getResult().getUser()).getUid();
                                uploadUserData(uid);
                                message.setValue("success");
                                intent.setValue(true);

                            } else {

                                Visibility.set(true);
                                message.setValue("Authentication failed.  " +
                                        task.getException().getLocalizedMessage());
                            }
                        }
                    });

        }
    }

    private void uploadUserData(String uid) {
     //   String userId = DatabaseHelper.getFirebaseDatabase().child("users").push().getKey();
        UserModel userModel = new UserModel(uid, username.get(), phone.get(), email.get());
        assert uid != null;
        DatabaseHelper.getFirebaseDatabase().child("users").child(uid).setValue(userModel);
    }
}
