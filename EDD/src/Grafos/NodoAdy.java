/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafos;

/**
 *
 * @author Anto
 */
public class NodoAdy {
    private NodoVert vertice;
    private NodoAdy sigAdyacente;
    private int etiqueta;

    public NodoAdy(NodoVert vertice, NodoAdy sigAdyacente, int etiqueta){
        this.vertice=vertice;
        this.sigAdyacente=sigAdyacente;
        this.etiqueta=etiqueta;
    }
    public NodoVert getVertice(){
        return vertice;
    }
    public void setVertice(NodoVert vertice){
        this.vertice=vertice;
    }
    public NodoAdy getSigAdyacente(){
        return this.sigAdyacente;
    }
    public void setSigAdyacente(NodoAdy sigAdyacente){
       this.sigAdyacente=sigAdyacente;
    }
    public void setPrimerAdy(NodoAdy sigAdyacente){
        this.sigAdyacente=sigAdyacente;
    }
    public int getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(int etiqueta) {
        this.etiqueta = etiqueta;
    }
    
}
