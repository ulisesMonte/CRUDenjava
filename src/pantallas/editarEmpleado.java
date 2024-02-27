package pantallas;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import dao.daoEmpleado;
import entidades.empleado;

public class editarEmpleado extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField name;
    private JTextField textField;
    private JComboBox<String> sectoresEmp;
    private JLabel lblNewLabel;
    private JLabel lblNewLabel_1;
    private JButton guardar;

    /**
     * Create the panel.
     */
    public editarEmpleado(String nombreEmpleado) {
        empleado emp = getEmpleado(nombreEmpleado);
        setLayout(null);

        name = new JTextField(emp.getNombre());
        name.setBounds(153, 64, 86, 20);
        add(name);
        name.setColumns(10);

        JLabel Nombre = new JLabel("Nombre");
        Nombre.setBounds(100, 67, 46, 14);
        add(Nombre);

        JLabel fecha = new JLabel("fecha ingreso");
        fecha.setBounds(83, 92, 70, 14);
        add(fecha);
        
        
        String fechaIngresoC = emp.getFechaIngreso().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String fechaIngreso = fechaIngresoC;
        textField = new JTextField(fechaIngreso);
        textField.setBounds(153, 95, 86, 20);
        add(textField);
        textField.setColumns(10);

        sectoresEmp = new JComboBox<>();
        sectoresEmp.setBounds(141, 122, 86, 22);
        add(sectoresEmp);

        cargarSectores();
        
        sectoresEmp.setSelectedItem(emp.getSector());
        
        lblNewLabel = new JLabel("Sector");
        lblNewLabel.setBounds(74, 126, 46, 14);
        add(lblNewLabel);
        
        lblNewLabel_1 = new JLabel("Licencia Activa");
        lblNewLabel_1.setBounds(74, 166, 46, 14);
        add(lblNewLabel_1);
        
        JCheckBox chckbxNewCheckBox = new JCheckBox();
        chckbxNewCheckBox.setBounds(142, 166, 97, 23);
        chckbxNewCheckBox.setSelected(emp.isLicenciaAciva());
        add(chckbxNewCheckBox);
        
        JButton volver = new JButton("volver");
        volver.setBounds(226, 223, 89, 23);
        volver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
                marco.setContentPane(new empleados());
                marco.validate();
            }
        });
        add(volver);
        
        guardar = new JButton("guardar");
        guardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre = name.getText();
                String fechaIngreso = textField.getText();
                String sectorSeleccionado = (String) sectoresEmp.getSelectedItem();
                boolean licenciaActiva = chckbxNewCheckBox.isSelected();
                String nombreV = emp.getNombre();
                if (!nombre.equals(emp.getNombre()) || !fechaIngreso.equals(emp.getFechaIngreso().toString())
                        || !sectorSeleccionado.equals(emp.getSector()) || licenciaActiva != emp.isLicenciaAciva()) {
                    try {
                        DateTimeFormatter form = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        LocalDate fechaIngresoB = LocalDate.parse(fechaIngreso, form);
                        emp.setFechaIngreso(fechaIngresoB);
                        emp.setNombre(nombre);
                        emp.setLicenciaAciva(licenciaActiva);
                        emp.setSector(sectorSeleccionado);

                        daoEmpleado daoE = new daoEmpleado();
                        daoE.actualizarEmpleado(emp, nombreV);

                        JOptionPane.showMessageDialog(null, "Empleado actualizado correctamente");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error al actualizar el empleado");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se realizaron cambios");
                }
            }
        });
        guardar.setBounds(83, 223, 89, 23);
        add(guardar);
    
    
    }
    
    
    
    private void cargarSectores() {
        dao.daoSector sectorDao = new dao.daoSector();
        ArrayList<String> sectores = sectorDao.obtenerSectores();
        for (String sector : sectores) {
            sectoresEmp.addItem(sector);
        }
    }

    public empleado getEmpleado(String name) {
        daoEmpleado daoE = new daoEmpleado();
        empleado emp = daoE.getEmpleadoForName(name);
        return emp;

    }
}
