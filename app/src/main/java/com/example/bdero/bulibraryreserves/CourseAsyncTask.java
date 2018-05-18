package com.example.bdero.bulibraryreserves;

import android.nfc.FormatException;
import android.os.AsyncTask;
import android.util.Log;

import com.example.bdero.bulibraryreserves.CourseResponse.Course;
import com.example.bdero.bulibraryreserves.RLResponse.ReadingList;
import com.example.bdero.bulibraryreserves.utils.NetworkUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class CourseAsyncTask extends AsyncTask<URL,Course,Void> {

    private static final String TOTAL_RECORD_COUNT = "total_record_count"; // Represents an integer number of results found, saved as a String;
    private static final String COURSE_ARRAY = "course";  // Name of a JSON array of courses found.
    private static final String COURSE_CODE = "code";  // Name of a JSON array of courses found.
    private static final String COURSE_STATUS = "status";  // Name for an indicator whether course is "ACTIVE" or "INACTIVE"
    private static final String STATUS_ACTIVE = "ACTIVE";
    private static final String STATUS_INACTIVE = "INACTIVE";
    private static final String READING_LISTS = "reading_list";  // Name of a JSON array of reading lists attached to a course.

    private final CourseListActivity mCourseListActivity;
    private boolean mSucceeded = false;

    private static final String COURSE_TASK_LOG_TAG = CourseAsyncTask.class.getSimpleName();

    CourseAsyncTask(CourseListActivity courseListActivity) {
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
                throw new FormatException("No courses match query " + urls[0].toString());
            }
            for (Course course : courseResponse.getCourses()){
                //Only continue if the course is still active.
                if (course.getStatus().equals(STATUS_ACTIVE)){
                    //Initialize the array of reading list links
                    course.setRLLinks(new ArrayList<String>());

                    URL readingListsURL = NetworkUtils.buildReadingListURL(mCourseListActivity, course.getLink());
                    String readingLists = NetworkUtils.getResponseFromHttpUrl(readingListsURL);
                    RLResponse rlResponse = new Gson().fromJson(readingLists, RLResponse.class);

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
        } catch (FormatException e){
            Log.d(COURSE_TASK_LOG_TAG, e.getMessage());
            mSucceeded = false;
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Course... courseValues) {
        super.onProgressUpdate(courseValues);
        for (Course courseItem : courseValues){
            CourseListAdapter adapter = mCourseListActivity.mAdapter;
            String code = courseItem.getCode();

            if (!adapter.getCourseCodes().contains(code)){
                adapter.addNewCourse(code);
            }

            adapter.addCourseInfo(code, courseItem);
            adapter.notifyItemInserted(adapter.getCourseCodes().indexOf(code));
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
