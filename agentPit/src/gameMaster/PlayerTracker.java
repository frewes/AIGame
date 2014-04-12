package gameMaster;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import agents.*;

/*@@import@@*/

public class PlayerTracker {

	public static String[] playerList(){
		Set<String> classes = getClassesInPackage("agents");
		String[] rlt = {};
		return classes.toArray(rlt);
	}
	
	public static Player creator(String name){
		Player player = null;
		try {
			player = (Player) Class.forName(name).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return player;
	}
	
	private static Set<String> getClassesInPackage(String packageName) {  
	    Set<String> classes = new HashSet<String>();  
  
	  
	    File directory = new File("./bin/"+packageName);  
	    if (directory.exists()) {  
	        // Get the list of the files contained in the package  
	        String[] files = directory.list();  
	        for (String fileName : files) {  
	            // We are only interested in .class files  
	            if (fileName.endsWith(".class")) {  
	                // Remove the .class extension
	                fileName = fileName.substring(0, fileName.length() - 6); 
	                try {
						if(Player.class.isAssignableFrom(Class.forName(packageName + "." + fileName))){
							classes.add(packageName + "." + fileName);  
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
	            }  
	        }  
	    } else {  
	    	System.out.println(packageName + " does not appear to exist as a valid package on the file system.");  
	    }  
	    return classes;  
	} 
}
