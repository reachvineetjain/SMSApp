package com.nehvin.smsapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Neha on 8/31/2017.
 */

public class MessageSenderContract {

    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.nehvin.smsapp";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "tasks" directory
    public static final String PATH_MESSAGESENDER = "messagesender";

    public static final class MessageSenderEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MESSAGESENDER).build();

        // Task table and column names
        public static final String TABLE_NAME = PATH_MESSAGESENDER;

        // Since TaskEntry implements the interface "BaseColumns", it has automatically produced
        // "_ID" column in addition to the below columns
        public static final String COLUMN_DATE_ADDED = "date_inserted";
        public static final String COLUMN_SENDER = "sender";
        public static final String COLUMN_SENDER_DETAILS = "sender_details";
        public static final String COLUMN_BLOCKED = "blocked";

    }



}
