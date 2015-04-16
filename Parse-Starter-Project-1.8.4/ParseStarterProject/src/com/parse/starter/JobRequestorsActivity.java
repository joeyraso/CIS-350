package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;


public class JobRequestorsActivity extends Activity {
    Job job;
    String jobId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_requestors_list);

        Intent intent = getIntent();
        jobId = intent.getStringExtra("jobID");

        //Query Parse
        ParseQuery<Job> query = new ParseQuery("Job");
        query.getInBackground(jobId, new GetCallback<Job>() {
            @Override
            public void done(Job o, ParseException e) {
                job = o;
            }
        });

        // get the list of requestors
        List<String> requestorIds = job.getList("jobRequestors");
        final ListView listview = (ListView) findViewById(R.id.requestorsList);

        if (requestorIds != null) {
            for (Object o : requestorIds) {
                Toast.makeText(JobRequestorsActivity.this, "IN LIST: " + o.toString(), Toast.LENGTH_SHORT).show();
            }
        }

        //List of IDS for all the jobs
       /* List <String> myPostedJobs = ParseUser.getCurrentUser().getList("myPostedJobs");

        if (myPostedJobs != null) {
            for (String jobId : myPostedJobs) {
                //Query Parse
                ParseQuery<Job> query = new ParseQuery("Job");
                query.getInBackground(jobId, new GetCallback<Job>() {
                    @Override
                    public void done(Job o, ParseException e) {
                        String name = o.getJobName();
                        System.out.println("Name: " + name);
                        jobNames.add(name);
                        Toast.makeText(MyPostedJobsActivity.this, "THERE IS A JOB:." + name,
                                Toast.LENGTH_SHORT).show();
                        jobDescriptions.add(o.getString("jobDescription"));
                    }
                });
            }
        }

        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_2,
                android.R.id.text1,
                jobNames) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                // Must always return just a View.
                View view = super.getView(position, convertView, parent);

                // If you look at the android.R.layout.simple_list_item_2 source, you'll see
                // it's a TwoLineListItem with 2 TextViews - text1 and text2.
                //TwoLineListItem listItem = (TwoLineListItem) view;
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text1.setText(jobNames.get(position));
                text1.setTextSize(25);
                text2.setText(jobDescriptions.get(position));
                text2.setPadding(50,0,0,0);
                return view;
            }
        };

//        listview.setAdapter(listAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                //openJob(position);
            }
        });*/

    }

    //go to the JobDetailsActivity
   /* public void openJob(int position) {
        String id = myPostedJobs.get(position);
        Intent intent = new Intent(this, JobDetailsActivity.class);
        intent.putExtra("jobID", id);
        startActivity(intent);
    }*/
}

