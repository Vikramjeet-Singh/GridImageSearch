package com.example.vikramjeet.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.vikramjeet.gridimagesearch.R;
import com.example.vikramjeet.helpers.TouchImageView;
import com.example.vikramjeet.models.Image;
import com.squareup.picasso.Picasso;

public class ImageDetailActivity extends ActionBarActivity {

    private TouchImageView ivImageDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        // Pull image out of the intent
        Image image = (Image) getIntent().getSerializableExtra("image");
        // Get image Url
        String imageUrl = image.getUrl();
        // Find imageView
        ivImageDetail = (TouchImageView) findViewById(R.id.ivImageDetail);
        // Load the image into image view using picasso
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

        // Share option selected
        if (id == R.id.miShare) {
//            shareImage();
            return true;
        }
        //  if (id == R.id.home) {
        //     onBackPressed();
        //     return true;
        //  }
        return super.onOptionsItemSelected(item);
    }

    public void shareImage() {
        Drawable mDrawable = ivImageDetail.getDrawable();
        Bitmap mBitmap = ((BitmapDrawable)mDrawable).getBitmap();

        String path = MediaStore.Images.Media.insertImage(ImageDetailActivity.this.getContentResolver(),
                mBitmap, "Image description", null);

        Uri uri = Uri.parse(path);

        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        emailIntent.setType("image/png");
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

}
