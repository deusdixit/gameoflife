package com.gameoflife.metric;

import java.util.HashMap;
import java.util.stream.Collectors;

public class Metric {

    HashMap<String,MetricSession> sessions;

    public Metric() {
        sessions = new HashMap<>();
    }

    public void startCapture(String tag) {
        MetricSession ms;
        if ( sessions.containsKey(tag)) {
            ms = sessions.get(tag);
        } else {
            ms = new MetricSession(tag);
            sessions.put(tag,ms);
        }
        ms.start();
    }

    public void stopCapture(String tag) {
        long stopTime = System.nanoTime();
        MetricSession ms;
        if ( sessions.containsKey(tag)) {
            ms = sessions.get(tag);
        } else {
            ms = new MetricSession(tag);
            sessions.put(tag,ms);
        }
        ms.stop(stopTime);
    }

    @Override
    public String toString() {
        return sessions.values().stream().map(x -> x.toString()).collect(Collectors.joining(" | "));
    }


}
