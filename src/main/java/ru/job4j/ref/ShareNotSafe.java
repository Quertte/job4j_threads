package ru.job4j.ref;

public class ShareNotSafe {
    public static void main(String[] args) throws InterruptedException {
        UserCache cache = new UserCache();
        User user = User.of("name");
        cache.add(user);
        Thread first = new Thread(
                () -> {
                    for (User user1 : cache.findAll()) {
                        user1.setName("rename");
                    }
                }
        );
        first.start();
        first.join();
        System.out.println(cache.findById(1).getName());
    }
}
