package presentacion;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import excepciones.UsuarioNoEsAerolineaExcepcion;
import excepciones.UsuarioNoExisteException;
import service.*;
import datatype.SeatType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.List;

public class AgregarRutaDeVueloAPaqueteGUI extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JLabel paqueteText = new JLabel("Paquete:");
	private JComboBox<String> paqueteCombo = new JComboBox<String>();
	private JLabel aerolineaText = new JLabel("Aerolinea:");
	private JComboBox<String> aerolineaCombo = new JComboBox<String>();
	private JLabel rutaText = new JLabel("Ruta:");
	private JComboBox<String> rutaCombo = new JComboBox<String>();
	private JButton cancelar = new JButton("Cancelar");
	private JButton aceptar = new JButton("Aceptar");
	private JLabel cantidadText = new JLabel("Cantidad:");
	private JTextField cantidad = new JTextField();
	private JLabel tipoAsiento = new JLabel("Tipo de asiento:");
	private JComboBox<String> asiento = new JComboBox<String>();
	
	private List<String> paquetes = null;
	private List<String> aerolineas = null;
	private List<String> rutas = null;
	/**
	 * Create the frame.
	 */
	public AgregarRutaDeVueloAPaqueteGUI(IControladorUsuario CU, IControladorRutaDeVuelo CRV, IControladorPaquete CP) {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(0, 0, 300, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		setTitle("Agregar ruta a paquete");
		
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(0, 2));
		
		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.SOUTH);
		panel_3.setLayout(new BorderLayout(0,0));
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.EAST);
		panel_4.setLayout(new GridLayout(0,2,3,0));
		
		
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
					aerolineas = CU.listarAerolineas();
					paquetes = CP.listarPaquetesSinComprar();
					if(paquetes.isEmpty()) {
						JOptionPane.showMessageDialog(e.getComponent(),"No existen paquetes");
						hide();
					}
				} catch (UsuarioNoExisteException ex) {
					JOptionPane.showMessageDialog(e.getComponent(),"No existen aerolineas");
					hide();
				}
				aerolineaCombo.removeAllItems();
				if(aerolineas!=null) {
					for(String a:aerolineas) {
						aerolineaCombo.addItem(a);
					}
				}
				paqueteCombo.removeAllItems();
				if(paquetes!=null) {
					for(String p:paquetes) {
						paqueteCombo.addItem(p);
					}
				}
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
			}
			
		});
		
		panel_1.add(paqueteText);
		paqueteText.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(paqueteCombo);
		
		panel_1.add(aerolineaText);
		aerolineaText.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(aerolineaCombo);
		
		panel_1.add(rutaText);
		rutaText.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(rutaCombo);
		
		panel_4.add(aceptar);
		
		panel_4.add(cancelar);

		panel_1.add(cantidadText);
		cantidadText.setHorizontalAlignment(SwingConstants.RIGHT);
		
		panel_1.add(cantidad);
		
		
		panel_1.add(tipoAsiento);
		tipoAsiento.setHorizontalAlignment(SwingConstants.RIGHT);
		asiento.addItem("Turista");
		asiento.addItem("Ejecutivo");
		panel_1.add(asiento);
		
		cancelar.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				hide();
			}
		});
		
		aceptar.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				boolean datosCorrectos = true;
				int cantidadInt = -1;
				try { cantidadInt = Integer.parseInt(cantidad.getText());
				}catch(Exception exept) {
					datosCorrectos = false;
				}
				if(cantidadInt < 1 || rutaCombo.getSelectedItem()==null) datosCorrectos = false;
				if(!datosCorrectos) JOptionPane.showMessageDialog((Component)contentPane,"Datos incorrectos, revise");
				if(datosCorrectos) {
					SeatType a = null;
					if(asiento.getSelectedItem() == "Turista") a = SeatType.TOURIST;
					else a = SeatType.EXECUTIVE;
					CP.agregarRutaAPaquete((String)rutaCombo.getSelectedItem(), (String)paqueteCombo.getSelectedItem(), a, cantidadInt);
					JOptionPane.showMessageDialog((Component)contentPane, "La Ruta de Vuelo se agrego correctamente al paquete.", "Agregar Ruta de Vuelo a Paquete",
	                        JOptionPane.INFORMATION_MESSAGE);
					hide();
				}
			}
		});
		
		aerolineaCombo.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				if(aerolineaCombo.getSelectedItem()!=null) {
					rutaCombo.removeAllItems();
					try {
						rutas = CRV.listarRutasConfirmadas((String)aerolineaCombo.getSelectedItem());
					} catch (UsuarioNoEsAerolineaExcepcion e1) {
						//Nunca pasa esto
						e1.printStackTrace();
					}
					
					for(String r:rutas) {
						rutaCombo.addItem(r);
					}
				}
			}
		});
	}

}
