package com.parse.starter;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class SearchableActivity extends Activity {

    ArrayAdapter<String> listAdapter;
    ArrayList<Job> jobObjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        handleIntent(getIntent());


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_searchable, menu);
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

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String search = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow

            final ArrayList<String> jobNames = new ArrayList<String>();
            final ArrayList<String> jobDescriptions = new ArrayList<String>();

            Log.v("searchable", search);
            //Query Parse
            ParseQuery<Job> query = new ParseQuery("Job");
            query.whereContains("jobName", search);
            query.findInBackground(new FindCallback<Job>() {
                @Override
                public void done(List objects, ParseException e) {
                    for (int i = 0; i < objects.size(); i++) {
                        Job o = (Job) objects.get(i);
                        jobObjects.add(o);
                        final String name = o.getString("jobName");
                        final String descr = o.getString("jobDescription");

                        Log.v("searchable", name);

                        String[] temp = new String[2];
                        temp[0] = name;
                        temp[1] = descr;

                        //Thread used to ensure list appears properly each time it is loaded
                        //Also adds each item to list
                        runOnUiThread(new Runnable() {
                            public void run() {
                                jobNames.add(name);
                                jobDescriptions.add(descr);
                                listAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            });

            ListView jobsListView = (ListView) findViewById(R.id.list);

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

                    text2.setTextColor(Color.parseColor("#dc4e00"));
                    text1.setTextColor(Color.parseColor("#89cede"));

                    text1.setText(jobNames.get(position));
                    text1.setTextSize(25);
                    text2.setText(jobDescriptions.get(position));
                    text2.setPadding(50, 0, 0, 0);
                    return view;
                }
            };

            jobsListView.setAdapter(listAdapter);
        }
    }
}
