package nyp_donem_odevi;


import java.util.ArrayList;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author bunuox
 */
public interface IBankaCalisan {

    void musteriEkle(String name, String surname, String password, String telNumber, String tc, String cinsiyet);

    void musteriBilgiGuncelle(String id, String name, String surname, String password, String telNumber, String tc, String cinsiyet);

    void musteriSil(String musteri_id);

    void krediOnayla(String kredi_id);

    void krediRed(String kredi_id);

    ArrayList<Object[]> bekleyenKredileriGetir();

    ArrayList<Object[]> musteriGetir();

    void sifreDegistir(String yeni_sifre);

    ArrayList<Object[]> islemGecmisiGetir();
    
    ArrayList<Object[]> limitIslemGetir();
    
    void limitGuncelle(String kredi_karti_id, int yeni_limit);
    
    void limitGuncelleme(String kredi_karti_id, int yeni_limit);
    
    ArrayList<Object[]> krediKartiGetir();
    
    public void krediKartiOnayla(String kredi_karti_id);
    
    public void krediKartiReddet(String kredi_karti_id);
}
