package com.example.bdero.bulibraryreserves.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by bdero on 3/13/2018.
 */

public class SubscribedBooksContract {

    /*
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website. A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * Play Store.
     */
    public static final String CONTENT_AUTHORITY = "com.example.bdero.bulibraryreserves";

    /*
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider for the BU Library Reserves app.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /*
     * Possible paths that can be appended to BASE_CONTENT_URI to form valid URIs that
     * BULibReserves can handle.
     */
    public static final String PATH_BOOKS = "books";


    /* Inner class that defines the table contents of the subscribed books table */
    public static final class SubscribedBookEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_BOOKS)
                .build();

        // Internally used name for the subscribed books table.
        public static final String TABLE_NAME = "books";

        public static final String COL_TITLE = "title";
        public static final String COL_AUTHOR = "author";
        public static final String COL_COURSE_CODE = "course_code";
        public static final String COL_COURSE_PROF = "course_prof";




    }

}
