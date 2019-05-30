
import java.io.Serializable;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Clase que representa a la configuracion para guardar la partida
 * @author juan
 */
public class Configuracion implements Serializable{
    private ArrayList<Boolean> estadobotones;
    private String nombreusuario,palabra_a_adivinar,palabra_escrita;
    private int puntos,contador_errores,contador_aciertos, minutos, segundos, horas;
    /**
     * Constructor por defecto
     */
    public Configuracion()
    {
        estadobotones = new ArrayList<>();
        nombreusuario = "";
        puntos = 0;
        palabra_a_adivinar = "";
        palabra_escrita ="";
        contador_errores = 0;
        contador_aciertos = 0;
        minutos = 0;
        horas = 0;
        segundos = 0;
        
    }
    /**
     * Obtiene un arraylist con toda la configuracion
     * @return devuelve un arraylist con toda la configuracion
     */
    public ArrayList<Boolean> getEstadobotones() {
        return estadobotones;
    }
    /**
     * modifica el arraylist con la configuracion de todos los botones
     * @param estadobotones nuevo estado para los botones
     */
    public void setEstadobotones(ArrayList<Boolean> estadobotones) {
        this.estadobotones = estadobotones;
    }
    /**
     * obtiene el nombre del usuario 
     * @return devuelve el nombre del usuario
     */
    public String getNombreusuario() {
        return nombreusuario;
    }
    /**
     * modifica el nombre del usuario
     * @param nombreusuario nuevo nombre del usuario
     */
    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }
    /**
     * obtiene los puntos obtenidos por el jugador
     * @return devuelve los puntos obtenidos por el jugador
     */
    public int getPuntos() {
        return puntos;
    }
    /**
     * Modifica los puntos obtenidos por el jugador
     * @param puntos nuevos puntos obtenidos
     */
    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
    /**
     * obtiene la palabra a adivinar 
     * @return devuelve la palabra a adivinar
     */
    public String getPalabra_a_adivinar() {
        return palabra_a_adivinar;
    }
    /**
     * modifica la palabra a adivinar
     * @param palabra_a_adivinar nueva palabra a adivinar
     */
    public void setPalabra_a_adivinar(String palabra_a_adivinar) {
        this.palabra_a_adivinar = palabra_a_adivinar;
    }
    /**
     * obtiene las letras que usuario ya ha acertado
     * @return devuelve las letras ya acertadas por el usuario
     */
    public String getPalabra_escrita() {
        return palabra_escrita;
    }
    /**
     * modifica las letras que el usuario ya ha acertado
     * @param palabra_escrita nuevas letras acertadas por el usuario
     */
    public void setPalabra_escrita(String palabra_escrita) {
        this.palabra_escrita = palabra_escrita;
    }
    /**
     * obtiene el nuemero de errores que el usuario a cometido
     * @return devuelve el numero de errores
     */
    public int getContador_errores() {
        return contador_errores;
    }
    /**
     * modifica el numero de errores del usuario
     * @param contador_errores nuevo numero de errores
     */
    public void setContador_errores(int contador_errores) {
        this.contador_errores = contador_errores;
    }
    /**
     * obtiene el numero de aciertos del usuario
     * @return devuelve el numero de aciertos
     */
    public int getContador_aciertos() {
        return contador_aciertos;
    }
    /**
     * modifica el numero de aciertos del usuario
     * @param contador_aciertos nuevo numero de aciertos del usuario
     */
    public void setContador_aciertos(int contador_aciertos) {
        this.contador_aciertos = contador_aciertos;
    }
    /**
     * obtiene el numero de minutos que el jugador lleva jugando
     * @return devuelve el numero de minutos jugados
     */
    public int getMinutos() {
        return minutos;
    }
    /**
     * modifica el numero de minutos jugados
     * @param minutos nuevo numero de minutos jugados
     */
    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }
    /**
     * obtiene el numero de segundos que el jugador lleva
     * @return devuelve el numero de segundos
     */
    public int getSegundos() {
        return segundos;
    }
    /**
     * modifica el numero de segundos jugados
     * @param segundos nuevo numero de segundos
     */
    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }
    /**
     * obtiene el numero de horas jugadas por el jugador
     * @return obtiene el numero de horas
     */
    public int getHoras() {
        return horas;
    }
    /**
     * modifica el numero de horas jugadas por el jugador
     * @param horas nuevo numero de horas
     */
    public void setHoras(int horas) {
        this.horas = horas;
    }
    
    
    
    
}
