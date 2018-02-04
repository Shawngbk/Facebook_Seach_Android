package com.example.apple.hw9;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by apple on 17/4/19.
 */

public class DetailViewPagerActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener,
        PostViewPagerItemFragment.FragmentPagerItemCallback{

    CallbackManager callbackManager;
    ShareDialog shareDialog;

    private TabLayout tabarnak;
    private ViewPager pager;
    public int onSelectedPosition;
    private String post_url;
    private String fav_id;
    private String fav_name;

    private String event_url;
    private String event_id;
    private String event_name;

    Map<String, Object> fav_map;
    private String whatType;
    //QueryRes qr;

    public ArrayList<String> fav_id_list;

    private static final String[] pageTitles = {"Albums", "Posts"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        String str=intent.getStringExtra("detail_json_str");
        //System.out.println("this is for test event detail+@#$^%$#*&^" + str);
        try {
            whatType = ViewPagerItemFragment.what_type;

            System.out.println("what type::::::" + whatType);

            if(whatType == "Events") {
                String event_str = "{}";
                JSONObject event_json = new JSONObject(event_str);
                //System.out.println("eventdetailjson is :::::::" + event_json);
                final QueryRes data = (QueryRes) getApplication();
                data.setDetail_obj(event_json);
                event_id = ViewPagerItemFragment.event_id;

                event_name = ViewPagerItemFragment.event_name;
                event_url = ViewPagerItemFragment.event_url;

            } else {
                JSONObject detail_json = new JSONObject(str);
                final QueryRes data = (QueryRes) getApplication();
                data.setDetail_obj(detail_json);
                post_url = detail_json.getJSONObject("picture").getJSONObject("data").getString("url");
                fav_id = detail_json.getString("id");
                fav_name = detail_json.getString("name");
            }


            //System.out.print("this is intent detail json" + detail_json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //setContentView(R.layout.activity_view_pager);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("More Details");
        }
        setContentView(R.layout.activity_detail_view_pager);

        getSupportActionBar().setElevation(0f);


        pager = (ViewPager)findViewById(R.id.detail_vpg_main_content);
        tabarnak = (TabLayout)findViewById(R.id.detail_tbl_main_content);

        //tabarnak.getTabAt(0).setIcon(R.drawable.users);
        //tabarnak.getTabAt(1).setIcon(R.drawable.pages);
        //tabarnak.getTabAt(2).setIcon(R.drawable.events);
        //tabarnak.getTabAt(3).setIcon(R.drawable.places);
        //tabarnak.getTabAt(4).setIcon(R.drawable.groups);
        tabarnak.setupWithViewPager(pager);

        setUpPagerAndTabs();
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Toast.makeText(getApplicationContext(), "You shared this post!",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
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
        tabarnak.getTabAt(0).setIcon(R.drawable.albums);
        //tabarnak.getTabAt(1).setText("");
        tabarnak.getTabAt(1).setIcon(R.drawable.posts);


    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        pager.setCurrentItem(tab.getPosition());
        onSelectedPosition = tab.getPosition();
        //System.out.println("onTabSelected output"+tab.getPosition());
        //getData(onSelectedPosition);
        //ViewPagerItemFragment vri = new ViewPagerItemFragment();

        //vri.setPositionFromActivity(onSelectedPosition);
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

    public static class CustomAdapter extends FragmentStatePagerAdapter {

        public CustomAdapter (FragmentManager manager){
            super(manager);
        }



        @Override
        public Fragment getItem(int position) {
            //System.out.println("getItem" + ViewPagerItemFragment.getInstance(pageTitles[position]));
            return PostViewPagerItemFragment.getInstance(pageTitles[position]);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(QueryRes.fav_id_list.contains(fav_id) || QueryRes.fav_id_list.contains(event_id)) {
            getMenuInflater().inflate(R.menu.remove_menu, menu);
        } else {
            getMenuInflater().inflate(R.menu.main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.fav_menu) {
            //QueryRes qr = (QueryRes) getActivity().getApplication();
            //qr = new QueryRes();
            if(QueryRes.fav_id_list.contains(fav_id)) {
                System.out.println("exit!!!");
            } else {
                fav_map = new HashMap<>();
                if(whatType == "Events") {
                    QueryRes.fav_id_list.add(event_id);
                    fav_map.put("photo", event_url);
                    fav_map.put("name", event_name);
                    fav_map.put("id", event_id);
                    BigInteger bi = new BigInteger(event_id);
                    fav_map.put("bi_id",bi);
                    QueryRes.event_fav_list.add(fav_map);
                    //System.out.println("QueryRes list test666666::" + QueryRes.event_fav_list);
                } else {
                    QueryRes.fav_id_list.add(fav_id);
                    //fav_map = new HashMap<>();
                    fav_map.put("photo", post_url);
                    fav_map.put("name", fav_name);
                    fav_map.put("id", fav_id);
                    BigInteger bi = new BigInteger(fav_id);
                    fav_map.put("bi_id",bi);
                    if(whatType == "Users") {
                        QueryRes.user_fav_list.add(fav_map);
                    } else if(whatType == "Pages") {
                        QueryRes.page_fav_list.add(fav_map);
                    } else if(whatType == "Places") {
                        QueryRes.place_fav_list.add(fav_map);
                    } else if(whatType == "Groups") {
                        QueryRes.group_fav_list.add(fav_map);
                    }
                }

            }

            //System.out.println("test is null:: fav_id_list:::" + QueryRes.fav_id_list);
            Toast.makeText(getApplicationContext(), "Added to Favorites!",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        if(id == R.id.fav_remove) {
            fav_map = new HashMap<>();
            if(whatType == "Events") {
                fav_map.put("photo", event_url);
                fav_map.put("name", event_name);
                fav_map.put("id", event_id);
                BigInteger bi = new BigInteger(event_id);
                fav_map.put("bi_id",bi);
                QueryRes.fav_id_list.remove(event_id);
                QueryRes.event_fav_list.remove(fav_map);
                //System.out.println("QueryRes list test777777::" + QueryRes.event_fav_list);
            } else {
                QueryRes.fav_id_list.remove(fav_id);
                fav_map.put("photo", post_url);
                fav_map.put("name", fav_name);
                fav_map.put("id", fav_id);
                BigInteger bi = new BigInteger(fav_id);
                fav_map.put("bi_id",bi);
                if(whatType == "Users") {
                    QueryRes.user_fav_list.remove(fav_map);
                    FavViewPagerItemFragment.listems = QueryRes.user_fav_list;
                } else if(whatType == "Pages") {
                    QueryRes.page_fav_list.remove(fav_map);
                } else if(whatType == "Places") {
                    QueryRes.place_fav_list.remove(fav_map);
                } else if(whatType == "Groups") {
                    QueryRes.group_fav_list.remove(fav_map);
                }

            }
            Toast.makeText(getApplicationContext(), "Removed from Favorites!",
                    Toast.LENGTH_SHORT).show();
            //FavViewPagerItemFragment.listems = QueryRes
        }

        if(id == R.id.share) {
//            FacebookSdk.sdkInitialize(getApplicationContext());
//            callbackManager = CallbackManager.Factory.create();
            //shareDialog = new ShareDialog(this);
            String url = "http://facebook.com/";
            if(whatType == "Events") {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle(event_name)
                        .setImageUrl(Uri.parse(event_url))
                        .setContentDescription(
                                "FB SEARCH FROM CS571 ")
                        .setContentUrl(Uri.parse(url + event_id))
                        .build();

                shareDialog.show(linkContent);
            } else {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle(fav_name)
                        .setImageUrl(Uri.parse(post_url))
                        .setContentDescription(
                                "FB SEARCH FROM CS571 ")
                        .setContentUrl(Uri.parse(url + fav_id))
                        .build();

                shareDialog.show(linkContent);
            }


//            if (ShareDialog.canShow(ShareLinkContent.class)) {
//                ShareLinkContent linkContent = new ShareLinkContent.Builder()
//                        .setContentUrl(Uri.parse(post_url))
//                        .build();
//                shareDialog.show(linkContent);
//                Intent intent = getIntent();
//                onActivityResult(1,1,intent);
//            }

        }

        if(id == R.id.share_remove) {
            String url = "http://facebook.com/";
            if(whatType == "Events") {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle(event_name)
                        .setImageUrl(Uri.parse(event_url))
                        .setContentDescription(
                                "FB SEARCH FROM CS571 ")
                        .setContentUrl(Uri.parse(url + event_id))
                        .build();

                shareDialog.show(linkContent);
            } else {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle(fav_name)
                        .setImageUrl(Uri.parse(post_url))
                        .setContentDescription(
                                "FB SEARCH FROM CS571 ")
                        .setContentUrl(Uri.parse(url + fav_id))
                        .build();

                shareDialog.show(linkContent);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

