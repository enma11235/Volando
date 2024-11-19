package presentacion;

import javax.swing.JInternalFrame;

import excepciones.UsuarioNoExisteException;
import model.*;
import datatype.*;
import service.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import com.toedter.calendar.JDateChooser;

import database.ManejadorUsuario;

import javax.swing.JTextField;
import javax.swing.JButton;
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
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import java.awt.FlowLayout;

public class ModificarDatosDeUsuario extends JInternalFrame {

	// Controlador de usuarios que se utilizará para las acciones del JFrame
    private IControladorRutaDeVuelo controlRV;
    private IControladorUsuario controlU;
    
    private JComboBox comboBoxUsuarios;
    private JComboBox comboBoxTipoDoc;
    //
    private JLabel lblSeleccionarUsuario;
    private JLabel lblNickName;
    private JLabel lblNombre;
    private JLabel lblEmail;
    
    private JLabel lblDescripcion;
    private JLabel lblSitioWeb;
    
    private JLabel lblApellido;
    private JLabel lblNacimento;
    private JLabel lblNacionalidad;
    private JLabel lblTipoDoc;
    private JLabel lblNumeroDoc;
    private JLabel label;
    //
    private JButton btnAceptar;
    private JButton btnEditar;
    private JButton btnCancelar;
    //
    private JTextField textFieldNickName;
    private JTextField textFieldNombre;
    private JTextField textFieldEmail;
    private JTextField textFieldApellido;
    private JTextField textFieldSitioWeb;
    private JTextField textFieldNacionalidad;
    private JTextField textFieldNumeroDoc;

    private JTextArea textFieldDescripcion;
    
    private JDateChooser textFieldNacimiento;
    
    private JPanel panelGenerico;
    private JPanel panelAerolinea;
    private JPanel panelCliente;
    private JPanel panel;
	private JPanel panel_1;

	private JScrollPane scrollPane;
		
	private DTUsuario dtusr;
	ArrayList<DTUsuario> usrs = null;

	public ModificarDatosDeUsuario(IControladorUsuario iu) {
		controlU = iu;
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setTitle("Modificar datos de Usuario");
        setBounds(0, 0, 550, 400);

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{400};
        gridBagLayout.rowHeights = new int[]{100,125, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
        getContentPane().setLayout(gridBagLayout);
        
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
					usrs = (ArrayList<DTUsuario>) controlU.listarUsuarios();
					if(usrs.isEmpty()) {
						JOptionPane.showMessageDialog(e.getComponent(),"No existen usuarios.");
						hide();
					}
				} catch (UsuarioNoExisteException ex) {
					JOptionPane.showMessageDialog(e.getComponent(),"No existen usuarios.");
					hide();
				}
				comboBoxUsuarios.removeAllItems();
				if(usrs!=null) {
					for(DTUsuario u:usrs) {
						comboBoxUsuarios.addItem(u.getNickname());
					}
				}
			}
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
			}
		});
      
        //
        panelGenerico = new JPanel();
        GridBagConstraints gbc_panelGenerico = new GridBagConstraints();
        gbc_panelGenerico.fill = GridBagConstraints.BOTH;
        gbc_panelGenerico.insets = new Insets(0, 0, 5, 0);
        gbc_panelGenerico.gridx = 0;
        gbc_panelGenerico.gridy = 0;
        getContentPane().add(panelGenerico, gbc_panelGenerico);
        
        GridBagLayout gbl_panelGenerico = new GridBagLayout();
        gbl_panelGenerico.columnWidths = new int[]{150,350};
        gbl_panelGenerico.rowHeights = new int[]{25,25,25,25};
        gbl_panelGenerico.columnWeights = new double[]{};
        gbl_panelGenerico.rowWeights = new double[]{0.0,0.0,0.0,0.0};
        panelGenerico.setLayout(gbl_panelGenerico);
        
        lblSeleccionarUsuario = new JLabel("Usuarios:");
        lblSeleccionarUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSeleccionarUsuario.setVerticalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblSeleccionarUsuario = new GridBagConstraints();
        gbc_lblSeleccionarUsuario.anchor = GridBagConstraints.EAST;
        gbc_lblSeleccionarUsuario.fill = GridBagConstraints.VERTICAL;
        gbc_lblSeleccionarUsuario.insets = new Insets(0, 0, 5, 5);
        gbc_lblSeleccionarUsuario.gridx = 0;
        gbc_lblSeleccionarUsuario.gridy = 0;
        panelGenerico.add(lblSeleccionarUsuario, gbc_lblSeleccionarUsuario);
        
        comboBoxUsuarios = new JComboBox();
        comboBoxUsuarios.setModel(new DefaultComboBoxModel());
        GridBagConstraints gbc_comboBoxUsuarios = new GridBagConstraints();
        gbc_comboBoxUsuarios.fill = GridBagConstraints.BOTH;
        gbc_comboBoxUsuarios.insets = new Insets(0, 0, 5, 0);
        gbc_comboBoxUsuarios.gridx = 1;
        gbc_comboBoxUsuarios.gridy = 0;
        panelGenerico.add(comboBoxUsuarios, gbc_comboBoxUsuarios);
        
        comboBoxUsuarios.addActionListener (new ActionListener () {
        	public void actionPerformed(ActionEvent e) {
        	    try {
        	    	if(comboBoxUsuarios.getSelectedItem()!=null) {
	        	        dtusr = controlU.obtenerInfoUsuario(comboBoxUsuarios.getSelectedItem().toString());
	        	        textFieldNickName.setText(dtusr.getNickname());
	        	        textFieldNombre.setText(dtusr.getNombre());
	        	        textFieldEmail.setText(dtusr.getEmail());
	
	        	        // Limpiar los elementos actuales del combo box antes de agregar nuevos
	        	        comboBoxTipoDoc.removeAllItems();
	
	        	        for (TipoDocumento td : TipoDocumento.values()) {
	        	            comboBoxTipoDoc.addItem(td);
	        	        }
	
	        	        if (dtusr instanceof DTAerolinea) {
	        	            panelCliente.setVisible(false);
	        	            panelAerolinea.setVisible(true);
	        	            btnEditar.setEnabled(true);
	
	        	            textFieldDescripcion.setText(((DTAerolinea) dtusr).getDescripcion());
	        	            textFieldSitioWeb.setText(((DTAerolinea) dtusr).getSitioWeb());
	        	        }else if (dtusr instanceof DTCliente) {
	        	            panelCliente.setVisible(true);
	        	            panelAerolinea.setVisible(false);
	        	            btnEditar.setEnabled(true);
	        	            comboBoxTipoDoc.setSelectedItem(((DTCliente) dtusr).getTipoDocumento());
	        	            textFieldApellido.setText(((DTCliente) dtusr).getApellido());
	        	            try {
	        	                textFieldNacimiento.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(((DTCliente) dtusr).getNacimiento().toString()));
	        	            } catch (ParseException e1) {
	        	                e1.printStackTrace();
	        	            }
	        	            textFieldNacionalidad.setText(((DTCliente) dtusr).getNacionalidad());
	        	            textFieldNumeroDoc.setText(((DTCliente) dtusr).getNumDocumento().toString());
	        	        }
        	    	}
        	    } catch (UsuarioNoExisteException e1) {
        	        e1.printStackTrace();
        	    }		
        }	 	
        });    
        
        lblNickName = new JLabel("nickName:");
        lblNickName.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblNickName = new GridBagConstraints();
        gbc_lblNickName.anchor = GridBagConstraints.EAST;
        gbc_lblNickName.fill = GridBagConstraints.VERTICAL;
        gbc_lblNickName.insets = new Insets(0, 0, 5, 5);
        gbc_lblNickName.gridx = 0;
        gbc_lblNickName.gridy = 1;
        panelGenerico.add(lblNickName, gbc_lblNickName);
        
        textFieldNickName = new JTextField();
        textFieldNickName.setEditable(false);
        GridBagConstraints gbc_textFieldNickName = new GridBagConstraints();
        gbc_textFieldNickName.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldNickName.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldNickName.gridx = 1;
        gbc_textFieldNickName.gridy = 1;
        panelGenerico.add(textFieldNickName, gbc_textFieldNickName);
        textFieldNickName.setColumns(10);
        
        lblNombre = new JLabel("Nombre:");
        lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblNombre = new GridBagConstraints();
        gbc_lblNombre.anchor = GridBagConstraints.EAST;
        gbc_lblNombre.fill = GridBagConstraints.VERTICAL;
        gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
        gbc_lblNombre.gridx = 0;
        gbc_lblNombre.gridy = 2;
        panelGenerico.add(lblNombre, gbc_lblNombre);          
                        
        textFieldNombre = new JTextField();
        textFieldNombre.setEditable(false);
        textFieldNombre.setColumns(10);
        GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
        gbc_textFieldNombre.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldNombre.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldNombre.gridx = 1;
        gbc_textFieldNombre.gridy = 2;
        panelGenerico.add(textFieldNombre, gbc_textFieldNombre);

        lblEmail = new JLabel("Email:");
        lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblEmail = new GridBagConstraints();
        gbc_lblEmail.anchor = GridBagConstraints.EAST;
        gbc_lblEmail.fill = GridBagConstraints.VERTICAL;
        gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
        gbc_lblEmail.gridx = 0;
        gbc_lblEmail.gridy = 3;
        panelGenerico.add(lblEmail, gbc_lblEmail);
        
        textFieldEmail = new JTextField();
        textFieldEmail.setEditable(false);
        textFieldEmail.setColumns(10);
        GridBagConstraints gbc_textFieldEmail = new GridBagConstraints();
        gbc_textFieldEmail.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldEmail.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldEmail.gridx = 1;
        gbc_textFieldEmail.gridy = 3;
        panelGenerico.add(textFieldEmail, gbc_textFieldEmail);
        
      //Panel para habilitar solo los datos extras de la aerolinea
        panelAerolinea = new JPanel();
        panelAerolinea.setVisible(false);
        GridBagConstraints gbc_panelAerolinea = new GridBagConstraints();
        gbc_panelAerolinea.fill = GridBagConstraints.BOTH;
        gbc_panelAerolinea.insets = new Insets(0, 0, 5, 0);
        gbc_panelAerolinea.gridx = 0;
        gbc_panelAerolinea.gridy = 1;
        getContentPane().add(panelAerolinea, gbc_panelAerolinea);
        
        GridBagLayout gbl_panelAerolinea = new GridBagLayout();
        gbl_panelAerolinea.columnWidths = new int[]{150,350};
        gbl_panelAerolinea.rowHeights = new int[]{100,25};
        gbl_panelAerolinea.columnWeights = new double[]{0.0, 0.0};
        gbl_panelAerolinea.rowWeights = new double[]{0.0, 0.0};
        panelAerolinea.setLayout(gbl_panelAerolinea);
        
        lblDescripcion = new JLabel("Descripcion:");
        lblDescripcion.setHorizontalAlignment(SwingConstants.RIGHT);
        lblDescripcion.setVerticalAlignment(SwingConstants.TOP);
        GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
        gbc_lblDescripcion.anchor = GridBagConstraints.EAST;
        gbc_lblDescripcion.fill = GridBagConstraints.VERTICAL;
        gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
        gbc_lblDescripcion.gridx = 0;
        gbc_lblDescripcion.gridy = 0;
        panelAerolinea.add(lblDescripcion, gbc_lblDescripcion);
        
        scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 1;
        gbc_scrollPane.gridy = 0;
        panelAerolinea.add(scrollPane, gbc_scrollPane);
        
        textFieldDescripcion = new JTextArea();
        scrollPane.setViewportView(textFieldDescripcion);
        textFieldDescripcion.setColumns(10);
        textFieldDescripcion.setEditable(false);
        textFieldDescripcion.setSize(200,400);
                  
        lblSitioWeb = new JLabel("Sitio Web:");
        lblSitioWeb.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblSitioWeb = new GridBagConstraints();
        gbc_lblSitioWeb.anchor = GridBagConstraints.EAST;
        gbc_lblSitioWeb.fill = GridBagConstraints.VERTICAL;
        gbc_lblSitioWeb.insets = new Insets(0, 0, 5, 5);
        gbc_lblSitioWeb.gridx = 0;
        gbc_lblSitioWeb.gridy = 1;
        panelAerolinea.add(lblSitioWeb, gbc_lblSitioWeb);
        
        textFieldSitioWeb= new JTextField();
        textFieldSitioWeb.setEditable(false);
        textFieldSitioWeb.setColumns(10);
        textFieldSitioWeb.setToolTipText("Ingrese un URL valido");
        GridBagConstraints gbc_textFieldSitioWeb = new GridBagConstraints();
        gbc_textFieldSitioWeb.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldSitioWeb.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldSitioWeb.gridx = 1;
        gbc_textFieldSitioWeb.gridy = 1;
        panelAerolinea.add(textFieldSitioWeb, gbc_textFieldSitioWeb); 
             
      //Panel para habilitar solo los datos extras del cliente
        panelCliente = new JPanel();
        panelCliente.setVisible(false);
        GridBagConstraints gbc_panelCliente = new GridBagConstraints();
        gbc_panelCliente.fill = GridBagConstraints.BOTH;
        gbc_panelCliente.insets = new Insets(0, 0, 5, 0);
        gbc_panelCliente.gridx = 0;
        gbc_panelCliente.gridy = 1;
        getContentPane().add(panelCliente, gbc_panelCliente);
        
        GridBagLayout gbl_panelCliente = new GridBagLayout();
        gbl_panelCliente.columnWidths = new int[]{150,350};
        gbl_panelCliente.rowHeights = new int[]{25,25,25,25,25};
        gbl_panelCliente.columnWeights = new double[]{0.0, 0.0};
        gbl_panelCliente.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0,0.0};
        panelCliente.setLayout(gbl_panelCliente);

        lblApellido = new JLabel("Apellido:");
        lblApellido.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblApellido = new GridBagConstraints();
        gbc_lblApellido.anchor = GridBagConstraints.EAST;
        gbc_lblApellido.fill = GridBagConstraints.VERTICAL;
        gbc_lblApellido.insets = new Insets(0, 0, 5, 5);
        gbc_lblApellido.gridx = 0;
        gbc_lblApellido.gridy = 0;
        panelCliente.add(lblApellido, gbc_lblApellido);
        
        textFieldApellido = new JTextField();
        textFieldApellido.setEditable(false);
        textFieldApellido.setColumns(10);
        GridBagConstraints gbc_textFieldApellido = new GridBagConstraints();
        gbc_textFieldApellido.anchor = GridBagConstraints.EAST;
        gbc_textFieldApellido.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldApellido.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldApellido.gridx = 1;
        gbc_textFieldApellido.gridy = 0;
        panelCliente.add(textFieldApellido, gbc_textFieldApellido);
        
        lblNacimento = new JLabel("Nacimiento:");
        lblNacimento.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblNacimeintoCliente = new GridBagConstraints();
        gbc_lblNacimeintoCliente.anchor = GridBagConstraints.EAST;
        gbc_lblNacimeintoCliente.fill = GridBagConstraints.VERTICAL;
        gbc_lblNacimeintoCliente.insets = new Insets(0, 0, 5, 5);
        gbc_lblNacimeintoCliente.gridx = 0;
        gbc_lblNacimeintoCliente.gridy = 1;
        panelCliente.add(lblNacimento, gbc_lblNacimeintoCliente);
        
      //Fecha editable con jcalendar
        textFieldNacimiento = new JDateChooser();
        textFieldNacimiento.setEnabled(false);
        textFieldNacimiento.setDateFormatString("dd/MM/yyyy");
        GridBagConstraints gbc_textFieldNacimiento = new GridBagConstraints();
        gbc_textFieldNacimiento.gridwidth = 1;
        gbc_textFieldNacimiento.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldNacimiento.fill = GridBagConstraints.BOTH;
        gbc_textFieldNacimiento.gridx = 1;
        gbc_textFieldNacimiento.gridy = 1;
        panelCliente.add(textFieldNacimiento, gbc_textFieldNacimiento);
        
        lblNacionalidad = new JLabel("Nacionalidad:");
        lblNacionalidad.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblNacionalidadCliente = new GridBagConstraints();
        gbc_lblNacionalidadCliente.anchor = GridBagConstraints.EAST;
        gbc_lblNacionalidadCliente.fill = GridBagConstraints.VERTICAL;
        gbc_lblNacionalidadCliente.insets = new Insets(0, 0, 5, 5);
        gbc_lblNacionalidadCliente.gridx = 0;
        gbc_lblNacionalidadCliente.gridy = 2;
        panelCliente.add(lblNacionalidad, gbc_lblNacionalidadCliente);
        
        textFieldNacionalidad = new JTextField();
        textFieldNacionalidad.setEditable(false);
        textFieldNacionalidad.setColumns(10);
        GridBagConstraints gbc_textFieldNacionalidadCliente = new GridBagConstraints();
        gbc_textFieldNacionalidadCliente.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldNacionalidadCliente.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldNacionalidadCliente.gridx = 1;
        gbc_textFieldNacionalidadCliente.gridy = 2;
        panelCliente.add(textFieldNacionalidad, gbc_textFieldNacionalidadCliente);
        
        lblTipoDoc = new JLabel("Tipo doc:");
        lblTipoDoc.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblTipoDocCliente = new GridBagConstraints();
        gbc_lblTipoDocCliente.anchor = GridBagConstraints.EAST;
        gbc_lblTipoDocCliente.fill = GridBagConstraints.VERTICAL;
        gbc_lblTipoDocCliente.insets = new Insets(0, 0, 5, 5);
        gbc_lblTipoDocCliente.gridx = 0;
        gbc_lblTipoDocCliente.gridy = 3;
        panelCliente.add(lblTipoDoc, gbc_lblTipoDocCliente);
        
        comboBoxTipoDoc = new JComboBox();
        comboBoxTipoDoc.setModel(new DefaultComboBoxModel());
        //comboBoxTipoDoc.setEditable(false);
        //comboBoxTipoDoc.setVisible(false);
        GridBagConstraints gbc_comboBoxTipoDoc = new GridBagConstraints();
        gbc_comboBoxTipoDoc.fill = GridBagConstraints.BOTH;
        gbc_comboBoxTipoDoc.insets = new Insets(0, 0, 5, 0);
        gbc_comboBoxTipoDoc.gridx = 1;
        gbc_comboBoxTipoDoc.gridy = 3;
        panelCliente.add(comboBoxTipoDoc, gbc_comboBoxTipoDoc);

        lblNumeroDoc = new JLabel("Numero Doc:");
        lblNumeroDoc.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblNumeroDocCliente = new GridBagConstraints();
        gbc_lblNumeroDocCliente.anchor = GridBagConstraints.EAST;
        gbc_lblNumeroDocCliente.fill = GridBagConstraints.VERTICAL;
        gbc_lblNumeroDocCliente.insets = new Insets(0, 0, 5, 5);
        gbc_lblNumeroDocCliente.gridx = 0;
        gbc_lblNumeroDocCliente.gridy = 4;
        panelCliente.add(lblNumeroDoc, gbc_lblNumeroDocCliente);
        
        textFieldNumeroDoc = new JTextField();
        textFieldNumeroDoc.setEditable(false);
        textFieldNumeroDoc.setColumns(10);
        GridBagConstraints gbc_textFieldNumeroDocCliente = new GridBagConstraints();
        gbc_textFieldNumeroDocCliente.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldNumeroDocCliente.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldNumeroDocCliente.gridx = 1;
        gbc_textFieldNumeroDocCliente.gridy = 4;
        panelCliente.add(textFieldNumeroDoc, gbc_textFieldNumeroDocCliente);

        label = new JLabel("");
        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.fill = GridBagConstraints.BOTH;
        gbc_label.insets = new Insets(0, 0, 5, 0);
        gbc_label.gridx = 0;
        gbc_label.gridy = 1;
        getContentPane().add(label, gbc_label);
        panel_1 = new JPanel();
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.insets = new Insets(0, 0, 5, 0);
        gbc_panel_1.fill = GridBagConstraints.BOTH;
        gbc_panel_1.gridx = 0;
        gbc_panel_1.gridy = 2;
        getContentPane().add(panel_1, gbc_panel_1);
        
        panel_1.setLayout(new BorderLayout(0, 0));
        panel = new JPanel();
        panel_1.add(panel, BorderLayout.EAST);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        btnAceptar = new JButton("Aceptar");
        panel.add(btnAceptar);
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	if (dtusr instanceof DTAerolinea) {
            		cmdModificarDatosAerolineaActionPerformed(arg0);
            	}else if (dtusr instanceof DTCliente) {
            		cmdModificarDatosClienteActionPerformed(arg0);
            	}

            }
        });
        btnEditar = new JButton("   Editar   ");
        panel.add(btnEditar);
        btnEditar.setEnabled(false);
        btnEditar.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
        	panelGenerico.setVisible(true);
        	textFieldNickName.setEditable(false);
        	textFieldEmail.setEditable(false);
        	textFieldNombre.setEditable(true);
        	if (dtusr instanceof DTAerolinea) {
            	textFieldDescripcion.setEditable(true);
            	textFieldSitioWeb.setEditable(true);
        	} else if (dtusr instanceof DTCliente) {
            	textFieldApellido.setEditable(true);
            	textFieldNacimiento.setEnabled(true);
            	textFieldNacionalidad.setEditable(true);
            	textFieldNumeroDoc.setEditable(true);
            	//comboBoxTipoDoc.setVisible(true);
        	}
        }
        });    
        btnCancelar = new JButton("Cancelar");
        panel.add(btnCancelar);
        btnCancelar.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	setVisible(false);
            panelCliente.setVisible(false);
            panelAerolinea.setVisible(false);
            textFieldNickName.setEditable(false);
        	textFieldNombre.setEditable(false);
        	textFieldEmail.setEditable(false);
        	textFieldDescripcion.setEditable(false);
        	textFieldSitioWeb.setEditable(false);
        	textFieldApellido.setEditable(false);
        	textFieldNacimiento.setEnabled(false);
        	textFieldNacionalidad.setEditable(false);
        	//comboBoxTipoDoc.setVisible(false);
        	textFieldNumeroDoc.setEditable(false);
        	
        	}
        });             
	}
	// Este método es invocado al querer registrar una ruta de vuelo, funcionalidad
    // provista por la operación del sistem registrarRutaDeVuelo().
    // Previamente se hace una verificación de los campos, particularmente que no sean vacíos
    // y que el nombre sea un string. 
    // Tanto en caso de que haya un error (de verificación o de registro) o no, se despliega
    // un mensaje utilizando un panel de mensaje (JOptionPane).
    protected void cmdModificarDatosAerolineaActionPerformed(ActionEvent arg0) {       
        if (checkFormularioUsuario() && checkFormularioAerolinea()) {
        	String nombre = this.textFieldNombre.getText();
        	String descripcion = this.textFieldDescripcion.getText();
            String sitioWeb = this.textFieldSitioWeb.getText();
            
            ManejadorUsuario mu = ManejadorUsuario.getInstance();
    		mu.editarDatosAereolinea(comboBoxUsuarios.getSelectedItem().toString(), nombre, dtusr.getPass(), dtusr.getImagen(), descripcion, sitioWeb);
            // Muestro éxito de la operación
            JOptionPane.showMessageDialog(this, "Los datos de la aerolinea se modificaron con éxito.", "Modificar datos de usuario",
                    JOptionPane.INFORMATION_MESSAGE);
            hide();
        }
    }
 // Este método es invocado al querer registrar una ruta de vuelo, funcionalidad
    // provista por la operación del sistem registrarRutaDeVuelo().
    // Previamente se hace una verificación de los campos, particularmente que no sean vacíos
    // y que el nombre sea un string. 
    // Tanto en caso de que haya un error (de verificación o de registro) o no, se despliega
    // un mensaje utilizando un panel de mensaje (JOptionPane).
    protected void cmdModificarDatosClienteActionPerformed(ActionEvent arg0) { 
    	if (checkFormularioUsuario() && checkFormularioCliente()) {
        	String nombre = this.textFieldNombre.getText();
        	String apellido = this.textFieldApellido.getText();
        	LocalDate nacimiento = textFieldNacimiento.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        	String nacionalidad = this.textFieldNacionalidad.getText();
            TipoDocumento tipoDoc = TipoDocumento.fromString(this.comboBoxTipoDoc.getSelectedItem().toString());
            String numeroDoc = this.textFieldNumeroDoc.getText();
            
            ManejadorUsuario mu = ManejadorUsuario.getInstance();
    		mu.editarDatosCliente(comboBoxUsuarios.getSelectedItem().toString(), nombre, apellido, dtusr.getPass(), dtusr.getImagen(), nacimiento, nacionalidad, tipoDoc,numeroDoc);

            // Muestro éxito de la operación
            JOptionPane.showMessageDialog(this, "Los datos del usuario se modificaron con éxito.", "Modificar datos de usuario",
                    JOptionPane.INFORMATION_MESSAGE);
            hide();
        }	 
    }
    // Permite validar la información introducida en los campos e indicar
    // a través de un mensaje de error (JOptionPane) cuando algo sucede.
    // Este tipo de chequeos se puede realizar de otras formas y con otras librerías de Java, 
    // por ejemplo impidiendo que se escriban caracteres no numéricos al momento de escribir en
    // en el campo de la cédula, o mostrando un mensaje de error apenas el foco pasa a otro campo.
    private boolean checkFormularioUsuario() {
    	String nombre = this.textFieldNombre.getText();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre de usuario no puede ser vacio.", "Modificar datos de usuario",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
	// Permite validar la información introducida en los campos e indicar
    // a través de un mensaje de error (JOptionPane) cuando algo sucede.
    // Este tipo de chequeos se puede realizar de otras formas y con otras librerías de Java, 
    // por ejemplo impidiendo que se escriban caracteres no numéricos al momento de escribir en
    // en el campo de la cédula, o mostrando un mensaje de error apenas el foco pasa a otro campo.
    private boolean checkFormularioAerolinea() {
        String descripcion = this.textFieldDescripcion.getText();
       
        String sitioWeb = this.textFieldSitioWeb.getText();
        String prefSitio1 = "http://";
        String prefSitio2 = "https://";
        String prefSitio3 = "www";

        if (descripcion.isEmpty() || sitioWeb.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No puede haber campos vacíos.", "Modificar datos de usuario",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if(sitioWeb.contains(prefSitio1) == false && sitioWeb.contains(prefSitio2) == false  && sitioWeb.contains(prefSitio3) == false ){
        	JOptionPane.showMessageDialog(this, "La pagina web no es correcta.", "Modificar datos de usuario",
                    JOptionPane.ERROR_MESSAGE);
        	return false;
        }

        return true;
    }
    // Permite validar la información introducida en los campos e indicar
    // a través de un mensaje de error (JOptionPane) cuando algo sucede.
    // Este tipo de chequeos se puede realizar de otras formas y con otras librerías de Java, 
    // por ejemplo impidiendo que se escriban caracteres no numéricos al momento de escribir en
    // en el campo de la cédula, o mostrando un mensaje de error apenas el foco pasa a otro campo.

    private boolean checkFormularioCliente() {
        String apellido = this.textFieldApellido.getText();
        String nacionalidad = this.textFieldNacionalidad.getText();
        String numeroDoc = this.textFieldNumeroDoc.getText();

        if (apellido.isEmpty() || nacionalidad.isEmpty()|| numeroDoc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No puede haber campos vacíos.", "Modificar datos de usuario",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
        	this.textFieldNacimiento.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "La fecha ingresada no es correcta, el formato debe ser dd/mm/yyyy.", "Modificar datos de usuario",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        /*try {
        	Integer.parseInt(this.textFieldNumeroDoc.getText());
        }catch (NumberFormatException e2){
        	JOptionPane.showMessageDialog(this, "El numero de documento debe ser un numero.", "Modificar datos de usuario",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }*/

        return true;
    } 
}