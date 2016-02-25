package com.example.chandana.mybuddy;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by chandana on 23-01-2016.
 */
public class FrameFragment extends FragmentActivity {
    TextView textViewHeader, textViewContent;
    ImageView backIcon, favouritIcon, ivDial, smsScreen, emailcompose;
    RelativeLayout rlFavourit, rlContactInfo;
    ViewPager viewPager;
    public static ArrayList<HashMap<String, String>> rgt;
    public static int listviewposition;
    String phonenumberDial;
    String email;
    FeedRunnerDBHelper fd;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpagerswipe);
        init();
    }

    private void init() {
        fd = new FeedRunnerDBHelper(getApplicationContext());
        sqLiteDatabase = fd.getWritableDatabase();
        textViewHeader = (TextView) findViewById(R.id.tvViewPagerTitle);
        textViewContent = (TextView) findViewById(R.id.tvSwipeContent);
        backIcon = (ImageView) findViewById(R.id.backIcon);
        rlFavourit = (RelativeLayout) findViewById(R.id.rl_favourit);
        rlContactInfo = (RelativeLayout) findViewById(R.id.Rl_contactinfo);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        favouritIcon = (ImageView) findViewById(R.id.iv_favouit);
        ivDial = (ImageView) findViewById(R.id.ivDialView);
        smsScreen = (ImageView) findViewById(R.id.smsDial);
        emailcompose=(ImageView)findViewById(R.id.ivemailCompose);
        Intent iFragmentget = getIntent();
        listviewposition = iFragmentget.getIntExtra("position", 0);
        Log.d("positionlist", "" + listviewposition);
        rgt = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("ArrayList");
//        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        viewPager.setAdapter(new CustomPagerAdapter());
        viewPager.setCurrentItem(listviewposition);
        HashMap<String, String> done = rgt.get(FrameFragment.listviewposition);
        textViewHeader.setText(done.get("Name"));
        phonenumberDial = done.get("phonenumber");
        Cursor c = listviewOnCreate(listviewposition);
        ivDial.setOnClickListener(call_dial);
        if (c != null) {
            c.moveToFirst();
            String HtmlForm = "I work as <font color=#87CEFA>"
                    + c.getString(c.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_WORKING))
                    + "</font>"
                    + " and live in "
                    + "<font color=#87CEFA>"
                    + c.getString(c.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_CITY))
                    + ","
                    + "</font>"
                    + "<font color=#87CEFA>"
                    + c.getString(c.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_STATE))
                    + "</font>";
            textViewContent.setText(Html.fromHtml(HtmlForm));
            email = c.getString(c.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_EMAIL));
            if (c.getString(c.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_FAVOURIT)).equals("F")) {
                favouritIcon.setImageDrawable(getResources().getDrawable(R.drawable.favourit_yellow));
            } else {
                favouritIcon.setImageDrawable(getResources().getDrawable(R.drawable.favourit_icon_round));
            }

        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("positionArray", "" + position);
                FrameFragment.listviewposition = position;
                HashMap<String, String> h = rgt.get(FrameFragment.listviewposition);
                textViewHeader.setText(h.get("Name"));
                Log.d("lkdjfalsdjfkla", "" + FrameFragment.listviewposition);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    String[] Columns = new String[]{FeedRunnerDBHelper.CONTACTS_COLUMN_FAVOURIT
                            , FeedRunnerDBHelper.CONTACTS_COLUMN_PHONENUMBER
                            , FeedRunnerDBHelper.CONTACTS_COLUMN_EMAIL
                            , FeedRunnerDBHelper.CONTACTS_COLUMN_CITY
                            , FeedRunnerDBHelper.CONTACTS_COLUMN_STATE
                            , FeedRunnerDBHelper.CONTACTS_COLUMN_WORKING};
                    Cursor cursorSelectFavout = sqLiteDatabase.query(FeedRunnerDBHelper.TABLE_CONTACTS, Columns, FeedRunnerDBHelper.CONTACTS_COLUMN_PHONENUMBER + "='" + rgt.get(FrameFragment.listviewposition).get("phonenumber")+"'", null, null, null, null, null);
//                    Cursor cursorSelectFavout = sqLiteDatabase.query(FeedRunnerDBHelper.TABLE_CONTACTS, new String[]{FeedRunnerDBHelper.CONTACTS_COLUMN_FAVOURIT}, FeedRunnerDBHelper.CONTACTS_COLUMN_PHONENUMBER + "=" + rgt.get(FrameFragment.listviewposition).get("phonenumber"), null, null, null, null, null);
                    if (cursorSelectFavout != null) {
                        cursorSelectFavout.moveToFirst();
                        Log.d("favouritCount", "" + cursorSelectFavout.getCount());
                        Log.d("favourit", "" + cursorSelectFavout.getString(cursorSelectFavout.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_FAVOURIT)));
                        String fCheck = cursorSelectFavout.getString(cursorSelectFavout.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_FAVOURIT));
                        if (fCheck.equals("F")) {
                            favouritIcon.setImageDrawable(getResources().getDrawable(R.drawable.favourit_yellow));
                        } else {
                            favouritIcon.setImageDrawable(getResources().getDrawable(R.drawable.favourit_icon_round));
                        }
                        String HtmlForm = "I work as <font color=#87CEFA>"
                                + cursorSelectFavout.getString(cursorSelectFavout.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_WORKING))
                                + "</font>"
                                + " and live in "
                                + "<font color=#87CEFA>"
                                + cursorSelectFavout.getString(cursorSelectFavout.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_CITY))
                                + ","
                                + "</font>"
                                + "<font color=#87CEFA>"
                                + cursorSelectFavout.getString(cursorSelectFavout.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_STATE))
                                + "</font>";
                        textViewContent.setText(Html.fromHtml(HtmlForm));
                        phonenumberDial = cursorSelectFavout.getString(cursorSelectFavout.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_PHONENUMBER));
                        email = cursorSelectFavout.getString(cursorSelectFavout.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_EMAIL));
                    }
                }


            }

        });
        Log.d("position", "" + HomeActivity.positionRowData);
        rlFavourit.setOnClickListener(rlFClick);
        rlContactInfo.setOnClickListener(rlContactInfoClick);
        smsScreen.setOnClickListener(sms_dial);
        emailcompose.setOnClickListener(email_compose);
        backIcon.setOnClickListener(back_activity);
    }

    private Cursor listviewOnCreate(int p0) {
        String[] contactDetails = new String[]{
                FeedRunnerDBHelper.CONTACTS_COLUMN_PHONENUMBER, FeedRunnerDBHelper.CONTACTS_COLUMN_EMAIL
                , FeedRunnerDBHelper.CONTACTS_COLUMN_WORKING,
                FeedRunnerDBHelper.CONTACTS_COLUMN_CITY,
                FeedRunnerDBHelper.CONTACTS_COLUMN_STATE,
                FeedRunnerDBHelper.CONTACTS_COLUMN_FAVOURIT
        };
        return sqLiteDatabase.query(FeedRunnerDBHelper.TABLE_CONTACTS, contactDetails, FeedRunnerDBHelper.CONTACTS_COLUMN_PHONENUMBER + "='" + rgt.get(p0).get("phonenumber")+"'", null, null, null, null);

    }

    class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return rgt.size();
        }

        @Override
        public Fragment getItem(int pos) {
            ImageViewSwipeFragment i = new ImageViewSwipeFragment();
            return i;
        }

    }

    class CustomPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return rgt.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (ImageView) object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = LayoutInflater.from(FrameFragment.this).inflate(R.layout.view_pager_item, container, false);
            ImageView ivPic = (ImageView) itemView.findViewById(R.id.ivPic);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            ivPic.setImageBitmap(BitmapFactory.decodeFile(rgt.get(position).get("imagePath"), options));
            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView) object);
        }
    }

    View.OnClickListener rlFClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            Toast.makeText(getApplicationContext(), "" + FrameFragment.listviewposition, Toast.LENGTH_SHORT).show();
            String UpDateFavourit = "UPDATE " + FeedRunnerDBHelper.TABLE_CONTACTS + " SET " + FeedRunnerDBHelper.CONTACTS_COLUMN_FAVOURIT + "='F'"
                    + " WHERE " + FeedRunnerDBHelper.CONTACTS_COLUMN_PHONENUMBER + "='" + rgt.get(FrameFragment.listviewposition).get("phonenumber");
            ContentValues contentUpdate = new ContentValues();
            contentUpdate.put(FeedRunnerDBHelper.CONTACTS_COLUMN_FAVOURIT, "F");
            int rowUpdateFavourit = sqLiteDatabase.update(FeedRunnerDBHelper.TABLE_CONTACTS, contentUpdate, FeedRunnerDBHelper.CONTACTS_COLUMN_PHONENUMBER + "='" + rgt.get(FrameFragment.listviewposition).get("phonenumber")+"'", null);
            if (rowUpdateFavourit != 0) {
                Toast.makeText(getApplicationContext(), "Contact successfully favourite", Toast.LENGTH_SHORT).show();
                favouritIcon.setImageDrawable(getResources().getDrawable(R.drawable.favourit_yellow));

            } else {
                Toast.makeText(getApplicationContext(), "Contact un successfull favourite", Toast.LENGTH_SHORT).show();
            }
        }
    };
    View.OnClickListener rlContactInfoClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), ContactInfoActivity.class).putExtra("ActivityCInfophoneNumber", rgt.get(FrameFragment.listviewposition).get("phonenumber")));
        }
    };
    View.OnClickListener call_dial = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            Toast.makeText(getApplicationContext(), "" + phonenumberDial, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phonenumberDial)));
        }
    };
    View.OnClickListener sms_dial = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phonenumberDial)));
        }
    };
    View.OnClickListener email_compose = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + email)));
        }
    };
    View.OnClickListener back_activity=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            finish();
        }
    };

    @Override
    public void onBackPressed() {

    }
}
