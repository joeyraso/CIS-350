package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;


public class ViewRequestorActivity extends Activity {

    String userId;
    String jobId;
    boolean isJobDoer = false;
    Job job;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_requestor);
        Intent intent = getIntent();
        userId = intent.getStringExtra("userID");
        jobId = intent.getStringExtra("jobID");

        ParseQuery<Job> query = new ParseQuery("Job");
        try {
            job = (Job) query.get(jobId);

            if (userId.equals(job.get("jobDoer"))) {
                Log.v("Debug:", "I am the job doer");
                isJobDoer = true;
            }

        } catch (ParseException e) {
            Log.v("Parse Exception:", "While trying to get job");
        }



        displayUserInfoParse();

        //displayUserDetails();
    }

    public void displayUserInfoParse() {
        Log.v("Debug:", "calls display");
        //Query Parse for the user that requested the job, so we can display their name
        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
        userQuery.getInBackground(userId, new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser o, ParseException e) {
                /*final String userNameParse = o.getUsername();*/
                String userName = o.getUsername();
                TextView username = (TextView) findViewById(R.id.usernameTextView);
                username.append(userName);

                String userEmail;
                String userPhone;
               if(isJobDoer) {
                   Log.v("Debug", "Yes I am the jobdoer");
                   userEmail = o.getString("email");
                   userPhone = o.getString("phone");
                } else {
                   userEmail = "Viewable upon acceptance.";
                   userPhone = "Viewable upon acceptance.";
               }

                TextView email = (TextView) findViewById(R.id.emailTextView);
                email.append(userEmail);

                TextView phone = (TextView) findViewById(R.id.phoneNumberTextView);
                phone.append(userPhone);

            }
        });
    }

    public void selectAsJobDoer(View view) {
        //update the Job object
        job.put("jobDoer", userId);
        job.put("jobStatus", "inProgress");
        job.saveInBackground();
        isJobDoer = true;

        String jobName = job.getString("jobName");
        String notification = "You have been selected to complete " + jobName;
        NotificationsManager.notifyUser(userId, notification);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_requestor, menu);
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

    //button logic to go to the homepage screen
    public void displayHomepage(View view) {
        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);
    }

}
