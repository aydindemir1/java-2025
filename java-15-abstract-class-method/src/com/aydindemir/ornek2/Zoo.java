package com.aydindemir.ornek2;


public class Zoo {
    public static void main(String[] args) {
        Dog dog = new Dog("Rex");
        dog.eat();  // Animal sınıfından miras alındı
        dog.sound(); // Dog sınıfı tarafından implement edildi
        dog.wagTail(); // Dog'a özel metod

        Bird bird = new Bird("Tweety");
        bird.eat();  // Animal sınıfından miras alındı
        bird.sound(); // Bird sınıfı tarafından implement edildi
        bird.fly();   // Bird'e özgü metod
    }
}

