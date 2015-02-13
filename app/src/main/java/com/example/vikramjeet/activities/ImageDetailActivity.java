package com.example.vikramjeet.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.vikramjeet.gridimagesearch.R;
import com.example.vikramjeet.models.Image;
import com.squareup.picasso.Picasso;

public class ImageDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        // Pull image out of the intent
        Image image = (Image) getIntent().getSerializableExtra("image");
        // Get image Url
        String imageUrl = image.getUrl();
        // Find imageView
        ImageView ivImageDetail = (ImageView) findViewById(R.id.ivImageDetail);
        // Load the image into image view using picasso
//        Picasso.with(this)
//                .load(imageUrl)
//                .resize(200, 200)
//                .centerCrop()
//                .placeholder(R.drawable.image_placeholder)
//                .error(R.drawable.image_error)
//                .into(ivImageDetail);

        Picasso.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_error)
                .resize(Integer.parseInt(image.getWidth()), Integer.parseInt(image.getHeight()))
                .into(ivImageDetail);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

//        if (id == R.id.home) {
//            onBackPressed();
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
