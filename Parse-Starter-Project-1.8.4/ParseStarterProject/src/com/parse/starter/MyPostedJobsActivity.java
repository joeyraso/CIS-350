package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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


public class MyPostedJobsActivity extends Activity {
    List<String> myPostedJobs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        final ListView listview = (ListView) findViewById(R.id.list);
        final ArrayList<String> jobNames = new ArrayList<String>();
        final ArrayList<String> jobDescriptions = new ArrayList<>();

        //List of IDS for all the jobs
        List<String> myPostedJobs = ParseUser.getCurrentUser().getList("myPostedJobs");
        for (String jobId : myPostedJobs) {
            //Query Parse
            ParseQuery<Job> query = new ParseQuery("Job");
            query.getInBackground(jobId, new GetCallback<Job>() {
                @Override
                public void done(Job o, ParseException e) {
                    String name = o.getJobName();
                    jobNames.add(name);
                    Toast.makeText(MyPostedJobsActivity.this, "THERE IS A JOB:." + name,
                            Toast.LENGTH_SHORT).show();
                    jobDescriptions.add(o.getString("jobDescription"));
                }
            });
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

        listview.setAdapter(listAdapter);

    }


//RUDIN COMMENT OUT - 8:22 AM
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_cart);
//
//        final ListView listview = (ListView) findViewById(R.id.postedJobsList);
//        final ArrayList<String> jobNames = new ArrayList<String>();
//        final ArrayList<String> jobDescriptions = new ArrayList<String>();
//
//
//        myPostedJobs = ParseUser.getCurrentUser().getList("myPostedJobs");
//
//        if (myPostedJobs != null) {
//
//            for (Object o : myPostedJobs) {
//                Toast.makeText(MyPostedJobsActivity.this, "IN LIST: " + o.toString(), Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        //List of IDS for all the jobs
//       /* List <String> myPostedJobs = ParseUser.getCurrentUser().getList("myPostedJobs");*/
//
//        if (myPostedJobs != null) {
//
//            /*for (String jobId : myPostedJobs) {
//                //Query Parse
//                ParseQuery<Job> query = new ParseQuery("Job");
//                query.getInBackground(jobId, new GetCallback<Job>() {
//                    @Override
//                    public void done(Job o, ParseException e) {
//                        String name = o.getJobName();
//                        System.out.println("Name: " + name);
//                        jobNames.add(name);
//                        Toast.makeText(MyPostedJobsActivity.this, "THERE IS A JOB:." + name,
//                                Toast.LENGTH_SHORT).show();
//                        jobDescriptions.add(o.getString("jobDescription"));
//                    }
//                });
//            }*/
//        }
//
//        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(
//                this,
//                android.R.layout.simple_list_item_2,
//                android.R.id.text1,
//                jobNames) {
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//
//                // Must always return just a View.
//                View view = super.getView(position, convertView, parent);
//
//                // If you look at the android.R.layout.simple_list_item_2 source, you'll see
//                // it's a TwoLineListItem with 2 TextViews - text1 and text2.
//                //TwoLineListItem listItem = (TwoLineListItem) view;
//                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
//                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
//                text1.setText(jobNames.get(position));
//                text1.setTextSize(25);
//                text2.setText(jobDescriptions.get(position));
//                text2.setPadding(50,0,0,0);
//                return view;
//            }
//        };
//
////        listview.setAdapter(listAdapter);
//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//                //openJob(position);
//            }
//        });
//
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //go to the JobDetailsActivity
    public void openJob(int position) {
        String id = myPostedJobs.get(position);
        Intent intent = new Intent(this, JobRequestorsActivity.class);
        intent.putExtra("jobID", id);
        startActivity(intent);
    }

    //button logic to go to the homepage screen
    public void displayHomepage(View view) {
        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);
    }
}

