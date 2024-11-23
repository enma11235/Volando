package presentacion;

import javax.swing.JInternalFrame;

import excepciones.CiudadRepetidaException;
import excepciones.RutaDeVueloRepetidaException;
import excepciones.UsuarioNoExisteException;
import datatype.*;
import service.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

import javax.swing.AbstractCellEditor;
import javax.swing.DropMode;
import com.toedter.calendar.JDayChooser;
import java.awt.BorderLayout;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class ConsultaDePaquete extends JInternalFrame {

	// falta todo !!!!

	private static final long serialVersionUID = 1L;
	// Controlador de rutas de vuelo que se utilizará para las acciones del JFrame
	private IControladorPaquete controlP;
	private JTextField txtPeriodo;
	private JTextField txtDescuento;
	private JTextField txtFechaAlta;
	private JTextArea txtDescr;
	private JTable table;
	private JComboBox<String> comboBox;
	private String paqueteSeleccionado = null; 

	/**
	 * Create the frame.
	 */
	public ConsultaDePaquete(IControladorPaquete ip, IControladorRutaDeVuelo irv) {

		// Se inicializa con el controlador de p
		controlP = ip;
		
		// Propiedades del JInternalFrame como dimensión, posición dentro del frame,
		// etc.
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("Consulta de paquete");
		setBounds(0, 0, 630, 470);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 135, 25, 405, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JLabel lblNewLabel = new JLabel("Seleccionar paquete:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		 this.addComponentListener(new ComponentListener() {
			
				@Override
				public void componentShown(ComponentEvent e) {
					
					if(paqueteSeleccionado != null) {	
							
								DTPaquete dt = ip.obtenerInfoPaquete(paqueteSeleccionado);
								txtDescr.setText(dt.getDescripcion());
								txtDescuento.setText(Float.toString(dt.getDescuento()));
								txtFechaAlta.setText(dt.getFechaAlta().toString());
								txtPeriodo.setText("Días: " + dt.getPeriodoValidez().toDays());
								ArrayList<DTRutaPaquete> dtrq = (ArrayList<DTRutaPaquete>) dt.getRutas();
								ArrayList<Object[]> rutasTable = new ArrayList<>();

								DefaultTableModel model = (DefaultTableModel) table.getModel();
								model.setRowCount(0);

								for (int x = 0; x < dtrq.size(); x++) {
									String ta = "";
									switch (dtrq.get(x).getTipoAsiento()) {
									case EXECUTIVE:
										ta = "Ejecutivo";
										break;
									case TOURIST:
										ta = "Turista";
										break;
									}
									Object[] ruta = { irv.obtenerNicknameAerolineaDeRuta(dtrq.get(x).getRuta().getNombre()), dtrq.get(x).getRuta().getNombre(),
											Integer.toString(dtrq.get(x).getCantidad()), Float.toString(dtrq.get(x).getCosto()), ta,
											"9" };
									rutasTable.add(ruta);
								}
								for (Object[] row : rutasTable) {
									model.addRow(row);
								}						
						}
					if(paqueteSeleccionado != null) {
						comboBox.setSelectedItem(paqueteSeleccionado );
						paqueteSeleccionado  = null;
					}
				}

				@Override
				public void componentResized(ComponentEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void componentMoved(ComponentEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void componentHidden(ComponentEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				
			});
		
		comboBox = new JComboBox<>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paqueteSeleccionado = (String) comboBox.getSelectedItem();
				if (paqueteSeleccionado != "" && paqueteSeleccionado != null) {
					DTPaquete dt = ip.obtenerInfoPaquete(paqueteSeleccionado);
					txtDescr.setText(dt.getDescripcion());
					txtDescuento.setText(Float.toString(dt.getDescuento()));
					txtFechaAlta.setText(dt.getFechaAlta().toString());
					txtPeriodo.setText("Días: " + dt.getPeriodoValidez().toDays());
					ArrayList<DTRutaPaquete> dtrq = (ArrayList<DTRutaPaquete>) dt.getRutas();
					ArrayList<Object[]> rutasTable = new ArrayList<>();

					DefaultTableModel model = (DefaultTableModel) table.getModel();
					model.setRowCount(0);

					for (int x = 0; x < dtrq.size(); x++) {
						String ta = "";
						switch (dtrq.get(x).getTipoAsiento()) {
						case EXECUTIVE:
							ta = "Ejecutivo";
							break;
						case TOURIST:
							ta = "Turista";
							break;
						}
						Object[] ruta = { irv.obtenerNicknameAerolineaDeRuta(dtrq.get(x).getRuta().getNombre()), dtrq.get(x).getRuta().getNombre(),
								Integer.toString(dtrq.get(x).getCantidad()), Float.toString(dtrq.get(x).getCosto()), ta,
								"9" };
						rutasTable.add(ruta);
					}
					for (Object[] row : rutasTable) {
						model.addRow(row);
					}
				}

			}
		});
		comboBox.addPopupMenuListener(new PopupMenuListener() {
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				if (comboBox.getItemCount() == 0) {
					cargarDatos();
				}
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
		});

		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 3;
		gbc_comboBox.gridy = 1;
		getContentPane().add(comboBox, gbc_comboBox);

		JLabel lblNewLabel_1 = new JLabel("Descripción:");
		lblNewLabel_1.setBackground(new Color(240, 240, 240));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 3;
		getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);

		txtDescr = new JTextArea();
		txtDescr.setLineWrap(true);
		txtDescr.setBackground(new Color(255, 255, 255));
		txtDescr.setEditable(false);
		GridBagConstraints gbc_txtDescr = new GridBagConstraints();
		gbc_txtDescr.insets = new Insets(0, 0, 5, 5);
		gbc_txtDescr.fill = GridBagConstraints.BOTH;
		gbc_txtDescr.gridx = 3;
		gbc_txtDescr.gridy = 3;
		getContentPane().add(txtDescr, gbc_txtDescr);

		JLabel lblNewLabel_1_1 = new JLabel("Período de validez:");
		GridBagConstraints gbc_lblNewLabel_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_1.gridx = 1;
		gbc_lblNewLabel_1_1.gridy = 4;
		getContentPane().add(lblNewLabel_1_1, gbc_lblNewLabel_1_1);

		txtPeriodo = new JTextField();
		txtPeriodo.setEditable(false);
		txtPeriodo.setColumns(10);
		GridBagConstraints gbc_txtPeriodo = new GridBagConstraints();
		gbc_txtPeriodo.insets = new Insets(0, 0, 5, 5);
		gbc_txtPeriodo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPeriodo.gridx = 3;
		gbc_txtPeriodo.gridy = 4;
		getContentPane().add(txtPeriodo, gbc_txtPeriodo);

		JLabel lblNewLabel_1_2 = new JLabel("Descuento:");
		GridBagConstraints gbc_lblNewLabel_1_2 = new GridBagConstraints();
		gbc_lblNewLabel_1_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_2.gridx = 1;
		gbc_lblNewLabel_1_2.gridy = 5;
		getContentPane().add(lblNewLabel_1_2, gbc_lblNewLabel_1_2);

		txtDescuento = new JTextField();
		txtDescuento.setEditable(false);
		txtDescuento.setColumns(10);
		GridBagConstraints gbc_txtDescuento = new GridBagConstraints();
		gbc_txtDescuento.insets = new Insets(0, 0, 5, 5);
		gbc_txtDescuento.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDescuento.gridx = 3;
		gbc_txtDescuento.gridy = 5;
		getContentPane().add(txtDescuento, gbc_txtDescuento);

		JLabel lblNewLabel_1_3 = new JLabel("Fecha de alta:");
		GridBagConstraints gbc_lblNewLabel_1_3 = new GridBagConstraints();
		gbc_lblNewLabel_1_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_3.gridx = 1;
		gbc_lblNewLabel_1_3.gridy = 6;
		getContentPane().add(lblNewLabel_1_3, gbc_lblNewLabel_1_3);

		txtFechaAlta = new JTextField();
		txtFechaAlta.setEditable(false);
		txtFechaAlta.setColumns(10);
		GridBagConstraints gbc_txtFechaAlta = new GridBagConstraints();
		gbc_txtFechaAlta.insets = new Insets(0, 0, 5, 5);
		gbc_txtFechaAlta.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFechaAlta.gridx = 3;
		gbc_txtFechaAlta.gridy = 6;
		getContentPane().add(txtFechaAlta, gbc_txtFechaAlta);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 8;
		getContentPane().add(scrollPane, gbc_scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Aerolinea", "Nombre de ruta", "Cantidad", "Costo", "Tipo Asiento", "Visualizar" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// Solo permitir "editar" la columna del botón
				return column == 5;
			}

		});
		//La columna "Visualizar" va a cargar los botones por los métodos en las clases debajo que son override de las default de Swing para tables
		table.getColumn("Visualizar").setCellRenderer(new ButtonRenderer());
		table.getColumn("Visualizar").setCellEditor(new ButtonEditor(new JCheckBox()));

		scrollPane.setViewportView(table);

		JButton btnNewButton = new JButton("Cancelar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBox.removeAllItems();
				txtDescr.setText("");
				txtPeriodo.setText("");
				txtDescuento.setText("");
				txtFechaAlta.setText("");
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.setRowCount(0);
				setVisible(false);
				dispose();
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.EAST;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 3;
		gbc_btnNewButton.gridy = 9;
		getContentPane().add(btnNewButton, gbc_btnNewButton);

	}

	//CLASE DENTRO DEL SOURCE QUE PERMITE CARGAR LOS BOTONES
	class ButtonRenderer extends JButton implements TableCellRenderer {
		public ButtonRenderer() {
			setOpaque(true);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setText("Ver");
			return this;
		}
	}

	// CLASE PARA MANEJAR EVENTO DEL BOTÓN
	class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
		protected JButton button;
		private String label;
		private boolean clicked;

		public ButtonEditor(JCheckBox checkBox) {
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
				}
			});
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			label = "Ver"; 
			button.setText(label);
			clicked = true;
			return button;
		}

		@Override
		public Object getCellEditorValue() {
			if (clicked) {
				// EVENTO TRAS CLICKEAR
				Main.getVentanaRuta().setRutaSeleccionada(table.getValueAt(table.getSelectedRow(), 1).toString());
				Main.getVentanaRuta().setAerolineaSeleccionada(table.getValueAt(table.getSelectedRow(), 0).toString());
				Main.getVentanaRuta().setVisible(true);
				Main.getVentanaRuta().toFront();
			}
			clicked = false;
			return label;
		}

		@Override
		public boolean stopCellEditing() {
			clicked = false;
			return super.stopCellEditing();
		}

		@Override
		protected void fireEditingStopped() {
			super.fireEditingStopped();
		}
	}

	private void cargarDatos() {
		ArrayList<String> paquetes = (ArrayList<String>) controlP.listarPaquetes();
		for (int x = 0; x < paquetes.size(); x++) {
			comboBox.addItem(paquetes.get(x));
		}

	}

	public void setNomPaquete(String selectedItem) {
		// TODO Auto-generated method stub
		paqueteSeleccionado = selectedItem; 
	}

}
