package com.group21.thermostat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by AntonK on 28.05.2015.
 */
public class Schedule implements Serializable {
    ArrayList<TimeSection> listDaySections;

    public Schedule() {
        listDaySections = new ArrayList<TimeSection>();
    }

    public boolean shouldSwitchToDayTemperature(int h, int m) {
        int minutesOfDay = h * 60 + m;
        for (int i = 0; i < listDaySections.size(); i++) {
            if (listDaySections.get(i).intersects(minutesOfDay)) {
                return true;
            }
        }
        return false;
    }

    public void remove(int index) {
        if (index >= 0 && index < listDaySections.size()) {
            listDaySections.remove(index);
        }
    }

    public boolean add(int h1, int m1, int h2, int m2) {
        TimeSection t = new TimeSection(h1 * 60 + m1, h2 * 60 + m2);
        if (mayAdd(t)) {
            listDaySections.add(t);
            Collections.sort(listDaySections, new Comparator<TimeSection>() {
                @Override
                public int compare(TimeSection o1, TimeSection o2) {
                    if (o1.left > o2.left) {
                        return 1;
                    } else if (o1.left < o2.left) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
            return true;
        }
        return false;
    }

    private boolean mayAdd(TimeSection t) {
        for (int i = 0; i < listDaySections.size(); i++) {
            if (t.intersects(listDaySections.get(i))) {
                return false;
            }
        }
        return true;
    }
}
