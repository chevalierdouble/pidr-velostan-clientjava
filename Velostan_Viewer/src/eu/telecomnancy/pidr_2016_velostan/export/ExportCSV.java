package eu.telecomnancy.pidr_2016_velostan.export;

import eu.telecomnancy.pidr_2016_velostan.model.RouteModel;
import eu.telecomnancy.pidr_2016_velostan.utils.Utils;
import eu.telecomnancy.pidr_2016_velostan.views.ExceptionAlert;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ExportCSV {
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";

	private static final String FILE_HEADER = "Informations"
							+COMMA_DELIMITER+ "Latitude"
							+COMMA_DELIMITER+ "Longitude";


	public static ExportResult writeCSVFile(RouteModel route) {

        List<String> list = new ArrayList<>();

		String directory = "csv";

        int nbFiles = (route.getListLocation().size() / 2000) + 1;

        File dir = new File(directory);

		// if the directory does not exist, create it
		if (!dir.exists()) {
			boolean result = false;
			try{
				dir.mkdir();
				result = true;
			} 
			catch(SecurityException e){
				e.printStackTrace();
				System.out.println("Directory "+directory+" not created.");  
			}        
			if(result) {    
				System.out.println("Directory "+directory+" has been created.");  
			}
		}		

        for (int i = 0; i < nbFiles; i++) {

            FileWriter fileWriter = null;

            try {
                String fileName = directory + "/route_" + route.getId_route() + "_" + i  + ".csv";
                list.add("\t - " + fileName);

                fileWriter = new FileWriter(fileName);

                fileWriter.append(FILE_HEADER.toString());

                fileWriter.append(NEW_LINE_SEPARATOR);

                int size = (i+1) * 2000; // ex: 2000 points de 0 à 1999
                if (size > route.getListLocation().size()) {
                    size = route.getListLocation().size();
                }
                for (int j = i*2000; j < size; j++) {
                    fileWriter.append("Numéro de vélo : "
                            +String.valueOf(route.getListLocation().get(j).getDevice()
                            +" ; Date : "+ Utils.getFormatter().format(route.getListLocation().get(j).getDate())
                            +" ; Vitesse : "+ route.getListLocation().get(j).getSpeed()+" km/h").replaceAll(COMMA_DELIMITER, " "));
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(String.valueOf(route.getListLocation().get(j).getLatitude()).replaceAll(COMMA_DELIMITER, " "));
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(String.valueOf(route.getListLocation().get(j).getLongitude()).replaceAll(COMMA_DELIMITER, " "));
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(NEW_LINE_SEPARATOR);
                }

                //System.out.println("CSV file was created successfully");

            } catch (Exception e) {
                System.out.println("Error in CsvFileWriter");
                e.printStackTrace();
                new ExceptionAlert(e, "Erreur : Génération du fichier CSV a échoué.").showAndWait();
            } finally {

                try {
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException e) {
                    System.out.println("Error while flushing/closing fileWriter !!!");
                    e.printStackTrace();
                }

            }
        }

        String dirAbsolute = dir.getAbsoluteFile().getParentFile().getAbsolutePath()+"/"+directory;
        ExportResult result = new ExportResult(dirAbsolute, list);

		return result;
	}
}
