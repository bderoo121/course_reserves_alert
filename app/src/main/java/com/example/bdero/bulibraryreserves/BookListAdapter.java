package com.example.bdero.bulibraryreserves;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by bdero on 3/12/2018.
 */
public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookViewHolder> {

    private String[][] mDataset; //{"Book 1", "Book 2", "Book 3", "Organic Chemistry"};

    public static class BookViewHolder extends RecyclerView.ViewHolder{

        public final TextView mBookTitle;
        public final TextView mClassInfo;

        public BookViewHolder(View view) {
            super(view);
            mBookTitle = (TextView) view.findViewById(R.id.tv_book_title);
            mClassInfo = (TextView) view.findViewById(R.id.tv_class_info);
        }
    }

    public BookListAdapter(String[][] myDataset){
        mDataset = myDataset;
    }

    //Create new views (invoked by layout manager)
    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int bookLayout = R.layout.item_book_list;
        boolean shouldAttachImmediately = false;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(bookLayout, parent, shouldAttachImmediately);
        return new BookViewHolder(view);
    }

    //Replace the contents of the view (invoked by layout manager)
    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        String bookTitle = mDataset[position][0];
        holder.mBookTitle.setText(bookTitle);

        String classInfo = mDataset[position][1];
        holder.mClassInfo.setText(classInfo);
    }

    @Override
    public int getItemCount() {
        if (mDataset == null) return 0;
        return mDataset.length;
    }
}
