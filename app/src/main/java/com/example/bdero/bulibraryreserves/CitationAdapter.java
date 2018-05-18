package com.example.bdero.bulibraryreserves;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

class CitationAdapter extends RecyclerView.Adapter<CitationAdapter.CitationHolder>{

    private static final String LOG_TAG = CitationAdapter.class.getSimpleName();

    //******************
    // some mDataSet;
    //******************

    @NonNull
    @Override
    public CitationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CitationHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class CitationHolder extends RecyclerView.ViewHolder{

        public CitationHolder(View itemView) {
            super(itemView);
        }
    }
}
