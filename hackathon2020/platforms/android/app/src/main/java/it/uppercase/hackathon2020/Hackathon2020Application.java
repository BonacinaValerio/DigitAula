package it.uppercase.hackathon2020;

import android.app.Application;

import com.google.firebase.FirebaseApp;

import it.uppercase.hackathon2020.common.dependencyinjection.ApplicationCompositionRoot;

public class Hackathon2020Application extends Application {

    private ApplicationCompositionRoot mApplicationCompositionRoot;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        mApplicationCompositionRoot = new ApplicationCompositionRoot();
    }

    public ApplicationCompositionRoot getCompositionRoot() {
        return mApplicationCompositionRoot;
    }

}
