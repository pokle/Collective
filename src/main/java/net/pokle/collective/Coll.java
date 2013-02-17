package net.pokle.collective;

import java.lang.reflect.Method;
import java.util.Collection;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;


public class Coll<E> {

    private final Class<E> sourceItemClass;
    private final Collection<E> sourceCollection;

    private Coll(Class<E> sourceItemClass, Collection<E> sourceCollection) {
        this.sourceItemClass = sourceItemClass;
        this.sourceCollection = sourceCollection;
    }

    public static <E> Coll<E> from(Class<E> sourceItemClass, Collection<E> sourceCollection) {
        return new Coll<E>(sourceItemClass, sourceCollection);
    }

    public E first() {
        return sourceCollection.iterator().next();
    }

    @SuppressWarnings("unchecked")
	public E all() {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setSuperclass(sourceItemClass);
        proxyFactory.setInterfaces(sourceItemClass.getInterfaces());
        MethodHandler methodHandler = new MethodHandler() {
            public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
                Object lastResult = null;
                for (E item : sourceCollection) {
                    lastResult = thisMethod.invoke(item, args);
                }
                return lastResult;
            }
        };
		proxyFactory.setHandler(methodHandler);
        proxyFactory.setFilter(new MethodFilter() {
            public boolean isHandled(Method m) {
                return true;
            }
        });
        Class<E> proxyClass = proxyFactory.createClass();
        final E proxyE;
        try {
            E proxy = proxyClass.newInstance();
			proxyE = (E) proxy;
        } catch (InstantiationException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return proxyE;
    }

}
