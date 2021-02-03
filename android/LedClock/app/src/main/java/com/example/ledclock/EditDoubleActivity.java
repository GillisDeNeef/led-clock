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

public class EditDoubleActivity extends AppCompatActivity {
    /* Constants */
    public static final String EXTRA_TITLE = "RESULT_DOUBLE_TITLE";
    public static final String EXTRA_DESCRIPTION = "RESULT_DOUBLE_DESCRIPTION";
    public static final String EXTRA_VALUE = "RESULT_DOUBLE_VALUE";
    public static final String EXTRA_RESULT = "RESULT_DOUBLE_RESULT";

    /* Activity components */
    private ImageButton mCloseBtn;
    private Button mSaveBtn;
    private TextView mDescription;
    private EditText mValue;

    // Called upon starting application
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_double);

        // Fetch data from intent
        Intent intent = getIntent();
        String title = intent.getStringExtra(EXTRA_TITLE);
        String description = intent.getStringExtra(EXTRA_DESCRIPTION);
        double value = intent.getDoubleExtra(EXTRA_VALUE, 0);

        // Set description
        mDescription = (TextView) findViewById(R.id.ResultFloatDescriptionText);
        mDescription.setText(description);

        // Set value
        mValue = (EditText) findViewById(R.id.ResultFloatValueText);
        mValue.setHint(title);
        mValue.setText(String.valueOf(value));

        // Return canceled upon clicking close button
        mCloseBtn = (ImageButton) findViewById(R.id.ResultFloatCloseButton);
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
        mSaveBtn = (Button) findViewById(R.id.ResultFloatSaveButton);
        mSaveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(EXTRA_RESULT, Double.parseDouble(mValue.getText().toString()));
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        // Move focus to edittext
        mValue.requestFocus();
        mValue.setCursorVisible(true);
    }
}