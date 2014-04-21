package banco;

import akka.actor.UntypedActor;


public class Persona extends UntypedActor 
{
	private String nombre;
	private Cuenta cuentaCompartida;
	private int[] transacciones;

	public Persona(String nombre){
		this.nombre = nombre;
	}
	
	public void setTransacciones(int[] lista){
		this.transacciones = lista;
	}
	
	public void setCuentaCompartida(Cuenta cuenta){
		this.cuentaCompartida = cuenta;
	}

	@Override
	public void onReceive(Object arg0) throws Exception {
		// TODO Auto-generated method stub
		for (int i = 0; i < transacciones.length; i++) {
			this.cuentaCompartida.transaccion(transacciones[i]);
		}
		System.out.println(nombre + " termina transacciones, saldo: $" 
				+ Banco.cuentaCompartida.getSaldo());
	}
	
}
