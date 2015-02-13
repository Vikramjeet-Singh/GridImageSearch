package com.example.vikramjeet.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.etsy.android.grid.StaggeredGridView;
import com.example.vikramjeet.adapters.ImageAdapter;
import com.example.vikramjeet.gridimagesearch.R;
import com.example.vikramjeet.helpers.EndlessScrollListener;
import com.example.vikramjeet.models.Filter;
import com.example.vikramjeet.models.Image;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ImageSearchActivity extends ActionBarActivity {
    private final int REQUEST_CODE = 200;
    public static final String url = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&q=";
    private EditText etQuery;
    private StaggeredGridView gvResults;
    private ArrayList<Image> images;
    private ImageAdapter adapter;
    private String searchURL;
    private AsyncHttpClient httpClient;
    private SearchView searchView;
    private Filter searchFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_search);

        // Set up views
        setupViews();
        // Create Images ArrayList
        images = new ArrayList<Image>();
        // Attach datasource to adapter
        adapter = new ImageAdapter(this, images);
        // Link adapter to gridview
        gvResults.setAdapter(adapter);

        // Attach the listener to the AdapterView onCreate
        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
            }
        });
    }

    // Append more data into the adapter
    public void customLoadMoreDataFromApi(int page) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter

        if (isNetworkAvailable()) {
            fetchImagesWhileScrolling(searchURL, page);
        }
    }


    private void setupViews() {
        gvResults = (StaggeredGridView) findViewById(R.id.gvResults);
        // Set up onClick handler
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Create an Intent
                Intent imageIntent = new Intent(ImageSearchActivity.this, ImageDetailActivity.class);
                // Get the image
                Image image = images.get(position);
                // Pass image result into the Intent
                imageIntent.putExtra("image", image);
                // Start the activity
                startActivity(imageIntent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            searchFilter  =  (Filter) data.getSerializableExtra("Filter");
//            // Toast the name to display temporarily on screen
//            Toast.makeText(this, filter.getImageSize(), Toast.LENGTH_SHORT).show();
        }
    }

    public String applyFilters(String url, String query) {
        url = url + query;
        if (searchFilter != null) {
            if (searchFilter.getImageType() != null) {
                url = url + "&imgtype=" + searchFilter.getImageType();
            }

            if (searchFilter.getImageColor() != null) {
                url = url + "&imgcolor=" + searchFilter.getImageColor();
            }

            if (searchFilter.getImageSize() != null) {
                url = url + "&imgsz=" + searchFilter.getImageSize();
            }

            if (!searchFilter.getSiteFilter().equals("")) {
                url = url + "&as_sitesearch=" + searchFilter.getSiteFilter();
            }
        }

//        Toast.makeText(ImageSearchActivity.this, url, Toast.LENGTH_SHORT).show();
        return url;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Create Network client
                httpClient = new AsyncHttpClient();

                // Create search  url
//                searchURL = url + query;
                searchURL = applyFilters(url, query);


                if(isNetworkAvailable()) {
                    // Fetch images
                    fetchImages(searchURL);
                }


                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            //noinspection SimplifiableIfStatement
            case R.id.action_settings:
                return true;
            case R.id.miFilters:
                showSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Helper methods

    private void showSettings() {
        // Create an Intent
        Intent settingsIntent = new Intent(ImageSearchActivity.this, SettingsActivity.class);
//        settingsIntent.putExtra("Filter", searchFilter);
        // Start the activity
        startActivityForResult(settingsIntent, REQUEST_CODE);
    }

    public void fetchImages(String url) {
        // Make request
        httpClient.get(url, new JsonHttpResponseHandler() {
            // successful response
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("Response", response.toString());
                JSONArray imageListJSON = null;
                try {
                    // Getting imageList json
                    imageListJSON = response.getJSONObject("responseData").getJSONArray("results");
                    // Cleat list for the initial search (not for pagination)
                    images.clear();
//                    Add images to the list
//                    images.addAll(Image.fromJSONArray(imageListJSON));
////                    Refresh data by notifying adapter
//                    adapter.notifyDataSetChanged();

                    // Using another approach of updating adapter itself and hence it will trigger the change to datasource
                    adapter.addAll(Image.fromJSONArray(imageListJSON));

                } catch (JSONException e) {
//                    Log.e("ImageSearchActivity", "Failed to parse response json");
                    e.printStackTrace();
                }
            }

            // failed response
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    public void fetchImagesWhileScrolling(String url, int page) {
        int offset = 8*(page-1);
        String finalURL = url + "&start=" + offset;

        httpClient.get(finalURL, new JsonHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("Response", response.toString());
                JSONArray imageListJSON = null;
                try {
                    // Getting imageList json
                    imageListJSON = response.getJSONObject("responseData").getJSONArray("results");
                    // Using another approach of updating adapter itself and hence it will trigger the change to datasource
                    adapter.addAll(Image.fromJSONArray(imageListJSON));

                } catch (JSONException e) {
                    Log.e("ImageSearchActivity", "Failed to parse response json");
                    e.printStackTrace();
                }
            }

            // failed response
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                 super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
