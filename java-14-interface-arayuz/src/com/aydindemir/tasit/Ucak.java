package com.aydindemir.tasit;

import com.aydindemir.base.Arac;
import com.aydindemir.kural.IInternationalAirTransportAssociation;
import com.aydindemir.kural.IKanun;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString (callSuper=true) // Base Class taki field ların değerini yazmak
public class Ucak extends Arac implements IKanun, IInternationalAirTransportAssociation {

    @Override
    public int hizSiniri(int hizLimiti) {
        return hizLimiti;
    }

    @Override
    public String bakimDurumu(String durumBilgisi) {
        return durumBilgisi;
    }

    @Override
    public float tasitVergisi(float vergiMiktari) {
        return vergiMiktari;
    }

    @Override
    public byte emisyonDegeri(byte olcumOrani) {
        return olcumOrani;
    }


    @Override
    public Boolean kurumUyeDurumu(boolean durumu) {
        return durumu;
    }

    @Override
    public float aidat(float aidatOdemesi) {
        return aidatOdemesi;
    }

    @Override
    public boolean vizeIzinDurumu(boolean durum) {
        return durum;
    }

    @Override
    public short yolcuKapasitesi(short koltukSayisi) {
        return koltukSayisi;
    }
}
