package com.example.chandana.mybuddy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.HashMap;

/**
 * Created by chandana on 23-01-2016.
 */
public class ImageViewSwipeFragment extends Fragment {
    ImageView imageViewSwipe;
    HashMap<String,String> list;
    public ImageViewSwipeFragment() {
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_imageswipe,container,false);
        imageViewSwipe=(ImageView)v.findViewById(R.id.ivFavouritSwipe);
        Log.d("fragmentPosition",""+FrameFragment.listviewposition);
        list=FrameFragment.rgt.get(FrameFragment.listviewposition);
        Log.d("listname",""+list.get("Name"));
        Log.d("string", list.get(("imagePath")));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        imageViewSwipe.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeFile(list.get(("imagePath")), options), 80, 80, false));

        return v;
    }


}
/*public ImageViewSwipeFragment( imageViewSwipe) {
        this.imageViewSwipe = imageViewSwipe;
    }*/
  /*if(HomeActivity.rowData.size()>HomeActivity.positionRowData)
        {
            HashMap<String,String>h=HomeActivity.rowData.get(HomeActivity.positionRowData++);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            imageViewSwipe.setImageBitmap( BitmapFactory.decodeFile(h.get("imagePath")));
        }
        else if(HomeActivity.rowData.size()==HomeActivity.positionRowData+1)
        {

            HashMap<String,String>h=HomeActivity.rowData.get(HomeActivity.positionRowData);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            imageViewSwipe.setImageBitmap( BitmapFactory.decodeFile(h.get("imagePath")));
        }*/
//        HashMap<String,String>h=HomeActivity.rowData.get(HomeActivity.positionRowData++);
