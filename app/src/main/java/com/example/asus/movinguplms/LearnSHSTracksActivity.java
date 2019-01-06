package com.example.asus.movinguplms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LearnSHSTracksActivity extends AppCompatActivity {

    private TextView viewAcademic, viewTvl, viewSports, viewArts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_shstracks);

        viewAcademic = (TextView) findViewById(R.id.shstrackaboutviewacademic);

        viewAcademic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendToLearnAcademicStrandActivity();
            }
        });
    }

    private void SendToLearnAcademicStrandActivity() {
        Intent learnIntent = new Intent(LearnSHSTracksActivity.this, LearnAcademicStrandsAcivity.class);
        startActivity(learnIntent);
    }
}
