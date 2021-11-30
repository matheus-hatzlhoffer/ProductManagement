package br.com.product_management.services;

/**
 * @author mhatzlhoffer
 * 
 * Service to help process Enums 
 *
 */
public class EnumService {
	public static <T extends Enum<?>> T searchEnum(Class<T> enumeration,
	        String search) {
	    for (T each : enumeration.getEnumConstants()) {
	        if (each.name().compareToIgnoreCase(search) == 0) {
	            return each;
	        }
	    }
	    return null;
	}
}
