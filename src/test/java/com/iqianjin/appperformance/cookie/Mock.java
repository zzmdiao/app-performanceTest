package com.iqianjin.appperformance.cookie;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Mock {
    private static final String LOGIN_PLAIN_SEPARATOR = "sovjnTi";
    private static final String LOGIN_MD5_SEPARATOR = "#";
    private static final String LOGIN_SIGN = "";
    private static final long START_USERID = 1L;
    private static final long QUANTITY = 100010L;
    private static final String NICK_NAME = "testUserName";
    private static final String TIME = "19700101";
    private static final String OUT_FILE = "/Users/finup/live1.csv";

    public static void main(String[] args) {

        List<String> dataList = new ArrayList<String>();
        dataList.add("userId,lp,lm");

        for (long userId = START_USERID; userId < START_USERID + QUANTITY; userId++) {
            StringBuilder builderLP = new StringBuilder();
            builderLP.append(NICK_NAME);
            builderLP.append(LOGIN_PLAIN_SEPARATOR);
            builderLP.append(userId);
            builderLP.append(LOGIN_PLAIN_SEPARATOR);
            builderLP.append(TIME);
            builderLP.append(LOGIN_PLAIN_SEPARATOR);
            String loginPlain = builderLP.toString();

            StringBuilder builderLM = new StringBuilder();
            builderLM.append(NICK_NAME);
            builderLM.append(LOGIN_MD5_SEPARATOR);
            builderLM.append(userId);
            builderLM.append(LOGIN_MD5_SEPARATOR);
            builderLM.append(TIME);
            builderLM.append(LOGIN_MD5_SEPARATOR);
            builderLM.append(LOGIN_SIGN);
            String loginMd5 = MD5keyUtil.getMD5Str(builderLM.toString());

            StringBuilder builderData = new StringBuilder();
            builderData.append(userId);
            builderData.append(",");
            builderData.append(loginPlain);
            builderData.append(",");
            builderData.append(loginMd5);
            dataList.add(builderData.toString());
        }

        boolean isSuccess = Csv.exportCsv(new File(OUT_FILE), dataList);
        System.out.println(isSuccess);
    }

}
