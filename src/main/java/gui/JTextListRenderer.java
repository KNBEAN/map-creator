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

        if (isSelected){
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }else{
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        setEnabled(true);
        setFont(list.getFont().deriveFont(14.0f));

        return this;
    }
}
