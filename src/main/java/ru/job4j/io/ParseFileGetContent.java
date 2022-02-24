package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFileGetContent implements GetContent {
    private final File file;

    public ParseFileGetContent(File file) {
        this.file = file;
    }

    public synchronized String getContent(Predicate<Character> predicate) {
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int data;
            while ((data = reader.read()) != -1) {
                if (predicate.test((char) data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public synchronized String getContent() {
        return getContent(a -> true);
    }

    public synchronized String getContentWithoutUnicode() {
        return getContent(a -> a < 0x80);
    }
}

