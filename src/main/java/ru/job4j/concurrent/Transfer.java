package ru.job4j.concurrent;

public interface Transfer {
    boolean transfer(int fromId, int toId, int amount);
}
