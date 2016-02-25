package com.example.chandana.mybuddy;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by chandana on 21-01-2016.
 */
public class SingleContactActivity extends Activity {
    ImageView ivbackGround,ivHeaderMenu,ivHeaderAddcontact;
    TextView textView,tvHeaderTitle;
    FeedRunnerDBHelper feedRunnerDBHelper;
    SQLiteDatabase sqLiteDatabase;
    Intent ExtractSingleContactActivity;
    RelativeLayout rlFavouritClick;
    LinearLayout llheaderBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_singlecontact);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header_custom);
        ExtractSingleContactActivity = getIntent();
        init();
        setTextImage();
        ivHeaderMenu.setOnClickListener(backClick);
    }

    private void setTextImage() {
        String queryContacts="SELECT "+FeedRunnerDBHelper.CONTACTS_COLUMN_IMGPATH+","+FeedRunnerDBHelper.CONTACTS_COLUMN_NAME+" From "
                +FeedRunnerDBHelper.TABLE_CONTACTS+" WHERE "+FeedRunnerDBHelper.CONTACTS_COLUMN_PHONENUMBER+"='"+ExtractSingleContactActivity.getStringExtra("PhoneNumber")+"'";
        Cursor cursorContacts=sqLiteDatabase.rawQuery(queryContacts,null);
        if(cursorContacts!=null)
        {
            Log.i("intent", "" + ExtractSingleContactActivity.getStringExtra("PhoneNumber"));
            Log.i("columncount",""+cursorContacts.getColumnCount());
            Log.i("rocount",""+cursorContacts.getCount());
            cursorContacts.moveToFirst();
//            Log.d("imagepath", "" + cursorContacts.getString(cursorContacts.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_IMGPATH)));
//            Log.d("TextView",""+cursorContacts.getString(cursorContacts.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_NAME)));
            tvHeaderTitle.setText(cursorContacts.getString(cursorContacts.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_NAME)));
//           textView.setText(cursorContacts.getString(cursorContacts.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_NAME)));
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            ivbackGround.setImageBitmap(BitmapFactory.decodeFile(cursorContacts.getString(cursorContacts.getColumnIndex(FeedRunnerDBHelper.CONTACTS_COLUMN_IMGPATH))));
//            h.imageView.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeFile(hashMap.get("imagePath"), options), 80, 80, false));
        }
       /* backGround.setImageDrawable();
        textView.setText();*/
    }
    private void init() {
        ivbackGround=(ImageView)findViewById(R.id.ivContactImage);
        textView=(TextView)findViewById(R.id.tvContactName);
        tvHeaderTitle=(TextView)findViewById(R.id.tv_header);
        ivHeaderMenu=(ImageView)findViewById(R.id.ivH_menu);
        ivHeaderAddcontact=(ImageView)findViewById(R.id.ivH_contacts);
        rlFavouritClick=(RelativeLayout)findViewById(R.id.rlFavouritSingleContact);
        rlFavouritClick.setOnClickListener(favouritUpdate);
        feedRunnerDBHelper=new FeedRunnerDBHelper(getApplicationContext());
        sqLiteDatabase=feedRunnerDBHelper.getReadableDatabase();
        ivHeaderMenu.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.backiconheader));
//        ivHeaderMenu.setVisibility(View.INVISIBLE);
        ivHeaderAddcontact.setVisibility(View.INVISIBLE);
        llheaderBackground=(LinearLayout)findViewById(R.id.llheader);
        llheaderBackground.setBackgroundColor(Color.parseColor("#0022C098"));
    }

    @Override
    public void onBackPressed() {

    }
    View.OnClickListener backClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
    View.OnClickListener favouritUpdate=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ContentValues contentValues=new ContentValues();
            contentValues.put(FeedRunnerDBHelper.CONTACTS_COLUMN_FAVOURIT,"F");
            int rowEffect=sqLiteDatabase.update(FeedRunnerDBHelper.TABLE_CONTACTS,contentValues,FeedRunnerDBHelper.CONTACTS_COLUMN_PHONENUMBER+"="+ExtractSingleContactActivity.getStringExtra("PhoneNumber"),null);
            if(rowEffect!=0)
            {
                Toast.makeText(getApplicationContext(),"row Updated",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
            else {
                Toast.makeText(getApplicationContext(),"row not Updated",Toast.LENGTH_SHORT).show();
            }
        }
    };
}
