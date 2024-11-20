package presentacion;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import excepciones.PaqueteNoExisteException;
import excepciones.PaqueteYaCompradoException;
import excepciones.UsuarioNoExisteException;
import service.*;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;


/*
   JInternalFrame que permite la compra de un paquete en el sistema.
 */

@SuppressWarnings("serial")
public class CompraPaquete extends JInternalFrame {
    private IControladorUsuario controlUsr;
    private IControladorPaquete controlPaquete;

    private JComboBox<String> comboClientes = null; 
    private JComboBox<String> comboPaquetes = null;
	JButton btnAceptar; 



	public CompraPaquete(IControladorUsuario icu, IControladorPaquete icp) {
	   
		controlUsr = icu;
		controlPaquete = icp; 
		
		setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
	    setTitle("Compra de paquete");
        setBounds(30, 30, 325, 210);
        getContentPane().setLayout(null);

		
		JLabel lblNewLabel = new JLabel("Paquetes");
		lblNewLabel.setBounds(39, 30, 58, 14);
		getContentPane().add(lblNewLabel);
		
		comboPaquetes = new JComboBox<String>();
		comboPaquetes.setBounds(138, 26, 126, 22);
		getContentPane().add(comboPaquetes);	
		
		JLabel lblClientes = new JLabel("Clientes");
		lblClientes.setBounds(39, 72, 58, 14);
		getContentPane().add(lblClientes);
		
		comboClientes = new JComboBox<String>();
		comboClientes.setBounds(138, 68, 126, 22);
		getContentPane().add(comboClientes);
		
		JButton btnNewButton = new JButton("Cancelar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboPaquetes.removeAllItems();
				comboClientes.removeAllItems();
				setVisible(false);
			}
		});
		btnNewButton.setBounds(164, 130, 89, 23);
		getContentPane().add(btnNewButton);
		
		btnAceptar = new JButton("Aceptar");

		// ActionListener para el botón "Aceptar"
		btnAceptar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String clienteSeleccionado = (String) comboClientes.getSelectedItem();
		        String paqueteSeleccionado = (String) comboPaquetes.getSelectedItem();
		        
		        try {
		            // Ejecutar la compra del paquete
		        	if (!clienteSeleccionado.equals("[Seleccionar cliente]") && !paqueteSeleccionado.equals("[Seleccionar paquete]")) {
		        		controlUsr.clienteCompraPaquete(clienteSeleccionado, paqueteSeleccionado);
		            // Mostrar mensaje de éxito
		            JOptionPane.showMessageDialog(CompraPaquete.this, "Compra realizada con éxito", "Aviso", JOptionPane.INFORMATION_MESSAGE);
		            hide();
		            
		        	}
		        	else {
			            JOptionPane.showMessageDialog(CompraPaquete.this, "Seleccionar datos", "Aviso", JOptionPane.INFORMATION_MESSAGE);

		        	}

		        } catch (PaqueteYaCompradoException u) {
		            // Manejar la excepción si el paquete ya fue adquirido
		            JOptionPane.showMessageDialog(CompraPaquete.this, u.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

		        } catch (UsuarioNoExisteException e1) {
		            e1.printStackTrace();
		        } catch (PaqueteNoExisteException e1) {
		            e1.printStackTrace();
		        }
		    }
		});
	
		btnAceptar.setBounds(55, 130, 89, 23);
		getContentPane().add(btnAceptar);
		
		
	}

	
	public void cargarClientes() {
		DefaultComboBoxModel<String> modelC;
		
		try {
			List<String> clientes = controlUsr.listarClientes(); // Obtener la lista de clientes
			String[] clientesArray = clientes.toArray(new String[0]); // Convertimos la lista en un array para setear el modelo
			
			modelC = new DefaultComboBoxModel<String>();
			modelC.addElement("[Seleccionar cliente]"); // Primer item
			
			// Agregamos los usuarios restantes al modelo
			for (String c : clientesArray) {
				modelC.addElement(c);
			}    		    
			comboClientes.setModel(modelC);
			
		} catch (UsuarioNoExisteException e) {
			// No se imprime mensaje de error sino que simplemente no se muestra ningún elemento
		} 
		
	}
	
	public void cargarPaquetes() {
		DefaultComboBoxModel<String> model;

			List<String> paquetes = controlPaquete.listarPaquetesNoVacios(); // Obtener la lista de clientes
			String[] paquetesArray = paquetes.toArray(new String[0]); // Convertimos la lista en un array para setear el modelo
			 
			model = new DefaultComboBoxModel<String>();
			model.addElement("[Seleccionar paquete]"); // Primer item
			
			// Agregamos los usuarios restantes al modelo
			for (String p : paquetesArray) {
				model.addElement(p);
			}    		    
			comboPaquetes.setModel(model);		
	}

}
