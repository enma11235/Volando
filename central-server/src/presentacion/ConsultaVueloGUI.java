package presentacion;


import model.*;
import datatype.*;
import service.*;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.PopupMenuEvent;

import excepciones.UsuarioNoEsAerolineaExcepcion;
import excepciones.UsuarioNoExisteException;
import excepciones.VueloNoExisteException;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ConsultaVueloGUI extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private List<String> aerolineas = null;
	private JComboBox<String> aerolineaCombo = new JComboBox<String>();
	private JComboBox<String> rutaCombo = new JComboBox<String>();
	private JPanel panel_1 = new JPanel();
	private JLabel aerolineaL = new JLabel("Aerolinea:");
	private JLabel vueloL = new JLabel("Vuelo:");
	private JLabel rutaL = new JLabel("Ruta:");
	private JComboBox<String> vueloCombo = new JComboBox<String>();
	private JLabel fecha = new JLabel("Fecha:");
	private JTextField fechaDato = new JTextField();
	private JLabel duracion = new JLabel("Duración:");
	private JTextField duracionDato = new JTextField();
	private JLabel cantAsientosTuristaMax = new JLabel("Máximo turista:");
	private JTextField cantAsientosTuristaMaxDato = new JTextField();
	private JLabel asientosTuristaDisp = new JLabel("Turista disponibles:");
	private JTextField asientosTuristaDispDato = new JTextField();
	private JLabel cantAsientosEjecutivoMax = new JLabel("Máximo ejecutivo:");
	private JTextField cantAsientosEjecutivoMaxDato = new JTextField();
	private JLabel asientosEjecutivoDisp = new JLabel("Ejecutivo disponibles:");
	private JTextField asientosEjecutivoDispDato = new JTextField();
	private JLabel fechaAlta = new JLabel("Fecha de alta:");
	private JTextField fechaAltaDato = new JTextField();
	private JLabel reser = new JLabel("Reservas:");
	private JComboBox<String> reservasCombo = new JComboBox<String>();
	private JButton cerrar = new JButton("Cerrar");
	
	private String aerolineaSeleccionada = null;
	private String rutaSeleccionada = null;
	private String vueloSeleccionado = null;
	/**
	 * Create the frame.
	 */
	public ConsultaVueloGUI(IUserController CU, IFlightRouteController CRV) {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(0, 0, 300, 332);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Consulta de vuelo");
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(0, 2));
		
		
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
					rutaCombo.setVisible(false);
				}
				if(aerolineaSeleccionada!=null) {
					aerolineaCombo.setSelectedItem(aerolineaSeleccionada);
					aerolineaSeleccionada = null;
					if(rutaSeleccionada != null) {
						rutaCombo.setSelectedItem(rutaSeleccionada);
						rutaSeleccionada = null;
						if(vueloSeleccionado != null) {
							vueloCombo.setSelectedItem(vueloSeleccionado);
							vueloSeleccionado = null;
						}
					}
				}
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				ConsultaVueloGUI cvg = (ConsultaVueloGUI)e.getComponent();
				cvg.hideAll();
			}
			
		});
		
		
		
		panel_1.add(aerolineaL);
		aerolineaL.setHorizontalAlignment(SwingConstants.RIGHT);
		
		panel_1.add(aerolineaCombo);
		
		
		panel_1.add(rutaL);
		rutaL.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(rutaCombo);
		rutaCombo.setVisible(false);
		
		
		panel_1.add(vueloL);
		vueloL.setHorizontalAlignment(SwingConstants.RIGHT);
		
		panel_1.add(vueloCombo);
		vueloCombo.setVisible(false);
		
		
		
		panel_1.add(fecha);
		fecha.setHorizontalAlignment(SwingConstants.RIGHT);
		
		fechaDato.setEditable(false);
		panel_1.add(fechaDato);
		
		
		panel_1.add(duracion);
		duracion.setHorizontalAlignment(SwingConstants.RIGHT);
		
		duracionDato.setEditable(false);
		panel_1.add(duracionDato);
		
		
		panel_1.add(cantAsientosTuristaMax);
		cantAsientosTuristaMax.setHorizontalAlignment(SwingConstants.RIGHT);
		
		cantAsientosTuristaMaxDato.setEditable(false);
		panel_1.add(cantAsientosTuristaMaxDato);
		
		panel_1.add(asientosTuristaDisp);
		asientosTuristaDisp.setHorizontalAlignment(SwingConstants.RIGHT);
		asientosTuristaDispDato.setEditable(false);
		panel_1.add(asientosTuristaDispDato);
		
		panel_1.add(cantAsientosEjecutivoMax);
		cantAsientosEjecutivoMax.setHorizontalAlignment(SwingConstants.RIGHT);
		
		cantAsientosEjecutivoMaxDato.setEditable(false);
		panel_1.add(cantAsientosEjecutivoMaxDato);
		
		panel_1.add(asientosEjecutivoDisp);
		asientosEjecutivoDisp.setHorizontalAlignment(SwingConstants.RIGHT);
		asientosEjecutivoDispDato.setEditable(false);
		panel_1.add(asientosEjecutivoDispDato);
		
		panel_1.add(fechaAlta);
		fechaAlta.setHorizontalAlignment(SwingConstants.RIGHT);
		
		fechaAltaDato.setEditable(false);
		panel_1.add(fechaAltaDato);
		
		
		panel_1.add(reser);
		reser.setHorizontalAlignment(SwingConstants.RIGHT);
		
		panel_1.add(reservasCombo);
		JLabel espacio = new JLabel("");
		espacio.setVisible(false);
		
		panel_1.add(espacio);
		panel_1.add(cerrar);
		
		//Volver todos los datos invisibles hasta que se seleccione el vuelo
		fecha.setVisible(false);
		fechaDato.setVisible(false);
		
		duracion.setVisible(false);
		duracionDato.setVisible(false);
		
		cantAsientosTuristaMax.setVisible(false);
		cantAsientosTuristaMaxDato.setVisible(false);
		
		
		cantAsientosEjecutivoMax.setVisible(false);
		cantAsientosEjecutivoMaxDato.setVisible(false);
		
		asientosTuristaDisp.setVisible(false);
		asientosTuristaDispDato.setVisible(false);
		asientosEjecutivoDisp.setVisible(false);
		asientosEjecutivoDispDato.setVisible(false);
		
		fechaAlta.setVisible(false);
		fechaAltaDato.setVisible(false);
		
		reser.setVisible(false);
		reservasCombo.setVisible(false);
		
		aerolineaCombo.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				if(aerolineaCombo.getSelectedItem()!=null) {
					rutaCombo.removeAllItems();
					List<String> rutas = null;
					try {
						rutas = CRV.listarRutasDeVuelo((String)aerolineaCombo.getSelectedItem());
					} catch (UsuarioNoEsAerolineaExcepcion e1) {
						//Nunca pasa esto
						e1.printStackTrace();
					}
					
					for(String r:rutas) {
						rutaCombo.addItem(r);
					}
					rutaCombo.setVisible(true);
					vueloCombo.setVisible(false);
				}
			}
		});
		
		
		rutaCombo.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				vueloCombo.setVisible(false);
				if(rutaCombo.getSelectedItem() != null) {
					vueloCombo.removeAllItems();
					List<String> vuelos = null;
		
					try {
						vuelos = CRV.listarVuelos((String)aerolineaCombo.getSelectedItem(), (String)rutaCombo.getSelectedItem());
					} catch (VueloNoExisteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(vuelos != null) {
						for(String v:vuelos) {
							vueloCombo.addItem(v);
						}
						vueloCombo.setVisible(true);
					}
					
					//Volver todos los datos invisibles hasta que se seleccione el vuelo
					fecha.setVisible(false);
					fechaDato.setVisible(false);
					
					duracion.setVisible(false);
					duracionDato.setVisible(false);
					
					cantAsientosTuristaMax.setVisible(false);
					cantAsientosTuristaMaxDato.setVisible(false);
					
					cantAsientosEjecutivoMax.setVisible(false);
					cantAsientosEjecutivoMaxDato.setVisible(false);
					
					
					asientosTuristaDisp.setVisible(false);
					asientosTuristaDispDato.setVisible(false);
					asientosEjecutivoDisp.setVisible(false);
					asientosEjecutivoDispDato.setVisible(false);
					
					fechaAlta.setVisible(false);
					fechaAltaDato.setVisible(false);
					
					reser.setVisible(false);
					reservasCombo.setVisible(false);
				}
			}
		});
		
		cerrar.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				hideAll();
				hide();
				dispose();
			}
		});
		
		vueloCombo.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				if(vueloCombo.getSelectedItem() != null) {
					FlightDTO datosVuelo = CRV.obtenerInfoVuelo((String)rutaCombo.getSelectedItem(), (String)vueloCombo.getSelectedItem());
					
					fechaDato.setText(datosVuelo.getFechaVuelo().toString());
					fecha.setVisible(true);
					fechaDato.setVisible(true);
					
					duracionDato.setText(""+datosVuelo.getDuracion().toHours()+":"+datosVuelo.getDuracion().toMinutesPart());
					duracion.setVisible(true);
					duracionDato.setVisible(true);
					
					cantAsientosTuristaMaxDato.setText(""+datosVuelo.getMaxTurista());
					cantAsientosTuristaMax.setVisible(true);
					cantAsientosTuristaMaxDato.setVisible(true);
					
					cantAsientosEjecutivoMaxDato.setText(""+datosVuelo.getMaxEjecutivo());
					cantAsientosEjecutivoMax.setVisible(true);
					cantAsientosEjecutivoMaxDato.setVisible(true);
					
					
					asientosTuristaDispDato.setText(""+datosVuelo.getTuristaDisponible());
					asientosTuristaDispDato.setVisible(true); 
					asientosTuristaDisp.setVisible(true);
					
					asientosEjecutivoDispDato.setText(""+datosVuelo.getEjecutivoDisponible());
					asientosEjecutivoDispDato.setVisible(true);
					asientosEjecutivoDisp.setVisible(true);
					
					fechaAltaDato.setText(datosVuelo.getFechaAlta().toString());
					fechaAlta.setVisible(true);
					fechaAltaDato.setVisible(true);
					
					reser.setVisible(true);
					reservasCombo.removeAllItems();
					
					ArrayList<String> reservas = (ArrayList<String>) CRV.listarReservas(datosVuelo.getNombre());
					if(reservas!=null) {
						for(String s: reservas) {
							reservasCombo.addItem(s);
						}
						reservasCombo.setVisible(true);
					}
					else reservasCombo.setVisible(false);
				}
			}
		});
	}
	
	public void hideAll() {
		rutaCombo.setVisible(false);
		vueloCombo.setVisible(false);
		
		fecha.setVisible(false);
		fechaDato.setVisible(false);
		
		duracion.setVisible(false);
		duracionDato.setVisible(false);
		
		cantAsientosTuristaMax.setVisible(false);
		cantAsientosTuristaMaxDato.setVisible(false);
		
		cantAsientosEjecutivoMax.setVisible(false);
		cantAsientosEjecutivoMaxDato.setVisible(false);
		
		asientosTuristaDisp.setVisible(false);
		asientosTuristaDispDato.setVisible(false);
		asientosEjecutivoDisp.setVisible(false);
		asientosEjecutivoDispDato.setVisible(false);
		
		fechaAlta.setVisible(false);
		fechaAltaDato.setVisible(false);
		
		reser.setVisible(false);
		reservasCombo.setVisible(false);
	}
	
	public void setAerolineaSeleccionada(String a) {
		aerolineaSeleccionada = a;
	}
	
	public void setRutaSeleccionada(String r) {
		rutaSeleccionada = r;
	}
	
	public void setVueloSeleccionado(String v) {
		vueloSeleccionado = v;
	}

}
