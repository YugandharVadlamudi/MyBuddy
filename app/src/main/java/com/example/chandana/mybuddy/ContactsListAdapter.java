package com.example.chandana.mybuddy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by chandana on 19-01-2016.
 */
public class ContactsListAdapter extends BaseAdapter {
    ArrayList<HashMap<String, String>> h;
    Context context;
    LayoutInflater inflater;

    public ContactsListAdapter(Context context, ArrayList hashmap) {
        this.context = context;
        h = hashmap;
        inflater = LayoutInflater.from(this.context);
    }
    public void againInflate(ArrayList hashmap)
    {
        h=hashmap;
    }

    @Override
    public int getCount() {
        return h.size();
    }

    @Override
    public HashMap getItem(int position) {
        return h.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder h;
        if (convertView == null) {
            convertView=inflater.inflate(R.layout.listviewstuff,parent,false);
            h=new Holder();
            h.Name=(TextView)convertView.findViewById(R.id.tv_ll_contactname);
            h.PhoneNumber=(TextView)convertView.findViewById(R.id.tv_ll_contactnumber);
            h.imageView=(ImageView)convertView.findViewById(R.id.ll_circle_imageView);
            convertView.setTag(h);
        }
        else {
            h=(Holder)convertView.getTag();
        }
        HashMap<String,String> hashMap=getItem(position);
        h.Name.setText(hashMap.get("Name"));
        h.PhoneNumber.setText(hashMap.get("phonenumber"));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        if(!hashMap.get("imagePath").equals("NP")) {
            h.imageView.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeFile(hashMap.get("imagePath"), options), 80, 80, false));
        }
        else
        {
            h.imageView.setImageResource(R.drawable.girl);
        }
        return convertView;
    }

    class Holder {
        TextView Name, PhoneNumber;
        ImageView imageView;
    }
}
