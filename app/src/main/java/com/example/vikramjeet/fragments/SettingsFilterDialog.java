package com.example.vikramjeet.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.vikramjeet.gridimagesearch.R;
import com.example.vikramjeet.models.Filter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFilterDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFilterDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFilterDialog extends DialogFragment implements TextView.OnClickListener {

    private final int kDefaultSelection = 0;
    private String imageSize;
    private String imageColor;
    private String imageType;
    private EditText etSiteFilter;
    private Button saveButton;
    private Button cancelButton;


    public interface SettingsFilterDialogListener {
        void onFinishFilterDialog(Filter filter);
    }

    public static SettingsFilterDialog newInstance(String title) {
        SettingsFilterDialog frag = new SettingsFilterDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    public SettingsFilterDialog() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings_filter_dialog, container);

        etSiteFilter = (EditText) view.findViewById(R.id.etSiteFilter);

        saveButton = (Button) view.findViewById(R.id.btnSave);
        saveButton.setOnClickListener(this);

        cancelButton = (Button) view.findViewById(R.id.btnCancel);
        cancelButton.setOnClickListener(this);

        // Load spinner view
        Spinner spinnerImageSize = (Spinner) view.findViewById(R.id.spinnerImageSize);
        // Create an ArrayAdapter using the string array using custom spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.image_size_array, R.layout.custom_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.custom_spinner_item);
        // Apply the adapter to the spinner
        spinnerImageSize.setAdapter(adapter);

        spinnerImageSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > kDefaultSelection) {
                    imageSize = (String) parent.getItemAtPosition(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Load spinner view
        Spinner spinnerColorFilter = (Spinner) view.findViewById(R.id.spinnerColorFilter);
        // Create an ArrayAdapter using the string array using custom spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),
                R.array.image_color_array, R.layout.custom_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.custom_spinner_item);
        // Apply the adapter to the spinner
        spinnerColorFilter.setAdapter(adapter2);

        spinnerColorFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > kDefaultSelection) {
                    imageColor = (String) parent.getItemAtPosition(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Load spinner view
        Spinner spinnerImageType = (Spinner) view.findViewById(R.id.spinnerImageType);
        // Create an ArrayAdapter using the string array using custom spinner layout
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getActivity(),
                R.array.image_type_array, R.layout.custom_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.custom_spinner_item);
        // Apply the adapter to the spinner
        spinnerImageType.setAdapter(adapter3);

        spinnerImageSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > kDefaultSelection) {
                    imageType = (String) parent.getItemAtPosition(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == saveButton) {
            String siteFilter = etSiteFilter.getText().toString();
            // Create Filter object
            Filter filter = new Filter(imageSize, imageColor, imageType, siteFilter);

            // Return Filter to activity
            SettingsFilterDialogListener listener = (SettingsFilterDialogListener) getActivity();
            listener.onFinishFilterDialog(filter);
        }
        dismiss();
    }
}
