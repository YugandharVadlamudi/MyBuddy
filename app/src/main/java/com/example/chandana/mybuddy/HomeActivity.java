package com.example.chandana.mybuddy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by chandana on 18-01-2016.
 */
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * Created by chandana on 18-01-2016.
 */
public class HomeActivity extends Activity implements View.OnClickListener {
    ImageView menu, contacts,profile,favourit;
    ListView llContactsList;
    FeedRunnerDBHelper feedRunnerDBHelper;
    SQLiteDatabase sqLiteDatabase;
    /* static*/   ArrayList<HashMap<String, String>> rowData = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> rowfullData = new ArrayList<HashMap<String, String>>();
    //    ArrayList<ArrayList>rowfullData=new ArrayList<ArrayList>();
    ArrayList<HashMap<String, String>> dummy = new ArrayList<HashMap<String, String>>();
    String[] data;
    SwipeRefreshLayout swipeRefreshLayout;
    ContactsListAdapter c;
    AdapterView.OnItemClickListener listviewClick;
    Intent getEmail;
    LinearLayout llBGTransprant;
    public static int positionRowData;
    FrameFragment f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_home);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header_custom);
        feedRunnerDBHelper = new FeedRunnerDBHelper(getApplicationContext());
//        feedRunnerDBHelper.deleteRows();
        sqLiteDatabase = feedRunnerDBHelper.getReadableDatabase();
        llContactsList = (ListView) findViewById(R.id.ll_contacts);
        menu = (ImageView) findViewById(R.id.ivH_menu);
        contacts = (ImageView) findViewById(R.id.ivH_contacts);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.sw_list_View);
        getEmail = getIntent();
        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEmail = getIntent();
                startActivity(new Intent(getApplicationContext(), ContactRegistrationActivity.class).putExtra("emailInsert",getEmail.getStringExtra("userName")));
            }
        });
        listviewDisplay();
        c = new ContactsListAdapter(getApplicationContext(), rowData);
        llContactsList.setAdapter(c);
        llContactsList.setOnItemClickListener(listviewClick);
        llContactsList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if (llContactsList != null && llContactsList.getChildCount() > 0) {
                    boolean firstItemvisible = llContactsList.getFirstVisiblePosition() == 0;
                    boolean topOfFirstItemVisible = llContactsList.getChildAt(0).getTop() == 0;
                    enable = firstItemvisible && topOfFirstItemVisible;
                }
                swipeRefreshLayout.setEnabled(enable);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
               /* String Select_contact_list = "SELECT " + FeedRunnerDBHelper.CONTACTS_COLUMN_IMGPATH + "," + FeedRunnerDBHelper.CONTACTS_COLUMN_NAME + "," + FeedRunnerDBHelper.CONTACTS_COLUMN_PHONENUMBER
                        + " FROM " + FeedRunnerDBHelper.TABLE_CONTACTS + " WHERE rowid==" + ContactRegistrationActivity.checkInsertContact
                        + " AND " + FeedRunnerDBHelper.CONTACTS_COLUMN_USERID + "=='" + MainActivity.emailPass + "'";
                Cursor cursor_contactList = sqLiteDatabase.rawQuery(Select_contact_list, null);
                if (cursor_contactList != null) {
                    while (cursor_contactList.moveToNext()) {
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("imagePath", cursor_contactList.getString(cursor_contactList.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_IMGPATH)));
                        hashMap.put("Name", cursor_contactList.getString(cursor_contactList.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_NAME)));
                        hashMap.put("phonenumber", cursor_contactList.getString(cursor_contactList.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_PHONENUMBER)));
//                rowData.add(hashMap);
//                rowfullData.add(rowData);
                        rowData.add(hashMap);
                        Log.d("done", "" + cursor_contactList.getString(cursor_contactList.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_PHONENUMBER)));
                    }
                    ContactRegistrationActivity.checkInsertContact = 000;
                }
                if (llContactsList.getCount() == 0) {
                    llContactsList.setAdapter(new ContactsListAdapter(HomeActivity.this, rowData));
                } else {
                    c.notifyDataSetChanged();
                }*/

            }
        });
        /*
        * image onclick listener
        * */
        menu.setOnClickListener(this);
        /*
        * listview onitem click listener
        * */
       /* llContactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private void listviewDisplay() {
        String Select_contact_list = "SELECT " + FeedRunnerDBHelper.CONTACTS_COLUMN_IMGPATH + "," + FeedRunnerDBHelper.CONTACTS_COLUMN_NAME + "," + FeedRunnerDBHelper.CONTACTS_COLUMN_PHONENUMBER
                + " FROM " + FeedRunnerDBHelper.TABLE_CONTACTS + " WHERE " /*rowid!=+ ContactRegistrationActivity.checkInsertContact*/
                +/* " AND " +*/ FeedRunnerDBHelper.CONTACTS_COLUMN_USERID + "='" + MainActivity.emailPass + "'";
//                /*+" AND "*/+FeedRunnerDBHelper.CONTACTS_COLUMN_USERID+"=='"+getEmail.getStringExtra("userName")+"'";
        Cursor cursor_contactList = sqLiteDatabase.rawQuery(Select_contact_list, null);
        if (cursor_contactList != null) {
            while (cursor_contactList.moveToNext()) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("imagePath", cursor_contactList.getString(cursor_contactList.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_IMGPATH)));
                hashMap.put("Name", cursor_contactList.getString(cursor_contactList.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_NAME)));
                hashMap.put("phonenumber", cursor_contactList.getString(cursor_contactList.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_PHONENUMBER)));
                rowData.add(hashMap);
//                rowfullData.add(rowData);
//                rowfullData.add(hashMap);
                Log.d("phone", "" + cursor_contactList.getString(cursor_contactList.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_IMGPATH)));
            }
            Log.e(" size is ", "" + rowData.size());
        } else {
            Log.e(" cousor  is ", "" + rowData.size());
        }


//        cursor_contactList.close();
//        for(int rowdatalength=0;rowdatalength<rowData.size();rowdatalength++)
//        {
//            HashMap<String,String> d=rowData.get(rowdatalength);
//            Log.d("imagpath,name",""+d.get("imagePath")+d.get("Name")+d.get("phonenumber"));
//        }
    }


    @Override
    public void onClick(View v) {
        final Dialog dialog = new Dialog(HomeActivity.this);
        dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogmenu);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(true);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().getAttributes().height = ViewGroup.LayoutParams.FILL_PARENT;
        dialog.show();
        llBGTransprant=(LinearLayout)dialog.findViewById(R.id.llMenuBGTransparnt);
        llBGTransprant.setBackgroundColor(Color.parseColor("#CC7F7F7F"));
        profile=(ImageView)dialog.findViewById(R.id.ivMenuProfile);
        final LinearLayout rlFavourit = (LinearLayout) dialog.findViewById(R.id.rlMenuFavourit);
        final LinearLayout rlProfile = (LinearLayout) dialog.findViewById(R.id.rlMenuProfile);
        final LinearLayout rlLogout = (LinearLayout) dialog.findViewById(R.id.rlMenuLogout);
        final LinearLayout rlCPassword=(LinearLayout)dialog.findViewById(R.id.rlMenuChangePassword);
        final Animation rlFavouritAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideup);
        final Animation rlFavouritAnimationSlideup = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideupout);
        rlProfile.startAnimation(rlFavouritAnimation);
//        rlProfile.startAnimation(rlFavouritAnimation);
        rlFavouritAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideup);
                animation.setStartOffset(200);
                rlFavourit.startAnimation(animation);
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideup);
                animation.setStartOffset(300);
                rlCPassword.startAnimation(animation);
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideup);
                animation.setStartOffset(400);
                rlLogout.startAnimation(animation);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        rlProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideupout);
                rlProfile.startAnimation(slideUp);
                slideUp.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideupout);
                        animation.setStartOffset(200);
                        rlFavourit.startAnimation(animation);
                        Animation animationCPassword = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideupout);
                        animationCPassword.setStartOffset(250);
                        rlCPassword.startAnimation(animationCPassword);
                        Animation animLogoutSlideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideupout);
                        animLogoutSlideUp.setStartOffset(300);
                        rlLogout.startAnimation(animLogoutSlideUp);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                       /* animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideupout);
                        animation.setStartOffset(200);
                        rlFavourit.startAnimation(animation);
                        Animation animLogoutSlideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideupout);
                        animLogoutSlideUp.setStartOffset(400);
                        rlLogout.startAnimation(animLogoutSlideUp);*/
                        dialog.dismiss();
                        startActivity(new Intent(getApplicationContext(), UserProfileActivity.class).putExtra("userName", MainActivity.emailPass));
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
//                startActivity(new Intent(getApplicationContext(), UserProfileActivity.class).putExtra("userName", MainActivity.emailPass));
            }
        });
        rlFavourit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation slideUpFavourit=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slideupout);
                rlProfile.startAnimation(slideUpFavourit);
                slideUpFavourit.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideupout);
                        animation.setStartOffset(200);
                        rlFavourit.startAnimation(animation);
                        Animation animationCPassword=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slideupout);
                        animationCPassword.setStartOffset(250);
                        rlCPassword.startAnimation(animationCPassword);
                        Animation animLogoutSlideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideupout);
                        animLogoutSlideUp.setStartOffset(300);
                        rlLogout.startAnimation(animLogoutSlideUp);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        dialog.dismiss();
                        startActivity(new Intent(getApplicationContext(),FavouritContactList.class));
//                        Toast.makeText(getApplicationContext(),"animation end",Toast.LENGTH_SHORT).show();
                        /*
                        * start activity here
                        * */
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
        rlLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation slideUpoutLogout=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slideupout);
                rlProfile.startAnimation(slideUpoutLogout);
                slideUpoutLogout.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideupout);
                        animation.setStartOffset(200);
                        rlFavourit.startAnimation(animation);
                        Animation animationCPassword=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slideupout);
                        animationCPassword.setStartOffset(250);
                        rlCPassword.startAnimation(animationCPassword);
                        Animation animLogoutSlideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideupout);
                        animLogoutSlideUp.setStartOffset(300);
                        rlLogout.startAnimation(animLogoutSlideUp);
                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        dialog.dismiss();
                        new AlertDialog.Builder(HomeActivity.this)
                                .setMessage("Are you sure you want to exit?")
                                .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();

//                        startActivity(new Intent(getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
        rlCPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation slideUpoutCPassword=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slideupout);
                rlProfile.startAnimation(slideUpoutCPassword);
                slideUpoutCPassword.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideupout);
                        animation.setStartOffset(200);
                        rlFavourit.startAnimation(animation);
                        Animation animationCPassword=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slideupout);
                        animationCPassword.setStartOffset(250);
                        rlCPassword.startAnimation(animationCPassword);
                        Animation animLogoutSlideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideupout);
                        animLogoutSlideUp.setStartOffset(300);
                        rlLogout.startAnimation(animLogoutSlideUp);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        dialog.dismiss();
                        startActivity(new Intent(getApplicationContext(),ChangePasswordActivity.class));
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });


    }

    {
        listviewClick = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                positionRowData = position;
                TextView tvPhoneNumber = (TextView) view.findViewById(R.id.tv_ll_contactnumber);
                /*Intent intentSingleContactActivity = new Intent(getApplicationContext(), SingleContactActivity.class);
                intentSingleContactActivity.putExtra("PhoneNumber", tvPhoneNumber.getText());
                startActivity(intentSingleContactActivity);*/
                Intent iFragment=new Intent(getApplicationContext(),FrameFragment.class);
                iFragment.putExtra("ArrayList",rowData);
                iFragment.putExtra("position",position);
                startActivity(iFragment);
//                startActivity(new Intent(getApplicationContext(),FrameFragment.class));
                Log.d("phonenumber", "" + tvPhoneNumber.getText());
//            Toast.makeText(getApplicationContext(),""+tvPhoneNumber.getText().toString(),Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public void onBackPressed() {
    }

}
