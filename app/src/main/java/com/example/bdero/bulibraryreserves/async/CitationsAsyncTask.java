package com.example.bdero.bulibraryreserves.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.bdero.bulibraryreserves.CourseListAdapter.CourseHolder;
import com.example.bdero.bulibraryreserves.async.CitationResponse.Citation;
import com.example.bdero.bulibraryreserves.async.CourseResponse.Course;
import com.example.bdero.bulibraryreserves.utils.NetworkUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class CitationsAsyncTask extends AsyncTask<Course,Void,ArrayList<Citation>> {

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

    @Override
    protected ArrayList<Citation> doInBackground(Course... courses) {
        ArrayList<Citation> output = new ArrayList<>();
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
        }

        /*ArrayList<CourseEntity> curCourse = courseList[0];
        ArrayList<String> rlLinks = new ArrayList<>();
        for (CourseEntity course : curCourse) {
            rlLinks.addAll(course.getReadingListLinks());
            for (String link : course.getReadingListLinks()){
                URL citationURL = NetworkUtils.buildCitationsURL(mContext, link);
                try {
                    String citationsResponse = NetworkUtils.getResponseFromHttpUrl(citationURL);
                    JSONObject citationsJSON = new JSONObject(citationsResponse);
                    JSONArray citationsArray = citationsJSON.getJSONArray("citation");
                    for (int indCitation = 0; indCitation < citationsArray.length(); indCitation++){
                        JSONObject curCitation = citationsArray.getJSONObject(indCitation);
                        String title = curCitation.getJSONObject("metadata").getString("title");
                        String author = curCitation.getJSONObject("metadata").getString("author");
                        String mms_Id = curCitation.getJSONObject("metadata").getString("mms_id");
                        Log.d(CITATION_TASK_LOG_TAG, "Citation #" +
                                String.valueOf(indCitation + 1) + ": " + title + " " + author + "(" + mms_Id + ")");

                    }

                } catch (IOException e){
                    Log.e(CITATION_TASK_LOG_TAG, "IO error from URL: " + citationURL);
                    e.printStackTrace();
                } catch (JSONException e){
                    Log.e(CITATION_TASK_LOG_TAG, "Error parsing JSON from result String.");
                    e.printStackTrace();
                }
            }
        }*/
        return output;
    }


    @Override
    protected void onPostExecute(ArrayList<Citation> citations) {
        super.onPostExecute(citations);

        //mCourseHolder.mCitationsRecyclerView.setAdapter(new ArrayAdapter<String>(mCourseHolder.mContext, R.layout.tracked_book_list_item));

        mCourseHolder.setProgBarVisibility(View.GONE);
    }
}
