package dao.controle;

import anotacao.PersistenceContext;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import util.JPAUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class InterceptadorDeDao implements MethodInterceptor {
    /*
     * Parametros:
     *
     * objeto - "this", o objeto "enhanced", isto �, o proxy.
     *
     * metodo - o m�todo interceptado, isto �, um m�todo da interface ProdutoDAO,
     * LanceDAO, etc.
     *
     * args - um array de args; tipos primitivos s�o empacotados. Cont�m os
     * argumentos que o m�todo interceptado recebeu.
     *
     * metodoProxy - utilizado para executar um m�todo super. Veja o coment�rio
     * abaixo.
     *
     * MethodProxy - Classes geradas pela classe Enhancer passam este objeto para o
     * objeto MethodInterceptor registrado quando um m�todo interceptado �
     * executado. Ele pode ser utilizado para invocar o m�todo original, ou chamar o
     * mesmo m�todo sobre um objeto diferente do mesmo tipo.
     *
     */

    public Object intercept(Object objeto, Method metodo, Object[] args, MethodProxy metodoOriginal) throws Throwable {
        try {
            Field[] campos = metodo.getDeclaringClass().getDeclaredFields();
            for (Field campo : campos) {
                if (campo.isAnnotationPresent(PersistenceContext.class)) {
                    campo.setAccessible(true);
                    campo.set(objeto, JPAUtil.getEntityManager());
                }
            }

            return metodoOriginal.invokeSuper(objeto, args);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}