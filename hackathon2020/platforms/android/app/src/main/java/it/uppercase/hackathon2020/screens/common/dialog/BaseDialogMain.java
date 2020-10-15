package it.uppercase.hackathon2020.screens.common.dialog;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import it.uppercase.hackathon2020.common.dependencyinjection.ActivityCompositionRoot;
import it.uppercase.hackathon2020.screens.main.MainActivity;

import static it.uppercase.hackathon2020.screens.common.dialog.DialogsManager.ARGUMENT_KEY_TAG;


/**
 * Base class for all dialogs
 */
public abstract class BaseDialogMain extends DialogFragment {

    /**
     * Return this dialog's custom tag. Please note that this tag is different
     * bfrom {@link Fragment#getTag()}
     * @return dialog's custom tag, or null if none was set
     */
    protected String getDialogTag() {
        if (getArguments() == null) {
            return null;
        } else {
            return getArguments().getString(ARGUMENT_KEY_TAG);
        }
    }

    protected ActivityCompositionRoot getCompositionRoot() {
        return ((MainActivity)requireActivity()).getCompositionRoot();
    }
}
