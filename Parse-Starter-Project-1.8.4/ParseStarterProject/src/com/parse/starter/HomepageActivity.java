package com.parse.starter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.app.ListActivity;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import android.view.View;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TwoLineListItem;
import android.view.LayoutInflater;
import org.apache.http.message.BasicNameValuePair;
import android.view.Display;
import android.graphics.Point;
import android.widget.Button;
import android.widget.RadioGroup.LayoutParams;


public class HomepageActivity extends Activity {
    ArrayAdapter<String> listAdapter;
    ArrayList<Job> jobObjects = new ArrayList<>();
    ArrayList<Job> shownObjects = new ArrayList<>();


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_homepage_view);

        //Set up listview
        final ArrayList<String> jobNames = new ArrayList<String>();
        final ArrayList<String> jobDescriptions = new ArrayList<String>();
        final List<String> jobInfo = new LinkedList<String>();




        //Query Parse
        ParseQuery<Job> query = new ParseQuery("Job");
        query.findInBackground(new FindCallback<Job>() {
            @Override
            public void done(List objects, ParseException e) {
                for (int i = 0; i < objects.size(); i++) {
                    Job o = (Job)objects.get(i);
                    jobObjects.add(o);
                    shownObjects.add(o);
                    String name = o.getString("jobName");
                    String descr = o.getString("jobDescription");

                    String[] temp = new String[2];
                    temp[0] = name;
                    temp[1] = descr;
                    jobNames.add(name);
                    jobDescriptions.add(descr);
                }
                //listAdapter.notifyDataSetChanged();
            }
        });

        ListView jobsListView = (ListView) findViewById(R.id.list);
//        listAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, jobNames);
//        jobsListView.setAdapter(listAdapter);


    listAdapter = new ArrayAdapter<String>(
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

        jobsListView.setAdapter(listAdapter);
        jobsListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                openJob(position);
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_homepage, menu);
        return true;
    }

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
        String id = jobObjects.get(position).getObjectId();
        Intent intent = new Intent(this, JobDetailsActivity.class);
        intent.putExtra("jobID", id);
        startActivity(intent);
    }


    //go to the profile screen
    public void displayProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    //go to the cart screen
    public void displayCart(View view) {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    // go to the job creation screen
    public void addJob(View view) {
        Intent intent = new Intent(this, jobCreationActivity.class);
        startActivity(intent);
    }


}
