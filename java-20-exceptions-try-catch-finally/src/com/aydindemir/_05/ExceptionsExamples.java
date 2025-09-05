package com.aydindemir._05;

//Java'da exception (istisna), bir programın çalışma zamanında karşılaştığı beklenmedik durumlar ve hatalar için kullanılan bir mekanizmadır. Java, hataları işlemek için exception handling (istisna işleme) yapısını sunar. Bu sayede program akışı, hatalı durumlarla başa çıkılabilecek şekilde kontrol edilebilir.
//
//Java'da iki ana türde exception bulunur: checked ve unchecked exceptions.
//
//1. Checked Exceptions:
//
//Bunlar, derleyici tarafından zorlama ile kontrol edilen exception'lardır.
//
//Eğer bir metod bir checked exception fırlatıyorsa, bu exception'ı ele almak zorundasınız. Aksi takdirde derleyici hata verir.
//
//Çoğunlukla, dış kaynaklarla (dosya, veritabanı bağlantıları vb.) çalışan durumlarda görülür.
//
//Örnek:
import java.io.*;

public class ExceptionsExamples {
    public void readFile() throws IOException {  // IOException checked exception
        FileReader fr = new FileReader("file.txt");
        BufferedReader br = new BufferedReader(fr);
        System.out.println(br.readLine());
    }

    public static void main(String[] args) {
        try {
            new ExceptionsExamples().readFile();
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}

//
//Bu örnekte, IOException checked exception'dır ve readFile() metodunun içinde bu hata fırlatıldığında, bu hatayı ya try-catch bloğuyla yakalamalı ya da metodun throws ifadesiyle başka bir yere atmalısınız.
//
//2. Unchecked Exceptions:
//
//Bunlar, runtime (çalışma zamanı) hatalarıdır ve derleyici tarafından kontrol edilmez.
//
//Bu tür exception'lar, çoğunlukla program hatalarından (örneğin mantık hataları) kaynaklanır ve genellikle programcı hatası olarak kabul edilir.
//
//RuntimeException sınıfından türetilen exception'lardır.
//
//Örnek:
 class Example {
    public void divide(int a, int b) {
        System.out.println(a / b);  // Divide by zero error (unchecked exception)
    }

    public static void main(String[] args) {
        new Example().divide(10, 0);  // Unchecked exception (ArithmeticException)
    }
}

//
//Bu örnekte, ArithmeticException bir unchecked exception'dır ve sıfıra bölme hatası meydana gelir. Ancak derleyici bu hatayı kontrol etmez; bu tür hatalar çalışma zamanında oluşur.
//
//3. Common Exception Türleri:
//
//Java'da bazı yaygın exception türleri şunlardır:
//
//Checked Exceptions:
//
//IOException: Giriş/çıkış işlemlerinde hata.
//
//SQLException: Veritabanı işlemleri sırasında hata.
//
//FileNotFoundException: Dosya bulunamadığında oluşur.
//
//ClassNotFoundException: Belirtilen sınıf bulunamadığında oluşur.
//
//Unchecked Exceptions:
//
//NullPointerException: Bir null nesneye erişmeye çalışıldığında oluşur.
//
//ArithmeticException: Aritmetik hata (örneğin sıfıra bölme).
//
//ArrayIndexOutOfBoundsException: Bir dizinin geçerli sınırları dışına çıkıldığında oluşur.
//
//ClassCastException: Yanlış türde bir nesneye dönüştürme yapılmaya çalışıldığında oluşur.
//
//IllegalArgumentException: Geçersiz argümanlar verildiğinde oluşur.
//
//Diğer İstisnalar:
//
//Exception: Tüm istisnaların genel sınıfıdır.
//
//RuntimeException: Unchecked istisnaların genel sınıfıdır.
//
//4. Exception Handling (İstisna İşleme):
//
//Java'da exception handling, hataları yönetmek için try-catch bloğu kullanır. Ayrıca finally bloğu ile kaynakların serbest bırakılması sağlanabilir.

//Yapı:
//try {
//    // Hata oluşturabilecek kod
//} catch (ExceptionType1 e1) {
//    // ExceptionType1 hatası yakalanır
//} catch (ExceptionType2 e2) {
//    // ExceptionType2 hatası yakalanır
//} finally {
//    // Her durumda çalışacak kod (isteğe bağlı)
//}

//Örnek:
 class Example1 {
    public static void main(String[] args) {
        try {
            int result = 10 / 0;  // ArithmeticException
        } catch (ArithmeticException e) {
            System.out.println("Error: Division by zero!");
        } finally {
            System.out.println("This block always executes.");
        }
    }
}


//Bu örnekte:
//
//try bloğu, hata oluşturabilecek kodu içerir.
//
//catch bloğu, hatayı yakalar ve uygun mesajı yazdırır.
//
//finally bloğu her durumda çalışır, hata olsa da olmasa da.
//
//5. Custom (Özel) Exceptions:
//
//Java'da kendi istisnalarınızı da oluşturabilirsiniz. Bunun için Exception sınıfından türetilmiş özel bir sınıf yaratılır.
//
//Örnek:
 class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}

 class Example2 {
    public void validateAge(int age) throws InvalidAgeException {
        if (age < 18) {
            throw new InvalidAgeException("Age must be 18 or older.");
        }
    }

    public static void main(String[] args) {
        try {
            new Example2().validateAge(15);
        } catch (InvalidAgeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
//
//
//Bu örnekte, InvalidAgeException adında özel bir exception sınıfı tanımlanmıştır ve bu istisna, yaşın 18'den küçük olup olmadığını kontrol etmek için kullanılmaktadır.
//
//Sonuç:
//
//Java'da exception'lar, hataları yönetmek ve programın çökmesini engellemek için kullanılır. Checked exceptions, genellikle dış kaynaklarla çalışırken (dosya okuma, veritabanı bağlantıları vb.) karşımıza çıkar, unchecked exceptions ise daha çok mantık hataları ve çalışma zamanı hatalarıdır. İstisnaları try-catch bloklarıyla yakalayarak, hataların programı kesmeden yönetilmesi sağlanabilir.
