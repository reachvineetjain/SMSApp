package com.nehvin.smsapp;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;



/**
 * Created by Neha on 8/29/2017.
 */

public class TextMessageAdapter  extends ArrayAdapter <TextMessage>{

    public static final String TAG = TextMessageAdapter.class.getSimpleName();

    public TextMessageAdapter(Activity activity, ArrayList<TextMessage> msgList)
    {
        super(activity,0, msgList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View listItemView = convertView;
        if(listItemView == null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextMessage currentNumber = getItem(position);
        assert currentNumber != null;

        TextView fromSender = listItemView.findViewById(R.id.from);
        Uri lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(currentNumber.getSenderDetails()));
        Cursor cursor = getContext().getContentResolver().query(lookupUri,
                new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME},null,null,null);
        if(cursor != null && cursor.getCount() == 1 && cursor.moveToFirst())
            fromSender.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME)));
        else
            fromSender.setText(currentNumber.getSenderDetails());


        TextView datetime = listItemView.findViewById(R.id.datetime);
        datetime.setText(currentNumber.getDatetime());

        TextView message = listItemView.findViewById(R.id.message);
        int msgLen = currentNumber.getMessage().length();
        if(msgLen < 40)
            message.setText(currentNumber.getMessage());
        else
            message.setText(currentNumber.getMessage().substring(0,40));

        TextView flag = listItemView.findViewById(R.id.flag);
        flag.setText(currentNumber.getFlag());

        return listItemView;
    }
}