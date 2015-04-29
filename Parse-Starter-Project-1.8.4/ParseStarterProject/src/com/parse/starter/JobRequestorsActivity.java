package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class JobRequestorsActivity extends Activity {
    Job job;
    String jobId;
    String jobName = "before";
    ArrayAdapter<String> requestorlistAdapter;
    List<String> requestorIds = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_requestors_list);

        Intent intent = getIntent();
        jobId = intent.getStringExtra("jobID");
        Log.v("DEBUG", "JOBID: " + jobId);

        //Query Parse
//        ParseQuery<Job> query = new ParseQuery("Job");
//        query.getInBackground(jobId, new GetCallback<Job>() {
//            @Override
//            public void done(Job o, ParseException e) {
//                if (o == null) {
//                    Log.v("DEBUG", "NULL");
//                } else {
//                    Log.v("DEBUG", "Job is NOT null");
//                    Log.v("DEBUG: job name", o.getString("jobName"));
//                    job = o;
//                    jobName = job.getString("jobName");
//                    requestorIds = job.getList("jobRequestors");
//                }
//                Log.v("DEBUG:", "if-else complete.");
//            }
//        });

        loadJobRequestors();
    }

    public void loadJobRequestors() {

       //SAMPLE DATA ADDED: REQUESTORS
       requestorIds.add("GvRY94rGfE"); //ankita
       requestorIds.add("c3Cc3547mD"); //joey

        // get the list of requestors
        final ArrayList<String> userNames = new ArrayList<String>();

        if (requestorIds != null) {
            Log.v("DEBUG:", "our list is non null.");
            for (String requestor : requestorIds) {
                //Query Parse for the user that requested the job, so we can display their name
                ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
                userQuery.getInBackground(requestor, new GetCallback<ParseUser>() {
                    @Override
                    public void done(ParseUser o, ParseException e) {
                        final String username = o.getUsername();

                        //Thread used to ensure list appears properly each time it is loaded
                        //Also adds each item to list
                        runOnUiThread(new Runnable() {
                            public void run() {
                                userNames.add(username);
                                requestorlistAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
            }

            final ListView requestorListview = (ListView) findViewById(R.id.requestorsList);

            requestorlistAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_2,
                    android.R.id.text1,
                    userNames) {

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {

                    // Must always return just a View.
                    View view = super.getView(position, convertView, parent);

                    // If you look at the android.R.layout.simple_list_item_2 source, you'll see
                    // it's a TwoLineListItem with 2 TextViews - text1 and text2.
                    //TwoLineListItem listItem = (TwoLineListItem) view;
                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                    TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                    text1.setText(userNames.get(position));
                    text1.setTextSize(25);
                    text2.setText("contact");
                    text2.setPadding(50, 0, 0, 0);
                    return view;
                }
            };

            requestorListview.setAdapter(requestorlistAdapter);
            requestorListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    //Unimplemented: Upon click, approve user
                    goToRequestorsProfile(position);
                }
            });

        }
    }

    private void goToRequestorsProfile(int position) {
        Intent intent = new Intent(this, ViewRequestorActivity.class);
        intent.putExtra("userID", requestorIds.get(position));
        intent.putExtra("jobID", jobId);
        startActivity(intent);
    }

    //button logic to go to the homepage screen
    public void displayHomepage(View view) {
        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);
    }
}
