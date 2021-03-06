package com.example.bdero.bulibraryreserves;

import android.os.AsyncTask;
import android.util.Log;

import com.example.bdero.bulibraryreserves.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class FetchCourseAsyncTask extends AsyncTask<URL,Course,Void> {

    private static final String TOTAL_RECORD_COUNT = "total_record_count"; // Represents an integer number of results found, saved as a String;
    private static final String COURSE_ARRAY = "course";  // Name of a JSON array of courses found.
    private static final String COURSE_CODE = "code";  // Name of a JSON array of courses found.
    private static final String COURSE_STATUS = "status";  // Name for an indicator whether course is "ACTIVE" or "INACTIVE"
    private static final String STATUS_ACTIVE = "ACTIVE";
    private static final String STATUS_INACTIVE = "INACTIVE";
    private static final String READING_LISTS = "reading_list";  // Name of a JSON array of reading lists attached to a course.

    private final CourseListActivity mCourseListActivity;

    private static final String COURSE_TASK_LOG_TAG = FetchCourseAsyncTask.class.getSimpleName();

    FetchCourseAsyncTask(CourseListActivity courseListActivity) {
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
            String courseList = NetworkUtils.getResponseFromHttpUrl(urls[0]);
            JSONObject courseResults = new JSONObject(courseList);
            if (Integer.parseInt(courseResults.getString(TOTAL_RECORD_COUNT)) == 0){
                Log.d(COURSE_TASK_LOG_TAG,"Valid results, but no records found.");
                return null;
            }
            HashMap<String,ArrayList<Course>> output = new HashMap<>();
            /*
             * Data will be structured as follows:
             * {"MA123": JSONArray of course items with code MA123
             *      [{course info for MA123-1, "reading_list":[]},{course info for MA123-2, "reading_list":[]}],
             *  "BI211": JSONArray of course items with code BI211
             *      [{course info for MA123-1, "reading_list":[]}]
             * }
             */
            JSONArray courseListJSON = courseResults.getJSONArray(COURSE_ARRAY);
            Log.d(COURSE_TASK_LOG_TAG, "Number of Courses: " + courseListJSON.length());

            for (int course = 0; course < courseListJSON.length(); course++) {

                // Fetch the loop's current course from the received data
                JSONObject curCourse = courseListJSON.getJSONObject(course);
                Log.d(COURSE_TASK_LOG_TAG, "Current course JSON: " + curCourse.toString());

                // Ignore any inactive courses
                if (curCourse.getString(COURSE_STATUS).equals(STATUS_ACTIVE)) {

                    // Fetch the current course's code for easy access
                    String curCourseCode = curCourse.getString(COURSE_CODE);
                    Log.d(COURSE_TASK_LOG_TAG, "Encountered course " + curCourseCode);

                    // If this is the first time encountering that course code, add a new
                    // course JSONObject to the output
                    if (!output.containsKey(curCourseCode)){
                        output.put(curCourseCode, new ArrayList<Course>());
                    }

                    Course c = new Course(curCourse);

                    // Build upon the given course URL to fetch its attached reading lists.
                    URL readingListsURL = NetworkUtils.buildReadingListURL(mCourseListActivity, c.getCourseLink());
                    JSONObject readingListsJSON = new JSONObject(NetworkUtils.getResponseFromHttpUrl(readingListsURL));

                    for (int indReadingList = 0; indReadingList < readingListsJSON.getJSONArray(READING_LISTS).length(); indReadingList++){
                        //TODO: Check for visibility or status.
                        JSONObject curReadingList = readingListsJSON.getJSONArray(READING_LISTS).getJSONObject(indReadingList);

                        // Add each reading list to the course
                        c.addReadingList(curReadingList.getString("link"));
                    }

                    Log.d(COURSE_TASK_LOG_TAG, "Course item: " + curCourse.toString());
                    // Put course information into its new container.
                    output.get(curCourseCode).add(c);
                    Log.d(COURSE_TASK_LOG_TAG, "Hash map: " + output.toString());

                    publishProgress(c);
                }
            }
        } catch (IOException e){
            Log.e(COURSE_TASK_LOG_TAG,"IO error");
            e.printStackTrace();
            return null;
        } catch (JSONException e){
            Log.e(COURSE_TASK_LOG_TAG, "Error parsing JSON from result String");
            return null;
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Course... courseValues) {
        super.onProgressUpdate(courseValues);
        for (Course courseItem : courseValues) {
            String code = courseItem.getCourseCode();

            // If adapter doesn't have this course, add it to the list.
            if (!mCourseListActivity.mAdapter.getCourseCodes().contains(code)){
                mCourseListActivity.mAdapter.addNewCourse(code);
            }

            // Reset the RecyclerView with the new adapter
            // TODO: Make this code work using notifyDataSetChanged to prevent reloading everything.
            mCourseListActivity.mAdapter.addCourseInfo(code, courseItem);
            mCourseListActivity.mCourseRecyclerView.setAdapter(mCourseListActivity.mAdapter);
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        mCourseListActivity.updateViewVisibility(mCourseListActivity.POST_SEARCH);

    }
}
