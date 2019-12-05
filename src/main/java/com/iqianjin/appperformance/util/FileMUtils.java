package com.iqianjin.appperformance.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileMUtils {

    public static MultipartFile fileToMultipartFile(File file) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        String textFieldName = "textField";
        FileItem item = factory.createItem(textFieldName, "text/plain", true, file.getName());
        int bytesRead;
        byte[] buffer = new byte[8192];
        try {
            FileInputStream fis = new FileInputStream(file);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MultipartFile multipartFile = new CommonsMultipartFile(item);
        return multipartFile;
    }

    public static boolean isFileEnd(String path, String type) {
        File file = new File(path);
        File[] filelist = file.listFiles();
        for (File f : filelist) {
            if (f.getName().endsWith(type)) {
                return true;
            }
        }
        return false;
    }

}
