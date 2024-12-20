package presentacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import logica.DTRutaDeVuelo;
import logica.IControladorRutaDeVuelo;

public class ConsultaRutasMasVisitadas extends JInternalFrame {

    private IControladorRutaDeVuelo ICRV;
    private JTable tableRutas;
    private DefaultTableModel model;

    public ConsultaRutasMasVisitadas(IControladorRutaDeVuelo controladorRutaDeVuelo) {
        this.ICRV = controladorRutaDeVuelo;

        // Configuración de la ventana
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setTitle("Rutas Más Visitadas");
        setBounds(10, 10, 800, 200);
        
        // Crear modelo de tabla
        String[] columnNames = {"#", "Ruta de Vuelo", "Aerolinea", "Ciudad Origen", "Ciudad Destino", "Cantidad de Visitas"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;  // No se puede editar ninguna celda
            }
        };
        
        tableRutas = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tableRutas);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Agregar listener para cargar las rutas cuando la ventana sea visible
        addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
            @Override
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent e) {
                cargarRutasMasVisitadas();  // Llamar al método cuando se abre la ventana
            }
        });
    }

    public void cargarRutasMasVisitadas() {
    	model.setRowCount(0);
        if (ICRV == null) {
            return;
        }

        try {
            List<DTRutaDeVuelo> top5Rutas = ICRV.obtener5MasVisitadas();
            if (top5Rutas.isEmpty()) {
                System.out.println("La lista de rutas más visitadas está vacía.");
            } else {
                int index = 1;
                for (DTRutaDeVuelo ruta : top5Rutas) {
                        model.addRow(new Object[]{
                        index++,
                        ruta.getNombre(),
                        ruta.getAerolinea().getNombre(),
                        ruta.getCiudadOrigen().getNombre(),
                        ruta.getCiudadDestino().getNombre(),
                        ruta.getVisitas()
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error al cargar rutas más visitadas: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
