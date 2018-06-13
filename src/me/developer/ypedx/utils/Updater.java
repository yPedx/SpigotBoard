package me.developer.ypedx.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Updater {
 
    
    private static URL checkURL;
    
    private static String newVersion = "";
    
    private static String resURL = "https://www.spigotmc.org/resources/47497";
 
    
    public static String getLatestVersion() {
    	
        return newVersion;
    }
 
    
    public static String getResourceURL() {
    	
        return resURL;
    }
 
    
    public static String checkForUpdates() throws Exception {
    	
        try {
        	
            Updater.checkURL = new URL("https://api.spigotmc.org/legacy/update.php?resource=47497");
            
        } catch (MalformedURLException e) {
        	
        }
    	
        URLConnection con = checkURL.openConnection();
        
        return newVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
    }

}