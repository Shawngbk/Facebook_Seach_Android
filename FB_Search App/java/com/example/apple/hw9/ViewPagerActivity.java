package com.example.apple.hw9;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONObject;

/**
 * Created by apple on 17/4/19.
 */

public class ViewPagerActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener,
        ViewPagerItemFragment.FragmentPagerItemCallback{


    private TabLayout tabarnak;
    private ViewPager pager;
    public int onSelectedPosition;

    private static final String[] pageTitles = {"Users", "Pages", "Events", "Places", "Groups"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_view_pager);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Result");
        }
        setContentView(R.layout.activity_view_pager);

        getSupportActionBar().setElevation(0f);


        pager = (ViewPager)findViewById(R.id.vpg_main_content);
        tabarnak = (TabLayout)findViewById(R.id.tbl_main_content);

        //tabarnak.getTabAt(0).setIcon(R.drawable.users);
        //tabarnak.getTabAt(1).setIcon(R.drawable.pages);
        //tabarnak.getTabAt(2).setIcon(R.drawable.events);
        //tabarnak.getTabAt(3).setIcon(R.drawable.places);
        //tabarnak.getTabAt(4).setIcon(R.drawable.groups);
        tabarnak.setupWithViewPager(pager);

        Button pre_btn = (Button) findViewById(R.id.pre_btn);
        pre_btn.setEnabled(false);

        Button next_btn = (Button) findViewById(R.id.next_btn);
        next_btn.setEnabled(false);

        setUpPagerAndTabs();

    }

    private void setUpPagerAndTabs(){
        tabarnak.setTabTextColors(ContextCompat.getColor(this, android.R.color.white),
                ContextCompat.getColor(this, R.color.colorAccent));
        tabarnak.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));


        CustomAdapter adapter = new CustomAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        //pager.
//System.out.println("0998878908767"+pager);
        tabarnak.addOnTabSelectedListener(this);
        tabarnak.setupWithViewPager(pager);
        //tabarnak.getTabAt(0).setText("");
        tabarnak.getTabAt(0).setIcon(R.drawable.users);
        //tabarnak.getTabAt(1).setText("");
        tabarnak.getTabAt(1).setIcon(R.drawable.pages);
        //tabarnak.getTabAt(2).setText("");
        tabarnak.getTabAt(2).setIcon(R.drawable.events);
        //tabarnak.getTabAt(3).setText("");
        tabarnak.getTabAt(3).setIcon(R.drawable.places);
        //tabarnak.getTabAt(4).setText("");
        tabarnak.getTabAt(4).setIcon(R.drawable.groups);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        pager.setCurrentItem(tab.getPosition());
        onSelectedPosition = tab.getPosition();
        //System.out.println("onTabSelected output"+tab.getPosition());
        getData(onSelectedPosition);
        //ViewPagerItemFragment vri = new ViewPagerItemFragment();

        //vri.setPositionFromActivity(onSelectedPosition);
    }

    public void getData(int flag) {
        JSONObject json = null;

        final QueryRes qs = (QueryRes) getApplication();
        //final QueryRes data = (QueryRes) get
        if(flag == 0) {
            json = qs.getUser_obj();
            //System.out.println("lihaile"+json);
        } else if(flag == 1) {
            json = qs.getPage_obj();
        } else if(flag == 2) {
            json = qs.getEvent_obj();
        } else if(flag == 3) {
            json = qs.getPlace_obj();
        } else if(flag == 4) {
            json = qs.getGroup_obj();
        }
/*
        try {
            String[] nameRes = new String[json.getJSONArray("data").length()];
            String[] idRes = new String[json.getJSONArray("data").length()];
            String[] photoRes = new String[json.getJSONArray("data").length()];
            for(int i = 0; i < json.getJSONArray("data").length();i++){
                JSONObject record = json.getJSONArray("data").getJSONObject(i);
                nameRes[i] = record.getString("name");
                photoRes[i] = record.getJSONObject("picture").getJSONObject("data").getString("url");
                idRes[i] = record.getString("id");
                System.out.println(photoRes[i]);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
*/
        //ViewPagerItemFragment vri = new ViewPagerItemFragment();
        //vri.setJson_obj(json);
        //System.out.println(flag);

    }

    public int returnOnSelectedPostion() {

        return onSelectedPosition;
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    @Override
    public void onPagerItemClick(String message) {
        Toast.makeText(this, message + "!", Toast.LENGTH_SHORT).show();
    }

    /*For Pagers with a smaller/static number of pages, use FragmentPagerAdapter. It keeps visited
    caches fragments which have been opened in memory. If you require a large/dynamic number
    of pages, use FragmentStatePagerAdapter instead.
    */
     class CustomAdapter extends FragmentStatePagerAdapter {

        public CustomAdapter (FragmentManager manager){
            super(manager);
        }



        @Override
        public Fragment getItem(int position) {
            //System.out.println("getItem" + ViewPagerItemFragment.getInstance(pageTitles[position]));
            return ViewPagerItemFragment.getInstance(pageTitles[position]);
            //return POSITION_NONE;

        }

        @Override
        public int getCount() {
            //System.out.println("getCount" + pageTitles.length);
            return pageTitles.length;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public CharSequence getPageTitle(int position){
            return pageTitles[position];
        }
    }
}

