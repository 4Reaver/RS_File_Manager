package com.example.reaver.rsfilemanager;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Reaver on 14.08.2014.
 */
public class EditFileFragment extends Fragment {
    TextView tvFileName;
    EditText etFileContents;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_file_fragment, container, false);

        tvFileName = (TextView) view.findViewById(R.id.tvFileName);
        etFileContents = (EditText) view.findViewById(R.id.etFileContents);

        return view;
    }

    public void updateContent(File file) {
        String content = "";

        tvFileName.setText(file.getName());
        try {
            String tmp;
            BufferedReader br = new BufferedReader(new FileReader(file));

            while ( (tmp = br.readLine()) != null ) {
                content = content.concat(tmp + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        etFileContents.setText(content);
    }

    public void saveFile(File file, String newContent) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            bw.write(newContent);

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
