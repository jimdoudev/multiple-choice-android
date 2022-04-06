package com.example.multiplechoiceproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class DataEntry extends AppCompatActivity {

    EditText EtName;
    EditText EtAM;
    ImageButton BtGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //hide the title bar
        getSupportActionBar().hide();
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_data_entry);
        EtName = findViewById(R.id.EtName);
        EtAM = findViewById(R.id.EtAM);
        BtGo = findViewById(R.id.BtGo);
        BtGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MultipleChoice.class);
                Bundle bundle = new Bundle();
                bundle.putString("Name", EtName.getText().toString());
                bundle.putString("AM", EtAM.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

}