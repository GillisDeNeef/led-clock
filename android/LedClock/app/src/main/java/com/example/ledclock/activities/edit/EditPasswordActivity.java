package com.example.ledclock.activities.edit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ledclock.R;

public class EditPasswordActivity extends AppCompatActivity {
    /* Constants */
    public static final String EXTRA_TITLE = "RESULT_PASSWORD_TITLE";
    public static final String EXTRA_DESCRIPTION = "RESULT_PASSWORD_DESCRIPTION";
    public static final String EXTRA_VALUE = "RESULT_PASSWORD_VALUE";
    public static final String EXTRA_RESULT = "RESULT_PASSWORD_RESULT";

    /* Activity components */
    private ImageButton mCloseBtn;
    private Button mSaveBtn;
    private TextView mDescription;
    private EditText mPassword;

    // Called upon starting application
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        // Fetch data from intent
        Intent intent = getIntent();
        String title = intent.getStringExtra(EXTRA_TITLE);
        String description = intent.getStringExtra(EXTRA_DESCRIPTION);
        String value = intent.getStringExtra(EXTRA_VALUE);

        // Set description
        mDescription = (TextView) findViewById(R.id.ResultPasswordDescriptionText);
        mDescription.setText(description);

        // Set value
        mPassword = (EditText) findViewById(R.id.ResultPasswordPassword);
        mPassword.setHint(title);
        mPassword.setText(value);

        // Return canceled upon clicking close button
        mCloseBtn = (ImageButton) findViewById(R.id.ResultPasswordCloseButton);
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
        mSaveBtn = (Button) findViewById(R.id.ResultPasswordSaveButton);
        mSaveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(EXTRA_RESULT, mPassword.getText().toString());
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        // Move focus to edittext
        mPassword.requestFocus();
        mPassword.setCursorVisible(true);
    }
}