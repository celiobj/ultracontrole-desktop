/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util.ComboBoxSearch;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class JComboBoxFilterMain {

    public static void main(String[] args) {
        List<String> employees = EmployeeDataAccess.getEmployees();
        JComboBox<String> comboBox = new JComboBox<>(
                employees.toArray(new String[employees.size()]));

        ComboBoxFilterDecorator<String> decorate = ComboBoxFilterDecorator.decorate(comboBox,CustomComboRenderer::getEmployeeDisplayText,JComboBoxFilterMain::employeeFilter);

        comboBox.setRenderer(new CustomComboRenderer(decorate.getFilterTextSupplier()));

        JPanel panel = new JPanel();
        panel.add(comboBox);

        JFrame frame = createFrame();
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static boolean employeeFilter(String emp, String textToFilter) {
        if (textToFilter.isEmpty()) {
            return true;
        }
        return CustomComboRenderer.getEmployeeDisplayText(emp).toLowerCase()
                .contains(textToFilter.toLowerCase());
    }

    public static JFrame createFrame() {
        JFrame frame = new JFrame("JComboBox Filter Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(600, 300));
        return frame;
    }
}
