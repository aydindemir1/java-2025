package com.aydindemir.ornek2;



public class Bird extends Animal {
    // Constructor
    public Bird(String name) {
        super(name);
    }

    // Soyut metodu implement etme
    public void sound() {
        System.out.println(name + " chirps.");
    }

    // Bird'e özgü metod
    public void fly() {
        System.out.println(name + " is flying.");
    }
}
