package nyp_donem_odevi;

import javax.swing.JOptionPane;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author bunuox
 */
public abstract class Kullanici {
    int id;
    String password;
    String name;
    String surname;
    String telNumber;
    String tc;
    String cinsiyet;
    int aktif;
    
    public void girisYapildi(){
        JOptionPane.showMessageDialog(null, this.name + " Hoşgeldiniz. Kullanıcı olarak giriş yapıldı.");
    }
}
