package nyp_donem_odevi;


import java.io.FileNotFoundException;
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
public interface IMusteri {

    void paraCek(String hesap_id,int cekilecek_miktar, String aciklama);

    void paraYatir(String hesap_id, int yatirilacak_miktar, String aciklama);
    
    void krediCek(String tur, int kredi_tutar, int kredi_vade);

    void paraTransfer(String hesap_id, String gonderilecek_hesap_iban, String aciklama, String gonderilecek_ad_soyad, int gonderilecek_miktar, String doviz_cinsi);

    void krediBorcOdeme(String hesap_id, String kredi_karti_id, int yatirilacak_miktar) throws FileNotFoundException;
    
    void krediKartiAl(long kredi_karti_no, int kredi_karti_cvv, int kredi_karti_limit, String sifre);

    int hesapAcma(String hesap_adi, String hesap_turu, String doviz_cinsi, String iban) throws FileNotFoundException;

    ArrayList<Object[]> hesaplariGoster();

    void faturaOdeme(String _hesap_id, String kurum, int cekilen_miktar, int bakiye, String odeme_yontemi);

    ArrayList<Object[]> islemGecmisiGoruntule(String hesap_id);
    
    ArrayList<Object[]> krediKartlariGoster();
    
    void krediKartiBorcOde(int kredi_karti_id,int hesap_id ,int odenecek_miktar);
    
    ArrayList<Object[]> krediKartiIslemGecmisiGoruntule(String kredi_karti_id);
    
    ArrayList<Object[]> krediGoster();
    
    ArrayList<Object[]> tlHesaplariGoster();
    
    void sifreDegistir(String yeni_sifre);
    
    void limitBasvur(String kredi_karti_id,int mevcut_limit,int yeni_limit);
    
}
