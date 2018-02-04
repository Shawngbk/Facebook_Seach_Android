package com.example.apple.hw9;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by apple on 17/4/19.
 */

public class QueryRes extends Application {
    private static Context context;
    private JSONObject user_obj;
    private JSONObject page_obj;
    private JSONObject event_obj;
    private JSONObject place_obj;
    private JSONObject group_obj;
    public static ArrayList<String> fav_id_list = new ArrayList<>();
    public static List<Map<String, Object>> user_fav_list = new ArrayList<>();
    public static List<Map<String, Object>> page_fav_list = new ArrayList<>();
    public static List<Map<String, Object>> event_fav_list = new ArrayList<>();
    public static List<Map<String, Object>> place_fav_list = new ArrayList<>();
    public static List<Map<String, Object>> group_fav_list = new ArrayList<>();

    /*
    public ArrayList<String> getFav_id_list() {
        return fav_id_list;
    }

    public void setFav_id_list(ArrayList<String> fav_id_list) {
        this.fav_id_list = fav_id_list;
        System.out.println("QSSSSSS test fav_id_list:::" + fav_id_list);
    }
*/


    public JSONObject getDetail_obj() {
        return detail_obj;
    }

    public void setDetail_obj(JSONObject detail_obj) {
        this.detail_obj = detail_obj;
    }

    private JSONObject detail_obj;


    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        QueryRes.context = context;
    }

    public JSONObject getPlace_obj() {
        return place_obj;
    }

    public void setPlace_obj(JSONObject place_obj) {
        this.place_obj = place_obj;
    }

    public JSONObject getGroup_obj() {
        return group_obj;
    }

    public void setGroup_obj(JSONObject group_obj) {
        this.group_obj = group_obj;
    }



    public JSONObject getEvent_obj() {
        return event_obj;
    }

    public void setEvent_obj(JSONObject event_obj) {
        this.event_obj = event_obj;
    }



    public JSONObject getUser_obj() {
        return user_obj;
    }

    public void setUser_obj(JSONObject user_obj) {
        this.user_obj = user_obj;
    }

    public JSONObject getPage_obj() {
        return page_obj;
    }

    public void setPage_obj(JSONObject page_obj) {
        this.page_obj = page_obj;
    }

    @Override
    public void onCreate() {

        user_obj = null;
        page_obj = null;
        event_obj = null;
        place_obj = null;
        group_obj = null;
        detail_obj = null;
        //posts = null;
        //albums = null;

        super.onCreate();
        context = getApplicationContext();
        //initImageLoader(context);

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app


        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    public static void initImageLoader(Context context) {

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs()
                .build();

        ImageLoader.getInstance().init(config);
    }
   // {"user_obj":null,"page_obj":null,"event_obj":null,"place_obj":null,"group_obj":null}
}
