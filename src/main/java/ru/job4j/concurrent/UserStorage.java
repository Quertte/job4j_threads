package ru.job4j.concurrent;


import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStorage implements Storage, Transfer {
    private final ConcurrentHashMap<Integer, User> store = new ConcurrentHashMap<>();

    public synchronized boolean add(User user) {
        return store.putIfAbsent(user.getId(), user) == null;
    }

    public synchronized boolean update(User user) {
        return store.replace(user.getId(), user) != null;
    }

    public synchronized boolean delete(User user) {
        return store.remove(user.getId(), user);
    }

    @Override
    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        User user = store.get(fromId);
        User user1 = store.get(toId);
        if (user != null && user1 != null && user.getAmount() >= amount) {
            user.setAmount(user.getAmount() - amount);
            user1.setAmount(user1.getAmount() + amount);
            rsl = true;
        }
        return rsl;
    }
}
