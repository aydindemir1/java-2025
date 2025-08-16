package com.aydindemir;

import java.util.Iterator;

public class _01_For_Dongusu {  //Sınıfa Ait Scope

	public static void main(String[] args) {  // Main Metodu nun scope kapsamı
		// TODO Auto-generated method stub

		System.out.println("i++  -------------------------------");
		
		// {} =>> Scope kapsam
		
		//int i = 0;
		
		//Dongu   // Baslangic    // Şart      //Arttirim ve azaltim miktari
		for(    int i = 0;     i < 5 ;       i++  ) {  // dongu scope kapsam
			 
			System.out.println(i);
		}
		
		
		
		System.out.println("i=i+2 ---------------------------------------------------");
		
		//Dongu   // Baslangic    // Şart      //Arttirim ve azaltim miktari
		for (      int i = 3;     i <= 7;         i=i+2) {
			
			System.out.println(i);
			
		}		
		
		
		
        System.out.println("i+=2 ---------------------------------------------------");
		
		//Dongu   // Baslangic    // Şart      //Arttirim ve azaltim miktari
		for (      int i = 3;     i <= 7;         i+=2) {
			
			System.out.println(i);
			
		}	
		
		
		System.out.println("i--  -------------------------------");
		    //Dongu   // Baslangic    // Şart      //Arttirim ve azaltim miktari
			for(    int i = 10;     i >= 6 ;       i--  ) {  // dongu scope kapsam
					 
					System.out.println(i);
				}
		
			
			System.out.println("---------------------------------------------------");
			
			for (int i = 0; i < 5; i++) {
				
				System.out.println("---------");
				System.out.println("i : " + i);
				System.out.println("----");
				
				System.out.println("Fiyat : " + i);
				
				for (int j = 0; j < i; j++) {
					
					System.out.println("j : " + j);
					
				}
				
				
			}
			
			
			System.out.println("-----------------   NORMAL DONGU   ----------------------------------");
			
			//Normal
			for (int i = 0; i < 10; i++) {
				
				System.out.println(i);
			}
			
			System.out.println("-----------------   SONSUZ DONGU   ----------------------------------");
			
//			//Sonsuz döngü
//			for (    ;    ;    ) {
//				
//				
//			}
//			
			//Sonsuz döngü
//			for (int i = 0; ; i++) {
//				
//				// Şart karar kontrol mekanizmaları
//				System.out.println(i);
//			}
			
			
			// Bu koda asla gidilemez cunku ustte sonsuz dongu var.
			 //int a = 100;
	}

}
