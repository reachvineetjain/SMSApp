package com.nehvin.smsapp;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.nehvin.smsapp.data.MessageSenderContract;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Neha on 9/2/2017.
 */

public class SmsBroadcastReceiver extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";
    Context mContext;

    TextMessage txtMsg;

    public SmsBroadcastReceiver(Context mContext) {
        super();
        this.mContext = mContext;
    }

    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";
            String senderPhoneBook;
            InboxFragment inst = InboxFragment.instance();
            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                String smsBody = smsMessage.getMessageBody();
                String address = smsMessage.getOriginatingAddress();

                Uri lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                        Uri.encode(address));
                Cursor cursor = mContext.getContentResolver().query(lookupUri,
                        new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME},null,null,null);
                if(cursor != null && cursor.getCount() == 1 && cursor.moveToFirst())
                    senderPhoneBook = (cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME)));
                else
                    senderPhoneBook = (address);


                long timeMillis = smsMessage.getTimestampMillis();

                Date date = new Date(timeMillis);
                SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm", Locale.getDefault());
                String dateText = format.format(date);

                // Create new empty ContentValues object
                ContentValues contentValues = new ContentValues();

                contentValues.put(MessageSenderContract.MessageSenderEntry.COLUMN_SENDER, address);
                contentValues.put(MessageSenderContract.MessageSenderEntry.COLUMN_SENDER_DETAILS, senderPhoneBook);

                Uri uri = mContext.getContentResolver().insert(
                        MessageSenderContract.MessageSenderEntry.CONTENT_URI, contentValues);

                if(uri != null) {
                    Toast.makeText(mContext, uri.toString(), Toast.LENGTH_LONG).show();
                }

                txtMsg = new TextMessage(senderPhoneBook, smsBody, dateText, "Not Sent");
                inst.updateList(txtMsg);

                smsMessageStr += address +" at "+"\t"+ dateText + "\n";
                smsMessageStr += smsBody + "\n";
            }
            Toast.makeText(mContext, smsMessageStr, Toast.LENGTH_SHORT).show();

            //this will update the UI with message
//            ReceiveSmsActivity inst = ReceiveSmsActivity.instance();
//            inst.updateList(smsMessageStr);
        }
    }

    public SmsBroadcastReceiver() {
        super();
    }
}
