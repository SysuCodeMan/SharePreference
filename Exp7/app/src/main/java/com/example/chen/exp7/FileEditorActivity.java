package com.example.chen.exp7;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Chen on 2016/11/14.
 */

public class FileEditorActivity extends AppCompatActivity implements View.OnClickListener{
    private String Filename = "Document.txt";
    private Button SaveButton, LoadButton, ClearButton;
    private EditText Text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_editor);
        FindViews();
        AddClickListeners();
    }

    private void FindViews() {
        SaveButton = (Button) findViewById(R.id.Save);
        LoadButton = (Button) findViewById(R.id.Load);
        ClearButton = (Button) findViewById(R.id.Clear);
        Text = (EditText) findViewById(R.id.Text);
    }

    private void AddClickListeners() {
        SaveButton.setOnClickListener(this);
        LoadButton.setOnClickListener(this);
        ClearButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Save:
                SaveText();
                break;
            case  R.id.Load:
                LoadText();
                break;
            case R.id.Clear:
                Text.setText("");
                break;
        }
    }

    private void SaveText() {
        try {
            FileOutputStream document = openFileOutput(Filename, MODE_PRIVATE);
            String text = Text.getText().toString();
            document.write(text.getBytes("UTF-8"));
            document.flush();
            document.close();
            Toast.makeText(this, "Save successfully", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException ex) {
            Toast.makeText(this, "FileNotFound Exception", Toast.LENGTH_SHORT).show();
        } catch (IOException ex) {
            Toast.makeText(this, "IO Exception!", Toast.LENGTH_SHORT).show();
        }
    }

    private void LoadText() {
        try {
            String text = "";
            FileInputStream document = openFileInput(Filename);
            byte[] readBytes = new byte[document.available()];
            while (document.read(readBytes) != -1) {
                text += new String(readBytes,"UTF-8");
            }
            Text.setText(text);
            Toast.makeText(this, "Load successfully", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException ex) {
            Toast.makeText(this, "Fail to load file", Toast.LENGTH_SHORT).show();
        } catch (IOException ex) {
            Toast.makeText(this, "Fail to load file", Toast.LENGTH_SHORT).show();
        }
    }
}
