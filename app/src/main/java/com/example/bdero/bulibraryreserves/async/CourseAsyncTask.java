package com.example.bdero.bulibraryreserves.async;

import android.os.AsyncTask;
import android.util.Log;

import com.example.bdero.bulibraryreserves.CourseListActivity;
import com.example.bdero.bulibraryreserves.CourseListAdapter;
import com.example.bdero.bulibraryreserves.async.CourseResponse.Course;
import com.example.bdero.bulibraryreserves.async.RLResponse.ReadingList;
import com.example.bdero.bulibraryreserves.utils.NetworkUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Performs a request for courses matching a search term.  Updates the CourseAdapter as the results
 * come in with onProgressUpdate, instead of onPostExecute
 */
public class CourseAsyncTask extends AsyncTask<URL,Course,Void> {

    private static final String STATUS_ACTIVE = "ACTIVE";
    private static final String STATUS_INACTIVE = "INACTIVE";

    private final CourseListActivity mCourseListActivity;
    private boolean mSucceeded = false;

    private static final String COURSE_TASK_LOG_TAG = CourseAsyncTask.class.getSimpleName();

    public CourseAsyncTask(CourseListActivity courseListActivity) {
        mCourseListActivity = courseListActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Hide search instructions. Make progress circle visible
        mCourseListActivity.updateViewVisibility(CourseListActivity.DURING_SEARCH);
    }

    @Override
    protected Void doInBackground(URL... urls) {
        // TODO: Use the API given errors instead of using own.
        // TODO: Fix the try catching in this section. Ugh
        try{
            String courseListString = NetworkUtils.getResponseFromHttpUrl(urls[0]);

            CourseResponse courseResponse = new Gson().fromJson(courseListString, CourseResponse.class);
            Log.d(COURSE_TASK_LOG_TAG, courseResponse.toString());
            if (courseResponse.getCount() == 0){
                throw new Exception("No courses match query " + urls[0].toString());
            }
            for (Course course : courseResponse.getCourses()){
                //Only continue if the course is still active.
                if (course.getStatus().equals(STATUS_ACTIVE)){

                    URL readingListsURL = NetworkUtils.buildReadingListURL(mCourseListActivity, course.getCourseLink());
                    String readingLists = NetworkUtils.getResponseFromHttpUrl(readingListsURL);
                    RLResponse rlResponse = new Gson().fromJson(readingLists, RLResponse.class);

                    //Initialize the array of reading list links
                    course.setRLLinks(new ArrayList<String>());

                    if (rlResponse.getReadingLists() != null) {
                        //TODO: Include/Exclude based on the different Statuses, Visibilities, and PublishingStatuses
                        for (ReadingList rl : rlResponse.getReadingLists())  {
                            course.addRLLink(rl.getLink());
                        }
                        publishProgress(course);
                    }
                }
            }
            mSucceeded = true;
        } catch (IOException e){
            Log.e(COURSE_TASK_LOG_TAG,"IO error");
            e.printStackTrace();
            mSucceeded = false;
        } catch (Exception e){
            Log.d(COURSE_TASK_LOG_TAG, e.getMessage());
            mSucceeded = false;
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Course... courseValues) {
        super.onProgressUpdate(courseValues);
        for (Course courseItem : courseValues){
            CourseListAdapter adapter = mCourseListActivity.getAdapter();
            String code = courseItem.getCode();

            if (!adapter.getCourseCodes().contains(code)){
                adapter.addNewCourse(code);
                adapter.addCourseInfo(code, courseItem);
                adapter.notifyItemInserted(adapter.getCourseCodes().indexOf(code));
            } else {
                adapter.addCourseInfo(code, courseItem);
                adapter.notifyItemChanged(adapter.getCourseCodes().indexOf(code));
            }
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        if (mSucceeded){
            mCourseListActivity.updateViewVisibility(mCourseListActivity.POST_SEARCH);
        } else {
            mCourseListActivity.updateViewVisibility(mCourseListActivity.ERR_SEARCH);
        }

    }
}
