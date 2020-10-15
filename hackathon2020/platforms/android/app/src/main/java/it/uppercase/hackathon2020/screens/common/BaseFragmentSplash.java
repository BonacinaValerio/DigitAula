package it.uppercase.hackathon2020.screens.common;


import androidx.fragment.app.Fragment;
import it.uppercase.hackathon2020.common.dependencyinjection.ActivityCompositionRoot;
import it.uppercase.hackathon2020.screens.main.SplashActivity;

public abstract class BaseFragmentSplash extends Fragment {

    protected ActivityCompositionRoot getCompositionRoot() {
        return ((SplashActivity)requireActivity()).getCompositionRoot();
    }

}
