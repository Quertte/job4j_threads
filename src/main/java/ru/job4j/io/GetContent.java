package ru.job4j.io;

import java.util.function.Predicate;

public interface GetContent {

    String getContent(Predicate<Character> predicate);

}
