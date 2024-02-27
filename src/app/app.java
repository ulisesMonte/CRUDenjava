package app;
import javax.swing.JFrame;

import pantallas.empleados;
public class app {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
				JFrame marco = new JFrame();
				marco.setVisible(true);
				marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				marco.setBounds(50, 50, 600, 600);
				marco.setContentPane(new empleados());
				marco.validate();
	}

}
