package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.app.ListActivity;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import android.view.View;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

public class HomepageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_homepage_view);

        //Set up listview
        final ArrayList<String> jobNames = new ArrayList<String>();
        final ArrayList<String> jobDescriptions = new ArrayList<String>();
        //populate
        ListView jobslv = (ListView) findViewById(android.R.id.list);
//        jobslv.setAdapter(listAdapter);

        //Querey Parse
        ParseQuery query = new ParseQuery("Job");
        query.findInBackground(new FindCallback() {
            @Override
            public void done(List objects, ParseException e) {
                for (int i = 0; i < objects.size(); i++) {
                    ParseObject o = (ParseObject)objects.get(i);
                    String name = o.getString("jobName");
                    String descr = o.getString("jobDescription");
                    jobNames.add(name);
                    jobDescriptions.add(descr);

                    Context context = getApplicationContext();
                    CharSequence text = "Hello toast!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
//                    listAdapter.add(name);
//                    listAdapter.add(descr);
                }
            }

            @Override
            public void done(Object o, Throwable throwable) {

            }
        });

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, jobNames);
//        jobslv.setAdapter(adapter);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
}
