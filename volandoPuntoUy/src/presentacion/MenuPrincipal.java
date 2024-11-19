package presentacion;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

import logica.Fabrica;
import logica.IControladorCiudadCategoria;
import logica.IControladorPaquete;
import logica.IControladorRutaDeVuelo;
import logica.IControladorUsuario;
import servidor.Publicador;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import cargaDatos.CargarDatosDePrueba;

/**
 * Clase principal (Frame) con el método Main.
 * 
 * @author TProg2024
 *
 */

public class MenuPrincipal {

	private JFrame frmGestionDeVuelos;

	private IControladorRutaDeVuelo ICRV;
	private IControladorCiudadCategoria ICC;
	private IControladorPaquete IP;
	private IControladorUsuario IU;
	private AltaDeRutaDeVuelo creAltaDeRutaDeVueloInternalFrame;
	private ModificarDatosDeUsuario modificarDatosDeUsuariosInternalFrame;
	private AltaPaquete altaPaquete;
	private AltaCiudad altaCiudad;
	private static ConsultaVueloGUI consultaVueloVentana;
	private AgregarRutaDeVueloAPaqueteGUI agregarRutaPaqueteVentana;
	private AltaUsuario altaUsuarioGUI;
	private static ConsultaDeRutaDeVuelo consultaRutaDeVueloVentana;
	private static ConsultaRutasMasVisitadas consultaRutasMasVisitadasVentana;
	private static ConsultaDePaquete consultaPaquete;
	private ConsultaUsuario consultaUsuario;
	private AltaVuelo altaVuelo;
	private CompraPaquete compraPaquete;
	private ReservarVuelo reservaDeVuelo;
	private ModificarEstadoRuta modificarEstadoRuta;
	private static Publicador p;

	public static void main(String[] args) {
		
		p = new Publicador();
        p.publicar();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPrincipal windows = new MenuPrincipal();
					windows.frmGestionDeVuelos.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static ConsultaVueloGUI getVentanaVuelos() {
    	return consultaVueloVentana;
    }
	public static ConsultaDeRutaDeVuelo getVentanaRuta() {
    	return consultaRutaDeVueloVentana;
    }
	public static ConsultaDePaquete getVentanaPaquete() {
    	return consultaPaquete;
    }
	/**
	 * Create the application.
	 */
	public MenuPrincipal() {
		initialize();
		

		// Inicialización
		Fabrica fabrica = Fabrica.getInstance();
		ICRV = fabrica.getIControladorRutaDeVuelo();
		ICC = fabrica.getIControladorCiudadCategoria();
		IP = fabrica.getIControladorPaquete();
		IU = fabrica.getIControladorUsuario();

		// Se crean los InternalFrame y se incluyen al Frame principal ocultos.
		// De esta forma, no es necesario crear y destruir objetos lo que enlentece la
		// ejecución.
		// Cada InternalFrame usa un layout diferente, simplemente para mostrar
		// distintas opciones.
		
		altaPaquete = new AltaPaquete(IP);
		altaPaquete.setVisible(false);
		frmGestionDeVuelos.getContentPane().add(altaPaquete);
		
		consultaVueloVentana = new ConsultaVueloGUI(IU, ICRV);
		consultaVueloVentana.setVisible(false);
		frmGestionDeVuelos.getContentPane().add(consultaVueloVentana);

		creAltaDeRutaDeVueloInternalFrame = new AltaDeRutaDeVuelo(ICRV,IU,ICC);
		creAltaDeRutaDeVueloInternalFrame.setVisible(false);
		frmGestionDeVuelos.getContentPane().add(creAltaDeRutaDeVueloInternalFrame);

		altaCiudad = new AltaCiudad(ICC);
		altaCiudad.setVisible(false);
		frmGestionDeVuelos.getContentPane().add(altaCiudad);

		agregarRutaPaqueteVentana = new AgregarRutaDeVueloAPaqueteGUI(IU, ICRV, IP);
		agregarRutaPaqueteVentana.setVisible(false);
		frmGestionDeVuelos.getContentPane().add(agregarRutaPaqueteVentana);

		altaUsuarioGUI = new AltaUsuario(IU);
		altaUsuarioGUI.setVisible(false);
		frmGestionDeVuelos.getContentPane().add(altaUsuarioGUI);

		consultaRutaDeVueloVentana = new ConsultaDeRutaDeVuelo(ICRV,IU);
		consultaRutaDeVueloVentana.setVisible(false);
		frmGestionDeVuelos.getContentPane().add(consultaRutaDeVueloVentana);
		
		consultaRutasMasVisitadasVentana = new ConsultaRutasMasVisitadas(ICRV);
		consultaRutasMasVisitadasVentana.setVisible(false);
		frmGestionDeVuelos.getContentPane().add(consultaRutasMasVisitadasVentana);

		modificarDatosDeUsuariosInternalFrame = new ModificarDatosDeUsuario(IU);
		modificarDatosDeUsuariosInternalFrame.setVisible(false);
		frmGestionDeVuelos.getContentPane().add(modificarDatosDeUsuariosInternalFrame);

		consultaPaquete = new ConsultaDePaquete(IP, ICRV);
		consultaPaquete.setVisible(false);
		frmGestionDeVuelos.getContentPane().add(consultaPaquete);
		

		consultaUsuario = new ConsultaUsuario(IU, IP, ICRV);
		consultaUsuario.setVisible(false);
		frmGestionDeVuelos.getContentPane().add(consultaUsuario);
		

		altaVuelo = new AltaVuelo(ICRV, IU);
		altaVuelo.setVisible(false);
		frmGestionDeVuelos.getContentPane().add(altaVuelo);

		compraPaquete = new CompraPaquete(IU, IP);
		compraPaquete.setVisible(false);
		frmGestionDeVuelos.getContentPane().add(compraPaquete);
		
		reservaDeVuelo = new ReservarVuelo(IU, ICRV);
		reservaDeVuelo.setVisible(false);
		frmGestionDeVuelos.getContentPane().add(reservaDeVuelo);
		
		modificarEstadoRuta = new ModificarEstadoRuta(IU, ICRV);
		modificarEstadoRuta.setVisible(false);
		frmGestionDeVuelos.getContentPane().add(modificarEstadoRuta);
		
		//arrelgo jinternalFrame come espacio
		JLabel  dsd = new JLabel();
		dsd.setVisible(false);
		frmGestionDeVuelos.getContentPane().add(dsd);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		// Se crea el Frame con las dimensiones indicadas.
		frmGestionDeVuelos = new JFrame();
		frmGestionDeVuelos.setTitle("Gestión");
		frmGestionDeVuelos.setBounds(50, 50, 850, 750);
		frmGestionDeVuelos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Se crea una barra de menú (JMenuBar) con dos menú (JMenu) desplegables.
		// Cada menú contiene diferentes opciones (JMenuItem), los cuales tienen un
		// evento asociado que permite realizar una acción una vez se seleccionan.

		// Barra de menu
		JMenuBar menuBar = new JMenuBar();
		frmGestionDeVuelos.setJMenuBar(menuBar);

		//// Menu para sistema
		JMenu menuSistema = new JMenu("Sistema");
		menuBar.add(menuSistema);


		JMenuItem menuCargarDatosPrueba = new JMenuItem("Cargar datos de prueba");
		menuCargarDatosPrueba.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CargarDatosDePrueba.insertarDatosPrueba(ICRV, ICC, IP, IU);
				menuSistema.remove(menuCargarDatosPrueba);
			}
		});
		menuSistema.add(menuCargarDatosPrueba);
		

		JMenuItem menuSalir = new JMenuItem("Salir");
		menuSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Salgo de la aplicación
				frmGestionDeVuelos.setVisible(false);
				p.getEndpoint().stop();
				frmGestionDeVuelos.dispose();
			}
		});
		menuSistema.add(menuSalir);
		
		//// Menu con casos de usos de Altas
		JMenu menuAltas = new JMenu("Altas");
		menuBar.add(menuAltas);

		JMenuItem menuItemAltaUsuario = new JMenuItem("Alta de Usuario");
		menuItemAltaUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				altaUsuarioGUI.setVisible(true);
			}
		});
		menuAltas.add(menuItemAltaUsuario);

		JMenuItem menuItemAltaRutaDeVuelo = new JMenuItem("Alta de Ruta de Vuelo");
		menuItemAltaRutaDeVuelo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Llamada al caso de uso alta de ruta de vuelo
				creAltaDeRutaDeVueloInternalFrame.setVisible(true);
			}
		});
		menuAltas.add(menuItemAltaRutaDeVuelo);

		JMenuItem menuItemAltaVuelo = new JMenuItem("Alta de Vuelo");
		menuItemAltaVuelo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				altaVuelo.cargarAerolineas(); 
				altaVuelo.setVisible(true); 
			}
		});
		menuAltas.add(menuItemAltaVuelo);

		JMenuItem menuItemAltaCiudad = new JMenuItem("Alta de Ciudad");
		menuItemAltaCiudad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				altaCiudad.setVisible(true);
			}
		});
		menuAltas.add(menuItemAltaCiudad);

		JMenuItem menuItemCrearPaquete = new JMenuItem("Crear Paquete de Rutas de Vuelo");
		menuItemCrearPaquete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				altaPaquete.setVisible(true);
			}
		});
		menuAltas.add(menuItemCrearPaquete);

		//// Menu con casos de usos de consultas
		JMenu menuConsultas = new JMenu("Consultas");
		menuBar.add(menuConsultas);

		JMenuItem menuItemConsultaUsuario = new JMenuItem("Consulta de Usuario");
		menuItemConsultaUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consultaUsuario.cargarUsuarios();
				consultaUsuario.setVisible(true);
			}
		});
		menuConsultas.add(menuItemConsultaUsuario);

		JMenuItem menuItemConsultaRutaDeVuelo = new JMenuItem("Consulta de Ruta de Vuelo");
		menuItemConsultaRutaDeVuelo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consultaRutaDeVueloVentana.setVisible(true);
			}
		});
		menuConsultas.add(menuItemConsultaRutaDeVuelo);
		
		JMenuItem menuItemConsultaRutasMasVisitadas = new JMenuItem("Consulta de Rutas Mas Visitadas");
		menuItemConsultaRutasMasVisitadas.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Abre la ventana de consulta de rutas más visitadas
		    	consultaRutasMasVisitadasVentana.cargarRutasMasVisitadas();
		        consultaRutasMasVisitadasVentana.setVisible(true);
		    }
		});
	
		menuConsultas.add(menuItemConsultaRutasMasVisitadas);

		JMenuItem menuItemConsultaVuelo = new JMenuItem("Consulta de Vuelo");
		menuItemConsultaVuelo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consultaVueloVentana.setVisible(true);
			}
		});
		menuConsultas.add(menuItemConsultaVuelo);

		JMenuItem menuItemConsultaPaquete = new JMenuItem("Consulta de Paquete de Rutas de Vuelo");
		menuItemConsultaPaquete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consultaPaquete.setVisible(true);
			}
		});
		menuConsultas.add(menuItemConsultaPaquete);

		//// Menu con casos de uso con operaciones
		JMenu menuOperaciones = new JMenu("Operaciones");
		menuBar.add(menuOperaciones);

		JMenuItem menuItemModificarUsuario = new JMenuItem("Modificar Datos de Usuario");
		menuItemModificarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modificarDatosDeUsuariosInternalFrame.setVisible(true);
			}
		});
		menuOperaciones.add(menuItemModificarUsuario);

		JMenuItem menuItemReservaVuelo = new JMenuItem("Reserva de Vuelo");
		menuItemReservaVuelo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reservaDeVuelo.setVisible(true);
			}
		});
		menuOperaciones.add(menuItemReservaVuelo);

		JMenuItem menuItemAgregarRutaPaquete = new JMenuItem("Agregar Ruta de Vuelo a Paquete");
		menuItemAgregarRutaPaquete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agregarRutaPaqueteVentana.setVisible(true);
			}
		});
		menuOperaciones.add(menuItemAgregarRutaPaquete);

		JMenuItem menuItemCompraPaquete = new JMenuItem("Compra de Paquete");
		menuItemCompraPaquete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				compraPaquete.cargarClientes();
				compraPaquete.cargarPaquetes();
				compraPaquete.setVisible(true); 
			}
		});
		menuOperaciones.add(menuItemCompraPaquete);
		
		JMenuItem menuItemModificarEstadoRuta = new JMenuItem("Modificar Estado de Ruta");
		menuItemModificarEstadoRuta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modificarEstadoRuta.setVisible(true);
			}
		});
		menuOperaciones.add(menuItemModificarEstadoRuta);

	}
}
