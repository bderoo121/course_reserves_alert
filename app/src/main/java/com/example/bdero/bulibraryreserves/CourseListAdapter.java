package com.example.bdero.bulibraryreserves;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bdero.bulibraryreserves.async.CitationResponse.Citation;
import com.example.bdero.bulibraryreserves.async.CourseResponse.Course;
import com.example.bdero.bulibraryreserves.async.CourseResponse.Instructor;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by bdero on 3/19/2018.
 */

public class CourseListAdapter extends RecyclerView.Adapter<CourseHolder> {

    private static final String LOG_TAG = CourseListAdapter.class.getSimpleName();

    private Context mContext;

    // The ArrayList is for indexed structure, the hashmap because multiple courses
    // can have the same course code.
    public LinkedHashMap<String, ArrayList<Course>> mDataSet;
    private ArrayList<String> mCourseCodes;

    CourseListAdapter(Context context) {
        mDataSet = new LinkedHashMap<>();
        mCourseCodes = new ArrayList<>();
        mContext = context;
    }

    public CourseListAdapter(Context context, LinkedHashMap<String, ArrayList<Course>> courseData) {
        this(context);
        mDataSet = courseData;
        mCourseCodes = new ArrayList<>(mDataSet.keySet());
    }

    @Override
    public CourseHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Create a new view
        int classLayout = R.layout.course_list_item;

        // Do not attach immediately when using a Viewholder model.
        boolean shouldAttachImmediately = false;

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(classLayout, parent, shouldAttachImmediately);

        return new CourseHolder(v, this);
    }

    @Override
    public void onBindViewHolder(CourseHolder holder, int position) {
        // Use the course code ArrayList to pick out the course info from the data.
        String courseCodeKey = mCourseCodes.get(position);
        holder.mClassCode.setText(courseCodeKey);

        // Use the course code as a key to fetch all relevant course objects
        holder.mCourse = mDataSet.get(courseCodeKey);

        // Use the title listed for the first object under the course
        // TODO: Implement a better way to handle multiple/missing course titles.
        String courseName = holder.mCourse.get(0).getName();
        holder.mCourseName.setText(courseName);

        // TODO: Implement a better way to handle multiple/missing course instructors.
        Instructor[] courseInstructors = holder.mCourse.get(0).getInstructors();
        if (courseInstructors.length > 0) {

            // CourseInstructor array object: [prim_identifier, first_name, last_name]
            String firstInstructor = "Prof. " + courseInstructors[0].getLast_name();

            if (courseInstructors.length > 1) {
                holder.mClassInstructor.setText(firstInstructor + ", ...");
            } else {
                holder.mClassInstructor.setText(firstInstructor);
            }
        }
        holder.mCitationAdapter.mDataset = getCitations(courseCodeKey);
        holder.mCitationAdapter.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        if (mDataSet == null) {
            return 0;
        } else {
            return mDataSet.size();
        }
    }

    public ArrayList<String> getCourseCodes() {
        return mCourseCodes;
    }

    public void addNewCourse(String code) {
        mDataSet.put(code, new ArrayList<Course>());
        mCourseCodes.add(code);
    }

    public void addCourseInfo(String code, Course course) {
        mDataSet.get(code).add(course);
    }

    public void clear() {
        final int size = mDataSet.size();
        mDataSet.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void updateCourseInfo(String code, ArrayList<Course> courses){
        mDataSet.get(code).clear();
        mDataSet.get(code).addAll(courses);
    }

    private ArrayList<Citation> getCitations(String courseCode){
        ArrayList<Course> courses = mDataSet.get(courseCode);
        ArrayList<Citation> output = new ArrayList<>();
        for (Course course : courses){
            output.addAll(course.getCitations());
        }
        return output;
    }
}
