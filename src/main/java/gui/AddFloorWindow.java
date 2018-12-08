package gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import data.dao.FloorDAOImp;
import data.dao.interfaces.FloorDAO;
import data.implementations.Floor;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.util.ResourceBundle;

public class AddFloorWindow extends JDialog {
    private JPanel contentPane;
    private JButton addNewFloorButton;
    private JLabel floorImageContainer;
    private JTextField floorField;
    private JTextField floorTagField;
    private JButton addImageButton;
    private JLabel imagePathLabel;
    private JList floorsList;
    private JTabbedPane tabbedPane1;
    private JButton changeFloorButton;
    private JButton removeFloorButton;
    private JTabbedPane tabbedPane2;
    private JList floorsWithoutMap;
    private FloorDAO floorDAO = new FloorDAOImp();
    private JFileChooser imageChooser;
    private String imagePath;
    private DefaultListModel<Floor> floorsListModel;
    private DefaultListModel<Floor> floorsWithoutMapModel;
    private int floorsWithoutMapIndex = -1;
    private int floorsListSelectedIndex = -1;

    public AddFloorWindow(String title) {
        setTitle(title);
        $$$setupUI$$$();
        setContentPane(contentPane);
        setResizable(false);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setSize(700, 350);
        setLocation(screenSize.width / 2 - getWidth() / 2, screenSize.height / 2 - getHeight() / 2);


        addImageButton.addActionListener(e -> {

            int imageContainerHeight = floorImageContainer.getHeight();
            int imageContainerWidth = floorImageContainer.getWidth();

            imageChooser = new JFileChooser();
            imageChooser.setFileFilter(new FileNameExtensionFilter
                    (ResourceBundle.getBundle("strings").getString("image_files") + " (*.jpg, *.jpeg, *.png)",
                            "jpg", "jpeg", "png"));

            int result = imageChooser.showOpenDialog(getParent());
            if (result == JFileChooser.APPROVE_OPTION) {
                imagePath = imageChooser.getSelectedFile().getPath();
                ImageIcon imageIcon = new ImageIcon(imagePath);

                System.out.println("Image Height: " + imageIcon.getIconHeight() + "\n" +
                        "Image Width: " + imageIcon.getIconWidth());

                if (imageIcon.getIconHeight() > imageContainerHeight && imageIcon.getIconWidth() > imageContainerWidth)
                    floorImageContainer.setIcon(PaintPanel.resizeImageIcon(imageIcon,
                            floorImageContainer.getWidth(),
                            floorImageContainer.getHeight()));
                else
                    floorImageContainer.setIcon(imageIcon);
                    imagePathLabel.setText(imagePath);

            } else {
                System.out.println("Open command cancelled by user");
            }
        });

        addNewFloorButton.addActionListener(e -> {
            String floorFieldText = floorField.getText().trim();
            int floorNumber;
            String floorTagText = floorTagField.getText().trim();

            if (floorFieldText.isEmpty() || floorTagText.isEmpty() || imagePath == null) {

                JOptionPane.showMessageDialog(getParent(),
                        ResourceBundle.getBundle("strings").getString("empty_fields_error_message"),
                        ResourceBundle.getBundle("strings").getString("error"),
                        JOptionPane.ERROR_MESSAGE);

            } else {
                try {
                    floorNumber = Integer.parseInt(floorFieldText);
                    if (floorNumber < 0) {
                        JOptionPane.showMessageDialog(getParent(),
                                ResourceBundle.getBundle("strings").getString("integer_error_message"),
                                ResourceBundle.getBundle("strings").getString("error"),
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        Floor floor = new Floor(floorNumber, floorTagText, imagePath);
                        floorsListModel.addElement(floor);
                        try {
                            Floor floorToRemove = floorsWithoutMapModel.getElementAt(floorsWithoutMapIndex);
                            floorsWithoutMapModel.removeElement(floorToRemove);
                            floorDAO.update(floor);
                        } catch (ArrayIndexOutOfBoundsException ex) {
                            floorDAO.insert(floor);
                        }

                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(getParent(),
                            ResourceBundle.getBundle("strings").getString("integer_error_message"),
                            ResourceBundle.getBundle("strings").getString("error"),
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        floorsList.addListSelectionListener(e -> {
            floorsListSelectedIndex = floorsList.getSelectedIndex();

            if (floorsListSelectedIndex == -1) {
                if (!floorsListModel.isEmpty()) {
                    floorsListSelectedIndex++;
                }
            }
            if (floorsListSelectedIndex > -1) {
                Floor selectedElement = floorsListModel.elementAt(floorsListSelectedIndex);
                floorField.setText(String.valueOf(selectedElement.getFloors()));
                floorTagField.setText(selectedElement.floorName(floorsListSelectedIndex));

                ImageIcon imageIcon = new ImageIcon(selectedElement.getImagePath());
                floorImageContainer.setIcon(PaintPanel.resizeImageIcon(imageIcon,
                        floorImageContainer.getWidth(),
                        floorImageContainer.getHeight()));
                imagePath = selectedElement.getImagePath();
                imagePathLabel.setText(selectedElement.getImagePath());
            }

        });

        floorsWithoutMap.addListSelectionListener(e -> {

            floorsWithoutMapIndex = floorsWithoutMap.getSelectedIndex();
            if (floorsWithoutMapIndex == -1) {
                if (!floorsWithoutMapModel.isEmpty()) {
                    floorsWithoutMapIndex++;
                }
            }
            if (floorsWithoutMapIndex > -1) {
                Floor selectedElement = floorsWithoutMapModel.elementAt(floorsWithoutMapIndex);
                floorField.setText(String.valueOf(selectedElement.getFloors()));
                floorTagField.setText(selectedElement.floorName(floorsWithoutMapIndex));

                ImageIcon imageIcon = new ImageIcon(selectedElement.getImagePath());
                floorImageContainer.setIcon(PaintPanel.resizeImageIcon(imageIcon,
                        floorImageContainer.getWidth(),
                        floorImageContainer.getHeight()));
                imagePath = selectedElement.getImagePath();
                imagePathLabel.setText(selectedElement.getImagePath());
            }

        });

        removeFloorButton.addActionListener(e -> {
            int removeSelection = JOptionPane.showConfirmDialog(getParent(),
                    ResourceBundle.getBundle("strings").getString("delete") + " " +
                            floorsListModel.getElementAt(floorsListSelectedIndex).floorName(floorsListSelectedIndex) +
                            "? " + ResourceBundle.getBundle("strings").getString("remove_graph"),
                    ResourceBundle.getBundle("strings").getString("confirm"),
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (removeSelection == JOptionPane.OK_OPTION) {
                Floor temporaryFloor = floorsListModel.getElementAt(floorsListSelectedIndex);
                floorsListModel.removeElement(temporaryFloor);
                floorDAO.delete(temporaryFloor.getFloors());
            }

        });

        changeFloorButton.addActionListener(e -> {
            if (floorsListSelectedIndex == -1) {
                JOptionPane.showMessageDialog(getParent(),
                        ResourceBundle.getBundle("strings").getString("change_selection_error_message"),
                        ResourceBundle.getBundle("strings").getString("error"),
                        JOptionPane.ERROR_MESSAGE);
            } else {

                Floor updatedFloor = new Floor(Integer.parseInt(floorField.getText()), floorTagField.getText(), imagePath);
                floorsListModel.setElementAt(updatedFloor, floorsListSelectedIndex);
                floorDAO.update(updatedFloor);
            }

        });
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        floorsListModel = new DefaultListModel<>();
        floorsWithoutMapModel = new DefaultListModel<>();

        floorsList = new JList(floorsListModel);
        floorsList.setCellRenderer(new FloorListRenderer());

        floorsWithoutMap = new JList(floorsWithoutMapModel);
        floorsWithoutMap.setCellRenderer(new FloorListRenderer());


        for (Floor f : floorDAO.getAllFloors()) {
            if (f.getImagePath() == null)
                floorsWithoutMapModel.addElement(f);
            else
                floorsListModel.addElement(f);
        }

    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 10, 0, 5), -1, -1));
        contentPane.add(panel1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, 1, null, null, null, 0, false));
        imagePathLabel = new JLabel();
        Font imagePathLabelFont = this.$$$getFont$$$(null, -1, 8, imagePathLabel.getFont());
        if (imagePathLabelFont != null) imagePathLabel.setFont(imagePathLabelFont);
        imagePathLabel.setText("");
        panel1.add(imagePathLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel2, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 1, new Insets(0, 5, 0, 0), -1, -1));
        panel2.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        floorImageContainer = new JLabel();
        floorImageContainer.setHorizontalAlignment(0);
        floorImageContainer.setHorizontalTextPosition(0);
        floorImageContainer.setText("");
        floorImageContainer.setVerticalAlignment(1);
        panel3.add(floorImageContainer, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTHEAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(128, 228), null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        addImageButton = new JButton();
        Font addImageButtonFont = this.$$$getFont$$$(null, Font.BOLD, 14, addImageButton.getFont());
        if (addImageButtonFont != null) addImageButton.setFont(addImageButtonFont);
        this.$$$loadButtonText$$$(addImageButton, ResourceBundle.getBundle("strings").getString("load_map"));
        addImageButton.setToolTipText(ResourceBundle.getBundle("strings").getString("load_map"));
        panel4.add(addImageButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        panel2.add(panel5, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(34, 146), null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel5.add(panel6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        floorField = new JTextField();
        Font floorFieldFont = this.$$$getFont$$$(null, -1, 14, floorField.getFont());
        if (floorFieldFont != null) floorField.setFont(floorFieldFont);
        panel6.add(floorField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(50, 29), null, 0, false));
        floorTagField = new JTextField();
        Font floorTagFieldFont = this.$$$getFont$$$(null, -1, 14, floorTagField.getFont());
        if (floorTagFieldFont != null) floorTagField.setFont(floorTagFieldFont);
        panel6.add(floorTagField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(50, 29), null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, Font.BOLD, 14, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        this.$$$loadLabelText$$$(label1, ResourceBundle.getBundle("strings").getString("floor"));
        panel6.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$(null, Font.BOLD, 14, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("Floor Tag");
        panel6.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addNewFloorButton = new JButton();
        addNewFloorButton.setEnabled(true);
        Font addNewFloorButtonFont = this.$$$getFont$$$(null, Font.BOLD, 14, addNewFloorButton.getFont());
        if (addNewFloorButtonFont != null) addNewFloorButton.setFont(addNewFloorButtonFont);
        addNewFloorButton.setHorizontalAlignment(0);
        addNewFloorButton.setHorizontalTextPosition(2);
        addNewFloorButton.setIcon(new ImageIcon(getClass().getResource("/images/Plus_icon_small.png")));
        this.$$$loadButtonText$$$(addNewFloorButton, ResourceBundle.getBundle("strings").getString("add_floor"));
        addNewFloorButton.setToolTipText(ResourceBundle.getBundle("strings").getString("add_floor"));
        panel5.add(addNewFloorButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel7, new GridConstraints(0, 2, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tabbedPane1 = new JTabbedPane();
        Font tabbedPane1Font = this.$$$getFont$$$(null, Font.BOLD, 14, tabbedPane1.getFont());
        if (tabbedPane1Font != null) tabbedPane1.setFont(tabbedPane1Font);
        panel7.add(tabbedPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        Font panel8Font = this.$$$getFont$$$(null, -1, -1, panel8.getFont());
        if (panel8Font != null) panel8.setFont(panel8Font);
        tabbedPane1.addTab("Floors", panel8);
        final JScrollPane scrollPane1 = new JScrollPane();
        panel8.add(scrollPane1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        scrollPane1.setViewportView(floorsList);
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 5), -1, -1));
        panel8.add(panel9, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        changeFloorButton = new JButton();
        changeFloorButton.setIcon(new ImageIcon(getClass().getResource("/images/edit_icon.png")));
        changeFloorButton.setText("");
        changeFloorButton.setToolTipText(ResourceBundle.getBundle("strings").getString("change_floor_list"));
        panel9.add(changeFloorButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        removeFloorButton = new JButton();
        removeFloorButton.setIcon(new ImageIcon(getClass().getResource("/images/remove_icon.gif")));
        removeFloorButton.setText("");
        removeFloorButton.setToolTipText(ResourceBundle.getBundle("strings").getString("remove_floor_list"));
        panel9.add(removeFloorButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tabbedPane2 = new JTabbedPane();
        Font tabbedPane2Font = this.$$$getFont$$$(null, Font.BOLD, 14, tabbedPane2.getFont());
        if (tabbedPane2Font != null) tabbedPane2.setFont(tabbedPane2Font);
        contentPane.add(tabbedPane2, new GridConstraints(0, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane2.addTab(ResourceBundle.getBundle("strings").getString("floor_with_no_map"), panel10);
        final JScrollPane scrollPane2 = new JScrollPane();
        panel10.add(scrollPane2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane2.setViewportView(floorsWithoutMap);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    private void $$$loadLabelText$$$(JLabel component, String text) {
        StringBuffer result = new StringBuffer();
        boolean haveMnemonic = false;
        char mnemonic = '\0';
        int mnemonicIndex = -1;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '&') {
                i++;
                if (i == text.length()) break;
                if (!haveMnemonic && text.charAt(i) != '&') {
                    haveMnemonic = true;
                    mnemonic = text.charAt(i);
                    mnemonicIndex = result.length();
                }
            }
            result.append(text.charAt(i));
        }
        component.setText(result.toString());
        if (haveMnemonic) {
            component.setDisplayedMnemonic(mnemonic);
            component.setDisplayedMnemonicIndex(mnemonicIndex);
        }
    }

    /**
     * @noinspection ALL
     */
    private void $$$loadButtonText$$$(AbstractButton component, String text) {
        StringBuffer result = new StringBuffer();
        boolean haveMnemonic = false;
        char mnemonic = '\0';
        int mnemonicIndex = -1;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '&') {
                i++;
                if (i == text.length()) break;
                if (!haveMnemonic && text.charAt(i) != '&') {
                    haveMnemonic = true;
                    mnemonic = text.charAt(i);
                    mnemonicIndex = result.length();
                }
            }
            result.append(text.charAt(i));
        }
        component.setText(result.toString());
        if (haveMnemonic) {
            component.setMnemonic(mnemonic);
            component.setDisplayedMnemonicIndex(mnemonicIndex);
        }
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
