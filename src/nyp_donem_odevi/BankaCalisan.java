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
import java.time.LocalDateTime;
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
public class BankaCalisan extends BankaPersoneli implements IBankaCalisan {

    int aktif = 1;

    BankaCalisan(String job_title, String name, String surname, String password, String telNumber, String branch, int maas, String tc, String cinsiyet) throws FileNotFoundException {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.telNumber = telNumber;
        this.jobTitle = job_title;
        this.branch = branch;
        this.maas = maas;
        this.aktif = 1;
        this.tc = tc;
        this.cinsiyet = cinsiyet;
        idEKle();
    }

    BankaCalisan(int id, String job_title, String name, String surname, String password, String telNumber, String tc, String cinsiyet, int maas, String branch) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.telNumber = telNumber;
        this.jobTitle = job_title;
        this.branch = branch;
        this.maas = maas;
        this.tc = tc;
        this.cinsiyet = cinsiyet;
    }

    BankaCalisan(int id) throws FileNotFoundException {
        Scanner s = new Scanner(new BufferedReader(new FileReader("banka_personeli.txt")));
        String[] sarray = null;
        while (s.hasNextLine()) {
            String ogb = s.nextLine();
            sarray = ogb.split(",");
            if (sarray[0].equals(String.valueOf(id))) {
                this.id = id;
                this.jobTitle = sarray[1];
                this.name = sarray[2];
                this.surname = sarray[3];
                this.password = sarray[4];
                this.telNumber = sarray[5];
                this.tc = sarray[6];
                this.cinsiyet = sarray[7];
                this.maas = Integer.parseInt(sarray[8]);
                this.branch = sarray[9];
                this.aktif = 1;
            }
        }
    }

    public void idEKle() throws FileNotFoundException {
        Scanner s = new Scanner(new BufferedReader(new FileReader("banka_personeli.txt")));
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

    public void calisanEkle() throws IOException {
        boolean kontrol = true;
        Scanner s = new Scanner(new BufferedReader(new FileReader("banka_personeli.txt")));
        String[] sarray = null;
        while (s.hasNextLine()) {
            String ogb = s.nextLine();
            sarray = ogb.split(",");

            if (sarray[6].equals(this.tc)) {
                JOptionPane.showMessageDialog(null, "Girilen T.C. numarası kullanımda!");
                kontrol = false;
            }
        }
        if (kontrol) {
            BufferedWriter writer = new BufferedWriter(new FileWriter("banka_personeli.txt", true));
            writer.write((String.valueOf(this.id) + ","
                    + this.jobTitle + ","
                    + this.name + ","
                    + this.surname + ","
                    + this.password + ","
                    + this.telNumber + ","
                    + this.tc + ","
                    + this.cinsiyet + ","
                    + String.valueOf(this.maas) + ","
                    + this.branch + ","
                    + String.valueOf(this.aktif) + "\n"));
            islemKaydet("CALISAN OLUSTURULDU");
            writer.close();
            JOptionPane.showMessageDialog(null, "Personel başarıyla kaydedildi.");
        }
    }

    @Override
    public void musteriEkle(String name, String surname, String password, String telNumber, String tc, String cinsiyet) {
        // TODO Auto-generated method stub
        Musteri m;
        try {
            m = new Musteri(name, surname, password, telNumber, tc, cinsiyet);
            m.musteriKaydet();
            islemKaydet("MUSTERI OLUSTURULDU");
        } catch (IOException ex) {
            Logger.getLogger(BankaCalisan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void musteriBilgiGuncelle(String musteri_id, String name, String surname, String password, String telNumber, String tc, String cinsiyet) {
        // TODO Auto-generated method stub        
        try {
            Musteri m = new Musteri(Integer.parseInt(musteri_id), name, surname, password, telNumber, tc, cinsiyet);
            List<String> file_content = new ArrayList<>(Files.readAllLines(Paths.get("musteri.txt"), StandardCharsets.UTF_8));
            for (int i = 0; i < file_content.size(); i++) {
                String line = file_content.get(i);
                String[] line_split = line.split(",");

                if (line_split[0].equals(musteri_id)) {
                    String new_info = m.string_format();
                    String joined = String.join(",", new_info);
                    file_content.set(i, joined);
                    islemKaydet(musteri_id + " ID MUSTERI BILGI GUNCELLEME");
                }
            }
            Files.write(Paths.get("musteri.txt"), file_content, StandardCharsets.UTF_8);

        } catch (IOException ex) {
            System.out.println((ex.getMessage()));
        }

    }

    @Override
    public void musteriSil(String musteri_id) {
        // TODO Auto-generated method stub
        try {
            List<String> file_content = new ArrayList<>(Files.readAllLines(Paths.get("musteri.txt"), StandardCharsets.UTF_8));
            for (int i = 0; i < file_content.size(); i++) {
                String line = file_content.get(i);
                String[] line_split = line.split(",");

                if (line_split[0].equals(musteri_id)) {
                    line_split[7] = "0";
                    String joined = String.join(",", line_split);
                    file_content.set(i, joined);
                    islemKaydet(musteri_id + " ID MUSTERI SILME");
                    JOptionPane.showMessageDialog(null, "Girilen T.C. numarası kullanımda!");
                }
            }
            Files.write(Paths.get("musteri.txt"), file_content, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void krediOnayla(String kredi_id) {
        // TODO Auto-generated method stub
        try {
            List<String> file_content = new ArrayList<>(Files.readAllLines(Paths.get("krediler.txt"), StandardCharsets.UTF_8));
            for (int i = 0; i < file_content.size(); i++) {
                String line = file_content.get(i);
                String[] line_split = line.split(",");

                if (line_split[0].equals(kredi_id)) {
                    line_split[7] = "1";
                    String joined = String.join(",", line_split);
                    file_content.set(i, joined);
                    islemKaydet(kredi_id + " ID KREDI ONAYLAMA");
                }
            }
            Files.write(Paths.get("krediler.txt"), file_content, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void krediRed(String kredi_id) {
        // TODO Auto-generated method stub
        try {
            List<String> file_content = new ArrayList<>(Files.readAllLines(Paths.get("krediler.txt"), StandardCharsets.UTF_8));
            for (int i = 0; i < file_content.size(); i++) {
                String line = file_content.get(i);
                String[] line_split = line.split(",");

                if (line_split[0].equals(kredi_id)) {
                    line_split[7] = "-1";
                    String joined = String.join(",", line_split);
                    file_content.set(i, joined);
                    islemKaydet(kredi_id + " ID KREDI REDDETME");
                }
            }
            Files.write(Paths.get("krediler.txt"), file_content, StandardCharsets.UTF_8);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public ArrayList<Object[]> bekleyenKredileriGetir() {
        // TODO Auto-generated method stub
        ArrayList<Object[]> o = new ArrayList<Object[]>();
        try {
            Scanner reader = new Scanner(new BufferedReader(new FileReader("krediler.txt")));
            while (reader.hasNextLine()) {
                String s = reader.nextLine();
                String[] bilgiler = s.split(",");
                if (bilgiler[7].equals("0")) {
                    Object[] eklenecek = {bilgiler[0], bilgiler[1], bilgiler[2], bilgiler[3], bilgiler[4], bilgiler[5]};
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
    public ArrayList<Object[]> musteriGetir() {
        // TODO Auto-generated method stub
        ArrayList<Object[]> o = new ArrayList<Object[]>();
        try {
            Scanner reader = new Scanner(new BufferedReader(new FileReader("musteri.txt")));
            while (reader.hasNextLine()) {
                String s = reader.nextLine();
                String[] bilgiler = s.split(",");
                if (bilgiler[7].equals("1")) {
                    Object[] eklenecek = {bilgiler[0], bilgiler[1], bilgiler[2], bilgiler[4], bilgiler[5], bilgiler[6]};
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
    public void sifreDegistir(String yeni_sifre) {
        try {
            List<String> file_content = new ArrayList<>(Files.readAllLines(Paths.get("banka_personeli.txt"), StandardCharsets.UTF_8));
            for (int i = 0; i < file_content.size(); i++) {
                String line = file_content.get(i);
                String[] line_split = line.split(",");
                if (line_split[0].equals(String.valueOf(this.id))) {
                    line_split[4] = yeni_sifre;
                    String joined = String.join(",", line_split);
                    file_content.set(i, joined);
                }
                islemKaydet("MEVCUT SIFRESINI DEGISTIRDI");
            }
            Files.write(Paths.get("banka_personeli.txt"), file_content, StandardCharsets.UTF_8);
            JOptionPane.showMessageDialog(null, "Şifre başarıyla değiştirildi.");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public ArrayList<Object[]> islemGecmisiGetir() {
        ArrayList<Object[]> return_ob = new ArrayList<Object[]>();
        try {
            // TODO Auto-generated method stub
            int count = 0;
            Scanner reader = new Scanner(new BufferedReader(new FileReader("hesap_islemleri.txt")));
            while (reader.hasNextLine()) {
                String s = reader.nextLine();
                String[] bilgiler = s.split(",");

                Scanner reader2 = new Scanner(new BufferedReader(new FileReader("hesaplar.txt")));

                while (reader2.hasNextLine()) {
                    String ss = reader2.nextLine();
                    String[] hesap_bilgiler = ss.split(",");

                    if (bilgiler[0].equals(hesap_bilgiler[1])) {
                        Object[] eklenecek = {hesap_bilgiler[0], bilgiler[1], bilgiler[2], "Hesap", bilgiler[4]};
                        return_ob.add(eklenecek);
                    }
                }
                reader2.close();
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Musteri.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            // TODO Auto-generated method stub
            Scanner reader = new Scanner(new BufferedReader(new FileReader("kredi_karti_islemler.txt")));
            while (reader.hasNextLine()) {
                String s = reader.nextLine();
                String[] bilgiler = s.split(",");

                Scanner reader2 = new Scanner(new BufferedReader(new FileReader("kredi_kartlari.txt")));

                while (reader2.hasNextLine()) {
                    String ss = reader2.nextLine();
                    String[] kredi_bilgiler = ss.split(",");

                    if (bilgiler[0].equals(kredi_bilgiler[1])) {
                        Object[] eklenecek = {kredi_bilgiler[0], bilgiler[1], bilgiler[2], "Kredi Kartı", bilgiler[4]};
                        return_ob.add(eklenecek);
                    }
                }
                reader2.close();
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Musteri.class.getName()).log(Level.SEVERE, null, ex);
        }
        return return_ob;
    }

    public void islemKaydet(String aciklama) throws IOException {
        try (
                BufferedWriter writer = new BufferedWriter(new FileWriter("personel_islemler.txt", true))) {
            writer.write((String.valueOf(this.id) + ","
                    + aciklama + ","
                    + String.valueOf(LocalDateTime.now()) + "\n"));
            writer.close();
        }
    }

    public String string_format() {
        return String.valueOf(this.id) + ","
                + this.jobTitle + ","
                + this.name + ","
                + this.surname + ","
                + this.password + ","
                + this.telNumber + ","
                + this.tc + ","
                + this.cinsiyet + ","
                + String.valueOf(this.maas) + ","
                + this.branch + ","
                + String.valueOf(this.aktif);
    }

    @Override
    public ArrayList<Object[]> limitIslemGetir() {
        ArrayList<Object[]> ob = new ArrayList<>();
        try {
            // TODO Auto-generated method stub
            Scanner reader = new Scanner(new BufferedReader(new FileReader("kredi_karti_limit_islemler.txt")));
            while (reader.hasNextLine()) {
                String s = reader.nextLine();
                String[] bilgiler = s.split(",");
                if (bilgiler[3].equals("0")) {
                    Object[] eklenecek = {bilgiler[0], bilgiler[1], bilgiler[2]};
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
    public void limitGuncelle(String kredi_karti_id, int yeni_limit) {
        try {
            List<String> file_content = new ArrayList<>(Files.readAllLines(Paths.get("kredi_karti_limit_islemler.txt"), StandardCharsets.UTF_8));
            List<String> file_content2 = new ArrayList<>(Files.readAllLines(Paths.get("kredi_kartlari.txt"), StandardCharsets.UTF_8));
            for (int i = 0; i < file_content.size(); i++) {
                String line = file_content.get(i);
                String[] line_split = line.split(",");
                if (line_split[0].equals(kredi_karti_id)) {
                    line_split[3] = "1";
                    String joined = String.join(",", line_split);
                    file_content.set(i, joined);
                }

                for (int j = 0; j < file_content2.size(); j++) {
                    String line2 = file_content2.get(j);
                    String[] line_split2 = line2.split(",");
                    if (line_split2[0].equals(String.valueOf(kredi_karti_id))) {
                        int fark = 0;
                        if (yeni_limit > Integer.parseInt(line_split2[4])) {
                            fark = yeni_limit - Integer.parseInt(line_split2[4]);
                            int kullanilabilir_bakiye = Integer.parseInt(line_split2[6]);
                            int guncelle = kullanilabilir_bakiye + fark;
                            line_split2[6] = String.valueOf(guncelle);
                        } else {
                            fark = Integer.parseInt(line_split2[4]) - yeni_limit;
                            int kullanilabilir_bakiye = Integer.parseInt(line_split2[6]);
                            int guncelle = kullanilabilir_bakiye - fark;
                            line_split2[6] = String.valueOf(guncelle);
                        }
                        line_split2[4] = String.valueOf(yeni_limit);
                        String joined2 = String.join(",", line_split2);
                        file_content2.set(j, joined2);
                    }
                }

                islemKaydet(kredi_karti_id + " ID KREDI KARTININ LIMITINI DEGISTIRDI");
            }
            Files.write(Paths.get("kredi_karti_limit_islemler.txt"), file_content, StandardCharsets.UTF_8);
            Files.write(Paths.get("kredi_kartlari.txt"), file_content2, StandardCharsets.UTF_8);
            JOptionPane.showMessageDialog(null, "Kredi kartı limiti başarıyla onaylandı.");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void limitGuncelleme(String kredi_karti_id, int yeni_limit) {
        try {
            List<String> file_content = new ArrayList<>(Files.readAllLines(Paths.get("kredi_karti_limit_islemler.txt"), StandardCharsets.UTF_8));
            for (int i = 0; i < file_content.size(); i++) {
                String line = file_content.get(i);
                String[] line_split = line.split(",");
                if (line_split[0].equals(kredi_karti_id) && line_split[2].equals(String.valueOf(yeni_limit))) {
                    line_split[3] = "-1";
                    String joined = String.join(",", line_split);
                    file_content.set(i, joined);
                }

                islemKaydet(kredi_karti_id + " ID KREDI KARTININ LIMITINI DEGISIMINI REDDETTI");
            }
            Files.write(Paths.get("kredi_karti_limit_islemler.txt"), file_content, StandardCharsets.UTF_8);
            JOptionPane.showMessageDialog(null, "Kredi kartı limiti başarıyla reddedildi.");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public ArrayList<Object[]> krediKartiGetir() {
        ArrayList<Object[]> ob = new ArrayList<>();
        try {
            // TODO Auto-generated method stub
            Scanner reader = new Scanner(new BufferedReader(new FileReader("kredi_kartlari.txt")));
            while (reader.hasNextLine()) {
                String s = reader.nextLine();
                String[] bilgiler = s.split(",");
                if (bilgiler[8].equals("0")) {
                    Object[] eklenecek = {bilgiler[1], bilgiler[0], bilgiler[2], bilgiler[4]};
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
    public void krediKartiOnayla(String kredi_karti_id) {
        // TODO Auto-generated method stub
        try {
            List<String> file_content = new ArrayList<>(Files.readAllLines(Paths.get("kredi_kartlari.txt"), StandardCharsets.UTF_8));
            for (int i = 0; i < file_content.size(); i++) {
                String line = file_content.get(i);
                String[] line_split = line.split(",");

                if (line_split[0].equals(kredi_karti_id)) {
                    line_split[8] = "1";
                    String joined = String.join(",", line_split);
                    file_content.set(i, joined);
                    islemKaydet(kredi_karti_id + " ID KREDI KARTI ONAYLAMA");
                }
            }
            Files.write(Paths.get("kredi_kartlari.txt"), file_content, StandardCharsets.UTF_8);
            JOptionPane.showMessageDialog(null, "Kredi kartı başvurusu başarıyla onaylandı.");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void girisYapildi() {
        JOptionPane.showMessageDialog(null, this.name + " Hoşgeldiniz. Personel olarak giriş yapıldı.");
    }

    @Override
    public void krediKartiReddet(String kredi_karti_id) {
        // TODO Auto-generated method stub
        try {
            List<String> file_content = new ArrayList<>(Files.readAllLines(Paths.get("kredi_kartlari.txt"), StandardCharsets.UTF_8));
            for (int i = 0; i < file_content.size(); i++) {
                String line = file_content.get(i);
                String[] line_split = line.split(",");

                if (line_split[0].equals(kredi_karti_id)) {
                    line_split[8] = "-1";
                    String joined = String.join(",", line_split);
                    file_content.set(i, joined);
                    islemKaydet(kredi_karti_id + " ID KREDI KARTI REDDETME");
                }
            }
            Files.write(Paths.get("kredi_kartlari.txt"), file_content, StandardCharsets.UTF_8);
            JOptionPane.showMessageDialog(null, "Kredi kartı başvurusu başarıyla reddedildi.");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
