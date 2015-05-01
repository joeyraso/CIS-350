package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class JobDetailsActivity extends Activity {
    Job job;
    String jobId;
    boolean isJobDoer = false;

    String userId = ParseUser.getCurrentUser().getObjectId();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        Intent intent = getIntent();
        jobId = intent.getStringExtra("jobID");
        Log.v("DEBUG:", "commencing.");

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

        ParseQuery<Job> query2 = new ParseQuery("Job");
        try {
            job = (Job) query2.get(jobId);
            String doer = job.getString("jobDoer");
            if (userId.equals(doer)) {
                isJobDoer = true;
            }
        } catch (ParseException e) {
            Log.v("Parse Exception:", "While trying to get job");
        }


        TextView buttonTitle = (TextView) findViewById(R.id.Request);
        if (isJobDoer) {
            Log.v("DEBUG:", "isJobDoer in onComplete");
            //This job belongs to them.
            buttonTitle.setText("Completed.");
        } else {
            Log.v("DEBUG:", "is not job doer in onComplete");
            buttonTitle.setText("Request Job");
        }

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

    public void updateJob(View view) {
        if (isJobDoer) {
            isJobDoer();
        } else {
            jobRequesting();
        }
    }

    private void isJobDoer() {
        //Find the job in question
        ParseQuery<Job> query = new ParseQuery("Job");
        query.getInBackground(jobId, new GetCallback<Job>() {
            @Override
            public void done(Job o, ParseException e) {
                String posterID = o.getJobPoster();
                ParseGeoPoint location = new ParseGeoPoint(30, 30); //dummy value
                TextView locationText = (TextView) findViewById(R.id.userLocation);
                locationText.setText(location.toString());

                //Find the job poster.
                ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
                userQuery.getInBackground(userId, new GetCallback<ParseUser>() {
                    @Override
                    public void done(ParseUser o, ParseException e) {
                        String email = o.getString("email");
                        TextView emailText = (TextView) findViewById(R.id.contactInfo);
                        emailText.setText(email);

                        String phone = o.getString("phone");
                        TextView phoneText = (TextView) findViewById(R.id.userPhoneNumber);
                        phoneText.setText(phone);

                    }
                });

            }
        });

    }

    private void jobRequesting() {
        //Allow the user to request this job
        //Update both the User and the Jobs
        addJobToMyRequested(); //current user gets this job added to requests
        addUserToJobRequestors();//add current user to jobs list of requestors
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }


    private void addJobToMyRequested() {
        //Updates myRequestedJobs list in the User
        List<String> myRequestedJobs = ParseUser.getCurrentUser().getList("myRequestedJobs");
        if (myRequestedJobs == null) {
            ParseUser.getCurrentUser().put("myRequestedJobs", new ArrayList<String>());
            myRequestedJobs = ParseUser.getCurrentUser().getList("myRequestedJobs");
        }

        myRequestedJobs.add(jobId);
        ParseUser.getCurrentUser().saveInBackground();
        return;
    }

    private void addUserToJobRequestors() {
        //Updates jobRequestors list in the Job
        List<String> currRequestors = job.getList("jobRequestors");
        if (currRequestors == null) {
            job.put("jobRequestors", new ArrayList<String>());
            currRequestors = job.getList("jobRequestors");
        }
        currRequestors.add(ParseUser.getCurrentUser().getObjectId());
        job.put("jobRequestors", currRequestors);
        //Log.v("DEBUG", "ADDED!");
        job.saveInBackground();
        return;
    }

    public void requestJobDetails(View view) {
        //Appends job poster email address, so the user can ask for additional job details before
        //requesting the job
        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
        userQuery.getInBackground(job.getJobPoster(), new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser o, ParseException e) {
                String email = o.getEmail();
                TextView emailField = (TextView) findViewById(R.id.contactInfo);
                emailField.setText(email);
            }
        });

    }

    //button logic to go to the homepage screen
    public void displayHomepage(View view) {
        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);
    }

}
