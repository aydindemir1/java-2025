package com.aydindemir;

public class Methodlar {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		📌 Java’da Metotlar Konu Anlatımı
//		1. Metot Nedir?
//
//		Metotlar, belirli bir görevi yerine getiren kod parçalarıdır.
//
//		Kod tekrarını önler, programı daha okunabilir ve bakımı kolay hale getirir.
//
//		2. Metot Tanımı
//
//		Genel söz dizimi:
//
//		erişimBelirteci geriDonusTipi metodAdi(parametreListesi) {
//		    // Metot gövdesi
//		    // Çalışacak kodlar
//		    return deger; // (void değilse)
//		}
//
//		Açıklama:
//
//		erişimBelirteci → public, private, protected, default
//
//		geriDonusTipi → int, double, String, void vb.
//
//		metodAdi → küçük harfle başlamalı (Java naming convention)
//
//		parametreListesi → int a, String b gibi
//
//		return → geri dönüş tipi void değilse kullanılmalı
//
//		3. Örnek: Parametresiz, Geri Dönüşsüz Metot
//		public class MetotOrnek {
//		    public static void selamVer() {
//		        System.out.println("Merhaba!");
//		    }
//
//		    public static void main(String[] args) {
//		        selamVer();  // çağırma
//		    }
//		}
//
//		4. Parametreli ve Geri Dönüşlü Metot
//		public class MetotOrnek {
//		    public static int topla(int a, int b) {
//		        return a + b;
//		    }
//
//		    public static void main(String[] args) {
//		        int sonuc = topla(5, 7);
//		        System.out.println("Toplam: " + sonuc);
//		    }
//		}
//
//		5. Void Metot (geri dönüşsüz)
//		public static void yazdir(String isim) {
//		    System.out.println("Merhaba, " + isim);
//		}
//
//		6. Overloading (Aşırı Yükleme)
//
//		Aynı isimli, farklı parametreli metotlar yazılabilir.
//
//		public static int carp(int a, int b) {
//		    return a * b;
//		}
//
//		public static double carp(double a, double b) {
//		    return a * b;
//		}
//
//
//		Çağrıldığında parametre tipine göre doğru metot seçilir.
//
//		7. Static ve Non-Static Metotlar
//
//		static metotlar → sınıf üzerinden çağrılır (örn. Math.sqrt(16)).
//
//		Non-static metotlar → nesne üzerinden çağrılır.
//
//		public class Hesap {
//		    public static int kare(int x) {   // static metot
//		        return x * x;
//		    }
//
//		    public int kup(int x) {           // non-static metot
//		        return x * x * x;
//		    }
//
//		    public static void main(String[] args) {
//		        System.out.println(Hesap.kare(5));  // sınıf üzerinden
//
//		        Hesap h = new Hesap();
//		        System.out.println(h.kup(5));       // nesne üzerinden
//		    }
//		}
//
//		8. Recursive (Özyinelemeli) Metot
//
//		Metot kendi kendini çağırabilir.
//		Örn: Faktöriyel hesabı
//
//		public static int faktoriyel(int n) {
//		    if (n == 0) return 1;
//		    return n * faktoriyel(n - 1);
//		}
//
//		9. Metotların Avantajları
//
//		✅ Kod tekrarını önler
//		✅ Programı modüler hale getirir
//		✅ Okunabilirliği artırır
//		✅ Bakım ve hata ayıklamayı kolaylaştırır
//
//		📌 Özet
//
//		Java’da metot türleri:
//
//		Parametresiz, geri dönüşsüz
//
//		Parametreli, geri dönüşsüz
//
//		Parametresiz, geri dönüşlü
//
//		Parametreli, geri dönüşlü
//
//		Overloaded (aşırı yüklenmiş)
//
//		Recursive (özyinelemeli)

	}

}
