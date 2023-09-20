package com.nefele.makanapa;

/**
 * Created by Awan on 19/09/2023.
 */
public class ItemCabang {
    String Cabang = "";
    String Nama = "";
    String Cover = "";
    String Kategori = "";
    public ItemCabang(String cabang, String nama, String cover, String kategori){
        this.Cabang = cabang;
        this.Nama = nama;
        this.Cover = cover;
        this.Kategori = kategori;
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

    public String getKategori() {
        return Kategori;
    }
}
