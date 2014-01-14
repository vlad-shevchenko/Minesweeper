package constant;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

/**
 * <p>
 * Handles loading constants from *.xml file and putting it to Const class.
 * </p>
 * 
 * @author Vlad
 */
public final class ConstInitializer {

	private XMLLoader loader;
	
	/**
	 * <p>
	 * Creates {@link XMLLoader} object, that prepare itself for constants loading.
	 * </p>
	 * 
	 * @author Vlad
	 */
	public ConstInitializer() {
		loader = new XMLLoader();
	}
	
	/**
	 * <p>
	 * Checks constants file and puts all found constants to Const.
	 * </p>
	 * 
	 * @author Vlad
	 */
	public void initialize() {
		HashMap<String, Entry<ConstType, Object>> consts = Const.getConsts();
		Set<String> constsNames = consts.keySet();
		Iterator<String> i = constsNames.iterator();
		
		while(i.hasNext()) {
			String key = i.next();
			ConstType type = consts.get(key).getKey();
			Object value = loader.getConst(type, key);
			
			Const.put(key, type, value);
		}
	}
}
