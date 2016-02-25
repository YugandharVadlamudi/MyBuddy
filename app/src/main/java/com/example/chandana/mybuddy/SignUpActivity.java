package com.example.chandana.mybuddy;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by chandana on 18-01-2016.
 */
public class SignUpActivity extends Activity {
    static EditText edFname,edLname,edEmail,edPassword,edChangePassword;
    Button bvSignUP;
    TextView textViewHeaderTitle;
    ImageView iViewHeaderMenu,iViewHeaderAddContacts;
    ContentValues contentValues_Sing_up;
    static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FeedRunnerDBHelper db;
    SQLiteDatabase sqLiteDatabase;
    long checkrowInsert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_sign_up);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header_custom);
        init();
        db = new FeedRunnerDBHelper(getApplicationContext());
        Log.d("db", "" + db);
        sqLiteDatabase = db.getWritableDatabase();
        Log.d("write",""+sqLiteDatabase);
        contentValues_Sing_up = new ContentValues();
    }
    private void init() {
        edFname=(EditText)findViewById(R.id.edS_SignUp_Fname);
        edLname=(EditText)findViewById(R.id.edS_SignUp_Lname);
        edEmail=(EditText)findViewById(R.id.edS_SignUp_Email);
        edPassword=(EditText)findViewById(R.id.edS_SignUp_Password);
        edChangePassword=(EditText)findViewById(R.id.edS_SignUp_ConfirmPassword);
        bvSignUP=(Button)findViewById(R.id.bvS_signUp_Singup);
        textViewHeaderTitle=(TextView)findViewById(R.id.tv_header);
        iViewHeaderAddContacts=(ImageView)findViewById(R.id.ivH_contacts);
        iViewHeaderMenu=(ImageView)findViewById(R.id.ivH_menu);
        iViewHeaderAddContacts.setVisibility(View.INVISIBLE);
        iViewHeaderMenu.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.backiconheader));
        iViewHeaderMenu.setOnClickListener(baclick);
        textViewHeaderTitle.setText("User Registration");
    }

    public void SubmitValues(View view) {
        if(!hasContent(edFname)&&!hasContent(edLname)&&!hasContent(edEmail)&&!hasContent(edPassword)&&!hasContent(edChangePassword))
        {
            Toast.makeText(getApplicationContext(),"Enter all fields",Toast.LENGTH_SHORT).show();
        }
        else if(!hasContent(edFname))
        {
            Toast.makeText(getApplicationContext(),"Enter First Name",Toast.LENGTH_SHORT).show();
        }
        else if(!hasContent(edLname))
        {
            Toast.makeText(getApplicationContext(),"Enter Last Name",Toast.LENGTH_SHORT).show();
        }
        else if (!hasContent(edEmail))
        {
            Toast.makeText(getApplicationContext(),"Enter email id",Toast.LENGTH_SHORT).show();
        }
        else if(!hasContent(edPassword))
        {
            Toast.makeText(getApplicationContext(),"Enter password",Toast.LENGTH_SHORT).show();
        }
        else if(!hasContent(edChangePassword))
        {
            Toast.makeText(getApplicationContext(),"Enter Change password",Toast.LENGTH_SHORT).show();
        }
        else if(!hasEmailPattern(edEmail))
        {
            Toast.makeText(getApplicationContext(),"Enter valide email address",Toast.LENGTH_SHORT).show();
        }
        else if(!passwordChangePasswordMatch(edPassword,edChangePassword))
        {
            Toast.makeText(getApplicationContext(),"Password,Change Password didn't match",Toast.LENGTH_SHORT).show();
        }
        else {
            contentValues_Sing_up.put(FeedRunnerDBHelper.COLUMN_fname, edFname.getText().toString());
            contentValues_Sing_up.put(FeedRunnerDBHelper.COLUMN_lname, edLname.getText().toString());
            contentValues_Sing_up.put(FeedRunnerDBHelper.COLUMN_EMAIL, edEmail.getText().toString());
            contentValues_Sing_up.put(FeedRunnerDBHelper.COLUMN_PASSWORD, edPassword.getText().toString());
            try
            {
                checkrowInsert = sqLiteDatabase.insertOrThrow(FeedRunnerDBHelper.Table_name, null, contentValues_Sing_up);
                if (checkrowInsert != -1) {
                    Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                    Intent intent_singIn = new Intent(getApplicationContext(), MainActivity.class);
                    intent_singIn.putExtra("email", edEmail.getText().toString());
                    startActivity(intent_singIn);
                } else {
                    Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
            catch (SQLiteConstraintException e)
            {
                Toast.makeText(getApplicationContext(),"Try with another email",Toast.LENGTH_SHORT).show();
            }
        }

    }
    public static boolean hasContent(EditText ed)
    {
        boolean bHasContent = false;
        if (ed.getText().toString().trim().length() > 0) {
            bHasContent = true;
        }
        return bHasContent ;
    }
    public static boolean hasEmailPattern(EditText ed)
    {
        boolean match=false;
        if(ed.getText().toString().matches(emailPattern))
        {
            match=true;
        }
        return match;
    }
    public static boolean hasPhonenumberLength(EditText ed)
    {
        boolean match=false;
        if(ed.getText().toString().length()==10)
        {
            match=true;
        }
        return match;
    }
    public static boolean hasAgeLength(EditText ed)
    {
        boolean match=false;
        if(ed.getText().toString().length()==3)
        {
            match=true;
        }
        return match;
    }
    public static boolean phoneNumberlegth(EditText ed)
    {
        boolean match=false;
        if(ed.getText().toString().length()==10)
        {
            match=true;
        }
        return match;

    }
    public static boolean passwordChangePasswordMatch(EditText password,EditText Cpasswrord)
    {
        boolean match=false;
        if(password.getText().toString().equals(Cpasswrord.getText().toString()))
        {
            match=true;
        }
        return  match;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
    View.OnClickListener baclick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
//String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
/*SubmitValues code before modifactaion
* if(hasContent(edEmail)&&hasContent(edFname)&&hasContent(edLname)&&hasContent(edPassword)&&hasContent(edChangePassword)) {
            if(hasEmailPattern(edEmail))
            {

                contentValues_Sing_up.put(FeedRunnerDBHelper.COLUMN_fname, edFname.getText().toString());
                contentValues_Sing_up.put(FeedRunnerDBHelper.COLUMN_lname, edLname.getText().toString());
                contentValues_Sing_up.put(FeedRunnerDBHelper.COLUMN_EMAIL, edEmail.getText().toString());
                contentValues_Sing_up.put(FeedRunnerDBHelper.COLUMN_PASSWORD, edPassword.getText().toString());
                try
                {
                    checkrowInsert = sqLiteDatabase.insertOrThrow(FeedRunnerDBHelper.Table_name, null, contentValues_Sing_up);
                    if (checkrowInsert != -1) {
                        Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                        Intent intent_singIn = new Intent(getApplicationContext(), MainActivity.class);
                        intent_singIn.putExtra("email", edEmail.getText().toString());
                        startActivity(intent_singIn);
                    } else {
                        Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (SQLiteConstraintException e)
                {
                    Toast.makeText(getApplicationContext(),"Try with another email",Toast.LENGTH_SHORT).show();
                }

            }
            else
            {
                Toast.makeText(getApplicationContext(), "Enter Valid Eamil id", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "all fields mandatory", Toast.LENGTH_SHORT).show();
            edPassword.setFocusable(true);
        }
*
*
*
*
* */
