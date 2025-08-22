package com.aydindemir.ornek;

public class Animal {
    String name;

    // Constructor
    public Animal(String name) {
        this.name = name;
    }

    // Metod
    public void eat() {
        System.out.println(name + " is eating.");
    }
}
