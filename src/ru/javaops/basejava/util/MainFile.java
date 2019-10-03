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
