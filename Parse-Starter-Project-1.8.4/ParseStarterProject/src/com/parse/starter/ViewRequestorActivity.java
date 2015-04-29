package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;


public class ViewRequestorActivity extends Activity {

    String userId;
    String jobId;
    boolean isJobDoer = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_requestor);
        Intent intent = getIntent();
        userId = intent.getStringExtra("userID");
        jobId = intent.getStringExtra("jobID");
        displayUserInfoParse();

        //displayUserDetails();
    }

    public void getJobInfoParse() {
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

    public void displayUserInfoParse() {
        //Query Parse for the user that requested the job, so we can display their name
        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
        userQuery.getInBackground(userId, new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser o, ParseException e) {
                /*final String userNameParse = o.getUsername();*/
                String userName = o.getUsername();
                TextView username = (TextView) findViewById(R.id.usernameTextView);
                username.append(userName);

                String userEmail = "Viewable upon acceptance.";
                String userPhone = "Viewable upon acceptance.";
               if(isJobDoer) {
                   userEmail = o.getString("email");
                   userPhone = o.getString("phone");

                }
                TextView email = (TextView) findViewById(R.id.emailTextView);
                email.append(userEmail);

                TextView phone = (TextView) findViewById(R.id.phoneNumberTextView);
                phone.append(userPhone);

            }
        });
    }

    private void selectAsJobDoer() {
        isJobDoer = true;
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

}
