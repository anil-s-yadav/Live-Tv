package com.legendarysoftwares.simpletabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class home extends AppCompatActivity {
Button home,slide,bubble;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        home=findViewById(R.id.home);
        slide=findViewById(R.id.slide);
        bubble=findViewById(R.id.bubble);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(home.this,MainActivity.class);
                startActivity(intent);
            }
        });
   }
}