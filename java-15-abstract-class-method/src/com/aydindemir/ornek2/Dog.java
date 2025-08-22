package com.aydindemir.ornek2;


public class Dog extends Animal {
    // Constructor
    public Dog(String name) {
        super(name);
    }

    // Soyut metodu implement etme
    public void sound() {
        System.out.println(name + " barks.");
    }

    // Dog'a özel metod
    public void wagTail() {
        System.out.println(name + " is wagging its tail.");
    }
}
