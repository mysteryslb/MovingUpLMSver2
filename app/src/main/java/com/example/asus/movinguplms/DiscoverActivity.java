package com.example.asus.movinguplms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DiscoverActivity extends AppCompatActivity {

    private TextView viewSchoolList, viewOnMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        viewSchoolList = (TextView) findViewById(R.id.discoveraboutviewschoollist);
        viewOnMaps = (TextView) findViewById(R.id.discoveraboutviewmaps);

        viewSchoolList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendToDiscoverSchoolListsActivity();
            }
        });
        viewOnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendToDiscoverOnMapsActivity();
            }
        });
    }

    private void SendToDiscoverSchoolListsActivity() {
        Intent discoverIntent = new Intent(DiscoverActivity.this, DiscoverSchoolsOnListActivity.class);
        startActivity(discoverIntent);
    }

    private void SendToDiscoverOnMapsActivity() {
        Intent discoverIntent = new Intent(DiscoverActivity.this, SHSSchoolMapsActivity.class);
        startActivity(discoverIntent);
    }
}
