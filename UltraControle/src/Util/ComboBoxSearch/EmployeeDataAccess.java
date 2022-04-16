/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util.ComboBoxSearch;

import org.fluttercode.datafactory.impl.DataFactory;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDataAccess {

    public static List<String> getEmployees() {
        List<String> list = new ArrayList<>();
        String[] depts = {"IT", "Account", "Admin", "Sales"};
        DataFactory df = new DataFactory();
        for (int i = 1; i <= 100; i++) {
            Employee e = new Employee();
            e.setName(df.getName());
            e.setAddress(df.getAddress() + ", " + df.getCity());
            e.setDept(df.getItem(depts));
            e.setPhone(df.getNumberText(3) + "-" + df.getNumberText(3)
                    + "-" + df.getNumberText(4));
           // list.add(e);
        }
        return list;
    }
}
