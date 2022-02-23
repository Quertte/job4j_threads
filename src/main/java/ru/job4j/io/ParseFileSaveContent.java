package ru.job4j.io;

import java.io.*;

public final class ParseFileSaveContent implements SaveContent {
    private final File file;

    public ParseFileSaveContent(File file) {
        this.file = file;
    }

    public void saveContent(String content) {
        try (OutputStream o = new FileOutputStream(file);
             BufferedOutputStream out = new BufferedOutputStream(o)) {
            for (int i = 0; i < content.length(); i += 1) {
                out.write(content.charAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
