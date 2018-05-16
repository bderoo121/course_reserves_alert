package com.example.bdero.bulibraryreserves;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bdero.bulibraryreserves.db.CourseResponse.Course;
import com.example.bdero.bulibraryreserves.db.CourseResponse.Instructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by bdero on 3/19/2018.
 */

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseHolder>{

    private static final String LOG_TAG = CourseListAdapter.class.getSimpleName();

    public LinkedHashMap<String,ArrayList<Course>> mDataSet;

    //Will hold the course titles. Important for relating adapter position to the data.
    private ArrayList<String> mCourseCodes;
    private Context mContext;

    CourseListAdapter(Context context){
        mDataSet = new LinkedHashMap<>();
        mCourseCodes = new ArrayList<>();
        mContext = context;
    }

    public CourseListAdapter(Context context, LinkedHashMap<String,ArrayList<Course>> courseData){
        this(context);
        mDataSet = courseData;
        mCourseCodes = new ArrayList<>(mDataSet.keySet());
    }

    ArrayList<String> getCourseCodes(){
        return mCourseCodes;
    }

    void addNewCourse(String code) {
        mDataSet.put(code, new ArrayList<Course>());
        mCourseCodes.add(code);
    }

    void addCourseInfo(String code, Course course) {
        mDataSet.get(code).add(course);
    }

    @Override
    public CourseHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Create a new view
        int classLayout = R.layout.course_list_item;
        boolean shouldAttachImmediately = false;

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(classLayout, parent, shouldAttachImmediately);

        return new CourseHolder(v);
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
        if (courseInstructors.length > 0){

            // CourseInstructor array object: [prim_identifier, first_name, last_name]
            String firstInstructor = "Prof. " + courseInstructors[0].getLast_name();

            if (courseInstructors.length > 1){
                holder.mClassInstructor.setText(firstInstructor + ", ...");
            } else {
                holder.mClassInstructor.setText(firstInstructor);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mDataSet == null){
            return 0;
        } else {
            return mDataSet.size();
        }
    }

    public void clear() {
        final int size = mDataSet.size();
        mDataSet.clear();
        notifyItemRangeRemoved(0,size);
    }

    public static class CourseHolder extends RecyclerView.ViewHolder{

        private static final String HOLDER_LOG_TAG = CourseHolder.class.getSimpleName();

        boolean mAreCitationsExpanded = false;
        boolean mAreCitationsLoaded = false;
        View mCourseWrapper;
        View mCitationsWrapper;
        ListView mCitationsListView;
        TextView mClassCode;
        TextView mCourseName;
        TextView mClassInstructor;
        ImageButton mExpandCitationsButton;
        ProgressBar mCitationsProgBar;

        Context mContext;
        ArrayList<Course> mCourse;
        ArrayAdapter<String> mCitationsAdapter;

        private CourseHolder(View holder){
            super(holder);

            mContext = holder.getContext();
            mClassCode = holder.findViewById(R.id.tv_class_code);
            mCourseName = holder.findViewById(R.id.tv_class_name);
            mClassInstructor = holder.findViewById(R.id.tv_class_instructor);
            mExpandCitationsButton = holder.findViewById(R.id.ib_expand_citation_list);
            mCitationsListView = holder.findViewById(R.id.lv_citation_list);
            mCitationsProgBar = holder.findViewById(R.id.progbar_citation_search);
            mCitationsWrapper = holder.findViewById(R.id.fl_citation_wrapper);
            mCourseWrapper = holder.findViewById(R.id.cl_course_wrapper);

            //When the course information wrapper is clicked on, expand the book information.
            mCourseWrapper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v(HOLDER_LOG_TAG, "CourseEntity wrapper clicked on.");
                    toggleCitationView();
                }
            });

            // When a citation is clicked, add it to the database of saved citations.
            mCitationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
        }

        void toggleCitationView(){
            if(mAreCitationsExpanded){
                //Citations visible. Shrink the citation list back.
                mCitationsWrapper.setVisibility(View.GONE);
                mAreCitationsExpanded = false;

            } else {
                // Citations invisible. If first time opening, load data. Expand the citation list.
                mCitationsWrapper.setVisibility(View.VISIBLE);
                mAreCitationsExpanded = true;
                if (!mAreCitationsLoaded){
                    //TODO: Initiate the CitationsAsyncTask
                   // new CitationsAsyncTask(mContext,this).execute(mCourse);
                }

            }
        }
    }


}
