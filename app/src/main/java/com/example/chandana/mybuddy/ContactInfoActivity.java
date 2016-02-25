package com.example.chandana.mybuddy;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by chandana on 23-01-2016.
 */
public class ContactInfoActivity extends Activity {
    TextView tvName,tvAge,tvPhoneNumber,tvEmail,tvProfession,tvBloodgroup,tvWorking,tvcity,tvState,tvCountry,tvHeader;
    ImageView ivMenuHeader,ivAddContacts;
    CircularImageView circularImageView;
    FeedRunnerDBHelper feedRunnerDBHelper;
    SQLiteDatabase sqLiteDatabase;
    Intent intentContactInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_favourit_contact_info);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header_custom);
        init();
        displayData();
    }

    private void displayData() {
        String SelectcontactInfo="SELECT * FROM "+FeedRunnerDBHelper.TABLE_CONTACTS+" WHERE "+FeedRunnerDBHelper.CONTACTS_COLUMN_PHONENUMBER+"='"+intentContactInfo.getStringExtra("ActivityCInfophoneNumber")+"'";
        Cursor cursorFavouritSelect=sqLiteDatabase.rawQuery(SelectcontactInfo,null);
        if(cursorFavouritSelect!=null)
        {
            Log.d("coursorCout", "" + cursorFavouritSelect.getCount());
            cursorFavouritSelect.moveToFirst();
            tvName.setText(cursorFavouritSelect.getString(cursorFavouritSelect.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_NAME)));
            tvAge.setText(cursorFavouritSelect.getString(cursorFavouritSelect.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_AGE)));
            tvPhoneNumber.setText(cursorFavouritSelect.getString(cursorFavouritSelect.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_PHONENUMBER)));
            tvEmail.setText(cursorFavouritSelect.getString(cursorFavouritSelect.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_EMAIL)));
            tvProfession.setText(cursorFavouritSelect.getString(cursorFavouritSelect.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_PROFESSION)));
            tvBloodgroup.setText(cursorFavouritSelect.getString(cursorFavouritSelect.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_BLOODGROUP)));
            tvWorking.setText(cursorFavouritSelect.getString(cursorFavouritSelect.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_WORKING)));
            tvcity.setText(cursorFavouritSelect.getString(cursorFavouritSelect.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_CITY)));
            tvState.setText(cursorFavouritSelect.getString(cursorFavouritSelect.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_STATE)));
            tvCountry.setText(cursorFavouritSelect.getString(cursorFavouritSelect.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_COUNTRY)));
            String bitmapPath=cursorFavouritSelect.getString(cursorFavouritSelect.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_IMGPATH));
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            circularImageView.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeFile(bitmapPath, options),100,100,false));

        }

    }

    private void init() {
        tvName=(TextView)findViewById(R.id.tvFnameBlank);
        tvPhoneNumber=(TextView)findViewById(R.id.tvLnameBlank);
        tvAge=(TextView)findViewById(R.id.tvAgeBlank);
        tvEmail=(TextView)findViewById(R.id.tvEmailBlank);
        tvProfession=(TextView)findViewById(R.id.tvProfessionBlank);
        tvBloodgroup=(TextView)findViewById(R.id.tvBloodGroupblank);
        tvWorking=(TextView)findViewById(R.id.tvProfessionBlank);
        tvcity=(TextView)findViewById(R.id.tvCityBlank);
        tvState=(TextView)findViewById(R.id.tvStateBlank);
        tvCountry=(TextView)findViewById(R.id.tvCountryBlank);
        ivMenuHeader=(ImageView)findViewById(R.id.ivH_menu);
        ivAddContacts=(ImageView)findViewById(R.id.ivH_contacts);
        tvHeader=(TextView)findViewById(R.id.tv_header);
        tvHeader.setText("Contact Info");
        ivAddContacts.setVisibility(View.INVISIBLE);
        ivMenuHeader.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.backiconheader));
        ivMenuHeader.setOnClickListener(backClick);
        circularImageView=(CircularImageView)findViewById(R.id.circleFavourit);
        feedRunnerDBHelper=new FeedRunnerDBHelper(getApplicationContext());
        sqLiteDatabase=feedRunnerDBHelper.getReadableDatabase();
        intentContactInfo = getIntent();
    }
    View.OnClickListener backClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//      startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            finish();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
