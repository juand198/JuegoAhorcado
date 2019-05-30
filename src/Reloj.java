
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Clase que representa el reloj
 * @author juan
 */
public class Reloj extends Thread
{
    private int horas, minutos, segundos;
    private JLabel labelm = null;
    /**
     * contructor por parametros
     * @param labelm label que representa el reloj en el principal
     */
    public Reloj(JLabel labelm) 
    {
        this.horas = 0;
        this.minutos = 0;
        this.segundos = 0;
        this.labelm = labelm;
    }  
    /**
     * obtiene el numero de horas jugadas
     * @return devuelve el numero de horas
     */
    public int getHoras() {
        return horas;
    }
    /**
     * modifica el numero de horas
     * @param horas nuevo valor para las horas
     */
    public void setHoras(int horas) {
        this.horas = horas;
    }
    /**
     * obtiene el numero de minutos jugados por el jugador
     * @return devuelve el numero de minutos
     */
    public int getMinutos() {
        return minutos;
    }
    /**
     * modifica el numero de minutos jugados
     * @param minutos nuevo valor para minutos
     */
    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }
    /**
     * obtiene el numero de segundos jugados por el jugador
     * @return devuelve el numero de segundos
     */
    public int getSegundos() {
        return segundos;
    }
    /**
     * modifica el numero de segundos jugados por el jugador
     * @param segundos nuevo valor para los segundos
     */
    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }
    
    /**
     * Modifica los minutos, segundos y horas en funcion del tiempo transcurrido, con 1s de sleep para el hilo
     */
    @Override
    public void run() 
    {
        while (true)
        {
            String shoras = "";
            String sminutos = "";
            String ssegundos = "";
            try { 
                segundos++;
                if  (segundos < 10)
                    ssegundos = "0";
                if (horas < 10)
                    shoras = "0";
                if (minutos < 10)
                    sminutos = "0";
                if (segundos == 60)
                {
                    segundos = 0;
                    minutos++;
                    if (minutos == 60)
                    {
                        minutos = 0;
                        horas++;
                    }
                }
                this.labelm.setText("Tiempo jugado: "+ shoras + this.horas + ":" + sminutos + this.minutos + ":" + ssegundos + this.segundos) ;
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Reloj.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    
    
}
