package com.example.vikramjeet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.vikramjeet.gridimagesearch.R;
import com.example.vikramjeet.models.Filter;

public class SettingsActivity extends ActionBarActivity {

    private String imageSize;
    private String imageColor;
    private String imageType;
    private EditText etSiteFilter;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
        saveButton = (Button) findViewById(R.id.btnSave);



        // Load spinner view
        Spinner spinnerImageSize = (Spinner) findViewById(R.id.spinnerImageSize);
        // Create an ArrayAdapter using the string array using custom spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.image_size_array, R.layout.custom_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.custom_spinner_item);
        // Apply the adapter to the spinner
        spinnerImageSize.setAdapter(adapter);

        spinnerImageSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    imageSize = (String) parent.getItemAtPosition(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Load spinner view
        Spinner spinnerColorFilter = (Spinner) findViewById(R.id.spinnerColorFilter);
        // Create an ArrayAdapter using the string array using custom spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.image_color_array, R.layout.custom_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.custom_spinner_item);
        // Apply the adapter to the spinner
        spinnerColorFilter.setAdapter(adapter2);

        spinnerColorFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    imageColor = (String) parent.getItemAtPosition(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Load spinner view
        Spinner spinnerImageType = (Spinner) findViewById(R.id.spinnerImageType);
        // Create an ArrayAdapter using the string array using custom spinner layout
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.image_type_array, R.layout.custom_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.custom_spinner_item);
        // Apply the adapter to the spinner
        spinnerImageType.setAdapter(adapter3);

        spinnerImageSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    imageType = (String) parent.getItemAtPosition(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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

        return super.onOptionsItemSelected(item);
    }

    public void onClickSaveButton(View v) {
        String siteFilter = etSiteFilter.getText().toString();
        // Create Filter object
        Filter filter = new Filter(imageSize, imageColor, imageType, siteFilter);
        // Prepare data Intent
        Intent dataIntent = new Intent();
        // Put Filter object in Intent
        dataIntent.putExtra("Filter", filter);
        // Set and Give the result back
        setResult(RESULT_OK, dataIntent);
        finish();
    }
}
