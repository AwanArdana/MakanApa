package com.nefele.makanapa;

/**
 * Created by Awan on 19/09/2023.
 */
public class ItemCabang {
    String Cabang = "";
    String Nama = "";
    String Cover = "";
    public ItemCabang(String cabang, String nama, String cover){
        this.Cabang = cabang;
        this.Nama = nama;
        this.Cover = cover;
    }

    public String getCabang() {
        return Cabang;
    }

    public String getNama() {
        return Nama;
    }

    public String getCover() {
        return Cover;
    }
}
