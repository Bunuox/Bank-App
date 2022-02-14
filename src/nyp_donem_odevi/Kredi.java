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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
public class Kredi {

    int kredi_id;
    int musteri_id;
    String kredi_tur;
    int vade;
    int kredi_tutar;
    double odenecek_miktar;
    double kredi_borc;
    int kredi_durum;

    public Kredi(int musteri_id, String kredi_tur, int vade, int kredi_tutar) throws FileNotFoundException {
        this.musteri_id = musteri_id;
        this.kredi_tur = kredi_tur;
        this.vade = vade;
        this.kredi_tutar = kredi_tutar;
        idEKle();
        this.odenecek_miktar = odenecekHesapla(vade, kredi_tutar);
        this.kredi_borc = odenecek_miktar;

    }

    public Kredi(int id, int musteri_id, String kredi_tur, int vade, int kredi_tutar) throws FileNotFoundException {
        this.kredi_id = id;
        this.musteri_id = musteri_id;
        this.kredi_tur = kredi_tur;
        this.vade = vade;
        this.kredi_tutar = kredi_tutar;
        this.odenecek_miktar = odenecekHesapla(vade, kredi_tutar);
        this.kredi_borc = odenecek_miktar;

    }

    public Kredi(int id) throws FileNotFoundException {
        this.kredi_id = id;
        Scanner s = new Scanner(new BufferedReader(new FileReader("krediler.txt")));
        String[] sarray = null;

        while (s.hasNextLine()) {
            String ogb = s.nextLine();
            sarray = ogb.split(",");

            if (sarray[0].equals(String.valueOf(this.kredi_id))) {
                this.musteri_id = Integer.parseInt(sarray[1]);
                this.kredi_tur = sarray[2];
                this.vade = Integer.parseInt(sarray[3]);
                this.kredi_tutar = Integer.parseInt(sarray[4]);
                this.odenecek_miktar = Float.parseFloat(sarray[5]);
                this.kredi_borc = Float.parseFloat(sarray[6]);
            }
        }
    }

    public void idEKle() throws FileNotFoundException {
        Scanner s = new Scanner(new BufferedReader(new FileReader("krediler.txt")));
        String[] sarray = null;
        while (s.hasNextLine()) {
            String ogb = s.nextLine();
            sarray = ogb.split(",");
        }
        if (sarray == null) {
            this.kredi_id = 0;
        } else {
            this.kredi_id = Integer.parseInt(sarray[0]) + 1;
        }
    }

    public void krediKaydet() throws IOException {
        this.kredi_durum = 0;
        BufferedWriter writer = new BufferedWriter(new FileWriter("krediler.txt", true));
        writer.write((String.valueOf(this.kredi_id) + ","
                + String.valueOf(this.musteri_id) + ","
                + this.kredi_tur + ","
                + String.valueOf(this.vade) + ","
                + String.valueOf(this.kredi_tutar) + ","
                + String.valueOf(this.odenecek_miktar) + ","
                + String.valueOf(this.kredi_borc) + ","
                + String.valueOf(this.kredi_durum) + "\n"));
        writer.close();
    }

    public static double odenecekHesapla(int vade, int tutar) {
        if (vade == 9) {
            return tutar * 1.25;
        } else if (vade == 12) {
            return tutar * 1.40;
        } else if (vade == 24) {
            return tutar * 1.68;
        } else if (vade == 60) {
            return tutar * 2;
        } else {
            return tutar * 2.5;
        }
    }

    public void krediBorcOde(int odenecek_miktar) {
        // TODO Auto-generated method stub
        try {
            List<String> file_content = new ArrayList<>(Files.readAllLines(Paths.get("krediler.txt"), StandardCharsets.UTF_8));
            for (int i = 0; i < file_content.size(); i++) {
                String line = file_content.get(i);
                String[] line_split = line.split(",");
                float borc = Float.parseFloat(line_split[6]);
                if (line_split[0].equals(String.valueOf(this.kredi_id))) {
                    line_split[6] = String.valueOf(borc - odenecek_miktar);
                    String joined = String.join(",", line_split);
                    file_content.set(i, joined);
                }
            }
            Files.write(Paths.get("krediler.txt"), file_content, StandardCharsets.UTF_8);
            JOptionPane.showMessageDialog(null, "Kredi Borcu Başarıyla Yatırıldı.");
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
