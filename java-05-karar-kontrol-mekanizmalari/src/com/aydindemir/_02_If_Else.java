package com.aydindemir;

public class _02_If_Else {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
        int adayYasi = 17;
		
		if(adayYasi>=18) {
			System.out.println("Evet ehliyet sınavına girebilirsiniz");
		} else {
			System.out.println("Hayır sınava giremezsiniz çünkü yaşınız henüz " + adayYasi);
		}
		
		
		System.out.println("--------------  If Else iç içe   ---------------------------");
		
		
          adayYasi = 17;
		
		if(adayYasi>=18) {
			System.out.println("Evet ehliyet sınavına girebilirsiniz");
		} else if (adayYasi < 0){
			System.out.println("Hayır sınava giremezsiniz çünkü sen yoksun:  " + adayYasi);
		} else if (adayYasi == 17 ){
			System.out.println("Evet ehliyet sınavına girebilirsiniz ama sadece stajyer ehliyeti alabilirsiniz:  " + adayYasi);
		} else {
			System.out.println("Hayır sınava giremezsiniz çünkü yaşınız henüz :  " + adayYasi);
		}

		
		System.out.println("--------------  If Else iç içe   ---------------------------");
		
		boolean saglikRaporu = true;
        adayYasi = 25;
        
        if(saglikRaporu == true) {
        	
        	if(adayYasi>=18) {
    			System.out.println("Evet ehliyet sınavına girebilirsiniz");
    		} else if (adayYasi < 0){
    			System.out.println("Hayır sınava giremezsiniz çünkü sen yoksun:  " + adayYasi);
    		} else if (adayYasi == 17 ){
    			System.out.println("Evet ehliyet sınavına girebilirsiniz ama sadece stajyer ehliyeti alabilirsiniz:  " + adayYasi);
    		} else {
    			System.out.println("Hayır sınava giremezsiniz çünkü yaşınız henüz :  " + adayYasi);
    		}
        } else {
        	System.out.println("Sağlık raporunuz olumlu olmadan sınava giremezsiniz.");
        }
		
		



	}

}
