package sixt.run;

import java.util.HashMap;

public class TestHashSet {
	 public static void main(String[] args) 
	    { 
	  
	        // Creating an empty HashMap 
	        HashMap<String, Integer> hash_map = new HashMap<String, Integer>(); 
	  
	        // Mapping int values to string keys 
	        hash_map.put("Geeks", 10); 
	        hash_map.put("4", 15); 
	        hash_map.put("Geeks", 20); 
	        hash_map.put("Welcomes", 25); 
	        hash_map.put("You", 30); 
	  
	        // Displaying the HashMap 
	        System.out.println("Initial Mappings are: " + hash_map); 
	  
	        // Using keySet() to get the set view of keys 
	        System.out.println("The set is: " + hash_map.keySet()); 
	    } 
}
