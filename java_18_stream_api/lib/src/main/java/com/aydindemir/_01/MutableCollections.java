package com.aydindemir._01;

public class MutableCollections {
//
//	Java’daki Mutable Koleksiyonlar
//	1. List Implementasyonları
//
//	Eleman sıralı tutulur, index vardır.
//
//	ArrayList → En çok kullanılan, dinamik dizi tabanlı.
//
//	LinkedList → Bağlı liste tabanlı (ekleme/çıkarma hızlı).
//
//	Vector → Eski (synchronized, yani thread-safe ama performansı düşük).
//
//	Stack → Vector’dan türemiş, LIFO mantığında.
//
//	Örnek:
//
//	List<String> list = new ArrayList<>();
//	list.add("Ali");
//	list.add("Veli");
//	list.set(1, "Ayşe"); // güncelleme
//	System.out.println(list); // [Ali, Ayşe]
//
//	2. Set Implementasyonları
//
//	Elemanlar benzersizdir (duplicate yok).
//
//	HashSet → En hızlı, sırasız tutar.
//
//	LinkedHashSet → Eklenme sırasını korur.
//
//	TreeSet → Sıralı (doğal sıralama veya comparator ile).
//
//	Örnek:
//
//	Set<String> set = new HashSet<>();
//	set.add("Ali");
//	set.add("Ali"); // duplicate yok, eklenmez
//	System.out.println(set); // [Ali]
//
//	3. Map Implementasyonları
//
//	Key–Value yapısı (anahtar → değer).
//
//	HashMap → En çok kullanılan, sırasız.
//
//	LinkedHashMap → Eklenme sırasını korur.
//
//	TreeMap → Anahtara göre sıralı.
//
//	Hashtable → Eski, thread-safe (ama yavaş).
//
//	Örnek:
//
//	Map<Integer, String> map = new HashMap<>();
//	map.put(1, "Bir");
//	map.put(2, "İki");
//	map.put(1, "One"); // key aynı olunca değer güncellenir
//	System.out.println(map); // {1=One, 2=İki}
//
//	4. Queue / Deque Implementasyonları
//
//	FIFO (first-in-first-out) veya çift taraflı kuyruk.
//
//	PriorityQueue → Öncelikli kuyruk (sıralı).
//
//	ArrayDeque → Çift uçlu kuyruk.
//
//	LinkedList → Aynı zamanda Queue gibi de kullanılabilir.
//
//	Örnek:
//
//	Queue<String> queue = new LinkedList<>();
//	queue.add("Ali");
//	queue.add("Ayşe");
//	System.out.println(queue.poll()); // Ali (ilk giren ilk çıkar)
}
