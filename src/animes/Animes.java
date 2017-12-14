/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animes;

import Controller.ControllerGUI;
import GUI.AnimeAddGUI;
import GUI.AnimeEditGUI;
import GUI.AnimeGUI;
import GUI.InfoGUI;
import java.awt.event.ActionListener;
import Modelo.AnimeList;
import java.io.IOException;
/**
 *
 * @author Dan
 */
public class Animes {
        private static AnimeList animes;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        //Inicializacion de variables
                animes = new AnimeList();
        	AnimeGUI view = new AnimeGUI(animes);
                AnimeAddGUI addview = new AnimeAddGUI();
                InfoGUI infoview = new InfoGUI(view);
                AnimeEditGUI editview = new AnimeEditGUI();
		ActionListener ctr = new ControllerGUI(view, addview, infoview, editview);

                //Asignar controlado
		view.controller(ctr);
                addview.controller(ctr);
                infoview.controller(ctr);
                editview.controller(ctr);

                //Mostrar solo la vista principal
                editview.setVisible(false);
                infoview.setVisible(false);
                addview.setVisible(false);
		view.setVisible(true);
    }

}
