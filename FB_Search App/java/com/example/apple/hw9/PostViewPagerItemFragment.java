package com.example.apple.hw9;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;

/**
 * Created by apple on 17/4/19.
 */

public class PostViewPagerItemFragment extends Fragment {
    private static final String PAGE_TITLE = "PAGE_TITLE";
    MySimpleAdapter adapter;
    //MyAlbumSimpleAdapter album_adapter;
    private String pageTitle;
    private FragmentPagerItemCallback callback;
    public int onSelectedPosition;
    List<Map<String, Object>> listems;
    Map<String, Object> listem;

    ExpandableListView album_list;
    MyAlbumSimpleAdapter listAdapter;

    private OkHttpClient client;
    private Handler handler;

    public String[] idRes;
    public String[] photoRes;
    public String[] nameRes;
    public String[] timeRes;
    public String[] msgRes;
    public String[] album_name;
    public String[] first_pic;
    public String[] second_pic;
    public String s1 = "https://graph.facebook.com/v2.8/";
    public String s2 = "/picture?&access_token=EAARKJzroutgBAM3uNwiXXQZAN2pSbSrunOnZCuhun9nlkk7ZBG2Sc4x1k66DDrpZCFRDMXkBCgEHTEiZAuZATrKe7b0MWNnVnMOJ3NSDqVhsO3vLkSIsfVScpkzKES1RwxAlmwhxX4tm8ZBttJd21DZBg4oC11TnZALoZD";
    JSONObject detailJson;

    List<String> listTitle;
    HashMap<String, List<String>> listTopic;

    public static String event_id;
    public static String event_name;
    public static String event_url;


    TabLayout tab;

    public void setJson_obj(JSONObject json_obj) {
        this.json_obj = json_obj;
        //System.out.println("getjson: " + json_obj);
        parseJson(json_obj);
    }

    private void parseJson(JSONObject json) {
        JSONObject parseDetailJson = json;
        try {
            String parseid = parseDetailJson.getString("id");
            String parsephoto = parseDetailJson.getJSONObject("picture").getJSONObject("data").getString("url");
            String parsename = parseDetailJson.getString("name");
            if(detailJson.has("posts")) {
                idRes = new String[parseDetailJson.getJSONObject("posts").getJSONArray("data").length()];
                photoRes = new String[parseDetailJson.getJSONObject("posts").getJSONArray("data").length()];
                nameRes = new String[parseDetailJson.getJSONObject("posts").getJSONArray("data").length()];
                timeRes = new String[parseDetailJson.getJSONObject("posts").getJSONArray("data").length()];
                msgRes = new String[parseDetailJson.getJSONObject("posts").getJSONArray("data").length()];

                JSONObject postJson = parseDetailJson.getJSONObject("posts");

                for(int i = 0; i < parseDetailJson.getJSONObject("posts").getJSONArray("data").length(); i++) {
                    idRes[i] = parseid;
                    photoRes[i] = parsephoto;
                    nameRes[i] = parsename;
                    if(postJson.getJSONArray("data").getJSONObject(i).has("created_time")) {
                        String temp_time = postJson.getJSONArray("data").getJSONObject(i).getString("created_time");
                        String exact_time = temp_time.substring(0, 10) + " " + temp_time.substring(11, 19);
                        timeRes[i] = exact_time;
                    }
                    if(postJson.getJSONArray("data").getJSONObject(i).has("message")) {
                        msgRes[i] = postJson.getJSONArray("data").getJSONObject(i).getString("message");
                    }

                }
            }
            if(detailJson.has("albums")) {
               album_name = new String[parseDetailJson.getJSONObject("albums").getJSONArray("data").length()];
                first_pic = new String[parseDetailJson.getJSONObject("albums").getJSONArray("data").length()];
                second_pic = new String[parseDetailJson.getJSONObject("albums").getJSONArray("data").length()];

                JSONObject albumJson = parseDetailJson.getJSONObject("albums");
                for(int i = 0; i < parseDetailJson.getJSONObject("albums").getJSONArray("data").length(); i++) {
                    if(albumJson.getJSONArray("data").getJSONObject(i).has("name")) {
                        album_name[i] = albumJson.getJSONArray("data").getJSONObject(i).getString("name");
                    }
                    if(albumJson.getJSONArray("data").getJSONObject(i).has("photos")) {
                        if(albumJson.getJSONArray("data").getJSONObject(i)
                                .getJSONObject("photos").getJSONArray("data").getJSONObject(0).has("id")) {
                            String first_url = s1 + albumJson.getJSONArray("data").getJSONObject(i)
                                    .getJSONObject("photos").getJSONArray("data").getJSONObject(0).getString("id") + s2;
                            first_pic[i] = first_url;
                        }
                        if(albumJson.getJSONArray("data").getJSONObject(i)
                                .getJSONObject("photos").getJSONArray("data").length() == 2) {
                            String second_url = s1 + albumJson.getJSONArray("data").getJSONObject(i)
                                    .getJSONObject("photos").getJSONArray("data").getJSONObject(1).getString("id") + s2;

                            second_pic[i] = second_url;
                        }
                    }

                }
            }
            //----------------------------------------------------

//            idRes = new String[parseDetailJson.getJSONObject("posts").getJSONArray("data").length()];
//            photoRes = new String[parseDetailJson.getJSONObject("posts").getJSONArray("data").length()];
//            nameRes = new String[parseDetailJson.getJSONObject("posts").getJSONArray("data").length()];
//            timeRes = new String[parseDetailJson.getJSONObject("posts").getJSONArray("data").length()];
//            msgRes = new String[parseDetailJson.getJSONObject("posts").getJSONArray("data").length()];
//            album_name = new String[parseDetailJson.getJSONObject("albums").getJSONArray("data").length()];
//            first_pic = new String[parseDetailJson.getJSONObject("albums").getJSONArray("data").length()];
//            second_pic = new String[parseDetailJson.getJSONObject("albums").getJSONArray("data").length()];
//            JSONObject postJson = parseDetailJson.getJSONObject("posts");
//            JSONObject albumJson = parseDetailJson.getJSONObject("albums");
//            for(int i = 0; i < parseDetailJson.getJSONObject("posts").getJSONArray("data").length(); i++) {
//                idRes[i] = parseid;
//                photoRes[i] = parsephoto;
//                nameRes[i] = parsename;
//                if(postJson.getJSONArray("data").getJSONObject(i).has("created_time")) {
//                    timeRes[i] = postJson.getJSONArray("data").getJSONObject(i).getString("created_time");
//                }
//                if(postJson.getJSONArray("data").getJSONObject(i).has("message")) {
//                    msgRes[i] = postJson.getJSONArray("data").getJSONObject(i).getString("message");
//                }
//
//                if(albumJson.getJSONArray("data").getJSONObject(i).has("name")) {
//                    album_name[i] = albumJson.getJSONArray("data").getJSONObject(i).getString("name");
//                }
//                if(albumJson.getJSONArray("data").getJSONObject(i)
//                        .getJSONObject("photos").getJSONArray("data").getJSONObject(0).has("id")) {
//                    String first_url = s1 + albumJson.getJSONArray("data").getJSONObject(i)
//                            .getJSONObject("photos").getJSONArray("data").getJSONObject(0).getString("id") + s2;
//                    first_pic[i] = first_url;
//                }
//                if(albumJson.getJSONArray("data").getJSONObject(i)
//                        .getJSONObject("photos").getJSONArray("data").length() == 2) {
//                    String second_url = s1 + albumJson.getJSONArray("data").getJSONObject(i)
//                            .getJSONObject("photos").getJSONArray("data").getJSONObject(1).getString("id") + s2;
//
//                    second_pic[i] = second_url;
//                }
//            }
//            for(int i = 0; i < parseDetailJson.getJSONObject("posts").getJSONArray("data").length(); i++) {
//
//            }
            //System.out.println("phote:::"+ idRes + photoRes);
            //System.out.println("msg::::::::"+ msgRes[2]);
            listTitle = new ArrayList<>();
            listTopic = new HashMap<>();
            List<String> album1 = new ArrayList<>();
            List<String> album2 = new ArrayList<>();
            List<String> album3 = new ArrayList<>();
            List<String> album4 = new ArrayList<>();
            List<String> album5 = new ArrayList<>();
            if(album_name.length == 1) {
                album1.add(first_pic[0]); album1.add(second_pic[0]);
            } else if(album_name.length == 2) {
                album1.add(first_pic[0]); album1.add(second_pic[0]);
                album2.add(first_pic[1]); album2.add(second_pic[1]);
            } else if(album_name.length == 3) {
                album1.add(first_pic[0]); album1.add(second_pic[0]);
                album2.add(first_pic[1]); album2.add(second_pic[1]);
                album3.add(first_pic[2]); album3.add(second_pic[2]);
            } else if(album_name.length == 4) {
                album1.add(first_pic[0]); album1.add(second_pic[0]);
                album2.add(first_pic[1]); album2.add(second_pic[1]);
                album3.add(first_pic[2]); album3.add(second_pic[2]);
                album4.add(first_pic[3]); album4.add(second_pic[3]);
            } else if(album_name.length == 5) {
                album1.add(first_pic[0]); album1.add(second_pic[0]);
                album2.add(first_pic[1]); album2.add(second_pic[1]);
                album3.add(first_pic[2]); album3.add(second_pic[2]);
                album4.add(first_pic[3]); album4.add(second_pic[3]);
                album5.add(first_pic[4]); album5.add(second_pic[4]);
            }

            for(int i = 0; i < album_name.length; i++) {
                listTitle.add(album_name[i]);
            }
            if(album_name.length == 1) {
                listTopic.put(album_name[0], album1);
            } else if(album_name.length == 2) {
                listTopic.put(album_name[0], album1);
                listTopic.put(album_name[1], album2);
            } else if(album_name.length == 3) {
                listTopic.put(album_name[0], album1);
                listTopic.put(album_name[1], album2);
                listTopic.put(album_name[2], album3);
            } else if(album_name.length == 4) {
                listTopic.put(album_name[0], album1);
                listTopic.put(album_name[1], album2);
                listTopic.put(album_name[2], album3);
                listTopic.put(album_name[3], album4);
            } else if(album_name.length == 5) {
                listTopic.put(album_name[0], album1);
                listTopic.put(album_name[1], album2);
                listTopic.put(album_name[2], album3);
                listTopic.put(album_name[3], album4);
                listTopic.put(album_name[4], album5);
            }



            listems = new ArrayList<Map<String, Object>>();
            if(timeRes != null) {
                for (int i = 0; i < timeRes.length; i++) {
                    listem = new HashMap<String, Object>();
                    listem.put("photo", photoRes[i]);
                    listem.put("name", nameRes[i]);
                    listem.put("id", idRes[i]);
                    listem.put("time",timeRes[i]);
                    listem.put("message",msgRes[i]);
                    //listem.put("album_name", album_name[i]);
                    //listem.put("pic1", first_pic[i]);
                    //listem.put("pic2", second_pic[i]);
                    listems.add(listem);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*
        try {
            nameRes = new String[json.getJSONArray("data").length()];
            idRes = new String[json.getJSONArray("data").length()];
            photoRes = new String[json.getJSONArray("data").length()];

            for(int i = 0; i < json.getJSONArray("data").length();i++){
                JSONObject record = json.getJSONArray("data").getJSONObject(i);
                nameRes[i] = record.getString("name");
                photoRes[i] = record.getJSONObject("picture").getJSONObject("data").getString("url");
                idRes[i] = record.getString("id");
                //System.out.println(photoRes[i]);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }

    private JSONObject json_obj;

    public void setPositionFromActivity(int positionFromActivity) {
        this.positionFromActivity = positionFromActivity;
    }





    public int positionFromActivity;




    private String type;

    public PostViewPagerItemFragment(){}

    private String[] name = { "剑萧舞蝶", "张三", "hello", "诗情画意" };
    private String[] name1 = { "蝶", "张2", "helo", "诗情画意" };
    private String[] name2 = { "蝶", "张3", "helo", "诗情画意" };
    private String[] name3 = { "蝶", "张4", "helo", "诗情画意" };
    private String[] name4 = { "蝶", "张5", "helo", "诗情画意" };

    //private String[] desc = { "魔域玩家", "百家执行", "高级的富一代", "妹子请过来..一个善于跑妹子的。。" };

    private int[] imageids = { R.drawable.albums, R.drawable.details,
            R.drawable.events, R.drawable.favorites_on };

    public static PostViewPagerItemFragment getInstance(String pageTitle){
        //Bundle bundle = new Bundle();

        PostViewPagerItemFragment fragment = new PostViewPagerItemFragment();

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
        //System.out.println("onCreare--------------"+ type);
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
        //TabLayout tabs = (TabLayout) getActivity().findViewById(R.id.tbl_main_content);
//        int pagetabPistion= tabs.getSelectedTabPosition();

        View v;
        //View v = inflater.inflate(R.layout.album_activity_res, container, false);
        //v = inflater.inflate(R.layout.post_activity_res_listview, container, false);
        // test();
        Bundle bundle = this.getArguments();
        //System.out.println("onCreateView--------------" + bundle.getString("PAGE_TITLE"));
        final QueryRes qs = (QueryRes) getActivity().getApplication();
        detailJson = qs.getDetail_obj();
        //parseJson(detailJson);
        System.out.println("prepare detail:::"+ detailJson);
        //if(bundle != null) {
            if(bundle.getString("PAGE_TITLE") == "Albums") {
                if(detailJson.has("albums")) {
                    v = inflater.inflate(R.layout.album_activity_res, container, false);
                    parseJson(detailJson);
                    album_list = (ExpandableListView) v.findViewById(R.id.expandableListView);
                    listAdapter = new MyAlbumSimpleAdapter(getContext(), listTitle, listTopic);
                    album_list.setAdapter(listAdapter);
                    album_list.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                        @Override
                        public void onGroupExpand(int groupPosition) {
                            for (int i = 0, count = listAdapter.getGroupCount(); i < count; i++) {
                                if (groupPosition != i) {// 关闭其他分组
                                    album_list.collapseGroup(i);
                                }
                            }
                        }
                    });
                /*
                album_adapter = new MyAlbumSimpleAdapter(getActivity(), listems,
                        R.layout.post_each_item,
                        new String[]{"album_name", "pic1","pic2"},
                        new int[]{R.id.txtParent, R.id.first_child, R.id.second_child});*/
                    //ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.activity_list_item, tests);

                } else {
                    v = inflater.inflate(R.layout.no_detail, container, false);
                    TextView tx = (TextView) v.findViewById(R.id.no_detail);
                    tx.setText("No albums available to display");
                }


            } else {
                if(detailJson.has("posts")) {
                    v = inflater.inflate(R.layout.post_activity_res_listview, container, false);
                    parseJson(detailJson);
                    //parseCommonJson();

/*
                listems = new ArrayList<Map<String, Object>>();
                for (int i = 0; i < name.length; i++) {
                    listem = new HashMap<String, Object>();
                    listem.put("photo", imageids[i]);
                    listem.put("name", name[i]);

                    listems.add(listem);
                }
*/

                    ListView post_list = ((ListView) v.findViewById(R.id.post_list));
                    //getResult();
                    adapter = new MySimpleAdapter(getActivity(), listems,
                            R.layout.post_each_item,
                            new String[]{"photo", "name","time","message"},
                            new int[]{R.id.post_photo, R.id.post_name, R.id.post_time, R.id.post_msg});
                    //ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.activity_list_item, tests);
                    post_list.setAdapter(adapter);
                } else {
                    v = inflater.inflate(R.layout.no_detail, container, false);
                    TextView tx = (TextView) v.findViewById(R.id.no_detail);
                    tx.setText("No posts available to display");
                }

            }
            //onSelectedPosition = new ViewPagerActivity().onSelectedPosition;
            //System.out.println("coooooooool" + onSelectedPosition);

            //adapter.notifyDataSetChanged();
        //}

        return v;
    }

    public void parseCommonJson() {
        listems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < name.length; i++) {
            listem = new HashMap<String, Object>();
            listem.put("photo", imageids[i]);
            listem.put("name", name[i]);

            listems.add(listem);
        }
    }


/*
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
                JSONObject page_json = null;
                try {
                    page_json = new JSONObject(htmlStr);
                    //parsePageJson(json);
                    //System.out.println(page_json);
                } catch (Exception e) {
                    System.out.println("error again");
                }
                parsePageJson(page_json);
            }
        });
    }

    private void parsePageJson(JSONObject json) {
        //listems.clear();
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
                //System.out.println(photoRes[i]);
            }
            ArrayList<Map<String, Object>> page_listems = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < nameRes.length; i++) {
                Map<String, Object> page_listem = new HashMap<String, Object>();
                listem.put("photo", photoRes[i]);
                listem.put("name", nameRes[i]);

                page_listems.add(page_listem);
            }
            //listems.clear();
            //listems.addAll(page_listems);
            //ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.vpg_main_content);
            //viewPager.getAdapter().notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
*/
    class PageHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //TabLayout tabs = (TabLayout) getActivity().findViewById(R.id.tbl_basic);
            int what = msg.what;
            //ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.vpg_main_content);
            //viewPager.getAdapter().notifyDataSetChanged();
            //adapter.notifyDataSetChanged();

            System.out.println("what is:" + what);
        }
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
}