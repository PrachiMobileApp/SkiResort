package edu.neu.server.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SkierMetrics {
    private long startTime;
    private long responseTime;

    @Override
    public String toString() {
        return startTime+":"+responseTime;
    }
}
