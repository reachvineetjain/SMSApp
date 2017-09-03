package com.nehvin.smsapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Neha on 8/31/2017.
 */

public class MessageSenderDBHelper extends SQLiteOpenHelper {

    // The name of the database
    private static final String DATABASE_NAME = "MessageSenderDb.db";

    // If you change the database schema, you must increment the database version
    private static final int VERSION = 1;

    public MessageSenderDBHelper(Context context) {

        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create tasks table (careful to follow SQL formatting rules)
        final String CREATE_TABLE = "CREATE TABLE "  + MessageSenderContract.MessageSenderEntry.TABLE_NAME + " (" +
                MessageSenderContract.MessageSenderEntry._ID  + " INTEGER PRIMARY KEY , " +
                MessageSenderContract.MessageSenderEntry.COLUMN_DATE_ADDED + " TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP ," +
                MessageSenderContract.MessageSenderEntry.COLUMN_SENDER + " TEXT NOT NULL UNIQUE , " +
                MessageSenderContract.MessageSenderEntry.COLUMN_SENDER_DETAILS + " TEXT NOT NULL , " +
                MessageSenderContract.MessageSenderEntry.COLUMN_BLOCKED + " INTEGER NOT NULL DEFAULT 0);";

        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MessageSenderContract.MessageSenderEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}