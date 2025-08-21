package com.aydindemir.veteriner;

import com.aydindemir.base.Hayvan;

public class Kedi extends Hayvan {

    public Kedi() {
        super();
        System.out.println("Kedi");
    }


    @Override
    public void sesVer(){
        System.out.println("Kedi : miyav miyav");
    }

    @Override
    public void hareketeGec(){
        System.out.println("Kedi : hareketeGec");
    }

}