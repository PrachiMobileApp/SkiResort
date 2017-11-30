package edu.neu.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 *
 * Simple class to wrap the data in a RFID lift pass reader record
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SkierData implements Serializable, Comparable<SkierData> {
    private int resortID;
    private int dayNum;
    private int skierID;
    private int liftID;
    private int time;

    public int compareTo(SkierData compareData) {
        int compareTime = ((SkierData) compareData).getTime();
        //ascending order
        return this.time - compareTime ;
    }

    @Override
    public String toString() {
        return "skierId=" + skierID + "dayNum=" + dayNum;
    }
}
