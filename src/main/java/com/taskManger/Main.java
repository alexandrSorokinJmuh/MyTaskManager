package com.taskManger;

import com.taskManger.entities.User;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        Predicate<String> p = (String s) -> { return s == null;};

        System.out.println(p.test("asd"));
    }
}
