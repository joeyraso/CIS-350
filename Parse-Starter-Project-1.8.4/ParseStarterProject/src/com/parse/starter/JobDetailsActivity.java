package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;


import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.GetCallback;
import android.widget.TextView;

import java.util.List;


public class JobDetailsActivity extends Activity {
    Job job;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        Intent intent = getIntent();
        String jobId = intent.getStringExtra("jobID");

        //Query Parse
        ParseQuery<Job> query = new ParseQuery("Job");
        query.getInBackground(jobId, new GetCallback<Job>() {
            @Override
            public void done(Job o, ParseException e) {
                TextView jobNameTextObject = (TextView) findViewById(R.id.detailsName);
                TextView jobDescriptionTextObject = (TextView) findViewById(R.id.detailsDescription);
                TextView startDateTextObject = (TextView) findViewById(R.id.detailsStartDate);
                TextView endDateTextObject = (TextView) findViewById(R.id.detailsEndDate);

                jobNameTextObject.setText(o.getJobName());
                jobDescriptionTextObject.setText(o.getJobDescription());
                startDateTextObject.setText(o.getStartDate());
                endDateTextObject.setText(o.getEndDate());
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_job_details, menu);
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
}
