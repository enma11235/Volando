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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JScrollPane;
import com.toedter.components.JSpinField;
import com.toedter.calendar.JDateChooser;
import javax.swing.JTable;
import javax.swing.JSpinner;
import javax.swing.table.DefaultTableModel;

public class ReservarVuelo extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private List<String> aerolineas = null;
	private JComboBox<String> aerolineaCombo = new JComboBox<String>();
	private JComboBox<String> rutaCombo = new JComboBox<String>();
	private JLabel aerolineaL = new JLabel("Aerolinea:");
	private JLabel vueloL = new JLabel("Vuelo:");
	private JLabel rutaL = new JLabel("Ruta:");
	private JComboBox<String> vueloCombo = new JComboBox<String>();
	private JComboBox<String> comboAsiento = new JComboBox<String>();
	private JLabel fecha = new JLabel("Fecha:");
	private JTextField fechaDato = new JTextField();
	private JLabel duracion = new JLabel("Duración:");
	private JTextField duracionDato = new JTextField();
	private JLabel cantAsientosTuristaDisponibles = new JLabel("Asientos turista disponibles:");
	private JTextField cantAsientosTuristaDisponiblesDato = new JTextField();
	private JLabel cantAsientosEjecutivoDisponibles = new JLabel("Asientos ejecutivo disponibles:");
	private JTextField cantAsientosEjecutivoDisponiblesDato = new JTextField();
	private JLabel fechaAlta = new JLabel("Fecha de alta:");
	private JTextField fechaAltaDato = new JTextField();
	private JLabel cli = new JLabel("Clientes:");
	private JComboBox<String> comboClientes = new JComboBox<String>();
	private JButton cerrar = new JButton("Cancelar");
	DefaultTableModel model = new DefaultTableModel(new Object[]{"Nombre", "Apellido"}, 0);
	private JSpinField spinnerEquipaje;
	private JDateChooser dateChooser;
	
	private String aerolineaSeleccionada = null;
	private DTVuelo datosVuelo;
	private String rutaSeleccionada = null;
	private String vueloSeleccionado = null;
	private final JPanel panel = new JPanel();
	private JScrollPane scrollPane;
	private final JScrollPane scrollPane_1 = new JScrollPane();
	private final JTable table = new JTable();
	private final JSpinner spinnerPasajes = new JSpinner();
	
	private IControladorRutaDeVuelo CRV;

	/**
	 * Create the frame.
	 */
	public ReservarVuelo(IControladorUsuario CU, IControladorRutaDeVuelo CRV) {
		this.CRV=CRV;
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(0, 0, 558, 497);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Alta reserva");
		setContentPane(contentPane);

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
					JOptionPane.showMessageDialog(e.getComponent(), "No existen aerolineas");
					hide();
				}

				aerolineaCombo.removeAllItems();
				if (aerolineas != null) {
					String a1 = "";
//					aerolineaCombo.addItem("Seleccionar");
					for (String a : aerolineas) {
						aerolineaCombo.addItem(a);
						a1 = a;
					}
					aerolineaCombo.setSelectedItem(a1);
//					rutaCombo.setVisible(false);
				}
				if (aerolineaSeleccionada != null) {
					aerolineaCombo.setSelectedItem(aerolineaSeleccionada);
					aerolineaSeleccionada = null;
					if (rutaSeleccionada != null) {
						rutaCombo.setSelectedItem(rutaSeleccionada);
						rutaSeleccionada = null;
						if (vueloSeleccionado != null) {
							vueloCombo.setSelectedItem(vueloSeleccionado);
							vueloSeleccionado = null;
						}
					}
				}
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				ReservarVuelo cvg = (ReservarVuelo) e.getComponent();
				cvg.hideAll();
			}

		});
		contentPane.setLayout(null);
		panel.setBounds(0, 11, 512, 269);
		contentPane.add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{83, 124, 35, 235, 0};
		gbl_panel.rowHeights = new int[]{30, 22, 22, 22, 20, 20, 20, 20, 20, 22, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		GridBagConstraints gbc_aerolineaL = new GridBagConstraints();
		gbc_aerolineaL.anchor = GridBagConstraints.EAST;
		gbc_aerolineaL.insets = new Insets(0, 0, 5, 5);
		gbc_aerolineaL.gridx = 1;
		gbc_aerolineaL.gridy = 1;
		panel.add(aerolineaL, gbc_aerolineaL);
		aerolineaL.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_aerolineaCombo = new GridBagConstraints();
		gbc_aerolineaCombo.fill = GridBagConstraints.BOTH;
		gbc_aerolineaCombo.insets = new Insets(0, 0, 5, 0);
		gbc_aerolineaCombo.gridx = 3;
		gbc_aerolineaCombo.gridy = 1;
		panel.add(aerolineaCombo, gbc_aerolineaCombo);
		
				aerolineaCombo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (aerolineaCombo.getSelectedItem() != null) {
							rutaCombo.removeAllItems();
							List<String> rutas = null;
							try {
								rutas = CRV.listarRutasConfirmadas((String) aerolineaCombo.getSelectedItem());
							} catch (UsuarioNoEsAerolineaExcepcion e1) {
								// Nunca pasa esto
								e1.printStackTrace();
							}
		//					rutaCombo.addItem("Seleccionar");
							String r1 = "";
							for (String r : rutas) {
								rutaCombo.addItem(r);
								r1 = r;
							}
							if (r1 != "")
								rutaCombo.setSelectedItem(r1);
		//					rutaCombo.setVisible(true);
		//					vueloCombo.setVisible(false);
						}
					}
				});
		GridBagConstraints gbc_rutaL = new GridBagConstraints();
		gbc_rutaL.anchor = GridBagConstraints.EAST;
		gbc_rutaL.insets = new Insets(0, 0, 5, 5);
		gbc_rutaL.gridx = 1;
		gbc_rutaL.gridy = 2;
		panel.add(rutaL, gbc_rutaL);
		rutaL.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_rutaCombo = new GridBagConstraints();
		gbc_rutaCombo.fill = GridBagConstraints.HORIZONTAL;
		gbc_rutaCombo.insets = new Insets(0, 0, 5, 0);
		gbc_rutaCombo.gridx = 3;
		gbc_rutaCombo.gridy = 2;
		panel.add(rutaCombo, gbc_rutaCombo);
		
				rutaCombo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
		//				vueloCombo.setVisible(false);
						if (rutaCombo.getSelectedItem() != null) {
							vueloCombo.removeAllItems();
							List<String> vuelos = null;
		
							try {
								vuelos = CRV.listarVuelos((String) aerolineaCombo.getSelectedItem(),
										(String) rutaCombo.getSelectedItem());
							} catch (VueloNoExisteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							if (vuelos != null) {
								vueloCombo.addItem("Seleccionar");
								for (String v : vuelos) {
									vueloCombo.addItem(v);
								}
		//						vueloCombo.setVisible(true);
							}
		
							// Volver todos los datos invisibles hasta que se seleccione el vuelo
							fecha.setVisible(false);
							fechaDato.setVisible(false);
		
							duracion.setVisible(false);
							duracionDato.setVisible(false);
		
							cantAsientosTuristaDisponibles.setVisible(false);
							cantAsientosTuristaDisponiblesDato.setVisible(false);
		
							cantAsientosEjecutivoDisponibles.setVisible(false);
							cantAsientosEjecutivoDisponiblesDato.setVisible(false);
		
							fechaAlta.setVisible(false);
							fechaAltaDato.setVisible(false);
		
							cli.setVisible(false);
							comboClientes.setVisible(false);
						}
					}
				});
		GridBagConstraints gbc_vueloL = new GridBagConstraints();
		gbc_vueloL.anchor = GridBagConstraints.EAST;
		gbc_vueloL.insets = new Insets(0, 0, 5, 5);
		gbc_vueloL.gridx = 1;
		gbc_vueloL.gridy = 3;
		panel.add(vueloL, gbc_vueloL);
		vueloL.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_vueloCombo = new GridBagConstraints();
		gbc_vueloCombo.fill = GridBagConstraints.HORIZONTAL;
		gbc_vueloCombo.insets = new Insets(0, 0, 5, 0);
		gbc_vueloCombo.gridx = 3;
		gbc_vueloCombo.gridy = 3;
		panel.add(vueloCombo, gbc_vueloCombo);
		
				vueloCombo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (vueloCombo.getSelectedItem() != null && vueloCombo.getSelectedItem() != "Seleccionar") {
							vueloCombo.removeItem("Seleccionar");
							datosVuelo = CRV.obtenerInfoVuelo((String) rutaCombo.getSelectedItem(),
									(String) vueloCombo.getSelectedItem());
		
							fechaDato.setText(datosVuelo.getFechaVuelo().toString());
							fecha.setVisible(true);
							fechaDato.setVisible(true);
		
							duracionDato.setText(
									"" + datosVuelo.getDuracion().toHours() + ":" + datosVuelo.getDuracion().toMinutesPart());
							duracion.setVisible(true);
							duracionDato.setVisible(true);
		
							cantAsientosTuristaDisponiblesDato.setText("" + datosVuelo.getTuristaDisponible());
							cantAsientosTuristaDisponibles.setVisible(true);
							cantAsientosTuristaDisponiblesDato.setVisible(true);
		
							cantAsientosEjecutivoDisponiblesDato.setText("" + datosVuelo.getEjecutivoDisponible());
							cantAsientosEjecutivoDisponibles.setVisible(true);
							cantAsientosEjecutivoDisponiblesDato.setVisible(true);
		
							fechaAltaDato.setText(datosVuelo.getFechaAlta().toString());
							fechaAlta.setVisible(true);
							fechaAltaDato.setVisible(true);
		
							cli.setVisible(true);
							comboClientes.removeAllItems();
		
							try {
								ArrayList<String> clientes = (ArrayList<String>) CU.listarClientes();
								if (clientes != null) {
		//											reservasCombo.addItem("Seleccionar");
									for (String s : clientes) {
										comboClientes.addItem(s);
									}
									comboClientes.setVisible(true);
								} else
									comboClientes.setVisible(false);
		
							} catch (Exception e1) {
		
							}
						}
					}
		
				});
		GridBagConstraints gbc_fecha = new GridBagConstraints();
		gbc_fecha.anchor = GridBagConstraints.EAST;
		gbc_fecha.insets = new Insets(0, 0, 5, 5);
		gbc_fecha.gridx = 1;
		gbc_fecha.gridy = 4;
		panel.add(fecha, gbc_fecha);
		fecha.setHorizontalAlignment(SwingConstants.RIGHT);
		
				// Volver todos los datos invisibles hasta que se seleccione el vuelo
				fecha.setVisible(false);
		GridBagConstraints gbc_fechaDato = new GridBagConstraints();
		gbc_fechaDato.fill = GridBagConstraints.HORIZONTAL;
		gbc_fechaDato.insets = new Insets(0, 0, 5, 0);
		gbc_fechaDato.gridx = 3;
		gbc_fechaDato.gridy = 4;
		panel.add(fechaDato, gbc_fechaDato);
		
				fechaDato.setEditable(false);
				fechaDato.setVisible(false);
		GridBagConstraints gbc_duracion = new GridBagConstraints();
		gbc_duracion.anchor = GridBagConstraints.EAST;
		gbc_duracion.insets = new Insets(0, 0, 5, 5);
		gbc_duracion.gridx = 1;
		gbc_duracion.gridy = 5;
		panel.add(duracion, gbc_duracion);
		duracion.setHorizontalAlignment(SwingConstants.RIGHT);
		
				duracion.setVisible(false);
		GridBagConstraints gbc_duracionDato = new GridBagConstraints();
		gbc_duracionDato.fill = GridBagConstraints.HORIZONTAL;
		gbc_duracionDato.insets = new Insets(0, 0, 5, 0);
		gbc_duracionDato.gridx = 3;
		gbc_duracionDato.gridy = 5;
		panel.add(duracionDato, gbc_duracionDato);
		
				duracionDato.setEditable(false);
				duracionDato.setVisible(false);
		GridBagConstraints gbc_cantAsientosTuristaMax = new GridBagConstraints();
		gbc_cantAsientosTuristaMax.anchor = GridBagConstraints.EAST;
		gbc_cantAsientosTuristaMax.insets = new Insets(0, 0, 5, 5);
		gbc_cantAsientosTuristaMax.gridx = 1;
		gbc_cantAsientosTuristaMax.gridy = 6;
		panel.add(cantAsientosTuristaDisponibles, gbc_cantAsientosTuristaMax);
		cantAsientosTuristaDisponibles.setHorizontalAlignment(SwingConstants.RIGHT);
		
				cantAsientosTuristaDisponibles.setVisible(false);
		GridBagConstraints gbc_cantAsientosTuristaMaxDato = new GridBagConstraints();
		gbc_cantAsientosTuristaMaxDato.fill = GridBagConstraints.HORIZONTAL;
		gbc_cantAsientosTuristaMaxDato.insets = new Insets(0, 0, 5, 0);
		gbc_cantAsientosTuristaMaxDato.gridx = 3;
		gbc_cantAsientosTuristaMaxDato.gridy = 6;
		panel.add(cantAsientosTuristaDisponiblesDato, gbc_cantAsientosTuristaMaxDato);
		
				cantAsientosTuristaDisponiblesDato.setEditable(false);
				cantAsientosTuristaDisponiblesDato.setVisible(false);
		GridBagConstraints gbc_cantAsientosEjecutivoMax = new GridBagConstraints();
		gbc_cantAsientosEjecutivoMax.anchor = GridBagConstraints.EAST;
		gbc_cantAsientosEjecutivoMax.insets = new Insets(0, 0, 5, 5);
		gbc_cantAsientosEjecutivoMax.gridx = 1;
		gbc_cantAsientosEjecutivoMax.gridy = 7;
		panel.add(cantAsientosEjecutivoDisponibles, gbc_cantAsientosEjecutivoMax);
		cantAsientosEjecutivoDisponibles.setHorizontalAlignment(SwingConstants.RIGHT);
		
				cantAsientosEjecutivoDisponibles.setVisible(false);
		GridBagConstraints gbc_cantAsientosEjecutivoMaxDato = new GridBagConstraints();
		gbc_cantAsientosEjecutivoMaxDato.fill = GridBagConstraints.HORIZONTAL;
		gbc_cantAsientosEjecutivoMaxDato.insets = new Insets(0, 0, 5, 0);
		gbc_cantAsientosEjecutivoMaxDato.gridx = 3;
		gbc_cantAsientosEjecutivoMaxDato.gridy = 7;
		panel.add(cantAsientosEjecutivoDisponiblesDato, gbc_cantAsientosEjecutivoMaxDato);
		
				cantAsientosEjecutivoDisponiblesDato.setEditable(false);
				cantAsientosEjecutivoDisponiblesDato.setVisible(false);
		GridBagConstraints gbc_fechaAlta = new GridBagConstraints();
		gbc_fechaAlta.anchor = GridBagConstraints.EAST;
		gbc_fechaAlta.insets = new Insets(0, 0, 5, 5);
		gbc_fechaAlta.gridx = 1;
		gbc_fechaAlta.gridy = 8;
		panel.add(fechaAlta, gbc_fechaAlta);
		fechaAlta.setHorizontalAlignment(SwingConstants.RIGHT);
		
				fechaAlta.setVisible(false);
		GridBagConstraints gbc_fechaAltaDato = new GridBagConstraints();
		gbc_fechaAltaDato.fill = GridBagConstraints.HORIZONTAL;
		gbc_fechaAltaDato.insets = new Insets(0, 0, 5, 0);
		gbc_fechaAltaDato.gridx = 3;
		gbc_fechaAltaDato.gridy = 8;
		panel.add(fechaAltaDato, gbc_fechaAltaDato);
		
				fechaAltaDato.setEditable(false);
				fechaAltaDato.setVisible(false);
		GridBagConstraints gbc_cli = new GridBagConstraints();
		gbc_cli.anchor = GridBagConstraints.EAST;
		gbc_cli.insets = new Insets(0, 0, 0, 5);
		gbc_cli.gridx = 1;
		gbc_cli.gridy = 9;
		panel.add(cli, gbc_cli);
		cli.setHorizontalAlignment(SwingConstants.RIGHT);
		
				cli.setVisible(false);
		GridBagConstraints gbc_comboClientes = new GridBagConstraints();
		gbc_comboClientes.fill = GridBagConstraints.BOTH;
		gbc_comboClientes.gridx = 3;
		gbc_comboClientes.gridy = 9;
		
		panel.add(comboClientes, gbc_comboClientes);
		comboClientes.setVisible(false);
		cerrar.setBounds(423, 437, 89, 23);
		contentPane.add(cerrar);
		
		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBounds(28, 280, 501, 152);
		contentPane.add(scrollPane);
		
		JPanel panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 131, 34, 133, 0};
		gbl_panel_1.rowHeights = new int[]{0, 21, 0, 29, 36, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblNewLabel = new JLabel("Tipo asiento");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		panel_1.add(lblNewLabel, gbc_lblNewLabel);
		
		comboAsiento = new JComboBox();
		GridBagConstraints gbc_comboAsiento = new GridBagConstraints();
		gbc_comboAsiento.insets = new Insets(0, 0, 5, 0);
		gbc_comboAsiento.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboAsiento.gridx = 3;
		gbc_comboAsiento.gridy = 0;
		panel_1.add(comboAsiento, gbc_comboAsiento);
		comboAsiento.addItem("Turista");
		comboAsiento.addItem("Ejecutivo");
		
		JLabel lblNewLabel_1 = new JLabel("Cant. pasajes:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 1;
		panel_1.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		GridBagConstraints gbc_spinnerPasajes = new GridBagConstraints();
		gbc_spinnerPasajes.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerPasajes.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerPasajes.gridx = 3;
		gbc_spinnerPasajes.gridy = 1;
		panel_1.add(spinnerPasajes, gbc_spinnerPasajes);
		
		
		JLabel lblNewLabel_2 = new JLabel("Cant. equipaje extra:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 2;
		panel_1.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		spinnerEquipaje = new JSpinField();
		spinnerEquipaje.setMinimum(0);
		GridBagConstraints gbc_spinnerEquipaje = new GridBagConstraints();
		gbc_spinnerEquipaje.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerEquipaje.fill = GridBagConstraints.BOTH;
		gbc_spinnerEquipaje.gridx = 3;
		gbc_spinnerEquipaje.gridy = 2;
		panel_1.add(spinnerEquipaje, gbc_spinnerEquipaje);
		
		JLabel lblNewLabel_3 = new JLabel("Fecha alta");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 3;
		panel_1.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		dateChooser = new JDateChooser();
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.insets = new Insets(0, 0, 5, 0);
		gbc_dateChooser.fill = GridBagConstraints.BOTH;
		gbc_dateChooser.gridx = 3;
		gbc_dateChooser.gridy = 3;
		panel_1.add(dateChooser, gbc_dateChooser);
		dateChooser.setDate(new Date());
		
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridwidth = 3;
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 4;
		panel_1.add(scrollPane_1, gbc_scrollPane_1);
		table.setModel(model);
		
		scrollPane_1.setViewportView(table);
		
		JButton btnNewButton = new JButton("Aceptar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ejecutarAlta();
			}
		});
		btnNewButton.setBounds(337, 437, 76, 23);
		contentPane.add(btnNewButton);
		
		comboClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scrollPane.setVisible(true);
			}
		});
		
		spinnerPasajes.addChangeListener(e -> {
			int pasajes = (int) spinnerPasajes.getValue();
			
			if(pasajes>=1) {
				if (pasajes < table.getRowCount()) {
		            for (int x = table.getRowCount() - 1; x >= pasajes; x--) {
		                model.removeRow(x);
		            }
				}
				model.setRowCount(pasajes-1);
			}
		});

		cerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarFormulario();
				hideAll();
				hide();
			}
		});
	}

	public void hideAll() {
//		rutaCombo.setVisible(false);
//		vueloCombo.setVisible(false);

		fecha.setVisible(false);
		fechaDato.setVisible(false);

		duracion.setVisible(false);
		duracionDato.setVisible(false);

		cantAsientosTuristaDisponibles.setVisible(false);
		cantAsientosTuristaDisponiblesDato.setVisible(false);

		cantAsientosEjecutivoDisponibles.setVisible(false);
		cantAsientosEjecutivoDisponiblesDato.setVisible(false);

		fechaAlta.setVisible(false);
		fechaAltaDato.setVisible(false);

		cli.setVisible(false);
		comboClientes.setVisible(false);
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
	
	private void ejecutarAlta() {
		if(checkDatos()) {
			try {
				LocalDate fechaAlta = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				String cliente = (String) comboClientes.getSelectedItem();
				String vuelo = (String) vueloCombo.getSelectedItem();
				int pasajes = (int) spinnerPasajes.getValue();
				int equipaje = spinnerEquipaje.getValue();
				SeatType ta = SeatType.TOURIST;
				if ((String) comboAsiento.getSelectedItem() == "Ejecutivo") {
					ta = SeatType.EXECUTIVE;
				}
				ArrayList<DTPasaje> arrPasajes = new ArrayList<DTPasaje>();
				for(int x=0; x<model.getRowCount(); x++) {
					String nombre = (String) model.getValueAt(x, 0); 
			        String apellido = (String) model.getValueAt(x, 1);
			        DTPasaje dtp = new DTPasaje(nombre, apellido,0);
			        arrPasajes.add(dtp);
				}
				
				
		        float costo = CRV.reservarVuelo(cliente, vuelo, ta, pasajes, equipaje, arrPasajes, fechaAlta);
		        JOptionPane.showMessageDialog(this, "Operación finalizada con costo: "+costo, "",
	                    JOptionPane.INFORMATION_MESSAGE);
		        limpiarFormulario();
		        hide();
			}catch(Exception e) {
				 JOptionPane.showMessageDialog(this, e.getMessage(), "",
		                    JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	

	
	private boolean checkDatos() {
		
		if(vueloCombo.getSelectedItem() == null || comboClientes.getSelectedItem()==null || comboAsiento.getSelectedItem() == null || (String) vueloCombo.getSelectedItem() == "Seleccionar") {
	         JOptionPane.showMessageDialog(this, "No puede haber campos vacíos", "",
	                    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		LocalDate fechaAlta;
        
        try {
	        Date fechaAltaDate = dateChooser.getDate();
	        fechaAlta = fechaAltaDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }catch(Exception e) {
        	JOptionPane.showMessageDialog(this, "Fecha inválida", "",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
		
		
		
		int pasajes = (int) spinnerPasajes.getValue();
		int equipaje = spinnerEquipaje.getValue();
		
		if(pasajes <= 0  || equipaje < 0) {
	         JOptionPane.showMessageDialog(this, "No puede haber cantidades negativas de equipaje, ni menores que 1 de pasaje", "",
	                    JOptionPane.ERROR_MESSAGE);
	         return false;
		}
		
		boolean maximaCap = false;
		int maximo = 0;
		switch((String) comboAsiento.getSelectedItem()) {
		case "Ejecutivo":
			maximo = datosVuelo.getEjecutivoDisponible();
			maximaCap = (pasajes>maximo);
			break;
		case "Turista":
			maximo = datosVuelo.getTuristaDisponible();
			maximaCap = (pasajes>maximo);
			break;
		}
		
		if (maximaCap) {
			JOptionPane.showMessageDialog(this, "No puede comprar más pasajes que el máximo ("+maximo+")", "",
                    JOptionPane.ERROR_MESSAGE);
         return false;
		}
		
		ArrayList<DTPasaje> arrPasajes = new ArrayList<DTPasaje>();
		for(int x=0; x<model.getRowCount(); x++) {
			String nombre = (String) model.getValueAt(x, 0); 
	        String apellido = (String) model.getValueAt(x, 1);

	        if (nombre == null || nombre.trim().isEmpty() || apellido == null || apellido.trim().isEmpty()) {
	            JOptionPane.showMessageDialog(this, "No puede haber nombres o apellidos vacíos", "", JOptionPane.ERROR_MESSAGE);
	            return false;
	        }
	        DTPasaje dtp = new DTPasaje(nombre, apellido,0);
	        arrPasajes.add(dtp);
		}
		
		if(arrPasajes.size() != pasajes-1) {
			JOptionPane.showMessageDialog(this, "Cantidad de pasajes distinta a ingresados", "", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
		
	}
	
	private void limpiarFormulario() {
		aerolineaCombo.removeAllItems();
		rutaCombo.removeAllItems();
		vueloCombo.removeAllItems();
		comboClientes.removeAllItems();
		model.setRowCount(0);
		spinnerEquipaje.setValue(0);
		spinnerPasajes.setValue(0);
		dateChooser.setDate(new Date());
		scrollPane.setVisible(false);
	}
	
}
