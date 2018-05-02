/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

/**
 *
 * @author Andrés Movilla
 * MASTER FILE
 */
public class Buscaminas extends JFrame implements Runnable {

    /**
     * Icono de mina.
     */
    ImageIcon imagenMina;

    
    /**
     * Icono de mina.
     */
    ImageIcon imagenBandera;

    
    /*
	PUNTOS: PARTE A
    */
    
    
    /**
     * Tamaño de una casilla.
     * 
     * ###
     * PUNTO 1:
     * Declare una variable entera 'tamanoCasilla' e inicialicela con un valor de 55.
     */

    
    /**
     * Espacio entre casillas.
     * 
     * ###
     * PUNTO 2:
     * Declare una variable entera 'espacioCasilla' e inicialicela con un valor de 5.
     */
    
    
    /**
     * Matriz de JLabel que corresponden al tablero.
     * 
     * ###
     * PUNTO 3:
     * Declare una matriz de tipo 'JLabel' con nombre 'textoTablero'.
     */

    
    /**
     * Matriz de int que correponden a la informacion del tablero.
     * 
     * ###
     * PUNTO 4:
     * Declare una matriz de Enteros con nombre 'minasTablero'.
     */

    
    /**
     * Numero de casillas en cada fila y columna del tablero.
     * 
     * ###
     * PUNTO 5:
     * Declare una variable entera 'tamanoTablero' e inicialicela con un valor de 10.
     */

    
    /**
     * Matriz de int que corresponden a los botones presionados.
     * 
     * ###
     * PUNTO 6:
     * Declare una matriz de Enteros con nombre 'botonesPresionados'.
     */

    
    /**
     * Numero de minas en el tablero.
     * 
     * ###
     * PUNTO 7:
     * Declare una variable entera 'totalMinas' e inicialicela con un valor de 0.
     */

    
    /**
     * Numero de casillas seleccionadas.
     * 
     * ###
     * PUNTO 8:
     * Declare una variable entera 'casillasSeleccionadas' e inicialicela con un valor de 0.
     */

    
    /**
     * Banderas plantadas.
     * 
     * ###
     * PUNTO 9:
     * Declare una variable entera 'banderasPlantadas' e inicialicela con un valor de 0.
     */

    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	new Thread(new Buscaminas()).start();
    }

    /**
     * Crea la ventana del juego.
     */
    public Buscaminas() {
	int size = tamanoTablero * (espacioCasilla + tamanoCasilla);
	setSize(size + 12, size + 36);
	setLocationRelativeTo(null);
	setLayout(null);
	setTitle("Buscaminas ");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setResizable(false);

	imagenMina = cargarImagen("mina.png");
	imagenBandera = cargarImagen("bandera.png");

	addMouseListener(new java.awt.event.MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		int[] p = buscarBoton(e.getX(), e.getY());
		if (p != null) {
		    if (javax.swing.SwingUtilities.isRightMouseButton(e)) {
			casillaBandera(p[0], p[1]);
		    } else if (javax.swing.SwingUtilities.isLeftMouseButton(e)) {
			casillaSeleccionar(p[0], p[1]);
		    }
		}
	    }
	});
	
	crearCasillas();
	agregarCasillas();
	crearMinas();

	setVisible(true);
    }

    /**
     * Carga imagen.
     *
     * @param path Direccion del archivo imagen.
     * @return Imagen cargada de la direccion.
     */
    ImageIcon cargarImagen(String path) {
	try {
	    BufferedImage img1 = javax.imageio.ImageIO.read(new java.io.File(path));
	    BufferedImage img2 = new BufferedImage(tamanoCasilla, tamanoCasilla, BufferedImage.TYPE_INT_ARGB);
	    java.awt.Graphics g = img2.getGraphics();
	    g.drawImage(img1, 0, 0, tamanoCasilla, tamanoCasilla, null);
	    g.dispose();

	    return new ImageIcon(img2);
	} catch (Exception e) {
	    return null;
	}
    }

    /**
     * Crea un color con la informacion dada.
     *
     * @param hue Tinte del color a crear.
     * @param saturation Saturacion del color a crear.
     * @param brightness Brillo del color a crear.
     * @return Color creado.
     */
    public Color crearColor(double hue, double saturation, double brightness) {
	return Color.getHSBColor((float) hue / 360f, (float) saturation / 100f, (float) brightness / 100f);
    }

    /**
     * Indica si una posicion se encuentra dentro del tablero
     *
     * @param x Posicion X de la casilla indicada.
     * @param y Posicion Y de la casilla indicada.
     * @return Retorna true si es una posicion valida, de lo contrario, falso.
     */
    boolean esPosicionValida(int x, int y) {
	return (x >= 0 && x < tamanoTablero && y >= 0 && y < tamanoTablero);
    }

    /**
     * Genera un numero aleatorio del 1 al 10.
     *
     * @return Numero generado.
     */
    int numeroAleatorio() {
	return new java.util.Random().nextInt(10) + 1;
    }

    /**
     * Muestra un aviso de perdida y reinicia el juego.
     */
    void mostrarVentanaPerdedor() {
	JOptionPane.showMessageDialog(this, "Perdió!", "Fin del juego", JOptionPane.PLAIN_MESSAGE);
	reiniciar();
    }

    /**
     * Muestra un aviso de ganada y reinicia el juego.
     */
    void mostrarVentanaGanador() {
	JOptionPane.showMessageDialog(this, "Ganó!", "Fin del juego", JOptionPane.PLAIN_MESSAGE);
	reiniciar();
    }

    /**
     * Muestra el numero n en la casilla indicada.
     *
     * @param x Posicion X de casilla indicada.
     * @param y Posicion Y de casilla indicada.
     * @param n Numero a mostrar.
     */
    void muestraMinasAlrededor(int x, int y, int n) {
	textoTablero[x][y].setText("" + n);
    }

    /**
     *
     */
    @Override
    public void run() {
	while (true) {

	    String t = this.getTitle();
	    String t2 = t.substring(1);
	    setTitle(t2 + t.substring(0, 1));

	    try {
		Thread.sleep(100);
	    } catch (Exception e) {
	    }
	}
    }

    /**
     * Pinta la casilla indicada de color rojo.
     *
     * @param x Posicion X de casilla indicada.
     * @param y Posicion Y de casilla indicada.
     */
    void pintarRojo(int x, int y) {
	textoTablero[x][y].setBackground(crearColor(359.3, 70.6, 100));
    }

    /**
     * Pinta la casilla indicada de color azul.
     *
     * @param x Posicion X de casilla indicada.
     * @param y Posicion Y de casilla indicada.
     */
    void pintarAzul(int x, int y) {
	textoTablero[x][y].setBackground(crearColor(209, 24.3, 100));
    }

    /**
     * Pinta la casilla indicada de color verde.
     *
     * @param x Posicion X de casilla indicada.
     * @param y Posicion Y de casilla indicada.
     */
    void pintarVerde(int x, int y) {
	textoTablero[x][y].setBackground(crearColor(90, 43.9, 100));
    }

    /**
     * Pinta la casilla indicada de color predeterminado.
     *
     * @param x Posicion X de casilla indicada.
     * @param y Posicion Y de casilla indicada.
     */
    void pintarPlano(int x, int y) {
	textoTablero[x][y].setBackground(crearColor(210, 43.9, 100));

    }

    /**
     * Muestra una mina en la casilla indicada.
     *
     * @param x Posicion X de casilla indicada.
     * @param y Posicion Y de casilla indicada.
     */
    void mostrarMina(int x, int y) {
	textoTablero[x][y].setIcon(imagenMina);
    }

    /**
     * Muestra una bandera en la casilla indicada.
     *
     * @param x Posicion X de la casilla indicada.
     * @param y Posicion Y de la casilla indicada.
     */
    void plantarBandera(int x, int y) {
	textoTablero[x][y].setIcon(imagenBandera);
    }

    /**
     * Quita la imagen de la bandera de la casilla indicada.
     *
     * @param x Posicion X de la casilla indicada.
     * @param y Posicion Y de la casilla indicada.
     */
    void quitarBandera(int x, int y) {
	textoTablero[x][y].setIcon(null);
    }

    /**
     * Encontrar boton.
     *
     * @param x Posicion X para buscar boton.
     * @param y Posicion Y para buscar boton.
     * @return La direccion i y direccion j del boton.
     */
    int[] buscarBoton(int x, int y) {

	int rx = 0, ry = 0;
	boolean found = false;
	for (int i = 0; i < tamanoTablero; i++) {
	    for (int j = 0; j < tamanoTablero; j++) {
		int dx = x - textoTablero[i][j].getX() - getInsets().left;
		int dy = y - textoTablero[i][j].getY() - getInsets().top;
		if (dx >= 0 && dy >= 0 && dx <= tamanoCasilla && dy <= tamanoCasilla) {
		    rx = i;
		    ry = j;
		    found = true;

		}
	    }
	}

	return found ? new int[]{rx, ry} : null;
    }
    
    /**
     * Limpiar casilla indicada.
     *
     * @param x Posicion X de la casilla a limpiar.
     * @param y Posicion Y de la casilla a limpiar.
     */
    void limpiarCasilla(int x, int y) {
	textoTablero[x][y].setText("");
	textoTablero[x][y].setIcon(null);
    }

    /**
     * Agrega los JButton y JLabel previamente creados a la ventana.
     */
    void agregarCasillas() {
	for (int i = 0; i < tamanoTablero; i++) {
	    for (int j = 0; j < tamanoTablero; j++) {
		textoTablero[i][j].setLocation(
			((j + 1) * espacioCasilla) + (j * tamanoCasilla),
			((i + 1) * espacioCasilla) + (i * tamanoCasilla)
		);
		textoTablero[i][j].setSize(tamanoCasilla, tamanoCasilla);
		textoTablero[i][j].setHorizontalAlignment(SwingConstants.CENTER);
		textoTablero[i][j].setOpaque(true);
		pintarPlano(i, j);
		add(textoTablero[i][j]);
	    }
	}
    }

    /*
	PUNTOS: PARTE B
     */
    
    /**
     * Reinicia la casilla indicada a su estado inicial.
     *
     * @param x Posicion X de la casilla a reiniciar.
     * @param y Posicion Y de la casilla a reiniciar.
     * 
     * ###
     * PUNTO 1:
     * Declare un metodo 'reiniciarCasilla' que haga lo siguiente:
     *	- Acepte como parametro la direccion X y Y de la casilla a reiniciar.
     *	- Pintar la direccion dada por parametro de color "plano".
     *	- Limpie la casilla con la direccion dada por parametro.
     *	- Cambie el valor de botonesPresionados en la direccion dada por
     *	    parametro a su valor inicial.
     */

    
    
    
    /**
     * Reinicia todas las casillas.
     * 
     * ###
     * PUNTO 2:
     * Declare un metodo 'reiniciarCasillas' que haga lo siguiente:
     *	- Recorra las casillas del tablero y las reinicie.
     */

    
    
    
    /**
     * Crea las minas en el tablero.
     * 
     * ###
     * PUNTO 3:
     * Declare un metodo 'crearMinas' que haga lo siguiente:
     *	- Inicialice la matriz minasTablero con tamaño cuadrado de tamanoTablero.
     *	- Recorra las casillas del tablero y volver al 30% una mina (1), de lo
     *	    contrario, nada(0).
     *	- Cuente cuantas minas hay en el tablero.
     */
    
    

    
    /**
     * Reinicia el juego.
     * 
     * ###
     * PUNTO 4:
     * Declare un metodo 'reiniciar' que haga lo siguiente:
     *	- Cambie el valor de totalMinas, casillasSeleccionadas, y 
     *	    banderasPlantas a su valor inicial.
     *	- Cree las minas.
     *	- Reinicie las casillas.
     */
    
    
    
    
    /**
     * Revisa si el usuario ganó.
     * 
     * ###
     * PUNTO 5:
     * Declare un metodo 'revisarGanar' que haga lo siguiente:
     *	- Revise si el usuario ganó tras marcar todas las casillas con una
     *	    seleccion o una bandera.
     *	- Mostrar el tablero y la ventana de ganador.
     */

    
    
    
    /**
     * Muestra todas las minas en el tablero.
     * 
     * ###
     * PUNTO 6:
     * Declare un metodo 'mostrarTablero' que haga lo siguiente:
     *	- Recorra el tablero y muestra la mina de la casilla.
     *	- Ademas, que pinte la casilla de verde si la casilla tenia una bandera,
     *	    de lo contrario, la pinta de rojo.
     */
    
    
    
    


    /**
     * Llama todos los metodos relacionados con poner una bandera en una casilla.
     *
     * @param x Posicion X de la casilla presionada.
     * @param y Posicion Y de la casilla presionada.
     * 
     * ###
     * PUNTO 7:
     * Declare un metodo 'casillaBandera' que haga lo siguiente:
     *	- Acepte como parametro la direccion X y Y de la casilla para poner
     *	    una bandera.
     *	- Revise que la posicion dada por parametro sea valida.
     *	- No permita poner mas banderas que minas en el tablero.
     *	- Plante una bandera solo si la casilla no está seleccionada, y revise si
     *	    el usuario ganó.
     *	- Quite la bandera si ya se encuentra una bandera plantada.
     */

    
    
    
    
    /**
     * Crea la matriz de JLabel.
     * 
     * ###
     * PUNTO 8:
     * Declare un metodo 'crearCasillas' que haga lo siguiente:
     *	- Inicialice la matriz botonesPresionados con tamaño cuadrado de tamanoTablero.
     *	- Inicialice la matriz textoTablero con tamaño cuadrado de tamanoTablero.
     *	- Recorra el tablero y inicialice cada valor de botonesPresionados como
     *	    cero, y cada valor de textoTablero como 'new JLabel()'.
     */


    

    /**
     * Cuenta las minas alrededor de la casilla indicada.
     *
     * @param x Posicion X de la casilla indicada.
     * @param y Posicion Y de la casilla indicada.
     * @return Numero de minas alrededor de la casilla indicada.
     * 
     * ###
     * PUNTO 9:
     * Declare un metodo 'minasAlrededor' que haga lo siguiente:
     *	- Acepte como parametro la direccion X y Y de la casilla a contar.
     *	- Cuente cuantas minas se encuentran alrededor de la direccion
     *	    dada por parametro.
     *	- Retorne el valor.
     */
    
    
    
    

    /**
     * Llama todos los metodos relacionados con seleccionar una casilla.
     *
     * @param x Posicion X de la casilla presionada.
     * @param y Posicion Y de la casilla presionada.
     * 
     * ###
     * PUNTO 10a:
     * Declare un metodo 'casillaSeleccionar' que haga lo siguiente:
     *	- Acepte como parametro la direccion X y Y de la casilla a reiniciar.
     *	- Revise que la posicion sea valida, y la casilla no haya sido 
     *	    seleccionada ni tenga una bandera.
     *	- Si la casilla seleccionada es una mina, se pinta la casilla de rojo,
     *	    se muestra la mina, y se muestra la ventana de perdedor.
     *	- Si no es una mina, se pinta de azul.
     *	- Si no hay minas alrededor, se seleccionan las casillas alrededor.
     *	- Si hay casillas alrededor, se muestran las minas alrededor.
     *	- Finalmente, se revisa si el jugador ganó.
     */
    
    
    

    /**
     * Selecciona todas las casillas alrededor de la direccion indicada.
     *
     * @param x Posicion X de la casilla indicada.
     * @param y Posicion Y de la casilla indicada.
     * 
     * ###
     * PUNTO 10b:
     * Declare un metodo 'casillaSeleccionarAlrededor' que haga lo siguiente:
     *	- Acepte como parametro la direccion X y Y de la casilla a reiniciar.
     *	- Seleccione las 8 casillas alrededor de la casilla indicada por
     *	    parametro.
     */
    
    
    
    
    
    
    
    
}
