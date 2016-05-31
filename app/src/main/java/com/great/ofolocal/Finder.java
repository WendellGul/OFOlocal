package com.great.ofolocal;

import android.os.Environment;

import java.io.*;
import java.util.HashMap;

/**
 * Created by Great on 2016/5/30.
 */
public class Finder {
    public final String PATH = Environment.getExternalStorageDirectory().getPath() + "/OFOlocal/";
    private BufferedReader reader;
    private BufferedWriter writer;
    private File file;
    private HashMap<String, String> map;

    public Finder() {
        map = new HashMap<>();
        File dir = new File(PATH);
        if(!dir.exists())
            dir.mkdirs();
        file = new File(PATH + "records");
        try {
            if(!file.exists())
                file.createNewFile();
            reader = new BufferedReader(new FileReader(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadRecords();
    }

    private void loadRecords() {
        String record;
        try {
            while ((record = reader.readLine()) != null) {
                map.put(record.substring(0, 5), record.substring(6));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String findRecord(String id) {
        return map.get(id);
    }

    public boolean importRecords(String id, String psw) {
        if(map.get(id) != null && map.get(id).compareTo(psw) == 0)
            return false;
        try {
            writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(id + " " + psw + "\n");
            writer.close();
            map.put(id, psw);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean importRecords(File file) {

        return true;
    }
}
