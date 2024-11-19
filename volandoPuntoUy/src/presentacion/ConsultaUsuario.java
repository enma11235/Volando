package presentacion;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.plaf.basic.BasicComboBoxEditor;

import excepciones.PaqueteNoExisteException;
import excepciones.ReservaNoExisteException;
import excepciones.RutaDeVueloNoExisteException;
import excepciones.UsuarioNoEsAerolineaExcepcion;
import excepciones.UsuarioNoExisteException;
import excepciones.VueloNoExisteException;
import logica.DTAerolinea;
import logica.DTCliente;
import logica.DTPaquete;
import logica.DTReserva;
import logica.DTUsuario;
import logica.DTVuelo;
import logica.IControladorPaquete;
import logica.IControladorRutaDeVuelo;
import logica.IControladorUsuario;
import logica.Paquete;
import logica.Reserva;
import logica.Paquete;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JPanel;



// JInternalFrame que permite consultar la información de un usuario del sistema.
@SuppressWarnings("serial")
public class ConsultaUsuario extends JInternalFrame {
	
	private JComboBox<String> comboUsuarios;
    private JComboBox<String> comboReservas;
    private JComboBox<String> comboPaquetes;
    private JComboBox<String> comboRutas;



	private IControladorUsuario contUsers;
	private IControladorRutaDeVuelo contRTV; 
	private IControladorPaquete contPaquete; 


	//Componentes para el los internalframes que muestran info de los usuarios

    //Usuario cliente
	
    private JTextField tfNickCliente;
    private JTextField tfNombreCliente;
    private JTextField tfEmailCliente;
    private JTextField tfApellido;
    private JTextField tfFNacimiento;
    private JTextField tfNacionalidad;
    private JTextField tfTDocumento;
    private JTextField tfNDocumento;
    
	//Usuario Aerolinea
	
    private JTextField tfNickAerolinea;
    private JTextField tfNombreAerolinea;
    private JTextField tfEmailAerolinea;
    private JTextField tfDescripcion;
    private JTextField tfSitioWeb;
    
    List<String> aerolineas = null;
    List<String> clientes = null;
    
    public ConsultaUsuario(IControladorUsuario icu, IControladorPaquete icp, IControladorRutaDeVuelo icrv) {
        // Se inicializa con el controlador de usuarios
    	
    	contUsers = icu; 
    	contRTV = icrv; 
    	contPaquete = icp; 

        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setTitle("Consultar un Usuario");
        setBounds(30, 30, 330, 180);
        getContentPane().setLayout(null);       
        
        JPanel panelCliente = new JPanel();
        panelCliente.setBounds(10, 91, 304, 387);
        getContentPane().add(panelCliente);
        panelCliente.setLayout(null);
        panelCliente.setVisible(false);       
        
        JPanel panelAerolinea = new JPanel();
        panelAerolinea.setBounds(10, 91, 304, 262);
        getContentPane().add(panelAerolinea);
		panelAerolinea.setLayout(null);
		panelAerolinea.setVisible(false);

		//Combo de usuarios
		comboUsuarios = new JComboBox<String>();
		comboUsuarios.setBounds(149, 49, 153, 22);
		getContentPane().add(comboUsuarios);
		
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
					aerolineas = icu.listarAerolineas();
				} catch (UsuarioNoExisteException ex) {
				}
				try {
					clientes = contUsers.listarClientes();
				} catch (UsuarioNoExisteException e1) {				
				}
				
				if(aerolineas == null && clientes == null) {
					JOptionPane.showMessageDialog(e.getComponent(),"No existen usuarios");
					hide();
				}
			}
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				//ConsultaVueloGUI cvg = (ConsultaVueloGUI)e.getComponent();
				//cvg.hideAll();
			}
			
		});
		
		JLabel lblUsuariosRegistrados = new JLabel("Usuarios registrados");
		lblUsuariosRegistrados.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuariosRegistrados.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblUsuariosRegistrados.setBounds(76, 11, 161, 14);
		getContentPane().add(lblUsuariosRegistrados);
		
		JLabel lblSelecccion = new JLabel("Seleccione un usuario"); 
		lblSelecccion.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSelecccion.setBounds(10, 52, 131, 14);
		getContentPane().add(lblSelecccion);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setBounds(112, 114, 87, 22);
		getContentPane().add(btnCerrar);
        
        
		//Datos de un usuario cliente
        
		JLabel lblNewLabel = new JLabel("Nickname");		 
        lblNewLabel.setBounds(14, 10, 90, 14);
        panelCliente.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Nombre");
        lblNewLabel_1.setBounds(14, 40, 90, 14);
        panelCliente.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Email");
        lblNewLabel_2.setBounds(14, 70, 90, 14);
        panelCliente.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Apellido");
        lblNewLabel_3.setBounds(14, 100, 90, 14);
        panelCliente.add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("Fecha nacimiento");
        lblNewLabel_4.setBounds(14, 130, 105, 14);
        panelCliente.add(lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel("Nacionalidad");
        lblNewLabel_5.setBounds(14, 160, 90, 14);
        panelCliente.add(lblNewLabel_5);

        JLabel lblNewLabel_6 = new JLabel("Tipo de documento");
        lblNewLabel_6.setBounds(14, 190, 116, 14);
        panelCliente.add(lblNewLabel_6);

        JLabel lblNewLabel_7 = new JLabel("N° documento");
        lblNewLabel_7.setBounds(14, 220, 90, 14);
        panelCliente.add(lblNewLabel_7);

        tfNickCliente = new JTextField();
        tfNickCliente.setEditable(false);
        tfNickCliente.setBounds(140, 7, 150, 20);
        tfNickCliente.setColumns(10);
        panelCliente.add(tfNickCliente);
        tfNickCliente.setCaretPosition(0);
	  

        tfNombreCliente = new JTextField();
        tfNombreCliente.setEditable(false);
        tfNombreCliente.setColumns(10);
        tfNombreCliente.setBounds(140, 37, 150, 20);
        panelCliente.add(tfNombreCliente);

        tfEmailCliente = new JTextField();
        tfEmailCliente.setEditable(false);
        tfEmailCliente.setColumns(10);
        tfEmailCliente.setBounds(140, 67, 150, 20);
        panelCliente.add(tfEmailCliente);
        tfEmailCliente.setCaretPosition(0);

        tfApellido = new JTextField();
        tfApellido.setEditable(false);
        tfApellido.setColumns(10);
        tfApellido.setBounds(140, 97, 150, 20);
        panelCliente.add(tfApellido);

        tfFNacimiento = new JTextField();
        tfFNacimiento.setEditable(false);
        tfFNacimiento.setColumns(10);
        tfFNacimiento.setBounds(140, 127, 150, 20);
        panelCliente.add(tfFNacimiento);

        tfNacionalidad = new JTextField();
        tfNacionalidad.setEditable(false);
        tfNacionalidad.setColumns(10);
        tfNacionalidad.setBounds(140, 157, 150, 20);
        panelCliente.add(tfNacionalidad);

        tfTDocumento = new JTextField();
        tfTDocumento.setEditable(false);
        tfTDocumento.setColumns(10);
        tfTDocumento.setBounds(140, 187, 150, 20);
        panelCliente.add(tfTDocumento);

        tfNDocumento = new JTextField();
        tfNDocumento.setEditable(false);
        tfNDocumento.setColumns(10);
        tfNDocumento.setBounds(140, 217, 150, 20);
        panelCliente.add(tfNDocumento);

        JLabel lblReservas = new JLabel("Reservas del cliente");
        lblReservas.setBounds(14, 256, 115, 14);
        panelCliente.add(lblReservas);

        //Combo de reservas
        comboReservas = new JComboBox<String>();
        comboReservas.setBounds(140, 253, 150, 20);
        panelCliente.add(comboReservas);
              
        JLabel lblPaquetes = new JLabel("Paquetes del cliente");
        lblPaquetes.setBounds(14, 297, 115, 14);
        panelCliente.add(lblPaquetes);
        
        //Combo de paquetes
        comboPaquetes = new JComboBox<String>();
        comboPaquetes.setBounds(140, 294, 150, 20);
        panelCliente.add(comboPaquetes);
        
        JButton btnCancelarCliente = new JButton("CANCELAR");
        btnCancelarCliente.setBounds(109, 352, 100, 23);
        panelCliente.add(btnCancelarCliente);  
          
        //Datos de un usuario aerolinea
		JLabel lblNName = new JLabel("Nickname");
	    lblNName.setBounds(14, 10, 90, 14);
	    panelAerolinea.add(lblNName);
	    
	    JLabel lblNombre = new JLabel("Nombre");
	    lblNombre.setBounds(14, 40, 90, 14);
	    panelAerolinea.add(lblNombre);

	    JLabel lblEmail = new JLabel("Email");
	    lblEmail.setBounds(14, 70, 90, 14);
	    panelAerolinea.add(lblEmail);

	    JLabel lblDescripcion = new JLabel("Descripcion");
	    lblDescripcion.setBounds(14, 100, 90, 14);
	    panelAerolinea.add(lblDescripcion);
	    
	    JLabel lblSitioWeb = new JLabel("Sitio web");
	    lblSitioWeb.setBounds(14, 130, 90, 14);
	    panelAerolinea.add(lblSitioWeb);

	    tfNickAerolinea = new JTextField(); 
	    tfNickAerolinea.setEditable(false);
	    tfNickAerolinea.setColumns(10);
	    tfNickAerolinea.setBounds(140, 37, 150, 20);
	    panelAerolinea.add(tfNickAerolinea);
	    tfNickAerolinea.setCaretPosition(0);
	    //Para descripciones largas
        tfNickAerolinea.setToolTipText("");

	    tfNombreAerolinea = new JTextField();
	    tfNombreAerolinea.setEditable(false);
	    tfNombreAerolinea.setColumns(10);
	    tfNombreAerolinea.setBounds(140, 5, 150, 20);
	    panelAerolinea.add(tfNombreAerolinea);

	    tfEmailAerolinea = new JTextField();
	    tfEmailAerolinea.setEditable(false);
	    tfEmailAerolinea.setColumns(10);
	    tfEmailAerolinea.setBounds(140, 67, 150, 20);
	    panelAerolinea.add(tfEmailAerolinea);
	    tfEmailAerolinea.setCaretPosition(0);
	    //Para emails largos
        tfEmailAerolinea.setToolTipText("");

	    tfDescripcion = new JTextField("");
	    tfDescripcion.setEditable(false);
	    tfDescripcion.setColumns(10);
	    tfDescripcion.setBounds(140, 97, 150, 20);
	    panelAerolinea.add(tfDescripcion);
        tfDescripcion.setCaretPosition(0);
	    //Para descripciones largas
        tfDescripcion.setToolTipText("");

	    tfSitioWeb = new JTextField();
	    tfSitioWeb.setEditable(false);
	    tfSitioWeb.setColumns(10);
	    tfSitioWeb.setBounds(140, 127, 150, 20);
	    panelAerolinea.add(tfSitioWeb);
	    tfSitioWeb.setCaretPosition(0);
	    //Para sitio webs largos
        tfSitioWeb.setToolTipText("");
	    
	    //Combo de rutas
	    comboRutas = new JComboBox<String>();
	    comboRutas.setBounds(140, 163, 150, 20);
	    panelAerolinea.add(comboRutas);

	    JLabel lblRutas = new JLabel("Rutas de la aerolinea");
	    lblRutas.setBounds(14, 166, 128, 14);
	    panelAerolinea.add(lblRutas);
	    
	    JButton btnCancelarAerolinea = new JButton("CANCELAR");
        btnCancelarAerolinea.setBounds(96, 228, 98, 23);
	    panelAerolinea.add(btnCancelarAerolinea);     
       
        //Eventos 
        
        //Cerramos consultaUsuario desde boton "Cerrar" 
        btnCerrar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		setVisible(false); //Ocultamos la ventana del caso de uso
        		
        		//Limpiamos info del Cliente y ocultamos el panel
        		limpiarFormularioCliente();
        		panelCliente.setVisible(false);
        		//Limpiamos info de la aerolinea y ocultamos el panel
        		limpiarFormularioAerolinea();
        		panelAerolinea.setVisible(false);
        	}
        });
        
        //Cerramos panel info cliente
        btnCancelarCliente.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		limpiarFormularioCliente();
        		
        		//Hacemos todo invisible y redimensionamos la ventana prinicpal al tamaño original
        		setBounds(getX(), getY(), 328, 175);
        		panelCliente.setVisible(false);
        		btnCerrar.setEnabled(true); 
                comboUsuarios.setSelectedIndex(0);  
        	}
        });
        
        //Cerramos panel info aerolinea
        btnCancelarAerolinea.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		limpiarFormularioAerolinea();
        		
        		//Todos los componentes se hacen invisibels y se redimensiona el cuadro principal a su tamaño original	           
        		setBounds(getX(), getY(), 328, 175);
        		panelAerolinea.setVisible(false);
        		btnCerrar.setEnabled(true); 
                comboUsuarios.setSelectedIndex(0);          		
        	}
        });
        
        
        // Seleccionar un usuario
        comboUsuarios.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		String selectedItem = (String) comboUsuarios.getSelectedItem();
        		
				try {
					
					//clientes = contUsers.listarClientes();
					if (selectedItem != null && selectedItem != "     ----- Usuarios -----     " && clientes != null && clientes.contains(selectedItem)) {
	        			// Agrandamos el frame principal para que se vea el de info cliente	        			
						DTCliente datosCliente = (DTCliente) contUsers.obtenerInfoUsuario(selectedItem);
					
					    tfNickCliente.setText(datosCliente.getNickname());	
					    //Para nicknames largos
					    tfNickCliente.setCaretPosition(0);
				        tfNickCliente.setToolTipText(datosCliente.getNickname());
				        
					    tfNombreCliente.setText(datosCliente.getNombre());					
					    tfEmailCliente.setText(datosCliente.getEmail());	
					    //Para emails largos
					    tfEmailCliente.setCaretPosition(0);
				        tfEmailCliente.setToolTipText(datosCliente.getEmail());
				        
					    tfApellido.setText(datosCliente.getApellido());					
					    LocalDate fechaNacimiento = datosCliente.getNacimiento();
						// Cambiamos formato de la fecha
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
						String fechaFormateada = fechaNacimiento.format(formatter);	
						tfFNacimiento.setText(fechaFormateada);
						
					    tfNacionalidad.setText(datosCliente.getNacionalidad());					
					    tfTDocumento.setText(datosCliente.getTipoDocumento().toString());					
					    tfNDocumento.setText(datosCliente.getNumDocumento());										    
					    
					    cargarReservasCliente(datosCliente.getNickname()); 
					    cargarPaquetesCliente(datosCliente.getNickname());
					    
						setBounds(getX(), getY(), 336, 532);
	        			panelCliente.setVisible(true);
	        			panelAerolinea.setVisible(false);
	        			btnCerrar.setEnabled(false); 
	        		}
				} catch (UsuarioNoExisteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
               
				try {
					//aerolineas = icu.listarAerolineas();
					if(selectedItem != null && selectedItem != "     ----- Usuarios -----     " && aerolineas!= null && aerolineas.contains(selectedItem)) {
	        			// Agrandamos el frame principal para que se vea el de info aerolinea
						DTAerolinea datosAerolinea = (DTAerolinea) contUsers.obtenerInfoUsuario(selectedItem);
						    
						tfNickAerolinea.setText(datosAerolinea.getNickname());	
					    //Para nicknames largos
						tfNickAerolinea.setCaretPosition(0);
						tfNickAerolinea.setToolTipText(datosAerolinea.getNickname());
				        
						tfNombreAerolinea.setText(datosAerolinea.getNombre());					
						tfEmailAerolinea.setText(datosAerolinea.getEmail());	
					    //Para emails largos
						tfEmailAerolinea.setCaretPosition(0);
						tfEmailAerolinea.setToolTipText(datosAerolinea.getEmail());
				        
						tfDescripcion.setText(datosAerolinea.getDescripcion());
						tfDescripcion.setCaretPosition(0);
						tfDescripcion.setToolTipText(datosAerolinea.getDescripcion());
						
						tfSitioWeb.setText(datosAerolinea.getSitioWeb());					
						tfSitioWeb.setCaretPosition(0);
						tfSitioWeb.setToolTipText(datosAerolinea.getSitioWeb());

						cargarRutasAerolinea(datosAerolinea.getNickname());
	        			setBounds(getX(), getY(), 336, 381);
	        			panelAerolinea.setVisible(true);
	        			panelCliente.setVisible(false);
	        			btnCerrar.setEnabled(false); 
	        		}
				} catch (UsuarioNoExisteException e1) {
					
				}
        	}
        });
        
     // Nuevo!! Andres - Seleccionar una ruta de vuelo
        comboRutas.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		String selectedItem = (String) comboRutas.getSelectedItem();
        		List<String> clientes;
				if (selectedItem != null&& !selectedItem.equals("     ----- Rutas -----     ")) {
					MenuPrincipal.getVentanaRuta().setVisible(true);
					MenuPrincipal.getVentanaRuta().toFront();
					MenuPrincipal.getVentanaRuta().setAerolineaSeleccionada((String) comboUsuarios.getSelectedItem());
					MenuPrincipal.getVentanaRuta().setRutaSeleccionada((String) comboRutas.getSelectedItem());
					comboRutas.setEnabled(false); // Deshabilitar el comboBox mientras la ventana esté abierta
					}

				MenuPrincipal.getVentanaRuta().addInternalFrameListener(new InternalFrameAdapter() {
				    @Override
				    public void internalFrameClosed(InternalFrameEvent e) {
				        comboRutas.setEnabled(true); // Rehabilita el comboBox cuando la ventana se cierre
				    }
				});
        	}
        });
        
        // seleccionar paquete
        comboPaquetes.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		String selectedItem = (String) comboPaquetes.getSelectedItem();
				if (selectedItem != null && !selectedItem.equals("     ----- Paquetes -----     ") ) {
				
					MenuPrincipal.getVentanaPaquete().setVisible(true);
					MenuPrincipal.getVentanaPaquete().toFront();
					MenuPrincipal.getVentanaPaquete().setNomPaquete((String) comboPaquetes.getSelectedItem());
					comboPaquetes.setEnabled(false);
				} 
					
					MenuPrincipal.getVentanaPaquete().addInternalFrameListener(new InternalFrameAdapter() {
					    @Override
					    public void internalFrameClosed(InternalFrameEvent e) {
					        comboPaquetes.setEnabled(true); // Rehabilitar el comboBox cuando la ventana se cierre
					    }		 
					});
			}       	
        });
        
        //eleccionar reserva
        comboReservas.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		String nomVuelo = (String) comboReservas.getSelectedItem();
        		
				if (nomVuelo != null && !nomVuelo.equals("     ----- Reservas -----     ") ) {
					
					try {
						String nomRutaDeVuelo = contRTV.obtenerRutaDeVuelo(nomVuelo);
						String nomAerolinea = contRTV.obtenerNicknameAerolineaDeRuta(nomRutaDeVuelo);
						
						MenuPrincipal.getVentanaVuelos().setVisible(true);
						MenuPrincipal.getVentanaVuelos().toFront();
						MenuPrincipal.getVentanaVuelos().setAerolineaSeleccionada(nomAerolinea);
						MenuPrincipal.getVentanaVuelos().setRutaSeleccionada(nomRutaDeVuelo);
						MenuPrincipal.getVentanaVuelos().setVueloSeleccionado(nomVuelo);
						
						comboReservas.setEnabled(false);
					} catch (VueloNoExisteException e1) {
						// TODO Auto-generated catch block
					}
				} 
					
					MenuPrincipal.getVentanaVuelos().addInternalFrameListener(new InternalFrameAdapter() {
					    @Override
					    public void internalFrameClosed(InternalFrameEvent e) {
					        comboReservas.setEnabled(true); // Rehabilitar el comboBox cuando la ventana se cierre
					    }		 
					});        	}
        	
        });
        
        //Cerramos consultaUsuario desde boton "x" 
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
            	setVisible(false);
        		limpiarFormularioCliente();
        		panelCliente.setVisible(false);
        		limpiarFormularioAerolinea();
        		panelAerolinea.setVisible(false);   
        		setBounds(getX(), getY(), 328, 175);
                comboUsuarios.setSelectedIndex(0);  
        		btnCerrar.setEnabled(true); 
        		}
        });
    }

  
    public void cargarUsuarios() {
    	
    	DefaultComboBoxModel<String> model;

    	try {
    		    List<String> usuarios = contUsers.listarUsuariosNick(); // Obtener la lista de usuarios
    		    String[] usuariosArray = usuarios.toArray(new String[0]); // Convertimos la lista en un array para setear el modelo
    		    
    		    model = new DefaultComboBoxModel<String>();
    		    model.addElement("     ----- Usuarios -----     "); // Primer item
    		    
    		    // Agregamos los usuarios restantes al modelo
    		    for (String usuario : usuariosArray) {
    		        model.addElement(usuario);
    		    }    		    
    		    comboUsuarios.setModel(model);
    		   
    	} catch (UsuarioNoExisteException e) {
    		// No se imprime mensaje de error sino que simplemente no se muestra ningún elemento
    	}   	
    }
   
    private void cargarReservasCliente(String nomCliente) {   
		
    	DefaultComboBoxModel<String> model;

    	try {
    		    List<DTReserva> reservas = contUsers.listarReservasCliente(nomCliente); // Obtener la lista de usuarios
    		    DTReserva[] reservasArray = reservas.toArray(new DTReserva[0]); // Convertimos la lista en un array para setear el modelo

    		    model = new DefaultComboBoxModel<String>();
    		    model.addElement("     ----- Reservas -----     "); // Primer item
    		    
    		    // Agregamos las reservas restantes al modelo
    		    for (DTReserva r : reservasArray) {
    		        model.addElement(r.getNomVuelo());
    		    }    		    
    		    comboReservas.setModel(model);
    		   
    	} catch (ReservaNoExisteException | UsuarioNoExisteException e) { 
		        model = new DefaultComboBoxModel<String>();
		        model.addElement("     ----- Reservas -----     ");
		        comboReservas.setModel(model);
		}   	
    }
    
    private void cargarPaquetesCliente(String nomCliente) {  
    	DefaultComboBoxModel<String> model;

    	try {
    		    List<DTPaquete> Paquetes = contUsers.listarPaquetesCompradosCliente(nomCliente); 
    		    DTPaquete[] paquetesArray = Paquetes.toArray(new DTPaquete[0]); // Convertimos la lista en un array para setear el modelo
    		    model = new DefaultComboBoxModel<String>();
    		    model.addElement("     ----- Paquetes -----     "); // Primer item
    		    
    		    // Agregamos las reservas restantes al modelo
    		    for (DTPaquete p : paquetesArray) {
    		        model.addElement(p.getNombre());
    		    }    		    
    		    comboPaquetes.setModel(model);
		} catch (UsuarioNoExisteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PaqueteNoExisteException e) {
			 JOptionPane.showMessageDialog(this, "El cliente no tiene paquetes asociados.", "Paquetes del Cliente", JOptionPane.INFORMATION_MESSAGE);
		        model = new DefaultComboBoxModel<String>();
		        model.addElement("     ----- Paquetes -----     ");
		        comboPaquetes.setModel(model);		
		}    	    	
    }
   	 
    //Nuevo! : Andres
    public void cargarRutasAerolinea(String nickAerolinea) {
    	DefaultComboBoxModel<String> model;

    	try {
    		    List<String> rutas = contRTV.listarRutasDeVuelo(nickAerolinea); // Obtener la lista de usuarios
    		    String[] rutasArray = rutas.toArray(new String[0]); // Convertimos la lista en un array para setear el modelo
    		    
    		    model = new DefaultComboBoxModel<String>();
    		    model.addElement("     ----- Rutas -----     "); // Primer item
    		    
    		    // Agregamos los usuarios restantes al modelo
    		    for (String ruta : rutasArray) {
    		        model.addElement(ruta);
    		    }    		    
    		    comboRutas.setModel(model);
    		   
    	} catch (UsuarioNoEsAerolineaExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
    }
    

    
    private void limpiarFormularioCliente() {

	    tfNickCliente.setText("");
	    tfNombreCliente.setText("");
	    tfEmailCliente.setText("");
	    tfApellido.setText("");
	    tfFNacimiento.setText("");
	    tfNacionalidad.setText("");
	    tfTDocumento.setText("");
	    tfNDocumento.setText("");
    }
    private void limpiarFormularioAerolinea() {

	    tfNickAerolinea.setText("");
	    tfNombreAerolinea.setText("");
	    tfEmailAerolinea.setText("");
	    tfApellido.setText("");
	    tfFNacimiento.setText("");
	    tfNacionalidad.setText("");
	    tfTDocumento.setText("");
	    tfNDocumento.setText("");
    }
}