package ru.javaops.basejava.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
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

        File dir = new File("./src/ru/javaops/basejava/model");
        try {
            System.out.println(dir.getCanonicalPath() + " directory? " + dir.isDirectory());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println("read: " + fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //HW 8
        System.out.println();
        System.out.println("HW 8 - The contents of the './src' directory and its subdirectories");
        String projectRoot = "./src";
        listDir(projectRoot);

        //HW 9
        System.out.println();
        System.out.println("HW 9 - The contents of the './src' directory and its subdirectories");
        try {
            Files.walkFileTree(Paths.get(projectRoot), new MyFileVisitor());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //HW 9 - mentor
        System.out.println();
        System.out.println("HW 9 (mentor) - The contents of the './src' directory and its subdirectories");
        File dirProject = new File(projectRoot);
        printDirectoryDeeply(dirProject, "");

    }

//  Рекурсия каталогов, выводит полный путь к каталогам и файлам
//    private static void listDir(String filePath) {
//        Objects.requireNonNull(filePath, "Path must not be null");
//        File dir = new File(filePath);
//        try {
//            System.out.println(dir.getCanonicalPath());
//        } catch (IOException e) {
//            throw new RuntimeException("Error", e);
//        }
//        if (dir.isDirectory()) {
//            String[] list = dir.list();
//            for (String name : list) {
//                listDir(filePath + "\\" + name);
//            }
//        }
//    }

    private static void listDir(String filePath) {
        Objects.requireNonNull(filePath, "Path must not be null");
        File dir = new File(filePath);

        if (dir.isDirectory()) {
            String[] list = dir.list();
            for (String name : Objects.requireNonNull(list)) {
                listDir(filePath + "\\" + name);
            }
        } else {
            System.out.println(dir.getName());
        }
    }

    public static void printDirectoryDeeply(File dir, String offset) {
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println(offset + "F: " + file.getName());
                } else if (file.isDirectory()) {
                    System.out.println(offset + "D: " + file.getName());
                    printDirectoryDeeply(file, offset + "  ");
                }
            }
        }
    }

    public static class MyFileVisitor extends SimpleFileVisitor<Path> {
        @Override
        public FileVisitResult visitFile(Path path,
                                         BasicFileAttributes fileAttributes) {
            System.out.println("\t" + path.getFileName().toString());
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path path,
                                                 BasicFileAttributes fileAttributes) {
            System.out.println(path.toString());
            return FileVisitResult.CONTINUE;
        }
    }

}
