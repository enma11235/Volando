package presentacion;

import javax.swing.JInternalFrame;

import excepciones.CiudadRepetidaException;
import excepciones.RutaDeVueloRepetidaException;
import datatype.DTCiudad;
import service.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.DropMode;
import com.toedter.calendar.JDayChooser;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class AltaCiudad extends JInternalFrame {
	private static final long serialVersionUID = 1L;
    // Controlador de rutas de vuelo que se utilizará para las acciones del JFrame
    private IControladorCiudadCategoria controlCC;
    
    // Los componentes gráficos se agregan como atributos de la clase
    // para facilitar su acceso desde diferentes métodos de la misma.
    private JTextField textFieldNombre;
    private JTextField textFieldPais;
    //
    private JLabel lblIngreseNombre;
    private JLabel lblIngresePais;
    //
    private JButton btnAceptar;
    private JButton btnCancelar;
    private JLabel lblAeropuerto;
    private JLabel lblDescripcin;
    private JLabel lblSitioWeb;
    private JTextField textFieldAeropuerto;
    private JTextArea textFieldDescripcion;
    private JTextField textFieldWeb;
    private JLabel lblFechaDeAlta;
    private JDateChooser calendar;
    private JPanel panel;
	private JScrollPane scrollPane;
    /**
     * Create the frame.
     */
    public AltaCiudad(IControladorCiudadCategoria icc) {
        // Se inicializa con el controlador de CC
        controlCC = icc;

        // Propiedades del JInternalFrame como dimensión, posición dentro del frame,
        // etc.
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setTitle("Alta de Ciudad");
        setBounds(10, 10, 500, 400);

        // En este caso utilizaremos el GridBagLayout que permite armar una grilla
        // en donde las filas y columnas no son uniformes.
        // Conviene trabajar este componente desde la vista de diseño gráfico y sólo
        // manipular los valores para ajustar alguna cosa.
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{150, 350};
        gridBagLayout.rowHeights = new int[]{25,25,25,75,25,25,25};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
     
        getContentPane().setLayout(gridBagLayout);
                
                        // Una etiqueta (JLabel) indicandp que en el siguiente campo debe ingresarse 
                        // el nombre de la ruta de vuelo. El texto está alineado horizontalmente a la derecha para
                        // que quede casi pegado al campo de texto.
                        lblIngreseNombre = new JLabel("Nombre:");
                        lblIngreseNombre.setHorizontalAlignment(SwingConstants.RIGHT);
                        GridBagConstraints gbc_lblIngreseNombre = new GridBagConstraints();
                        gbc_lblIngreseNombre.fill = GridBagConstraints.BOTH;
                        gbc_lblIngreseNombre.insets = new Insets(0, 0, 5, 5);
                        gbc_lblIngreseNombre.gridx = 0;
                        gbc_lblIngreseNombre.gridy = 0;
                        getContentPane().add(lblIngreseNombre, gbc_lblIngreseNombre);
        
                // Una campo de texto (JTextField) para ingresar el nombre de la ruta de vuelo. 
                // Por defecto es posible ingresar cualquier string.
                textFieldNombre = new JTextField();
                textFieldNombre.setToolTipText("Nombre ciudad");
                GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
                gbc_textFieldNombre.gridwidth = 2;
                gbc_textFieldNombre.fill = GridBagConstraints.BOTH;
                gbc_textFieldNombre.insets = new Insets(0, 0, 5, 0);
                gbc_textFieldNombre.gridx = 1;
                gbc_textFieldNombre.gridy = 0;
                getContentPane().add(textFieldNombre, gbc_textFieldNombre);
                textFieldNombre.setColumns(10);
                
                // Una etiqueta (JLabel) indicando que en el siguiente campo debe ingresarse 
                // la fecha de la ruta de vuelo. El texto está alineado horizontalmente a la derecha para
                // que quede casi pegado al campo de texto.
                lblIngresePais = new JLabel("País:");
                lblIngresePais.setHorizontalAlignment(SwingConstants.RIGHT);
                GridBagConstraints gbc_lblIngresePais = new GridBagConstraints();
                gbc_lblIngresePais.fill = GridBagConstraints.BOTH;
                gbc_lblIngresePais.insets = new Insets(0, 0, 5, 5);
                gbc_lblIngresePais.gridx = 0;
                gbc_lblIngresePais.gridy = 1;
                getContentPane().add(lblIngresePais, gbc_lblIngresePais);
                        

                                textFieldPais = new JTextField();
                                textFieldPais.setToolTipText("Pais");
                                textFieldPais.setColumns(10);
                                GridBagConstraints gbc_textFieldPais = new GridBagConstraints();
                                gbc_textFieldPais.gridwidth = 2;
                                gbc_textFieldPais.fill = GridBagConstraints.BOTH;
                                gbc_textFieldPais.insets = new Insets(0, 0, 5, 0);
                                gbc_textFieldPais.gridx = 1;
                                gbc_textFieldPais.gridy = 1;
                                getContentPane().add(textFieldPais, gbc_textFieldPais);
                        
                        lblAeropuerto = new JLabel("Aeropuerto:");
                        lblAeropuerto.setHorizontalAlignment(SwingConstants.RIGHT);
                        GridBagConstraints gbc_lblAeropuerto = new GridBagConstraints();
                        gbc_lblAeropuerto.anchor = GridBagConstraints.EAST;
                        gbc_lblAeropuerto.insets = new Insets(0, 0, 5, 5);
                        gbc_lblAeropuerto.gridx = 0;
                        gbc_lblAeropuerto.gridy = 2;
                        getContentPane().add(lblAeropuerto, gbc_lblAeropuerto);
                        
                        textFieldAeropuerto = new JTextField();
                        textFieldAeropuerto.setToolTipText("Aeropuerto");
                        textFieldAeropuerto.setColumns(10);
                        GridBagConstraints gbc_textFieldAeropuerto = new GridBagConstraints();
                        gbc_textFieldAeropuerto.gridwidth = 2;
                        gbc_textFieldAeropuerto.insets = new Insets(0, 0, 5, 0);
                        gbc_textFieldAeropuerto.fill = GridBagConstraints.HORIZONTAL;
                        gbc_textFieldAeropuerto.gridx = 1;
                        gbc_textFieldAeropuerto.gridy = 2;
                        getContentPane().add(textFieldAeropuerto, gbc_textFieldAeropuerto);
                        
                        lblDescripcin = new JLabel("Descripción:");
                        lblDescripcin.setHorizontalAlignment(SwingConstants.RIGHT);
                        lblDescripcin.setVerticalAlignment(SwingConstants.TOP);
                        GridBagConstraints gbc_lblDescripcin = new GridBagConstraints();
                        gbc_lblDescripcin.anchor = GridBagConstraints.EAST;
                        gbc_lblDescripcin.fill = GridBagConstraints.VERTICAL;
                        gbc_lblDescripcin.insets = new Insets(0, 0, 5, 5);
                        gbc_lblDescripcin.gridx = 0;
                        gbc_lblDescripcin.gridy = 3;
                        getContentPane().add(lblDescripcin, gbc_lblDescripcin);
                        
                        scrollPane = new JScrollPane();
                        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
                        gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
                        gbc_scrollPane.fill = GridBagConstraints.BOTH;
                        gbc_scrollPane.gridx = 1;
                        gbc_scrollPane.gridy = 3;
                        getContentPane().add(scrollPane, gbc_scrollPane);
                        
                        textFieldDescripcion = new JTextArea();
                        scrollPane.setViewportView(textFieldDescripcion);
                        textFieldDescripcion.setColumns(10);
                        textFieldDescripcion.setEditable(true);
                        textFieldDescripcion.setSize(100,400);
                        
                        lblSitioWeb = new JLabel("Sitio web:");
                        lblSitioWeb.setHorizontalAlignment(SwingConstants.RIGHT);
                        GridBagConstraints gbc_lblSitioWeb = new GridBagConstraints();
                        gbc_lblSitioWeb.anchor = GridBagConstraints.EAST;
                        gbc_lblSitioWeb.insets = new Insets(0, 0, 5, 5);
                        gbc_lblSitioWeb.gridx = 0;
                        gbc_lblSitioWeb.gridy = 4;
                        getContentPane().add(lblSitioWeb, gbc_lblSitioWeb);
                        
                        textFieldWeb = new JTextField();
                        textFieldWeb.setToolTipText("Web");
                        textFieldWeb.setColumns(10);
                        GridBagConstraints gbc_textFieldWeb = new GridBagConstraints();
                        gbc_textFieldWeb.gridwidth = 2;
                        gbc_textFieldWeb.insets = new Insets(0, 0, 5, 0);
                        gbc_textFieldWeb.fill = GridBagConstraints.HORIZONTAL;
                        gbc_textFieldWeb.gridx = 1;
                        gbc_textFieldWeb.gridy = 4;
                        getContentPane().add(textFieldWeb, gbc_textFieldWeb);
                                
                                lblFechaDeAlta = new JLabel("Fecha de alta:");
                                lblFechaDeAlta.setHorizontalAlignment(SwingConstants.RIGHT);
                                GridBagConstraints gbc_lblFechaDeAlta = new GridBagConstraints();
                                gbc_lblFechaDeAlta.anchor = GridBagConstraints.EAST;
                                gbc_lblFechaDeAlta.insets = new Insets(0, 0, 5, 5);
                                gbc_lblFechaDeAlta.gridx = 0;
                                gbc_lblFechaDeAlta.gridy = 5;
                                getContentPane().add(lblFechaDeAlta, gbc_lblFechaDeAlta);
                                                        
                                                        calendar = new JDateChooser();
                                                        calendar.setDateFormatString("dd/MM/yyyy");
                                                        GridBagConstraints gbc_calendar = new GridBagConstraints();
                                                        gbc_calendar.insets = new Insets(0, 0, 5, 5);
                                                        gbc_calendar.fill = GridBagConstraints.BOTH;
                                                        gbc_calendar.gridx = 1;
                                                        gbc_calendar.gridy = 5;
                                                        getContentPane().add(calendar, gbc_calendar);
                                                        calendar.setDate(new Date());
                                                                        
                                                                        panel = new JPanel();
                                                                        GridBagConstraints gbc_panel = new GridBagConstraints();
                                                                        gbc_panel.insets = new Insets(0, 0, 5, 0);
                                                                        gbc_panel.gridwidth = 2;
                                                                        gbc_panel.fill = GridBagConstraints.BOTH;
                                                                        gbc_panel.gridx = 1;
                                                                        gbc_panel.gridy = 6;
                                                                        getContentPane().add(panel, gbc_panel);
                                                                        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
                                                                        

                                                                                btnAceptar = new JButton("Aceptar");
                                                                                panel.add(btnAceptar);
                                                                                
                                                                                        btnCancelar = new JButton("Cancelar");
                                                                                        panel.add(btnCancelar);
                                                                        btnCancelar.addActionListener(new ActionListener() {
                                                                            public void actionPerformed(ActionEvent e) {
                                                                                limpiarFormulario();
                                                                                setVisible(false);
                                                                            }
                                                                        });
                                                                btnAceptar.addActionListener(new ActionListener() {
                                                                    public void actionPerformed(ActionEvent arg0) {
                                                                        cmdRegistroUsuarioActionPerformed(arg0);
                                                                    }
                                                                });
    }

    
    
    
    protected void cmdRegistroUsuarioActionPerformed(ActionEvent arg0) {

    	String nombreCiudad = this.textFieldNombre.getText().trim();
        String nombrePais = this.textFieldPais.getText().trim();
        String sitioWeb = this.textFieldWeb.getText().trim();
        String aeropuerto = this.textFieldAeropuerto.getText().trim();
        String desc = this.textFieldDescripcion.getText().trim();


        if (checkFormulario()) {
            try {  
                Date fechaAltaDate = calendar.getDate();
                LocalDate fechaAlta = fechaAltaDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            	controlCC.crearNuevaCiudad(nombrePais, nombreCiudad, aeropuerto, desc, sitioWeb, fechaAlta);
                // Muestro éxito de la operación
                JOptionPane.showMessageDialog(this, "La ciudad se ha creado con éxito.", "",
                        JOptionPane.INFORMATION_MESSAGE);
                

            } catch (CiudadRepetidaException e) {
                // Muestro error de registro
                JOptionPane.showMessageDialog(this, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
            }

            // Limpio el internal frame antes de cerrar la ventana
            limpiarFormulario();
            setVisible(false);
        }
    }

    
    
    private boolean checkFormulario() {

        if (textFieldNombre.getText().isBlank() || textFieldPais.getText().isBlank() || textFieldDescripcion.getText().isBlank()|| textFieldAeropuerto.getText().isBlank()|| textFieldWeb.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "No puede haber campos vacíos", "",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        LocalDate fechaAlta;
        
        try {
	        Date fechaAltaDate = calendar.getDate();
	        fechaAlta = fechaAltaDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }catch(Exception e) {
        	JOptionPane.showMessageDialog(this, "Fecha inválida", "",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        String nombreCiudad = this.textFieldNombre.getText().trim();
        String nombrePais = this.textFieldPais.getText().trim();
        String sitioWeb = this.textFieldWeb.getText().trim();
        String aeropuerto = this.textFieldAeropuerto.getText().trim();
        
        
        if (fechaAlta.getYear()<1000 || fechaAlta.getYear()>9999) {
        	JOptionPane.showMessageDialog(this, "Año inválido", "",
                    JOptionPane.ERROR_MESSAGE);
        	return false;
        }

        String regex = "^[\\p{L} \\p{M}]+$"; //Solo letras y espacios en nombre país y nombre ciudad
        Pattern pattern = Pattern.compile(regex);
        
        if (!pattern.matches(regex, nombreCiudad) || !pattern.matches(regex, nombrePais)) {
            JOptionPane.showMessageDialog(this, "Formato/s de nombre/s inválido/s", "",
                    JOptionPane.ERROR_MESSAGE);
        	return false;
        }
        
        regex = "^(https?|ftp)?(:\\/\\/)?((([a-zA-Z\\d]([a-zA-Z\\d-]*[a-zA-Z\\d])*)\\.)*[a-zA-Z]{2,}|localhost|((\\d{1,3}\\.){3}\\d{1,3})|(\\[[0-9a-fA-F:]+\\]))(:(\\d+))?(\\/[\\w\\-\\.~]*)*(\\?[;&a-zA-Z\\d%_.~+=-]*)?(\\#[\\w\\-]*)?$";
        pattern = Pattern.compile(regex);
        if (!pattern.matches(regex, sitioWeb)) {
            JOptionPane.showMessageDialog(this, "Formato de sitio web inválido", "",
                    JOptionPane.ERROR_MESSAGE);
        	return false;
        }
        
        regex = "^[a-zA-Z0-9\\s\\-_'\\.]+$";
        pattern = Pattern.compile(regex);
        if (!pattern.matches(regex, aeropuerto)) {
            JOptionPane.showMessageDialog(this, "Formato de aeropuerto inválido", "",
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
        textFieldPais.setText("");
        textFieldAeropuerto.setText("");
        textFieldDescripcion.setText("");
        textFieldWeb.setText("");
    }
}

