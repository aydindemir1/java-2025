package com.aydindemir.ornek2;

public abstract class Animal {
    String name;

    // Constructor
    public Animal(String name) {
        this.name = name;
    }

    // Somut metod (alt sınıflar da kullanabilir)
    public void eat() {
        System.out.println(name + " is eating.");
    }

    // Soyut metod (alt sınıflar implement etmek zorunda)
    public abstract void sound();
}
