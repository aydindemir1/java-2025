package com.aydindemir._02;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
// Kalıp - Model
public class Personel {

    private String  adi;
    private String  soyadi;
    private boolean medeniDurum;

    public void cizgiCek(){
        System.out.println("==================");
    }

    public void ekranaYaz(){
       System.out.println("EKRANA YAZ");
    }

    public void ekranaYaz(Integer sira, String adi){
        System.out.println("Sira: " + sira  + " --  " + sira.getClass());
        System.out.println("Adi: " + adi +  "  --  " + adi.getClass());
    }

    public void ekranaYaz(Double sira, String adi){
        System.out.println("Sira: " + sira  + " --  " + sira.getClass());
        System.out.println("Adi: " + adi +  "  --  " + adi.getClass());
    }

    public void ekranaYaz(Float sira, String adi){
        System.out.println("Sira: " + sira  + " --  " + sira.getClass());
        System.out.println("Adi: " + adi +  "  --  " + adi.getClass());
    }

    public void ekranaYaz(Long sira, String adi){
        System.out.println("Sira: " + sira  + " --  " + sira.getClass());
        System.out.println("Adi: " + adi +  "  --  " + adi.getClass());
    }




}
