package com.example.reaver.rsfilemanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class FileListActivity extends Activity  implements CreateFileDialogFragment.CreateDialogListener
        , DeleteDialogFragment.DeleteDialogListener {
    private static final int CREATE_FILE_MENU_ID = 1;
    private static final int CREATE_DIR_MENU_ID = 2;
    private static final int DELETE_MENU_ID = 3;
    public static final String CREATE_FILE_TAG = "CreateFile";
    public static final String CREATE_DIR_TAG = "CreateDir";
    public static final String DELETE_TAG = "DeleteTag";
    public static final String LOG_TAG = "My";

    private FileListFragment fileListFragment;
    private CreateFileDialogFragment createFileDialogFragment;
    private DeleteDialogFragment deleteDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_list_activity);

        fileListFragment = (FileListFragment) getFragmentManager().findFragmentById(R.id.file_list_fragment);
        createFileDialogFragment = new CreateFileDialogFragment();
        deleteDialogFragment = new DeleteDialogFragment();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.file_list, menu);
        menu.add(0, CREATE_FILE_MENU_ID, 0, "Create file");
        menu.add(0, CREATE_DIR_MENU_ID, 0, "Create directory");
        menu.add(0, DELETE_MENU_ID, 0, "Delete selected");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if ( id == CREATE_FILE_MENU_ID ) {
            createFileDialogFragment.show(getFragmentManager(), CREATE_FILE_TAG);
        } else if ( id == CREATE_DIR_MENU_ID ) {
            createFileDialogFragment.show(getFragmentManager(), CREATE_DIR_TAG);
        } else if (  id == DELETE_MENU_ID ) {
            deleteDialogFragment.show(getFragmentManager(), DELETE_TAG);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogNewFileResult(String newName, String tag) {
        if ( tag.equals(CREATE_FILE_TAG) ) {
            fileListFragment.createFile(newName);
        } else if ( tag.equals(CREATE_DIR_TAG ) ) {
            fileListFragment.createDir(newName);
        }
    }

    @Override
    public void onDeleteDialogResult() {
        fileListFragment.deleteSelected();
    }
}
