package ru.job4j.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class RunAsyncEx {
    private static void iWork() throws InterruptedException {
        int count = 0;
        while (count < 10) {
            System.out.println("Вы: Я работаю");
            TimeUnit.SECONDS.sleep(1);
            count++;
        }
    }

    public static CompletableFuture<Void> goToTrash() {
        return CompletableFuture.runAsync(
                () -> {
                    System.out.println("Сын: Мам/Пап, я пошел выносить мусор");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Сын: Мам/Пап, я вернулся");
                }
        );
    }

    public static void runAsyncExample() throws Exception {
        CompletableFuture<Void> gtt = goToTrash();
        iWork();
    }

    public static void thenRunExample() throws Exception {
        CompletableFuture<Void> gtt = goToTrash();
        gtt.thenRun(
                () -> {
                    int count = 0;
                    while (count < 3) {
                        System.out.println("Сын: я мою руки");
                        try {
                            TimeUnit.SECONDS.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        count++;
                    }
                    System.out.println("Сын: я помыл руки");
                }
        );
        iWork();
    }
}
