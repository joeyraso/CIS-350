package com.parse.starter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        final ListView listview = (ListView) findViewById(R.id.listview);
        String[] values = new String[] { "Walk dog", "Clean car", "Fix bike", "Get me Chipotle"};


        /*Job job = new Job();
        job.setJobName("Walk dog");

        Job job2 = new Job();
        job.setJobName("Clean car");

        Job job3 = new Job();
        job.setJobName("Fix bike");

        Job job4 = new Job();
        job.setJobName("Get me Chipotle");

        Job job5 = new Job();
        job.setJobName("Make website");

        Job job6 = new Job();
        job.setJobName("Help me assemble something");

        String[] values = new String[]{job.getJobName(), job2.getJobName(), job3.getJobName(),
                job4.getJobName(), job5.getJobName(), job6.getJobName()};*/


        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
            }

        });

        listview.setBackgroundColor(Color.rgb(30, 137, 255));
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

}
