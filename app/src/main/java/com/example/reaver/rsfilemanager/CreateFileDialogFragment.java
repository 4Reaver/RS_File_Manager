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
public class CreateFileDialogFragment extends DialogFragment implements View.OnClickListener {
    public interface CreateDialogListener {
        public void onDialogNewFileResult(String newName, String tag);
    }

    CreateDialogListener activity;
    EditText etNewFileName;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.activity = (CreateDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString().concat(" must implement DialogListener"));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_file_dialog_fragment, container, false);
        Button btnOK = (Button) view.findViewById(R.id.btnOK);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);

        etNewFileName = (EditText) view.findViewById(R.id.etNewFileName);

        btnCancel.setOnClickListener(this);
        btnOK.setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        if ( getTag().equals(FileListActivity.CREATE_FILE_TAG) ) {
            etNewFileName.setHint("Enter name of new file");
            getDialog().setTitle("Create file");
        } else if ( getTag().equals(FileListActivity.CREATE_DIR_TAG) ) {
            etNewFileName.setHint("Enter name of new dir");
            getDialog().setTitle("Create dir");
        }

        super.onStart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOK:
                String newName = etNewFileName.getText().toString();
                activity.onDialogNewFileResult(newName, getTag());

                etNewFileName.setText("");
                dismiss();
                break;
            case R.id.btnCancel:
                dismiss();
                break;
        }
    }
}
