package com.gameoflife;

import com.gameoflife.metric.Metric;

public interface Visual {

    public void setMetric(Metric m);
    public void output(Board b);
}
