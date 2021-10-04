/**
 * 
 */
package com.nokia.netact.comparevms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.nokia.netact.comparevms.output.Comparison;
import com.nokia.netact.comparevms.output.ReportGenerator;

/**
 * @author dmehar
 *
 */
public class Main {

    private static Map<String, Map<String, List<Comparison>>> output = new HashMap<String, Map<String, List<Comparison>>>();
    static CompareVMHelper compareVMHelper = new CompareVMHelper();
    static ReadFile readFile = new ReadFile();

    /**
     * Main method for start of VM data comparison
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) {
        try {
            Workbook workbook = readFile.ReadResourcesFile();
            compareVMHelper.setEvaluator(workbook);

            Set<Entry<Object, Object>> vmTypes = ReadFile.resourceMapping.entrySet();
            for (Entry<Object, Object> vmType : vmTypes) {
                if ("ResourceXlsDir".equals(vmType.getKey().toString()) || "ResoursesFile".equals(vmType.getKey().toString())
                        || "vconfFilesDir".equals(vmType.getKey().toString())) {
                    continue;
                }
                VConfVariables fileData = readFile.ReadYamlFile((String) vmType.getValue());
                Sheet sheet = workbook.getSheet(vmType.getKey().toString());

                compareVMHelper.initParamIndexes(sheet);
                compareVMHelper.initDatastoreUsageValues(sheet);
                compareVMHelper.is3XLConfiguration(sheet);

                Map<String, List<Comparison>> vms = compareVMHelper.compareVMs(fileData, sheet,
                        vmType.getKey().toString());
                // Sort Map based on VM names
                vms = compareVMHelper.sortBasedonVMName(vms);

                output.put(vmType.getKey().toString(), vms);
            }
            System.out.println("Data comparison is successful.");
            // Generate HTML report
            ReportGenerator.generateReport(output);
            System.out.println("Result: " + CompareVMHelper.matchResult);
        } catch (Exception e) {
            System.out.println("Exception occured while comparing VM data. " + e.getMessage());
        }
    }

}
