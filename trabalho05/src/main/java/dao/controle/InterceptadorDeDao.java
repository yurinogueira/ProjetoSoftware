package dao.controle;

import anotacao.Executar;
import anotacao.RecuperaLista;
import dao.impl.JPADaoGeneric;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import util.JPAUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class InterceptadorDeDao implements MethodInterceptor {

    /*
     * Parametros:
     *
     * objeto - "this", o objeto "enhanced", isto é, o proxy.
     *
     * metodo - o método interceptado, isto é, um método da interface ProdutoDAO,
     * LanceDAO, etc.
     *
     * args - um array de args; tipos primitivos são empacotados. Contém os
     * argumentos que o método interceptado recebeu.
     *
     * metodoProxy - utilizado para executar um método super. Veja o comentário
     * abaixo.
     *
     * MethodProxy - Classes geradas pela classe Enhancer passam este objeto para o
     * objeto MethodInterceptor registrado quando um método interceptado é
     * executado. Ele pode ser utilizado para invocar o método original, ou chamar o
     * mesmo método sobre um objeto diferente do mesmo tipo.
     *
     */

    public Object intercept(Object objeto, Method metodo, Object[] args, MethodProxy metodoProxy) throws Throwable {
        System.out.println("Método interceptador do DAO: " + metodo.getName() + " da classe " + metodo.getDeclaringClass().getName());

        JPADaoGeneric<?, ?> daoGenerico = (JPADaoGeneric<?, ?>) objeto;
        daoGenerico.em = JPAUtil.getEntityManager();

        if (metodo.isAnnotationPresent(Executar.class)) {
            return metodoProxy.invokeSuper(objeto, args);
        } else if (metodo.isAnnotationPresent(RecuperaLista.class)) {
            return daoGenerico.buscaLista(metodo, args);
        } else {
            throw new RuntimeException("Método não anotado com @Executar ou @RecuperaLista");
        }
    }
}
