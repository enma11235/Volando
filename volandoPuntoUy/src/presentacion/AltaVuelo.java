package presentacion;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

import logica.IControladorRutaDeVuelo;
import logica.IControladorUsuario;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;

import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;

import excepciones.UsuarioNoEsAerolineaExcepcion;
import excepciones.UsuarioNoExisteException;
import excepciones.VueloRepetidoException;

/*
   JInternalFrame que permite dar de alta un vuelo en el sistema.
 */

@SuppressWarnings("serial")
public class AltaVuelo extends JInternalFrame {
	private JTextField tfNombre;
	private JTextField tfDuracion;
	private JTextField tfATuristas;
	private JTextField tfAEjecutivos;
	private JComboBox comboAerolineas;
	private JComboBox comboRutas; 
	private JDateChooser dateChooserFechaVuelo; 
	private JDateChooser dateChooserFechaAlta; 

	private IControladorUsuario contUsers;
    private IControladorRutaDeVuelo controlRV;


	public AltaVuelo(IControladorRutaDeVuelo icrv, IControladorUsuario icu){
		
		contUsers = icu; 
		controlRV = icrv; 
		

	        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	        setTitle("Alta de Vuelo");
	        setBounds(30, 30, 325, 404);
		  
	        getContentPane().setLayout(null);
	        
	        JLabel lblNewLabel = new JLabel("Aerolinea");
	        lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
	        lblNewLabel.setBounds(10, 10, 113, 14);
	        getContentPane().add(lblNewLabel);
	        
	        comboAerolineas = new JComboBox();
	        comboAerolineas.setBounds(144, 7, 152, 22);
	        getContentPane().add(comboAerolineas);
	        
	        JLabel lblNewLabel_1_1 = new JLabel("Ruta");
	        lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
	        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
	        lblNewLabel_1_1.setBounds(10, 50, 113, 14);
	        getContentPane().add(lblNewLabel_1_1);
	        
	        comboRutas = new JComboBox();
	        comboRutas.setBounds(144, 47, 152, 22);
	        getContentPane().add(comboRutas);
	        
	        JLabel lblNewLabel_1 = new JLabel("Nombre");
	        lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
	        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
	        lblNewLabel_1.setBounds(10, 90, 113, 14);
	        getContentPane().add(lblNewLabel_1);
	        
	        JLabel lblNewLabel_1_2 = new JLabel("Fecha del vuelo");
	        lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.RIGHT);
	        lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
	        lblNewLabel_1_2.setBounds(10, 130, 113, 14);
	        getContentPane().add(lblNewLabel_1_2);
	        
	        JLabel lblNewLabel_1_2_1 = new JLabel("Duracion");
	        lblNewLabel_1_2_1.setHorizontalAlignment(SwingConstants.RIGHT);
	        lblNewLabel_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
	        lblNewLabel_1_2_1.setBounds(10, 170, 113, 14);
	        getContentPane().add(lblNewLabel_1_2_1);
	        
	        JLabel lblNewLabel_1_2_2 = new JLabel("Asientos turistas");
	        lblNewLabel_1_2_2.setHorizontalAlignment(SwingConstants.RIGHT);
	        lblNewLabel_1_2_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
	        lblNewLabel_1_2_2.setBounds(10, 210, 113, 14);
	        getContentPane().add(lblNewLabel_1_2_2);
	        
	        JLabel lblNewLabel_1_2_2_1 = new JLabel("Asientos ejecutivos");
	        lblNewLabel_1_2_2_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
	        lblNewLabel_1_2_2_1.setHorizontalAlignment(SwingConstants.RIGHT);
	        lblNewLabel_1_2_2_1.setBounds(10, 250, 113, 14);
	        getContentPane().add(lblNewLabel_1_2_2_1);
	        
	        JLabel lblNewLabel_1_2_2_2 = new JLabel("Fecha de alta");
	        lblNewLabel_1_2_2_2.setHorizontalAlignment(SwingConstants.RIGHT);
	        lblNewLabel_1_2_2_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
	        lblNewLabel_1_2_2_2.setBounds(10, 290, 113, 14);
	        getContentPane().add(lblNewLabel_1_2_2_2);
	        	        
	        tfNombre = new JTextField();
	        tfNombre.setBounds(144, 88, 152, 20);
	        getContentPane().add(tfNombre);
	        tfNombre.setColumns(10);
	      
	        tfDuracion = new JTextField();
	        tfDuracion.setColumns(10);
	        tfDuracion.setBounds(144, 168, 152, 20);
	        getContentPane().add(tfDuracion);
	        tfDuracion.setToolTipText("Formato hh:mm");
	        
	        tfATuristas = new JTextField();
	        tfATuristas.setColumns(10);
	        tfATuristas.setBounds(144, 208, 152, 20);
	        getContentPane().add(tfATuristas);
	        
	        tfAEjecutivos = new JTextField();
	        tfAEjecutivos.setColumns(10);
	        tfAEjecutivos.setBounds(144, 248, 152, 20);
	        getContentPane().add(tfAEjecutivos);	               
	        
	        JButton btnNewButton = new JButton("Aceptar");
	        btnNewButton.setBounds(49, 340, 89, 23);
	        getContentPane().add(btnNewButton);
	        
	        JButton btnCancelar = new JButton("Cancelar");
	        btnCancelar.setBounds(172, 340, 89, 23);
	        getContentPane().add(btnCancelar);
	        
	        dateChooserFechaVuelo = new JDateChooser();
	        dateChooserFechaVuelo.setBounds(144, 127, 152, 20);
	        getContentPane().add(dateChooserFechaVuelo);	        
	        // Cambiamos formato en el cual se muestra la fecha
	        dateChooserFechaVuelo.setDateFormatString("dd/MM/yyyy");	       
	        
	        dateChooserFechaAlta = new JDateChooser();
	        dateChooserFechaAlta.setBounds(144, 287, 152, 20);
	        dateChooserFechaAlta.setDateFormatString("dd/MM/yyyy");	       
	        getContentPane().add(dateChooserFechaAlta);
	        //Por defecto mostramos la fecha actual 
	        dateChooserFechaAlta.setDate(new Date());
	        
	        
	        //Eventos
	        
	        //Damos de alta el vuelo
	        btnNewButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent arg0) {
	        		cmdRegistroUsuarioActionPerformed(arg0);
	        	}
	        });
	        
	        //Cancelamos el alta del vuelo
	        btnCancelar.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		comboAerolineas.removeAllItems();
	        		comboRutas.removeAllItems();
	        		setVisible(false);  
                    dateChooserFechaAlta.setDate(null); //Limpiamos fechas
                    dateChooserFechaVuelo.setDate(null); 
	        		limpiarFormulario();	        		        	
	        	}
	        });
	        
	     // Seleccionar una aerolinea
	        comboAerolineas.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		String selectedItem = (String) comboAerolineas.getSelectedItem();
					if (selectedItem != null && selectedItem != "----- Aerolineas -----") {							
						cargarRutas(selectedItem); 						
					}
	        	}
	        });
	        
	        //Restricciones de las fechas: 
	        //Si elegimos una fecha de vuelo un dia X, la fecha de alta tiene que ser menor o igual a esa fecha. 
	        //Si elegimos una fecha de alta X, la fecha de vuelo tiene que ser mayor o igual a esa fecha. 

	        // Listener para la fecha de vuelo
	        dateChooserFechaVuelo.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
	            @Override
	            public void propertyChange(PropertyChangeEvent evt) {
	                if ("date".equals(evt.getPropertyName())) {
	                    Date fechaVuelo = dateChooserFechaVuelo.getDate();//los dateChooser devuelven fechas en formato Date
	                    Date fechaAlta = dateChooserFechaAlta.getDate();
	                    
	                    if (fechaVuelo != null && fechaAlta != null) {
	                        // Si la fecha de alta es mayor que la fecha de vuelo, mostrar mensaje de error
	                        if (fechaAlta.after(fechaVuelo)) {
	                            JOptionPane.showMessageDialog(AltaVuelo.this, "La fecha de alta no puede ser mayor a la fecha de vuelo.");
	                            dateChooserFechaAlta.setDate(null); // Limpiar la fecha de alta
	                        }
	                    }
	                }
	            }
	        });

	        // Listener para la fecha de alta
	        dateChooserFechaAlta.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
	            @Override
	            public void propertyChange(PropertyChangeEvent evt) {
	                if ("date".equals(evt.getPropertyName())) {
	                    Date fechaVuelo = dateChooserFechaVuelo.getDate();
	                    Date fechaAlta = dateChooserFechaAlta.getDate();
	                    
	                    if (fechaVuelo != null && fechaAlta != null) {
	                        // Si la fecha de vuelo es menor que la fecha de alta, mostrar mensaje de error
	                        if (fechaVuelo.before(fechaAlta)) {
	                            JOptionPane.showMessageDialog(AltaVuelo.this, "La fecha de vuelo debe ser mayor a la fecha de alta.");
	                            dateChooserFechaVuelo.setDate(null); // Limpiar la fecha de vuelo
	                        }
	                    }
	                }
	            }
	        });
	}
	
	
	protected void cmdRegistroUsuarioActionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stu
		    
        // Obtengo datos de los controles Swing
		String nomRuta = (String)comboRutas.getSelectedItem(); 
	    Date fvuelo = dateChooserFechaVuelo.getDate();//Seleccion del JCalendar, devuelve Date
	    LocalDate fechaVuelo = null;
	    if (fvuelo != null) {
	            // Convierte Date a LocalDate
	            fechaVuelo = fvuelo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    }
	    else {
	    	 JOptionPane.showMessageDialog(this, "Ingresar una fecha de vuelo valida.", "Alta de Vuelo",
                     JOptionPane.INFORMATION_MESSAGE);
	    }
 
        String nombreVuelo = this.tfNombre.getText();
        
        String duracion = this.tfDuracion.getText().trim();
        Duration dur = Duration.ZERO; 
        String regex =  "^\\d+(:[0-5]\\d){0,2}$";
        
        if (duracion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese la duración del vuelo en el formato hh:mm .", "Campo Vacío",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        else {
        	if (!duracion.matches(regex)) {
                JOptionPane.showMessageDialog(this, "El formato de duración es incorrecto. Debe ser hh, hh:mm .", "Formato Incorrecto",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
	        String[] partes = duracion.split(":");
	        int horas = 0, minutos = 0;
	        try {
	            // Asignar horas
	            horas = Integer.parseInt(partes[0]);

	            // Asignar minutos (si se ingresan)
	            if (partes.length >= 2) {
	                minutos = Integer.parseInt(partes[1]); 
	            }


	            // Calcular la duración
	            dur = Duration.ofHours(horas).plusMinutes(minutos);
	        } catch (NumberFormatException e) {
	            JOptionPane.showMessageDialog(this, "La duración debe ser en formato numérico.", "Formato Incorrecto",
	                    JOptionPane.ERROR_MESSAGE);
	        }
        }
        
        int asientosTuristas = 0; 
        int asientosEjecutivos = 0; 
        if (tfATuristas.getText().isEmpty() || tfAEjecutivos.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el número de asientos turistas y ejecutivos.", "Campo Vacío",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        else {
        	try {
        		asientosTuristas = Integer.parseInt(this.tfATuristas.getText());
   	         	asientosEjecutivos = Integer.parseInt(this.tfAEjecutivos.getText());
            } 
        	catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "La cantidad de asientos turistas y cantidad de asientos ejecutivos deben ser un número.", "Formato Incorrecto",
                        JOptionPane.ERROR_MESSAGE);
                return; 
            }
	      
        }
        
        Date fAlta = dateChooserFechaAlta.getDate();//Seleccion del JCalendar, devuelve Date
        LocalDate fechaAlta = null; 
        
	    if (fAlta != null) { 
	            // Convierte Date a LocalDate 
	            fechaAlta = fAlta.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    }
	    else {
	    	 JOptionPane.showMessageDialog(this, "Ingresar una fecha de alta vuelo.", "Alta de Vuelo",
                     JOptionPane.INFORMATION_MESSAGE);
	    }

        if (checkFormulario()) {
            try {
            	controlRV.altaVuelo(nomRuta, nombreVuelo, fechaVuelo, dur, asientosTuristas, asientosEjecutivos, fechaAlta, "");

                // Muestro exito de la operación
                JOptionPane.showMessageDialog(this, "El Vuelo se ha creado con éxito", "Alta de Vuelo",
                        JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);  
                comboAerolineas.removeAllItems();
        		comboRutas.removeAllItems();
                dateChooserFechaAlta.setDate(null); //Limpiamos fechas
                dateChooserFechaVuelo.setDate(null); 
        	
                limpiarFormulario(); 
            } catch (VueloRepetidoException e) {
                // Muestro error de registro
                JOptionPane.showMessageDialog(this, e.getMessage(), "Alta de Vuelo", JOptionPane.ERROR_MESSAGE);
            }

            // Limpio el internal frame antes de cerrar la ventana
            limpiarFormulario();
            setVisible(false);
        }
    }

    
    private boolean checkFormulario() {
 
        String nombreVuelo = this.tfNombre.getText();    
        String nomRuta = (String)comboRutas.getSelectedItem(); 
		String nomAerolinea = (String)comboAerolineas.getSelectedItem(); 
        Date fvuelo = dateChooserFechaVuelo.getDate();//Seleccion del JCalendar, devuelve Date   
        Date falta = dateChooserFechaAlta.getDate();//Seleccion del JCalendar, devuelve Date        
        String duracion = this.tfDuracion.getText(); // Chequear que sea un string en dicho formato, esto lo hago con regex
        String asientosTuristas = this.tfATuristas.getText().trim();
        String asientosEjecutivos = this.tfAEjecutivos.getText().trim();
 
        if (nomRuta == null || nomRuta.equals("----- Rutas -----") || nombreVuelo == null || nombreVuelo.equals("----- Aerolineas -----") ||nomAerolinea == null || nomAerolinea.equals("----- Aerolineas -----") || fvuelo == null || falta == null ||
        	duracion.isEmpty() || asientosTuristas.isEmpty() || asientosEjecutivos.isEmpty()) {
        	    JOptionPane.showMessageDialog(this, "No puede haber campos vacíos", "Error de Validación",
        	            JOptionPane.ERROR_MESSAGE);
        	    return false;
        	}
        return true;
    }
	public void cargarAerolineas() {
	   	DefaultComboBoxModel<String> model;

    	try {
    		    List<String> aerolineas = contUsers.listarAerolineas(); 
    		    String[] aerolineasArray = aerolineas.toArray(new String[0]); 
    		    
    		    model = new DefaultComboBoxModel<String>();
    		    model.addElement("----- Aerolineas -----"); // Primer item
    		    
    		    // Agregamos los usuarios restantes al modelo
    		    for (String aer : aerolineasArray) {
    		        model.addElement(aer);
    		    }    		    
    		    comboAerolineas.setModel(model);
    		   
    	} catch (UsuarioNoExisteException e) {
    		// No se imprime mensaje de error sino que simplemente no se muestra ningún elemento
    	} 
	    	
	    }
	private void cargarRutas(String nomAerolinea) {
		DefaultComboBoxModel<String> model;
    	try {
    		    List<String> rutas = controlRV.listarRutasConfirmadas(nomAerolinea); 
    		    String[] rutasArray = rutas.toArray(new String[0]); 
    		    
    		    model = new DefaultComboBoxModel<String>();
    		    model.addElement("----- Rutas -----"); // Primer item
    		    
    		    for (String rtv : rutasArray) {
    		        model.addElement(rtv);
    		    }    		    
    		    comboRutas.setModel(model);
    		   
    	} catch (UsuarioNoEsAerolineaExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
	}
	  private void limpiarFormulario() {
			tfNombre.setText("");
    		tfDuracion.setText("");
    		tfATuristas.setText("");
    		tfAEjecutivos.setText("");
	    }
}
