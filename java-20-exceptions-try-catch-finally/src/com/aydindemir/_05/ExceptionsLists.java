package com.aydindemir._05;

public class ExceptionsLists {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Tabii, verdiğiniz listeyi adım adım ve detaylı şekilde açıklayalım. Her bir exception türünü anlamak, hangi durumlarda meydana geldiklerini ve nasıl başa çıkılacaklarını öğrenmek yazılım geliştirmede büyük önem taşır. İşte her bir exception türüyle ilgili açıklamalar:
//
//			1. Throwable (Üst sınıf)
//
//			Throwable, Java'daki tüm error ve exception türlerinin üst sınıfıdır. Throwable'dan türeyen iki ana sınıf vardır:
//
//			Exception: Programda beklenen hatalar için kullanılır.
//
//			Error: Çoğu zaman dışsal nedenlerle oluşan, programın toparlanamayacağı hatalar için kullanılır.
//
//			Exception
//
//			Exception sınıfı, tüm checked ve unchecked exception'ların temel sınıfıdır.
//
//			IOException (Checked Exception)
//
//			Giriş/Çıkış işlemleri sırasında oluşan hatalardır. Örneğin, dosya okuma veya yazma işlemi sırasında bir hata oluşursa bu exception fırlatılır.
//
//			Örnek:
//
//			throw new IOException("File not found");
//
//			SQLException (Checked Exception)
//
//			Veritabanı işlemleri sırasında oluşan hatalar. SQL sorguları ile ilgili hatalar meydana geldiğinde bu exception fırlatılır.
//
//			Örnek:
//
//			throw new SQLException("Database error");
//
//			FileNotFoundException (Checked Exception)
//
//			Bir dosya açılmaya çalışıldığında, dosyanın belirtilen yerde bulunamaması durumunda fırlatılır.
//
//			Örnek:
//
//			throw new FileNotFoundException("File not found in the specified path");
//
//			EOFException (Checked Exception)
//
//			Dosyanın sonuna ulaşılmadan okuma işlemi yapılmaya çalışıldığında fırlatılır.
//
//			Örnek:
//
//			throw new EOFException("End of file reached unexpectedly");
//
//			ParseException (Checked Exception)
//
//			Veriyi beklenen biçime dönüştürmeye çalışırken oluşan hatalar. Örneğin, geçersiz bir tarih formatı verildiğinde bu exception fırlatılır.
//
//			Örnek:
//
//			throw new ParseException("Invalid date format", 0);
//
//			InterruptedIOException (Checked Exception)
//
//			Bir okuma/yazma işlemi, başka bir iş tarafından kesildiğinde fırlatılır.
//
//			RuntimeException (Unchecked Exception)
//
//			Unchecked Exceptions, derleyici tarafından kontrol edilmez, çalışma zamanında meydana gelirler ve genellikle programcı hatalarından kaynaklanır.
//
//			NullPointerException (Unchecked Exception)
//
//			null bir nesneye erişilmeye çalışıldığında fırlatılır. Bu genellikle bir nesne başlatılmadan (veya null olarak) metod veya alanlara erişmeye çalışıldığında oluşur.
//
//			Örnek:
//
//			String s = null;
//			s.length();  // NullPointerException fırlatılır
//
//			ArithmeticException (Unchecked Exception)
//
//			Aritmetik hatalar, örneğin sıfıra bölme işlemi yapılmaya çalışıldığında fırlatılır.
//
//			Örnek:
//
//			int result = 10 / 0;  // ArithmeticException fırlatılır
//
//			ArrayIndexOutOfBoundsException (Unchecked Exception)
//
//			Bir dizinin geçerli sınırları dışında bir index ile erişilmeye çalışıldığında fırlatılır.
//
//			Örnek:
//
//			int[] arr = {1, 2, 3};
//			int x = arr[5];  // ArrayIndexOutOfBoundsException fırlatılır
//
//			ClassCastException (Unchecked Exception)
//
//			Bir nesne, uygun olmayan bir türdeki nesneye dönüştürülmeye çalışıldığında fırlatılır.
//
//			Örnek:
//
//			Object obj = new Integer(10);
//			String str = (String) obj;  // ClassCastException fırlatılır
//
//			IllegalArgumentException (Unchecked Exception)
//
//			Yöntem çağrılırken geçersiz bir argüman sağlandığında fırlatılır.
//
//			Örnek:
//
//			public void setAge(int age) {
//			    if (age < 0) {
//			        throw new IllegalArgumentException("Age cannot be negative");
//			    }
//			}
//
//			IllegalStateException (Unchecked Exception)
//
//			Bir nesnenin geçersiz bir durumda olduğu zaman fırlatılır. Örneğin, bir nesne beklenmedik bir durumda kullanıldığında.
//
//			NumberFormatException (Unchecked Exception)
//
//			Bir sayıyı geçerli olmayan bir biçimde dönüştürmeye çalıştığınızda fırlatılır. Örneğin, bir string'i integer'a dönüştürmeye çalışırken yanlış format verilmesi.
//
//			Örnek:
//
//			int x = Integer.parseInt("abc");  // NumberFormatException fırlatılır
//
//			2. System-Level Exceptions
//			NullPointerException
//
//			Daha önce açıklandığı gibi, bir null referansına erişmeye çalıştığınızda oluşur.
//
//			ArithmeticException
//
//			Aritmetik hatalar, örneğin sıfıra bölme.
//
//			ArrayIndexOutOfBoundsException
//
//			Dizinin geçerli sınırlarının dışına çıkıldığında oluşur.
//
//			ClassCastException
//
//			Yanlış türde dönüştürme yapmaya çalıştığınızda fırlatılır.
//
//			IllegalArgumentException
//
//			Geçersiz bir argüman verildiğinde fırlatılır.
//
//			IllegalStateException
//
//			Bir nesnenin geçersiz durumda olduğu zaman fırlatılır.
//
//			NumberFormatException
//
//			Bir sayıyı yanlış formatta dönüştürmeye çalıştığınızda fırlatılır.
//
//			IndexOutOfBoundsException
//
//			Bir koleksiyona geçersiz bir indeks ile erişilmeye çalışıldığında fırlatılır.
//
//			UnsupportedOperationException
//
//			Desteklenmeyen bir işlem yapılmaya çalışıldığında bu exception fırlatılır.
//
//			NegativeArraySizeException
//
//			Bir dizi oluşturulmaya çalışıldığında negatif boyut verildiğinde fırlatılır.
//
//			3. IO Exceptions
//			IOException
//
//			Dosya okuma/yazma, ağ bağlantıları gibi giriş/çıkış işlemleri sırasında oluşan hatalardır.
//
//			FileNotFoundException
//
//			Dosyanın belirtilen yerde bulunamadığında fırlatılır.
//
//			EOFException
//
//			Dosyanın sonuna gelindiğinde veri okuma yapılmaya çalışıldığında fırlatılır.
//
//			ParseException
//
//			Veri dönüştürme sırasında oluşan hatalardır (örneğin, geçersiz tarih formatı).
//
//			MalformedURLException
//
//			Geçersiz bir URL verildiğinde oluşur.
//
//			IOException
//
//			Genel giriş/çıkış hatasıdır.
//
//			4. Database Exceptions
//			SQLException
//
//			Veritabanı işlemleri sırasında meydana gelen hataları belirtir. Örneğin, geçersiz SQL komutları.
//
//			SQLSyntaxErrorException
//
//			SQL komutunun yazım hatası nedeniyle oluşan hatadır.
//
//			SQLIntegrityConstraintViolationException
//
//			Veritabanında bir veri tutarsızlığı meydana geldiğinde (örneğin bir anahtar kısıtlaması ihlali).
//
//			SQLTimeoutException
//
//			Veritabanı işleminin zaman aşımına uğradığında fırlatılır.
//
//			5. Class Loading and Reflection Exceptions
//			ClassNotFoundException
//
//			Bir sınıfın yansıma (reflection) ile yüklenmeye çalışıldığında ve sınıf bulunamadığında fırlatılır.
//
//			NoSuchMethodException
//
//			Bir metodun yansıma ile bulunamadığında fırlatılır.
//
//			NoSuchFieldException
//
//			Bir alanın yansıma ile bulunamadığında fırlatılır.
//
//			6. Concurrency Exceptions
//			InterruptedException
//
//			Bir thread bekleme durumunda iken kesildiğinde fırlatılır.
//
//			ConcurrentModificationException
//
//			Bir koleksiyon üzerinde aynı anda birden fazla işleme yapılmaya çalışıldığında oluşur.
//
//			7. Networking Exceptions
//			MalformedURLException
//
//			Geçersiz bir URL yapısı olduğunda oluşur.
//
//			UnknownHostException
//
//			DNS üzerinden bir host adı çözülemeye çalışıldığında fırlatılır.
//
//			SocketTimeoutException
//
//			Socket bağlantısı zaman aşımına uğradığında fırlatılır.
//
//			ConnectException
//
//			Sunucuya bağlanırken bağlantı hatası meydana geldiğinde fırlatılır.
//
//			8. Reflection and Annotation Exceptions
//			InvocationTargetException
//
//			Yansıma (reflection) ile bir metod çağrıldığında ve metod içinde başka bir exception oluştuğunda bu exception fırlatılır.
//
//			NoSuchMethodException
//
//			Yansıma ile bir metod çağrıldığında, metod bulunamadığında fırlatılır.
//
//			NoSuchFieldException
//
//			Bir alan (field) yansıma ile erişilmeye çalışıldığında ve alan bulunamadığında fırlatılır.
//
//			9. Miscellaneous Exceptions
//			UnsupportedOperationException
//
//			Desteklenmeyen bir işlem yapılmaya çalışıldığında.
//
//			SecurityException
//
//			Bir işlem, güvenlik kısıtlamaları nedeniyle yapılamadığında oluşur.
//
//			IllegalStateException
//
//			Bir nesne geçersiz durumda olduğunda meydana gelir.
//
//			Sonuç
//
//			Bu listedeki exception türleri, Java'da sıkça karşılaşılan ve önemli olan hataları kapsar. Exception handling (istisna yönetimi), uygulamanın doğru ve güvenli çalışabilmesi için kritik bir rol oynar. Checked ve Unchecked exception'lar arasındaki farkları, her bir hatanın hangi durumlarda meydana geldiğini ve nasıl ele alınması gerektiğini bilmek, daha güvenilir ve sürdürülebilir yazılımlar geliştirmede önemli bir adımdır.

	}

}
