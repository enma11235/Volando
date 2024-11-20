package presentacion;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.print.attribute.AttributeSet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import com.toedter.calendar.JDateChooser;

import excepciones.UsuarioRepetidoException;
import service.*;
import datatype.*;


public class AltaUsuario extends JInternalFrame {
	private JPanel contentPane;
	
	//PANELES (deben ser agregados al contentPane)
	
	protected JPanel panelSuperior;
	protected JPanel panelCentral;
	protected JPanel panelInferior;
	
	//COMPONENTES (deben ser agregados a los paneles)
	
	//los que van en el panel1
	JLabel lbl_tipoUsuario;
	JLabel lbl_cliente;
	JLabel lbl_aereolinea;
	JRadioButton radio_btn_cliente;
	JRadioButton radio_btn_aereolinea;
	
	//los que van en el panel2
	JLabel lbl_nombre;
	JTextField text_field_nombre;
	
	//los que van en el panel3
	JLabel lbl_apellido;
	JTextField text_field_apellido;
	JLabel lbl_descripcion;
	JTextField text_field_descripcion;
		
	//los que van en el panel4
	JLabel lbl_nickname;
	JTextField text_field_nickname;
	
	//los que van en el panel5
	JLabel lbl_email;
	JTextField text_field_email;
	
	JLabel lbl_contrasena;
	JTextField text_field_contrasena;
	JLabel lbl_contrasenaConf;
	JTextField text_field_contrasenaConf;
	
	//los que van en el panel6
	JLabel lbl_nacimiento;
	JDateChooser text_field_nacimiento;
	JLabel lbl_web;
	JTextField text_field_web;
	
	//los que van en el panel7
	JLabel lbl_nacionalidad;
	JTextField text_field_nacionalidad;
	
	//los que van en el panel8
	JLabel lbl_tipo_doc;
	JComboBox combo_box_tipo_doc;
	
	//los que van en el panel9
	JLabel lbl_num_doc;
	JTextField text_field_num_doc;
	
	//los que van en el panel10
	JButton btn_aceptar;
	JButton btn_cancelar;
	
	//METODO PARA MONTAR LOS COMPONENTES CENTRALES
	
	private void montarComponentesCentrales() {
		
		//removemos todos los componentes centrales montados
		
		panelCentral.removeAll();
		
		//y los montamos de nuevo
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		
		if(radio_btn_cliente.isSelected()) {
			
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = gbc.SOUTHWEST;
			panelCentral.add(lbl_nombre, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 0;
			panelCentral.add(text_field_nombre, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 1;
			panelCentral.add(lbl_apellido, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 1;
			panelCentral.add(text_field_apellido, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 2;
			panelCentral.add(lbl_nickname, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 2;
			panelCentral.add(text_field_nickname, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 3;
			panelCentral.add(lbl_email, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 3;
			panelCentral.add(text_field_email, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 4;
			panelCentral.add(lbl_contrasena, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 4;
			panelCentral.add(text_field_contrasena, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 5;
			panelCentral.add(lbl_contrasenaConf, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 5;
			panelCentral.add(text_field_contrasenaConf, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 6;
			panelCentral.add(lbl_nacimiento, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 6;
			panelCentral.add(text_field_nacimiento, gbc);
		
			gbc.gridx = 0;
			gbc.gridy = 7;
			panelCentral.add(lbl_nacionalidad, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 7;
			panelCentral.add(text_field_nacionalidad, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 8;
			panelCentral.add(lbl_tipo_doc, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 8;
			panelCentral.add(combo_box_tipo_doc, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 9;
			panelCentral.add(lbl_num_doc, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 9;
			panelCentral.add(text_field_num_doc, gbc);
			
			
			
		} else {
			
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = gbc.SOUTHWEST;
			panelCentral.add(lbl_nombre, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 0;
			panelCentral.add(text_field_nombre, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 1;
			panelCentral.add(lbl_nickname, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 1;
			panelCentral.add(text_field_nickname, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 2;
			panelCentral.add(lbl_email, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 2;
			panelCentral.add(text_field_email, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 3;
			panelCentral.add(lbl_contrasena, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 3;
			panelCentral.add(text_field_contrasena, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 4;
			panelCentral.add(lbl_contrasenaConf, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 4;
			panelCentral.add(text_field_contrasenaConf, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 5;
			panelCentral.add(lbl_descripcion, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 5;
			panelCentral.add(text_field_descripcion, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 6;
			panelCentral.add(lbl_web, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 6;
			panelCentral.add(text_field_web, gbc);
			
			

		}
		
		contentPane.revalidate();
		contentPane.repaint();
		
	}
	
	// constructor
	
	public AltaUsuario(IControladorUsuario icu) {
		this.setTitle("Alta Usuario");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(0, 0, 500, 300);
		
		//INICIALIZAMOS EL CONTENT PANE
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());
		
		//INICIALIZAMOS LOS PANELES
		
		panelSuperior = new JPanel();
		panelCentral = new JPanel();
		panelInferior = new JPanel();
		
		panelCentral.setLayout(new GridBagLayout());
		
		
		lbl_cliente = new JLabel("Cliente");
		lbl_aereolinea = new JLabel("Aereolinea");
		radio_btn_cliente = new JRadioButton();
		radio_btn_aereolinea = new JRadioButton();
		
		radio_btn_cliente.setSelected(false);
		radio_btn_aereolinea.setSelected(true);
		
		radio_btn_cliente.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(radio_btn_aereolinea.isSelected()) {
					radio_btn_aereolinea.setSelected(false);
					montarComponentesCentrales();
				} else {
					radio_btn_cliente.setSelected(true);
				}
			}
		});
		
		radio_btn_aereolinea.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(radio_btn_cliente.isSelected()) {
					radio_btn_cliente.setSelected(false);
					montarComponentesCentrales();
				} else {
					radio_btn_aereolinea.setSelected(true);
				}
			}
		});
		
		//INICIALIZAMOS LOS COMPONENTES
		
		lbl_nombre = new JLabel("Nombre");
		text_field_nombre = new JTextField(10);
		
		lbl_apellido = new JLabel("Apellido");
		text_field_apellido = new JTextField(10);
		
		lbl_descripcion = new JLabel("Descripcion");
		text_field_descripcion = new JTextField(10);
			
		lbl_nickname = new JLabel("Nickname");
		text_field_nickname = new JTextField(10);
		
		lbl_email = new JLabel("Email");
		text_field_email = new JTextField(10);
		
		lbl_contrasena = new JLabel("Contraseña");
		text_field_contrasena = new JTextField(10);
		
		lbl_contrasenaConf = new JLabel("Confirmar contraseña");
		text_field_contrasenaConf = new JTextField(10);
		
		lbl_nacimiento = new JLabel("Nacimiento");
		
		text_field_nacimiento = new JDateChooser();
        text_field_nacimiento.setEnabled(true);
        text_field_nacimiento.setDateFormatString("dd/MM/yyyy");
        try {
			text_field_nacimiento.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(LocalDate.now().toString()));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        lbl_web = new JLabel("Web");
        text_field_web = new JTextField(10);
		
		lbl_nacionalidad = new JLabel("Nacionalidad");
		text_field_nacionalidad = new JTextField(10);
		
		lbl_tipo_doc = new JLabel("Tipo Documento");
		
		// Obtener la cantidad de valores en el enumerado
		int cantTipoDocs = DocumentType.values().length;

		// Crear un arreglo de String del tamaño de la cantidad de valores en el enumerado
		String[] opciones = new String[cantTipoDocs];
		// Llenar el arreglo con los nombres de los valores del enumerado
		for (int i = 0; i < cantTipoDocs; i++) {
		    opciones[i] = DocumentType.values()[i].name();
		}
		combo_box_tipo_doc = new JComboBox(opciones);
		
		lbl_num_doc = new JLabel("Numero Documento");
		text_field_num_doc = new JTextField(10);
		
		btn_aceptar = new JButton("Aceptar");
		btn_cancelar = new JButton("Cancelar");
		
		btn_aceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String contEmail = "@";
				String prefSitio1 = "http://";
		        String prefSitio2 = "https://";
		        String prefSitio3 = "www";
				if(radio_btn_cliente.isSelected()) {
					if (text_field_nickname.getText().isEmpty() || text_field_nombre.getText().isEmpty() || text_field_email.getText().isEmpty() || text_field_apellido.getText().isEmpty() || text_field_nacionalidad.getText().isEmpty() || text_field_num_doc.getText().isEmpty()) {
			            JOptionPane.showMessageDialog((Component)contentPane, "No puede haber campos vacíos.", "Alta de Usuario",
			                    JOptionPane.ERROR_MESSAGE);
			        }
					else if (text_field_email.getText().contains(contEmail) == false){
			        	JOptionPane.showMessageDialog((Component)contentPane, "El email no es correcto.", "Alta de Usuario",
			                    JOptionPane.ERROR_MESSAGE);
			        }
					else if (!(text_field_contrasena.getText().compareTo(text_field_contrasenaConf.getText()) == 0)) {
						JOptionPane.showMessageDialog((Component)contentPane, "Las contraseña y la confirmación de la misma no coinciden.", "Alta de Usuario",
			                    JOptionPane.ERROR_MESSAGE);
					}
					else {
						DocumentType tipoA = DocumentType.fromString((String) combo_box_tipo_doc.getSelectedItem());
						try {
							icu.altaCliente(text_field_nickname.getText(), text_field_nombre.getText(), text_field_email.getText(), text_field_contrasena.getText(), text_field_apellido.getText(),
							text_field_nacimiento.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), text_field_nacionalidad.getText(), tipoA, text_field_num_doc.getText(), "");
							JOptionPane.showMessageDialog((Component)contentPane, "El cliente fue registrado con exito..", "Alta de Usuario",
			                        JOptionPane.INFORMATION_MESSAGE);
							hide();
						} catch (UsuarioRepetidoException e1) {
							JOptionPane.showMessageDialog(
						            contentPane, // El componente padre, puede ser null si no está dentro de otro componente
						            e1.getMessage(), // Mensaje a mostrar
						            "Error", // Título del cuadro de diálogo
						            JOptionPane.ERROR_MESSAGE // Tipo de mensaje
						        );
						}
					}
					
				} else {
					if (text_field_nickname.getText().isEmpty() || text_field_nombre.getText().isEmpty() || text_field_email.getText().isEmpty()  || text_field_descripcion.getText().isEmpty() || text_field_web.getText().isEmpty()) {
			            JOptionPane.showMessageDialog((Component)contentPane, "No puede haber campos vacíos.", "Alta de Usuario",
			                    JOptionPane.ERROR_MESSAGE);
			        }
					else if (text_field_email.getText().contains(contEmail) == false){
			        	JOptionPane.showMessageDialog((Component)contentPane, "El email no es correcto.", "Alta de Usuario",
			                    JOptionPane.ERROR_MESSAGE);
			        }
					else if(text_field_web.getText().contains(prefSitio1) == false && text_field_web.getText().contains(prefSitio2) == false  && text_field_web.getText().contains(prefSitio3) == false ){
			        	JOptionPane.showMessageDialog((Component)contentPane, "La pagina web no es correcta.", "Alta de Usuario",
			                    JOptionPane.ERROR_MESSAGE);
			        }
					else if (!(text_field_contrasena.getText().compareTo(text_field_contrasenaConf.getText()) == 0)) {
						JOptionPane.showMessageDialog((Component)contentPane, "Las contraseña y la confirmación de la misma no coinciden.", "Alta de Usuario",
			                    JOptionPane.ERROR_MESSAGE);
					}
					else
					{
						try {
							icu.altaAereolinea(text_field_nickname.getText(), text_field_nombre.getText(), text_field_email.getText(), text_field_contrasena.getText(), text_field_descripcion.getText(), text_field_web.getText(), "");
							JOptionPane.showMessageDialog((Component)contentPane, "El cliente fue registrado con exito..", "Alta de Usuario",
			                        JOptionPane.INFORMATION_MESSAGE);
							hide();
						} catch (UsuarioRepetidoException e1) {
							JOptionPane.showMessageDialog(
						            contentPane, // El componente padre, puede ser null si no está dentro de otro componente
						            e1.getMessage(), // Mensaje a mostrar
						            "Error", // Título del cuadro de diálogo
						            JOptionPane.ERROR_MESSAGE // Tipo de mensaje
						        );
						}
					} 
				}
			}
		});
		
		btn_cancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				text_field_nombre.setText("");
				text_field_apellido.setText("");
				text_field_email.setText("");
				text_field_nickname.setText("");
				text_field_nacionalidad.setText("");
				text_field_num_doc.setText("");
				text_field_web.setText("");
				text_field_descripcion.setText("");
				dispose();
			}
		});
		
		//MONTAMOS LOS COMPOENENTES DEL PANEL1
		
		panelSuperior.add(lbl_cliente);
		panelSuperior.add(radio_btn_cliente);
		panelSuperior.add(lbl_aereolinea);
		panelSuperior.add(radio_btn_aereolinea);
		
		//MONTAMOS LOS COMPONENTES DEL PANEL CENTRAL
		
		panelInferior.add(btn_aceptar);
		panelInferior.add(btn_cancelar);
		
		
		montarComponentesCentrales();
		
		//MONTAMOS LOS PANELES EN EL CONTENT PANE
		
		contentPane.add(panelSuperior, BorderLayout.PAGE_START);
		
		contentPane.add(panelCentral, BorderLayout.CENTER);
		
		contentPane.add(panelInferior, BorderLayout.PAGE_END);
		
		//MONTAMOS EL CONTENT PANE
		
		this.setContentPane(new JScrollPane(contentPane));
	}

}

