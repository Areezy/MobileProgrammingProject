package com.example.mobileprogrammingproject.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class ProjectContract {


    public static final String CONTENT_AUTHORITY = "com.example.mobileprogrammingproject";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_ITEMSNAME = "itemname";
    public static final String PATH_ITEMSPRICE = "itemprice";
    public static final String PATH_ACTIVEBIDS = "activebids";

    public static final class  ItemNameEntry implements BaseColumns {


        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_ITEMSNAME)
                .build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMSNAME;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMSNAME;

        public static final String TABLE_NAME = "ItemNames";


        public static final String COLUMN_ITEMNAME = "ITEMNAMES";


        public static Uri buildItemNameUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildQueryUri() {
            return CONTENT_URI.buildUpon().appendPath(PATH_ITEMSPRICE).appendPath(PATH_ACTIVEBIDS).build();
        }
    }


    public static final class ItemPriceEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_ITEMSPRICE)
                .build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMSPRICE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMSPRICE;

        public static final String TABLE_NAME = "ItemPrices";


        public static final String COLUMN_ITEMPRICE = "ITEMPRICES";


        public static Uri buildItemPriceUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }


    }


    public static final class ActiveBidEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_ACTIVEBIDS)
                .build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ACTIVEBIDS;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ACTIVEBIDS;

        public static final String TABLE_NAME = "ActiveBids";


        public static final String COLUMN_ACTIVEBIDS = "ACTIVEBIDS";


        public static Uri buildActiveBidsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }


    }



}


