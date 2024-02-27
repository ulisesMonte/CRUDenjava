package pantallas;
import dao.daoEmpleado;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import dao.daoEmpleado;
import entidades.empleado;

import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class empleados extends JPanel {
	daoEmpleado emp = new daoEmpleado();
	private JTable table;
	ArrayList<empleado> empleadosList = emp.getEmpleados();

	public empleados() {
		setLayout(null);
		
		JButton add = new JButton("agregar");
		add.setBounds(321, 42, 89, 23);
		add(add);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 11, 279, 208);
		add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		DefaultTableModel modelDb = new DefaultTableModel();
		modelDb.addColumn("nombre");
		modelDb.addColumn("Antiguo");
		modelDb.addColumn("sector");
		modelDb.addColumn("licencia");
		
		
		table.setModel(modelDb);
		
		JButton eliminar = new JButton("eliminar");
		eliminar.setBounds(321, 87, 89, 23);
		add(eliminar);
		
		JButton editar = new JButton("editar");
		editar.setBounds(321, 132, 89, 23);
		add(editar);
		
		mostrarObj();
		
		editar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = table.getSelectedRow();
				if (filaSeleccionada != -1) {
		            empleado emp = empleadosList.get(filaSeleccionada);
		            String nombreEmpleado = emp.getNombre();
		            JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
					marco.setContentPane(new editarEmpleado(nombreEmpleado));
					marco.validate();
		        } else {
		            JOptionPane.showMessageDialog(null, "Por favor, seleccione un empleado a editar.");
		        }
			}
		});
		
		eliminar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int filaSeleccionada = table.getSelectedRow();
		        if (filaSeleccionada != -1) {
		            empleado emp = empleadosList.get(filaSeleccionada);
		            String nombreEmpleado = emp.getNombre();
		            eliminarEmpleado(nombreEmpleado);
		        } else {
		            JOptionPane.showMessageDialog(null, "Por favor, seleccione un empleado a eliminar.");
		        }
		    }
		});
		
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
				marco.setContentPane(new AddEmpleado());
				marco.validate();
			}
		});
		
	}
	
	
	public void mostrarObj() {
	    DefaultTableModel modelDb = (DefaultTableModel) table.getModel();
	    modelDb.setRowCount(0); 
	    daoEmpleado emp = new daoEmpleado();
	    ArrayList<String> nombresMostrados = new ArrayList<>(); 
	    for (empleado e : empleadosList) {
	        if (!nombresMostrados.contains(e.getNombre())) {
	            Object[] empleadoD = { e.getNombre(), e.antiguedad(), e.getSector(), e.isLicenciaAciva() };
	            modelDb.addRow(empleadoD);
	            nombresMostrados.add(e.getNombre()); 
	        }
	    }
	}
	
	
	public void eliminarEmpleado(String nombreEmpleado) throws HeadlessException  {
		daoEmpleado empDAO = new daoEmpleado();
		if (empDAO.eliminarEmpleado(nombreEmpleado)) {
		    JOptionPane.showMessageDialog(null, "Empleado eliminado correctamente.");
		    DefaultTableModel model = (DefaultTableModel) table.getModel();
		    for (int i = 0; i < model.getRowCount(); i++) {
		        if (model.getValueAt(i, 0).equals(nombreEmpleado)) {
		            model.removeRow(i);
		            break;
		        }
		    }
		} else {
		    JOptionPane.showMessageDialog(null, "Error al eliminar el empleado.");
		}
	}
}