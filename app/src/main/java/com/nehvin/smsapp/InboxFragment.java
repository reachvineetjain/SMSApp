package com.nehvin.smsapp;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class InboxFragment extends Fragment {

//    private OnFragmentInteractionListener mListener;
    public static final String TAG = InboxFragment.class.getSimpleName();
    final ArrayList<TextMessage> textMsg = new ArrayList<TextMessage>();
    TextMessageAdapter inboxAdapter ;
    ListView listView;
    private static InboxFragment inst;

    public static InboxFragment instance() {
        return inst;
    }


    public InboxFragment() {
        // Required empty public constructor
    }


    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_inbox, container, false);

        refreshSmsInbox();

        inboxAdapter = new TextMessageAdapter(getActivity(),textMsg);

        listView = (ListView)rootView.findViewById(R.id.activity_inbox);

        listView.setAdapter(inboxAdapter);

        return rootView;
    }


    public void refreshSmsInbox() {

        ContentResolver contentResolver = getContext().getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);

//        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexBody = smsInboxCursor.getColumnIndex(Telephony.TextBasedSmsColumns.BODY);
//        int indexAddress = smsInboxCursor.getColumnIndex("address");
        int indexAddress = smsInboxCursor.getColumnIndex(Telephony.TextBasedSmsColumns.ADDRESS);
//        int dateReceived = smsInboxCursor.getColumnIndex("date");
        int idSMS = smsInboxCursor.getColumnIndex(Telephony.Sms._ID);
        int idSMSInbox = smsInboxCursor.getColumnIndex(Telephony.Sms.Inbox._ID);
        int dateReceived = smsInboxCursor.getColumnIndex(Telephony.Sms.Inbox.DATE);
        int dateSent = smsInboxCursor.getColumnIndex(Telephony.TextBasedSmsColumns.DATE_SENT);
        int creator = smsInboxCursor.getColumnIndex(Telephony.TextBasedSmsColumns.CREATOR);
        int person = smsInboxCursor.getColumnIndex(Telephony.TextBasedSmsColumns.PERSON);
        int protocol = smsInboxCursor.getColumnIndex(Telephony.TextBasedSmsColumns.PROTOCOL);
        int read = smsInboxCursor.getColumnIndex(Telephony.TextBasedSmsColumns.READ);
        int seen = smsInboxCursor.getColumnIndex(Telephony.TextBasedSmsColumns.SEEN);
        int status = smsInboxCursor.getColumnIndex(Telephony.TextBasedSmsColumns.STATUS);
        int replyPathCenter = smsInboxCursor.getColumnIndex(Telephony.TextBasedSmsColumns.REPLY_PATH_PRESENT);
        int serviceCenter = smsInboxCursor.getColumnIndex(Telephony.TextBasedSmsColumns.SERVICE_CENTER);
        int threadID = smsInboxCursor.getColumnIndex(Telephony.TextBasedSmsColumns.THREAD_ID);
        int type = smsInboxCursor.getColumnIndex(Telephony.TextBasedSmsColumns.TYPE);

        Date date = new Date(dateReceived);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy  HH:mm");
        String dateText = format.format(date);

        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        textMsg.clear();
        do {
//            String str = smsInboxCursor.getString(indexAddress) +" at "+
//                    "\n" + smsInboxCursor.getString(indexBody) + dateText+ "\n";
//            Log.i(TAG, "refreshSmsInbox: dateSent : "+format.format(new Date(smsInboxCursor.getLong(dateSent))));
// DOES NOT WORK FOR API 16 Log.i(TAG, "refreshSmsInbox: creator : "+smsInboxCursor.getString(creator));
//            Log.i(TAG, "refreshSmsInbox: "+smsInboxCursor.getString(indexAddress));
//            Log.i(TAG, "refreshSmsInbox: person : "+smsInboxCursor.getString(person));
//            Log.i(TAG, "refreshSmsInbox: protocol : "+smsInboxCursor.getString(protocol));
//            Log.i(TAG, "refreshSmsInbox: read : "+smsInboxCursor.getString(read));
//            Log.i(TAG, "refreshSmsInbox: seen : "+smsInboxCursor.getString(seen));
//            Log.i(TAG, "refreshSmsInbox: status : "+smsInboxCursor.getString(status));
//            Log.i(TAG, "refreshSmsInbox: replyPathCenter : "+smsInboxCursor.getString(replyPathCenter));
//            Log.i(TAG, "refreshSmsInbox: serviceCenter : "+smsInboxCursor.getString(serviceCenter));
//            Log.i(TAG, "refreshSmsInbox: threadID : "+smsInboxCursor.getString(threadID));
//            Log.i(TAG, "refreshSmsInbox: type : "+smsInboxCursor.getString(type));
//            Log.i(TAG, "refreshSmsInbox: "+"\\n");
            Log.i(TAG, "refreshSmsInbox: ID SMS "+ smsInboxCursor.getInt(idSMS));
            Log.i(TAG, "refreshSmsInbox: ID SMS Inbox"+ smsInboxCursor.getInt(idSMSInbox));

            dateText = format.format(new Date(smsInboxCursor.getLong(dateReceived)));
            textMsg.add(new TextMessage(smsInboxCursor.getString(indexAddress), smsInboxCursor.getString(indexBody), dateText, "Not Sent"));
        } while (smsInboxCursor.moveToNext());
    }


    public void updateList(final TextMessage smsMessage) {
        textMsg.add(0, smsMessage);
        inboxAdapter.notifyDataSetChanged();
    }


//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
