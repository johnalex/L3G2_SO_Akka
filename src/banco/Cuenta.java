package banco;

public abstract class Cuenta {

	protected int saldo;
	
	public abstract void transaccion(int monto);
	
	public int getSaldo(){
		return this.saldo;
	}
	
	public void vaciar(){
		this.saldo = 0;
	}
	
	public void imprimirSaldo(){
		System.out.println("El saldo de la cuenta es: $" + this.saldo);
	}
}
