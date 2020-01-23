package com.example.mobileprogrammingproject.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProjectDBHelper extends SQLiteOpenHelper {
    ContentResolver mResolver;
    Context mContext;
    int dbversion;
    private static  final int    DATABASE_VERSION = 21;
    public  static final String DATABASE_NAME    = "project.db";

    public ProjectDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();

        mContext = context;
        mResolver       = mContext.getContentResolver();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_ITEMNAME_TABLE = "CREATE TABLE " + ProjectContract.ItemNameEntry.TABLE_NAME + " (" +
                ProjectContract.ItemNameEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ProjectContract.ItemNameEntry.COLUMN_ITEMNAME + " TEXT  NOT NULL " +
                " );";

        final String SQL_CREATE_ITEMPRICE_TABLE = "CREATE TABLE " + ProjectContract.ItemPriceEntry.TABLE_NAME + " (" +
                ProjectContract.ItemPriceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ProjectContract.ItemPriceEntry.COLUMN_ITEMPRICE + " TEXT  NOT NULL " +
                " );";

        final String SQL_CREATE_ACTIVEBIDS_TABLE = "CREATE TABLE " + ProjectContract.ActiveBidEntry.TABLE_NAME + " (" +
                ProjectContract.ActiveBidEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ProjectContract.ActiveBidEntry.COLUMN_ACTIVEBIDS + " TEXT  NOT NULL " +
                " );";


        db.execSQL(SQL_CREATE_ITEMNAME_TABLE);
        db.execSQL(SQL_CREATE_ITEMPRICE_TABLE);
        db.execSQL(SQL_CREATE_ACTIVEBIDS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +  ProjectContract.ActiveBidEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " +  ProjectContract.ItemPriceEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " +   ProjectContract.ItemNameEntry.TABLE_NAME);
        onCreate(db);
    }

    public void AddmockdataToDB (){

        ContentValues  ItemNameValues = new ContentValues();

        ContentValues [] ItemNameValues_array = {
                ItemNameValues
        };

        ItemNameValues.put( ProjectContract.ItemNameEntry.COLUMN_ITEMNAME,"iPhone 11 pro" );
        mResolver.insert(ProjectContract.ItemNameEntry.CONTENT_URI, ItemNameValues);
        ItemNameValues.put( ProjectContract.ItemNameEntry.COLUMN_ITEMNAME,"HP Omen" );
        mResolver.insert(ProjectContract.ItemNameEntry.CONTENT_URI, ItemNameValues);
        ItemNameValues.put( ProjectContract.ItemNameEntry.COLUMN_ITEMNAME,"Turkish Tea" );

        mResolver.insert(ProjectContract.ItemNameEntry.CONTENT_URI, ItemNameValues);
        ItemNameValues.put( ProjectContract.ItemNameEntry.COLUMN_ITEMNAME,"Microwave" );
        mResolver.insert(ProjectContract.ItemNameEntry.CONTENT_URI, ItemNameValues);
        ItemNameValues.put( ProjectContract.ItemNameEntry.COLUMN_ITEMNAME,"Phillips LCD" );
        mResolver.insert(ProjectContract.ItemNameEntry.CONTENT_URI, ItemNameValues);
        ItemNameValues.put( ProjectContract.ItemNameEntry.COLUMN_ITEMNAME,"Xiaomi Smartphone" );
        mResolver.insert(ProjectContract.ItemNameEntry.CONTENT_URI, ItemNameValues);









        ContentValues ItemPriceValues = new ContentValues();


        ItemPriceValues.put( ProjectContract.ItemPriceEntry.COLUMN_ITEMPRICE,"10000" );
        mResolver.insert(ProjectContract.ItemPriceEntry.CONTENT_URI, ItemPriceValues);
        ItemPriceValues.put( ProjectContract.ItemPriceEntry.COLUMN_ITEMPRICE,"7500" );
        mResolver.insert(ProjectContract.ItemPriceEntry.CONTENT_URI, ItemPriceValues);
        ItemPriceValues.put( ProjectContract.ItemPriceEntry.COLUMN_ITEMPRICE,"153" );
        mResolver.insert(ProjectContract.ItemPriceEntry.CONTENT_URI, ItemPriceValues);
        ItemPriceValues.put( ProjectContract.ItemPriceEntry.COLUMN_ITEMPRICE,"2000" );
        mResolver.insert(ProjectContract.ItemPriceEntry.CONTENT_URI, ItemPriceValues);
        ItemPriceValues.put( ProjectContract.ItemPriceEntry.COLUMN_ITEMPRICE,"5200");
        mResolver.insert(ProjectContract.ItemPriceEntry.CONTENT_URI, ItemPriceValues);
        ItemPriceValues.put( ProjectContract.ItemPriceEntry.COLUMN_ITEMPRICE,"37000" );

       mResolver.insert(ProjectContract.ItemPriceEntry.CONTENT_URI, ItemPriceValues);


        ContentValues ActiveBidsValues = new ContentValues();


        ActiveBidsValues.put( ProjectContract.ActiveBidEntry.COLUMN_ACTIVEBIDS,"true" );
        mResolver.insert( ProjectContract.ActiveBidEntry.CONTENT_URI, ActiveBidsValues);
        ActiveBidsValues.put( ProjectContract.ActiveBidEntry.COLUMN_ACTIVEBIDS,"false" );
        mResolver.insert( ProjectContract.ActiveBidEntry.CONTENT_URI, ActiveBidsValues);
        ActiveBidsValues.put( ProjectContract.ActiveBidEntry.COLUMN_ACTIVEBIDS,"true" );
        mResolver.insert( ProjectContract.ActiveBidEntry.CONTENT_URI, ActiveBidsValues);
        ActiveBidsValues.put( ProjectContract.ActiveBidEntry.COLUMN_ACTIVEBIDS,"false" );
        mResolver.insert( ProjectContract.ActiveBidEntry.CONTENT_URI, ActiveBidsValues);
        ActiveBidsValues.put( ProjectContract.ActiveBidEntry.COLUMN_ACTIVEBIDS,"false");
        mResolver.insert( ProjectContract.ActiveBidEntry.CONTENT_URI, ActiveBidsValues);
        ActiveBidsValues.put( ProjectContract.ActiveBidEntry.COLUMN_ACTIVEBIDS,"false" );

        mResolver.insert( ProjectContract.ActiveBidEntry.CONTENT_URI, ActiveBidsValues);


    }
}
