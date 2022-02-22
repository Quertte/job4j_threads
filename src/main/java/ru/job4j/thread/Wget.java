package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String dest;

    public Wget(String url, int speed, String dest) {
        this.url = url;
        this.speed = speed;
        this.dest = dest;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fos = new FileOutputStream(dest)) {
            byte[] buffer = new byte[speed];
            int bytesRead;
            long startTime = System.currentTimeMillis();
            int downloadData = 0;
            while ((bytesRead = in.read(buffer, 0, speed)) != -1) {
                downloadData += bytesRead;
                if (downloadData >= speed) {
                    long finishTime = System.currentTimeMillis() - startTime;
                    System.out.println(finishTime);
                    if (finishTime < 1000) {
                        Thread.sleep(1000 - finishTime);
                    }
                }
                downloadData = 0;
                startTime = System.currentTimeMillis();
                fos.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 3) {
            throw new IllegalArgumentException("Error");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String dest = args[2];
        Thread wget = new Thread(new Wget(url, speed, dest));
        wget.start();
        wget.join();
    }
}
