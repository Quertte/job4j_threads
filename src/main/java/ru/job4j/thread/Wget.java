package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fos = new FileOutputStream("pom_tmp.xml")) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            long startTime = System.currentTimeMillis();
            while ((bytesRead = in.read(buffer, 0, 1024)) != -1) {
                long actualTime = System.currentTimeMillis() - startTime;
                long expectedTime = bytesRead / speed;
                fos.write(buffer, 0, bytesRead);
                if (actualTime < expectedTime) {
                    Thread.sleep(expectedTime - actualTime);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
