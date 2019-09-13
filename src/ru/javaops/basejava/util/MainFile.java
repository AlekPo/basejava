package ru.javaops.basejava.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/model");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //HW 8
        System.out.println("The contents of the './src' directory and its subdirectories");
        String projectRoot = "./src";
        listDir(projectRoot);
    }

    private static void listDir(String filePath) {
        Objects.requireNonNull(filePath, "Path must not be null");
        File dir = new File(filePath);
        try {
            System.out.println(dir.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }
        if (dir.isDirectory()) {
            String[] list = dir.list();
            for (String name : list) {
                listDir(filePath + "\\" + name);
            }
        }
    }
}
