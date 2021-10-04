/**
 * 
 */
package com.nokia.netact.comparevms;

import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import com.nokia.netact.comparevms.output.Comparison;

/**
 * @author dmehar
 *
 */
public class VMComparator implements Comparator<Entry<String, List<Comparison>>> {

    @Override
    public int compare(Entry<String, List<Comparison>> o1, Entry<String, List<Comparison>> o2) {
        String vm1 = o1.getKey();
        String vm2 = o2.getKey();

        int vmNumber1 = Integer.valueOf(vm1.substring(2));
        int vmNumber2 = Integer.valueOf(vm2.substring(2));
        int comp = 0;
        if (vmNumber1 > vmNumber2) {
            comp = 1;
        } else {
            comp = -1;
        }
        return comp;
    }

}
