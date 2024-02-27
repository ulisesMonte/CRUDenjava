package dao;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import entidades.empleado;
public class daoEmpleado {
	    daoSector daoS = new daoSector();
		public String URL = "jdbc:mysql://localhost:3306/recuperacion";
		public String usuario = "root";
		public String pass = "12A34B56C";
		
		public void alta(empleado emp) {
			try (Connection conn = DriverManager.getConnection(URL,usuario,pass)) {
				String query = "INSERT INTO empleados (nombre, fechaingreso, licencia, idsector) VALUES (?,?,?,?)";
	            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	            
	            preparedStatement.setString(1,emp.getNombre());
	            preparedStatement.setDate(2, java.sql.Date.valueOf(emp.getFechaIngreso()));
	            preparedStatement.setBoolean(3, emp.isLicenciaAciva());
	            daoSector daoS = new daoSector();
	            preparedStatement.setInt(4,daoS.getIdSector(emp));
	            preparedStatement.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		}
		
		
		
		
		
		public ArrayList<empleado> getEmpleados(){
			ArrayList<empleado> emp = new ArrayList<>();
			try(Connection conn = DriverManager.getConnection(URL,usuario,pass)){
				Statement statement= conn.createStatement();
				String query = "SELECT empleados.nombre,  empleados.fechaingreso, empleados.idsector, empleados.licencia FROM empleados, sectores ";
				PreparedStatement preparedStatement = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
				ResultSet rs = preparedStatement.executeQuery();
				while(rs.next()) {
					String name = rs.getString("nombre");	
					LocalDate fechaIngreso = rs.getDate("fechaingreso").toLocalDate();
					int idSector = rs.getInt("idsector");
					boolean licencia = rs.getBoolean("empleados.licencia");
					empleado empleado = new empleado();
					empleado.setFechaIngreso(fechaIngreso);
					empleado.setNombre(name);
					empleado.setLicenciaAciva(licencia);
					daoSector daoS = new daoSector();
					String nombreSec = daoS.getNombreSector(idSector);
					empleado.setSector(nombreSec);
					emp.add(empleado);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return emp;
		}
		
		public boolean eliminarEmpleado(String e) {
			try (Connection conn = DriverManager.getConnection(URL, usuario, pass)) {
		        String query = "DELETE FROM empleados where nombre = ?";
		        PreparedStatement preparedStatement = conn.prepareStatement(query);
		        preparedStatement.setString(1, e);
		        int rowsAffected = preparedStatement.executeUpdate();

		        if (rowsAffected > 0) {
		            return true; 
		        } else {
		            return false; 
		        }
		    } catch (SQLException ex) {
		        ex.printStackTrace(); 
		        return false; 		    }
		}
		
		
		public empleado getEmpleadoForName(String nombre) {
		    empleado emp = new empleado();
		    try (Connection conn = DriverManager.getConnection(URL, usuario, pass)) {
		        String query = "SELECT empleados.nombre, empleados.fechaingreso, empleados.idsector, empleados.licencia FROM empleados, sectores WHERE empleados.nombre = ?";
		        PreparedStatement preparedStatement = conn.prepareStatement(query);
		        preparedStatement.setString(1, nombre);
		        ResultSet rs = preparedStatement.executeQuery();
		        if (rs.next()) {
		            String name = rs.getString("nombre");
		            LocalDate fechaIngreso = rs.getDate("fechaingreso").toLocalDate();
		            int idSector = rs.getInt("idsector");
		            boolean licencia = rs.getBoolean("empleados.licencia");
		            emp.setFechaIngreso(fechaIngreso);
		            emp.setNombre(name);
		            emp.setLicenciaAciva(licencia);
		            daoSector daoS = new daoSector();
		            String nombreSec = daoS.getNombreSector(idSector);
		            emp.setSector(nombreSec);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return emp;
		}
		
		public void actualizarEmpleado(empleado emp, String nombreAnterior) {
		    try (Connection conn = DriverManager.getConnection(URL, usuario, pass)) {
		        String query = "UPDATE empleados SET nombre=?, fechaingreso=?, idsector=?, licencia=? WHERE nombre=?";
		        PreparedStatement preparedStatement = conn.prepareStatement(query);

		        preparedStatement.setString(1, emp.getNombre());
		        preparedStatement.setDate(2, java.sql.Date.valueOf(emp.getFechaIngreso()));
		        preparedStatement.setInt(3, daoS.getIdSector(emp));
		        preparedStatement.setBoolean(4, emp.isLicenciaAciva());
		        preparedStatement.setString(5, nombreAnterior);

		        preparedStatement.executeUpdate();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}



		
		
}
