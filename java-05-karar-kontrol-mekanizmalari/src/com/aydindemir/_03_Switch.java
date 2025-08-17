package com.aydindemir;

public class _03_Switch {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	 char harf = 'B';
		
	 switch (harf) {
	 
	 case 'A': {
		
		System.out.println("Seçilen : " + harf);
		break;
		
	}
	 case 'B': {
			
			System.out.println("Seçilen : " + harf);
			break;
			
		}
	 case 'C': {
			
			System.out.println("Seçilen : " + harf);
			break;
			
		}
	 case 'D': {
			
			System.out.println("Seçilen : " + harf);
			break;
			
		}
	 default:
		 System.out.println("Seçilen : " + harf + " bulunamadı");
		 break;
	
	}
	 
	 
	 
	 System.out.println("----------------------------------------------------");
	 
	 
	 
	 
	int  haftaninGunleri = 2;
		
	 switch (haftaninGunleri) {
	 
	 case 1: {
		
		System.out.println("Pazartesi");
		break;
		
	}
	 case 2: {
			
			System.out.println("Salı");
			break;
			
		}
	 case 3: {
			
			System.out.println("Çarşamba");
			break;
			
		}
	 case 4: {
			
			System.out.println("Perşembe");
			break;
			
		}
	 case 5: {
			
			System.out.println("Cuma");
			break;
			
		}
	 case 6: {
			
			System.out.println("Cumartesi");
			break;
			
		}
	 case 7: {
			
			System.out.println("Pazar");
			break;
			
		}
	 default:
		 System.out.println("Lütfen 1 ile 7 arasında seçim yapınız");
		 break;
	
	}
	 
	 
	 System.out.println("----------------------------------------------------");
	 
	 
	 
	 
	     haftaninGunleri = 2;
			
		 switch (haftaninGunleri) {
		 
		 case 1: {
			
			System.out.println("Pazartesi");
			System.out.println("---  Hafta İçi ----");
			break;
			
		}
		 case 2: {
				
				System.out.println("Salı");
				System.out.println("---  Hafta İçi ----");
				break;
				
			}
		 case 3: {
				
				System.out.println("Çarşamba");
				System.out.println("---  Hafta İçi ----");
				break;
				
			}
		 case 4: {
				
				System.out.println("Perşembe");
				System.out.println("---  Hafta İçi ----");
				break;
				
			}
		 case 5: {
				
				System.out.println("Cuma");
				System.out.println("---  Hafta İçi ----");
				break;
				
			}
		 case 6: {
				
				System.out.println("Cumartesi");
				System.out.println("---  Hafta Sonu ----");
				break;
				
			}
		 case 7: {
				
				System.out.println("Pazar");
				System.out.println("---  Hafta Sonu ----");
				break;
				
			}
		 default:
			 System.out.println("Lütfen 1 ile 7 arasında seçim yapınız");
			 break;
		
		}
		 
		 
		  System.out.println("-----------------------------");


	       int  haftaninDurumu = 3;

	        switch (haftaninDurumu){

	            case 1:
	                System.out.println("== Haftaiçi ===");
	                System.out.println("Pazartesi");
	                System.out.println("Salı");
	                System.out.println("Ç");
	                System.out.println("Per");
	                System.out.println("Cuma");
	                break;

	            case 2:
	                System.out.println("== Hafta sonu ===");
	                System.out.println("Cmt");
	                System.out.println("Pazar");
	                break;

	            default:
	                System.out.println("Lütfen 1 ile 2 arasında bir seçim yapınız");
	                break;

	        }


	}

}
