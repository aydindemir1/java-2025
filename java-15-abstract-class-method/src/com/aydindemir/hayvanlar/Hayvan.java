package com.aydindemir.hayvanlar;

import com.aydindemir.base.Canli;

public abstract class Hayvan extends Canli {
	
	// Bir sınıfın içerisinde abstract metod yazabilmek için sınıfın da abstract olması lazım.
	// Abstract metodların, sınıfı inherit eden bütün classlar tarafından gövdesi yazılmalıdır.


    public void  yemekYe (){
        System.out.println("Hayvan: yemekYe");
    }

    public void  suIc (){
        System.out.println("Hayvan: suIc");
    }

    public void  hareketTuru (){
        System.out.println("Hayvan: hareketTuru");
    }

   // Soyut metod (alt sınıflar implement etmek zorunda)
    public abstract void sesVer();

    // Soyut metod (alt sınıflar implement etmek zorunda)
    public abstract void bilgisiniGetir();


}
