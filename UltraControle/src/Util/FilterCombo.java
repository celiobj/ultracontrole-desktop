/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

/**
 *
 * @author celiobj
 */
import Classes.Produto;
import Persistencia.RepositorioProduto;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This class extends JComboBox providing it with filtering capability.
 *
 * So the idea is this
 *
 * 1. Focus the combo 2. Type a character sequence 3. With each key press the
 * box will update itself filtering out the items that do not match the users
 * character sequence.
 *
 * Note 1: The user can press the left and right arrow keys to move forward and
 * backward in the filter character sequence.
 *
 * Note 2: The user can press the "Back Space", "Delete", or "Escape" keys to
 * restore the original values and reset the filtering sequence.
 *
 * Enjoy!
 *
 * @author Corbin Holland
 */
public class FilterCombo extends JComboBox {

    public FilterCombo(String[] itemList) {
        FilterComboBoxModel model = new FilterComboBoxModel(itemList);
        setModel(model);

        model.addAllElements(itemList);

        // Remove standard key listeners that come with the JComboBox
        KeyListener[] lis = getKeyListeners();
        for (int i = 0; i < lis.length; i++) {
            removeKeyListener(lis[i]);
        }

        // Add custom KeyListener class
        addKeyListener(model.getKeyListener());

        // Add custom ActionListener class
        addActionListener(model.getActionListener());

        // Add your own custom action listener here if needed
        addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.out.println("Action Listener in FilterCombo class -- Add your code here");
            }

        });

        // Add your own custom item listener here if needed
        addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                System.out.println("Item Listener in FilterCombo class -- Add your code here");
            }

        });

    }

    public class FilterComboBoxModel extends DefaultComboBoxModel {

        private String[] masterItemList;
        private String masterSelectedItem;
        private StringBuilder filter = new StringBuilder(8);
        private int fidx = 0;
        private CustomKeyListener keylis = null;
        private CustomActionListener actionlis = null;

        private ActionListener[] actionLisList;
        private ItemListener[] itemLisList;

        public FilterComboBoxModel(String[] itemList) {
            initComboBoxModel(itemList);
        }

        private void initComboBoxModel(String[] itemList) {
            setMasterItemList(itemList);
        }

        public CustomKeyListener getKeyListener() {
            if (keylis == null) {
                keylis = new CustomKeyListener();
            }
            return keylis;
        }

        public CustomActionListener getActionListener() {
            if (actionlis == null) {
                actionlis = new CustomActionListener();
            }
            return actionlis;
        }

        public void setMasterItemList(String[] items) {
            masterItemList = items;
            masterSelectedItem = items[0];
            restoreItems();
        }

        public void addAllElements(Object[] items) {
            for (int i = 0; i < items.length; i++) {
                addItem(items[i]);
            }
        }

        public Object[] getAllElements() {
            Object[] list = new Object[getItemCount()];
            for (int i = 0; i < list.length; i++) {
                list[i] = this.getElementAt(i);
            }
            return list;
        }

        public void filterItems(String pat) {

            ArrayList<String> newList = new ArrayList<String>();

            String[] list = masterItemList;

            int patlen = pat.length();
            for (int i = 0; i < list.length; i++) {
                String item = list[i];
                if (item.length() < patlen) {
                    continue;
                }
                String tok = item.substring(0, patlen);

                if (tok.equalsIgnoreCase(pat)) {
                    newList.add(item);
                }
            }

            // Add the new list to the combobox - notice we disable listeners
            suspendAllListeners();
            removeAllElements();
            if (newList.isEmpty()) {
                addItem("Vazio");
            } else {
                addAllElements(newList.toArray());
            }
            if (fidx == 0) {
                setSelectedItem(masterSelectedItem);
            }

            restoreAllListeners();

        }

        private void suspendAllListeners() {
            actionLisList = getActionListeners();
            for (ActionListener a : actionLisList) {
                removeActionListener(a);
            }
            itemLisList = getItemListeners();
            for (ItemListener i : itemLisList) {
                removeItemListener(i);
            }
        }

        private void restoreAllListeners() {
            for (ActionListener a : actionLisList) {
                addActionListener(a);
            }
            for (ItemListener i : itemLisList) {
                addItemListener(i);
            }
        }

        public void restoreItems() {
            suspendAllListeners();
            removeAllElements();
            addAllElements(masterItemList);
            resetFilter();
            setSelectedItem(masterSelectedItem);
            restoreAllListeners();
        }

        private void resetFilter() {
            filter.setLength(0);
            fidx = 0;
        }

        public class CustomKeyListener extends KeyAdapter {

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z) {
                    char letter = (char) keyCode;
                    filter.insert(fidx, letter);
                    fidx++;
                    filter.setLength(fidx);
                    filterItems(filter.toString());
                } else if (keyCode == KeyEvent.VK_LEFT) {
                    fidx = (fidx == 0 ? fidx : fidx - 1);
                    filterItems(filter.substring(0, fidx));
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    fidx = (fidx == filter.length() ? fidx : fidx + 1);
                    filterItems(filter.substring(0, fidx));
                } else if (keyCode == KeyEvent.VK_BACK_SPACE
                        || keyCode == KeyEvent.VK_DELETE
                        || keyCode == KeyEvent.VK_ESCAPE) {
                    restoreItems();
                }

//            System.out.println("fidx = " + fidx + " -- Filter: " + filter);
                setToolTipText("Current filter: " + filter.substring(0, fidx));
            }
        }

        public class CustomActionListener implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                masterSelectedItem = (String) getSelectedItem();
                restoreItems();
            }
        }
    }

    public static void main(String[] args) {
        
        FilterCombo fc = new FilterCombo(args);

        // Create the frame
        JFrame frame = new JFrame("FilterCombo Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.add(fc);
        frame.add(panel);
        frame.pack();

        frame.setVisible(true);
    }
}
