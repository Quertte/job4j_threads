package ru.job4j.concurrent;


import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStorage implements Storage, Transfer {
    private final ConcurrentHashMap<Integer, User> store = new ConcurrentHashMap<>();

    public synchronized boolean add(User user) {
        boolean rsl = false;
        if (!store.containsValue(user)) {
            store.put(user.getId(), user);
            rsl = true;
        }
        return rsl;
    }

    public synchronized boolean update(User user) {
        boolean rsl = false;
        if (store.containsValue(user)) {
            store.put(user.getId(), user);
            rsl = true;
        }
        return rsl;
    }

    public synchronized boolean delete(User user) {
        boolean rsl = false;
        if (store.containsValue(user)) {
            store.remove(user.getId());
            rsl = true;
        }
        return rsl;
    }

    @Override
    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        if (store.containsKey(fromId) && store.containsKey(toId)) {
            User user = store.get(fromId);
            User user1 = store.get(fromId);
            if (user.getAmount() >= amount) {
                user.setAmount(user.getAmount() - amount);
                user1.setAmount(user1.getAmount() + amount);
                rsl = true;
            }
        }
        return rsl;
    }
}
