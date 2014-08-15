package com.example.reaver.rsfilemanager;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Reaver on 11.08.2014.
 */
public class DeleteDialogFragment extends DialogFragment implements View.OnClickListener {
    public interface DeleteDialogListener {
        public void onDeleteDialogResult();
    }

    DeleteDialogListener activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.activity = (DeleteDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString().concat(" must implement DialogListener"));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.delete_dialog_fragment, container, false);
        Button btnOK = (Button) view.findViewById(R.id.btnOK);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(this);
        btnOK.setOnClickListener(this);

        getDialog().setTitle("Delete selected?");

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOK:
                activity.onDeleteDialogResult();

                dismiss();
                break;
            case R.id.btnCancel:
                dismiss();
                break;
        }
    }
}
