package excecao;

import java.io.Serial;

public class InfraestruturaException extends RuntimeException {
	@Serial
	private final static long serialVersionUID = 1541654165;

	public InfraestruturaException(Exception e) {
		super(e);
	}

	public InfraestruturaException(String msg) {
		super(msg);
	}
}