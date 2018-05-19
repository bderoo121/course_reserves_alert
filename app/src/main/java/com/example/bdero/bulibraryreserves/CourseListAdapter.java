package com.example.bdero.bulibraryreserves;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bdero.bulibraryreserves.async.CitationsAsyncTask;
import com.example.bdero.bulibraryreserves.async.CourseResponse.Course;
import com.example.bdero.bulibraryreserves.async.CourseResponse.Instructor;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by bdero on 3/19/2018.
 */

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseHolder> {

    private static final String LOG_TAG = CourseListAdapter.class.getSimpleName();

    public LinkedHashMap<String, ArrayList<Course>> mDataSet;

    //Will hold the course titles. Important for relating adapter position to the data.
    private ArrayList<String> mCourseCodes;
    private Context mContext;

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
        if (courseInstructors.length > 0) {

            // CourseInstructor array object: [prim_identifier, first_name, last_name]
            String firstInstructor = "Prof. " + courseInstructors[0].getLast_name();

            if (courseInstructors.length > 1) {
                holder.mClassInstructor.setText(firstInstructor + ", ...");
            } else {
                holder.mClassInstructor.setText(firstInstructor);
            }
        }
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

    public static class CourseHolder extends RecyclerView.ViewHolder {

        private static final String HOLDER_LOG_TAG = CourseHolder.class.getSimpleName();

        boolean mAreCitationsExpanded = false;
        boolean mAreCitationsLoaded = false;

        Context mContext;
        ArrayList<Course> mCourse;

        View mCourseWrapper;
        TextView mClassCode;
        TextView mCourseName;
        TextView mClassInstructor;
        ImageButton mExpandCitationsButton;

        View mCitationsWrapper;
        RecyclerView mCitationsRecyclerView;
        LayoutManager mLayoutManager; //TODO: Can this be a static variable?
        CitationAdapter mCitationAdapter;
        ProgressBar mCitationsProgBar;

        private CourseHolder(View holder) {
            super(holder);

            mContext = holder.getContext();

            mCourseWrapper = holder.findViewById(R.id.cl_course_wrapper);
            mClassCode = holder.findViewById(R.id.tv_class_code);
            mCourseName = holder.findViewById(R.id.tv_class_name);
            mClassInstructor = holder.findViewById(R.id.tv_class_instructor);
            mExpandCitationsButton = holder.findViewById(R.id.ib_expand_citation_list);

            mCitationsWrapper = holder.findViewById(R.id.fl_citation_wrapper);
            mCitationsRecyclerView = holder.findViewById(R.id.rv_citation_list);
            mLayoutManager = new LinearLayoutManager(mContext);
            mCitationAdapter = new CitationAdapter();
            mCitationsProgBar = holder.findViewById(R.id.progbar_citation_search);

            mCitationsRecyclerView.setLayoutManager(mLayoutManager);
            mCitationsRecyclerView.setAdapter(mCitationAdapter);

            //When the course information wrapper is clicked on, expand the book information.
            mCourseWrapper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v(HOLDER_LOG_TAG, "Course wrapper clicked on.");
                    toggleCitationView();
                }
            });
        }

        private void toggleCitationView() {
            if (mAreCitationsExpanded) {
                //Citations visible. Shrink the citation list back.
                mCitationsWrapper.setVisibility(View.GONE);
                mAreCitationsExpanded = false;

            } else {
                // Citations invisible. If first time opening, load data. Expand the citation list.
                mCitationsWrapper.setVisibility(View.VISIBLE);
                mAreCitationsExpanded = true;
                if (!mAreCitationsLoaded) {
                    //TODO: Initiate the CitationsAsyncTask
                    Course[] courseArray = mCourse.toArray(new Course[0]);
                    new CitationsAsyncTask(mContext, this).execute(courseArray);
                }

            }
        }

        public void setProgBarVisibility(int toState) {
            if (toState == View.VISIBLE || toState == View.INVISIBLE || toState == View.GONE) {
                mCitationsProgBar.setVisibility(toState);
            }
        }
    }
}
