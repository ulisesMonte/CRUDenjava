package pantallas;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import dao.daoEmpleado;
import entidades.empleado;
import entidades.sector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.awt.Component;
import java.awt.event.ActionEvent;

public class AddEmpleado extends JPanel {
    private JTextField nameEmp;
    private JTextField fechaEmpleado;

    /**
     * Create the panel.
     */
    public AddEmpleado() {
        setLayout(null);
        
        nameEmp = new JTextField();
        nameEmp.setBounds(141, 49, 86, 20);
        add(nameEmp);
        nameEmp.setColumns(10);
        
        JLabel lblNewLabel = new JLabel("Nombre");
        lblNewLabel.setBounds(93, 43, 106, 32);
        add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Fecha Ingreso");
        lblNewLabel_1.setBounds(65, 83, 101, 14);
        add(lblNewLabel_1);
        
        fechaEmpleado = new JTextField();
        fechaEmpleado.setBounds(141, 80, 86, 20);
        add(fechaEmpleado);
        fechaEmpleado.setColumns(10);
        
        
        JLabel lblNewLabel_2 = new JLabel("Sector");
        lblNewLabel_2.setBounds(93, 123, 50, 20);
        add(lblNewLabel_2);
        
        JComboBox<String> sectorEmp = new JComboBox<>();
        sectorEmp.setBounds(141, 122, 86, 22);
        add(sectorEmp);
        
        
        JLabel lblNewLabel_3 = new JLabel("Licencia Activo");
        lblNewLabel_3.setBounds(65, 176, 106, 14);
        add(lblNewLabel_3);
        
        JCheckBox licenciaEmp = new JCheckBox("");
        licenciaEmp.setBounds(166, 176, 97, 23);
        add(licenciaEmp);
        
        JButton btnNewButton = new JButton("Guardar");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sec = sectorEmp.getSelectedItem().toString();
                String fechaEmp = fechaEmpleado.getText();
                DateTimeFormatter form = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate fechaEmpleado = LocalDate.parse(fechaEmp, form);
                daoEmpleado daoEmp = new daoEmpleado();
                empleado emp = new empleado();
                emp.setNombre(nameEmp.getText());
                emp.setFechaIngreso(fechaEmpleado);
                emp.setSector(sec);
                emp.setLicenciaAciva(licenciaEmp.isSelected());
                daoEmpleado dao = new daoEmpleado();
                dao.alta(emp);
    		    JOptionPane.showMessageDialog(null, "Empleado agregado correctamente.");
            }
        });
        btnNewButton.setBounds(122, 223, 89, 23);
        add(btnNewButton);
        
        JButton volver = new JButton("volver");
        volver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
                marco.setContentPane(new empleados());
                marco.validate();
            }
        });
        volver.setBounds(226, 223, 89, 23);
        add(volver);
        
        dao.daoSector sectorDao = new dao.daoSector();
        ArrayList<String> sectores = sectorDao.obtenerSectores();
        for(String sector : sectores) {
        	sectorEmp.addItem(sector);
        }

    }
}
