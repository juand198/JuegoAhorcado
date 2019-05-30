
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.System.exit;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Alumno: Juan David Jiménez Cáceres
   C.F.G.S. Desarrollo de Aplicaciones Multiplataforma
   Módulo Profesional: Programación
   Curso escolar: 2018/2019
   Profesor: Francisco Jesús Delgado Almirón
   Instituto Tecnológico Poniente
 * @author juan
 */
public class Principal extends javax.swing.JFrame {

    /**
     * Creates new form Principal
     */
    public Principal() {
        initComponents();
        mensajeNombre();
        reloj = new Reloj (this.lTiempo);
        reloj.start();
    }
    private String palabra_a_adivinar ="";
    private String letra ="",nombre ="";
    private int puntos = 0;
    private int posicion=0;
    private int numero_palabras= 297;
    private int contador_errores = 0,contador_aciertos=0;
    private ArrayList<String>fotos = null;
    private ArrayList<Boolean>estado_botones = new ArrayList();
    private Reloj reloj = null;
    /**
     * Metodo que obtiene el nombre del primer panel
     */
    public void mensajeNombre()
    {    
        String r_icono = "Imagenes/36601.png"; 
        ImageIcon icono = new ImageIcon(getClass().getResource(r_icono));
        
        String respuesta = (String)JOptionPane.showInputDialog( 
                this,"", "Introduce tu nombre",JOptionPane.PLAIN_MESSAGE,icono, null, null);
        if ((respuesta != null) && (respuesta.length() > 0)) 
        {
            nombre = respuesta;
            lNombre.setText(nombre);
            abrirArchivo();            
        }
        else
        {
            lNombre.setText("Sin nombre");
            abrirArchivo();
        }
    }
    /**
     * Llama al metodo abrir archivo, para seleccionar una palabra nueva y habilita todos los botones de las letras
     */
    public void juegoNuevo()
    {
        abrirArchivo();
        this.bA.setEnabled(true);
        this.bB.setEnabled(true);
        this.bC.setEnabled(true);
        this.bD.setEnabled(true);
        this.bE.setEnabled(true);
        this.bF.setEnabled(true);
        this.bG.setEnabled(true);
        this.bH.setEnabled(true);
        this.bI.setEnabled(true);
        this.bJ.setEnabled(true);
        this.bK.setEnabled(true);
        this.bL.setEnabled(true);
        this.bM.setEnabled(true);
        this.bN.setEnabled(true);
        this.bÑ.setEnabled(true);
        this.bO.setEnabled(true);
        this.bP.setEnabled(true);
        this.bQ.setEnabled(true);
        this.bR.setEnabled(true);
        this.bS.setEnabled(true);
        this.bT.setEnabled(true);
        this.bU.setEnabled(true);
        this.bV.setEnabled(true);
        this.bW.setEnabled(true);
        this.bX.setEnabled(true);
        this.bY.setEnabled(true);
        this.bZ.setEnabled(true);
        puntos = 0;
        contador_aciertos=0;
        contador_errores = 0;
        cambiarFoto();
        this.lPuntuacion.setText("Puntuacion:" + puntos);
        this.tfResolver.setText("");        
    }
    /**
     * Guarda la partida con formato .dat adjuntando el nombre que se da en la primera pantalla al nombre del archivo 
     * y lo guarda en la ubicación que se desee y serializa la clase configuración para poder luego cargar el estado 
     * de la partida tal y como se dejo
     */
    public void guardarPartida()
    {
        Configuracion c1 = new Configuracion();
        c1.setContador_aciertos(contador_aciertos);
        c1.setContador_errores(contador_errores);
        c1.setEstadobotones(estado_botones);
        c1.setNombreusuario(nombre);
        c1.setPalabra_a_adivinar(palabra_a_adivinar);
        c1.setPalabra_escrita(this.lPalabraOculta.getText());
        c1.setPuntos(puntos);
        c1.setHoras(reloj.getHoras());
        c1.setMinutos(reloj.getMinutos());
        c1.setSegundos(reloj.getSegundos());
        FileOutputStream fos = null; 
        ObjectOutputStream salida = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("Partida_" + nombre + ".dat"));
        fileChooser.setDialogTitle("Selecciona un fichero");
        FileFilter filter = new FileNameExtensionFilter("Ficheros de partida", "dat"); 
        fileChooser.addChoosableFileFilter(filter);
        int resultado = fileChooser.showOpenDialog(null);
        String ruta = "";
        if (resultado == JFileChooser.APPROVE_OPTION) 
        {
            ruta = fileChooser.getSelectedFile().getAbsolutePath();
        }
        else
        {
            mensajeInformacion("Partida no guardada");
        }
        try 
        {
            String nombre_archivo = ruta;
            fos = new FileOutputStream(nombre_archivo); 
            salida = new ObjectOutputStream(fos); 
            salida.writeObject(c1);
            mensajeInformacion("Partida guardada correctamente");
        }
        catch (IOException e) 
        {
            mensajeError("Error", e.toString());
        }
        finally 
        {
            try 
            {
                if(fos != null) fos.close();
                if(salida != null) salida.close();
            }
            catch (IOException e) 
            {
                mensajeError("Error", e.toString());
            } 
        }
    }
    /**
     * Carga la partida con el estado de todos los botones,puntuacion, nombre y palabra tanto a buscar como las letras ya
     * adivinadas, gracias a que el metodo guardarPartida serializa la clase configuracion
     */
    public void cargarPartida()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("fichero.dat"));
        FileFilter filter = new FileNameExtensionFilter("Ficheros de texto", "dat"); 
        fileChooser.addChoosableFileFilter(filter);
        int resultado = fileChooser.showOpenDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) 
        {
            FileInputStream fis = null; 
            ObjectInputStream entrada = null; 
            try 
            {
                fis = new FileInputStream(fileChooser.getSelectedFile().getAbsolutePath()); 
                entrada = new ObjectInputStream(fis);
                Configuracion c1 = null;
                c1 = (Configuracion) entrada.readObject();
                juegoNuevo();
                this.lNombre.setText("Nombre: " + c1.getNombreusuario());
                this.lPuntuacion.setText("Puntuacion: " + String.valueOf(c1.getPuntos()));
                this.lPalabraOculta.setText(c1.getPalabra_escrita());
                palabra_a_adivinar = c1.getPalabra_a_adivinar();
                contador_aciertos = c1.getContador_aciertos();
                contador_errores = c1.getContador_errores();
                reloj.setMinutos(c1.getMinutos());
                reloj.setHoras(c1.getHoras());
                reloj.setSegundos(c1.getSegundos());
                estado_botones = c1.getEstadobotones();
                this.lNombre.setText(c1.getNombreusuario());
                cambiarFoto();
                this.bA.setEnabled(estado_botones.get(0));
                this.bB.setEnabled(estado_botones.get(1));
                this.bC.setEnabled(estado_botones.get(2));
                this.bD.setEnabled(estado_botones.get(3));
                this.bE.setEnabled(estado_botones.get(4));
                this.bF.setEnabled(estado_botones.get(5));
                this.bG.setEnabled(estado_botones.get(6));
                this.bH.setEnabled(estado_botones.get(7));
                this.bI.setEnabled(estado_botones.get(8));
                this.bJ.setEnabled(estado_botones.get(9));
                this.bK.setEnabled(estado_botones.get(10));
                this.bL.setEnabled(estado_botones.get(11));
                this.bM.setEnabled(estado_botones.get(12));
                this.bN.setEnabled(estado_botones.get(13));
                this.bÑ.setEnabled(estado_botones.get(14));
                this.bO.setEnabled(estado_botones.get(15));
                this.bP.setEnabled(estado_botones.get(16));
                this.bQ.setEnabled(estado_botones.get(17));
                this.bR.setEnabled(estado_botones.get(18));
                this.bS.setEnabled(estado_botones.get(19));
                this.bT.setEnabled(estado_botones.get(20));
                this.bU.setEnabled(estado_botones.get(21));
                this.bV.setEnabled(estado_botones.get(22));
                this.bW.setEnabled(estado_botones.get(23));
                this.bX.setEnabled(estado_botones.get(24));
                this.bY.setEnabled(estado_botones.get(25));
                this.bZ.setEnabled(estado_botones.get(26));
            }
            catch (IOException | ClassNotFoundException e) 
            {
                mensajeError("Error", e.toString());
            }
            finally 
            {
                try 
                {
                    if(fis != null) fis.close();
                    if(entrada != null) entrada.close();
                }
                catch (IOException e) 
                {
                    mensajeError("Error",e.toString());
                } 
            }
        }
    }
    /**
     * Abre el archivo donde estan todas las palabras y escoge una al azar.
     */
    public void abrirArchivo()
    {
        
        File archivo = null; 
        FileReader fr = null; 
        BufferedReader br = null;
        for (int i = 0; i < 27; i++) 
        {
            estado_botones.add(true);
        }
        try
        {
            archivo = new File ("palabras.txt"); 
            fr = new FileReader (archivo); 
            br = new BufferedReader(fr);
            Random aleatorio = new Random(); 
            int numero_aleatorio = aleatorio.nextInt(numero_palabras - 1 + 1) + 1;
            System.out.println(numero_aleatorio);
            palabra_a_adivinar = "";
            for (int i = 0; i < numero_aleatorio; i++) 
            {
                palabra_a_adivinar = br.readLine();
            }
            lPalabraOculta.setText("");
            int cont = 0;
            for (int i = 0; i < palabra_a_adivinar.length(); i++) 
            {
                lPalabraOculta.setText(lPalabraOculta.getText() + "_");
                cont++;
            }
            this.lNumeroLetras.setText("Tiene: " + cont + " letras");
            
        } catch (FileNotFoundException ex) {
            mensajeError("Erorr",ex.toString());
        } catch (IOException ex) {
            mensajeError("Error", ex.toString());
        }
        finally
        {
            try 
            {                
                fr.close();
            } catch (IOException ex) 
            {
                mensajeError("Error", ex.toString());
            }
        }
    }
    /**
     * Comprueba si la letra introducida está en la palabra a adivinar y en caso de estar, escribe la letra,
     * añade la puntuacion, suma uno al numero de aciertos, en caso de no estar suma uno al numero de errrores y llama
     * a la funcion cambiarFoto() para que cambie el muñeco ahorcado. cuando los aciertos son iguales al numero de letras que
     * contiene la palabra, se muestra el mensaje de has ganado y te deja elegir si echar otra partida o no, en caso de ser si
     * llama a la funcion nuevoJuego() y en caso de ser no, se cierra el programa
     */
    public void rellenarPalabra()
    {        
        if (palabra_a_adivinar.contains(letra))
        {            
            String palabra_aux = "";
            
            for (int i = 0; i < palabra_a_adivinar.length(); i++) 
            {
                if (String.valueOf(palabra_a_adivinar.charAt(i)).equals(letra))
                {
                    puntos += 5;
                    contador_aciertos++;
                    palabra_aux += letra;                   
                }
                else
                {
                    
                    if (!String.valueOf(this.lPalabraOculta.getText().charAt(i)).equals("_"))
                    {
                        palabra_aux += this.lPalabraOculta.getText().charAt(i);
                    }
                    else
                    {
                        palabra_aux += "_";
                    }
                }
            }
            lPalabraOculta.setText(palabra_aux);
            this.lPuntuacion.setText("Puntuacion:" + puntos);
            if (contador_aciertos==palabra_a_adivinar.length())
            {
                if (hacerPregunta("Has Ganado, ¿Otra partida?")==0)
                {
                    juegoNuevo();                    
                }
                else
                {
                    exit(0);
                }
            }
        }
        else   
        {            
            contador_errores++;            
        }
    }
    /**
     * Hace una pregunta con un mensaje y dos respuestas
     * @param mensaje mensaje que se representa en el panel
     * @return  devuelve la respuesta del usuario
     */
    public int hacerPregunta(String mensaje)
    {
        int respuesta = JOptionPane.showConfirmDialog( this, mensaje , "Ganaste",JOptionPane.YES_NO_OPTION);
        return respuesta;
    }
    /**
     * Acumular el estado de los botones en un ArrayList
     * @return devuelve un ArrayList con el estado de los botones para luego serializar.
     */
    public ArrayList deshabilitarBotones()
    {
        estado_botones.remove(posicion);
        estado_botones.add(posicion, false);
        return estado_botones;
    }
    /**
     * Muestra un mensaje de error con un mensaje y un titulo
     * @param titulo Titulo del panel
     * @param mensaje mensaje a mostrar en el panel
     */
    public void mensajeError(String titulo, String mensaje)
    {
        JOptionPane.showMessageDialog(this, mensaje , titulo,
        JOptionPane.ERROR_MESSAGE);
    }
    /**
     * Muestra un mensaje de informacion
     * @param mensaje mensaje de informacion para mostrar
     */
    public void mensajeInformacion(String mensaje)
    {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    /**
     * Cambia una foto en funcion del numero de error que el usuario lleve, es un ArrayList donde se escoge una posicion u otra
     * en funcion del numero de errores
     */
    public void cambiarFoto()
    {
        fotos = new ArrayList();
        fotos.add("Imagenes/im1.jpg");
        fotos.add("Imagenes/im2.jpg");
        fotos.add("Imagenes/im3.jpg");
        fotos.add("Imagenes/im4.jpg");
        fotos.add("Imagenes/im5.jpg");
        fotos.add("Imagenes/im6.jpg");
        ImageIcon icono  = null;
        URL iconURL = null;
        try
        {
            if (contador_errores == fotos.size())
            {
                if (hacerPregunta("Has Perdido, la palabra era " + palabra_a_adivinar + " , ¿Otra partida?")==0)
                {
                    juegoNuevo();                    
                }
                else
                {
                    exit(0);
                }
            }
            else
            {
                iconURL = getClass().getResource(fotos.get(contador_errores));        
                ImageIcon icon = new ImageIcon(iconURL);
                lFoto.setIcon(icon);
            }
        }
        catch (Exception ex)
        {
            mensajeError("Error", ex.toString());
        }        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jPanel1 = new javax.swing.JPanel();
        pFoto = new javax.swing.JPanel();
        lFoto = new javax.swing.JLabel();
        pFuncion = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lJuegoDelAhoracado = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        lNumeroLetras = new javax.swing.JLabel();
        lPalabraOculta = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        bResolver = new javax.swing.JButton();
        tfResolver = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        bA = new javax.swing.JButton();
        bB = new javax.swing.JButton();
        bC = new javax.swing.JButton();
        bD = new javax.swing.JButton();
        bE = new javax.swing.JButton();
        bF = new javax.swing.JButton();
        bG = new javax.swing.JButton();
        bH = new javax.swing.JButton();
        bI = new javax.swing.JButton();
        bJ = new javax.swing.JButton();
        bK = new javax.swing.JButton();
        bL = new javax.swing.JButton();
        bM = new javax.swing.JButton();
        bN = new javax.swing.JButton();
        bÑ = new javax.swing.JButton();
        bO = new javax.swing.JButton();
        bP = new javax.swing.JButton();
        bQ = new javax.swing.JButton();
        bR = new javax.swing.JButton();
        bS = new javax.swing.JButton();
        bT = new javax.swing.JButton();
        bU = new javax.swing.JButton();
        bV = new javax.swing.JButton();
        bW = new javax.swing.JButton();
        bX = new javax.swing.JButton();
        bY = new javax.swing.JButton();
        bZ = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lTiempo = new javax.swing.JLabel();
        lNombre = new javax.swing.JLabel();
        lPuntuacion = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jmJuego = new javax.swing.JMenu();
        jmNuevaPartida = new javax.swing.JMenuItem();
        jmGuardarPartida = new javax.swing.JMenuItem();
        jmCargarPartida = new javax.swing.JMenuItem();
        jmSobreElJuego = new javax.swing.JMenu();
        jmInformacion = new javax.swing.JMenuItem();

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Juego del ahorcado");

        jPanel1.setLayout(new java.awt.GridLayout(1, 2));

        pFoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pFoto.setLayout(new java.awt.BorderLayout());

        lFoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/im1.jpg"))); // NOI18N
        lFoto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pFoto.add(lFoto, java.awt.BorderLayout.CENTER);

        jPanel1.add(pFoto);

        pFuncion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pFuncion.setLayout(new java.awt.GridLayout(2, 0));

        jPanel3.setLayout(new java.awt.GridLayout(3, 0));

        lJuegoDelAhoracado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lJuegoDelAhoracado.setText("JUEGO DEL AHORCADO");
        jPanel3.add(lJuegoDelAhoracado);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Palabra Secreta"));
        jPanel7.setLayout(new java.awt.GridLayout(2, 0));

        lNumeroLetras.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel7.add(lNumeroLetras);

        lPalabraOculta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lPalabraOculta.setText("------");
        jPanel7.add(lPalabraOculta);

        jPanel3.add(jPanel7);

        jPanel6.setLayout(new java.awt.BorderLayout());

        bResolver.setText("Resolver palabra");
        bResolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bResolverActionPerformed(evt);
            }
        });
        jPanel6.add(bResolver, java.awt.BorderLayout.SOUTH);
        jPanel6.add(tfResolver, java.awt.BorderLayout.CENTER);

        jLabel5.setText("Resolver:");
        jPanel6.add(jLabel5, java.awt.BorderLayout.WEST);

        jPanel3.add(jPanel6);

        pFuncion.add(jPanel3);

        jPanel5.setLayout(new java.awt.GridLayout(6, 5));

        bA.setText("A");
        bA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAActionPerformed(evt);
            }
        });
        jPanel5.add(bA);

        bB.setText("B");
        bB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBActionPerformed(evt);
            }
        });
        jPanel5.add(bB);

        bC.setText("C");
        bC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCActionPerformed(evt);
            }
        });
        jPanel5.add(bC);

        bD.setText("D");
        bD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDActionPerformed(evt);
            }
        });
        jPanel5.add(bD);

        bE.setText("E");
        bE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEActionPerformed(evt);
            }
        });
        jPanel5.add(bE);

        bF.setText("F");
        bF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bFActionPerformed(evt);
            }
        });
        jPanel5.add(bF);

        bG.setText("G");
        bG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bGActionPerformed(evt);
            }
        });
        jPanel5.add(bG);

        bH.setText("H");
        bH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHActionPerformed(evt);
            }
        });
        jPanel5.add(bH);

        bI.setText("I");
        bI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bIActionPerformed(evt);
            }
        });
        jPanel5.add(bI);

        bJ.setText("J");
        bJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bJActionPerformed(evt);
            }
        });
        jPanel5.add(bJ);

        bK.setText("K");
        bK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bKActionPerformed(evt);
            }
        });
        jPanel5.add(bK);

        bL.setText("L");
        bL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bLActionPerformed(evt);
            }
        });
        jPanel5.add(bL);

        bM.setText("M");
        bM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bMActionPerformed(evt);
            }
        });
        jPanel5.add(bM);

        bN.setText("N");
        bN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bNActionPerformed(evt);
            }
        });
        jPanel5.add(bN);

        bÑ.setText("Ñ");
        bÑ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bÑActionPerformed(evt);
            }
        });
        jPanel5.add(bÑ);

        bO.setText("O");
        bO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bOActionPerformed(evt);
            }
        });
        jPanel5.add(bO);

        bP.setText("P");
        bP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPActionPerformed(evt);
            }
        });
        jPanel5.add(bP);

        bQ.setText("Q");
        bQ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bQActionPerformed(evt);
            }
        });
        jPanel5.add(bQ);

        bR.setText("R");
        bR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRActionPerformed(evt);
            }
        });
        jPanel5.add(bR);

        bS.setText("S");
        bS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSActionPerformed(evt);
            }
        });
        jPanel5.add(bS);

        bT.setText("T");
        bT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTActionPerformed(evt);
            }
        });
        jPanel5.add(bT);

        bU.setText("U");
        bU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUActionPerformed(evt);
            }
        });
        jPanel5.add(bU);

        bV.setText("V");
        bV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bVActionPerformed(evt);
            }
        });
        jPanel5.add(bV);

        bW.setText("W");
        bW.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bWActionPerformed(evt);
            }
        });
        jPanel5.add(bW);

        bX.setText("X");
        bX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bXActionPerformed(evt);
            }
        });
        jPanel5.add(bX);

        bY.setText("Y");
        bY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bYActionPerformed(evt);
            }
        });
        jPanel5.add(bY);

        bZ.setText("Z");
        bZ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bZActionPerformed(evt);
            }
        });
        jPanel5.add(bZ);

        pFuncion.add(jPanel5);

        jPanel1.add(pFuncion);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.GridLayout(1, 3));

        lTiempo.setText("Tiempo jugado:");
        jPanel2.add(lTiempo);

        lNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lNombre.setText("Nombre");
        jPanel2.add(lNombre);

        lPuntuacion.setText("Puntuacion:");
        jPanel2.add(lPuntuacion);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        jmJuego.setText("Juego");

        jmNuevaPartida.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jmNuevaPartida.setText("Nueva partida");
        jmNuevaPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmNuevaPartidaActionPerformed(evt);
            }
        });
        jmJuego.add(jmNuevaPartida);

        jmGuardarPartida.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jmGuardarPartida.setText("Guardar partida");
        jmGuardarPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmGuardarPartidaActionPerformed(evt);
            }
        });
        jmJuego.add(jmGuardarPartida);

        jmCargarPartida.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jmCargarPartida.setText("Cargar partida");
        jmCargarPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmCargarPartidaActionPerformed(evt);
            }
        });
        jmJuego.add(jmCargarPartida);

        jMenuBar1.add(jmJuego);

        jmSobreElJuego.setText("Sobre el juego");

        jmInformacion.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jmInformacion.setText("Información");
        jmInformacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmInformacionActionPerformed(evt);
            }
        });
        jmSobreElJuego.add(jmInformacion);

        jMenuBar1.add(jmSobreElJuego);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAActionPerformed
        // TODO add your handling code here:
        letra ="A";
        this.bA.setEnabled(false);
        rellenarPalabra(); 
        posicion = 0;
        deshabilitarBotones();        
        cambiarFoto();
    }//GEN-LAST:event_bAActionPerformed

    private void bBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBActionPerformed
        // TODO add your handling code here:
        letra = "B";
        rellenarPalabra(); 
        this.bB.setEnabled(false);               
        posicion = 1;        
        deshabilitarBotones();
        cambiarFoto();
    }//GEN-LAST:event_bBActionPerformed

    private void bCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCActionPerformed
        // TODO add your handling code here:
        letra = "C";
        this.bC.setEnabled(false);
        rellenarPalabra();        
        posicion = 2; 
        deshabilitarBotones();
        cambiarFoto();
    }//GEN-LAST:event_bCActionPerformed

    private void bDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDActionPerformed
        // TODO add your handling code here:
        letra = "D";
        this.bD.setEnabled(false);
        rellenarPalabra(); 
        posicion = 3;
        deshabilitarBotones();
        cambiarFoto();
    }//GEN-LAST:event_bDActionPerformed

    private void bEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEActionPerformed
        // TODO add your handling code here:
        letra = "E";
        this.bE.setEnabled(false);
        rellenarPalabra();        
        posicion = 4;
        deshabilitarBotones();
        cambiarFoto();
    }//GEN-LAST:event_bEActionPerformed

    private void bFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bFActionPerformed
        // TODO add your handling code here:
        letra = "F";
        this.bF.setEnabled(false);
        rellenarPalabra();        
        posicion = 5;
        deshabilitarBotones();
        cambiarFoto();
    }//GEN-LAST:event_bFActionPerformed

    private void bGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bGActionPerformed
        // TODO add your handling code here:
        letra = "G";
        this.bG.setEnabled(false);
        rellenarPalabra();        
        posicion = 6;
        deshabilitarBotones();
        cambiarFoto();
    }//GEN-LAST:event_bGActionPerformed

    private void bHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHActionPerformed
        // TODO add your handling code here:
        letra = "H";
        this.bH.setEnabled(false);
        rellenarPalabra();        
        posicion = 7;
        deshabilitarBotones();
        cambiarFoto();
    }//GEN-LAST:event_bHActionPerformed

    private void bIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bIActionPerformed
        // TODO add your handling code here:
        letra = "I";
        this.bI.setEnabled(false);
        rellenarPalabra();        
        posicion = 8;
        deshabilitarBotones();
        cambiarFoto();
    }//GEN-LAST:event_bIActionPerformed

    private void bJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bJActionPerformed
        // TODO add your handling code here:
        letra = "J";
        this.bJ.setEnabled(false);
        rellenarPalabra();        
        posicion = 9;
        deshabilitarBotones();
        cambiarFoto();
    }//GEN-LAST:event_bJActionPerformed

    private void bKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bKActionPerformed
        // TODO add your handling code here:
        letra = "K";
        this.bK.setEnabled(false);
        rellenarPalabra();        
        posicion = 10;
        deshabilitarBotones();
        cambiarFoto();
    }//GEN-LAST:event_bKActionPerformed

    private void bLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bLActionPerformed
        // TODO add your handling code here:
        letra = "L";
        this.bL.setEnabled(false);
        rellenarPalabra();        
        posicion = 11;
        deshabilitarBotones();
        cambiarFoto();
    }//GEN-LAST:event_bLActionPerformed

    private void bMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bMActionPerformed
        // TODO add your handling code here:
        letra = "M";
        this.bM.setEnabled(false);
        rellenarPalabra();        
        posicion = 12;
        deshabilitarBotones();
        cambiarFoto();
    }//GEN-LAST:event_bMActionPerformed

    private void bNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bNActionPerformed
        // TODO add your handling code here:
        letra = "N";
        this.bN.setEnabled(false);
        rellenarPalabra();        
        posicion = 13;
        deshabilitarBotones();
        cambiarFoto();
    }//GEN-LAST:event_bNActionPerformed

    private void bÑActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bÑActionPerformed
        // TODO add your handling code here:
        letra = "Ñ";
        this.bÑ.setEnabled(false);
        rellenarPalabra();        
        posicion = 14;
        deshabilitarBotones();
        cambiarFoto();
    }//GEN-LAST:event_bÑActionPerformed

    private void bOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bOActionPerformed
        // TODO add your handling code here:
        letra = "O";
        this.bO.setEnabled(false);
        rellenarPalabra();        
        posicion = 15;
        deshabilitarBotones();
        cambiarFoto();
    }//GEN-LAST:event_bOActionPerformed

    private void bPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPActionPerformed
        // TODO add your handling code here:
        letra = "P";
        this.bP.setEnabled(false);
        rellenarPalabra();        
        posicion = 16;
        deshabilitarBotones();
        cambiarFoto();
    }//GEN-LAST:event_bPActionPerformed

    private void bQActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bQActionPerformed
        // TODO add your handling code here:
        letra = "Q";
        this.bQ.setEnabled(false);
        rellenarPalabra();        
        posicion = 17;
        deshabilitarBotones();
        cambiarFoto();
    }//GEN-LAST:event_bQActionPerformed

    private void bRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRActionPerformed
        // TODO add your handling code here:
        letra = "R";
        this.bR.setEnabled(false);
        rellenarPalabra();        
        posicion = 18;
        deshabilitarBotones();
        cambiarFoto();
    }//GEN-LAST:event_bRActionPerformed

    private void bSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSActionPerformed
        // TODO add your handling code here:
        letra = "S";
        this.bS.setEnabled(false);
        rellenarPalabra();        
        posicion = 19;
        deshabilitarBotones();
        cambiarFoto();
    }//GEN-LAST:event_bSActionPerformed

    private void bTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTActionPerformed
        // TODO add your handling code here:
        letra = "T";
        this.bT.setEnabled(false);
        rellenarPalabra();        
        posicion = 20;
        deshabilitarBotones();
        cambiarFoto();
    }//GEN-LAST:event_bTActionPerformed

    private void bUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUActionPerformed
        // TODO add your handling code here:
        letra = "U";
        this.bU.setEnabled(false);
        rellenarPalabra();        
        posicion = 21;
        deshabilitarBotones();
        cambiarFoto();
    }//GEN-LAST:event_bUActionPerformed

    private void bVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bVActionPerformed
        // TODO add your handling code here:
        letra = "V";
        this.bV.setEnabled(false);
        rellenarPalabra();        
        posicion = 22;
        deshabilitarBotones();
        cambiarFoto();
    }//GEN-LAST:event_bVActionPerformed

    private void bWActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bWActionPerformed
        // TODO add your handling code here:
        letra = "W";
        this.bW.setEnabled(false);
        rellenarPalabra();        
        posicion = 23;
        deshabilitarBotones();
        cambiarFoto();
    }//GEN-LAST:event_bWActionPerformed

    private void bXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bXActionPerformed
        // TODO add your handling code here:
        letra = "X";
        this.bX.setEnabled(false);
        rellenarPalabra();        
        posicion = 24;
        deshabilitarBotones();
        cambiarFoto();
    }//GEN-LAST:event_bXActionPerformed

    private void bYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bYActionPerformed
        // TODO add your handling code here:
        letra = "Y";
        this.bY.setEnabled(false);
        rellenarPalabra();        
        posicion = 25;
        deshabilitarBotones();
        cambiarFoto();
    }//GEN-LAST:event_bYActionPerformed

    private void bZActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bZActionPerformed
        // TODO add your handling code here:
        letra = "Z";
        this.bZ.setEnabled(false);
        rellenarPalabra();        
        posicion = 26;
        deshabilitarBotones();
        cambiarFoto();
    }//GEN-LAST:event_bZActionPerformed

    private void bResolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bResolverActionPerformed
        // TODO add your handling code here:
        if (palabra_a_adivinar.equals(this.tfResolver.getText().toUpperCase()))
        {
            for (int i = 0; i < palabra_a_adivinar.length(); i++) 
            {
                puntos += 10;
            }
            this.lPuntuacion.setText("Puntuacion: " + puntos);
            if (hacerPregunta("Has ganado, has adivinado la palabra de golpe, tienes " + puntos + " puntos , ¿Otra partida?")==0)
            {
                juegoNuevo();
            }
            else
            {
                exit(0);
            }
        }
        else
        {
            contador_errores++;
            this.tfResolver.setText("");
        }
        
    }//GEN-LAST:event_bResolverActionPerformed

    private void jmInformacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmInformacionActionPerformed
        // TODO add your handling code here:
        mensajeInformacion("<html><head><body><b>Autor:</b> Juan David Jiménez Cáceres"
                + "<br><b>Curso:</b> 2018/2019</br>"
                + "<br><b>Reto:</b> Reto número 2 </br>"
                + "<br><b>INSTITUTO TECNOLOGICO PONIENTE</b></br>"
                + "<br><img src=http://www.ponienteformacion.com/portal/images/logo_ITP.png></br></body></head></html>");
    }//GEN-LAST:event_jmInformacionActionPerformed

    private void jmNuevaPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmNuevaPartidaActionPerformed
        // TODO add your handling code here:
        juegoNuevo();
    }//GEN-LAST:event_jmNuevaPartidaActionPerformed

    private void jmGuardarPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmGuardarPartidaActionPerformed
        // TODO add your handling code here:
        guardarPartida();
    }//GEN-LAST:event_jmGuardarPartidaActionPerformed

    private void jmCargarPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmCargarPartidaActionPerformed
        // TODO add your handling code here:
        cargarPartida();
    }//GEN-LAST:event_jmCargarPartidaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bA;
    private javax.swing.JButton bB;
    private javax.swing.JButton bC;
    private javax.swing.JButton bD;
    private javax.swing.JButton bE;
    private javax.swing.JButton bF;
    private javax.swing.JButton bG;
    private javax.swing.JButton bH;
    private javax.swing.JButton bI;
    private javax.swing.JButton bJ;
    private javax.swing.JButton bK;
    private javax.swing.JButton bL;
    private javax.swing.JButton bM;
    private javax.swing.JButton bN;
    private javax.swing.JButton bO;
    private javax.swing.JButton bP;
    private javax.swing.JButton bQ;
    private javax.swing.JButton bR;
    private javax.swing.JButton bResolver;
    private javax.swing.JButton bS;
    private javax.swing.JButton bT;
    private javax.swing.JButton bU;
    private javax.swing.JButton bV;
    private javax.swing.JButton bW;
    private javax.swing.JButton bX;
    private javax.swing.JButton bY;
    private javax.swing.JButton bZ;
    private javax.swing.JButton bÑ;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JMenuItem jmCargarPartida;
    private javax.swing.JMenuItem jmGuardarPartida;
    private javax.swing.JMenuItem jmInformacion;
    private javax.swing.JMenu jmJuego;
    private javax.swing.JMenuItem jmNuevaPartida;
    private javax.swing.JMenu jmSobreElJuego;
    private javax.swing.JLabel lFoto;
    private javax.swing.JLabel lJuegoDelAhoracado;
    private javax.swing.JLabel lNombre;
    private javax.swing.JLabel lNumeroLetras;
    private javax.swing.JLabel lPalabraOculta;
    private javax.swing.JLabel lPuntuacion;
    private javax.swing.JLabel lTiempo;
    private javax.swing.JPanel pFoto;
    private javax.swing.JPanel pFuncion;
    private javax.swing.JTextField tfResolver;
    // End of variables declaration//GEN-END:variables
}
