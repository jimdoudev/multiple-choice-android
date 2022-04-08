package com.example.multiplechoiceproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WelcomePage extends AppCompatActivity {

    Button BtStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //hide the title bar
        getSupportActionBar().hide();
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        BtStart = findViewById(R.id.BtStart);
        BtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent intent = new Intent (getApplicationContext(), Menu.class);
                startActivity(intent);
            }
        });
        if(!FileExists("MultipleChoiceProject.db")) {
            CopyDB("MultipleChoiceProject.db");
        }
    }

    boolean FileExists(String FileName) {
        File file = new File (getApplicationContext().getFilesDir(), FileName);
        return file.exists();
    }

    void CopyDB(String FileName) {
        AssetManager AssetMan = getAssets();
        InputStream Inp;
        OutputStream Outp;
        byte[] Buffer;
        int BR;
        try {
            Inp = AssetMan.open(FileName);
            File OutputFile = new File (getApplicationContext().getFilesDir(), FileName);
            Outp = new FileOutputStream(OutputFile);
            Buffer = new byte[1024];
            while ((BR = Inp.read(Buffer)) != -1) {
                Outp.write (Buffer, 0, BR);
            }
            Inp.close();
            Outp.flush();
            Outp.close();
        } catch (IOException e) {
            Toast tst = Toast.makeText (getApplicationContext (), "IO Error during DB copy.", Toast.LENGTH_LONG);
            tst.show ();
            finish();
        }
    }


}