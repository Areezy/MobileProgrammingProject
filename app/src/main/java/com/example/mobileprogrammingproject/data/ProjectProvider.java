package com.example.mobileprogrammingproject.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.Contract;

public class ProjectProvider extends ContentProvider {

    public static final int ITEMNAME                        = 100;
    public static final int ITEMPRICE          = 101;
    public static final int ACTIVEBIDS = 102;
    public static final int ITEMNAME_ITEMPRICE_ACTIVEBIDS = 103;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private              ProjectDBHelper mOpenHelper;
    private static final SQLiteQueryBuilder sItemName_ItemPrice_ActiveBids_QueryBuilder;

    static{
        sItemName_ItemPrice_ActiveBids_QueryBuilder = new SQLiteQueryBuilder();

        // weather INNER JOIN location ON Weather.location_id = Location._id

        sItemName_ItemPrice_ActiveBids_QueryBuilder.setTables(
                 ProjectContract.ItemNameEntry.TABLE_NAME + " INNER JOIN " +
                        ProjectContract.ItemPriceEntry.TABLE_NAME +
                        " ON " + ProjectContract.ItemNameEntry.TABLE_NAME +
                        "." + ProjectContract.ItemNameEntry._ID +
                        " = " +  ProjectContract.ItemPriceEntry.TABLE_NAME +
                        "." + ProjectContract.ItemPriceEntry._ID +

                         " INNER JOIN " +
                         ProjectContract.ActiveBidEntry.TABLE_NAME +
                         " ON " + ProjectContract.ActiveBidEntry.TABLE_NAME +
                         "." + ProjectContract.ActiveBidEntry._ID +
                         " = " +  ProjectContract.ItemNameEntry.TABLE_NAME +
                         "." + ProjectContract.ItemNameEntry._ID

        );
    }

    public static UriMatcher buildUriMatcher(){

        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        String authority   = ProjectContract.CONTENT_AUTHORITY;

        matcher.addURI(authority,  ProjectContract.PATH_ITEMSNAME           , ITEMNAME);
        matcher.addURI(authority,   ProjectContract.PATH_ITEMSPRICE               , ITEMPRICE);
        matcher.addURI(authority, ProjectContract.PATH_ACTIVEBIDS    , ACTIVEBIDS);
        matcher.addURI(authority, ProjectContract.PATH_ITEMSNAME +"/"+  ProjectContract.PATH_ITEMSPRICE +"/"+ProjectContract.PATH_ACTIVEBIDS,ITEMNAME_ITEMPRICE_ACTIVEBIDS );


        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new ProjectDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db    = mOpenHelper.getReadableDatabase();
        final int            match = sUriMatcher.match(uri);
        Cursor               retCursor;

        switch(match){
            case ITEMNAME: {
                retCursor = db.query(ProjectContract.ItemNameEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
            } break;

            case ITEMPRICE: {
                retCursor = db.query(ProjectContract.ItemPriceEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
            } break;

            case ACTIVEBIDS:{

                retCursor = db.query(ProjectContract.ActiveBidEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

            } break;

            case ITEMNAME_ITEMPRICE_ACTIVEBIDS:{

                retCursor = sItemName_ItemPrice_ActiveBids_QueryBuilder.query(db,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

            } break;


            default:{
                throw new UnsupportedOperationException("Unknown uri:" + uri);
            }
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }



    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match){

            case ITEMNAME                       : return ProjectContract.ItemNameEntry.CONTENT_TYPE;
            case ITEMPRICE                      : return ProjectContract.ItemPriceEntry.CONTENT_TYPE;
            case ACTIVEBIDS                      : return ProjectContract.ActiveBidEntry.CONTENT_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: "+ uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final SQLiteDatabase db    = mOpenHelper.getWritableDatabase();
        final int            match = sUriMatcher.match(uri);
        Uri                  returnUri;

        switch(match){
            case ITEMNAME: {
                long _id = db.insert(ProjectContract.ItemNameEntry.TABLE_NAME, null, values);

                if (_id > 0){
                    returnUri = ProjectContract.ItemNameEntry.buildItemNameUri(_id);
                }
                else{
                    throw new android.database.SQLException("Failed to insert row into" + uri);
                }

            } break;
            case ITEMPRICE:{
                long _id = db.insert(ProjectContract.ItemPriceEntry.TABLE_NAME, null, values);

                if (_id > 0){
                    returnUri = ProjectContract.ItemPriceEntry.buildItemPriceUri(_id);
                }
                else{
                    throw new android.database.SQLException("Failed to insert row into" + uri);
                }
            } break;

            case ACTIVEBIDS:{
                long _id = db.insert(ProjectContract.ActiveBidEntry.TABLE_NAME, null, values);

                if (_id > 0){
                    returnUri = ProjectContract.ActiveBidEntry.buildActiveBidsUri(_id);
                }
                else{
                    throw new android.database.SQLException("Failed to insert row into" + uri);
                }
            }break;
            default:{
                throw new UnsupportedOperationException("Unknown uri:" + uri);
            }
        }
        getContext().getContentResolver().notifyChange(uri,null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        if(selection == null)
            selection = "1";
        switch(match){
            case ITEMNAME:
                rowsDeleted = db.delete(ProjectContract.ItemNameEntry.TABLE_NAME, selection,
                        selectionArgs);;
                break;
            case ITEMPRICE:
                rowsDeleted = db.delete(ProjectContract.ItemPriceEntry.TABLE_NAME, selection,
                        selectionArgs);
                break;

            case ACTIVEBIDS:
                rowsDeleted = db.delete(ProjectContract.ActiveBidEntry.TABLE_NAME, selection,
                        selectionArgs);
                break;

            case ITEMNAME_ITEMPRICE_ACTIVEBIDS:
                rowsDeleted = sItemName_ItemPrice_ActiveBids_QueryBuilder.delete(db, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(rowsDeleted != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return rowsDeleted;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch(match){
            case ITEMNAME:

                rowsUpdated = db.update(ProjectContract.ItemNameEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case ITEMPRICE:
                rowsUpdated = db.update(ProjectContract.ItemPriceEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case ACTIVEBIDS:
                rowsUpdated = db.update(ProjectContract.ActiveBidEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(rowsUpdated != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return rowsUpdated;
    }
}
