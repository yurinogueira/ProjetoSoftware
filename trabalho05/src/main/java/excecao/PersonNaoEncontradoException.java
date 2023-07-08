package excecao;

public class PersonNaoEncontradoException extends Exception {
	private final static long serialVersionUID = 1;
	public PersonNaoEncontradoException(String msg) {
		super(msg);
	}

}