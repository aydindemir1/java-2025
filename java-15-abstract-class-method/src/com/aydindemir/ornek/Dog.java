package com.aydindemir.ornek;


public class Dog extends Animal {
    // Constructor
    public Dog(String name) {
        super(name);
    }

    // Dog'a özel metod
    public void bark() {
        System.out.println(name + " is barking.");
    }
}
