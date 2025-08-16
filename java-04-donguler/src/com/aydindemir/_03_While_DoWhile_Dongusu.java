package com.aydindemir;

public class _03_While_DoWhile_Dongusu {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("------------- FOR DONGUSU --------------");
		
		//Dongu   // Baslangic    // Şart      //Arttirim ve azaltim miktari
		for(      int i = 0;       i < 5 ;      i++  ) {  
			 
			System.out.println(i);
		}
		
		
		System.out.println("------------- WHILE DONGUSU --------------");
		
		int i = 0; // Baslangic 
		
		while (i < 5) {  // Şart  
			
			System.out.println(i);
			
			 i++; //Arttirim ve azaltim miktari
			 
		}
		
		
       System.out.println("------------- DO WHILE DONGUSU --------------");
		
		int j = 1000; // Baslangic 
		
		do {   
			
			System.out.println(j);
			
			 j++; //Arttirim ve azaltim miktari
			 
		} while (j < 5);  // Şart 
		

	}

}
