package com.veames.messenger;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPasswordViewModel extends ViewModel {

    private static final String LOG_TAG = "ResetPasswordViewModel";

    private FirebaseAuth auth;

    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<Boolean> success = new MutableLiveData<>();

    public ResetPasswordViewModel() {
        auth = FirebaseAuth.getInstance();
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<Boolean> isSuccess() {
        return success;
    }

    public void resetPassword(String email) {
        auth.sendPasswordResetEmail(email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(LOG_TAG, "Reset link has been successfully sen");
                        success.setValue(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(LOG_TAG, e.toString());
                        error.setValue(e.getMessage());
                    }
                });
    }
}