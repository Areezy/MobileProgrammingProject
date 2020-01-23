package com.example.mobileprogrammingproject;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Cursor mCursor;

    private ListItemClickListener mOnClickListener;


     private Context mContext;
     private List<items> mData;
//    private ArrayList<String> mNamess = new ArrayList<>();
//    private ArrayList<String> mImageUrlsa = new ArrayList<>();


    public RecyclerViewAdapter(Context mContext, List<items> mData, ListItemClickListener listener) {
        this.mContext = mContext;
        this.mData = mData;
        mOnClickListener = listener;
//        mNamess = names;
//        mImageUrlsa = imageUrls;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_items, parent, false);


        return new MyViewHolder(view, mOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


//        Glide.with(mContext)
//                .asBitmap()
//                .load(mImageUrls.get(position))
//                .into(holder.image);
//
//        holder.name.setText(mNames.get(position));




        mCursor.moveToPosition(position);


    //    holder.itemname.setText(mData.get(position).getItemname());
     //   holder.price.setText(mData.get(position).getItemprice());
//        holder.item_image_preview.setImageResource(mData.get(position).getItemimagepreview());

        String itemN = mCursor.getString(1);
        String itemP = mCursor.getString(0);
        String BidStatus = mCursor.getString(2);
        Log.v("key22", BidStatus);
        holder.itemname.setText(itemN);
        holder.price.setText(itemP);
        holder.bidstatus.setText("Bid Status :" + BidStatus);





    }

    @Override
    public int getItemCount() {

        if (mCursor == null){
            return 0;
        }
        else{
            return mCursor.getCount();
        }
    }


    public void swapCursor(Cursor newCursor){
        mCursor = newCursor;
        notifyDataSetChanged();

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private  ListItemClickListener mListener;
        TextView price;
        TextView itemname;
        TextView bidstatus;
        ImageView item_image_preview;

        public MyViewHolder(@NonNull View itemView, ListItemClickListener listener) {
            super(itemView);
            mListener = listener;
            price = (TextView) itemView.findViewById(R.id.itemPrice);
            itemname = (TextView) itemView.findViewById(R.id.itemName);
            bidstatus = (TextView) itemView.findViewById(R.id.BidStatus);

            itemView.setOnClickListener(this);

        // item_image_preview = (ImageView) itemView.findViewById(R.id.itemImage);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mListener.onListItemClick(clickedPosition);
        }
    }
}
