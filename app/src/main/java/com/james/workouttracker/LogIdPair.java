package com.james.workouttracker;

import java.util.ArrayList;

public class LogIdPair {
    public ArrayList<Workout> logHistoryList;
    public ArrayList<Integer> logHistoryIds;

    public LogIdPair(ArrayList<Workout> logHistoryList, ArrayList<Integer> logHistoryIds) {
        this.logHistoryList = new ArrayList<Workout>();
        for (int i = 0; i < logHistoryList.size(); i++) {
            this.logHistoryList.add(logHistoryList.get(i));
        }

        this.logHistoryIds = new ArrayList<Integer>();
        for (int i = 0; i < logHistoryIds.size(); i++) {
            this.logHistoryIds.add(logHistoryIds.get(i));
        }
    }
}
