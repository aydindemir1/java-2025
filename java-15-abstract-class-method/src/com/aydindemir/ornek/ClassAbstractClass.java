package com.aydindemir.ornek;

public class ClassAbstractClass {
//	Gerçek hayat örneği üzerinden açıklamak gerekirse, bir hayvanat bahçesi yönetim sistemi üzerinden class ve abstract class farklarını gösterebiliriz.
//
//	1. Normal class Örneği:
//
//	Bir Dog sınıfı, Animal sınıfından türetilen bir normal sınıf olabilir. Bu sınıf tamamen somut metodlar içerir ve doğrudan nesne oluşturulabilir.

// ornek package

//	Bu örnekte, Dog sınıfı Animal sınıfından türetilmiştir ve nesne oluşturulabilir. Dog sınıfı hem somut metodları (örneğin eat()) hem de özel metodları (örneğin bark()) içerir.
//
//	2. abstract class Örneği:
//
//	Şimdi, abstract class kullanarak aynı projeyi ele alalım. Örneğimizde, Animal sınıfı soyut bir sınıf olacak ve tüm hayvanlar için ortak özellikler ve metodlar tanımlanacak. Ancak her hayvan türüne özgü bazı metodlar soyut olarak bırakılacak ve alt sınıflar bu metodları implement etmek zorunda olacak.

	//ornek2 package

	

	
	
//	Farklar ve Kullanım Amacı:
//
//	Soyut (Abstract) Sınıf (Animal):
//
//	Genel özellikler ve davranışlar tanımlanmıştır (örneğin eat() metodu).
//
//	Soyut metod (sound()): Her hayvan türüne özgü ses çıkarma davranışını alt sınıfların implement etmesi gerekmektedir. Örneğin, Dog için bark(), Bird için chirp() metodu.
//
//	Nesne oluşturulamaz: Animal sınıfından doğrudan nesne oluşturulamaz. Ama alt sınıflar (Dog, Bird) nesne oluşturabilir.
//
//	Normal Sınıf (Dog):
//
//	Özelleştirilmiş metodlar içerir (örneğin wagTail()).
//
//	Nesne oluşturulabilir: Dog sınıfından doğrudan nesne oluşturulabilir.
//
//	Sonuç:
//
//	Abstract class: Sadece temel şablonları ve genel davranışları sağlar, alt sınıflar bu şablonları genişletir ve özelleştirir.
//
//	Normal class: Her şeyin somut olduğu ve doğrudan nesne oluşturulabilen bir sınıf türüdür.
//
//	Bu örnekte, hayvanların ortak davranışları (yemek yeme gibi) Animal soyut sınıfında tanımlanırken, her hayvan türüne özgü davranışlar (örneğin, bark() veya chirp()) alt sınıflarda tanımlanır.

}
