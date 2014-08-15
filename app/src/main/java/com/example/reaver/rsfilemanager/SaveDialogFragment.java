package com.example.reaver.rsfilemanager;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Reaver on 14.08.2014.
 */
public class SaveDialogFragment extends DialogFragment implements View.OnClickListener {
    public interface SaveDialogListener {
        public void onSave();
        public void onDontSave();
    }

    SaveDialogListener saveDialogListener;

    @Override
    public void onAttach(Activity activity) {
        try {
            saveDialogListener = (SaveDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString().concat(" must implement saveDialogListener"));
        }
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.save_dialog_fragment, container, false);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        Button btnDontSave = (Button) view.findViewById(R.id.btnDontSave);
        Button btnSave = (Button) view.findViewById(R.id.btnSave);

        btnCancel.setOnClickListener(this);
        btnDontSave.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        getDialog().setTitle("Save changes?");

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                dismiss();
                break;
            case R.id.btnDontSave:
                saveDialogListener.onDontSave();
                dismiss();
                break;
            case R.id.btnSave:
                saveDialogListener.onSave();
                dismiss();
                break;
        }
    }
}
