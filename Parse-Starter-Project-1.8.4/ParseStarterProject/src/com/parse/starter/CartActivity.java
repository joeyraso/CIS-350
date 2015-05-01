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

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends Activity {
    //Used for clicking of jobs and displaying their info
    ArrayList<Job> jobObjects = new ArrayList<>();
    ArrayAdapter<String> cartListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        final ListView cartListview = (ListView) findViewById(R.id.list);
        final ArrayList<String> jobNames = new ArrayList<String>();
        final ArrayList<String> jobDescriptions = new ArrayList<>();

        //List of IDS for all the jobs
        List<String> myRequestedJobs = ParseUser.getCurrentUser().getList("myRequestedJobs");
        for (String jobId : myRequestedJobs) {
            //Query Parse
            ParseQuery<Job> query = new ParseQuery("Job");
            query.getInBackground(jobId, new GetCallback<Job>() {
                @Override
                public void done(final Job o, ParseException e) {
                    jobObjects.add(o);
                    final String name = o.getJobName();

                    runOnUiThread(new Runnable() {
                        public void run() {
                            jobNames.add(name);
                            jobDescriptions.add(o.getString("jobDescription"));
                            cartListAdapter.notifyDataSetChanged();
                        }
                    });
                }
            });

        }
        cartListAdapter = new ArrayAdapter<String>(
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
                text2.setPadding(50, 0, 0, 0);
                return view;
            }
        };

        cartListview.setAdapter(cartListAdapter);
        cartListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                openJob(position);
            }
        });

    }

    //button logic to go to the homepage screen
    public void displayHomepage(View view) {
        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);
    }

    //go to the JobDetailsActivity
    public void openJob(int position) {
        String id = jobObjects.get(position).getObjectId();
        Intent intent = new Intent(this, JobDetailsActivity.class);
        intent.putExtra("jobID", id);
        startActivity(intent);
    }

}
