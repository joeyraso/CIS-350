package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class JobRequestorsActivity extends Activity {
    Job job;
    String jobId;
    ArrayAdapter<String> requestorlistAdapter;


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
        final ListView requestorListview = (ListView) findViewById(R.id.requestorsList);
        final ArrayList<String> userNames = new ArrayList<String>();
        if (requestorIds != null) {
            for (String requestor : requestorIds) {
                //Query Parse for user
                ParseQuery<ParseUser> userQuery = new ParseQuery("User");
                query.getInBackground(requestor, new GetCallback<Job>() {
                    @Override
                    public void done(Job o, ParseException e) {
                        String name = o.getJobName();
                        userNames.add("username");
                    }
                });
            }

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
                    text2.setText("");
                    text2.setPadding(50,0,0,0);
                    return view;
                }
            };
            requestorlistAdapter.notifyDataSetChanged();

            requestorListview.setAdapter(requestorlistAdapter);
            requestorListview.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
                {
                    //Unimplemented: Upon click, approve user
                }
            });

        }

        }

    //button logic to go to the homepage screen
    public void displayHomepage(View view) {
        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);
    }
}
