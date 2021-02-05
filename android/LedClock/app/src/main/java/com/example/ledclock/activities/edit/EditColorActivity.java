package com.example.ledclock.activities.edit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ledclock.R;
import com.skydoves.colorpickerview.ColorPickerView;

public class EditColorActivity extends AppCompatActivity {
    /* Constants */
    public static final String EXTRA_TITLE = "RESULT_COLOR_TITLE";
    public static final String EXTRA_DESCRIPTION = "RESULT_COLOR_DESCRIPTION";
    public static final String EXTRA_VALUE = "RESULT_COLOR_VALUE";
    public static final String EXTRA_RESULT = "RESULT_COLOR_RESULT";

    /* Activity components */
    private ImageButton mCloseBtn;
    private Button mSaveBtn;
    private TextView mDescription;
    private ColorPickerView mValue;

    // Called upon starting application
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_color);

        // Fetch data from intent
        Intent intent = getIntent();
        String title = intent.getStringExtra(EXTRA_TITLE);
        String description = intent.getStringExtra(EXTRA_DESCRIPTION);
        int value = intent.getIntExtra(EXTRA_VALUE, 0);

        // Set description
        mDescription = (TextView) findViewById(R.id.ResultColorDescriptionText);
        mDescription.setText(description);

        // Set value
        mValue = (ColorPickerView) findViewById(R.id.ResultColorValuePicker);
        mValue.setInitialColor(value);

        // Return canceled upon clicking close button
        mCloseBtn = (ImageButton) findViewById(R.id.ResultColorCloseButton);
        mCloseBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });

        // Return value upon clicking save button
        mSaveBtn = (Button) findViewById(R.id.ResultColorSaveButton);
        mSaveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(EXTRA_RESULT, mValue.getColor() & 0xFFFFFF);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}