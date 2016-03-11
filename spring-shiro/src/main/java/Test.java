import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Test {
	public static void main(String[] args) {
		Set<String> keys = new HashSet<>();
		keys.add("a");
		keys.add("b");
		keys.add("c");
		keys.add("d");
		
		Enumeration<String> enumeration = Collections.enumeration(keys);
		for(; enumeration.hasMoreElements();){
			System.out.println(enumeration.nextElement());
		}
	}
}
