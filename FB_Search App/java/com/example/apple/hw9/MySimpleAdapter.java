package com.example.apple.hw9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class MySimpleAdapter extends SimpleAdapter {
    private int[] mTo;
    private String[] mFrom;
    private ViewBinder mViewBinder;
    private List<? extends Map<String, ?>> mData;
    private int mResource;
    private int mDropDownResource;
    private LayoutInflater mInflater;


    public MySimpleAdapter(Context context,
                           List<? extends Map<String, ?>> data, int resource, String[] from,
                           int[] to) {
        super(context, data, resource, from, to);
        mData = data;
        mResource = mDropDownResource = resource;
        mFrom = from;
        mTo = to;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * @see android.widget.Adapter#getView(int, View, ViewGroup)
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, mResource);
    }

    private View createViewFromResource(int position, View convertView,
                                        ViewGroup parent, int resource) {
        View v;
        if (convertView == null) {
            v = mInflater.inflate(resource, parent, false);

            final int[] to = mTo;
            final int count = to.length;
            final View[] holder = new View[count];

            for (int i = 0; i < count; i++) {
                holder[i] = v.findViewById(to[i]);
                //System.out.println("mysimpleadapter::::::+++++" + holder[i]);
            }

            v.setTag(holder);
        } else {
            v = convertView;
        }
        bindView(position, v);

        return v;
    }

    private void bindView(int position, View view) {
        final Map dataSet = mData.get(position);

        if (dataSet == null) {
            return;
        }
        final ViewBinder binder = mViewBinder;
        final View[] holder = (View[]) view.getTag();
        final String[] from = mFrom;
        final int[] to = mTo;
        final int count = to.length;

        //for(int i = 0; i < to.length; i++) {
          //  System.out.println("this is Tooooooooooo::::"+to[i]);
        //}

        for (int i = 0; i < count; i++) {
            final View v = holder[i];
            if (v != null) {
                //System.out.println("MysimpleAdapter::" + dataSet);
                final Object data = dataSet.get(from[i]);
                //System.out.println("from::" + from[i]);
                //System.out.println("dataaaaaaaaaa::" + data);
                String text = data == null ? "" : data.toString();
                //System.out.println("it is text::" + text);
                if (text == null) {
                    text = "";
                }
                boolean bound = false;
                if (binder != null) {
                    //System.out.println("this is Tooooooooooo::::"+binder);
                    bound = binder.setViewValue(v, data, text);
                }
                if (!bound) {
                    if (v instanceof Checkable) {
                        if (data instanceof Boolean) {
                            ((Checkable) v).setChecked((Boolean) data);
                        } else {
                            throw new IllegalStateException(v.getClass().getName() +
                                    " should be bound to a Boolean, not a " + data.getClass());
                        }
                    } else if (v instanceof TextView) {
                        // Note: keep the instanceof TextView check at the bottom of these
                        // ifs since a lot of views are TextViews (e.g. CheckBoxes).
                        //setViewText((TextView) v, text);
                        TextView tx = (TextView) v.findViewById(to[i]);
                        tx.setText(text);
                        //((TextView) v).setText(text);
                    } else if (v instanceof ImageView) {
                        //System.out.println("this is data::::"+data);
                        if (data instanceof BigInteger) {
                            String id_str = data.toString();
                            if(QueryRes.fav_id_list.contains(id_str)) {
                                setViewImage((ImageView) v,R.drawable.favorites_on);
                            }
                            //System.out.println("BigInteger");
                            //setViewImage((ImageView) v, (Integer) data);
                        } else if (data instanceof String) {      //备注1
                            ImageView thumbnailImage = (ImageView) v.findViewById(to[i]);
                            String imageUrl = (String) data;
                            ImageLoader imageLoader = ImageLoader.getInstance();
                            imageLoader.displayImage(imageUrl, thumbnailImage);
                            //System.out.println("frotestjinru ::" + "hahah");
                        } else {
                            throw new IllegalStateException(v.getClass().getName() + " is not a " +
                                    " view that can be bounds by this SimpleAdapter");
                        }
                    }
                }
            } else {
                throw new IllegalStateException("lihaiel" +  " is not a " +
                        " problem");
            }
        }
    }

    public void setViewImage(ImageView v, int value) {
        v.setImageResource(value);
    }
}