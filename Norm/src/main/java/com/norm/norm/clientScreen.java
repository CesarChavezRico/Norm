package com.norm.norm;

import android.app.ActionBar;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class clientScreen extends ActionBarActivity {


    private NfcAdapter mNfcAdapter;
    private IntentFilter[] mNdefExchangeFilters;
    private PendingIntent mNfcPendingIntent;
    private final String NFC_NORM_IDENTIFIER = "N0rm!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_screen);

        /* NFC */
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        mNfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TOP), 0);

        IntentFilter discovery = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        mNdefExchangeFilters = new IntentFilter[] { discovery };


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.client_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume(){
        super.onResume();
        nfcCheck();
    }
    @Override
    protected void onPause(){
        super.onPause();
        if(mNfcAdapter != null) mNfcAdapter.disableForegroundDispatch(this);
    }

    /**
     * we check to see if the NFC adapter is enabled and we execute enableForegroundDispatch(),
     * passing in our pending intent and filters.
     */
    private void nfcCheck() {
        Log.i("NFC Intent","nfcCheck");
        if(mNfcAdapter != null) {
            mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent,
                    mNdefExchangeFilters, null);
        } else {
            Toast.makeText(this, "Sorry, No NFC Adapter found.", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * This is where we get the intent once we've tapped the tag.
     * Then we can use 'getParceableExtra()' to get the tag data and build an NDEF message array.
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        Log.i("NFC Intent","Llegamos");
        super.onNewIntent(intent);
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            NdefMessage[] messages = null;
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMsgs != null) {
                messages = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    messages[i] = (NdefMessage) rawMsgs[i];
                }
            }
            if (messages != null) {
                if (messages[0] != null) {
                    String result = "";
                    byte[] payload = messages[0].getRecords()[0].getPayload();
                    // this assumes that we get back am SOH followed by host/code
                    for (int b = 1; b < payload.length; b++) { // skip SOH
                        result += (char) payload[b];
                    }
                    parseNFC(result);
                }
            } else {
                Toast.makeText(this, "The NFC tag appears to be empty", Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * Restaurant@Special1,Specialn
     * @param nfcMessage
     */
    public void parseNFC(String nfcMessage) {

        ArrayList Orders = new ArrayList();
        ArrayList Special = new ArrayList();

        String[] Rest_Specials = nfcMessage.split("@");
        String Restaurant = Rest_Specials[0].substring(2);
        Toast.makeText(this, "Ryan! Welcome to "+Restaurant, Toast.LENGTH_LONG).show();

        String[] Specials = Rest_Specials[1].split(",");

        /**
         * Populate expandable specials list
         */
        ExpandableListView specialsList = (ExpandableListView)findViewById(R.id.specials);
        for (String s : Specials){
            Special.add(new special(Restaurant,s));
        }
        specialAdapter my_sA = new specialAdapter(clientScreen.this, Special);
        specialsList.setAdapter(my_sA);

        if (Restaurant.equals("Taco Villa")) {
            setTitle(Restaurant);
            ArrayList Order1 = new ArrayList(
                    Arrays.asList("Combo Burrito Red", "Chips & Guacamole", "Large Coke"));
            Orders.add(new order("Taco Villa","My Burrito combo", Order1));

            ArrayList Order2 = new ArrayList(
                    Arrays.asList("Small Tortilla", "Diet Pepsi"));
            Orders.add(new order("Taco Villa","Tortilla", Order2));

            /**
             * Populate expandable orders list
             */
            ExpandableListView orderList = (ExpandableListView)findViewById(R.id.orders);
            orderAdapter my_oA = new orderAdapter(clientScreen.this, Orders);
            orderList.setAdapter(my_oA);

        } else if (Restaurant.equals("Chick-fil-A")){
            setTitle(Restaurant);
            ArrayList Order3 = new ArrayList(
                    Arrays.asList("Frt Cup LG", "Dt DrPpr LG"));
            Orders.add(new order("Chick-fil-A","Fruit", Order3));

            ArrayList Order4 = new ArrayList(
                    Arrays.asList("Meal-CFASan", "Meal-CGSan"));
            Orders.add(new order("Chick-fil-A","Really Hungry!!!", Order4));


            /**
             * Populate expandable orders list
             */
            ExpandableListView orderList = (ExpandableListView)findViewById(R.id.orders);
            orderAdapter my_oA = new orderAdapter(clientScreen.this, Orders);
            orderList.setAdapter(my_oA);



        } else {
            Toast.makeText(this, "Sorry this NFC tag is not a Petrolog tag", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Set Restaurant name on title
     */
    private void setTitle(String title){
        /* Action bar title (Restaurant) */
        ActionBar bar = getActionBar();
        bar.setTitle(title);
    }
}
