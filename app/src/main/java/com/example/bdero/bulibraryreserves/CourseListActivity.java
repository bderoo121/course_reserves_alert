package com.example.bdero.bulibraryreserves;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bdero.bulibraryreserves.async.CitationsAsyncTask;
import com.example.bdero.bulibraryreserves.async.CourseAsyncTask;
import com.example.bdero.bulibraryreserves.utils.NetworkUtils;

import java.net.URL;

public class CourseListActivity extends AppCompatActivity {

    private static final String CITATION_TASK_LOG_TAG = CitationsAsyncTask.class.getSimpleName();

    //TODO: Fix course list display error at bottom of screen.

    //States to indicate which views to display in the activity.
    public static final int PRE_SEARCH = 0, DURING_SEARCH = 1, POST_SEARCH = 2, ERR_SEARCH = 3;

    private EditText mSearchBar;
    private TextView mInfoText;
    private ProgressBar mProgressBar;
    RecyclerView mCourseRecyclerView;
    private CourseListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_list_activity);

        mSearchBar = findViewById(R.id.et_class_search_bar);
        mInfoText = findViewById(R.id.tv_info_text);
        mProgressBar = findViewById(R.id.progbar_class_search);
        mCourseRecyclerView = findViewById(R.id.rv_class_search);

        // Use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mCourseRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CourseListAdapter(getBaseContext());
        mCourseRecyclerView.setAdapter(mAdapter);

        // Setup the search button in the IME
        mSearchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String query = v.getText().toString();
                    if (!query.equals("")){
                        Toast.makeText(v.getContext(),"Searching for course: " + query , Toast.LENGTH_LONG)
                                .show();
                        mAdapter.clear();
                        URL fetchCoursesURL = NetworkUtils.buildCourseURL(getApplicationContext(), query);
                        new CourseAsyncTask(CourseListActivity.this).execute(fetchCoursesURL);

                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(),0);

                        return true;
                    }
                }
                return false;
            }
        });
        // Set initial view visibilities
        updateViewVisibility(PRE_SEARCH);
    }

    public void updateViewVisibility(int searchStatus) {
        switch (searchStatus) {
            case PRE_SEARCH:
                mProgressBar.setVisibility(View.INVISIBLE);
                //mCourseRecyclerView.setVisibility(View.INVISIBLE);
                mInfoText.setText(R.string.instruction_text);
                mInfoText.setVisibility(View.VISIBLE);
                break;

            case DURING_SEARCH:
                mInfoText.setVisibility(View.INVISIBLE);
                mInfoText.setText("");
                //mCourseRecyclerView.setVisibility(View.INVISIBLE);
                mCourseRecyclerView.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.VISIBLE);
                break;

            case POST_SEARCH:
                mInfoText.setVisibility(View.INVISIBLE);
                mInfoText.setText("");
                mProgressBar.setVisibility(View.INVISIBLE);

                //Adding the adapter to the RecyclerView is handled in the CourseAsyncTask
                mCourseRecyclerView.setVisibility(View.VISIBLE);
                break;

            case ERR_SEARCH:
                mProgressBar.setVisibility(View.INVISIBLE);
                mCourseRecyclerView.setVisibility(View.INVISIBLE);
                mInfoText.setVisibility(View.VISIBLE);
                mInfoText.setText(R.string.no_matches_found);
                break;

            default:
                throw new IllegalArgumentException("Unknown value: search status - " + searchStatus);
        }
    }

    public CourseListAdapter getAdapter() {
        return mAdapter;
    }


}
