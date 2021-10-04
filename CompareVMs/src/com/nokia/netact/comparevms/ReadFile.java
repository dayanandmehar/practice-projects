package com.nokia.netact.comparevms;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

/**
 * @author dmehar
 *
 */
public class ReadFile {

    public static Properties resourceMapping = new Properties();
    public static Properties parametersToCompare = new Properties();
    public static Properties parametersToCompare_3xl = new Properties();
    static String separator = System.getProperty("file.separator");

    static {
        try {
            FileReader resReader = new FileReader("./config/ResourcesMapping.properties");
            resourceMapping.load(resReader);

            FileReader paramReader = new FileReader("./config/ParametersToCompare.properties");
            parametersToCompare.load(paramReader);

            FileReader param3XLReader = new FileReader("./config/DatastoreNames_3XL.properties");
            parametersToCompare_3xl.load(param3XLReader);

        } catch (IOException e) {
            System.out.println("Exception while reading configuration file.");
        }
    }

    /**
     * This method reads Resource excel sheet configured in ResourcesMapping.properties
     * and convert it in Workbook
     * 
     * @return workbook
     * @throws IOException
     */
    public Workbook ReadResourcesFile() throws IOException {
        Workbook workbook = null;
        String path = resourceMapping.getProperty("ResourceXlsDir") + separator + resourceMapping.getProperty("ResoursesFile");
        try {
            workbook = WorkbookFactory.create(new File(path));
            // System.out.println("All name " + workbook.getAllNames());
        } catch (EncryptedDocumentException e) {
            System.out.println("EncryptedDocumentException " + e);
            throw e;
        } catch (IOException e) {
            System.out.println("Exception while reading resorces file.");
            throw e;
        }
        return workbook;
    }

    /**
     * This method reads yaml file with fileName argument from resources path configured ResourcesMapping.properties
     * and returns VConfVariables object
     * 
     * @param object
     * @return VConfVariables
     * @throws Exception
     */
    public VConfVariables ReadYamlFile(String fileName) throws Exception {

        VConfVariables fileData = null;
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            String filePath = resourceMapping.getProperty("vconfFilesDir") + separator + fileName;
            fileData = mapper.readValue(new File(filePath.trim()), VConfVariables.class);
            // System.out.println("VConfVariables: " + fileData);
        } catch (Exception e) {
            System.out.println("Exception...." + e);
            throw e;
        }
        return fileData;
    }

}