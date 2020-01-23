package com.example.mobileprogrammingproject;

import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.content.Loader;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;


import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mobileprogrammingproject.NotificationsUtilites.NotificationUtils;
import com.example.mobileprogrammingproject.data.ProjectContract;
import com.example.mobileprogrammingproject.data.ProjectDBHelper;
import com.example.mobileprogrammingproject.sync.ReminderUtilities;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,  ListItemClickListener {
    public static final int      FORECAST_LOADER = 0;

    private ImageView ChargingImageView;
    ExampleBroadCastReciver exampleBroadcastReceiver = new ExampleBroadCastReciver();
    ChargingBroadcastReceiver mChargingReceiver;

    IntentFilter              mChargingIntentFilter;

    public static final String[] MAIN_FORECAST_PROJECTION = {
          ProjectContract.ItemPriceEntry.COLUMN_ITEMPRICE,
            ProjectContract.ItemNameEntry.COLUMN_ITEMNAME,
        ProjectContract.ActiveBidEntry.COLUMN_ACTIVEBIDS


    };
    ArrayList<items> lstitems;
    ProjectDBHelper mDBHelper;
    RecyclerViewAdapter  myAdapter;

    String [] itemname = {
            "iPhone 11 pro","HP Omen","Turkish Tea","Microwave","Phillips LCD","Xiaomi Smartphone"
    };

    String [] itemprice = {
            "10000", "7500", "153","2000","5200","37000"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ChargingImageView = findViewById(R.id.chargingImageView);


        lstitems = new ArrayList<>();

        mDBHelper = new ProjectDBHelper(this);

        mDBHelper.getReadableDatabase();

        getLoaderManager().initLoader(FORECAST_LOADER, null, this);

        for(int i =0; i < itemname.length; i++){
         items items = new items();
         items.setItemprice(itemprice[i]);
         items.setItemname(itemname[i]);
       //  items.setItemimagepreview(images[i]);
         lstitems.add(items);
         }

        mChargingIntentFilter = new IntentFilter();
        mChargingIntentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        mChargingIntentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);

        mChargingReceiver     = new ChargingBroadcastReceiver();



        ReminderUtilities.scheduleShoppingReminder(this);

        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerView);

        myAdapter = new RecyclerViewAdapter(this,lstitems, this);
        myrv.setLayoutManager(new GridLayoutManager(this, 2));



        myrv.setAdapter(myAdapter);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow_menu, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(exampleBroadcastReceiver,filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(exampleBroadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            BatteryManager batteryManager = (BatteryManager)
                    getSystemService(BATTERY_SERVICE);

            showCharging(batteryManager.isCharging());

        } else {
            IntentFilter ifilter = new IntentFilter(Intent
                    .ACTION_BATTERY_CHANGED);
            Intent currentBatteryStatusIntent = registerReceiver(null,
                    ifilter);
            int batteryStatus = currentBatteryStatusIntent
                    .getIntExtra(BatteryManager.EXTRA_STATUS, -1);

            boolean isCharging = false;

            if (batteryStatus == BatteryManager.BATTERY_STATUS_CHARGING ||
                    batteryStatus == BatteryManager.BATTERY_STATUS_FULL){
                isCharging = true;
            }
            showCharging(isCharging);
        }


        registerReceiver(mChargingReceiver, mChargingIntentFilter);



    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(mChargingReceiver);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.account:
                Intent intent2 = new Intent(this, AccountActivity.class);
                startActivity(intent2);
                return true;

            case R.id.database:
                mDBHelper.AddmockdataToDB();
                return true;

            case R.id.insertion:
                Intent intent3 = new Intent(this, DatabaseActivity.class);
                startActivity(intent3);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

       // String sortOrder             = WeatherEntry.COLUMN_DATE + " ASC";
        Uri Itemname_ItemPrice_ActiveBids = ProjectContract.ItemNameEntry.buildQueryUri();

        return new CursorLoader(this,
                Itemname_ItemPrice_ActiveBids,
                MAIN_FORECAST_PROJECTION,
                null,
                null,
               null);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            myAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        myAdapter.swapCursor(null);
    }


    @Override
    public void onListItemClick(int clickedItemIndex) {
        ContentResolver mContentResolver;
        Uri Itemname_ItemPrice_ActiveBids = ProjectContract.ItemNameEntry.buildQueryUri();

        Uri ItemnameURi = ProjectContract.ItemNameEntry.CONTENT_URI;
        Uri ItemPriceURi = ProjectContract.ItemPriceEntry.CONTENT_URI;
        Uri ActiveBidsURi = ProjectContract.ActiveBidEntry.CONTENT_URI;
        String [] whereArgs = new String []{
                String.valueOf(clickedItemIndex)
        };

        mContentResolver = getContentResolver();

        int args = clickedItemIndex+1;

        mContentResolver.delete(ItemnameURi, ProjectContract.ItemNameEntry._ID+ " = " + args ,  null);
        mContentResolver.delete(ItemPriceURi, ProjectContract.ItemPriceEntry._ID + "=" + args, null );
        mContentResolver.delete(ActiveBidsURi, ProjectContract.ActiveBidEntry._ID + "=" + args, null);

    }
    public void testNotification(View view) {
        NotificationUtils.remindUser(this);
    }




    private class ChargingBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            String  action     = intent.getAction();
            boolean isCharging = (action.equals(Intent.ACTION_POWER_CONNECTED));

            showCharging(isCharging);
        }
    }

    private void showCharging(boolean isCharging){
        if (isCharging) {
            ChargingImageView.setImageResource(R.drawable.charging);

        } else {
            ChargingImageView.setImageResource(R.drawable.notcharging);
        }
    }
    private class ExampleBroadCastReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                boolean noConnectivity = intent.getBooleanExtra(
                        ConnectivityManager.EXTRA_NO_CONNECTIVITY, false
                );
                if (noConnectivity) {
                    Toast.makeText(context, "Disconnected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();
                }
            }

    }}


}
