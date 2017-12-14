/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.List;

/**
 *
 * @author Dan
 */
public class Serie implements Comparable<Object>{
    private String name;
    private int nCaps;
    private String generos;
    private boolean isFavorite;
    
    public Serie(){
        
    }
    
    public Serie(String name, int nc, String g, boolean fav){
        this.name = name;
        nCaps = nc;
        generos = g;
        isFavorite = fav;
    }

    public Serie(String nombre) {
        this.name = nombre;
        nCaps = 0;
        generos = null;
        isFavorite = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getnCaps() {
        return nCaps;
    }

    public void setnCaps(int nCaps) {
        this.nCaps = nCaps;
    }

    public String getGeneros() {
        return generos;
    }

    public void setGeneros(String generos) {
        this.generos = generos;
    }

    public boolean isIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }
    
    @Override
    public boolean equals(Object o){
        boolean isEqual = false;
        if(o instanceof Serie){
            Serie s = (Serie) o;
            isEqual = s.getName().equalsIgnoreCase(name);
        }         
        return isEqual;
    }
    
    @Override
    public String toString(){
        return name+";"+nCaps+";"+generos+";"+isFavorite;
                }

    @Override
    public int compareTo(Object o) {
        if(o instanceof Serie)
        return this.name.compareTo(((Serie) o).name);
        else{
            return -1;
        }
    }
    
}
