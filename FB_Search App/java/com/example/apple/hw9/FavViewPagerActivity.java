package com.example.apple.hw9;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONObject;

/**
 * Created by apple on 17/4/19.
 */

public class FavViewPagerActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener,
        FavViewPagerItemFragment.FragmentPagerItemCallback{


    private TabLayout fav_tabarnak;
    private ViewPager fav_pager;
    public static int onSelectedPosition;

    private static final String[] pageTitles = {"Users", "Pages", "Events", "Places", "Groups"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_view_pager);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Favorites");
        }
        setContentView(R.layout.fav_activity_view_pager);

        getSupportActionBar().setElevation(0f);


        fav_pager = (ViewPager)findViewById(R.id.fav_vpg_main_content);
        fav_tabarnak = (TabLayout)findViewById(R.id.fav_tbl_main_content);

        //tabarnak.getTabAt(0).setIcon(R.drawable.users);
        //tabarnak.getTabAt(1).setIcon(R.drawable.pages);
        //tabarnak.getTabAt(2).setIcon(R.drawable.events);
        //tabarnak.getTabAt(3).setIcon(R.drawable.places);
        //tabarnak.getTabAt(4).setIcon(R.drawable.groups);
        fav_tabarnak.setupWithViewPager(fav_pager);


        setUpPagerAndTabs();

    }

    private void setUpPagerAndTabs(){
        fav_tabarnak.setTabTextColors(ContextCompat.getColor(this, android.R.color.white),
                ContextCompat.getColor(this, R.color.colorAccent));
        fav_tabarnak.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));


        CustomAdapter adapter = new CustomAdapter(getSupportFragmentManager());
        fav_pager.setAdapter(adapter);
        //pager.
//System.out.println("0998878908767"+pager);
        fav_tabarnak.addOnTabSelectedListener(this);
        fav_tabarnak.setupWithViewPager(fav_pager);
        //tabarnak.getTabAt(0).setText("");
        fav_tabarnak.getTabAt(0).setIcon(R.drawable.users);
        //tabarnak.getTabAt(1).setText("");
        fav_tabarnak.getTabAt(1).setIcon(R.drawable.pages);
        //tabarnak.getTabAt(2).setText("");
        fav_tabarnak.getTabAt(2).setIcon(R.drawable.events);
        //tabarnak.getTabAt(3).setText("");
        fav_tabarnak.getTabAt(3).setIcon(R.drawable.places);
        //tabarnak.getTabAt(4).setText("");
        fav_tabarnak.getTabAt(4).setIcon(R.drawable.groups);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        fav_pager.setCurrentItem(tab.getPosition());
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
            return FavViewPagerItemFragment.getInstance(pageTitles[position]);
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

