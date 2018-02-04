package com.example.apple.hw9;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by apple on 17/4/19.
 */

public class ResShow extends AppCompatActivity {
    //private Toolbar aboutme_toolbar = (Toolbar) findViewById(R.id.aboutme_toolbar);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_show);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.aboutme_toolbar);
        //setSupportActionBar(aboutme_toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Result");
        }
    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
    */
}

