package com.example.apple.hw9;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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

public class ViewPagerItemFragment_copy extends Fragment {
    private static final String PAGE_TITLE = "PAGE_TITLE";
    MySimpleAdapter adapter;
    private String pageTitle;
    private FragmentPagerItemCallback callback;
    public int onSelectedPosition;
    List<Map<String, Object>> listems;
    Map<String, Object> listem;

    private OkHttpClient client;
    private Handler handler;

    public String[] idRes;
    public String[] photoRes;
    public String[] nameRes;

    Button pre_btn;
    Button next_btn;

    String pre_url = "";
    String next_url = "";

    TabLayout tab;

    public void setJson_obj(JSONObject json_obj) {
        this.json_obj = json_obj;
        //System.out.println("getjson: " + json_obj);
        parseJson(json_obj);
    }

    private void parseJson(JSONObject json) {
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
            listems = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < nameRes.length; i++) {
                listem = new HashMap<String, Object>();
                listem.put("photo", photoRes[i]);
                listem.put("name", nameRes[i]);

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

    public ViewPagerItemFragment_copy(){}

    private String[] name = { "剑萧舞蝶", "张三", "hello", "诗情画意" };
    private String[] name1 = { "蝶", "张2", "helo", "诗情画意" };
    private String[] name2 = { "蝶", "张3", "helo", "诗情画意" };
    private String[] name3 = { "蝶", "张4", "helo", "诗情画意" };
    private String[] name4 = { "蝶", "张5", "helo", "诗情画意" };

    //private String[] desc = { "魔域玩家", "百家执行", "高级的富一代", "妹子请过来..一个善于跑妹子的。。" };

    private int[] imageids = { R.drawable.albums, R.drawable.details,
            R.drawable.events, R.drawable.favorites_on };

    public static ViewPagerItemFragment_copy getInstance(String pageTitle){
        //Bundle bundle = new Bundle();

        ViewPagerItemFragment_copy fragment = new ViewPagerItemFragment_copy();

        Bundle args = new Bundle();
        args.putString(PAGE_TITLE, pageTitle);
        fragment.setArguments(args);
        System.out.println("ViewPagerItemFragment getInstance--------------" + fragment);
        //System.out.println("receive x from activity" + fragment);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreare--------------");
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
        System.out.println("onCreateView--------------");


        if(bundle != null) {
            if(bundle.getString("PAGE_TITLE") == "Users") {
                //System.out.println("onCreateView:::::::" + bundle.getString("PAGE_TITLE"));
                getResult("Users");
                /*
                listems = new ArrayList<Map<String, Object>>();
                for (int i = 0; i < name.length; i++) {
                    Map<String, Object> listem = new HashMap<String, Object>();
                    listem.put("photo", imageids[i]);
                    listem.put("name", name[i]);

                    listems.add(listem);
                }*/
            } else if (bundle.getString("PAGE_TITLE") == "Pages") {
                //System.out.println("onCreateView:::::::" + bundle.getString("PAGE_TITLE"));
                getResult("Pages");
            } else if(bundle.getString("PAGE_TITLE") == "Events") {
                getResult("Events");
                //System.out.println("onCreateView:::::::" + bundle.getString("PAGE_TITLE"));
            } else if(bundle.getString("PAGE_TITLE") == "Places") {
                getResult("Places");
                //System.out.println("onCreateView:::::::" + bundle.getString("PAGE_TITLE"));
            } else if(bundle.getString("PAGE_TITLE") == "Groups") {
                //System.out.println("onCreateView:::::::" + bundle.getString("PAGE_TITLE"));
                getResult("Groups");
            }
            //onSelectedPosition = new ViewPagerActivity().onSelectedPosition;
            //System.out.println("coooooooool" + onSelectedPosition);



            ListView res_list = ((ListView) v.findViewById(R.id.res_list));
            //getResult();
            adapter = new MySimpleAdapter(getActivity(), listems,
                    R.layout.each_item,
                    new String[]{"photo", "name"},
                    new int[]{R.id.photo, R.id.name});
            //ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.activity_list_item, tests);
            res_list.setAdapter(adapter);
            //adapter.notifyDataSetChanged();
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

    public void getResult(String str) {
        //tab = (TabLayout) getActivity().findViewById(R.id.tbl_main_content);
        //int po = tab.getSelectedTabPosition();

        JSONObject json = null;

        final QueryRes qs = (QueryRes) getActivity().getApplication();
        Activity activity = getActivity();
        pre_btn = (Button) activity.findViewById(R.id.pre_btn);
        next_btn = (Button) activity.findViewById(R.id.next_btn);
        //final QueryRes data = (QueryRes) get
        if(str == "Users") {
            json = qs.getUser_obj();
            parseJson(json);
            //json = qs.getUser_obj();
            //System.out.println("lihaile"+json);
            //System.out.println("user:--"+json);
        } else if(str == "Pages") {
            json = qs.getPage_obj();
            parseJson(json);
            //json = qs.getPage_obj();
            //System.out.println("page:--"+json);
        } else if(str == "Events") {
            json = qs.getEvent_obj();
            parseJson(json);
            //json = qs.getEvent_obj();
            //System.out.println("event:--"+json);
        } else if(str == "Places") {
            json = qs.getPlace_obj();
            parseJson(json);
            //json = qs.getPlace_obj();
            //System.out.println("place:--"+json);
        } else {
            json = qs.getGroup_obj();
            parseJson(json);
            //json = qs.getGroup_obj();
            //System.out.println("group"+json);
        }



        try {

            final JSONObject paging = (json.has("paging") ? json.getJSONObject("paging") : null);
            if(paging != null) {
                pre_btn.setEnabled(paging.has("previous"));
                next_btn.setEnabled(paging.has("next"));
                if (paging.has("previous")) {
                    pre_url = paging.getString("previous");
                }
                if(paging.has("next")) {
                    next_url = paging.getString("next");
                    //System.out.println("This is next url: " + next_url);
                }
            }

            next_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getNextPage(next_url);
                    Message msg = handler.obtainMessage();
                    msg.what = 1;
                    handler.sendMessage(msg);

                    //System.out.println("This is next url: " + next_url);
                }
            });
        } catch (Exception e) {
            System.out.println("get paging error:" + e);
        }


       // System.out.println("cooo" + po);
        //return po;
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
            listems.clear();
            listems.addAll(page_listems);
            //ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.vpg_main_content);
            //viewPager.getAdapter().notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
/*
        List<Map<String, Object>> listems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < nameRes.length; i++) {
            listem = new HashMap<String, Object>();
            listem.put("photo", photoRes[i]);
            listem.put("name", nameRes[i]);
            listems.add(listem);
            //System.out.println(photoRes[i]);
        }
        */


    }

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
        System.out.println("onAttache--------------");
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
        System.out.println("onDetach--------------");
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