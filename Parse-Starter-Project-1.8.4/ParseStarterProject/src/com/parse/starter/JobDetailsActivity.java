package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;


import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.GetCallback;
import com.parse.ParseUser;

import android.widget.TextView;

import java.util.List;


public class JobDetailsActivity extends Activity {
    Job job;
    String jobId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        Intent intent = getIntent();
        jobId = intent.getStringExtra("jobID");

        //Query Parse
        ParseQuery<Job> query = new ParseQuery("Job");
        query.getInBackground(jobId, new GetCallback<Job>() {
            @Override
            public void done(Job o, ParseException e) {
                job = o;
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

    public void requestJob(View view) {
        //Update both the User and the Jobs
        addJobToMyRequested(); //current user gets this job added to requests
        addUserToJobRequestors();//add current user to jobs list of requestors


        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }


    private void addJobToMyRequested() {
        //Copy over the old list of reqeustedJobs
        String[] currRequests = (String[]) ParseUser.getCurrentUser().get("myRequestedJobs");
        String[] newRequests = new String[currRequests.length + 1];
        for (int i = 0; i < currRequests.length; i++) {
            newRequests[i] = currRequests[i];
        }

        //This adds the requested job to the list of requestedJobs
        newRequests[newRequests.length - 1] = jobId;

        //update so the User contains the new list of requestedJobs
        ParseUser.getCurrentUser().put("myRequestedJobs", newRequests);
    }

    private void addUserToJobRequestors() {
        String[] currRequestors = (String[]) job.get("jobRequestors");
        String[] newRequestors = new String[currRequestors.length + 1];
        for (int i = 0; i < currRequestors.length; i++) {
            newRequestors[i] = currRequestors[i];
        }

        //This adds the user to the list of jobRequestors
        newRequestors[newRequestors.length - 1] = ParseUser.getCurrentUser().getObjectId();

        //update so the Job contains the new requesotr
       job.put("jobRequestors", newRequestors);
    }
}
