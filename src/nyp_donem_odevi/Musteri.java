package nyp_donem_odevi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Musteri extends Kullanici implements IMusteri {

    int aktif;
    int tc_kontrol = 1;

    Musteri(String name, String surname, String password, String telNumber, String tc, String cinsiyet) throws FileNotFoundException, IOException {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.telNumber = telNumber;
        this.aktif = 1;
        this.tc = tc;
        this.cinsiyet = cinsiyet;
        this.idEkle();
    }

    Musteri(int id, String name, String surname, String password, String telNumber, String tc, String cinsiyet) throws FileNotFoundException {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.telNumber = telNumber;
        this.aktif = 1;
        this.tc = tc;
        this.cinsiyet = cinsiyet;
    }

    Musteri(int id) throws FileNotFoundException {
        Scanner s = new Scanner(new BufferedReader(new FileReader("musteri.txt")));
        String[] sarray = null;
        while (s.hasNextLine()) {
            String ogb = s.nextLine();
            sarray = ogb.split(",");
            if (sarray[0].equals(String.valueOf(id))) {
                this.id = id;
                this.name = sarray[1];
                this.surname = sarray[2];
                this.password = sarray[3];
                this.telNumber = sarray[4];
                this.tc = sarray[5];
                this.cinsiyet = sarray[6];
                this.aktif = 1;
            }
        }
    }

    private void idEkle() throws FileNotFoundException, IOException {
        Scanner s = new Scanner(new BufferedReader(new FileReader("musteri.txt")));
        String[] sarray = null;
        while (s.hasNextLine()) {
            String ogb = s.nextLine();
            sarray = ogb.split(",");
        }
        if (sarray == null) {
            this.id = 0;
        } else {
            this.id = Integer.parseInt(sarray[0]) + 1;
        }

    }

    @Override
    public void paraCek(String hesap_id, int cekilecek_miktar, String aciklama) {
        // TODO Auto-generated method stub
        try {
            List<String> file_content = new ArrayList<>(Files.readAllLines(Paths.get("hesaplar.txt"), StandardCharsets.UTF_8));
            for (int i = 0; i < file_content.size(); i++) {
                String line = file_content.get(i);
                String[] line_split = line.split(",");
                int bakiye = Integer.parseInt(line_split[6]);
                if (line_split[0].equals(hesap_id)) {
                    line_split[6] = Integer.toString(bakiye - cekilecek_miktar);
                    String joined = String.join(",", line_split);
                    file_content.set(i, joined);
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter("hesap_islemleri.txt", true));
                        writer.write(line_split[0] + "," + "-"
                                + String.valueOf(cekilecek_miktar) + ","
                                + aciklama + ","
                                + String.valueOf(bakiye - cekilecek_miktar)
                                + "," + String.valueOf(LocalDate.now()) + "\n");

                        writer.close();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "İşlem Sırasında Hata Meydana Geldi :(");
                    }
                }
            }

            Files.write(Paths.get("hesaplar.txt"), file_content, StandardCharsets.UTF_8);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void paraYatir(String hesap_id, int yatirilacak_miktar, String aciklama) {
        // TODO Auto-generated method stub
        try {
            List<String> file_content = new ArrayList<>(Files.readAllLines(Paths.get("hesaplar.txt"), StandardCharsets.UTF_8));
            for (int i = 0; i < file_content.size(); i++) {
                String line = file_content.get(i);
                String[] line_split = line.split(",");
                int bakiye = Integer.parseInt(line_split[6]);
                if (line_split[0].equals(hesap_id)) {
                    line_split[6] = Integer.toString(bakiye + yatirilacak_miktar);
                    String joined = String.join(",", line_split);
                    file_content.set(i, joined);
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter("hesap_islemleri.txt", true));
                        writer.write(line_split[0] + "," + "+"
                                + String.valueOf(yatirilacak_miktar) + ","
                                + aciklama + ","
                                + String.valueOf(bakiye + yatirilacak_miktar)
                                + "," + String.valueOf(LocalDate.now()) + "\n");
                        writer.close();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "İşlem Sırasında Hata Meydana Geldi :(");
                    }
                }
            }

            Files.write(Paths.get("hesaplar.txt"), file_content, StandardCharsets.UTF_8);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void krediCek(String tur, int tutar, int vade) {
        // TODO Auto-generated method stub
        try {
            Kredi k = new Kredi(this.id, tur, vade, tutar);
            k.krediKaydet();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Kredi çekerken hata meydana geldi!");
        }
    }

    @Override
    public void paraTransfer(String hesap_id, String alici_iban, String aciklama, String alici_ad_soyad, int gonderilecek_miktar, String doviz_cinsi) {
        // TODO Auto-generated method stub
        try {
            List<String> file_content = new ArrayList<>(Files.readAllLines(Paths.get("hesaplar.txt"), StandardCharsets.UTF_8));
            for (int i = 0; i < file_content.size(); i++) {
                String line = file_content.get(i);
                String[] line_split = line.split(",");

                if (line_split[3].equals(alici_iban)) {
                    String alici_musteri_id = line_split[1];
                    List<String> musteri_file_content = new ArrayList<>(Files.readAllLines(Paths.get("musteri.txt"), StandardCharsets.UTF_8));
                    for (int j = 0; j < musteri_file_content.size(); j++) {

                        String line2 = musteri_file_content.get(j);
                        String[] line2_split = line2.split(",");

                        String ad = alici_ad_soyad.substring(0, alici_ad_soyad.indexOf(" "));
                        String soyad = alici_ad_soyad.substring(alici_ad_soyad.indexOf(" ") + 1, alici_ad_soyad.length());

                        if (line2_split[0].equals(alici_musteri_id) && line2_split[1].equalsIgnoreCase(ad) && line2_split[2].equalsIgnoreCase(soyad) && line_split[3].equalsIgnoreCase(alici_iban)) {

                            if (line_split[5].equals(doviz_cinsi)) {
                                String alici_hesap_id = line_split[0];
                                Musteri m = new Musteri(Integer.parseInt(alici_musteri_id), ad, soyad, line2_split[3], line2_split[4], line2_split[5], line2_split[6]);
                                m.paraYatir(alici_hesap_id, gonderilecek_miktar, String.valueOf(this.name) + " ADLI KULLANICIDAN HAVALE " + "AÇIKLAMA: " + aciklama);
                                this.paraCek(hesap_id, gonderilecek_miktar, ad + " ADLI KULLANICIYA HAVALE " + "AÇIKLAMA: " + aciklama);
                                JOptionPane.showMessageDialog(null, "İşlem Başarı ile Gerçekleşti!");
                                break;
                            } else {
                                JOptionPane.showMessageDialog(null, "Yalnızca DÖviz Cinsi Aynı Olan Hesaplara Para Transferi Yapabilirsiniz!");
                                break;
                            }

                        } else {
                            JOptionPane.showMessageDialog(null, "Alıcı Bulunamadı");
                        }
                    }
                }
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void krediBorcOdeme(String hesap_id, String kredi_id, int yatirilacak_miktar) throws FileNotFoundException {
        // TODO Auto-generated method stub
        Kredi k = new Kredi(Integer.parseInt(kredi_id));
        k.krediBorcOde(yatirilacak_miktar);
        this.paraCek(hesap_id, yatirilacak_miktar, "KREDİ BORÇ ÖDEME");
    }

    @Override
    public int hesapAcma(String hesap_adi, String hesap_turu, String doviz_cinsi, String iban) throws FileNotFoundException {
        // TODO Auto-generated method stub                    System.out.println("kredi bulundu");
        try {
            Scanner reader = new Scanner(new BufferedReader(new FileReader("hesaplar.txt")));
            int idx = 0, line=0;
            
            while (reader.hasNextLine()) {
                String s = reader.nextLine();
                String[] bilgiler = s.split(",");
                idx = Integer.parseInt(bilgiler[0]);
                line++;
            }
            if(line != 0) idx += 1;
            Hesap hesap = new Hesap(idx, this.id, hesap_turu, iban, hesap_adi, doviz_cinsi);
            reader.close();
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("hesaplar.txt", true));
                writer.write(hesap.string_format() + "\n");
                writer.close();
                return 1;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Musteri.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(Musteri.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public ArrayList<Object[]> hesaplariGoster() {
        // TODO Auto-generated method stub
        ArrayList<Object[]> o = new ArrayList<Object[]>();
        try {
            Scanner reader = new Scanner(new BufferedReader(new FileReader("hesaplar.txt")));
            while (reader.hasNextLine()) {
                String s = reader.nextLine();
                String[] bilgiler = s.split(",");
                if (bilgiler[1].equals(String.valueOf(this.id))) {
                    Object[] eklenecek = {bilgiler[0], bilgiler[2], bilgiler[4], bilgiler[5], bilgiler[6]};
                    o.add(eklenecek);
                }

            }
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(musteriEkran.class.getName()).log(Level.SEVERE, null, ex);
        }
        return o;
    }

    @Override
    public ArrayList<Object[]> tlHesaplariGoster() {
        // TODO Auto-generated method stub
        ArrayList<Object[]> o = new ArrayList<Object[]>();
        try {
            Scanner reader = new Scanner(new BufferedReader(new FileReader("hesaplar.txt")));
            while (reader.hasNextLine()) {
                String s = reader.nextLine();
                String[] bilgiler = s.split(",");
                if (bilgiler[1].equals(String.valueOf(this.id)) && bilgiler[5].equals("TL")) {
                    Object[] eklenecek = {bilgiler[0], bilgiler[2], bilgiler[4], bilgiler[5], bilgiler[6]};
                    o.add(eklenecek);
                }

            }
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(musteriEkran.class.getName()).log(Level.SEVERE, null, ex);
        }
        return o;
    }

    @Override
    public ArrayList<Object[]> krediKartlariGoster() {
        ArrayList<Object[]> o = new ArrayList<Object[]>();
        try {
            Scanner reader = new Scanner(new BufferedReader(new FileReader("kredi_kartlari.txt")));
            while (reader.hasNextLine()) {
                String s = reader.nextLine();
                String[] bilgiler = s.split(",");
                if (bilgiler[1].equals(String.valueOf(this.id)) && bilgiler[8].equals("1")) {
                    Object[] eklenecek = {bilgiler[0], bilgiler[2], bilgiler[4], bilgiler[5], bilgiler[6]};
                    o.add(eklenecek);
                }

            }
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(musteriEkran.class.getName()).log(Level.SEVERE, null, ex);
        }
        return o;
    }

    @Override
    public void faturaOdeme(String hesap_id, String kurum, int cekilecek_miktar, int bakiye, String odeme_yontemi) {
        // TODO Auto-generated method stub
        if (odeme_yontemi.equals("Banka Hesabı")) {
            try {
                List<String> file_content = new ArrayList<>(Files.readAllLines(Paths.get("hesaplar.txt"), StandardCharsets.UTF_8));
                for (int i = 0; i < file_content.size(); i++) {
                    String line = file_content.get(i);
                    String[] line_split = line.split(",");
                    if (line_split[0].equals(hesap_id)) {
                        line_split[6] = Integer.toString(bakiye - cekilecek_miktar);
                        String joined = String.join(",", line_split);
                        try {
                            file_content.set(i, joined);

                            BufferedWriter writer = new BufferedWriter(new FileWriter("hesap_islemleri.txt", true));
                            writer.write(line_split[0] + "," + "-"
                                    + String.valueOf(cekilecek_miktar) + ","
                                    + kurum + ","
                                    + String.valueOf(bakiye - cekilecek_miktar)
                                    + "," + String.valueOf(LocalDate.now()) + "\n");

                            writer.close();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "İşlem Sırasında Hata Meydana Geldi :(");
                        }
                    }
                }

                Files.write(Paths.get("hesaplar.txt"), file_content, StandardCharsets.UTF_8);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        if (odeme_yontemi.equals("Kredi Kartı")) {
            try {
                List<String> file_content = new ArrayList<>(Files.readAllLines(Paths.get("kredi_kartlari.txt"), StandardCharsets.UTF_8));
                for (int i = 0; i < file_content.size(); i++) {
                    String line = file_content.get(i);
                    String[] line_split = line.split(",");
                    if (line_split[0].equals(hesap_id)) {
                        line_split[5] = Integer.toString(cekilecek_miktar + Integer.parseInt(line_split[5]));
                        line_split[6] = Integer.toString(bakiye - cekilecek_miktar);
                        String joined = String.join(",", line_split);
                        try {
                            file_content.set(i, joined);

                            BufferedWriter writer = new BufferedWriter(new FileWriter("kredi_karti_islemler.txt", true));
                            writer.write(line_split[0] + "," + "-"
                                    + String.valueOf(cekilecek_miktar) + ","
                                    + kurum + ","
                                    + String.valueOf(bakiye - cekilecek_miktar)
                                    + "," + String.valueOf(LocalDate.now()) + "\n");

                            writer.close();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "İşlem Sırasında Hata Meydana Geldi :(");
                        }
                    }
                }

                Files.write(Paths.get("kredi_kartlari.txt"), file_content, StandardCharsets.UTF_8);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    @Override
    public ArrayList<Object[]> islemGecmisiGoruntule(String hesap_id) {
        ArrayList<Object[]> return_ob = new ArrayList<Object[]>();
        try {
            // TODO Auto-generated method stub
            int count = 0;
            Scanner reader = new Scanner(new BufferedReader(new FileReader("hesap_islemleri.txt")));
            while (reader.hasNextLine()) {
                String s = reader.nextLine();
                String[] bilgiler = s.split(",");
                if (bilgiler[0].equals(hesap_id)) {
                    Object[] eklenecek = {bilgiler[4], bilgiler[1], bilgiler[2], bilgiler[3]};
                    return_ob.add(eklenecek);
                }
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Musteri.class.getName()).log(Level.SEVERE, null, ex);
        }
        return return_ob;
    }

    public String string_format() {
        return String.valueOf(this.id) + ","
                + this.name + ","
                + this.surname + ","
                + String.valueOf(this.password) + ","
                + String.valueOf(this.telNumber) + ","
                + this.tc + ","
                + this.cinsiyet + ","
                + String.valueOf(this.aktif);
    }

    @Override
    public void krediKartiAl(long kredi_karti_no, int kredi_karti_cvv, int kredi_karti_limit, String sifre) {
        try {
            KrediKarti k = new KrediKarti(this.id, kredi_karti_no, kredi_karti_cvv, kredi_karti_limit, sifre);
            k.krediKartiKaydet();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void krediKartiBorcOde(int kredi_karti_id, int hesap_id, int odenecek_miktar) {
        try {
            List<String> file_content = new ArrayList<>(Files.readAllLines(Paths.get("kredi_kartlari.txt"), StandardCharsets.UTF_8));
            for (int i = 0; i < file_content.size(); i++) {
                String line = file_content.get(i);
                String[] line_split = line.split(",");
                int borc = Integer.parseInt(line_split[5]);
                int bakiye = Integer.parseInt(line_split[6]);
                if (line_split[0].equals(String.valueOf(kredi_karti_id))) {
                    line_split[5] = Integer.toString(borc - odenecek_miktar);
                    line_split[6] = Integer.toString(bakiye + odenecek_miktar);
                    String joined = String.join(",", line_split);
                    file_content.set(i, joined);
                    this.paraCek(String.valueOf(hesap_id), odenecek_miktar, "Kredi Karti Borc Odeme");
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter("kredi_karti_islemler.txt", true));
                        writer.write(line_split[0] + "," + "+"
                                + String.valueOf(odenecek_miktar) + ","
                                + "Borc Yatirma" + ","
                                + String.valueOf(borc - odenecek_miktar)
                                + "," + String.valueOf(LocalDate.now()) + "\n");

                        writer.close();
                        JOptionPane.showMessageDialog(null, "Ödeme başarıyla gerçekleşti!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "İşlem Sırasında Hata Meydana Geldi :(");
                    }
                }
            }

            Files.write(Paths.get("kredi_kartlari.txt"), file_content, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public ArrayList<Object[]> krediKartiIslemGecmisiGoruntule(String kredi_karti_id) {
        ArrayList<Object[]> ob = new ArrayList<>();
        try {
            // TODO Auto-generated method stub
            Scanner reader = new Scanner(new BufferedReader(new FileReader("kredi_karti_islemler.txt")));
            while (reader.hasNextLine()) {
                String s = reader.nextLine();
                String[] bilgiler = s.split(",");
                if (bilgiler[0].equals(kredi_karti_id)) {
                    Object[] eklenecek = {bilgiler[4], bilgiler[1], bilgiler[2], bilgiler[3]};
                    ob.add(eklenecek);
                }
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Musteri.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ob;
    }

    @Override
    public ArrayList<Object[]> krediGoster() {
        ArrayList<Object[]> ob = new ArrayList<>();
        try {
            Scanner reader = new Scanner(new BufferedReader(new FileReader("krediler.txt")));
            while (reader.hasNextLine()) {
                String s = reader.nextLine();
                String[] bilgiler = s.split(",");
                if (bilgiler[1].equals(String.valueOf(this.id)) && bilgiler[7].equals("1")) {
                    Object[] eklenecek = {bilgiler[0], bilgiler[2], bilgiler[3], bilgiler[5], bilgiler[6]};
                    ob.add(eklenecek);
                }
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(musteriEkran.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ob;
    }

    public void sifreDegistir(String yeni_sifre) {
        try {
            List<String> file_content = new ArrayList<>(Files.readAllLines(Paths.get("musteri.txt"), StandardCharsets.UTF_8));
            for (int i = 0; i < file_content.size(); i++) {
                String line = file_content.get(i);
                String[] line_split = line.split(",");
                if (line_split[0].equals(String.valueOf(this.id))) {
                    line_split[3] = yeni_sifre;
                    String joined = String.join(",", line_split);
                    file_content.set(i, joined);
                }
            }
            Files.write(Paths.get("musteri.txt"), file_content, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void limitBasvur(String kredi_karti_id, int mevcut_limit, int yeni_limit) {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter("kredi_karti_limit_islemler.txt", true));
            writer.write((String.valueOf(kredi_karti_id) + ","
                    + String.valueOf(mevcut_limit) + ","
                    + String.valueOf(yeni_limit) + ","
                    + "0" + "\n"));
            writer.close();
            JOptionPane.showMessageDialog(null, "Başvuru başarıyla alındı.");
        } catch (IOException ex) {
            Logger.getLogger(Musteri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void girisYapildi() {
        JOptionPane.showMessageDialog(null, this.name + " Hoşgeldiniz. Müşteri olarak giriş yapıldı.");
    }

    public void musteriKaydet() throws IOException {
        boolean kontrol = true;
        Scanner s = new Scanner(new BufferedReader(new FileReader("musteri.txt")));
        String[] sarray = null;
        while (s.hasNextLine()) {
            String ogb = s.nextLine();
            sarray = ogb.split(",");

            if (sarray[5].equals(this.tc)) {
                JOptionPane.showMessageDialog(null, "Girilen T.C. numarası kullanımda!");
                kontrol = false;
            }
        }
        if (kontrol) {
            BufferedWriter writer = new BufferedWriter(new FileWriter("musteri.txt", true));
            writer.write((String.valueOf(this.id) + ","
                    + this.name + ","
                    + this.surname + ","
                    + this.password + ","
                    + this.telNumber + ","
                    + this.tc + ","
                    + this.cinsiyet + ","
                    + String.valueOf(this.aktif) + "\n"));
            writer.close();
            JOptionPane.showMessageDialog(null, "Müşteri başarıyla kaydedildi!");
        }
        s.close();
    }
}
