package com.example.apple.hw9;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by apple on 17/4/19.
 */

public class ViewPagerItemFragment extends Fragment {
    private static final String PAGE_TITLE = "PAGE_TITLE";
    MySimpleAdapter adapter;
    private String pageTitle;
    private FragmentPagerItemCallback callback;
    public int onSelectedPosition;

    List<Map<String, Object>> listems;
    Map<String, Object> listem;

    public static Bundle favType;

    List<Map<String, Object>> page_listems = new ArrayList<>();

    JSONObject page_json;

    private OkHttpClient client;
    private Handler handler;

    public String[] idRes;
    public String[] photoRes;
    public String[] nameRes;
    public BigInteger[] bi_id;

    Button pre_btn;
    Button next_btn;

    String pre_url = "";
    String next_url = "";

    private JSONObject json;
    private JSONObject detail_json;

    public static String what_type;

    TabLayout tab;

    public static String event_id;
    public static String event_name;
    public static String event_url;

    public void setJson_obj(JSONObject json_obj) {
        this.json_obj = json_obj;
        //System.out.println("getjson: " + json_obj);
        parseJson(json_obj);
    }

    private void parseJson(JSONObject json) {
        //listems = new ArrayList<>();
        try {
            nameRes = new String[json.getJSONArray("data").length()];
            idRes = new String[json.getJSONArray("data").length()];
            photoRes = new String[json.getJSONArray("data").length()];
            bi_id = new BigInteger[json.getJSONArray("data").length()];

            for(int i = 0; i < json.getJSONArray("data").length();i++){
                JSONObject record = json.getJSONArray("data").getJSONObject(i);
                nameRes[i] = record.getString("name");
                photoRes[i] = record.getJSONObject("picture").getJSONObject("data").getString("url");
                idRes[i] = record.getString("id");
                String temp_id = record.getString("id");
                BigInteger final_id = new BigInteger(temp_id);
                bi_id[i] = final_id;
                //System.out.println(photoRes[i]);
                //System.out.println("main____"+nameRes[0]);
            }
            //System.out.println("this is Miannnnnnnnn::::" + photoRes[0]);
            listems = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < nameRes.length; i++) {
                listem = new HashMap<String, Object>();
                listem.put("photo", photoRes[i]);
                listem.put("name", nameRes[i]);
                listem.put("id", idRes[i]);
                listem.put("bi_id", bi_id[i]);
                listems.add(listem);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
/*
        List<Map<String, Object>> listems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < nameRes.length; i++) {
            Map<String, Object> listem = new HashMap<String, Object>();
            listem.put("photo", photoRes[i]);
            listem.put("name", nameRes[i]);

            listems.add(listem);
        }*/
    }

    private JSONObject json_obj;

    public void setPositionFromActivity(int positionFromActivity) {
        this.positionFromActivity = positionFromActivity;
        //System.out.println("receive x from activity" + positionFromActivity);
        //String[] temp = getData(positionFromActivity);
    }





    public int positionFromActivity;




    private String type;

    public ViewPagerItemFragment(){}

    private String[] name = { "剑萧舞蝶", "张三", "hello", "诗情画意" };
    private String[] name1 = { "蝶", "张2", "helo", "诗情画意" };
    private String[] name2 = { "蝶", "张3", "helo", "诗情画意" };
    private String[] name3 = { "蝶", "张4", "helo", "诗情画意" };
    private String[] name4 = { "蝶", "张5", "helo", "诗情画意" };

    //private String[] desc = { "魔域玩家", "百家执行", "高级的富一代", "妹子请过来..一个善于跑妹子的。。" };

    private int[] imageids = { R.drawable.albums, R.drawable.details,
            R.drawable.events, R.drawable.favorites_on };

    public static ViewPagerItemFragment getInstance(String pageTitle){
        //Bundle bundle = new Bundle();

        ViewPagerItemFragment fragment = new ViewPagerItemFragment();

        Bundle args = new Bundle();
        args.putString(PAGE_TITLE, pageTitle);
        fragment.setArguments(args);
        //System.out.println("ViewPagerItemFragment getInstance--------------" + fragment);
        //System.out.println("receive x from activity" + fragment);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        //System.out.println("onCreare--------------");
        super.onCreate(savedInstanceState);
        type = getArguments().getString(PAGE_TITLE);


        if (getArguments() != null) {
            this.pageTitle = getArguments().getString(PAGE_TITLE);

        } else {
            Log.d("TAG", "Well... F***.");
        }
        handler = new PageHandler();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_res_listview, container, false);
       // test();
        Bundle bundle = this.getArguments();
        //System.out.println("onCreateView--------------");
        //if(page_listems.e)
        //page_listems = new ArrayList<Map<String, Object>>();

        if(bundle != null) {
            if(bundle.getString("PAGE_TITLE") == "Users") {
                //System.out.println("onCreateView:::::::" + bundle.getString("PAGE_TITLE"));
                //what_type = "Users";
                getResult("Users");
            } else if (bundle.getString("PAGE_TITLE") == "Pages") {
                //what_type = "Pages";
                //System.out.println("onCreateView:::::::" + bundle.getString("PAGE_TITLE"));
                getResult("Pages");
            } else if(bundle.getString("PAGE_TITLE") == "Events") {
                //what_type = "Events";
                getResult("Events");
                //System.out.println("onCreateView:::::::" + bundle.getString("PAGE_TITLE"));
            } else if(bundle.getString("PAGE_TITLE") == "Places") {
                //what_type = "Places";
                getResult("Places");
                //System.out.println("onCreateView:::::::" + bundle.getString("PAGE_TITLE"));
            } else if(bundle.getString("PAGE_TITLE") == "Groups") {
                //what_type = "Groups";
                //System.out.println("onCreateView:::::::" + bundle.getString("PAGE_TITLE"));
                getResult("Groups");
            }


            /*
            if(!page_listems.isEmpty()) {
                System.out.println(page_listems.isEmpty()+"+++++++++++++++");
                listems.clear();
                listems = page_listems;
                page_listems.clear();
            }
            */
            //onSelectedPosition = new ViewPagerActivity().onSelectedPosition;
            //System.out.println("coooooooool" + onSelectedPosition);



            ListView res_list = ((ListView) v.findViewById(R.id.res_list));
            //getResult();
            adapter = new MySimpleAdapter(getActivity(), listems,
                    R.layout.each_item,
                    new String[]{"photo", "name", "bi_id"},
                    new int[]{R.id.photo, R.id.name, R.id.fav});
            //ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.activity_list_item, tests);
            res_list.setAdapter(adapter);
            res_list.setOnItemClickListener(new itemListener());
        }
        //System.out.println(pageTitle);
        /*
        TextView content = ((TextView)v.findViewById(R.id.lbl_pager_item_content));
        content.setText(pageTitle+"111");
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onPagerItemClick(
                        ((TextView)v).getText().toString()
                );
            }
        });
*/
        return v;
    }

    private class itemListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TabLayout tabs = (TabLayout) getActivity().findViewById(R.id.tbl_main_content);
            int pagetabPistion= tabs.getSelectedTabPosition();

            //System.out.println("This is item listener,show the item position_+_+_+_+_+_+" + pagetabPistion);
            HashMap<String, String> idMap = (HashMap<String, String>) adapter.getItem(position);
            String itemId = idMap.get("id");
            String detail_url = "http://cs-server.usc.edu:35016/hw9.php?id=" + itemId;
            getDetalJson(detail_url);
            //System.out.println("item id is:::::" + itemId);
            if(pagetabPistion == 0) {
                what_type = "Users";
            } else if(pagetabPistion == 1) {
                what_type = "Pages";
            } else if(pagetabPistion == 2) {
                event_id = idRes[position];
                //System.out.println("event id is:::::" + event_id);
                event_name = nameRes[position];
                event_url = photoRes[position];
                what_type = "Events";
            } else if(pagetabPistion == 3) {
                what_type = "Places";
            } else {
                what_type = "Groups";
            }
            //String detail_url =
            //String fbId = mMap.get("id");
            //String url = "http://default-environment.n4y2pxmrhz.us-west-2.elasticbeanstalk.com/index.php?id="+fbId;

            //new HttpAsyncTask().execute(url);

        }
    }

    public void getDetalJson(String url) {
        //System.out.println("This is next url: " + url);
        String kw = "flag";
        client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
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
                //System.out.println("this is json of detail" + htmlStr);
                try {
                    detail_json = new JSONObject(htmlStr);
                    //parsePageJson(json);
                    //System.out.println(detail_json);
                } catch (Exception e) {
                    System.out.println("error again");
                }
                Intent intent = new Intent(getActivity(), DetailViewPagerActivity.class);
                intent.putExtra("detail_json_str",htmlStr);
                startActivity(intent);
            }
        });
    }

    public void getResult(String str) {
        //tab = (TabLayout) getActivity().findViewById(R.id.tbl_main_content);
        //int po = tab.getSelectedTabPosition();

        json = null;

        final QueryRes qs = (QueryRes) getActivity().getApplication();
        Activity activity = getActivity();
        pre_btn = (Button) activity.findViewById(R.id.pre_btn);
        next_btn = (Button) activity.findViewById(R.id.next_btn);
        //final QueryRes data = (QueryRes) get
        if(str == "Users") {
            json = qs.getUser_obj();
            //parseJson(json);
            //json = qs.getUser_obj();
            //System.out.println("lihaile"+json);
            //System.out.println("user:--"+json);
        } else if(str == "Pages") {
            json = qs.getPage_obj();
           // parseJson(json);
            //json = qs.getPage_obj();
            //System.out.println("page:--"+json);
        } else if(str == "Events") {
            json = qs.getEvent_obj();
            //parseJson(json);
            //json = qs.getEvent_obj();
            //System.out.println("event:--"+json);
        } else if(str == "Places") {
            json = qs.getPlace_obj();
           // parseJson(json);
            //json = qs.getPlace_obj();
            //System.out.println("place:--"+json);
        } else {
            json = qs.getGroup_obj();
            //parseJson(json);
            //json = qs.getGroup_obj();
            //System.out.println("group"+json);
        }
        //parseJson(json);


        try {
            final QueryRes data = (QueryRes) getActivity().getApplication();
            TabLayout tabs = (TabLayout) getActivity().findViewById(R.id.tbl_main_content);
            JSONObject paging_obj;
            int pagetabPistion= tabs.getSelectedTabPosition();
            if(pagetabPistion == 0) {
                paging_obj = data.getUser_obj();
            } else if(pagetabPistion == 1) {
                paging_obj = data.getPage_obj();
            } else if(pagetabPistion == 2) {
                paging_obj = data.getEvent_obj();
            } else if(pagetabPistion == 3) {
                paging_obj = data.getPlace_obj();
            } else {
                paging_obj = data.getGroup_obj();
            }
            final JSONObject paging = (paging_obj.has("paging") ? paging_obj.getJSONObject("paging") : null);

            if(paging != null && paging_obj != null) {
                //System.out.println("paging::::::::::" + paging);
                pre_btn.setEnabled(paging.has("previous"));
                next_btn.setEnabled(paging.has("next"));
            }

            next_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final QueryRes data = (QueryRes) getActivity().getApplication();

                    TabLayout tabs = (TabLayout) getActivity().findViewById(R.id.tbl_main_content);
                    int pagetabPistion= tabs.getSelectedTabPosition();

                    if(pagetabPistion == 0) {
                        json = data.getUser_obj();
                        //System.out.println("onCreateView:::::::" + pagetabPistion);
                    } else if(pagetabPistion == 1) {
                        json = data.getPage_obj();
                        //System.out.println("onCreateView:::::::" + pagetabPistion);
                    } else if(pagetabPistion == 2) {
                        json = data.getEvent_obj();
                        //System.out.println("onCreateView:::::::" + pagetabPistion);
                    } else if(pagetabPistion == 3) {
                        json = data.getPlace_obj();
                        //System.out.println("onCreateView:::::::" + pagetabPistion);
                    } else if(pagetabPistion == 4) {
                        json = data.getGroup_obj();
                        //System.out.println("onCreateView:::::::" + pagetabPistion);
                    }
                    try {
                        final JSONObject paging = (json.has("paging") ? json.getJSONObject("paging") : null);
                        if(paging != null) {
                            if(paging.has("next")) {
                                next_url = paging.getString("next");
                                //System.out.println("This is next url: " + next_url);
                                new PagingAsyncTask().execute(next_url);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //getNextPage(next_url);
                    //System.out.println("This is next url: " + next_url);
                }
            });

            pre_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final QueryRes data = (QueryRes) getActivity().getApplication();

                    TabLayout tabs = (TabLayout) getActivity().findViewById(R.id.tbl_main_content);
                    int pagetabPistion= tabs.getSelectedTabPosition();

                    if(pagetabPistion == 0) {
                        json = data.getUser_obj();
                        //System.out.println("onCreateView:::::::" + pagetabPistion);
                    } else if(pagetabPistion == 1) {
                        json = data.getPage_obj();
                        //System.out.println("onCreateView:::::::" + pagetabPistion);
                    } else if(pagetabPistion == 2) {
                        json = data.getEvent_obj();
                        //System.out.println("onCreateView:::::::" + pagetabPistion);
                    } else if(pagetabPistion == 3) {
                        json = data.getPlace_obj();
                        //System.out.println("onCreateView:::::::" + pagetabPistion);
                    } else if(pagetabPistion == 4) {
                        json = data.getGroup_obj();
                        //System.out.println("onCreateView:::::::" + pagetabPistion);
                    }
                    try {
                        final JSONObject paging = (json.has("paging") ? json.getJSONObject("paging") : null);
                        if(paging != null) {
                            if(paging.has("next")) {
                                pre_url = paging.getString("previous");
                                //System.out.println("This is next url: " + pre_url);
                                new PagingAsyncTask().execute(pre_url);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //getNextPage(next_url);
                    //System.out.println("This is next url: " + next_url);
                }
            });

        } catch (Exception e) {
            System.out.println("get paging error:" + e);
        }
        parseJson(json);

       // System.out.println("cooo" + po);
        //return po;
    }

    //-------------------------------------------------------
    class PagingAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {
            return getJSON(url[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject json = new JSONObject(s);
                //System.out.println("yibudesjon+_+_+_+_+"+json);
                page_json = json;
                //System.out.println("page_json:::"+page_json);

                final QueryRes data = (QueryRes) getActivity().getApplication();

                TabLayout tabs = (TabLayout) getActivity().findViewById(R.id.tbl_main_content);
                int pagetabPistion= tabs.getSelectedTabPosition();

                if(pagetabPistion == 0) {
                    data.setUser_obj(json);
                    //System.out.println("onCreateView:::::::" + pagetabPistion);
                } else if (pagetabPistion == 1) {
                    data.setPage_obj(json);
                    //System.out.println("onCreateView:::::::" + pagetabPistion);
                } else if(pagetabPistion == 2) {
                    data.setEvent_obj(json);
                    //System.out.println("onCreateView:::::::" + pagetabPistion);
                } else if(pagetabPistion == 3) {
                    data.setPlace_obj(json);
                    //System.out.println("onCreateView:::::::" + pagetabPistion);
                } else if(pagetabPistion == 4) {
                    data.setGroup_obj(json);
                    //System.out.println("onCreateView:::::::" + pagetabPistion);
                }

                ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.vpg_main_content);
                viewPager.getAdapter().notifyDataSetChanged();

            }catch (JSONException e){
                e.printStackTrace();
            }
        }

    }

    private String getJSON(String url){
        StringBuilder sb = new StringBuilder();
        HttpURLConnection urlConnection = null;
        try {
            URL u = new URL(url);
            urlConnection = (HttpURLConnection) u.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String input = null;
            while ((input= reader.readLine()) != null){
                sb.append(input);
            }

            urlConnection.disconnect();

        }catch (IOException e){
            System.out.println(e.toString());
        }

        return sb.toString();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //System.out.println("onAttache--------------");
        //System.out.println("receive x from activity" + positionFromActivity);
        if (context instanceof FragmentPagerItemCallback) {
            callback = (FragmentPagerItemCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentPagerItemCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //System.out.println("onDetach--------------");
        callback = null;
    }

    public interface  FragmentPagerItemCallback {
        void onPagerItemClick(String message);

    }

    public String[] getData(int flag) {
        JSONObject json = null;

        final QueryRes qs = (QueryRes)getActivity().getApplication();
        //final QueryRes data = (QueryRes) get
        if(flag == 0) {
            json = qs.getUser_obj();
            System.out.println(json);
        }

        System.out.println(flag);
        return null;
    }




    public void getNextPage(String url) {
        //System.out.println("This is next url: " + url);
        String kw = "flag";
        client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
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
                    json = new JSONObject(htmlStr);
                    //parsePageJson(json);
                    System.out.println(json);
                } catch (Exception e) {
                    System.out.println("error again");
                }
                //parseJson(json);
                Message msg = handler.obtainMessage();
                msg.what = 1;
                handler.sendMessage(msg);
                //parsePageJson(page_json);
            }
        });
    }

    private void parsePageJson(JSONObject json) {
        listems.clear();
        //System.out.println("parsePageJson" +  json);
        try {
            nameRes = new String[json.getJSONArray("data").length()];
            idRes = new String[json.getJSONArray("data").length()];
            photoRes = new String[json.getJSONArray("data").length()];

            for(int i = 0; i < json.getJSONArray("data").length();i++){
                JSONObject record = json.getJSONArray("data").getJSONObject(i);
                nameRes[i] = record.getString("name");
                photoRes[i] = record.getJSONObject("picture").getJSONObject("data").getString("url");
                idRes[i] = record.getString("id");
                System.out.println("page"+nameRes[0]);
            }
            page_listems = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < nameRes.length; i++) {
                Map<String, Object> page_listem = new HashMap<String, Object>();
                listem.put("photo", photoRes[i]);
                listem.put("name", nameRes[i]);

                page_listems.add(page_listem);
            }

            //listems.clear();
            //listems.addAll(page_listems);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    class PageHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //TabLayout tabs = (TabLayout) getActivity().findViewById(R.id.tbl_basic);
            int what = msg.what;
            ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.vpg_main_content);
            viewPager.getAdapter().notifyDataSetChanged();
            //adapter.notifyDataSetChanged();

            System.out.println("what is:" + what);
        }
    }

}
