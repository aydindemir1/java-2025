package com.aydindemir._01;

public class ImmutableCollections {

//	Java’da Immutable Koleksiyon Türleri
//	1. List.of(...)
//	List<String> names = List.of("Ali", "Veli", "Ayşe");
//	System.out.println(names); // [Ali, Veli, Ayşe]
//	names.add("Mehmet"); // ❌ UnsupportedOperationException
//
//	2. Set.of(...)
//	Set<String> cities = Set.of("İstanbul", "Ankara", "İzmir");
//	System.out.println(cities); // [İstanbul, Ankara, İzmir]
//	cities.remove("Ankara"); // ❌ UnsupportedOperationException
//
//
//	⚠️ Set.of tekrarlı eleman kabul etmez, duplicate varsa IllegalArgumentException atar.
//
//	3. Map.of(...)
//	Map<Integer, String> countryCodes = Map.of(
//	    90, "Türkiye",
//	    1, "ABD",
//	    44, "İngiltere"
//	);
//	System.out.println(countryCodes.get(90)); // Türkiye
//	countryCodes.put(49, "Almanya"); // ❌ UnsupportedOperationException
//
//	4. Collections.unmodifiableXXX(...) (Java 1.2’den beri var)
//	List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C"));
//	List<String> immutableList = Collections.unmodifiableList(list);
//
//	immutableList.add("D"); // ❌ UnsupportedOperationException
//
//
//	⚠️ Ama dikkat: unmodifiableList gerçekte shallow immutable → yani orijinal liste (list) değişirse, immutableList de değişir.
//
//	5. Immutable Collections (3rd Party)
//
//	Guava (Google) → ImmutableList, ImmutableSet, ImmutableMap
//
//	Daha zengin immutable collection desteği verir.
//
//	import com.google.common.collect.ImmutableList;
//
//	ImmutableList<String> guavaList = ImmutableList.of("X", "Y", "Z");
//	guavaList.add("A"); // ❌ Compile-time'da bile hata verir
}
