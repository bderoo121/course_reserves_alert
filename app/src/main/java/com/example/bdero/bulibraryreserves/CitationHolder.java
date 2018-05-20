package com.example.bdero.bulibraryreserves;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

class CitationHolder extends RecyclerView.ViewHolder{

    TextView mCitationTitle;
    TextView mCitationAuthor;
    TextView mCitationEdition;
    CheckBox mTrackCheckBox;


    CitationHolder(View holder) {
        super(holder);

        mCitationTitle = holder.findViewById(R.id.tv_citation_title);
        mCitationAuthor = holder.findViewById(R.id.tv_citation_author);
        mCitationEdition = holder.findViewById(R.id.tv_citation_edition);
        mTrackCheckBox = holder.findViewById(R.id.cb_citation_track);


    }
}
