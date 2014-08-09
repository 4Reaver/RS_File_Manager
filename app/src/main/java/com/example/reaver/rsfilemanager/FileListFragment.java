package com.example.reaver.rsfilemanager;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Reaver on 09.08.2014.
 */
public class FileListFragment extends Fragment implements AdapterView.OnItemClickListener {
    private File currentFolder = Environment.getRootDirectory();
    private FileListAdapter adapter;
    private ListView lvFileList;
    private View rootFolder;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.file_list_fragment, container, false);
        File[] files = currentFolder.listFiles();
        ArrayList<CustomFile> fileList = CustomFile.convert(files);

        adapter = new FileListAdapter(getActivity(), fileList);

        rootFolder = inflater.inflate(R.layout.file_item, null);
        ((TextView) rootFolder.findViewById(R.id.item_text)).setText("..");
        ((ImageView) rootFolder.findViewById(R.id.item_icon)).setImageResource(R.drawable.folder_icon);

        lvFileList = (ListView) v.findViewById(R.id.lvFileList);
        lvFileList.addHeaderView(rootFolder, null, true);
        lvFileList.setAdapter(adapter);
        lvFileList.setOnItemClickListener(this);

        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        File selectedFile;
        boolean isCurrentRoot = currentFolder.getParentFile() == null;

        if ( position == 0 && !isCurrentRoot ) {
            selectedFile = currentFolder.getParentFile();
        } else {
            selectedFile = (File) lvFileList.getAdapter().getItem(position);
        }

        if ( !selectedFile.canRead() ) {
            Toast.makeText(getActivity(), "Access denied", Toast.LENGTH_SHORT).show();
            return;
        }

        if ( selectedFile.getParentFile() == null ) {
            lvFileList.removeHeaderView(rootFolder);
        } else if ( currentFolder.getParentFile() == null && selectedFile.isDirectory() ) {
            lvFileList.addHeaderView(rootFolder);
        }

        if ( selectedFile.isDirectory() ) {
            currentFolder = selectedFile;
            adapter.setFiles(CustomFile.convert(currentFolder.listFiles()));
            adapter.notifyDataSetChanged();
        }
    }
}
