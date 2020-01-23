package com.example.mobileprogrammingproject;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;

import com.example.mobileprogrammingproject.data.ProjectContract;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileprogrammingproject.R;

public class DatabaseActivity extends AppCompatActivity {

    private EditText itemnameEditText;
    private EditText itempriceEditText;
    private EditText activebidsEditText;
    private Button insertbutton;
    private Button updatebutton;
    private EditText idEditText;
    ContentResolver mContentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        Toolbar toolbar = findViewById(R.id.toolbar);
        itemnameEditText = findViewById(R.id.ItemNameEditText);
        itempriceEditText = findViewById(R.id.ItemPriceEditText);
        activebidsEditText = findViewById(R.id.ActiveBidsEditText);
        idEditText = findViewById(R.id.IdEditText);
        insertbutton = findViewById(R.id.insertbutton);
        updatebutton = findViewById(R.id.updatebutton);
        setSupportActionBar(toolbar);

        mContentResolver = getContentResolver();


        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri ItemnameURi = ProjectContract.ItemNameEntry.CONTENT_URI;
                Uri ItemPriceURi = ProjectContract.ItemPriceEntry.CONTENT_URI;
                Uri ActiveBidsURi = ProjectContract.ActiveBidEntry.CONTENT_URI;

                String itemnametobeupdated = itemnameEditText.getText().toString();
                String itempricetobeupdated = itempriceEditText.getText().toString();
                String activebidsstatustobeupdated = activebidsEditText.getText().toString();
                String idtobeupdated = idEditText.getText().toString();

                ContentValues mContentValues = new ContentValues();

                ContentValues mContentValues2 = new ContentValues();


                ContentValues mContentValues3 = new ContentValues();

                mContentValues.put(ProjectContract.ItemNameEntry.COLUMN_ITEMNAME, itemnametobeupdated);

                mContentResolver.update(ItemnameURi, mContentValues,ProjectContract.ItemNameEntry._ID+ " = " + idtobeupdated, null );


                mContentValues2.put(ProjectContract.ItemPriceEntry.COLUMN_ITEMPRICE, itempricetobeupdated);

                mContentResolver.update(ItemPriceURi, mContentValues2,ProjectContract.ItemPriceEntry._ID+ " = " + idtobeupdated, null );

                mContentValues3.put(ProjectContract.ActiveBidEntry.COLUMN_ACTIVEBIDS, activebidsstatustobeupdated);

                mContentResolver.update(ActiveBidsURi, mContentValues3,ProjectContract.ActiveBidEntry._ID+ " = " + idtobeupdated, null );

                Toast.makeText(getApplicationContext(), "Data updated Successfully",
                        Toast.LENGTH_LONG).show();



            }
        });


        insertbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri ItemnameURi = ProjectContract.ItemNameEntry.CONTENT_URI;
                Uri ItemPriceURi = ProjectContract.ItemPriceEntry.CONTENT_URI;
                Uri ActiveBidsURi = ProjectContract.ActiveBidEntry.CONTENT_URI;

                String itemnametobeinserted = itemnameEditText.getText().toString();
                String itempricetobeinserted = itempriceEditText.getText().toString();
                String activebidsstatustobeinserted = activebidsEditText.getText().toString();

                ContentValues mContentValues = new ContentValues();

                ContentValues mContentValues2 = new ContentValues();

                ContentValues mContentValues3 = new ContentValues();

                mContentValues.put(ProjectContract.ActiveBidEntry.COLUMN_ACTIVEBIDS, activebidsstatustobeinserted);
                mContentResolver.insert(ActiveBidsURi, mContentValues);

                mContentValues2.put(ProjectContract.ItemNameEntry.COLUMN_ITEMNAME, itemnametobeinserted);
                mContentResolver.insert(ItemnameURi, mContentValues2);



                mContentValues3.put(ProjectContract.ItemPriceEntry.COLUMN_ITEMPRICE, itempricetobeinserted);
                mContentResolver.insert(ItemPriceURi, mContentValues3);


                Toast.makeText(getApplicationContext(), "Data inserted Successfully",
                        Toast.LENGTH_LONG).show();


            }
        });


    }

}
