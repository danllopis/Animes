/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author Dan
 */
public class AnimeList {

    private List<Serie> animes = new ArrayList<>();

    public AnimeList() throws FileNotFoundException, IOException {

        try (BufferedReader br = new BufferedReader(new FileReader("animes.txt"))) {
            String line = br.readLine();
            while (line != null) {
                Serie serie = processLine(line);
                animes.add(serie);
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            File file = new File("animes.txt");
            file.createNewFile();
        }
       Collections.sort(animes);
    }

    private Serie processLine(String line) {
        StringTokenizer st = new StringTokenizer(line, ";");
        Serie serie;
        String nombre = st.nextToken();
        int nCaps = Integer.parseInt(st.nextToken());
        String generos = st.nextToken();
        boolean fav = Boolean.parseBoolean(st.nextToken());

        serie = new Serie(nombre, nCaps, generos, fav);

        return serie;
    }

    public List<Serie> getAnimes() {
        return animes;
    }

    public void setAnimes(List<Serie> animes) {
        this.animes = animes;
    }

}
