package eu.telecomnancy.pidr_2016_velostan.export;

import java.util.List;

/**
 * Created by yoann on 21/05/16.
 */
public class ExportResult {

    private String directory;
    List<String> list;

    public ExportResult(String directory,List<String> list) {
        this.directory = directory;
        this.list = list;
    }

    public String getDirectory() {
        return directory;
    }

    public List<String> getList() {
        return list;
    }
}
