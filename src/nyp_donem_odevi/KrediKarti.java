package nyp_donem_odevi;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author bunuox
 */
public class KrediKarti {

    int id;
    int musteri_id;
    long kredi_karti_no;
    int kredi_karti_cvv;
    int kredi_karti_limit;
    int kullanilabilir_bakiye;
    int kredi_karti_borc;
    String sifre;
    int aktif = 0;

    public KrediKarti(int musteri_id, long kredi_karti_no, int kredi_karti_cvv, int kredi_karti_limit, String sifre) throws IOException {
        this.musteri_id = musteri_id;
        this.kredi_karti_cvv = kredi_karti_cvv;
        this.kredi_karti_no = kredi_karti_no;
        this.kredi_karti_limit = kredi_karti_limit;
        this.sifre = sifre;
        this.kullanilabilir_bakiye = kredi_karti_limit;
        this.kredi_karti_borc = 0;
        this.idEkle();

    }

    public KrediKarti(int id, int musteri_id, long kredi_karti_no, int kredi_karti_cvv, int kredi_karti_limit, String sifre) {
        this.id = id;
        this.musteri_id = musteri_id;
        this.kredi_karti_no = kredi_karti_no;
        this.kredi_karti_cvv = kredi_karti_cvv;
        this.kredi_karti_limit = kredi_karti_limit;
        this.sifre = sifre;
    }

    public void krediKartiKaydet() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("kredi_kartlari.txt", true));
        writer.write((String.valueOf(this.id) + ","
                + String.valueOf(this.musteri_id) + ","
                + String.valueOf(this.kredi_karti_no) + ","
                + String.valueOf(this.kredi_karti_cvv) + ","
                + String.valueOf(this.kredi_karti_limit) + ","
                + String.valueOf(this.kredi_karti_borc) + ","
                + String.valueOf(this.kullanilabilir_bakiye) + ","
                + this.sifre + ","
                + String.valueOf(this.aktif) + "\n"));
        writer.close();
    }

    private void idEkle() throws FileNotFoundException, IOException {
        Scanner s = new Scanner(new BufferedReader(new FileReader("kredi_kartlari.txt")));
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

    public String stringFormat() {
        return (String.valueOf(this.id) + ","
                + String.valueOf(this.musteri_id) + ","
                + String.valueOf(this.kredi_karti_no) + ","
                + String.valueOf(this.kredi_karti_cvv) + ","
                + String.valueOf(this.kredi_karti_limit) + ","
                + this.sifre + ","
                + String.valueOf(this.aktif) + "\n");
    }
}
