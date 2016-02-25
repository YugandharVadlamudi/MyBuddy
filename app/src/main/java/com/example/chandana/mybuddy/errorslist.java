package com.example.chandana.mybuddy;

/**
 * Created by chandana on 20-01-2016.
 */
public class errorslist {
//    java.lang.OutOfMemoryError: (Heap Size=81520KB, Allocated=56839KB)
String Select_contact_list = "SELECT " + FeedRunnerDBHelper.CONTACTS_COLUMN_IMGPATH + "," + FeedRunnerDBHelper.CONTACTS_COLUMN_NAME + "," + FeedRunnerDBHelper.CONTACTS_COLUMN_PHONENUMBER
        + " FROM " + FeedRunnerDBHelper.TABLE_CONTACTS + " WHERE " /*rowid!=+ ContactRegistrationActivity.checkInsertContact*/
        +/* " AND " +*/ FeedRunnerDBHelper.CONTACTS_COLUMN_USERID + "='" + MainActivity.emailPass + "'";


}
