/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conjuntistas;

/**
 *
 * @author Anto
 */
public class CeldaHash {
    private Object elem;
    private int estado ;
    
    public CeldaHash (Object elem, int estado ){
        this.elem=elem;
        this.estado=estado;
    }
    //modificadoras
    public void setElem (Object elem){
        this.elem=elem;
    }
    public void setEstado (int estdo){
        this.estado=estado;
    }
    //observadoras
    public Object getElem (){
        return elem;
    }
    public int getEstado (){
        return estado;       
    }
}
