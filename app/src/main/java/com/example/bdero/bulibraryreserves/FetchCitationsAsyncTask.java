package com.example.bdero.bulibraryreserves;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.bdero.bulibraryreserves.CourseListAdapter.CourseHolder;
import com.example.bdero.bulibraryreserves.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class FetchCitationsAsyncTask extends AsyncTask<ArrayList<Course>,Void,ArrayList<String>> {

    private static final String CITATION_TASK_LOG_TAG = FetchCitationsAsyncTask.class.getSimpleName();

    private final Context mContext;
    private final CourseHolder mCourseHolder;


    FetchCitationsAsyncTask (Context context, CourseListAdapter.CourseHolder holder){
        mCourseHolder = holder;
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<String> doInBackground(ArrayList<Course>... courseList) {
        ArrayList<Course> curCourse = courseList[0];
        ArrayList<String> rlLinks = new ArrayList<>();
        for (Course course : curCourse) {
            rlLinks.addAll(course.getReadingLists());
            for (String link : course.getReadingLists()){
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
        }

        return null;
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);

        mCourseHolder.mCitationsListView.setAdapter(new ArrayAdapter<String>(mCourseHolder.mContext, R.layout.item_book_list));

        mCourseHolder.mCitationsProgBar.setVisibility(View.GONE);
    }
}
