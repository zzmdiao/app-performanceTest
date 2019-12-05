package com.iqianjin.appperformance.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

@Slf4j
public class Adbutils {

    public static String exec(String adb) {
        Runtime runtime = Runtime.getRuntime();
        StringBuffer buffer = new StringBuffer();
        Process proc = null;
        BufferedReader reader = null;
        try {
            proc = runtime.exec(adb);
            reader = new BufferedReader(new InputStreamReader(proc.getInputStream(), "UTF-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                log.info(line);
                buffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return buffer.toString();
    }

    public static ArrayList<String> exec(String[] command) {
        ArrayList<String> result = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            Process process = null;
            process = Runtime.getRuntime().exec(command);
            reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            String line = null;
            log.info(reader.readLine());
            while ((line = reader.readLine()) != null) {
                log.info(line);
                result.add(line);
            }
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Deprecated
    public static void exec2(String command) {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(command);
            //写出脚本执行中的过程信息
            BufferedReader infoInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorInput = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line = "";
            while ((line = infoInput.readLine()) != null) {
                log.info(line);
            }
            while ((line = errorInput.readLine()) != null) {
                log.error(line);
            }
            infoInput.close();
            errorInput.close();

            //阻塞执行线程直至脚本执行完成后返回
            process.waitFor();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行adb命令并打印返回结果
     *
     * @param command
     */
    public static void execAdb(String command) {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(command);
            Scanner scanner = new Scanner(process.getInputStream());
            scanner.useDelimiter("\n");
            while (scanner.hasNext()) {
                log.info(scanner.next());
            }
            scanner.close();
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
