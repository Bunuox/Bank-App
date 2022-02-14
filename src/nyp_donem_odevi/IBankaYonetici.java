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
public interface IBankaYonetici {

    void personelEkle(String job_title, String name, String surname, String password, String telNumber, String branch, int maas, String tc, String cinsiyet);

    void personelBilgiGuncelle(String personel_id, String job_title, String name, String surname, String password, String telNumber, String branch, int maas);

    void personelSil(String personel_id);
    
    ArrayList<Object[]> personelGetir();
    
    ArrayList<Object[]> personelIslemGetir();
    
    ArrayList<Object[]> limitIslemGetir();
}