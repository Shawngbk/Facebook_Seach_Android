package com.example.apple.hw9;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button clear_btn;
    private EditText editText;
    private Button search_btn;
    private OkHttpClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        clear_btn = (Button) findViewById(R.id.clear_btn);
        editText = (EditText) findViewById(R.id.editText);
        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
        search_btn = (Button) findViewById(R.id.search_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kw = editText.getText().toString();
                if(kw.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter a keyword!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    getWebService();
                }
            }
        });
        client = new OkHttpClient();

    }
    private void getWebService() {
        String kw = editText.getText().toString().trim();
        String page_url = "isNull";
        final Request request = new Request.Builder()
                .url("http://sample-env.5e5thfsm44.us-west-2.elasticbeanstalk.com/hw9.php?keywords=" + kw)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String htmlStr =  response.body().string();
                //Type listTpye = new TypeToken<LinkedList<QueryRes>>(){}.getType();
                //System.out.println(htmlStr.toString());

                try {
                    JSONObject json = new JSONObject(htmlStr);
                    final QueryRes data = (QueryRes) getApplication();
                    data.setUser_obj(json.getJSONObject("user_obj"));
                    data.setPage_obj(json.getJSONObject("page_obj"));
                    data.setEvent_obj(json.getJSONObject("event_obj"));
                    data.setPlace_obj(json.getJSONObject("place_obj"));
                    data.setGroup_obj(json.getJSONObject("group_obj"));
                    //System.out.println(data.toString());
                    //System.out.println(data.getUser_obj());
                } catch (Exception e) {
                    System.out.println("error again");
                }

                Intent intent = new Intent(getBaseContext(), ViewPagerActivity.class);
                startActivity(intent);
                //Gson gson = new Gson();
                //LinkedList<QueryRes> qr =  gson.fromJson(htmlStr.toString(), listTpye);
                //for(Iterator iterator = qr.iterator(); iterator.hasNext();) {

                //}
                //QueryRes qr =

                //System.out.println("coooool" + qr.getUser_obj());

                /*
                parseJSONWithGSON(htmlStr);
                Gist gist = gson.fromJson(response.body().charStream(), Gist.class);
                for (Map.Entry<String, GistFile> entry : gist.files.entrySet()) {
                    System.out.println(entry.getKey());
                    System.out.println(entry.getValue().content);
                }*/
            }
        });
    }
/*
    private void parseJSONWithGSON(String jsonData) {
        Gist gist = parseJSONWithGSON();
    }

    static class Gist {
        Map<String, GistFile> files;
    }

    static class GistFile {
        String content;
    }
/*
    private void getWebService() {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String kw = editText.getText().toString();
        String json = "{\n" + kw + "}";
        //MediaType JSON = MediaType.parse("application")

        RequestBody requestBody = RequestBody.create(JSON, json);
        //RequestBody requestBody = RequestBody.create(JSON, json);
        //RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url("http://localhost:8888/hw9.php").post(requestBody).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("error!");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response)  {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println(response.body().string());
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                });
            }
        });
    }
*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_favorite) {
            Intent intent = new Intent(getBaseContext(), FavViewPagerActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_myself) {
            Intent intent = new Intent(MainActivity.this, AboutMe.class);
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
