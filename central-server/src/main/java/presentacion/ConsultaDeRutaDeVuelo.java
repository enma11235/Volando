package presentacion;

import javax.swing.JInternalFrame;

import excepciones.RutaDeVueloNoExisteException;
import excepciones.RutaDeVueloRepetidaException;
import excepciones.UsuarioNoEsAerolineaExcepcion;
import excepciones.UsuarioNoExisteException;
import excepciones.VueloNoExisteException;
import service.*;
import datatype.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JList;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import java.awt.FlowLayout;

public class ConsultaDeRutaDeVuelo extends JInternalFrame {

	// Controlador de usuarios que se utilizará para las acciones del JFrame
    private IControladorRutaDeVuelo controlRV;
    private IControladorUsuario controlU;
    
    private JComboBox comboBoxAerolineas;
    private JComboBox comboBoxRutasDeVuelo;
    private JComboBox comboBoxVuelos;

    private JLabel lblNombre;
    private JLabel lblDescripcion;
    private JLabel lblDescripcionC;
    private JLabel lblHora;
    private JLabel lblCostoTurista;
    private JLabel lblCostoEjecutivo;
    private JLabel lblCostoEqExtra;
    private JLabel lblFechaAlta;
    private JLabel lblEstado;
    private JLabel lblVisitas;

    private JButton btnCancelar;
    
    private JTextField textFieldNombre;
    private JTextArea textFieldDescripcion;
    private JTextField textFieldDescripcionC;
    private JTextField textFieldHora;
    private JTextField textFieldCostoTurista;
    private JTextField textFieldCostoEjecutivo;
    private JTextField textFieldCostoEqExtra;
    private JTextField textFieldAlta;
    private JTextField textFieldFechaAlta;
    private JTextField textFieldEstado;
    private JTextField textFieldVisitas;
    private JPanel panel;

	private JLabel lblSeleccionarAerolinea;
	private JLabel lblSeleccionarRutaDeVuelo;

	private JLabel lblSeleccionarVuelo;
	private JPanel panel_1;

	private JScrollPane scrollPane;
	private JLabel lblSeleccionarCategoria;
	private JScrollPane scrollPane2;
	
	private String rutaSeleccionada = null;
	private String aerolineaSeleccionada = null;
	private String rutaSeleccionada1 = null;
	private String aerolineaSeleccionada1 = null;
	private ArrayList<String> aerolineas = null;
	private ArrayList<String> rutasDeVuelo = null;
	private List<String> vuelos = null;
	
	private boolean init = true;

	public ConsultaDeRutaDeVuelo(IControladorRutaDeVuelo irv, IControladorUsuario iu) {
		controlRV = irv;
		controlU = iu;
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setTitle("Consulta de Ruta de Vuelo");
        setBounds(0, 0, 500, 600);
        GridBagLayout gridBagLayout_1 = new GridBagLayout();
        gridBagLayout_1.columnWidths = new int[]{150, 550};
        gridBagLayout_1.rowHeights = new int[]{25,25,25,75,25,25,25,25,25, 75, 25,25};
        gridBagLayout_1.columnWeights = new double[]{0.0, 1.0};
        gridBagLayout_1.rowWeights = new double[]{0.0,0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,0.0};
        getContentPane().setLayout(gridBagLayout_1);

        this.addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void componentShown(ComponentEvent e) {
				try {
					aerolineas = (ArrayList<String>) controlU.listarAerolineas();
					if(aerolineas.isEmpty()) {
						JOptionPane.showMessageDialog(e.getComponent(),"No existen aerolineas.");
						hide();
					}
				} catch (UsuarioNoExisteException ex) {
					JOptionPane.showMessageDialog(e.getComponent(),"No existen aerolineas.");
					hide();
				}
				comboBoxAerolineas.removeAllItems();
				if(aerolineas!=null) {
					for(String a:aerolineas) {
						comboBoxAerolineas.addItem(a);
					}
				}
				
				if(aerolineaSeleccionada1 != null) {
					comboBoxAerolineas.setSelectedItem(aerolineaSeleccionada1 );
					aerolineaSeleccionada1  = null;
				}
				if(rutaSeleccionada1 != null) {
					comboBoxRutasDeVuelo.setSelectedItem(rutaSeleccionada1 );
					rutaSeleccionada1  = null;
				}
			}
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
			}
		});
        //Campo para seleccion de aerolinea
        lblSeleccionarAerolinea = new JLabel("Aerolineas:");
        lblSeleccionarAerolinea.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSeleccionarAerolinea.setVerticalAlignment(SwingConstants.CENTER);
        //comboBoxAerolineas.setEditable(false);
        GridBagConstraints gbc_lblSeleccionarAerolinea = new GridBagConstraints();
        gbc_lblSeleccionarAerolinea.insets = new Insets(0, 0, 5, 5);
        gbc_lblSeleccionarAerolinea.anchor = GridBagConstraints.EAST;
        gbc_lblSeleccionarAerolinea.fill = GridBagConstraints.VERTICAL;
        gbc_lblSeleccionarAerolinea.gridx = 0;
        gbc_lblSeleccionarAerolinea.gridy = 0;
        getContentPane().add(lblSeleccionarAerolinea, gbc_lblSeleccionarAerolinea);
        
        comboBoxAerolineas = new JComboBox();
        comboBoxAerolineas.setModel(new DefaultComboBoxModel());
        GridBagConstraints gbc_comboBoxAerolineas = new GridBagConstraints();
        gbc_comboBoxAerolineas.fill = GridBagConstraints.BOTH;
        gbc_comboBoxAerolineas.insets = new Insets(0, 0, 5, 0);
        gbc_comboBoxAerolineas.gridx = 1;
        gbc_comboBoxAerolineas.gridy = 0;
        getContentPane().add(comboBoxAerolineas, gbc_comboBoxAerolineas);
        
        //Evento del combobox aerolineas
        comboBoxAerolineas.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				if(comboBoxAerolineas.getSelectedItem()!=null) {
					aerolineaSeleccionada = comboBoxAerolineas.getSelectedItem().toString();
					try {
						
						rutasDeVuelo = (ArrayList<String>) controlRV.listarRutasDeVuelo(aerolineaSeleccionada);
						
					} catch (UsuarioNoEsAerolineaExcepcion e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					comboBoxRutasDeVuelo.removeAllItems();
					comboBoxVuelos.removeAllItems();
					if(!init) limpiarFormulario();
					init = false;
					if(rutasDeVuelo!=null) {
						for(String r:rutasDeVuelo) {
							comboBoxRutasDeVuelo.addItem(r);
						}
					}
					comboBoxRutasDeVuelo.setEnabled(true);
				}		
			}
		});
        //Campo para seleccion de la ruta de vuelo
        lblSeleccionarRutaDeVuelo = new JLabel("Rutas de Vuelo:");
        lblSeleccionarRutaDeVuelo.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSeleccionarRutaDeVuelo.setVerticalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblSeleccionarRutaDeVuelo = new GridBagConstraints();
        gbc_lblSeleccionarRutaDeVuelo.anchor = GridBagConstraints.EAST;
        gbc_lblSeleccionarRutaDeVuelo.fill = GridBagConstraints.VERTICAL;
        gbc_lblSeleccionarRutaDeVuelo.insets = new Insets(0, 0, 5, 5);
        gbc_lblSeleccionarRutaDeVuelo.gridx = 0;
        gbc_lblSeleccionarRutaDeVuelo.gridy = 1;
        getContentPane().add(lblSeleccionarRutaDeVuelo, gbc_lblSeleccionarRutaDeVuelo);
        
        comboBoxRutasDeVuelo = new JComboBox();
        comboBoxRutasDeVuelo.setModel(new DefaultComboBoxModel());
        GridBagConstraints gbc_comboBoxRutasDeVuelo = new GridBagConstraints();
        gbc_comboBoxRutasDeVuelo.fill = GridBagConstraints.BOTH;
        gbc_comboBoxRutasDeVuelo.insets = new Insets(0, 0, 5, 0);
        gbc_comboBoxRutasDeVuelo.gridx = 1;
        gbc_comboBoxRutasDeVuelo.gridy = 1;
        getContentPane().add(comboBoxRutasDeVuelo, gbc_comboBoxRutasDeVuelo);
        
        //Evento de combobox rutadevuelo
        comboBoxRutasDeVuelo.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				if(comboBoxAerolineas.getSelectedItem()!=null && comboBoxRutasDeVuelo.getSelectedItem()!=null) {
					rutaSeleccionada = comboBoxRutasDeVuelo.getSelectedItem().toString();
					comboBoxVuelos.removeAllItems();
					try {
						
						vuelos = controlRV.listarVuelos(aerolineaSeleccionada,rutaSeleccionada);
					} catch (VueloNoExisteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					cargarVuelos();		
				}	
			}
		});
        //Campo para el nombre de la ruta de vuelo 
        lblNombre = new JLabel("Nombre:");
        lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNombre.setVerticalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblNombre = new GridBagConstraints();
        gbc_lblNombre.anchor = GridBagConstraints.EAST;
        gbc_lblNombre.fill = GridBagConstraints.VERTICAL;
        gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
        gbc_lblNombre.gridx = 0;
        gbc_lblNombre.gridy = 2;
        getContentPane().add(lblNombre, gbc_lblNombre);
        
        textFieldNombre = new JTextField();
        GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
        gbc_textFieldNombre.fill = GridBagConstraints.BOTH;
        gbc_textFieldNombre.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldNombre.gridx = 1;
        gbc_textFieldNombre.gridy = 2;
        getContentPane().add(textFieldNombre, gbc_textFieldNombre);
        textFieldNombre.setEditable(false);
        textFieldNombre.setColumns(10);
        
        //Campo para la descripcion de la ruta de vuelo
        lblDescripcion = new JLabel("Descripcion:");
        lblDescripcion.setHorizontalAlignment(SwingConstants.RIGHT);
        lblDescripcion.setVerticalAlignment(SwingConstants.TOP);
        lblDescripcion.setEnabled(true);
        GridBagConstraints gbc_lblIngreseDescripcion = new GridBagConstraints();
        gbc_lblIngreseDescripcion.anchor = GridBagConstraints.EAST;
        gbc_lblIngreseDescripcion.fill = GridBagConstraints.VERTICAL;
        gbc_lblIngreseDescripcion.insets = new Insets(0, 0, 5, 5);
        gbc_lblIngreseDescripcion.gridx = 0;
        gbc_lblIngreseDescripcion.gridy = 3;
        getContentPane().add(lblDescripcion, gbc_lblIngreseDescripcion);
        
        scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 1;
        gbc_scrollPane.gridy = 3;
        getContentPane().add(scrollPane, gbc_scrollPane);
        
        textFieldDescripcion = new JTextArea();
        textFieldDescripcion.setEditable(false);
        textFieldDescripcion.setEnabled(true);
        scrollPane.setViewportView(textFieldDescripcion);
        textFieldDescripcion.setColumns(10);
        textFieldDescripcion.setSize(100,400);
        
      //Campo para la descripcion corta de la ruta de vuelo
        lblDescripcionC = new JLabel("Descripcion Corta:");
        lblDescripcionC.setHorizontalAlignment(SwingConstants.RIGHT);
        lblDescripcionC.setVerticalAlignment(SwingConstants.TOP);
        lblDescripcionC.setEnabled(true);
        GridBagConstraints gbc_lblIngreseDescripcionC = new GridBagConstraints();
        gbc_lblIngreseDescripcionC.anchor = GridBagConstraints.EAST;
        gbc_lblIngreseDescripcionC.fill = GridBagConstraints.VERTICAL;
        gbc_lblIngreseDescripcionC.insets = new Insets(0, 0, 5, 5);
        gbc_lblIngreseDescripcionC.gridx = 0;
        gbc_lblIngreseDescripcionC.gridy = 4;
        getContentPane().add(lblDescripcionC, gbc_lblIngreseDescripcionC);
        
        textFieldDescripcionC = new JTextField();
        GridBagConstraints gbc_textFieldDescripcionC = new GridBagConstraints();
        gbc_textFieldDescripcionC.fill = GridBagConstraints.BOTH;
        gbc_textFieldDescripcionC.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldDescripcionC.gridx = 1;
        gbc_textFieldDescripcionC.gridy = 4;
        getContentPane().add(textFieldDescripcionC, gbc_textFieldDescripcionC);
        textFieldDescripcionC.setEditable(false);
        textFieldDescripcionC.setColumns(10);
        
        //Campo para la hora de la ruta de vuelo
        lblHora = new JLabel("Hora:");
        lblHora.setHorizontalAlignment(SwingConstants.RIGHT);
        lblHora.setVerticalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblHora = new GridBagConstraints();
        gbc_lblHora.anchor = GridBagConstraints.EAST;
        gbc_lblHora.fill = GridBagConstraints.VERTICAL;
        gbc_lblHora.insets = new Insets(0, 0, 5, 5);
        gbc_lblHora.gridx = 0;
        gbc_lblHora.gridy = 5;
        getContentPane().add(lblHora, gbc_lblHora);

        textFieldHora = new JTextField();
        GridBagConstraints gbc_textFieldHora = new GridBagConstraints();
        gbc_textFieldHora.fill = GridBagConstraints.BOTH;
        gbc_textFieldHora.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldHora.gridx = 1;
        gbc_textFieldHora.gridy = 5;
        getContentPane().add(textFieldHora, gbc_textFieldHora);
        textFieldHora.setEditable(false);
        textFieldHora.setColumns(10);
        
        //Campo para el costo de turista de la ruta de vuelo
        lblCostoTurista = new JLabel("Costo Turista:");
        lblCostoTurista.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCostoTurista.setVerticalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblCostoTurista = new GridBagConstraints();
        gbc_lblCostoTurista.anchor = GridBagConstraints.EAST;
        gbc_lblCostoTurista.fill = GridBagConstraints.VERTICAL;
        gbc_lblCostoTurista.insets = new Insets(0, 0, 5, 5);
        gbc_lblCostoTurista.gridx = 0;
        gbc_lblCostoTurista.gridy = 6;
        getContentPane().add(lblCostoTurista, gbc_lblCostoTurista);
        
        textFieldCostoTurista = new JTextField();
        GridBagConstraints gbc_textFieldCostoTurista = new GridBagConstraints();
        gbc_textFieldCostoTurista.fill = GridBagConstraints.BOTH;
        gbc_textFieldCostoTurista.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldCostoTurista.gridx = 1;
        gbc_textFieldCostoTurista.gridy = 6;
        getContentPane().add(textFieldCostoTurista, gbc_textFieldCostoTurista);
        textFieldCostoTurista.setEditable(false);
        textFieldCostoTurista.setColumns(10);
        
        //Campo para la ruta de vuelo del costo ejecutivo
        lblCostoEjecutivo = new JLabel("Costo Ejecutivo:");
        lblCostoEjecutivo.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCostoEjecutivo.setVerticalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblCostoEjecutivo = new GridBagConstraints();
        gbc_lblCostoEjecutivo.anchor = GridBagConstraints.EAST;
        gbc_lblCostoEjecutivo.fill = GridBagConstraints.VERTICAL;
        gbc_lblCostoEjecutivo.insets = new Insets(0, 0, 5, 5);
        gbc_lblCostoEjecutivo.gridx = 0;
        gbc_lblCostoEjecutivo.gridy = 7;
        getContentPane().add(lblCostoEjecutivo, gbc_lblCostoEjecutivo);
        
        textFieldCostoEjecutivo = new JTextField();
        GridBagConstraints gbc_textFieldCostoEjecutivo = new GridBagConstraints();
        gbc_textFieldCostoEjecutivo.fill = GridBagConstraints.BOTH;
        gbc_textFieldCostoEjecutivo.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldCostoEjecutivo.gridx = 1;
        gbc_textFieldCostoEjecutivo.gridy = 7;
        getContentPane().add(textFieldCostoEjecutivo, gbc_textFieldCostoEjecutivo);
        textFieldCostoEjecutivo.setEditable(false);
        textFieldCostoEjecutivo.setColumns(10);
        
        //Campo para el costo del equipaje extra de la ruta de vuelo
        lblCostoEqExtra = new JLabel("Costo Equipaje:");
        lblCostoEqExtra.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCostoEqExtra.setVerticalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblCostoEqExtra = new GridBagConstraints();
        gbc_lblCostoEqExtra.anchor = GridBagConstraints.EAST;
        gbc_lblCostoEqExtra.fill = GridBagConstraints.VERTICAL;
        gbc_lblCostoEqExtra.insets = new Insets(0, 0, 5, 5);
        gbc_lblCostoEqExtra.gridx = 0;
        gbc_lblCostoEqExtra.gridy = 8;
        getContentPane().add(lblCostoEqExtra, gbc_lblCostoEqExtra);
        
        textFieldCostoEqExtra = new JTextField();
        GridBagConstraints gbc_textFieldCostoEqExtra = new GridBagConstraints();
        gbc_textFieldCostoEqExtra.fill = GridBagConstraints.BOTH;
        gbc_textFieldCostoEqExtra.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldCostoEqExtra.gridx = 1;
        gbc_textFieldCostoEqExtra.gridy = 8;
        getContentPane().add(textFieldCostoEqExtra, gbc_textFieldCostoEqExtra);
        textFieldCostoEqExtra.setEditable(false);
        textFieldCostoEqExtra.setColumns(10);
        
        //Campo para la fecha de alta de la ruta de vuelo, por defecto la del sistema
        lblFechaAlta = new JLabel("Fecha Alta:");
        lblFechaAlta.setHorizontalAlignment(SwingConstants.RIGHT);
        lblFechaAlta.setVerticalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblFechaAlta = new GridBagConstraints();
        gbc_lblFechaAlta.anchor = GridBagConstraints.EAST;
        gbc_lblFechaAlta.fill = GridBagConstraints.VERTICAL;
        gbc_lblFechaAlta.insets = new Insets(0, 0, 5, 5);
        gbc_lblFechaAlta.gridx = 0;
        gbc_lblFechaAlta.gridy = 9;
        getContentPane().add(lblFechaAlta, gbc_lblFechaAlta);
        
        textFieldAlta = new JTextField();
        GridBagConstraints gbc_textFieldAlta = new GridBagConstraints();
        gbc_textFieldAlta.fill = GridBagConstraints.BOTH;
        gbc_textFieldAlta.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldAlta.gridx = 1;
        gbc_textFieldAlta.gridy = 9;
        getContentPane().add(textFieldAlta, gbc_textFieldAlta);
        textFieldAlta.setEditable(false);
        textFieldAlta.setColumns(10);
        
        //Campo para la categoria de la ruta de vuelo
        lblSeleccionarCategoria = new JLabel("Categorias:");
        lblSeleccionarCategoria.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSeleccionarCategoria.setVerticalAlignment(SwingConstants.TOP);
        GridBagConstraints gbc_lblSeleccionarCategoria = new GridBagConstraints();
        gbc_lblSeleccionarCategoria.insets = new Insets(0, 0, 5, 5);
        gbc_lblSeleccionarCategoria.anchor = GridBagConstraints.EAST;
        gbc_lblSeleccionarCategoria.fill = GridBagConstraints.VERTICAL;
        gbc_lblSeleccionarCategoria.gridx = 0;
        gbc_lblSeleccionarCategoria.gridy = 10;
        getContentPane().add(lblSeleccionarCategoria, gbc_lblSeleccionarCategoria);
        
        scrollPane2 = new JScrollPane();
        GridBagConstraints gbc_scrollPane2 = new GridBagConstraints();
        gbc_scrollPane2.insets = new Insets(0, 0, 5, 0);
        gbc_scrollPane2.fill = GridBagConstraints.BOTH;
        gbc_scrollPane2.gridx = 1;
        gbc_scrollPane2.gridy = 10;
        getContentPane().add(scrollPane2, gbc_scrollPane2);

        String[] cats = new String[5];
        cats[0]=("");
        JList listCats = new JList();
        scrollPane2.setViewportView(listCats);
        listCats.setVisibleRowCount(8);
        listCats.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listCats.setSize(100, 400);   
        
      //Campo para el estado de la ruta de vuelo
        lblEstado = new JLabel("Estado:");
        lblEstado.setHorizontalAlignment(SwingConstants.RIGHT);
        lblEstado.setVerticalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblEstado = new GridBagConstraints();
        gbc_lblEstado.anchor = GridBagConstraints.EAST;
        gbc_lblEstado.fill = GridBagConstraints.VERTICAL;
        gbc_lblEstado.insets = new Insets(0, 0, 5, 5);
        gbc_lblEstado.gridx = 0;
        gbc_lblEstado.gridy = 11;
        getContentPane().add(lblEstado, gbc_lblEstado);
        
        textFieldEstado = new JTextField();
        GridBagConstraints gbc_textFieldEstado = new GridBagConstraints();
        gbc_textFieldEstado.fill = GridBagConstraints.BOTH;
        gbc_textFieldEstado.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldEstado.gridx = 1;
        gbc_textFieldEstado.gridy = 11;
        getContentPane().add(textFieldEstado, gbc_textFieldEstado);
        textFieldEstado.setEditable(false);
        textFieldEstado.setColumns(10);
        
        lblVisitas = new JLabel("Visitas:");
        lblVisitas.setHorizontalAlignment(SwingConstants.RIGHT);
        lblVisitas.setVerticalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblVisitas = new GridBagConstraints();
        gbc_lblVisitas.anchor = GridBagConstraints.EAST;
        gbc_lblVisitas.fill = GridBagConstraints.VERTICAL;
        gbc_lblVisitas.insets = new Insets(0, 0, 5, 5);
        gbc_lblVisitas.gridx = 0;
        gbc_lblVisitas.gridy = 12;
        getContentPane().add(lblVisitas, gbc_lblVisitas);
        
        textFieldVisitas = new JTextField();
        GridBagConstraints gbc_textFieldVisitas = new GridBagConstraints();
        gbc_textFieldVisitas.fill = GridBagConstraints.BOTH;
        gbc_textFieldVisitas.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldVisitas.gridx = 1;
        gbc_textFieldVisitas.gridy = 12;
        getContentPane().add(textFieldVisitas, gbc_textFieldVisitas);
        textFieldVisitas.setEditable(false);
        textFieldVisitas.setColumns(10);
        
        //Campo para seleccionar los vuelos de la ruta de vuelo y obtener mas informacion 
        lblSeleccionarVuelo = new JLabel("Vuelos:");
        lblSeleccionarVuelo.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSeleccionarVuelo.setVerticalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblSeleccionarVuelo = new GridBagConstraints();
        gbc_lblSeleccionarVuelo.anchor = GridBagConstraints.EAST;
        gbc_lblSeleccionarVuelo.fill = GridBagConstraints.VERTICAL;
        gbc_lblSeleccionarVuelo.insets = new Insets(0, 0, 5, 5);
        gbc_lblSeleccionarVuelo.gridx = 0;
        gbc_lblSeleccionarVuelo.gridy = 13;
        getContentPane().add(lblSeleccionarVuelo, gbc_lblSeleccionarVuelo);
        
        comboBoxVuelos = new JComboBox();
        GridBagConstraints gbc_comboBoxVuelos = new GridBagConstraints();
        gbc_comboBoxVuelos.fill = GridBagConstraints.BOTH;
        gbc_comboBoxVuelos.insets = new Insets(0, 0, 5, 0);
        gbc_comboBoxVuelos.gridx = 1;
        gbc_comboBoxVuelos.gridy = 13;
        getContentPane().add(comboBoxVuelos, gbc_comboBoxVuelos);
        
        comboBoxVuelos.setModel(new DefaultComboBoxModel());
        comboBoxVuelos.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            //String s2=(String) comboBoxVuelos.getActionCommand();
            if (comboBoxVuelos.getSelectedItem() != null) {
				MenuPrincipal.getVentanaVuelos().setVisible(true);
				MenuPrincipal.getVentanaVuelos().toFront();
				MenuPrincipal.getVentanaVuelos().setAerolineaSeleccionada(aerolineaSeleccionada);
				MenuPrincipal.getVentanaVuelos().setRutaSeleccionada(rutaSeleccionada);
				MenuPrincipal.getVentanaVuelos().setVueloSeleccionado((String) comboBoxVuelos.getSelectedItem());
			}
            else
            {
            	MenuPrincipal.getVentanaVuelos().setVisible(false);
				MenuPrincipal.getVentanaVuelos().toBack();
            }
         }
		});
        
        panel_1 = new JPanel();
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.insets = new Insets(0, 0, 5, 0);
        gbc_panel_1.fill = GridBagConstraints.BOTH;
        gbc_panel_1.gridx = 1;
        gbc_panel_1.gridy = 14;
        getContentPane().add(panel_1, gbc_panel_1);
        panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        //Boton finalizar
        btnCancelar = new JButton("Cancelar");
        panel_1.add(btnCancelar);
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //limpiarFormulario();
                setVisible(false);
                dispose(); 
            }
        });
   
	}
	
	private void cargarVuelos() {
		if(comboBoxAerolineas.getSelectedItem()!=null && comboBoxRutasDeVuelo.getSelectedItem()!=null) {
			rutaSeleccionada = comboBoxRutasDeVuelo.getSelectedItem().toString();
			aerolineaSeleccionada = comboBoxAerolineas.getSelectedItem().toString();
		}
		if(aerolineaSeleccionada!=null && rutaSeleccionada!=null) {
			if (vuelos != null) {
				for(String v:vuelos) {
					comboBoxVuelos.addItem(v);
				}
				comboBoxVuelos.setSelectedItem(null);
				comboBoxVuelos.setEnabled(true);

				completarInforv();
			}
			
		}
	}
	
	private void completarInforv() {
		//Completar info vuelo
		try {
			DTRutaDeVuelo dtrv = controlRV.obtenerInfoRutaDeVuelo(rutaSeleccionada);
			textFieldNombre.setText(dtrv.getNombre());
			textFieldDescripcion.setText(dtrv.getDescripcion());
			textFieldDescripcionC.setText(dtrv.getDescripcionCorta());
	        textFieldHora.setText((dtrv.getHora().toString()));
	        textFieldCostoTurista.setText(dtrv.getCostoTurista().toString());
	        textFieldCostoEjecutivo.setText(dtrv.getCostoEjecutivo().toString());
	        textFieldCostoEqExtra.setText(dtrv.getCostoEquipajeExtra().toString());
	        textFieldEstado.setText(dtrv.getEstado().name());
	        textFieldVisitas.setText(Integer.toString(dtrv.getVisitas()));
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	        String fechastr = dtrv.getFecha().format(formatter);
	        textFieldAlta.setText(fechastr);
	        String[] Arraycat;
	        ArrayList<DTCategoria> cats = (ArrayList<DTCategoria>) dtrv.getCategorias();
	        if(cats!=null) {
		        Arraycat = new String[cats.size()];
		        for (int i = 0; i < Arraycat.length; i++) {
		        	Arraycat[i] = cats.get(i).getNombre();
		        	
		        }
	        } else Arraycat = new String[0];
	        JList listCats = new JList(Arraycat);
	        scrollPane2.setViewportView(listCats);
	        listCats.setVisibleRowCount(8);
	        listCats.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	        listCats.setSize(100, 400); 
		} catch (RutaDeVueloNoExisteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
    // Permite borrar el contenido de un formulario antes de cerrarlo.
    // Recordar que las ventanas no se destruyen, sino que simplemente 
    // se ocultan, por lo que conviene borrar la información para que 
    // no aparezca al mostrarlas nuevamente.
    private void limpiarFormulario() {
        textFieldNombre.setText("");
        textFieldDescripcion.setText("");
        textFieldDescripcionC.setText("");
        textFieldHora.setText("");
        textFieldCostoTurista.setText("");
        textFieldCostoEjecutivo.setText("");
        textFieldCostoEqExtra.setText("");;	
        textFieldVisitas.setText("");
        textFieldAlta.setText("");
        rutaSeleccionada=null;
        scrollPane2.setViewport(null);
        textFieldEstado.setText("");
    }
    
	public void setRutaSeleccionada(String r) {
		rutaSeleccionada1 = r;
	}
    
	public void setAerolineaSeleccionada(String a) {
		aerolineaSeleccionada1 = a;
	}
    
}