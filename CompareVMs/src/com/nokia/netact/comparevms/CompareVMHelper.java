/**
 * 
 */
package com.nokia.netact.comparevms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.nokia.netact.comparevms.output.Comparison;

/**
 * Helper class to do data comparison
 * 
 * @author dmehar
 *
 */
public class CompareVMHelper {

    private static FormulaEvaluator evaluator = null;
    private static int columnHeaders = 0;
    private final static int m_Fact_1024 = 1024;
    private final static int m_Fact_1000 = 1000;
    private static Map<String, Integer> paramIndexes = null;
    private static Map<String, String> datastoreUsageValues = null;
    private static boolean is3XL = false;
    private static boolean openStack;
    public static boolean matchResult = true;

    /**
     * This method iterates through all VM types and get appropriate row from excel sheet for further comparison
     * 
     * @param fileData
     * @param sheet
     * @param vmType 
     * @return
     */
    public Map<String, List<Comparison>> compareVMs(VConfVariables fileData, Sheet sheet, String vmType) {
        Map<String, List<Comparison>> vms = new HashMap<String, List<Comparison>>();
        
        if (vmType != null && vmType.contains("OpenStack")) {
            openStack = true;
        } else {
            openStack = false;
        }

        for (VM vm : fileData.getVm()) {
            String vmName = vm.getVm_name();
            for (int i = columnHeaders + 1; i <= sheet.getLastRowNum(); i++) {
                String cellValue = sheet.getRow(i).getCell(0).getStringCellValue();
                if (cellValue != null) {
                    cellValue = cellValue.replace("#", "");
                }

                if (vmName.equalsIgnoreCase(cellValue)) {
                    List<Comparison> results = doCompare(vm, sheet.getRow(i), vmType);
                    vms.put(vmName, results);
                    break;
                }
            }
        }
        return vms;
    }

    /**
     * Get Datastore usage values
     * 
     * @param sheet
     */
    public void initDatastoreUsageValues(Sheet sheet) {
        datastoreUsageValues = new HashMap<String, String>();
        if (sheet.getSheetName().startsWith("3XL")) {
            getDatastoreUsageValuesFor3XL(sheet);
        } else {
            for (int rowNum = sheet.getLastRowNum(); rowNum >= 0; rowNum--) {
                Row row = sheet.getRow(rowNum);
                if (row != null) {
                    String firstCell = getCellValue(row.getCell(0));
                    String usageValue = getCellValue(row.getCell(1));
                    if ("Datastore".equals(firstCell)) {
                        break;
                    }
                    if ("Backup".equals(firstCell)) {
                        datastoreUsageValues.put("Backuptool", usageValue);
                    } else if ("Customer".equals(firstCell)) {
                        datastoreUsageValues.put("Customer", usageValue);
                    } else if ("Global".equals(firstCell)) {
                        datastoreUsageValues.put("Global", usageValue);
                    } else if ("DB Arc".equals(firstCell)) {
                        datastoreUsageValues.put("Arc", usageValue);
                    } else if ("DB Data".equals(firstCell)) {
                        datastoreUsageValues.put("DB", usageValue);
                    } else if ("DB Redo".equals(firstCell)) {
                        datastoreUsageValues.put("Redo", usageValue);
                    } else if ("PMDatastore".equals(firstCell)) {
                        datastoreUsageValues.put("Ext rep", usageValue);
                    } else if ("DB FB".equals(firstCell)) {
                        datastoreUsageValues.put("DB Flashback", usageValue);
                    }
                }
            }
        }
    }

    /**
     * Get Datastore usage values for 3XL configuration
     * 
     * @param sheet
     */
    private void getDatastoreUsageValuesFor3XL(Sheet sheet) {
        for (int rowNum = sheet.getLastRowNum(); rowNum >= 0; rowNum--) {
            Row row = sheet.getRow(rowNum);
            if (row != null) {
                String firstCell = getCellValue(row.getCell(0));
                String usageValue = getCellValue(row.getCell(1));

                if ("Datastore".equals(firstCell)) {
                    break;
                }
                if (firstCell != null) {
                    if (firstCell.startsWith("DBBackup")) {
                        addDatastoreValue("Backuptool", usageValue);
                    } else if (firstCell.startsWith("Customer")) {
                        addDatastoreValue("Customer", usageValue);
                    } else if (firstCell.startsWith("NFSGlobal")) {
                        addDatastoreValue("Global", usageValue);
                    } else if (firstCell.startsWith("DBArch")) {
                        addDatastoreValue("Arc", usageValue);
                    } else if (firstCell.startsWith("DBData")) {
                        addDatastoreValue("DB", usageValue);
                    } else if (firstCell.startsWith("DBRedo")) {
                        addDatastoreValue("Redo", usageValue);
                    } else if (firstCell.startsWith("PMDatastore")) {
                        addDatastoreValue("PMDatastore", usageValue);
                    } else if (firstCell.startsWith("NFSBackup")) {
                        addDatastoreValue("NFSBackup", usageValue);
                    } else if (firstCell.startsWith("DB FB")) {
                        addDatastoreValue("DB Flashback", usageValue);
                    }
                }
            }
        }
    }

    /**
     * Maintain datastoreUsageValues
     * 
     * @param usageValue
     */
    private void addDatastoreValue(String column, String usageValue) {
        String value = datastoreUsageValues.get(column);
        if (value == null) {
            value = "0";
        }
        Double sum = Double.parseDouble(value) + (usageValue != null ? Double.parseDouble(usageValue) : 0);
        datastoreUsageValues.put(column, String.valueOf(sum));
    }

    /**
     * This method decides the column indexes in Excel sheet for parameters given ParametersToCompare.properties file
     * Initializes paramIndexes map
     * 
     * @param sheet
     */
    public void initParamIndexes(Sheet sheet) {
        paramIndexes = new HashMap<String, Integer>();
        for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                continue;
            }
            List<String> headers = new ArrayList<String>();
            for (int cellNum = 0; cellNum <= 7; cellNum++) {
                headers.add(getCellValue(row.getCell(cellNum)));
            }

            int match = 0;
            for (String head : headers) {
                if ("Small".equals(head) || "Mainstream".equals(head) || "Large".equals(head)
                        || "Startup Priority".equals(head) || "Operating System".equals(head) || "Services".equals(head)
                        || "vCPU".equals(head) || "vRAM".equals(head)) {
                    match++;
                }
            }
            // Decide column headers
            if (match > 5) {
                columnHeaders = rowNum;

                // get required parameter indexes
                for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
                    String cellValue = getCellValue(row.getCell(cellNum));
                    if (cellValue != null) {
                        cellValue = cellValue.trim();
                        if (ReadFile.parametersToCompare.containsKey(cellValue)
                                && !paramIndexes.containsKey(cellValue)) {
                            paramIndexes.put(cellValue, cellNum);
                        } else if (cellValue.contains("DB Flashback")) {
                            paramIndexes.put("DB Flashback", cellNum);
                        }
                    }
                }
                break;
            }
        }

    }

    /**
     * Compare resource sheet value and vConf value 
     * returns List<Comparison> object
     * 
     * @param vm
     * @param row
     * @param vmType 
     * @param isOpenStack
     */
    private List<Comparison> doCompare(VM vm, Row row, String vmType) {
        List<Comparison> results = new ArrayList<Comparison>();

        for (String param : ReadFile.parametersToCompare.stringPropertyNames()) {

            if (paramIndexes.get(param) != null) {
                String resValue = getCellValue(row.getCell(paramIndexes.get(param)));

                if (resValue != null && datastoreUsageValues.get(param) != null) {
                    resValue = datastoreUsageValues.get(param);
                    if ("Backuptool".equals(param) && "vm5".equals(vm.getVm_name())) {
                        resValue = datastoreUsageValues.get("NFSBackup");
                    }
                }

                if (resValue == null) {
                    continue;
                }
                String vConfValue = getVConfValue(param, vm, ReadFile.parametersToCompare.getProperty(param));
                Comparison comparison = new Comparison();
                comparison.setAttribute(param);
                if ("Datastore".equals(param)) {
                    matchStringValues(vConfValue, resValue, comparison);
                    comparison.setComment("vConf value: " + vConfValue + ", Resource sheet value: " + resValue);
                } else if ("Ext rep".equals(param) && !openStack && !is3XL) {
                    if (vmType.contains("Small") || vmType.contains("Mainstream")) {
                        resValue = Double.toString(Double.parseDouble(resValue) / 2);
                    } else {
                        resValue = Double.toString(Double.parseDouble(resValue) / 4);
                    }
                    matchValues(vConfValue, resValue, comparison, param);
                    comparison.setComment("vConf value: " + vConfValue + " MB, Resource sheet value: " + resValue + " GB");
                } else if ("vCPU".equals(param)) {
                    matchValues(vConfValue, resValue, comparison, param);
                    comparison.setComment("vConf value: " + vConfValue + " GB, Resource sheet value: " + resValue + " GB");
                } else {
                    matchValues(vConfValue, resValue, comparison, param);
                    comparison.setComment("vConf value: " + vConfValue + " MB, Resource sheet value: " + resValue + " GB");
                }
                results.add(comparison);
            }
        }

        return results;
    }

    /**
     * This method compares input values and give matching results. vConfValue is in
     * MB and resValue is in GB/MB. Before matching converting GB value into MB.
     * Conversion factor used is either 1000 or 1024
     * 
     * @param vConfValue
     * @param resValue
     * @param comparison
     * @param param
     */
    private void matchValues(String vConfValue, String resValue, Comparison comparison, String param) {

        if (vConfValue != null && (Double.parseDouble(vConfValue) == Double.parseDouble(resValue)
                || (Double.parseDouble(vConfValue) == (Double.parseDouble(resValue) * m_Fact_1024))
                || (Double.parseDouble(resValue)  * m_Fact_1000 - Double.parseDouble(vConfValue) <10 ) && (Double.parseDouble(resValue)  * m_Fact_1000 - Double.parseDouble(vConfValue) >=0 ) )) {
            comparison.setMatchSatus("MATCH");
        } else {
            comparison.setMatchSatus("MISMATCH");
            matchResult = false;
        }
    }

    /**
     * Match String value and set matchStaus value
     * 
     * @param vConfValue
     * @param resValue
     * @param comparison
     */
    private void matchStringValues(String vConfValue, String resValue, Comparison comparison) {
        if (vConfValue != null && resValue != null && vConfValue.equals(resValue)) {
            comparison.setMatchSatus("MATCH");
        } else {
            comparison.setMatchSatus("MISMATCH");
            matchResult = false;
        }
    }

    /**
     * Get data from Cell based on cell type.
     * 
     * @param cell
     * @return String value
     */
    private String getCellValue(Cell cell) {
        String value = null;
        if (cell != null) {
            CellValue cellValue = evaluator.evaluate(cell);
            if (cellValue != null) {
                switch (cellValue.getCellType()) {
                case NUMERIC:
                    value = String.valueOf(cell.getNumericCellValue());
                    break;
                case STRING:
                    value = cell.getStringCellValue();
                    break;
                case BLANK:
                    break;
                case ERROR:
                    break;
                default:
                    value = cell.getStringCellValue();
                    break;
                }
            }
        }
        return value;
    }

    /**
     * Initializing evaluator value
     * 
     * @param workbook
     */
    public void setEvaluator(Workbook workbook) {
        if (workbook != null) {
            evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        }
    }

    /**
     * This method helps to sort the Map based on VM names
     * 
     * @param vms
     * @return sorted Map
     */
    public Map<String, List<Comparison>> sortBasedonVMName(Map<String, List<Comparison>> vms) {

        List<Entry<String, List<Comparison>>> listOfEntries = new ArrayList<Entry<String, List<Comparison>>>(
                vms.entrySet());
        // Do sorting using Comparator
        Collections.sort(listOfEntries, new VMComparator());

        Map<String, List<Comparison>> sortedByValue = new LinkedHashMap<String, List<Comparison>>(listOfEntries.size());

        for (Entry<String, List<Comparison>> entry : listOfEntries) {
            sortedByValue.put(entry.getKey(), entry.getValue());
        }

        return sortedByValue;
    }

    /**
     * This method accepts the header parameter and return the vConf file value for that attribute
     * 
     * @param header
     * @param vm
     * @param vConfParam
     * @return
     */
    public String getVConfValue(String header, VM vm, String vConfParam) {
        double diskSize = 0;
        if ("vCPU".equals(header)) {
            return vm.getVcpu();
        } else if ("vRAM".equals(header)) {
            return vm.getMemoryMB();
        } else if ("Memory Reservation".equals(header)) {
            return vm.getMemory_reservation();
        } else if ("Datastore".equals(header)) {
            return vm.getDatastore_name();
        } else {
            if (is3XL) {
                for (Disk disk : vm.getDisk()) {
                    String datastoreName = disk.getDatastore_name();
                    String diskName = disk.getDisk_name();
                    if ("VMDK".equals(header) && datastoreName.equals(vm.getDatastore_name())
                            && !diskName.startsWith("isdk")) {
                        diskSize = diskSize + Double.parseDouble(disk.getSizeMB());
                    } else if ("Backuptool".equals(header) && "NFSBackup".equals(datastoreName)
                            && "vm5".equals(vm.getVm_name())) {
                        diskSize = diskSize + Double.parseDouble(disk.getSizeMB());
                    } else {
                        String value = ReadFile.parametersToCompare_3xl.getProperty(header);
                        if (value != null) {
                            List<String> dsNames = new ArrayList<String>(Arrays.asList(value.split("\\|")));
                            if (dsNames.contains(datastoreName)) {
                                diskSize = diskSize + Double.parseDouble(disk.getSizeMB());
                            }
                        }
                    }
                }
            } else {
                for (Disk disk : vm.getDisk()) {
                    String datastoreName = disk.getDatastore_name();
                    String diskName = disk.getDisk_name();

                    if ("VMDK".equals(header) && datastoreName.equals(vm.getDatastore_name())
                            && !diskName.startsWith("isdk")) {
                        diskSize = diskSize + Double.parseDouble(disk.getSizeMB());
                    } else if ("Global".equals(header) && (vConfParam != null && vConfParam.contains(datastoreName)
                            && !"NFS_home".equals(diskName)) || matchDatastore(vConfParam, diskName)) {
                        diskSize = diskSize + Double.parseDouble(disk.getSizeMB());
                    } else if (vConfParam != null && matchDatastore(vConfParam, datastoreName)
                            || matchDatastore(vConfParam, diskName)) {
                        diskSize = diskSize + Double.parseDouble(disk.getSizeMB());
                    }
                }
            }
        }

        return String.valueOf(diskSize);
    }

    /**
     * Match string values
     * 
     * @param vConfParam
     * @param datastoreName
     * @return
     */
    private boolean matchDatastore(String vConfParam, String datastoreName) {
        return datastoreName != null && !datastoreName.isEmpty() && vConfParam.contains(datastoreName);
    }

    /**
     * Check if it is 3XL configuration
     * 
     * @param sheet
     */
    public void is3XLConfiguration(Sheet sheet) {
        if (sheet.getSheetName().startsWith("3XL")) {
            is3XL = true;
        } else {
            is3XL = false;
        }

    }

}
