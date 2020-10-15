package it.uppercase.hackathon2020.screens.common;


import androidx.fragment.app.Fragment;
import it.uppercase.hackathon2020.common.dependencyinjection.ActivityCompositionRoot;
import it.uppercase.hackathon2020.screens.main.MainActivity;

public abstract class BaseFragmentMain extends Fragment {
    protected ActivityCompositionRoot getCompositionRoot() {
        return ((MainActivity)requireActivity()).getCompositionRoot();
    }

}
