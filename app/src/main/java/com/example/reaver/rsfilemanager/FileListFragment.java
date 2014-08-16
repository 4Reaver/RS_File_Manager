package com.example.reaver.rsfilemanager;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Reaver on 09.08.2014.
 */
public class FileListFragment extends Fragment implements AdapterView.OnItemClickListener, ActionBar.OnNavigationListener {
    private Context context;
    private File currentFolder;
    private FileListAdapter adapter;
    private ListView lvFileList;
    private View headerParentFolder;
    private TextView tvFolderName;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity.getApplicationContext();
        currentFolder = context.getFilesDir();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.file_list_fragment, container, false);
        File[] files = currentFolder.listFiles();
        ArrayList<CustomFile> fileList = CustomFile.convert(files);
        SpinnerAdapter spinnerAdapter;

        prepareHeader(inflater);
        adapter = new FileListAdapter(getActivity(), fileList);
        lvFileList = (ListView) v.findViewById(R.id.lvFileList);
        lvFileList.addHeaderView(headerParentFolder, null, true);
        lvFileList.setAdapter(adapter);
        lvFileList.setOnItemClickListener(this);

        tvFolderName = (TextView) v.findViewById(R.id.tvCurrentFolder);
        tvFolderName.setText(getFolderName());

        spinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.navigation_options, R.layout.spinner_item);
        getActivity().getActionBar().setListNavigationCallbacks(spinnerAdapter, this);

        return v;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    private void prepareHeader(LayoutInflater inflater) {
        headerParentFolder = inflater.inflate(R.layout.file_item, null);
        ((TextView) headerParentFolder.findViewById(R.id.item_text)).setText("..");
        ((ImageView) headerParentFolder.findViewById(R.id.item_icon)).setImageResource(R.drawable.folder_icon);
        headerParentFolder.findViewById(R.id.cbItem).setVisibility(View.GONE);
    }

    private String getFolderName() {
        String name = currentFolder.getAbsolutePath();

        if ( name.equals("") ) {
            return "/";
        }

        return "../" + name;
    }

    private boolean isTxtFile(File file) {
        String fileName = file.getName();
        int length = fileName.length();
        String extension = fileName.substring(fileName.lastIndexOf(".")+1, length);

        return extension.equalsIgnoreCase("txt");
    }

    private boolean isRoot(File file) {
        return file.getParentFile() == null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        File selectedFile;
        boolean isCurrentRoot = isRoot(currentFolder);

        if ( position == 0 && !isCurrentRoot ) {
            selectedFile = currentFolder.getParentFile();
        } else {
            selectedFile = (File) lvFileList.getAdapter().getItem(position);
        }
        if ( !selectedFile.canRead() ) {
            Toast.makeText(getActivity(), "Access denied", Toast.LENGTH_SHORT).show();
            return;
        }

        if ( selectedFile.isDirectory() ) {
            if ( isRoot(selectedFile) ) {
                lvFileList.removeHeaderView(headerParentFolder);
            } else if ( isCurrentRoot ) {
                lvFileList.addHeaderView(headerParentFolder);
            }

            currentFolder = selectedFile;
            tvFolderName.setText(getFolderName());
            adapter.setFiles(CustomFile.convert(currentFolder.listFiles()));
            adapter.notifyDataSetChanged();
        } else if ( isTxtFile(selectedFile) && selectedFile.canWrite() ) {
            ((FileListActivity) getActivity()).startEditingFile(selectedFile);
        }
    }

    public void createFile(String newName) {
        try {
            if ( new File(currentFolder, newName).createNewFile() ) {
                Toast.makeText(getActivity(), "Created file: " + newName, Toast.LENGTH_SHORT).show();
                adapter.setFiles(CustomFile.convert(currentFolder.listFiles()));
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getActivity(), "Cannot create file: " + newName, Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Cannot create file: " + newName, Toast.LENGTH_SHORT).show();
        }
    }

    public void createDir(String newName) {
        if ( new File(currentFolder, newName).mkdir() ) {
            Toast.makeText(getActivity(), "Created dir: " + newName, Toast.LENGTH_SHORT).show();
            adapter.setFiles(CustomFile.convert(currentFolder.listFiles()));
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getActivity(), "Cannot create dir: " + newName, Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteSelected() {
        ArrayList<CustomFile> files = adapter.getFiles();

        for ( int i = files.size() - 1; i >= 0; i-- ) {
            CustomFile file = files.get(i);

            if ( file.isChecked() && file.canWrite() ) {
                boolean del = file.delete();

                if ( del ) {
                    files.remove(i);
                } else {
                    Toast.makeText(getActivity(), file.getName() + " must be empty", Toast.LENGTH_LONG ).show();
                }
            } else if ( !file.canWrite() ) {
                Toast.makeText(getActivity(), file.getName() + ": access denied", Toast.LENGTH_LONG ).show();
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        switch (itemPosition) {
            case 0:
                currentFolder = context.getFilesDir();
                break;
            case 1:
                currentFolder = Environment.getRootDirectory();
                break;
            case 2:
                currentFolder = Environment.getExternalStorageDirectory();
                break;
        }
        tvFolderName.setText(getFolderName());
        adapter.setFiles(CustomFile.convert(currentFolder.listFiles()));
        adapter.notifyDataSetChanged();
        return true;
    }
}
