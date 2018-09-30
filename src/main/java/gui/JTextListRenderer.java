package gui;

import javax.swing.*;
import java.awt.*;

public class JTextListRenderer extends JTextArea implements ListCellRenderer {

    protected JTextListRenderer(){
        super();
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        setText(value.toString());

        Color background = null;
        Color foreground = null;

        if (isSelected){
            background = list.getSelectionBackground();
            foreground = list.getSelectionForeground();
        }else{
            background = list.getBackground();
            foreground = list.getForeground();
        }

        setBackground(background);
        setForeground(foreground);

        setEnabled(true);
        setFont(list.getFont().deriveFont(14.0f));

        return this;
    }
}
