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

/**
 * Created by chandana on 22-01-2016.
 */
public class ChangePasswordActivity extends Activity {
    ImageView ivMenuHeader, ivAddContactsHeader;
    TextView tvHeaderTitle, tvEmail;
    FeedRunnerDBHelper feedRunnerDBHelper;
    SQLiteDatabase sqLiteDatabase;
    EditText edUserFirst, edOldPassword,edChepassword,ednewPassword;
    Button bvProfileUpdate;
    CircularImageView profile;
    private static final int RESULT_LOAD_IMAGE =1 ;
    String picturePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_change_password);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header_custom);
        init();
/*profile.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }
});*/
    }

    private void init() {
//        edUserFirst = (EditText) findViewById(R.id.edUserName);
        edOldPassword = (EditText) findViewById(R.id.edOldPassword);
        edChepassword=(EditText)findViewById(R.id.edChangePasswrod);
        ednewPassword=(EditText)findViewById(R.id.edConformPassword);
        tvEmail = (TextView) findViewById(R.id.tvCPasswordEmail);
        tvHeaderTitle = (TextView) findViewById(R.id.tv_header);
        ivMenuHeader = (ImageView) findViewById(R.id.ivH_menu);
//        profile=(CircularImageView)findViewById(R.id.cvprofile);
        bvProfileUpdate=(Button)findViewById(R.id.bvProfileUpdateSubmit);
        ivAddContactsHeader = (ImageView) findViewById(R.id.ivH_contacts);
        ivAddContactsHeader.setVisibility(View.INVISIBLE);
        ivMenuHeader.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.backiconheader));
        ivMenuHeader.setOnClickListener(backPress);
//        ivMenuHeader.setVisibility(View.INVISIBLE);
        tvHeaderTitle.setText("Change Password");
        feedRunnerDBHelper = new FeedRunnerDBHelper(getApplicationContext());
        sqLiteDatabase = feedRunnerDBHelper.getWritableDatabase();
//        profile.setOnClickListener(backPress);
        bvProfileUpdate.setOnClickListener(SubmitUpdate);
//        displayDetails();
    }

    private void displayDetails() {
        feedRunnerDBHelper = new FeedRunnerDBHelper(getApplicationContext());
        sqLiteDatabase = feedRunnerDBHelper.getWritableDatabase();
        String userDetails="SELECT "+FeedRunnerDBHelper.COLUMN_fname+","+FeedRunnerDBHelper.COLUMN_lname+","+FeedRunnerDBHelper.COLUMN_EMAIL+","+FeedRunnerDBHelper.COLUMN_IMAGE_PATH
                +","+FeedRunnerDBHelper.COLUMN_PASSWORD
                +" FROM "+FeedRunnerDBHelper.Table_name+" WHERE "+FeedRunnerDBHelper.COLUMN_EMAIL+"=='"+MainActivity.emailPass+"';";
        Cursor cursor=sqLiteDatabase.rawQuery(userDetails,null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            edOldPassword.setText(cursor.getString(cursor.getColumnIndex(FeedRunnerDBHelper.COLUMN_lname)));
            edUserFirst.setText(cursor.getString(cursor.getColumnIndex(FeedRunnerDBHelper.COLUMN_fname)));
            tvEmail.setText(cursor.getString(cursor.getColumnIndex(FeedRunnerDBHelper.COLUMN_EMAIL)));
//            edChepassword.setText(cursor.getString(cursor.getColumnIndex(FeedRunnerDBHelper.COLUMN_PASSWORD)));
            Log.d("curcorImagePath", "" + cursor.getString(cursor.getColumnIndex(FeedRunnerDBHelper.COLUMN_IMAGE_PATH)));
            if(!cursor.getString(cursor.getColumnIndex(FeedRunnerDBHelper.COLUMN_IMAGE_PATH)).equals("NP")) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                profile.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeFile(cursor.getString(cursor.getColumnIndex(FeedRunnerDBHelper.COLUMN_IMAGE_PATH)), options), 100, 100, false));
            }
            else
            {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                profile.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.girl));
//                profile.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeFile(getApplicationContext().getResources().getDrawable(R.drawable.backiconheader), options), 100, 100, false));
            }
            Log.d("countChangePassword", "" + cursor.getCount());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

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
            profile.setImageBitmap( Bitmap.createScaledBitmap(BitmapFactory.decodeFile(picturePath, options), 100, 100, false));

        }
        bvProfileUpdate.setEnabled(true);
    }

    View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            finish();
        }
    };
    View.OnClickListener SubmitUpdate=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_SHORT).show();
            if(SignUpActivity.hasContent(edOldPassword)&&SignUpActivity.hasContent(ednewPassword)&&SignUpActivity.hasContent(edChepassword))
            {
                if(edChepassword.getText().toString().equals(ednewPassword.getText().toString())) {
                    String oldGet="SELECT "+FeedRunnerDBHelper.COLUMN_PASSWORD+" from "+FeedRunnerDBHelper.Table_name
                            +" WHERE "+FeedRunnerDBHelper.COLUMN_EMAIL+"='"+MainActivity.emailPass+"'";
Log.d("email",""+MainActivity.emailPass);
                    Cursor cusor_oldPassword=sqLiteDatabase.rawQuery(oldGet,null);
                    if(cusor_oldPassword!=null)
                    {
                        cusor_oldPassword.moveToFirst();
                        if(cusor_oldPassword.getString(cusor_oldPassword.getColumnIndex(FeedRunnerDBHelper.COLUMN_PASSWORD)).equals(edOldPassword.getText().toString()))
                        {
                            ContentValues contentValues=new ContentValues();
                            contentValues.put(FeedRunnerDBHelper.COLUMN_PASSWORD,edChepassword.getText().toString());
                            int rowUpdateCheck=sqLiteDatabase.update(FeedRunnerDBHelper.Table_name,contentValues,FeedRunnerDBHelper.COLUMN_EMAIL+"='"+MainActivity.emailPass+"'",null);
                            if(rowUpdateCheck!=0)
                            {
                                Toast.makeText(getApplicationContext(),"Password Successfully Changed",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Password didn't Changed",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Old password did not match",Toast.LENGTH_SHORT).show();
                        }
                        Log.d("getPassword",""+cusor_oldPassword.getString(cusor_oldPassword.getColumnIndex(FeedRunnerDBHelper.COLUMN_PASSWORD)));
                    }
                    else {
                        Log.d("getPassword","no rowsfound");
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"new password and confirm password did not match",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(),"all fields mandatory",Toast.LENGTH_SHORT).show();
            }

        }
    };
}
/*
*  String updateUserDetails = "UPDATE " + FeedRunnerDBHelper.Table_name + " SET " + FeedRunnerDBHelper.COLUMN_lname
                        + "='" + edUserLastName.getText().toString() + "'" + "," + FeedRunnerDBHelper.COLUMN_fname + "=" + edUserFirst.getText().toString()
                        + "," + FeedRunnerDBHelper.COLUMN_IMAGE_PATH + "=";
                ContentValues contentValues = new ContentValues();
                contentValues.put(FeedRunnerDBHelper.COLUMN_lname, edUserLastName.getText().toString());
                contentValues.put(FeedRunnerDBHelper.COLUMN_fname, edUserLastName.getText().toString());
                contentValues.put(FeedRunnerDBHelper.COLUMN_IMAGE_PATH, picturePath);
                contentValues.put(FeedRunnerDBHelper.COLUMN_PASSWORD, edChepassword.getText().toString());
                Log.d("path", "" + picturePath);
                int rowupdate = sqLiteDatabase.update(FeedRunnerDBHelper.Table_name, contentValues, FeedRunnerDBHelper.COLUMN_EMAIL + "=='" + MainActivity.emailPass + "'", null);
                if (rowupdate != 0) {
                    Toast.makeText(getApplicationContext(), "row updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "row not updated", Toast.LENGTH_SHORT).show();
                }

* */