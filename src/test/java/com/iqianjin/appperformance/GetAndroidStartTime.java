package com.iqianjin.appperformance;

import com.iqianjin.appperformance.manager.GetStartTime;
import org.junit.Test;

public class GetAndroidStartTime {

    @Test
    void getStartTime() {
        GetStartTime.getAndroidStartTime(5);
    }

    @Test
    void getAndroidStartTime() {
        GetStartTime.getFFmpeg();
    }
}
