package com.example.ledclock.activities.edit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.ledclock.R;

public class EditPercentageActivity extends AppCompatActivity {
    /* Constants */
    public static final String EXTRA_TITLE = "EDIT_PERCENTAGE_TITLE";
    public static final String EXTRA_DESCRIPTION = "EDIT_PERCENTAGE_DESCRIPTION";
    public static final String EXTRA_VALUE = "EDIT_PERCENTAGE_VALUE";
    public static final String EXTRA_RESULT = "EDIT_PERCENTAGE_RESULT";
    public static final String ACTION_PROGRESS = "EDIT_PERCENTAGE_PROGRESS";

    /* Activity components */
    private ImageButton mCloseBtn;
    private Button mSaveBtn;
    private TextView mDescription;
    private SeekBar mValue;

    // Called upon starting application
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_percentage);

        // Fetch data from intent
        Intent intent = getIntent();
        String title = intent.getStringExtra(EXTRA_TITLE);
        String description = intent.getStringExtra(EXTRA_DESCRIPTION);
        int value = intent.getIntExtra(EXTRA_VALUE, 0);

        // Set description
        mDescription = (TextView) findViewById(R.id.ResultPercentageDescriptionText);
        mDescription.setText(description);

        // Set value
        mValue = (SeekBar) findViewById(R.id.ResultPercentageValueSeekBar);
        mValue.setProgress(value);
        mValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    Intent broadcastIntent = new Intent();
                    broadcastIntent.setAction(ACTION_PROGRESS);
                    broadcastIntent.putExtra(EXTRA_TITLE, title);
                    broadcastIntent.putExtra(EXTRA_VALUE, progress);
                    sendBroadcast(broadcastIntent);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // Return canceled upon clicking close button
        mCloseBtn = (ImageButton) findViewById(R.id.ResultPercentageCloseButton);
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
        mSaveBtn = (Button) findViewById(R.id.ResultPercentageSaveButton);
        mSaveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(EXTRA_RESULT, mValue.getProgress());
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}