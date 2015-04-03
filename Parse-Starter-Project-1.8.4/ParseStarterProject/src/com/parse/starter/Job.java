package com.parse.starter;

/**
 * Created by joeyraso on 4/2/15.
 */

import com.parse.ParseObject;
import com.parse.ParseClassName;

@ParseClassName("Job")
public class Job extends ParseObject{

    public String getJobName() {
        return getString("jobName");
    }

    public void setJobName(String name) {
        put("jobName", name);
    }
}
