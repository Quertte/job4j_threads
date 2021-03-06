package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(10000);
        progress.interrupt();
    }

    @Override
    public void run() {
        String[] process = {"\\", "|", "/"};
        int i = 0;
        while (!Thread.currentThread().isInterrupted()) {
            if (i == process.length) {
                i = 0;
            }
            try {
                Thread.sleep(500);
                System.out.print("\r load: " + process[i++]);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
