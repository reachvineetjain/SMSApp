package com.nehvin.smsapp;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class InboxFragment extends Fragment {

//    private OnFragmentInteractionListener mListener;
    final ArrayList<TextMessage> textMsg = new ArrayList<TextMessage>();

    public InboxFragment() {
        // Required empty public constructor
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
//        textMsg.add(new TextMessage("+919820243666", "5. The quick brown box jumped over the lazy dog. " +
//                "There is no wonder in the dog being a lazy animal even though both are of the " +
//                "same breed","25 Sep 2017 14:56", "Not Sent"));
//
//        textMsg.add(new TextMessage("+918779600355", "5. The quick brown box jumped over the lazy dog. " +
//                "There is no wonder in the dog being a lazy animal even though both are of the " +
//                "same breed","25 Sep 2017 13:21", "Sent"));
//
//        textMsg.add(new TextMessage("Vineet", "4. The quick brown box jumped over the lazy dog. " +
//                "There is no wonder in the dog being a lazy animal even though both are of the " +
//                "same breed","25 Aug 2017 08:08", "Not Sent"));
//
//        textMsg.add(new TextMessage("Punit", "3. The quick brown box jumped over the lazy dog. " +
//                "There is no wonder in the dog being a lazy animal even though both are of the " +
//                "same breed","25 July 2017 12:52", "Sent"));
//
//        textMsg.add(new TextMessage("Suneet", "2. The quick brown box jumped over the lazy dog. " +
//                "There is no wonder in the dog being a lazy animal even though both are of the " +
//                "same breed","25 June 2017 00:54", "Not Sent"));
//
//        textMsg.add(new TextMessage("Manish", "1. The quick brown box jumped over the lazy dog. " +
//                "There is no wonder in the dog being a lazy animal even though both are of the " +
//                "same breed","25 May 2017 05:23", "Not Sent"));

        TextMessageAdapter inboxAdapter = new TextMessageAdapter(getActivity(),textMsg);

        ListView listView = (ListView)rootView.findViewById(R.id.activity_inbox);

        listView.setAdapter(inboxAdapter);

        return rootView;
    }


    public void refreshSmsInbox() {

        ContentResolver contentResolver = getContext().getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);

        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        int timeMillis = smsInboxCursor.getColumnIndex("date");
        Date date = new Date(timeMillis);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String dateText = format.format(date);

        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        textMsg.clear();
        do {
            String str = smsInboxCursor.getString(indexAddress) +" at "+
                    "\n" + smsInboxCursor.getString(indexBody) + dateText+ "\n";
            dateText = format.format(new Date(smsInboxCursor.getLong(timeMillis)));
            textMsg.add(new TextMessage(smsInboxCursor.getString(indexAddress), smsInboxCursor.getString(indexBody), dateText, "Not Sent"));
        } while (smsInboxCursor.moveToNext());
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
