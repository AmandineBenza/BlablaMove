package com.xaamruda.bbm.commons.testing;

import java.lang.reflect.Field;

public final class UnitTestingUtils {

	private UnitTestingUtils() {}
	
	/**
	 * Set a static private field of a given class.<br>
	 * 
	 * @param clazz targeted class
	 * @param fieldName field to set
	 * @param value new field instance
	 * @return either success
	 */
	public static <T> boolean setStaticField(Class<T> clazz, String fieldName, Object value) {
	    Field field;
		try {
			field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(null, value);
			return true;
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Get a static private field of a given class.<br>
	 *  
	 * @param clazz declaring class
	 * @param fieldName field name
	 * @return the field instance, null if a problem occurred
	 */
	@SuppressWarnings("unchecked")
	public static <C, T> T getStaticField(Class<C> clazz, String fieldName) {
		Field field;
		try {
			field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			return (T) field.get(null);
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	

}
