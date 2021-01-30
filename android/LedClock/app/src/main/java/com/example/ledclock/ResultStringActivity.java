package com.example.ledclock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ResultStringActivity extends AppCompatActivity {
    /* Constants */
    public static final String EXTRA_TITLE = "RESULT_STRING_TITLE";
    public static final String EXTRA_DESCRIPTION = "RESULT_STRING_DESCRIPTION";
    public static final String EXTRA_VALUE = "RESULT_STRING_VALUE";
    public static final String EXTRA_RESULT = "RESULT_STRING_RESULT";

    /* Activity components */
    private ImageButton mCloseBtn;
    private Button mSaveBtn;
    private TextView mDescription;
    private EditText mValue;

    // Called upon starting application
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_string);

        // Fetch data from intent
        Intent intent = getIntent();
        String title = intent.getStringExtra(EXTRA_TITLE);
        String description = intent.getStringExtra(EXTRA_DESCRIPTION);
        String value = intent.getStringExtra(EXTRA_VALUE);

        // Set description
        mDescription = (TextView) findViewById(R.id.ResultStringDescriptionText);
        mDescription.setText(description);

        // Set value
        mValue = (EditText) findViewById(R.id.ResultStringValueText);
        mValue.setHint(title);
        mValue.setText(value);

        // Return canceled upon clicking close button
        mCloseBtn = (ImageButton) findViewById(R.id.ResultStringCloseButton);
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
        mSaveBtn = (Button) findViewById(R.id.ResultStringSaveButton);
        mSaveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(EXTRA_RESULT, mValue.getText().toString());
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

    }
}