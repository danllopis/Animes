/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import GUI.AnimeAddGUI;
import GUI.AnimeEditGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import GUI.AnimeGUI;
import GUI.InfoGUI;
import Modelo.AnimeList;
import Modelo.Serie;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javax.swing.DefaultListModel;

/**
 *
 * @author Dan
 */
public class ControllerGUI implements ActionListener {

    private final AnimeGUI anime;
    private final AnimeList lista;
    private final List<Serie> listaseries;
    private final AnimeAddGUI animeAdd;
    private final InfoGUI info;
    private final AnimeEditGUI edit;

    public ControllerGUI(AnimeGUI view, AnimeAddGUI addview, InfoGUI infog, AnimeEditGUI editg) {
        this.anime = view;
        lista = anime.getAnimeList();
        info = infog;
        listaseries = lista.getAnimes();
        animeAdd = addview;
        edit = editg;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if(command.equals("add")) {
            anime.setVisible(false);
            animeAdd.setVisible(true);

        } else if (command.equals("delete")) {
            String nombre = anime.getTextnombre().getText();
            String genero = anime.getTextgender().getText();

            Serie remove = getSerie();

            boolean delete = anime.getListModel().removeElement(remove.getName());
            listaseries.remove(remove);
            
            DefaultListModel listModel = new DefaultListModel();
            if (nombre != null && !nombre.equals("")) {

                for (Serie s : listaseries) {
                    if (s.getName().toLowerCase().contains(nombre.toLowerCase())) {
                        listModel.addElement(s.getName());
                    }
                }
                                    anime.getjList().removeAll();
                                    anime.getjList().setModel(listModel);
            }
                if (genero != null && !genero.equals("") && !genero.equals(",")) {
                    for (Serie s : listaseries) {
                        if (s.getGeneros().toLowerCase().contains(genero.toLowerCase())) {

                            listModel.addElement(s.getName());
                        }
                    }
                                        anime.getjList().removeAll();
                                        anime.getjList().setModel(listModel);
                }

                    try {
                        guardarEnFichero(listaseries);
                    } catch (IOException ex) {

                    }
                    int size = Integer.parseInt(anime.getNsize()) - 1;
                    anime.setNsize(size);
                
            
        } else if (command.equals("back")) {
            anime.setVisible(true);
            info.setVisible(false);
            info.resetInfo();

        } else if (command.equals("searchname")) {
            String nombre = anime.getTextnombre().getText();
            if (nombre != null && !nombre.equals("")) {
                DefaultListModel listModel = new DefaultListModel();
                if(nombre.length()!=1){
                for (Serie s : listaseries) {
                    if (s.getName().toLowerCase().contains(nombre.toLowerCase())) {

                        listModel.addElement(s.getName());
                    }
                }
                } else {
                    for (Serie s : listaseries) {
                    if (s.getName().toLowerCase().startsWith(nombre.toLowerCase())) {

                        listModel.addElement(s.getName());
                    }
                    
                }
                }
                anime.getjList().removeAll();
                anime.getjList().setModel(listModel);

            } else {
                anime.getjList().setModel(anime.getListModel());
            }

        } else if (command.equals("searchgenero")) {
            String genero = anime.getTextgender().getText();
            if (genero != null && !genero.equals("") && !genero.equals(",")) {
                DefaultListModel listModel = new DefaultListModel();
                for (Serie s : listaseries) {
                    if (s.getGeneros().toLowerCase().contains(genero.toLowerCase())) {

                        listModel.addElement(s.getName());
                    }
                }
                anime.getjList().removeAll();
                anime.getjList().setModel(listModel);

            } else {
                anime.getjList().setModel(anime.getListModel());
            }

        } else if (command.equals("create")) {
            String Addnombre = animeAdd.gettNombre().getText();
            String nombre = Addnombre;
            
            String AddnumCaps = animeAdd.gettNumcaps().getText();
            int numCaps;
            
            if(!AddnumCaps.equals("")){            
                numCaps = Integer.parseInt(AddnumCaps);
            } else {
                numCaps = 0;
            }
            
            String Addgeneros = animeAdd.gettGeneros().getText();
            String generos;
            
            if(!Addgeneros.equals("")){            
                generos = Addgeneros;
            } else {
                generos = "Nada";
            }

            boolean fav = Boolean.parseBoolean(animeAdd.getBg().getSelection().getActionCommand());
            Serie nueva = new Serie(nombre, numCaps, generos, fav);

            if (!comprobarSiEsta(nueva)) {
                try {
                    anime.getListModel().addElement(nueva.getName());
                    sortListModel(anime.getListModel());
                    anime.getAnimeList().getAnimes().add(nueva);
                    guardarEnFichero(listaseries);
                } catch (IOException ex) {
                    throw new RuntimeException("No existe archivo para guardar");
                }
                
                int size = Integer.parseInt(anime.getNsize()) + 1;
                anime.setNsize(size);

            }
            animeAdd.limpiarGuiAdd();
            animeAdd.setVisible(false);
            anime.setVisible(true);

        } else if (command.equals("cancel")) {
            edit.setVisible(false);
            animeAdd.limpiarGuiAdd();
            animeAdd.setVisible(false);
            anime.setVisible(true);

        } else if (command.equals("showInfo")) {

            //Obtener serie de la lista
            Serie aux = getSerie();

            info.setInfo(aux);

            info.setVisible(true);
            anime.setVisible(false);
        } else if (command.equals("edit")) {

            //Obtener serie de la lista
            Serie aux = getSerie();

            edit.setInfo(aux);
            anime.setVisible(false);
            edit.setVisible(true);

        } else if (command.equals("doEdit")) {
            int nA = anime.getjList().getSelectedIndex();
            Serie editada = edit.getInfo();
            listaseries.set(nA, editada);

            anime.getListModel().setElementAt(editada.getName(), nA);

            try {
                guardarEnFichero(listaseries);
            } catch (IOException ex) {

            }
            
            anime.getTextnombre().setText("");
            anime.getTextgender().setText("");
            anime.setListModel(anime.getListModel());

            anime.setVisible(true);
            edit.setVisible(false);
        }

    }

    private void guardarEnFichero(List<Serie> s) throws FileNotFoundException, IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("animes.txt"));
        Collections.sort(s);
        if (!s.isEmpty()) {
            for (Serie aux : s) {
                bw.write(aux.toString());
                bw.newLine();
            }
        }
        bw.close();

    }

    private Serie getSerie() {
        String name = anime.getjList().getSelectedValue();
        Serie aux = new Serie(name);
        for (Serie s : listaseries) {
            if (s.equals(aux)) {
                aux = s;
            }
        }

        return aux;
    }



    private boolean comprobarSiEsta(Serie s) {
        boolean esta = false;
        for (Serie a : listaseries) {
            if (s.equals(a)) {
                esta = true;
            }
        }

        return esta;
    }
    
    private void sortListModel(DefaultListModel m){
        List aux = Collections.list(m.elements());
        Collections.sort(aux);
        m.clear();
        for(Object o: aux){
            m.addElement(o);
        }
    }
}
