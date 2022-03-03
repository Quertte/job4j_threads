package ru.job4j.concurrent;

public class ThreadPoolMain {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main starts");
        ThreadPool pool = new ThreadPool();
        for (int i = 0; i < 10; i++) {
            pool.work(new RunnableImpl());
        }
        pool.shutdown();
        Thread.sleep(2000);
        System.out.println("Main ends");
    }
}

class RunnableImpl implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " begins work");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(Thread.currentThread().getName() + " ends work");
    }
}