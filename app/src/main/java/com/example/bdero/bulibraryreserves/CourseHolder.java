package com.example.bdero.bulibraryreserves;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bdero.bulibraryreserves.async.CitationResponse.Citation;
import com.example.bdero.bulibraryreserves.async.CitationsAsyncTask;
import com.example.bdero.bulibraryreserves.async.CourseResponse.Course;

import java.util.ArrayList;

public class CourseHolder extends RecyclerView.ViewHolder {

    private static final String HOLDER_LOG_TAG = CourseHolder.class.getSimpleName();

    ArrayList<Course> mCourse;
    ArrayList<Citation> mCitationList;

    Context mContext;
    CourseListAdapter mParent;
    boolean mAreCitationsExpanded;
    boolean mAreCitationsLoaded;

    View mCourseWrapper;
    TextView mClassCode;
    TextView mCourseName;
    TextView mClassInstructor;
    ImageButton mExpandCitationsButton;

    View mCitationsWrapper;
    RecyclerView mCitationsRecyclerView;
    RecyclerView.LayoutManager mLayoutManager; //TODO: Can this be a static variable?
    CitationAdapter mCitationAdapter;
    ProgressBar mCitationsProgBar;

    CourseHolder(View holder, CourseListAdapter parent) {
        super(holder);

        mContext = holder.getContext();
        mParent = parent;
        mAreCitationsExpanded = false;
        mAreCitationsLoaded = false;

        mCourseWrapper = holder.findViewById(R.id.cl_course_wrapper);
        mClassCode = holder.findViewById(R.id.tv_class_code);
        mCourseName = holder.findViewById(R.id.tv_class_name);
        mClassInstructor = holder.findViewById(R.id.tv_class_instructor);
        mExpandCitationsButton = holder.findViewById(R.id.ib_expand_citation_list);

        mCitationsWrapper = holder.findViewById(R.id.fl_citation_wrapper);
        mCitationsRecyclerView = holder.findViewById(R.id.rv_citation_list);
        mLayoutManager = new LinearLayoutManager(mContext);
        mCitationAdapter = new CitationAdapter(mContext);
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

        //TODO: Setup the RecyclerView with an OnClickListener.
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
            Course[] unloadedCourses = getUnloadedCourses(mCourse);
            if (unloadedCourses.length > 0){
                new CitationsAsyncTask(mContext, this).execute(unloadedCourses);
            }

        }
    }

    private Course[] getUnloadedCourses(ArrayList<Course> courses) {
        ArrayList<Course> output = new ArrayList<>();
        for (Course course : courses){
            if (!course.areCitationsLoaded()){
                output.add(course);
            }
        }
        return output.toArray(new Course[0]);
    }

    public void setProgBarVisibility(int toState) {
        if (toState == View.VISIBLE || toState == View.INVISIBLE || toState == View.GONE) {
            mCitationsProgBar.setVisibility(toState);
        }
    }

    public CitationAdapter getCitationAdapter() {
        return mCitationAdapter;
    }


    public void updateCourses(ArrayList<Course> courses) {
        mCourse.clear();
        mCourse.addAll(courses);
        String courseCode = courses.get(0).getCode();
        mParent.mDataSet.put(courseCode,courses);
        int courseIndex = mParent.getCourseCodes().indexOf(courseCode);
        mParent.notifyItemChanged(courseIndex);
    }
}
