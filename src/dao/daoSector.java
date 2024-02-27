package dao;

import java.util.ArrayList;

import entidades.empleado;
import entidades.sector;

import java.sql.*;

public class daoSector {
	public String URL = "jdbc:mysql://localhost:3306/recuperacion";
	public String usuario = "root";
	public String pass = "12A34B56C";
	public ArrayList<String> obtenerSectores(){
		ArrayList<String> sectores = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(URL, usuario, pass)) {
			java.sql.Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT idsector, nombre FROM sectores");
			while (resultSet.next()) {
                String sector = resultSet.getString("nombre");
                sectores.add(sector);
            }
			 resultSet.close();
	         statement.close();
	         conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return sectores;
	}
	
	public String getNombreSector(int id )  {
		String nombreSec = null;
		
		try(Connection conn = DriverManager.getConnection(URL,usuario,pass)){
			java.sql.Statement statement = conn.createStatement();
			String query = "SELECT nombre from sectores WHERE idsector = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                nombreSec = rs.getString("nombre");
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return nombreSec;
		
		
	}
	
	public int getIdSector(empleado emp )  {
		
		int idsec = -1;
		
		try(Connection conn = DriverManager.getConnection(URL,usuario,pass)){
			java.sql.Statement statement = conn.createStatement();
			String query = "SELECT idsector from sectores WHERE nombre = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,emp.getSector());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                idsec = rs.getInt("idsector");
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return idsec;
		
		
	}
}