package com.example.chandana.mybuddy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by chandana on 19-01-2016.
 */
public class ContactRegistrationActivity extends Activity {
    private static final int RESULT_LOAD_IMAGE =1 ;
    private static final int REQUEST_CAMERA =1 ;
    EditText edName,edAge,edPhoneNumber,edEmail,edProfession,edBloodGroup,edWorking,edCity,edState,edCountry;
    Button bvSubmit;
    //    ImageView ivCircle;
    CircularImageView ivCircle;
    Bitmap bm;
    FeedRunnerDBHelper feedRunnerDBHelper;
    ContentValues contentValues_Contacts_table;
    SQLiteDatabase sqLiteDatabase;
    String picturePath="";
    static long checkInsertContact;
    private static final int SELECT_PICTURE = 1;
    TextView tvTitle;
    ImageView imageMenu,imageAddContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedRunnerDBHelper=new FeedRunnerDBHelper(getApplicationContext());
        sqLiteDatabase=feedRunnerDBHelper.getWritableDatabase();
        contentValues_Contacts_table=new ContentValues();
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_contacts);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header_custom);
        init();
        ivCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
                AlertDialog.Builder builder=new AlertDialog.Builder(ContactRegistrationActivity.this);
                builder.setTitle("Add Photot");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which].equals("Take Photo")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, REQUEST_CAMERA);

                        } else if (items[which].equals("Choose from Library")) {
                            Intent i = new Intent(
                                    Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(i, RESULT_LOAD_IMAGE);
                        }
                        else if (items[which].equals("Cancle")){
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();

            }
        });
    }

    private void init() {
        edName=(EditText)findViewById(R.id.edS_Contacts_Name);
        edAge=(EditText)findViewById(R.id.edS_Contacts_Age);
        edPhoneNumber=(EditText)findViewById(R.id.edS_Contacts_Number);
        edEmail=(EditText)findViewById(R.id.edS_Contacts_Email);
        edProfession=(EditText)findViewById(R.id.edS_Contacts_profession);
        edBloodGroup=(EditText)findViewById(R.id.edS_Contacts_Blood_group);
        edWorking=(EditText)findViewById(R.id.edS_Contacts_Working);
        edCity=(EditText)findViewById(R.id.edS_Contacts_city);
        edState=(EditText)findViewById(R.id.edS_Contacts_state);
        edCountry=(EditText)findViewById(R.id.edS_Contacts_country);
        ivCircle=(CircularImageView)findViewById(R.id.ivS_Contacts_circle1);
        bvSubmit=(Button)findViewById(R.id.bvS_Contacts_Submit);
        tvTitle=(TextView)findViewById(R.id.tv_header);
        imageAddContact=(ImageView)findViewById(R.id.ivH_contacts);
        imageMenu=(ImageView)findViewById(R.id.ivH_menu);
        imageAddContact.setVisibility(View.INVISIBLE);
        imageMenu.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.backiconheader));
        tvTitle.setText("Contact Registration");
        imageMenu.setOnClickListener(backClick);
        Log.d("string",""+MainActivity.emailPass);
       /* Intent emailInsert=getIntent();
        Log.d("emailInsert",emailInsert.getStringExtra("emailInsert"));
*/
    }
    public void contactsSubmit(View view) {
        if(SignUpActivity.hasContent(edName)&&SignUpActivity.hasContent(edAge)&&SignUpActivity.hasContent(edPhoneNumber)&&SignUpActivity.hasContent(edEmail)&&SignUpActivity.hasContent(edProfession)&&SignUpActivity.hasContent(edBloodGroup)&&SignUpActivity.hasContent(edWorking)&&SignUpActivity.hasContent(edCity)&&SignUpActivity.hasContent(edName)&&SignUpActivity.hasContent(edState)&&SignUpActivity.hasContent(edCountry)&&!picturePath.equals("")) {
            if(SignUpActivity.hasEmailPattern(edEmail)&&SignUpActivity.phoneNumberlegth(edPhoneNumber)) {
                contentValues_Contacts_table.put(FeedRunnerDBHelper.CONTACTS_COLUMN_NAME, edName.getText().toString());
                contentValues_Contacts_table.put(FeedRunnerDBHelper.CONTACTS_COLUMN_AGE, edAge.getText().toString());
                contentValues_Contacts_table.put(FeedRunnerDBHelper.CONTACTS_COLUMN_PHONENUMBER, edPhoneNumber.getText().toString());
                contentValues_Contacts_table.put(FeedRunnerDBHelper.CONTACTS_COLUMN_EMAIL, edEmail.getText().toString());
                contentValues_Contacts_table.put(FeedRunnerDBHelper.CONTACTS_COLUMN_PROFESSION, edProfession.getText().toString());
                contentValues_Contacts_table.put(FeedRunnerDBHelper.CONTACTS_COLUMN_BLOODGROUP, edBloodGroup.getText().toString());
                contentValues_Contacts_table.put(FeedRunnerDBHelper.CONTACTS_COLUMN_WORKING, edWorking.getText().toString());
                contentValues_Contacts_table.put(FeedRunnerDBHelper.CONTACTS_COLUMN_CITY, edCity.getText().toString());
                contentValues_Contacts_table.put(FeedRunnerDBHelper.CONTACTS_COLUMN_STATE, edState.getText().toString());
                contentValues_Contacts_table.put(FeedRunnerDBHelper.CONTACTS_COLUMN_COUNTRY, edCountry.getText().toString());
                contentValues_Contacts_table.put(FeedRunnerDBHelper.CONTACTS_COLUMN_NAME, edName.getText().toString());
                    contentValues_Contacts_table.put(FeedRunnerDBHelper.CONTACTS_COLUMN_IMGPATH, picturePath);
                contentValues_Contacts_table.put(FeedRunnerDBHelper.CONTACTS_COLUMN_USERID,MainActivity.emailPass);
//                Intent emailInsert=getIntent();
//                contentValues_Contacts_table.put(FeedRunnerDBHelper.CONTACTS_COLUMN_USERID,emailInsert.getStringExtra("emailInsert"));
                try {
                    checkInsertContact = sqLiteDatabase.insertOrThrow(FeedRunnerDBHelper.TABLE_CONTACTS, null, contentValues_Contacts_table);
                    if (checkInsertContact != -1) {
                        Toast.makeText(getApplicationContext(), "Contact registered", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Contact didn't registered", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (SQLiteConstraintException e)
                {
                    Toast.makeText(getApplicationContext(),"Try with another Phone number",Toast.LENGTH_SHORT).show();
                }

            }
            else {
                Toast.makeText(getApplicationContext(), "enter valid email or valid phone number ", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "all fields mandatory", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //region imageview displayt
        Log.d("requsetCode","reqCode"+requestCode+":resCode"+resultCode);
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
            ivCircle.setImageBitmap( Bitmap.createScaledBitmap(BitmapFactory.decodeFile(picturePath, options), 100, 100, false));
        }

        //endregion
    }
    View.OnClickListener backClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    @Override
    public void onBackPressed() {

    }
}
