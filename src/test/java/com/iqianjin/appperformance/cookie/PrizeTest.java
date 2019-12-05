package com.iqianjin.appperformance.cookie;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PrizeTest {

    private static final long START_ID = 1L;
    private static final long QUANTITY = 200000L;
    private static final String OUT_FILE = "/Users/finup/prize.csv";

    public static void main(String[] args) {

        List<String> dataList = new ArrayList<String>();
        dataList.add("活动id,奖品id,用户id,发放数量");
        int actId = 314;

        int num = 1;
        int max=540,min=532;

        for (long userId = 1; userId < START_ID + QUANTITY;userId++) {
            int prizeId = (int) (Math.random()*(max-min)+min);
            StringBuilder builderData = new StringBuilder();
            builderData.append(actId);
            builderData.append(",");
            builderData.append(prizeId);
            builderData.append(",");
            builderData.append(userId);
            builderData.append(",");
            builderData.append(num);
            dataList.add(builderData.toString());
        }

        boolean isSuccess = Csv.exportCsv(new File(OUT_FILE), dataList);
        System.out.println(isSuccess);
    }
}
