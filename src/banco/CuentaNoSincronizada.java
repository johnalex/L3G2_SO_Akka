package banco;

public class CuentaNoSincronizada extends Cuenta {

	@Override
	public void transaccion(int monto) {
		saldo = saldo + monto;

	}

}
