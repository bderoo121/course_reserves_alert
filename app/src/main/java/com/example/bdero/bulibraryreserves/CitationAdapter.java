package com.example.bdero.bulibraryreserves;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bdero.bulibraryreserves.async.CitationResponse.Citation;

import java.util.ArrayList;

public class CitationAdapter extends RecyclerView.Adapter<CitationHolder>{

    private static final String LOG_TAG = CitationAdapter.class.getSimpleName();

    Context mContext;
    ArrayList<Citation> mDataset;

    CitationAdapter(Context context){
        mDataset = new ArrayList<>();
        mContext = context;
    }

    @NonNull
    @Override
    public CitationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Create a new view
        int classLayout = R.layout.citation_list_item;

        // Do not attach immediately when using a Viewholder model.
        boolean shouldAttachImmediately = false;

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(classLayout, parent, shouldAttachImmediately);

        return new CitationHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CitationHolder holder, int position) {
        Citation citation = mDataset.get(position);
        holder.mCitationTitle.setText(citation.getMetadata().getTitle());
        holder.mCitationAuthor.setText(citation.getMetadata().getAuthor());
        holder.mCitationEdition.setText("???th ed.");

    }

    @Override
    public int getItemCount() {
        if (mDataset == null){
            return 0;
        } else {
            return mDataset.size();
        }
    }

}
