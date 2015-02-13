package com.example.vikramjeet.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vikramjeet.gridimagesearch.R;
import com.example.vikramjeet.models.Image;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Vikramjeet on 2/10/15.
 */
public class ImageAdapter extends ArrayAdapter<Image> {

    private static class ViewHolder {
        ImageView ivImage;
        TextView tvImageTitle;
    }

    public ImageAdapter(Context context, List<Image> images) {
        super(context, R.layout.item_image, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the image object
        Image image = getItem(position);

        //View look up cache stored in tag;
        ViewHolder viewHolder = null;
        if (convertView == null) {  //If recycled view is not available
            viewHolder = new ViewHolder();
            // create new convert view
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image, parent, false);
            // Lookup the views
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
            viewHolder.tvImageTitle = (TextView) convertView.findViewById(R.id.tvImageTitle);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Clear out the user photo imageview (in case previous image is still in there)
        viewHolder.ivImage.setImageResource(0);

        // Insert the image using Picasso (asynchronous)
        Picasso.with(getContext())
                .load(image.getThumbnailUrl())
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_error)
                .into(viewHolder.ivImage);

        // Set image title
        viewHolder.tvImageTitle.setText(Html.fromHtml(image.getTitle()));

        return convertView;
    }
}
