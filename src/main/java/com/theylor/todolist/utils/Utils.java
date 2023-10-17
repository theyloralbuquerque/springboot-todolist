package com.theylor.todolist.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {
	
	public static void copyNonNullProperties(Object source, Object target) {
		
		// Copia os objetos do source e armazena no target e evitando copiar objetos null (getNullPropertyNames(source)).
		BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
	}
	
	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);		
		
		PropertyDescriptor[] pds = src.getPropertyDescriptors(); // Retorna um array de objetos PropertyDescriptor e armazena em pds.
		
		Set<String> emptyNames = new HashSet<>();
		
		for (PropertyDescriptor pd : pds) {
			// Para cada item de src eu vou pegar o name e armazenar em srcValue.
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null) {
				// Pega todos o name dos elementos null da lista pd e adiciona no Set emptyNames.
				emptyNames.add(pd.getName());
			}
		}
		
		String[] result = new String[emptyNames.size()];
		
		// Retorna o array result com os valores do Set emptyNames.
		return emptyNames.toArray(result);
	}

}
