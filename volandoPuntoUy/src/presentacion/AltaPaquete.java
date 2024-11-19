package presentacion;

import javax.swing.JInternalFrame;

import excepciones.CiudadRepetidaException;
import excepciones.RutaDeVueloRepetidaException;
import logica.DTCiudad;
import logica.IControladorCiudadCategoria;
import logica.IControladorPaquete;
import logica.IControladorRutaDeVuelo;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.time.Duration;
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
import com.toedter.components.JSpinField;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class AltaPaquete extends JInternalFrame {
	private static final long serialVersionUID = 1L;
    // Controlador de rutas de vuelo que se utilizará para las acciones del JFrame
    private IControladorPaquete controlP;
    private JPanel contentPane;
    
    // Los componentes gráficos se agregan como atributos de la clase
    // para facilitar su acceso desde diferentes métodos de la misma.
    private JTextField textFieldNombre;
    private JTextField textFieldPorcentaje;
    //
    private JLabel lblIngreseNombre;
    private JLabel lbldesc;
    //
    private JButton btnAceptar;
    private JButton btnCancelar;
    private JLabel llbp;
    private JLabel lblDescripcin;
    private JLabel lblFechaDeAlta;
    private JDateChooser calendar;
    private JSpinField spinnerDias;
    private JTextArea textFieldDescripcion;
    private JPanel panel;
	private JScrollPane scrollPane;
    /**
     * Create the frame.
     */
    public AltaPaquete(IControladorPaquete icp) {
        // Se inicializa con el controlador de CC
        controlP = icp;

        // Propiedades del JInternalFrame como dimensión, posición dentro del frame,
        // etc.
        
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(0, 0, 600, 350);
		setTitle("Alta de Paquete");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().setLayout(new FlowLayout());
		getContentPane().add(contentPane);

        // En este caso utilizaremos el GridBagLayout que permite armar una grilla
        // en donde las filas y columnas no son uniformes.
        // Conviene trabajar este componente desde la vista de diseño gráfico y sólo
        // manipular los valores para ajustar alguna cosa.
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] {150, 400};
        gridBagLayout.rowHeights = new int[] {25, 25, 25, 25, 75, 25};
        gridBagLayout.columnWeights = new double[] { 0.0, 1.0 };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        contentPane.setLayout(gridBagLayout);

        // Una etiqueta (JLabel) indicandp que en el siguiente campo debe ingresarse 
        // el nombre de la ruta de vuelo. El texto está alineado horizontalmente a la derecha para
        // que quede casi pegado al campo de texto.
        lblIngreseNombre = new JLabel("Nombre:");
        lblIngreseNombre.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblIngreseNombre = new GridBagConstraints();
        gbc_lblIngreseNombre.fill = GridBagConstraints.BOTH;
        gbc_lblIngreseNombre.insets = new Insets(0, 0, 5, 5);
        gbc_lblIngreseNombre.gridx = 0;
        gbc_lblIngreseNombre.gridy = 1;
        contentPane.add(lblIngreseNombre, gbc_lblIngreseNombre);

        // Una campo de texto (JTextField) para ingresar el nombre de la ruta de vuelo. 
        // Por defecto es posible ingresar cualquier string.
        textFieldNombre = new JTextField();
        textFieldNombre.setToolTipText("Nombre ciudad");
        GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
        gbc_textFieldNombre.fill = GridBagConstraints.BOTH;
        gbc_textFieldNombre.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldNombre.gridx = 1;
        gbc_textFieldNombre.gridy = 1;
        contentPane.add(textFieldNombre, gbc_textFieldNombre);
        textFieldNombre.setColumns(10);
        
        // Una etiqueta (JLabel) indicando que en el siguiente campo debe ingresarse 
        // la fecha de la ruta de vuelo. El texto está alineado horizontalmente a la derecha para
        // que quede casi pegado al campo de texto.
        lbldesc = new JLabel("Descuento (pctj.):");
        lbldesc.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lbldesc = new GridBagConstraints();
        gbc_lbldesc.fill = GridBagConstraints.BOTH;
        gbc_lbldesc.insets = new Insets(0, 0, 5, 5);
        gbc_lbldesc.gridx = 0;
        gbc_lbldesc.gridy = 2;
        contentPane.add(lbldesc, gbc_lbldesc);
        

                textFieldPorcentaje = new JTextField();
                textFieldPorcentaje.setToolTipText("Pais");
                textFieldPorcentaje.setColumns(10);
                GridBagConstraints gbc_textFieldPorcentaje = new GridBagConstraints();
                gbc_textFieldPorcentaje.fill = GridBagConstraints.BOTH;
                gbc_textFieldPorcentaje.insets = new Insets(0, 0, 5, 0);
                gbc_textFieldPorcentaje.gridx = 1;
                gbc_textFieldPorcentaje.gridy = 2;
                contentPane.add(textFieldPorcentaje, gbc_textFieldPorcentaje);
                        
                        llbp = new JLabel("Período (días)");
                        llbp.setHorizontalAlignment(SwingConstants.RIGHT);
                        GridBagConstraints gbc_llbp = new GridBagConstraints();
                        gbc_llbp.anchor = GridBagConstraints.EAST;
                        gbc_llbp.insets = new Insets(0, 0, 5, 5);
                        gbc_llbp.gridx = 0;
                        gbc_llbp.gridy = 3;
                        contentPane.add(llbp, gbc_llbp);
                        
                        spinnerDias = new JSpinField();
                        spinnerDias.setMaximum(999999);
                        spinnerDias.setValue(1);
                        spinnerDias.setMinimum(1);
                        GridBagConstraints gbc_spinnerDias = new GridBagConstraints();
                        gbc_spinnerDias.insets = new Insets(0, 0, 5, 5);
                        gbc_spinnerDias.fill = GridBagConstraints.BOTH;
                        gbc_spinnerDias.gridx = 1;
                        gbc_spinnerDias.gridy = 3;
                        contentPane.add(spinnerDias, gbc_spinnerDias);
                        
                        lblDescripcin = new JLabel("Descripción:");
                        lblDescripcin.setHorizontalAlignment(SwingConstants.RIGHT);
                        lblDescripcin.setVerticalAlignment(SwingConstants.TOP);
                        GridBagConstraints gbc_lblDescripcin = new GridBagConstraints();
                        gbc_lblDescripcin.anchor = GridBagConstraints.EAST;
                        gbc_lblDescripcin.fill = GridBagConstraints.VERTICAL;
                        gbc_lblDescripcin.insets = new Insets(0, 0, 5, 5);
                        gbc_lblDescripcin.gridx = 0;
                        gbc_lblDescripcin.gridy = 4;
                        contentPane.add(lblDescripcin, gbc_lblDescripcin);
                        
                        scrollPane = new JScrollPane();
                        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
                        gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
                        gbc_scrollPane.fill = GridBagConstraints.BOTH;
                        gbc_scrollPane.gridx = 1;
                        gbc_scrollPane.gridy = 4;
                        contentPane.add(scrollPane, gbc_scrollPane);
                        
                        textFieldDescripcion = new JTextArea();
                        textFieldDescripcion.setLineWrap(true);
                        scrollPane.setViewportView(textFieldDescripcion);
                        textFieldDescripcion.setColumns(10);
                        textFieldDescripcion.setEditable(true);
                        textFieldDescripcion.setSize(100,400);

                        
                        lblFechaDeAlta = new JLabel("Fecha de alta:");
                        lblFechaDeAlta.setHorizontalAlignment(SwingConstants.RIGHT);
                        GridBagConstraints gbc_lblFechaDeAlta = new GridBagConstraints();
                        gbc_lblFechaDeAlta.anchor = GridBagConstraints.EAST;
                        gbc_lblFechaDeAlta.insets = new Insets(0, 0, 5, 5);
                        gbc_lblFechaDeAlta.gridx = 0;
                        gbc_lblFechaDeAlta.gridy = 5;
                        contentPane.add(lblFechaDeAlta, gbc_lblFechaDeAlta);
                                
                                calendar = new JDateChooser();
                                calendar.setDateFormatString("dd/MM/yyyy");
                                GridBagConstraints gbc_calendar = new GridBagConstraints();
                                gbc_calendar.insets = new Insets(0, 0, 5, 5);
                                gbc_calendar.fill = GridBagConstraints.BOTH;
                                gbc_calendar.gridx = 1;
                                gbc_calendar.gridy = 5;
                                contentPane.add(calendar, gbc_calendar);
                                calendar.setDate(new Date());
                                                        
                                                        panel = new JPanel();
                                                        GridBagConstraints gbc_panel = new GridBagConstraints();
                                                        gbc_panel.insets = new Insets(0, 0, 5, 0);
                                                        gbc_panel.gridwidth = 2;
                                                        gbc_panel.fill = GridBagConstraints.BOTH;
                                                        gbc_panel.gridx = 1;
                                                        gbc_panel.gridy = 6;
                                                        contentPane.add(panel, gbc_panel);
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

    	String nombre = this.textFieldNombre.getText().trim();
        String porcentajeString =  this.textFieldPorcentaje.getText().trim();
        int dias = this.spinnerDias.getValue();
        String desc = this.textFieldDescripcion.getText().trim();

        if (checkFormulario()) {
            try {  
                Date fechaAltaDate = calendar.getDate();
                LocalDate fechaAlta = fechaAltaDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                
                float porcentaje = Float.parseFloat(porcentajeString);
                Duration periodo = Duration.ofDays(dias);
                
                controlP.crearPaqueteRutasDeVuelo(nombre, desc, periodo, porcentaje, fechaAlta, "");
                // Muestro éxito de la operación
                JOptionPane.showMessageDialog(this, "El paquete se ha creado con éxito.", "",
                        JOptionPane.INFORMATION_MESSAGE);
             // Limpio el internal frame antes de cerrar la ventana
                limpiarFormulario();
                setVisible(false);

            } catch (Exception e) {
                // Muestro error de registro
                JOptionPane.showMessageDialog(this, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
            }


        }
    }

    
    
    private boolean checkFormulario() {

        if (textFieldNombre.getText().isBlank() || textFieldPorcentaje.getText().isBlank() || textFieldDescripcion.getText().isBlank()|| spinnerDias.getValue()<=0) {
            JOptionPane.showMessageDialog(this, "No puede haber campos vacíos o inválidos", "",
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
        
        try {
        	float pctj = Float.parseFloat(textFieldPorcentaje.getText().trim());
        	if(pctj <0 || pctj >100) {
        		JOptionPane.showMessageDialog(this, "Porcentaje fuera de rango", "",
                        JOptionPane.ERROR_MESSAGE);
        		return false;
        	}
        }catch(Exception e) {
        	JOptionPane.showMessageDialog(this, "Porcentaje inválido", "",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (fechaAlta.getYear()<1000 || fechaAlta.getYear()>9999) {
        	JOptionPane.showMessageDialog(this, "Año inválido", "",
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
        textFieldPorcentaje.setText("");
        textFieldDescripcion.setText("");
        spinnerDias.setValue(0);
        
    }
}

