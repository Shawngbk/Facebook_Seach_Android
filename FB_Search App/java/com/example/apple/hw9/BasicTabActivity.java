package com.example.apple.hw9;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by apple on 17/4/19.
 */

public class BasicTabActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    private TabLayout tabarnak;
    private TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_res_show);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.aboutme_toolbar);
        //setSupportActionBar(aboutme_toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Result");
        }
        setContentView(R.layout.activity_basic_tab);

        getSupportActionBar().setElevation(0f);

        content = (TextView)findViewById(R.id.lbl_basic_content);

        tabarnak = (TabLayout)findViewById(R.id.tbl_basic);

        //create new tabs and set titles、

        tabarnak.addTab(tabarnak.newTab().setIcon(R.drawable.users));
        tabarnak.addTab(tabarnak.newTab().setIcon(R.drawable.pages));
        tabarnak.addTab(tabarnak.newTab().setIcon(R.drawable.events));
        tabarnak.addTab(tabarnak.newTab().setIcon(R.drawable.places));
        tabarnak.addTab(tabarnak.newTab().setIcon(R.drawable.groups));

        content.setText("1");
        tabarnak.setTabTextColors(ContextCompat.getColor(this, android.R.color.white),
                ContextCompat.getColor(this, R.color.colorAccent));
        tabarnak.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));

        tabarnak.addOnTabSelectedListener(this);
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        //content.setText(tab.getText().toString());
        //content.setText(tab.getTag().hashCode());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }
}