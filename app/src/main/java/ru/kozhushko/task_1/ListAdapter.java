package ru.kozhushko.task_1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ListAdapter extends BaseAdapter {

    private final String BaseURL="http://mobevo.ext.terrhq.ru/";
    private ArrayList<ItemInfo> list;
    private LayoutInflater layoutInflater;
    private Downloader loader;
    private JSONObject json=null;
    Context context;

    private final String TECHNOLOGY="technology";
    private final String TITLE="title";
    private final String INFO="info";
    private final String PICTURE="picture";

    public ListAdapter(Context context, String JSONString) {
        layoutInflater = LayoutInflater.from(context);
        loader= Downloader.getInstance();
        list= new ArrayList<ItemInfo>();
        try {
            json=new JSONObject(JSONString);
            paresJSON();
        } catch (JSONException e) {
            Log.e("ERROR!","Json Error");
        }
    }

    private void paresJSON() throws JSONException{
        try {
            json=json.getJSONObject(TECHNOLOGY);
            Iterator keys=json.keys();
            while(keys.hasNext()) {
                String currentKey = (String)keys.next();
                JSONObject current = json.getJSONObject(currentKey);
                ItemInfo info=new ItemInfo();
                if (current.has(INFO))
                    info.setInfo(current.getString(INFO));
                if (current.has(PICTURE))
                    info.setPictureURL(BaseURL+current.getString(PICTURE));
                if (current.has(TITLE))
                    info.setTitle(current.getString(TITLE));
                list.add(info);
            }
            Log.d("d","end parse");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView = layoutInflater.inflate(R.layout.list_item, null);
            holder=new ViewHolder();
            holder.img= (ImageView) convertView.findViewById(R.id.imageView);
            holder.title= (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        } else{
            holder=(ViewHolder)convertView.getTag();
        }
        ItemInfo itemInfo=list.get(position);
        holder.title.setText(itemInfo.getTitle());
        loader.setImage(holder.img,itemInfo.getPictureURL());
        return convertView;
    }

    private class ViewHolder{
        ImageView img;
        TextView title;
    }
}
