package eu.telecomnancy.pidr_2016_velostan.database;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadPropertyFile {	
	
    public Properties readFile(String filename) throws IOException {
        Properties p= new Properties();

        //        p.load(getClass().getResourceAsStream(filename));
        InputStream is = new FileInputStream(filename);
        p.load(is);
        
        return p;
        
    }

}
