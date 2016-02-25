package com.example.chandana.mybuddy;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by chandana on 23-01-2016.
 */
public class FavouritContactList extends Activity {
    ListView llFavourit;
    FeedRunnerDBHelper feedRunnerDBHelper;
    SQLiteDatabase sqLiteDatabase;
    ContactsListAdapter contactsListAdapter;
    TextView tvHeader;
    ImageView imageViewmenu,imageViewaddContacts;
    ArrayList<HashMap<String, String>> rowDataFavourit = new ArrayList<HashMap<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_favourit_listview);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header_custom);
        setContentView(R.layout.activity_favourit_listview);
        init();
    }

    private void init() {
        tvHeader=(TextView)findViewById(R.id.tv_header);
        imageViewmenu=(ImageView)findViewById(R.id.ivH_menu);
        imageViewaddContacts=(ImageView)findViewById(R.id.ivH_contacts);
        imageViewaddContacts.setVisibility(View.INVISIBLE);
imageViewmenu.setOnClickListener(backClick);
        imageViewmenu.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.backiconheader));
        tvHeader.setText("Favourites");
        llFavourit=(ListView)findViewById(R.id.Favoutlistview);
        FavouritListArray();
        ContactsListAdapter cFavouritList=new ContactsListAdapter(getApplicationContext(),rowDataFavourit);
        llFavourit.setAdapter(cFavouritList);
        llFavourit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv=(TextView)view.findViewById(R.id.tv_ll_contactnumber);
                if(tv.getText().toString().length()>0)
                {
                    Log.d("tvnumber",tv.getText().toString());
                    startActivity(new Intent(getApplicationContext(), ContactInfoActivity.class).putExtra("ActivityCInfophoneNumber",tv.getText().toString()));
                }


            }
        });
    }

    private void FavouritListArray() {
        String Favourit="SELECT "+FeedRunnerDBHelper.CONTACTS_COLUMN_IMGPATH+","+FeedRunnerDBHelper.CONTACTS_COLUMN_NAME+","+FeedRunnerDBHelper.CONTACTS_COLUMN_PHONENUMBER
                +" FROM "+FeedRunnerDBHelper.TABLE_CONTACTS+" WHERE "+FeedRunnerDBHelper.CONTACTS_COLUMN_USERID+"='"+MainActivity.emailPass+"'"
                +" AND "+FeedRunnerDBHelper.CONTACTS_COLUMN_FAVOURIT+"='F'";
        feedRunnerDBHelper=new FeedRunnerDBHelper(getApplicationContext());
        sqLiteDatabase=feedRunnerDBHelper.getReadableDatabase();
        Cursor cursorFavourit=sqLiteDatabase.rawQuery(Favourit,null);
       if(cursorFavourit!=null)
       {
           Log.d("FavouritGEtCOunt",""+cursorFavourit.getCount());
           if(cursorFavourit.getCount()==1)
           {
                cursorFavourit.moveToFirst();
               HashMap<String,String> h=new HashMap<String,String>();
               h.put("imagePath", cursorFavourit.getString(cursorFavourit.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_IMGPATH)));
               h.put("Name", cursorFavourit.getString(cursorFavourit.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_NAME)));
               h.put("phonenumber", cursorFavourit.getString(cursorFavourit.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_PHONENUMBER)));
               rowDataFavourit.add(h);
           }
           else if (cursorFavourit.getCount()>0)
           {
               while (cursorFavourit.moveToNext())
               {
                   HashMap<String,String> h=new HashMap<String,String>();
                   h.put("imagePath", cursorFavourit.getString(cursorFavourit.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_IMGPATH)));
                   h.put("Name", cursorFavourit.getString(cursorFavourit.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_NAME)));
                   h.put("phonenumber", cursorFavourit.getString(cursorFavourit.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_PHONENUMBER)));
                    rowDataFavourit.add(h);
               }
           }
       }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public void onBackPressed() {

    }

    View.OnClickListener backClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            finish();
        }
    };

}
