package nyp_donem_odevi;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bunuox
 */
public class Hesap {

    private int hesap_id;
    private String hesap_isim;
    private int musteri_id;
    private String hesap_turu;
    private String doviz_turu;
    private String iban_no;
    private int bakiye;
    public int aktif;
    public int getBakiye() {
        return bakiye;
    }

    public void setBakiye(int bakiye) {
        this.bakiye = bakiye;
    }

    public int getHesap_id() {
        return hesap_id;
    }

    public void setHesap_id(int hesap_id) {
        this.hesap_id = hesap_id;
    }

    public int getMusteri_id() {
        return musteri_id;
    }

    public void setMusteri_id(int musteri_id) {
        this.musteri_id = musteri_id;
    }

    public String getHesap_turu() {
        return hesap_turu;
    }

    public void setHesap_turu(String hesap_turu) {
        this.hesap_turu = hesap_turu;
    }

    public String getIban_no() {
        return iban_no;
    }

    public void setIban_no(String iban_no) {
        this.iban_no = iban_no;
    }
    
    public Hesap(int hesap_id, int musteri_id, String hesap_turu, String iban_no, String hesap_isim, String doviz_turu){
        this.hesap_id = hesap_id;
        this.musteri_id = musteri_id;
        this.hesap_turu = hesap_turu;
        this.iban_no = iban_no;
        this.hesap_isim = hesap_isim;
        this.bakiye = 0;
        this.doviz_turu = doviz_turu;
 	this.aktif = 1;
    }
    
    public String string_format(){     
        return String.valueOf(this.hesap_id) + "," + String.valueOf(this.musteri_id)
                + "," + this.hesap_isim + "," + this.iban_no + "," + this.hesap_turu + ","
                + this.doviz_turu + "," + '0' + "," + String.valueOf(this.aktif);
   
    }
}
