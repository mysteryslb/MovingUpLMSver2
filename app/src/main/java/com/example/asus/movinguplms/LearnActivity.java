package com.example.asus.movinguplms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LearnActivity extends AppCompatActivity {

    private TextView viewAbout, viewPrograms, viewFaqs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        viewAbout = (TextView) findViewById(R.id.learnaboutview);
        viewPrograms = (TextView) findViewById(R.id.learnaboutviewofferedprograms);
        viewFaqs = (TextView) findViewById(R.id.learnaboutviewfaqs);

        viewPrograms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendToLearSHSTracksActivity();
            }
        });
    }

    private void SendToLearSHSTracksActivity() {
        Intent learnIntent = new Intent(LearnActivity.this, LearnSHSTracksActivity.class);
        startActivity(learnIntent);
    }
}
