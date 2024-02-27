package entidades;

import java.time.LocalDate;
public class empleado {
	private LocalDate fechaActual = LocalDate.now();
	private String nombre;
	private LocalDate fechaIngreso;
	private String sector;
	private boolean licenciaAciva;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public LocalDate getFechaIngreso() {
		return fechaIngreso;
	}
	public void setFechaIngreso(LocalDate fecha) {
		this.fechaIngreso = fecha;
	}
	public String getSector() {
		return this.sector ;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public boolean isLicenciaAciva() {
		return licenciaAciva;
	}
	public void setLicenciaAciva(boolean licenciaAciva) {
		this.licenciaAciva = licenciaAciva;
	}
	
	public int antiguedad() {
		int antiguedad = fechaActual.getYear() - fechaIngreso.getYear();
		return antiguedad;
	}
}
