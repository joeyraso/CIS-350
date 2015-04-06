package com.parse.starter;

/**
 * Created by joeyraso on 4/2/15.
 */

import com.parse.ParseObject;
import com.parse.ParseClassName;

@ParseClassName("Job")
public class Job extends ParseObject{
    public Job(String name, String description, String start, String end) {
        setJobName(name);
        setJobDescription(description);
        setStartDate(start);
        setEndDate(end);
    }

    public Job() {

    }

    public void setJobName(String name) {
        put("jobName", name);
    }

    public void setJobDescription(String description) { put("jobDescription", description);}

    public void setStartDate(String start) {
        put("startDate", start);
    }

    public void setEndDate(String end) {
        put("endDate", end);
    }

    public String getJobName() {
        return getString("jobName");
    }

    public String getJobDescription() {
        return getString("jobDescription");
    }

    public String getStartDate() {
        return getString("startDate");
    }

    public String getEndDate() {
        return getString("endDate");
    }
}
