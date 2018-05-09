package com.example.bdero.bulibraryreserves;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by bdero on 3/12/2018.
 */
public class TrackedBookListAdapter extends RecyclerView.Adapter<TrackedBookListAdapter.BookViewHolder> {

    private String[][] mDataset; //{"Book name", "class code/prof.", "availability"};

    static class BookViewHolder extends RecyclerView.ViewHolder{

        final TextView mBookTitle;
        final TextView mClassInfo;
        final TextView mAvailabilityNote;
        final ImageView mAvailabilityImage;

        BookViewHolder(View view) {
            super(view);
            mBookTitle = view.findViewById(R.id.tv_book_title);
            mClassInfo = view.findViewById(R.id.tv_class_info);
            mAvailabilityNote = view.findViewById(R.id.tv_availability_note);
            mAvailabilityImage= view.findViewById(R.id.im_availability_indicator);
        }
    }

    TrackedBookListAdapter(String[][] myDataset){
        mDataset = myDataset;
    }

    //Create new views (invoked by layout manager)
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int bookLayout = R.layout.tracked_book_list_item;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(bookLayout, parent, false);
        return new BookViewHolder(view);
    }

    //Replace the contents of the view (invoked by layout manager)
    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        String bookTitle = mDataset[position][0];
        holder.mBookTitle.setText(bookTitle);

        String classInfo = mDataset[position][1];
        holder.mClassInfo.setText(classInfo);

        boolean available = mDataset[position][2].equals("available");
        if (available) {
            holder.mAvailabilityImage.setVisibility(View.GONE);
            holder.mAvailabilityNote.setText(R.string.available);
        } else {
            holder.mAvailabilityNote.setText(R.string.on_loan_alert);
            holder.mAvailabilityImage.setImageResource(R.drawable.ic_add_alert_24dp);
            holder.mAvailabilityImage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (mDataset == null) return 0;
        return mDataset.length;
    }
}
