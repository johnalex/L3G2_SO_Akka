package banco;

import akka.actor.UntypedActor;


public class Persona extends UntypedActor 
{
	

	@Override
	public void onReceive(Object message) throws Exception {
		if(message instanceof int[]){
			int[] operaciones=(int[]) message;
			for(int i=0; i<operaciones.length; i++){
				Banco.cuentaCompartida.transaccion(operaciones[i]);
			}
			Banco.cuentaCompartida.imprimirSaldo();
		}
		else{
			unhandled(message);
		}
	}
	
}
