package com.example.reaver.rsfilemanager;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import java.io.File;

/**
 * Created by Reaver on 14.08.2014.
 */
public class EditFileActivity extends Activity implements SaveDialogFragment.SaveDialogListener{
    private SaveDialogFragment saveDialogFragment;
    private EditText etFileContest;
    private File file;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String path = getIntent().getExtras().getString("path");
        file = new File(path);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_file_activity);

        fragment = getFragmentManager().findFragmentById(R.id.edit_file_fragment);
        ((EditFileFragment) fragment).updateContent(file);
        etFileContest = (EditText) fragment.getView().findViewById(R.id.etFileContents);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        saveDialogFragment = new SaveDialogFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        saveDialogFragment.show(getFragmentManager(), "Save changes");

        return true;
    }

    @Override
    public void onSave() {
        String content = etFileContest.getText().toString();
        //Intent intent = new Intent();
        ((EditFileFragment) fragment).saveFile(file, content);
        //intent.putExtra("newContent", content);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onDontSave() {
        setResult(RESULT_CANCELED);
        finish();
    }
}
