package banco;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Banco {

	static int[] operacionesAntonio;
	static int[] operacionesBlanca;
	private static Persona antonio;
	private static Persona blanca;
	public static Cuenta cuentaCompartida;
	private static TiposCuenta tipoCuenta;
	
	public static void main(String[] args) {
		System.out.println("\t\t:::BancoSO:::");

		String ruta = "D:\\alex\\Desktop\\operaciones.txt";
		//String ruta = "/home/alex/desktop/operaciones.txt";
		// tipoCuenta = S:Semaforo || M:Monitor || N:NoSincronizada || E:ExecutorService
		String tipoCuenta = "E";

		cuentaCompartida = new CuentaNoSincronizada();
		leerTransacciones(ruta);
		ejecutarTransacciones();
	}

	static void leerTransacciones(String rutaArchivo) {
		Scanner scan;
		try {
			scan = new Scanner(new File(rutaArchivo));
			operacionesAntonio = new int[scan.nextInt()];
			operacionesBlanca = new int[scan.nextInt()];

			for (int i = 0; i < operacionesAntonio.length; i++) {
				operacionesAntonio[i] = scan.nextInt();
			}

			for (int i = 0; i < operacionesBlanca.length; i++) {
				operacionesBlanca[i] = scan.nextInt();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	static void ejecutarTransacciones() {
		cuentaCompartida.vaciar();
		
		ActorSystem clientesSystem = ActorSystem.create("SistemaActores");
		ActorRef cliente = clientesSystem.actorOf(new Props(Persona.class), "Cliente");
		
		cliente.tell(operacionesAntonio, null);
		cliente.tell(operacionesBlanca, null);
		
		clientesSystem.shutdown();
		clientesSystem.awaitTermination();
	}

	private enum TiposCuenta {
		NoSincronizada, Semaforo, Monitor
	}

	

}
