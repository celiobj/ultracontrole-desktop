/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util.TextFieldSearch;

import java.awt.Point;
import java.util.List;
import java.util.function.Function;
import javax.swing.text.JTextComponent;

/**
 *
 * @author celiobj
 */
public class TextComponentSuggestionClient implements SuggestionClient<JTextComponent> {

    private final Function<String, List<String>> suggestionProvider;

    public TextComponentSuggestionClient(Function<String, List<String>> suggestionProvider) {
        this.suggestionProvider = suggestionProvider;
    }

    @Override
    public Point getPopupLocation(JTextComponent invoker) {
        return new Point(0, invoker.getPreferredSize().height);
    }

    @Override
    public void setSelectedText(JTextComponent invoker, String selectedValue) {
        invoker.setText(selectedValue);
    }

    @Override
    public List<String> getSuggestions(JTextComponent invoker) {
        return suggestionProvider.apply(invoker.getText().trim());
    }
}
