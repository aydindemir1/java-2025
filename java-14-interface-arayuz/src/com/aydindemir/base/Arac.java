package com.aydindemir.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Arac {

 private short koltukSayi;
 private byte tekerSayisi;
 private String marka;
 private int motorHacmi;
 private String yakitTipi;

}