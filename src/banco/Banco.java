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

	public static void main(String[] args) {
		System.out.println("\t\t:::BancoSO:::");

		//String ruta = "E:\\Alex\\Desktop\\operaciones.txt";
		String ruta = "/home/alex/desktop/operaciones.txt";
		// tipoCuenta = S:Semaforo || M:Monitor || N:NoSincronizada || E:ExecutorService
		String tipoCuenta = "E";

		crearClientes(tipoCuenta);
		leerTransacciones(ruta);
		
		if (tipoCuenta=="E") 
			ejecutarTransacciones(2); // Ejecuta transacciones con ExecutorService, param:fixedThreads
		else ejecutarTransacciones();
	}
	
	

	static void crearClientes(String tipoCuentaCompartida) {
		antonio = new Persona("Antonio");
		blanca = new Persona("Blanca");
		/*

		switch (tipoCuentaCompartida) {
		case "S":
			tipoCuenta = TiposCuenta.Semaforo;
			break;
		case "M":
			tipoCuenta = TiposCuenta.Monitor;
			break;
		default:
			tipoCuenta = TiposCuenta.NoSincronizada;
			break;
		}

		System.out.println("Cuenta compartida de tipo: "
				+ tipoCuenta.toString());

		switch (tipoCuenta) {
		case Semaforo:
			cuentaCompartida = new CuentaSemaforo();
			break;
		case Monitor:
			cuentaCompartida = new CuentaSemaforo();
			break;
		default:
			cuentaCompartida = new CuentaNoSincronizada();
			break;
		}
		*/
		cuentaCompartida = new CuentaNoSincronizada();
		antonio.setCuentaCompartida(cuentaCompartida);
		blanca.setCuentaCompartida(cuentaCompartida);

	}

	static void leerTransacciones(String rutaArchivo) {
		Scanner scan;
		try {
			scan = new Scanner(new File(rutaArchivo));
			int[] operacionesAntonio = new int[scan.nextInt()];
			int[] operacionesBlanca = new int[scan.nextInt()];

			for (int i = 0; i < operacionesAntonio.length; i++) {
				operacionesAntonio[i] = scan.nextInt();
			}

			for (int i = 0; i < operacionesBlanca.length; i++) {
				operacionesBlanca[i] = scan.nextInt();
			}

			antonio.setTransacciones(operacionesAntonio);
			blanca.setTransacciones(operacionesBlanca);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	static void ejecutarTransacciones() {
		cuentaCompartida.vaciar();
		ActorSystem clientesSystem = ActorSystem.create("Clientes");
		//
		Props p1 = Props.create(Persona.class);
		ActorRef cA = clientesSystem.actorOf(p1, "antonio");
		
		Props p2 = Props.create(Persona.class);
		ActorRef cB = clientesSystem.actorOf(p2, "blanca");
		
		cA.tell(null, null);
		cB.tell(null, null);
		
		clientesSystem.shutdown();
		clientesSystem.awaitTermination();
	}
	
	static void ejecutarTransacciones(int fixedThreads) {
		/*ExecutorService executor = Executors.newFixedThreadPool(fixedThreads);
		executor.execute(antonio);
		executor.execute(blanca);
		executor.shutdown();*/
	}

	private enum TiposCuenta {
		NoSincronizada, Semaforo, Monitor
	}

	private static Persona antonio;
	private static Persona blanca;
	public static Cuenta cuentaCompartida;
	private static TiposCuenta tipoCuenta;

}
