package com.example.bdero.bulibraryreserves.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.bdero.bulibraryreserves.CitationAdapter;
import com.example.bdero.bulibraryreserves.CourseHolder;
import com.example.bdero.bulibraryreserves.async.CitationResponse.Citation;
import com.example.bdero.bulibraryreserves.async.CourseResponse.Course;
import com.example.bdero.bulibraryreserves.utils.NetworkUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class CitationsAsyncTask extends AsyncTask<Course,Void,ArrayList<Course>> {

    private static final String CITATION_TASK_LOG_TAG = CitationsAsyncTask.class.getSimpleName();

    private final Context mContext;
    private final CourseHolder mCourseHolder;


    public CitationsAsyncTask(Context context, CourseHolder holder){
        mCourseHolder = holder;
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * Updates a collection of course objects that match a course code by adding all citations
     * attached to any of their reading lists.
     * @param courses
     * @return courses (+citations)
     */
    @Override
    protected ArrayList<Course> doInBackground(Course... courses) {
        ArrayList<Course> output = new ArrayList<>();
        //Merge all of the reading list links into a single location.
        for (Course curCourse : courses){
            if (curCourse.getCitations() == null) {
                curCourse.setCitations(new ArrayList<Citation>());
            }
            for (String link : curCourse.getRlLinks()){
                URL citationURL = NetworkUtils.buildCitationsURL(mContext, link);
                try {
                    String citationResponse = NetworkUtils.getResponseFromHttpUrl(citationURL);
                    CitationResponse response = new Gson().fromJson(citationResponse, CitationResponse.class);
                    curCourse.addCitations(response.getCitations());

                } catch (IOException e) {
                    Log.e(CITATION_TASK_LOG_TAG, "IO error.");
                    e.printStackTrace();
                }
            }
            output.add(curCourse);
        }
        return output;
    }

    // Pass the courses back to the holder to immediately utilize.
    // Also pass the courses back to the CourseAdapter for rebuilding
    @Override
    protected void onPostExecute(ArrayList<Course> courses) {
        super.onPostExecute(courses);
        CitationAdapter adapter = mCourseHolder.getCitationAdapter();
        mCourseHolder.updateCourses(courses);





        //mCourseHolder.mCitationsRecyclerView.setAdapter(new ArrayAdapter<String>(mCourseHolder.mContext, R.layout.tracked_book_list_item));

        mCourseHolder.setProgBarVisibility(View.GONE);
    }
}
