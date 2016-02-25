package com.example.chandana.mybuddy;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;
/**
 * Created by chandana on 22-01-2016.
 */
public class UserProfileActivity extends Activity {
    private static final int RESULT_LOAD_IMAGE =1 ;
    TextView tvUserName,tvLastName,tvEmail,tvHeaderTitle;
    Button submitUpdate;
    EditText edUserName,edLastName,edEmail;
    FeedRunnerDBHelper feedRunnerDBHelper;
    SQLiteDatabase sqliteDbUserProfile;
    Intent iget;
    ImageView ivHeaderMenu,ivHeaderAddContacts;
    CircularImageView profilePicture;
    String picturePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_userprofile);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header_custom);
        init();
        displaydata();
        ivHeaderMenu.setOnClickListener(backHomeActivity);
    }

    private void displaydata() {
        Log.d("name", "" + iget.getStringExtra("userName"));
        String sqlQueryProfile="SELECT "+FeedRunnerDBHelper.COLUMN_fname+","+FeedRunnerDBHelper.COLUMN_lname+","+FeedRunnerDBHelper.COLUMN_EMAIL
                +","+FeedRunnerDBHelper.COLUMN_IMAGE_PATH
                +" FROM "+FeedRunnerDBHelper.Table_name+" WHERE "+FeedRunnerDBHelper.COLUMN_EMAIL+"=='"+iget.getStringExtra("userName")+"'";
      feedRunnerDBHelper=new FeedRunnerDBHelper(getApplicationContext());
        sqliteDbUserProfile=feedRunnerDBHelper.getReadableDatabase();
        Cursor cursorProfile=sqliteDbUserProfile.rawQuery(sqlQueryProfile, null);
        if(cursorProfile!=null)
        {
            Log.d("getcount", "" + cursorProfile.getCount());
           cursorProfile.moveToFirst();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            edEmail.setText(cursorProfile.getString(cursorProfile.getColumnIndex(FeedRunnerDBHelper.COLUMN_EMAIL)));
            edLastName.setText(cursorProfile.getString(cursorProfile.getColumnIndex(FeedRunnerDBHelper.COLUMN_lname)));
            edUserName.setText(cursorProfile.getString(cursorProfile.getColumnIndex(FeedRunnerDBHelper.COLUMN_fname)));
            if(!cursorProfile.getString(cursorProfile.getColumnIndex(FeedRunnerDBHelper.COLUMN_IMAGE_PATH)).equals("NP")) {
                profilePicture.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeFile(cursorProfile.getString(cursorProfile.getColumnIndex(FeedRunnerDBHelper.COLUMN_IMAGE_PATH)), options), 100, 100, false));
            }
            else
            {
                profilePicture.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.girl));
//                profile.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeFile(getApplicationContext().getResources().getDrawable(R.drawable.backiconheader), options), 100, 100, false));
            }
        }
        cursorProfile.close();
    }
    private void init() {
        edUserName=(EditText)findViewById(R.id.edProflieFirstName);
        edLastName=(EditText)findViewById(R.id.edProfileLastName);
//        tvEmail=(TextView)findViewById(R.id.tvProfileEmail);
        edEmail=(EditText)findViewById(R.id.tvProfileEmail);
        ivHeaderMenu=(ImageView)findViewById(R.id.ivH_menu);
        ivHeaderAddContacts=(ImageView)findViewById(R.id.ivH_contacts);
        profilePicture=(CircularImageView)findViewById(R.id.ivProfilePecture);
        submitUpdate=(Button)findViewById(R.id.bvsubmitUpdate);
        iget = getIntent();
//        tvEmail.setText(iget.getStringExtra("userName"));
        edEmail.setText(iget.getStringExtra("userName"));
        tvHeaderTitle=(TextView)findViewById(R.id.tv_header);
        Log.d("header",""+tvHeaderTitle);
        tvHeaderTitle.setText("User Profile");
        ivHeaderAddContacts.setVisibility(View.INVISIBLE);
        ivHeaderMenu.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.backiconheader));
        submitUpdate.setOnClickListener(updateDatabase);
        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public void onBackPressed() {

    }
    View.OnClickListener backHomeActivity=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            finish();
        }
    };
    View.OnClickListener updateDatabase=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(SignUpActivity.hasContent(edUserName)&&SignUpActivity.hasContent(edLastName))
            {
                ContentValues updateColumns=new ContentValues();
                updateColumns.put( FeedRunnerDBHelper.COLUMN_fname,edUserName.getText().toString());
                updateColumns.put(FeedRunnerDBHelper.COLUMN_lname,edLastName.getText().toString());
                if(picturePath!=null)
                {
                    updateColumns.put(FeedRunnerDBHelper.COLUMN_IMAGE_PATH,picturePath);
                }
//                updateColumns.put(FeedRunnerDBHelper.COLUMN_IMAGE_PATH,);
//                updateColumns.put(FeedRunnerDBHelper.COLUMN_IMAGE_PATH,);
                int rowUpdateCheck=sqliteDbUserProfile.update(FeedRunnerDBHelper.Table_name,updateColumns,FeedRunnerDBHelper.COLUMN_EMAIL+"='"+iget.getStringExtra("userName")+"'",null);
                if(rowUpdateCheck!=0)
                {
                    Toast.makeText(getApplicationContext(),"Profile Updated",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Profile didn't Updated",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(),"First name or Last name is empty",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            Log.d("imagpath", "" + selectedImage);
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            Log.d("path", "" + picturePath);
            cursor.close();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            profilePicture.setImageBitmap( Bitmap.createScaledBitmap(BitmapFactory.decodeFile(picturePath, options), 100, 100, false));
        }
    }
}
