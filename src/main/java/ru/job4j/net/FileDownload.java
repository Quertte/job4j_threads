package ru.job4j.net;

import java.io.BufferedInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class FileDownload {
    public static void main(String[] args) throws Exception {
        String file = "https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml";
        try (BufferedInputStream in = new BufferedInputStream(new URL(file).openStream());
             FileOutputStream fos = new FileOutputStream("pom_tmp.xml")) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            long  startTime = System.currentTimeMillis();
            while ((bytesRead = in.read(buffer, 0, 1024)) != -1) {
                fos.write(buffer, 0, bytesRead);
                Thread.sleep(1000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
