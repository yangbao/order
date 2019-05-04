package com.immoc.server;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("1", "12", "133", "1444", "12", "177777");
        list.stream().map(String::length).collect(Collectors.toList()).forEach(System.out::println);
    }
}
