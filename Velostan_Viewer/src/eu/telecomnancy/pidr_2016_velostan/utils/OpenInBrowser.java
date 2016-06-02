package eu.telecomnancy.pidr_2016_velostan.utils;

import java.awt.Desktop;
import java.awt.Menu;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OpenInBrowser {
	private Desktop desktop ;
	private URI url ;
	
	public OpenInBrowser() {
		super();
	}

	public void open(String urlString){
		try {
			url = new URI(urlString);
			if (Desktop.isDesktopSupported())
			{
				desktop = Desktop.getDesktop();
				desktop.browse(url);
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}

