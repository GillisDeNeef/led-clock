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

public class ResultIntActivity extends AppCompatActivity {
    /* Constants */
    public static final String EXTRA_TITLE = "RESULT_INT_TITLE";
    public static final String EXTRA_DESCRIPTION = "RESULT_INT_DESCRIPTION";
    public static final String EXTRA_VALUE = "RESULT_INT_VALUE";
    public static final String EXTRA_RESULT = "RESULT_INT_RESULT";

    /* Activity components */
    private ImageButton mCloseBtn;
    private Button mSaveBtn;
    private TextView mDescription;
    private EditText mValue;

    // Called upon starting application
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_int);

        // Fetch data from intent
        Intent intent = getIntent();
        String title = intent.getStringExtra(EXTRA_TITLE);
        String description = intent.getStringExtra(EXTRA_DESCRIPTION);
        int value = intent.getIntExtra(EXTRA_VALUE, 0);

        // Set description
        mDescription = (TextView) findViewById(R.id.ResultIntDescriptionText);
        mDescription.setText(description);

        // Set value
        mValue = (EditText) findViewById(R.id.ResultIntValueText);
        mValue.setHint(title);
        mValue.setText(String.valueOf(value));

        // Return canceled upon clicking close button
        mCloseBtn = (ImageButton) findViewById(R.id.ResultIntCloseButton);
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
        mSaveBtn = (Button) findViewById(R.id.ResultIntSaveButton);
        mSaveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(EXTRA_RESULT, Integer.parseInt(mValue.getText().toString()));
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        // Move focus to edittext
        mValue.requestFocus();
        mValue.setCursorVisible(true);
    }
}