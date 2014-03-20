package gameMaster;

import java.io.File;
import java.util.HashSet;
import java.util.Set;


public class GameTracker {
	
	public static String[] getNames(){
		Set<String> classes = getClassesInPackage("pits");
		String[] rlt = {};
		return classes.toArray(rlt);
	}
	
	public static GameBase getGames(String name){
		GameBase game = null;
		try {
			game = (GameBase) Class.forName(name).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return game;
	}
	
	/** 
	 * Given a package name, attempts to reflect to find all classes within the package 
	 * on the local file system. 
	 *  
	 * @param packageName 
	 * @return 
	 */  
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
	                 
	                    classes.add(packageName + "." + fileName);  
	                  
	            }  
	        }  
	    } else {  
	    	System.out.println(packageName + " does not appear to exist as a valid package on the file system.");  
	    }  
	    return classes;  
	} 
	
}
