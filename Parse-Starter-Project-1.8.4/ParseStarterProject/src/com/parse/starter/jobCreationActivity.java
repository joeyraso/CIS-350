package com.parse.starter;

import com.parse.ParseObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import java.util.ArrayList;

public class jobCreationActivity extends Activity {

    EditText jobNameTextObject;
    EditText jobDescriptionTextObject;
    EditText startDateTextObject;
    EditText endDateTextObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_creation_view);

        jobNameTextObject = (EditText) findViewById(R.id.jobNameText);
        jobDescriptionTextObject = (EditText) findViewById(R.id.descriptionText);
        startDateTextObject = (EditText) findViewById(R.id.startDate);
        endDateTextObject = (EditText) findViewById(R.id.endDateText);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_job_creation, menu);
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

    //go to the sign up screen
    public void submitJob(View view) {
        String jobName = jobNameTextObject.getText().toString().trim();
        String jobDescription = jobDescriptionTextObject.getText().toString().trim();
        String startDate = startDateTextObject.getText().toString().trim();
        String endDate = endDateTextObject.getText().toString().trim();

        //all fields must be filled in for the sign up to work
        StringBuilder signupErrors = new StringBuilder("");
        boolean fieldError = false;
        if (jobName.length() == 0 ) {
            signupErrors.append("Username must be 4 characters. ");
            fieldError = true;
        }
        if (jobDescription.length() == 0 ) {
            signupErrors.append("Password must be 4 characters. ");
            fieldError = true;
        }
        if (startDate.length() == 0 ) {
            signupErrors.append("You must enter an email address. ");
            fieldError = true;
        }
        if (endDate.length() == 0 ) {
            signupErrors.append("You must enter a phone number. ");
            fieldError = true;
        }

        //displays the fieldErrors using Toast (taught in HW2)
        if (fieldError) {
            Toast.makeText(this, signupErrors.toString(),
                    Toast.LENGTH_SHORT).show();
            //We must breakout of the signUpUser() method if errors exist
            return;
        }

        Job newJob = new Job(jobName, jobDescription, startDate, endDate);
        newJob.saveInBackground();

        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);
    }
}
