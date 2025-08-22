package com.aydindemir.ornek;



public class Zoo {
    public static void main(String[] args) {
        Dog dog = new Dog("Rex");
        dog.eat();  // Animal sınıfından miras alındı
        dog.bark(); // Dog sınıfına özgü
    }
}
