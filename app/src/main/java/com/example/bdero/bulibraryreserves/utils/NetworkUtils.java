package com.example.bdero.bulibraryreserves.utils;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.bdero.bulibraryreserves.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by bdero on 3/14/2018.
 */

public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    public static final int QUERY_COURSES = 23020;
    public static final int FETCH_READING_LISTS = 23021;
    public static final int FETCH_CITATIONS = 23022;

    private static final String ALMA_API_URL = "https://api-na.hosted.exlibrisgroup.com/almaws/v1";
    private static final String PATH_COURSE = "courses";
    private static final String PATH_READING_LISTS = "reading-lists";
    private static final String PATH_CITATIONS = "citations";

    private static final String PARAM_QUERY = "q";
    private static final String PARAM_LIMIT = "limit";
    private static final String PARAM_OFFSET = "offset";
    private static final String PARAM_ORDER_BY = "order_by";
    private static final String PARAM_DIRECTION = "direction";
    public static final String PARAM_FORMAT = "format";
    public static final String PARAM_APIKEY = "apikey";

    /*API key is not included in public repo. Program will not work unless a key is added*/
    public static final int APIKEY_RESOURCE = R.string.apikey;

    private static final int DEFAULT_LIMIT = 50;
    private static final int DEFAULT_OFFSET = 0;
    private static final String DEFAULT_ORDER_BY = "code,section";
    private static final String DIRECTION_ASC = "ASC";
    public static final String FORMAT_JSON = "json";

    @Nullable
    public static URL buildCourseURL(Context context, String queryParameter) {
        String courseQueryString = Uri.parse(ALMA_API_URL).buildUpon()
                        .appendPath(PATH_COURSE)
                        .appendQueryParameter(PARAM_QUERY, "code~" + queryParameter)
                        .appendQueryParameter(PARAM_LIMIT, String.valueOf(DEFAULT_LIMIT))
                        .appendQueryParameter(PARAM_OFFSET, String.valueOf(DEFAULT_OFFSET))
                        .appendQueryParameter(PARAM_ORDER_BY, DEFAULT_ORDER_BY)
                        .appendQueryParameter(PARAM_DIRECTION, DIRECTION_ASC)
                        .appendQueryParameter(PARAM_FORMAT, FORMAT_JSON)
                        .appendQueryParameter(PARAM_APIKEY, context.getString(APIKEY_RESOURCE))
                        .build().toString();
        try {
            return new URL(courseQueryString);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Malformed URL: " + courseQueryString);
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static URL formatURL(Context context, String baseURLString){
        String formattedURLString = Uri.parse(baseURLString).buildUpon()
                .appendQueryParameter(PARAM_FORMAT, FORMAT_JSON)
                .appendQueryParameter(PARAM_APIKEY, context.getString(APIKEY_RESOURCE))
                .build().toString();
        try{
            return new URL(formattedURLString);
        } catch (MalformedURLException e){
            Log.e(LOG_TAG, "Unparseable URL: " + formattedURLString);
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static URL buildReadingListURL(Context context, String courseURLString){
        String readingListUrlString = Uri.parse(courseURLString).buildUpon()
                .appendPath(PATH_READING_LISTS)
                .appendQueryParameter(PARAM_FORMAT, FORMAT_JSON)
                .appendQueryParameter(PARAM_APIKEY, context.getString(APIKEY_RESOURCE))
                .build().toString();
        try{
            return new URL(readingListUrlString);
        } catch (MalformedURLException e){
            Log.e(LOG_TAG,"Unparseable URL: " + readingListUrlString);
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static URL buildCitationsURL (Context context, String baseURLString){
        String citationsUrlString = Uri.parse(baseURLString).buildUpon()
                .appendPath(PATH_CITATIONS)
                .appendQueryParameter(PARAM_FORMAT, FORMAT_JSON)
                .appendQueryParameter(PARAM_APIKEY, context.getString(APIKEY_RESOURCE))
                .build().toString();
        try{
            return new URL(citationsUrlString);
        } catch (MalformedURLException e){
            Log.e(LOG_TAG, "Unparseable URL: " + citationsUrlString);
            e.printStackTrace();
            return null;
        }
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {

            urlConnection.disconnect();
        }
    }
}
