package ru.sendto.utils;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Utils to manipulate state of objects using lambdas
 * 
 * @author Lev Nadeinsky
 * @date	2017-06-22
 */
public class StateUtils {
	
	private static StateUtils INSTANCE = new StateUtils();
	
	private StateUtils() {
	}

	/**
	 * Copy field using getter and setter lamdas
	 * @param getter - lambda like dto::getField
	 * @param setter - lambda like other::setField
	 * @return value of dto::getField
	 */
	public static <T> T copyAndGet(Supplier<T> getter, Consumer<T> setter){
		final T val = getter.get();
		setter.accept(val);
		return val;
	}
	
	/**
	 * Copy state from one object to another
	 * @param getter - lambda like dto::getField
	 * @param setter - lambda like other::setField
	 * @return instance of StateUtils for chaining methods StateUtils.copy(..).copy(..).copy(..)...
	 */
	public static <T> StateUtils copy(Supplier<T> getter, Consumer<T> setter){
		setter.accept(getter.get());
		return INSTANCE;
	}	
	/**
	 * Copy state from one object to another with mapping
	 * dto::getField ---> T 
	 * mapper converts T->R
	 * other::setFiled( R )
	 * 
	 * @param getter - lambda like dto::getField
	 * @param mapper - lambda that converts from getter result type to setter parameter type
	 * @param setter - lambda like other::setField
	 * @return instance of StateUtils for chaining methods StateUtils.copy(..).copy(..).copy(..)...
	 */
	public static <T,R> StateUtils copy(Supplier<T> getter, Function<T,R> mapper, Consumer<R> setter){
		setter.accept(mapper.apply(getter.get()));
		return INSTANCE;
	}

	/**
	 * To string state covertor
	 * @param separator
	 * @param getter 
	 * @param getters
	 * @return
	 */
	public static <T> String toString(String separator, Supplier<T> getter, Supplier<T>...getters){
		StringBuilder sb = new StringBuilder(getter.get()+"");
		for (Supplier<T> g : getters) {
			sb.append(separator);
			sb.append(g.get());
		}
		return sb.toString();
	}

	/**
	 * To string state covertor with \n separetor
	 * @param getter 
	 * @param getters
	 * @return
	 */
	public static <T> String toStringLn(Supplier<T> getter, Supplier<T>...getters){
		return toString("\n", getter, getters);
	}

}
