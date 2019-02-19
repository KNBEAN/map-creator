package gui;

import data.implementations.Floor;

import javax.swing.*;
import java.awt.*;

public class FloorListRenderer extends JTextArea implements ListCellRenderer<Floor> {

    protected FloorListRenderer(){
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }


    @Override
    public Component getListCellRendererComponent(JList<? extends Floor> list, Floor value, int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.getFloorInfo());

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
