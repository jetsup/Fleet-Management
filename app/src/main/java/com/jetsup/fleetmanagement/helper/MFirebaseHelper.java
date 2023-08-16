package com.jetsup.fleetmanagement.helper;

import static com.jetsup.fleetmanagement.util.GlobalsConstants.M_TAG;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

public class MFirebaseHelper {
    public MFirebaseHelper(FirebaseFirestore database) {
        Log.i(M_TAG, "Firebase Class invoked");
    }
}
