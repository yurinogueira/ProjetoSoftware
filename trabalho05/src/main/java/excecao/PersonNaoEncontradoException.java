package excecao;

import java.io.Serial;

public class PersonNaoEncontradoException extends Exception {
	@Serial
	private final static long serialVersionUID = 1154154514;
	public PersonNaoEncontradoException(String msg) {
		super(msg);
	}

}