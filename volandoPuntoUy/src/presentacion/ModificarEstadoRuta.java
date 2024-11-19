package presentacion;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import excepciones.UsuarioNoEsAerolineaExcepcion;
import excepciones.UsuarioNoExisteException;
import model.*;
import datatype.*;
import service.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.List;

public class ModificarEstadoRuta extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JLabel aerolineaText = new JLabel("Aerolinea:");
	private JComboBox<String> aerolineaCombo = new JComboBox<String>();
	private JLabel rutaText = new JLabel("Ruta:");
	private JComboBox<String> rutaCombo = new JComboBox<String>();
	private JButton cancelar = new JButton("Cancelar");
	private JButton aceptar = new JButton("Aceptar");

	private JLabel estadoL = new JLabel("Estado:");
	private String estados[] = {"Confirmada","Rechazada"};
	private JComboBox selectorEstado = new JComboBox(estados);
	
	private List<String> paquetes = null;
	private List<String> aerolineas = null;
	private List<String> rutas = null;
	/**
	 * Create the frame.
	 */
	public ModificarEstadoRuta(IControladorUsuario CU, IControladorRutaDeVuelo CRV) {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(0, 0, 300, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		setTitle("Modificar estado ruta");
		
		
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
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
			}
			
		});
		
		
		panel_1.add(aerolineaText);
		aerolineaText.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(aerolineaCombo);
		
		panel_1.add(rutaText);
		rutaText.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(rutaCombo);
		
		panel_1.add(estadoL);
		panel_1.add(selectorEstado);
		estadoL.setHorizontalAlignment(SwingConstants.RIGHT);
		
		panel_4.add(aceptar);
		
		panel_4.add(cancelar);
		
		

		
		cancelar.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				hide();
			}
		});
		
		aceptar.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				if((String)selectorEstado.getSelectedItem() == "Confirmada") {
					CRV.aceptarRuta((String)rutaCombo.getSelectedItem());
				}
				if((String)selectorEstado.getSelectedItem() == "Rechazada") {
					CRV.rechazarRuta((String)rutaCombo.getSelectedItem());
				}
				hide();
			}
		});
		
		aerolineaCombo.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				if(aerolineaCombo.getSelectedItem()!=null) {
					rutaCombo.removeAllItems();
					try {
						rutas = CRV.listarRutasIngresadas((String)aerolineaCombo.getSelectedItem());
					} catch (UsuarioNoEsAerolineaExcepcion e1) {
						//Nunca pasa esto
						e1.printStackTrace();
					}
					
					for(String r:rutas) {
						rutaCombo.addItem(r);
					}
					if(rutas.isEmpty()) {
						aceptar.setEnabled(false);
					}
					else aceptar.setEnabled(true);
				}
			}
		});
	}

}
