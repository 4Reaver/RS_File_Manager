package com.example.reaver.rsfilemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Reaver on 09.08.2014.
 */
public class FileListAdapter extends BaseAdapter {
    private static int FILE_ICON_ID = R.drawable.document_icon;
    private static int DIRECTIORY_ICON_ID = R.drawable.folder_icon;

    ArrayList<CustomFile> files;
    Context context;
    LayoutInflater inflater;

    public FileListAdapter(Context context, ArrayList<CustomFile> files) {
        this.context = context;
        this.files = files;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ArrayList<CustomFile> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<CustomFile> files) {
        this.files = files;
    }

    @Override
    public int getCount() {
        return files.size();
    }

    @Override
    public Object getItem(int position) {
        return files.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        CustomFile file = (CustomFile) getItem(position);
        ImageView icon;

        if ( view == null ) {
            view = inflater.inflate(R.layout.file_item, parent, false);
        }

        icon = (ImageView) view.findViewById(R.id.item_icon);
        if ( !file.isDirectory() ) {
            icon.setImageResource(FILE_ICON_ID);
        } else {
            icon.setImageResource(DIRECTIORY_ICON_ID);
        }
        ((TextView) view.findViewById(R.id.item_text)).setText(file.getName());

        return view;
    }
}
