package com.iqianjin.appperformance.cookie;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV操作(读取和写入)
 */
public class Csv {

    /**
     * 读取
     *
     * @param file     csv文件(路径+文件名)，csv文件不存在会自动创建
     * @param dataList 数据
     * @return
     */
    public static boolean exportCsv(File file, List<String> dataList) {
        boolean isSucess = false;

        FileOutputStream out = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            out = new FileOutputStream(file);
            osw = new OutputStreamWriter(out);
            bw = new BufferedWriter(osw);
            if (dataList != null && !dataList.isEmpty()) {
                for (String data : dataList) {
                    bw.append(data).append("\r");
                }
            }
            isSucess = true;
        } catch (Exception e) {
            isSucess = false;
        } finally {
            streamClose(bw);
            streamClose(osw);
            streamClose(out);
        }

        return isSucess;
    }

    /**
     * 写入
     *
     * @param file csv文件(路径+文件)
     * @return
     */
    public static List<String> importCsv(File file) {
        List<String> dataList = new ArrayList<String>();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                dataList.add(line);
            }
        } catch (Exception e) {
        } finally {
            streamClose(br);
        }

        return dataList;
    }

    private static void streamClose(Closeable out){
        if (out != null) {
            try {
                out.close();
                out = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        exportCsv();
        importCsv();
    }


    /**
     * CSV读取测试
     *
     * @throws Exception
     */
    public static void importCsv() {
        List<String> dataList = Csv.importCsv(new File("/Users/apple/iqj.csv"));
        if (dataList != null && !dataList.isEmpty()) {
            for (int i = 0; i < dataList.size(); i++) {
                if (i != 0) {//不读取第一行
                    String s = dataList.get(i);
                    System.out.println("s  " + s);
                    String[] as = s.split(",");
                    System.out.println(as[0]);
                    System.out.println(as[1]);
                    System.out.println(as[2]);
                }
            }
        }
    }

    /**
     * CSV写入测试
     *
     * @throws Exception
     */
    public static void exportCsv() {
        List<String> dataList = new ArrayList<String>();
        dataList.add("number,name,sex");
        dataList.add("1,张三,男");
        dataList.add("2,李四,男");
        dataList.add("3,小红,女");
        boolean isSuccess = Csv.exportCsv(new File("/Users/apple/iqj.csv"), dataList);
        System.out.println(isSuccess);
    }


}
