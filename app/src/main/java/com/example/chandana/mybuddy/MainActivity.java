package com.example.chandana.mybuddy;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {
    Button bvSignIn;
    static EditText edEmail, edPassword;
    FeedRunnerDBHelper db_one;
    TextView tVHeader,bvSignUp ;
    ImageView iVwAddContacts,iVMenu;
    SQLiteDatabase sqLiteDatabase;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    static String emailPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header_custom);
        init();
        //region getting email
        Intent intent_signup = getIntent();
        if (intent_signup != null) {
            edEmail.setText(intent_signup.getStringExtra("email"));
        }
        //endregion
    }

    private void init() {
        bvSignUp = (TextView) findViewById(R.id.bvS_SingIn_SignUP);
        bvSignIn = (Button) findViewById(R.id.bvS_SignIn_SignIn);
        edPassword = (EditText) findViewById(R.id.edS_Login_Password);
        edEmail = (EditText) findViewById(R.id.edS_Login_Email);
        tVHeader=(TextView)findViewById(R.id.tv_header);
        iVwAddContacts=(ImageView)findViewById(R.id.ivH_contacts);
        iVMenu=(ImageView)findViewById(R.id.ivH_menu);
        iVwAddContacts.setVisibility(View.INVISIBLE);
        iVMenu.setVisibility(View.INVISIBLE);
        tVHeader.setText("Login");
        bvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singnUP();
            }
        });
    }

    public void singnUP() {
//        Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
    }
    public void SingIn(View view) {

        if(!SignUpActivity.hasContent(edEmail))
        {
            Toast.makeText(getApplicationContext(),"Enter email id",Toast.LENGTH_SHORT).show();
        }
        else if(!SignUpActivity.hasContent(edPassword))
        {
            Toast.makeText(getApplicationContext(),"Enter Password",Toast.LENGTH_SHORT).show();
        }
        else if(!SignUpActivity.hasEmailPattern(edEmail))
        {
            Toast.makeText(getApplicationContext(),"Enter Valid email",Toast.LENGTH_SHORT).show();
        }

        else {
            db_one = new FeedRunnerDBHelper(getApplicationContext());
            sqLiteDatabase = db_one.getReadableDatabase();
            String login_email = "SELECT EMAIL FROM " + FeedRunnerDBHelper.Table_name + " WHERE " + FeedRunnerDBHelper.COLUMN_EMAIL + "='" + MainActivity.edEmail.getText().toString() + "' AND " + FeedRunnerDBHelper.COLUMN_PASSWORD + " = '" + edPassword.getText().toString() + "';";
            Cursor c = sqLiteDatabase.rawQuery(login_email, null);
            if (c.getCount() == 1) {
                c.moveToFirst();
                emailPass = c.getString(c.getColumnIndex("EMAIL"));
                startActivity(new Intent(getApplicationContext(), HomeActivity.class).putExtra("userName", c.getString(c.getColumnIndex("EMAIL"))));
                        /*FeedRunnerDBHelper fd=new FeedRunnerDBHelper(getApplicationContext());
                        fd.deleteRows();*/
            } else {
                Toast.makeText(getApplicationContext(), "No such a email or password", Toast.LENGTH_SHORT).show();
            }
        }
    }
}


 /*sqLiteDatabase=db_one.getReadableDatabase();
        String login_email = "SELECT EMAIL FROM " + FeedRunnerDBHelper.Table_name + " WHERE " + FeedRunnerDBHelper.COLUMN_EMAIL + " = ' " + edEmail.getText().toString() + "';";
        sqLiteDatabase.execSQL(login_email);*/
//        startActivity(new Intent(getApplicationContext(), HomeActivity.class));


//database
/*
* String login_email="SELECT EMAIL FROM "+FeedRunnerDBHelper.Table_name;
        Cursor c_email=sqLiteDatabase.rawQuery(login_email,null);
        ArrayList<String > stemail=new ArrayList<String>();
        stemail.add(c_email.toString());
        if(c_email!=null)
        {
            if(stemail.contains(edEmail.getText().toString()))
               {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"not a valid email",Toast.LENGTH_SHORT).show();
                }
//            while (c_email.moveToNext())
//            {
//                String l=c_email.getString(0);
//                if(l.equals(edEmail.getText().toString()))
//                {
//                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//                }
//                else
//                {
//                    Toast.makeText(getApplicationContext(),"not a valid email",Toast.LENGTH_SHORT).show();
//                }
//                Log.d("l::",l);
//            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"not a valid email",Toast.LENGTH_SHORT).show();
        }
*
*
*
*
*
* */
