package com.aydindemir;

public class Insan {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
//		String firstName = "Alp Arda";
//		String lastName = "Aras";
//		Integer age = 24;
//        Float height = 1.80f;
//		Double salary = 120_000.0;
//		Boolean maritalStatus  = true;
//		Character gender = 'M';
//		String eyesColor = "Blue";
//		printInfo( firstName, lastName, age, height, salary, maritalStatus, gender, eyesColor);
//		
		
		// 1.kişi
		printInfo("Alp Arda", "Aras", 24, 1.80f, 120_000.0, true, 'M', "Blue");
		
		
		
//		System.out.println("firstName : " + firstName);
//		System.out.println("lastName : " + lastName);
//		System.out.println("Age : " + age);
//		System.out.println("height : " + height);
//		System.out.println("salary : "+ salary);
//		System.out.println("maritalStatus : "+ maritalStatus);
//		System.out.println("gender : "+ gender);
//		System.out.println("eyesColor : "+ eyesColor);
		
	
		
		
//
//		 firstName = "Abdullah";
//		 lastName = "Gülbaz";
//		 age = 26;
//         height = 1.70f;
//		 salary = 110_000.0;
//		 maritalStatus = true;
//		 gender = 'M';
//		 eyesColor = "Black";
//		 
//		
//		 
//		 printInfo( firstName, lastName, age, height, salary, maritalStatus, gender, eyesColor);
//		
//		
//		 
//		
//
//		 firstName = "Celalettin";
//		 lastName = "Aksoy";
//		 age = 37;
//         height = 1.90f;
//		 salary = 130_000.0;
//		 maritalStatus = Boolean.FALSE;
//		 gender = 'M';
//		 eyesColor = "Green";
//		 
//		
//		 
//		 printInfo( firstName, lastName, age, height, salary, maritalStatus, gender, eyesColor);
//
//		
//		
//		
//
//		 firstName = "Betül";
//		 lastName = "Sarıkaya";
//		 age = 18;
//        height = 1.70f;
//		 salary = 135_000.0;
//		 maritalStatus = Boolean.FALSE;
//		 gender = 'F';
//		 eyesColor = "Green";
//		 
//		 printInfo( firstName, lastName, age, height, salary, maritalStatus, gender, eyesColor);
		
		  // 2. Kişi
        printInfo("Abdullah", "Gülbaz", 26, 1.70f, 110_000.0, true, 'M', "Black");

        // 3. Kişi
        printInfo("Celalettin", "Aksoy", 37, 1.90f, 130_000.0, Boolean.FALSE, 'M', "Green");

        // 4. Kişi
        printInfo("Betül", "Sarıkaya", 18, 1.70f, 135_000.0, Boolean.FALSE, 'F', "Green");
	
	}

	private static void printInfo(String firstName, 
			String lastName, 
			Integer age, 
			Float height,
			Double salary,
			Boolean maritalStatus, 
			Character gender, 
			String eyesColor) {
		// TODO Auto-generated method stub
		
		cizgiCek(); 
		
		System.out.println("firstName lastName: " + firstName + " " + lastName);	
		System.out.println("Age : " + age);
		System.out.println("height : " + height);
		 System.out.println("gender : "+ gender);
		 System.out.println("eyesColor : "+ eyesColor);
		System.out.println("salary : "+ salary);
        System.out.println("maritalStatus : "+ maritalStatus);
       
        
		
		
		
		
	}
	
	private static void cizgiCek() {
		// TODO Auto-generated method stub
		
		System.out.println("--=--=--=--=--------------");
		
	}

}
