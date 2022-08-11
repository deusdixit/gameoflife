package com.gameoflife.metric;

import java.util.LinkedList;

public class MetricSession {

    private final int LIMIT = 50;
    private LinkedList<Long> timings;
    private String tag;

    private long startTime;

    public MetricSession(String tag) {
        timings = new LinkedList<>();
        this.tag = tag;
    }

    public void start() {
        startTime = System.nanoTime();
    }

    public void stop(long t) {
        addTiming(t-startTime);
    }

    private void addTiming(long t) {
        timings.add(t);
        if ( timings.size() > LIMIT ) {
            timings.removeFirst();
        }
    }

    public double getMean() {
        if (timings.size() == 0) {
            return 0;
        }
        long acc = 0;
        for (Long l : timings) {
            acc += l;
        }
        return (double) acc / (double) timings.size() / 1000000.0;
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj instanceof MetricSession) {
            MetricSession ms = (MetricSession) obj;
            return ms.tag.equals(tag);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return tag.hashCode();
    }

    @Override
    public String toString() {
        return tag + " : " + String.format("%05f" ,getMean());
    }
}
