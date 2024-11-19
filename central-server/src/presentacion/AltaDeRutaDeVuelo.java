package presentacion;

import javax.swing.JInternalFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import excepciones.RutaDeVueloNoExisteException;
import excepciones.RutaDeVueloRepetidaException;
import excepciones.UsuarioNoEsAerolineaExcepcion;
import excepciones.UsuarioNoExisteException;
import model.*;
import datatype.*;
import service.*;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.awt.event.ActionEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SpinnerDateModel;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JScrollPane;
import javax.swing.AbstractListModel;

@SuppressWarnings("serial")
public class AltaDeRutaDeVuelo extends JInternalFrame {

    // Controlador de rutas de vuelo que se utilizará para las acciones del JFrame
    private IFlightRouteController controlRV;
    private IUserController controlU;
    private ICityCategoryController controlCC;
    
    // Los componentes gráficos se agregan como atributos de la clase
    private JComboBox comboBoxAerolineas;
    private JComboBox comboBoxCiudadOrigen;
    private JComboBox comboBoxCiudadDestino;
    
    private JTextField textFieldNombre;
    private JTextArea textFieldDescripcion;
    private JTextField textFieldDescripcionC;
    private JTextField textFieldCostoTurista;
    private JTextField textFieldCostoEjecutivo;
    private JTextField textFieldCostoEqExtra;
    private JDateChooser textFieldFechaAlta;

    private JLabel lblIngreseNombre;
    private JLabel lblIngreseDescripcion;
    private JLabel lblIngreseDescripcionC;
    private JLabel lblIngreseHora;
    private JLabel lblIngreseCostoTurista;
    private JLabel lblIngreseCostoEjecutivo;
    private JLabel lblIngreseCostoEqExtra;
    private JLabel lblIngreseFechaAlta;
    private JLabel lblSeleccionarCiudadOrigen;
	private JLabel lblSeleccionarCiudadDestino;
	private JLabel lblSeleccionarCategoria;
	private JLabel lblSeleccionarAerolinea;
	 
    private JButton btnAceptar;
    private JButton btnCancelar;
    private JButton btnLimpiar;
    
    private JPanel panel;
    private JSpinner spinnerHora;
    private JScrollPane scrollPane;
    private JScrollPane scrollPane2;
	
	private JList<String> listCats;
	private List<String> cts = new ArrayList<String>(); 
	private DefaultListModel<String> modelo;
	
	private ArrayList<String> aerolineas = null;
	private ArrayList<CityDTO> dataciudades = null;
	private ArrayList<String> clavesCiudades = null;
	private List<String> categorias = null;

    public AltaDeRutaDeVuelo(IFlightRouteController irv,IUserController iu, ICityCategoryController icc) {
        // Se inicializa con el controlador de Rutas de Vuelo
        controlRV = irv;
        controlU = iu;
        controlCC = icc;

        // Propiedades del JInternalFrame como dimensión, posición dentro del frame,
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setTitle("Alta de Ruta de Vuelo");
        setBounds(0, 0, 500, 500);
        //Propiedades de la grilla del jinternal frame
        GridBagLayout gridBagLayout_1 = new GridBagLayout();
        gridBagLayout_1.columnWidths = new int[]{150, 350};
        gridBagLayout_1.rowHeights = new int[]{25,25,75,25,25,25,25,25, 25, 25,75};
        gridBagLayout_1.columnWeights = new double[]{0.0, 1.0};
        gridBagLayout_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,0.0, 0.0};
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
					dataciudades = (ArrayList<CityDTO>) controlCC.listarCiudades();
					categorias = controlCC.listarCategorias();
					if(dataciudades.isEmpty()) {
						JOptionPane.showMessageDialog(e.getComponent(),"No existen ciudades.");
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
				comboBoxCiudadOrigen.removeAllItems();
				comboBoxCiudadDestino.removeAllItems();
				if(dataciudades!=null) {
					for(CityDTO dc:dataciudades) {
						comboBoxCiudadOrigen.addItem(dc.getPais()+'-'+dc.getNombre());
						comboBoxCiudadDestino.addItem(dc.getPais()+'-'+dc.getNombre());
					}
				}

				if(categorias!=null) {
					modelo = new DefaultListModel<>();
					for(String c : categorias) {
						modelo.addElement(c);
					}
					listCats.setModel(modelo);
				}
			}
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
			}
		});
        //Propiedades del combobox para seleccionar la aerolinea
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
                
        comboBoxAerolineas.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				if(comboBoxAerolineas.getSelectedItem()!=null) {
					
				}
			}
		});        
        
        //Campos para el nombre
        lblIngreseNombre = new JLabel("Nombre:");
        lblIngreseNombre.setHorizontalAlignment(SwingConstants.RIGHT);
        lblIngreseNombre.setVerticalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblIngreseNombre = new GridBagConstraints();
        gbc_lblIngreseNombre.anchor = GridBagConstraints.EAST;
        gbc_lblIngreseNombre.fill = GridBagConstraints.VERTICAL;
        gbc_lblIngreseNombre.insets = new Insets(0, 0, 5, 5);
        gbc_lblIngreseNombre.gridx = 0;
        gbc_lblIngreseNombre.gridy = 1;
        getContentPane().add(lblIngreseNombre, gbc_lblIngreseNombre);
        
        textFieldNombre = new JTextField();
        textFieldNombre.setColumns(10);
        textFieldNombre.setEditable(true);
        GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
        gbc_textFieldNombre.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldNombre.fill = GridBagConstraints.BOTH;
        gbc_textFieldNombre.gridx = 1;
        gbc_textFieldNombre.gridy = 1;
        getContentPane().add(textFieldNombre, gbc_textFieldNombre);        

        //Campos para el descripcion
        lblIngreseDescripcion = new JLabel("Descripcion:");
        lblIngreseDescripcion.setHorizontalAlignment(SwingConstants.RIGHT);
        lblIngreseDescripcion.setVerticalAlignment(SwingConstants.TOP);
        GridBagConstraints gbc_lblIngreseDescripcion = new GridBagConstraints();
        gbc_lblIngreseDescripcion.anchor = GridBagConstraints.EAST;
        gbc_lblIngreseDescripcion.fill = GridBagConstraints.VERTICAL;
        gbc_lblIngreseDescripcion.insets = new Insets(0, 0, 5, 5);
        gbc_lblIngreseDescripcion.gridx = 0;
        gbc_lblIngreseDescripcion.gridy = 2;
        getContentPane().add(lblIngreseDescripcion, gbc_lblIngreseDescripcion);
        
        scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 1;
        gbc_scrollPane.gridy = 2;
        getContentPane().add(scrollPane, gbc_scrollPane);
        
        textFieldDescripcion = new JTextArea();
        scrollPane.setViewportView(textFieldDescripcion);
        textFieldDescripcion.setColumns(10);
        textFieldDescripcion.setEditable(true);
        textFieldDescripcion.setSize(100,400);
        
      //Campos para el descripcion corta
        lblIngreseDescripcionC = new JLabel("Descripcion Corta:");
        lblIngreseDescripcionC.setHorizontalAlignment(SwingConstants.RIGHT);
        lblIngreseDescripcionC.setVerticalAlignment(SwingConstants.TOP);
        GridBagConstraints gbc_lblIngreseDescripcionC = new GridBagConstraints();
        gbc_lblIngreseDescripcionC.anchor = GridBagConstraints.EAST;
        gbc_lblIngreseDescripcionC.fill = GridBagConstraints.VERTICAL;
        gbc_lblIngreseDescripcionC.insets = new Insets(0, 0, 5, 5);
        gbc_lblIngreseDescripcionC.gridx = 0;
        gbc_lblIngreseDescripcionC.gridy = 3;
        getContentPane().add(lblIngreseDescripcionC, gbc_lblIngreseDescripcionC);
        
        textFieldDescripcionC = new JTextField();
        textFieldDescripcionC.setColumns(10);
        textFieldDescripcionC.setEditable(true);
        GridBagConstraints gbc_textFieldDescripcionC = new GridBagConstraints();
        gbc_textFieldDescripcionC.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldDescripcionC.fill = GridBagConstraints.BOTH;
        gbc_textFieldDescripcionC.gridx = 1;
        gbc_textFieldDescripcionC.gridy = 3;
        getContentPane().add(textFieldDescripcionC, gbc_textFieldDescripcionC); 
        
        //Campos para la hora
        lblIngreseHora = new JLabel("Hora:");
        lblIngreseHora.setHorizontalAlignment(SwingConstants.RIGHT);
        lblIngreseHora.setVerticalAlignment(SwingConstants.TOP);
        GridBagConstraints gbc_lblIngreseHora = new GridBagConstraints();
        gbc_lblIngreseHora.anchor = GridBagConstraints.EAST;
        gbc_lblIngreseHora.fill = GridBagConstraints.VERTICAL;
        gbc_lblIngreseHora.insets = new Insets(0, 0, 5, 5);
        gbc_lblIngreseHora.gridx = 0;
        gbc_lblIngreseHora.gridy = 4;
        getContentPane().add(lblIngreseHora, gbc_lblIngreseHora);
        
        spinnerHora = new JSpinner();
        spinnerHora.setModel(new SpinnerDateModel(new Date(1725591600000L), null, null, Calendar.HOUR_OF_DAY));
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(spinnerHora, "HH:mm:ss");
        spinnerHora.setEditor(timeEditor);
        spinnerHora.setEnabled(true);
        GridBagConstraints gbc_textFieldHora = new GridBagConstraints();
        gbc_textFieldHora.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldHora.fill = GridBagConstraints.BOTH;
        gbc_textFieldHora.gridx = 1;
        gbc_textFieldHora.gridy = 4;
        getContentPane().add(spinnerHora, gbc_textFieldHora);

        //Campos para el costo turista
        lblIngreseCostoTurista = new JLabel("Costo Turista:");
        lblIngreseCostoTurista.setHorizontalAlignment(SwingConstants.RIGHT);
        lblIngreseCostoTurista.setVerticalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblIngreseCostoTurista = new GridBagConstraints();
        gbc_lblIngreseCostoTurista.anchor = GridBagConstraints.EAST;
        gbc_lblIngreseCostoTurista.fill = GridBagConstraints.VERTICAL;
        gbc_lblIngreseCostoTurista.insets = new Insets(0, 0, 5, 5);
        gbc_lblIngreseCostoTurista.gridx = 0;
        gbc_lblIngreseCostoTurista.gridy = 5;
        getContentPane().add(lblIngreseCostoTurista, gbc_lblIngreseCostoTurista);
        
        textFieldCostoTurista = new JTextField();
        textFieldCostoTurista.setColumns(10);
        textFieldCostoTurista.setEditable(true);
        GridBagConstraints gbc_textFieldCostoTurista = new GridBagConstraints();
        gbc_textFieldCostoTurista.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldCostoTurista.fill = GridBagConstraints.BOTH;
        gbc_textFieldCostoTurista.gridx = 1;
        gbc_textFieldCostoTurista.gridy = 5;
        getContentPane().add(textFieldCostoTurista, gbc_textFieldCostoTurista);

        //Campos para el Costo Ejecutivo
        lblIngreseCostoEjecutivo = new JLabel("Costo Ejecutivo:");
        lblIngreseCostoEjecutivo.setHorizontalAlignment(SwingConstants.RIGHT);
        lblIngreseCostoEjecutivo.setVerticalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblIngreseCostoEjecutivo = new GridBagConstraints();
        gbc_lblIngreseCostoEjecutivo.anchor = GridBagConstraints.EAST;
        gbc_lblIngreseCostoEjecutivo.fill = GridBagConstraints.VERTICAL;
        gbc_lblIngreseCostoEjecutivo.insets = new Insets(0, 0, 5, 5);
        gbc_lblIngreseCostoEjecutivo.gridx = 0;
        gbc_lblIngreseCostoEjecutivo.gridy = 6;
        getContentPane().add(lblIngreseCostoEjecutivo, gbc_lblIngreseCostoEjecutivo);
        
        textFieldCostoEjecutivo = new JTextField();
        textFieldCostoEjecutivo.setColumns(10);
        textFieldCostoEjecutivo.setEditable(true);
        GridBagConstraints gbc_textFieldCostoEjecutivo = new GridBagConstraints();
        gbc_textFieldCostoEjecutivo.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldCostoEjecutivo.fill = GridBagConstraints.BOTH;
        gbc_textFieldCostoEjecutivo.gridx = 1;
        gbc_textFieldCostoEjecutivo.gridy = 6;
        getContentPane().add(textFieldCostoEjecutivo, gbc_textFieldCostoEjecutivo);
                
        //Campos para el costo del equipaje extra
        lblIngreseCostoEqExtra = new JLabel("Costo Equipaje:");
        lblIngreseCostoEqExtra.setHorizontalAlignment(SwingConstants.RIGHT);
        lblIngreseCostoEqExtra.setVerticalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblIngreseCostoEqExtra = new GridBagConstraints();
        gbc_lblIngreseCostoEqExtra.anchor = GridBagConstraints.EAST;
        gbc_lblIngreseCostoEqExtra.fill = GridBagConstraints.VERTICAL;
        gbc_lblIngreseCostoEqExtra.insets = new Insets(0, 0, 5, 5);
        gbc_lblIngreseCostoEqExtra.gridx = 0;
        gbc_lblIngreseCostoEqExtra.gridy = 7;
        getContentPane().add(lblIngreseCostoEqExtra, gbc_lblIngreseCostoEqExtra);
        
        textFieldCostoEqExtra = new JTextField();
        textFieldCostoEqExtra.setColumns(10);
        textFieldCostoEqExtra.setEditable(true);
        GridBagConstraints gbc_textFieldCostoEqExtra = new GridBagConstraints();
        gbc_textFieldCostoEqExtra.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldCostoEqExtra.fill = GridBagConstraints.BOTH;
        gbc_textFieldCostoEqExtra.gridx = 1;
        gbc_textFieldCostoEqExtra.gridy = 7;
        getContentPane().add(textFieldCostoEqExtra, gbc_textFieldCostoEqExtra);
        
        //Campos para la ciudad de origen
        lblSeleccionarCiudadOrigen = new JLabel("Ciudad Origen:");
        lblSeleccionarCiudadOrigen.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSeleccionarCiudadOrigen.setVerticalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblSeleccionarCiudadOrigen = new GridBagConstraints();
        gbc_lblSeleccionarCiudadOrigen.insets = new Insets(0, 0, 5, 5);
        gbc_lblSeleccionarCiudadOrigen.anchor = GridBagConstraints.EAST;
        gbc_lblSeleccionarCiudadOrigen.fill = GridBagConstraints.VERTICAL;
        gbc_lblSeleccionarCiudadOrigen.gridx = 0;
        gbc_lblSeleccionarCiudadOrigen.gridy = 8;
        getContentPane().add(lblSeleccionarCiudadOrigen, gbc_lblSeleccionarCiudadOrigen);

        comboBoxCiudadOrigen = new JComboBox();
        comboBoxCiudadOrigen.setModel(new DefaultComboBoxModel());
        GridBagConstraints gbc_comboBoxCiudadOrigen = new GridBagConstraints();
        gbc_comboBoxCiudadOrigen.fill = GridBagConstraints.BOTH;
        gbc_comboBoxCiudadOrigen.insets = new Insets(0, 0, 5, 0);
        gbc_comboBoxCiudadOrigen.gridx = 1;
        gbc_comboBoxCiudadOrigen.gridy = 8;
        getContentPane().add(comboBoxCiudadOrigen, gbc_comboBoxCiudadOrigen);
        
        //Campos para la ciudad de destino
        lblSeleccionarCiudadDestino = new JLabel("Ciudad Destino:");
        lblSeleccionarCiudadDestino.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSeleccionarCiudadDestino.setVerticalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblSeleccionarCiudadDestino = new GridBagConstraints();
        gbc_lblSeleccionarCiudadDestino.insets = new Insets(0, 0, 5, 5);
        gbc_lblSeleccionarCiudadDestino.anchor = GridBagConstraints.EAST;
        gbc_lblSeleccionarCiudadDestino.fill = GridBagConstraints.VERTICAL;
        gbc_lblSeleccionarCiudadDestino.gridx = 0;
        gbc_lblSeleccionarCiudadDestino.gridy = 9;
        getContentPane().add(lblSeleccionarCiudadDestino, gbc_lblSeleccionarCiudadDestino);

        comboBoxCiudadDestino = new JComboBox();
        comboBoxCiudadDestino.setModel(new DefaultComboBoxModel());
        GridBagConstraints gbc_comboBoxCiudadDestino = new GridBagConstraints();
        gbc_comboBoxCiudadDestino.fill = GridBagConstraints.BOTH;
        gbc_comboBoxCiudadDestino.insets = new Insets(0, 0, 5, 0);
        gbc_comboBoxCiudadDestino.gridx = 1;
        gbc_comboBoxCiudadDestino.gridy = 9;
        getContentPane().add(comboBoxCiudadDestino, gbc_comboBoxCiudadDestino);
        
        //Campos para la fecha de alta
        lblIngreseFechaAlta = new JLabel("Fecha Alta:");
        lblIngreseFechaAlta.setHorizontalAlignment(SwingConstants.RIGHT);
        lblIngreseFechaAlta.setVerticalAlignment(SwingConstants.TOP);
        GridBagConstraints gbc_lblIngreseFechaAlta = new GridBagConstraints();
        gbc_lblIngreseFechaAlta.anchor = GridBagConstraints.EAST;
        gbc_lblIngreseFechaAlta.fill = GridBagConstraints.VERTICAL;
        gbc_lblIngreseFechaAlta.insets = new Insets(0, 0, 5, 5);
        gbc_lblIngreseFechaAlta.gridx = 0;
        gbc_lblIngreseFechaAlta.gridy = 10;
        getContentPane().add(lblIngreseFechaAlta, gbc_lblIngreseFechaAlta);
        
        //Fecha editable con jcalendar
        textFieldFechaAlta = new JDateChooser();
        textFieldFechaAlta.setEnabled(true);
        textFieldFechaAlta.setDateFormatString("dd/MM/yyyy");
        try {
			textFieldFechaAlta.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(LocalDate.now().toString()));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        GridBagConstraints gbc_textFieldFechaAlta = new GridBagConstraints();
        gbc_textFieldFechaAlta.gridwidth = 1;
        gbc_textFieldFechaAlta.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldFechaAlta.fill = GridBagConstraints.BOTH;
        gbc_textFieldFechaAlta.gridx = 1;
        gbc_textFieldFechaAlta.gridy = 10;
        getContentPane().add(textFieldFechaAlta, gbc_textFieldFechaAlta);
        
        //Campos para las categorias
        lblSeleccionarCategoria = new JLabel("Categorias:");
        lblSeleccionarCategoria.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSeleccionarCategoria.setVerticalAlignment(SwingConstants.TOP);
        GridBagConstraints gbc_lblSeleccionarCategoria = new GridBagConstraints();
        gbc_lblSeleccionarCategoria.insets = new Insets(0, 0, 5, 5);
        gbc_lblSeleccionarCategoria.anchor = GridBagConstraints.EAST;
        gbc_lblSeleccionarCategoria.fill = GridBagConstraints.VERTICAL;
        gbc_lblSeleccionarCategoria.gridx = 0;
        gbc_lblSeleccionarCategoria.gridy = 11;
        getContentPane().add(lblSeleccionarCategoria, gbc_lblSeleccionarCategoria);
        
        scrollPane2 = new JScrollPane();
        GridBagConstraints gbc_scrollPane2 = new GridBagConstraints();
        gbc_scrollPane2.insets = new Insets(0, 0, 5, 0);
        gbc_scrollPane2.fill = GridBagConstraints.BOTH;
        gbc_scrollPane2.gridx = 1;
        gbc_scrollPane2.gridy = 11;
        getContentPane().add(scrollPane2, gbc_scrollPane2);
        
        listCats = new JList();
        scrollPane2.setViewportView(listCats);
        listCats.setVisibleRowCount(5);
        listCats.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listCats.setSize(100, 400);

        panel = new JPanel();
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.gridheight = 2;
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.gridx = 1;
        gbc_panel.gridy = 12;
        getContentPane().add(panel, gbc_panel);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
	    //Boton aceptar
        btnAceptar = new JButton("Aceptar");
        panel.add(btnAceptar);
        
        btnAceptar.addActionListener(new ActionListener() {
      	   public void actionPerformed(ActionEvent arg0) {
      		 cmdRegistroRutaDeVueloActionPerformed(arg0);
         		}
         });
        
        //Boton cancelar
        btnCancelar = new JButton("Cancelar");
        panel.add(btnCancelar);
        
        btnCancelar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
               limpiarFormulario();
               modelo.clear();
               setVisible(false);
            }
        });
        
        //Boton Limpiar
        btnLimpiar = new JButton("Limpiar");
        panel.add(btnLimpiar); 
        
        btnLimpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	limpiarFormulario();
            }
        });
     }
              
    // Este método es invocado al querer registrar una ruta de vuelo, funcionalidad
    // provista por la operación del sistem registrarRutaDeVuelo().
    // Previamente se hace una verificación de los campos, particularmente que no sean vacíos
    // y que el nombre sea un string. 
    // Tanto en caso de que haya un error (de verificación o de registro) o no, se despliega
    // un mensaje utilizando un panel de mensaje (JOptionPane).
    protected void cmdRegistroRutaDeVueloActionPerformed(ActionEvent arg0) {       
        if (checkFormulario()) {
            try {
            	String nombreRV = this.textFieldNombre.getText();
                String descripcionRV = this.textFieldDescripcion.getText();
                String descripcionCRV = this.textFieldDescripcionC.getText();
                LocalTime hora = LocalTime.parse( new SimpleDateFormat("HH:mm:ss").format(this.spinnerHora.getValue()));
                Float costoTurista = Float.parseFloat(this.textFieldCostoTurista.getText());
                Float costoEjecutivo = Float.parseFloat(this.textFieldCostoEjecutivo.getText());
                Float costoEqExtra = Float.parseFloat(this.textFieldCostoEqExtra.getText());
                LocalDate fecha = textFieldFechaAlta.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                String nickname = (String)comboBoxAerolineas.getSelectedItem();
                String claveCiudadOrigen = (String)comboBoxCiudadOrigen.getSelectedItem();
                String claveCiudadDestino = (String)comboBoxCiudadDestino.getSelectedItem();
                String[] ca= new String[cts.size()];
                if(cts.size() == 0) {
                	JOptionPane.showMessageDialog(this, "Debe seleccionar al menos una categoría", "Registrar Ruta de Vuelo", JOptionPane.ERROR_MESSAGE);
                	return;
                }

                for (int i=0;i<ca.length;i++) {
                    ca[i] = cts.get(i);
                }
                controlRV.agregarRutaDeVuelo(nickname, nombreRV, descripcionRV, descripcionCRV, hora, costoTurista, costoEjecutivo, costoEqExtra, claveCiudadOrigen,claveCiudadDestino,fecha,ca,"","",0);

                // Muestro éxito de la operación
                JOptionPane.showMessageDialog(this, "La ruta de vuelo se ha creado con éxito.", "Registrar Ruta de Vuelo",
                        JOptionPane.INFORMATION_MESSAGE);
 
            } catch (RutaDeVueloRepetidaException eR) {
                // Muestro error de registro
                JOptionPane.showMessageDialog(this, eR.getMessage(), "Registrar Ruta de Vuelo", JOptionPane.ERROR_MESSAGE);
            } catch(UsuarioNoEsAerolineaExcepcion eU) {
            	JOptionPane.showMessageDialog(this, eU.getMessage(), "Registrar Ruta de Vuelo", JOptionPane.ERROR_MESSAGE);
            }

            // Limpio el internal frame antes de cerrar la ventana
            limpiarFormulario();
            setVisible(false);
        }
    }
 
    // Permite validar la información introducida en los campos e indicar
    // a través de un mensaje de error (JOptionPane) cuando algo sucede.
    // Este tipo de chequeos se puede realizar de otras formas y con otras librerías de Java, 
    // por ejemplo impidiendo que se escriban caracteres no numéricos al momento de escribir en
    // en el campo de la cédula, o mostrando un mensaje de error apenas el foco pasa a otro campo.
    private boolean checkFormulario() {
    	String nombreRV = this.textFieldNombre.getText();
        String descripcionRV = this.textFieldDescripcion.getText();
        String descripcionCRV = this.textFieldDescripcionC.getText();
        String costoTuristaRV = this.textFieldCostoTurista.getText();
        String costoEjecutivoRV = this.textFieldCostoEjecutivo.getText();
        String costoEqExtraRV = this.textFieldCostoEqExtra.getText();
        String claveCiudadOrigen = this.comboBoxCiudadOrigen.getSelectedItem().toString();
        String claveCiudadDestino = this.comboBoxCiudadDestino.getSelectedItem().toString();
    
        cts = listCats.getSelectedValuesList();

        if (nombreRV.isEmpty() || descripcionRV.isEmpty() || descripcionCRV.isEmpty() || costoTuristaRV.isEmpty() || costoEjecutivoRV.isEmpty() || costoEqExtraRV.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No puede haber campos vacíos.", "Registrar Ruta de Vuelo",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
        	LocalTime.parse(new SimpleDateFormat("HH:mm:ss").format(this.spinnerHora.getValue()));
        } catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(this, "La hora ingresada no es correcta, el formato debe ser hh:mm:ss.", "Registrar Ruta de Vuelo",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        //Verificar si se ingresan numeros
        try {
        	Float.parseFloat(costoTuristaRV);
        } catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(this, "El costo turista debe ser un numero.", "Registrar Ruta de Vuelo",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
        	Float.parseFloat(costoEjecutivoRV);
        } catch (NumberFormatException e2) {
            JOptionPane.showMessageDialog(this, "El costo ejecutivo debe ser un numero.", "Registrar Ruta de Vuelo",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
        	Float.parseFloat(costoEqExtraRV);
        } catch (NumberFormatException e3) {
            JOptionPane.showMessageDialog(this, "El costo del equipaje extra debe ser un numero.", "Registrar Ruta de Vuelo",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
        	textFieldFechaAlta.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(this, "La fecha ingresada no es correcta, el formato debe ser dd/mm/yyyy.", "Registrar Ruta de Vuelo",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (claveCiudadOrigen.equals(claveCiudadDestino)) {
            JOptionPane.showMessageDialog(this, "La ciudad de origen y destino no pueden ser iguales.", "Registrar Ruta de Vuelo",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    // Permite borrar el contenido de un formulario antes de cerrarlo.
    // Recordar que las ventanas no se destruyen, sino que simplemente 
    // se ocultan, por lo que conviene borrar la información para que 
    // no aparezca al mostrarlas nuevamente.
    private void limpiarFormulario() {
        textFieldNombre.setText("");
        textFieldDescripcion.setText("");
        textFieldDescripcionC.setText("");
        textFieldCostoTurista.setText("");
        textFieldCostoEjecutivo.setText("");
        textFieldCostoEqExtra.setText("");
        listCats.clearSelection();
    }
}
