package com.example.reaver.rsfilemanager;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

/**
 * Created by Reaver on 09.08.2014.
 */
public class CustomFile extends File {
    private boolean checked;

    public CustomFile(File file) {
        super(file.toURI());
        this.checked = false;
    }

    public boolean isChecked() {
        return checked;
    }

    public void invertChek() {
        checked = !checked;
    }

    public static ArrayList<CustomFile> convert(File[] files) {
        int size = files.length;
        ArrayList<CustomFile> fileList = new ArrayList<CustomFile>();

        for (int i = 0; i < size; i++ ) {
            fileList.add(new CustomFile(files[i]));
        }

        return fileList;
    }
}
