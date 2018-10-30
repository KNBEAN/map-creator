package gui;

import com.google.gson.reflect.TypeToken;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import database.JsonParser;
import database.collections.*;
import database.implementations.*;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindow extends JFrame {
    private JPanel mainPanel;
    private JButton addNode;
    private JButton addLocation;
    private JButton addEdge;
    private JCheckBox allCheckBox;
    private JCheckBox nodesCheckBox;
    private JCheckBox locationCheckBox;
    private JCheckBox edgesCheckBox;
    private JTabbedPane tabbedPane1;
    private JPanel addButtonsPanel;
    private JPanel checkBoxesPanel;
    private JPanel mapPanel;
    private JPanel dataPanel;
    private JLabel mapContainer;
    private JList nodesList;
    private JList locationsList;
    private JList edgesList;

    private EdgeArray edgeCollection = new EdgeArray();
    private NodeArray nodeCollection = new NodeArray();
    private FloorArray floorCollection = new FloorArray();
    private LocationArray locationCollection = new LocationArray();
    private Location_TagArray locationTagCollection = new Location_TagArray();


    //DefaultListModel has to accept entity model objects example: DefaultListModel<NodeEntity>
    private DefaultListModel<Node> nodesListModel;
    private DefaultListModel<Location> locationsListModel;
    private DefaultListModel<Edge> edgesListModel;

    public MainWindow(String title) {
        super(title);
        addMenuBar();
        $$$setupUI$$$();
        getContentPane().add(mainPanel);

        nodesList.addListSelectionListener(e -> {
            /*int i = nodesList.getSelectedIndex();
            System.out.println(nodesListModel.getElementAt(i).getX());*/
        });
    }

    private void addMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu(ResourceBundle.getBundle("strings").getString("file"));
        JMenuItem openItem = new JMenuItem(ResourceBundle.getBundle("strings").getString("open"));
        openItem.addActionListener(e -> {

            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = chooser.showOpenDialog(getParent());

            if (result == JFileChooser.APPROVE_OPTION) {
                String folderPath = chooser.getSelectedFile().getPath();

                loadJsonsFromFolder(folderPath);
                loadCollectionsToListModel();

            } else
                System.out.println("Open command cancelled by user");

        });
        fileMenu.add(openItem);

        JMenu editMenu = new JMenu(ResourceBundle.getBundle("strings").getString("edit"));
        JMenu layersMenu = new JMenu(ResourceBundle.getBundle("strings").getString("layers"));

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(layersMenu);

        setJMenuBar(menuBar);
    }

    private void loadCollectionsToListModel() {

        for (int i = 0; i < nodeCollection.getArrayList().size(); i++) {
            nodesListModel.add(i, nodeCollection.getArrayList().get(i));
        }

        for (int i = 0; i < edgeCollection.getArrayList().size(); i++) {
            edgesListModel.add(i, edgeCollection.getArrayList().get(i));
        }

        for (int i = 0; i < locationCollection.getArrayList().size(); i++) {
            locationsListModel.add(i, locationCollection.getArrayList().get(i));
        }

    }

    private void loadJsonsFromFolder(String folderPath) {

        String edgePath = folderPath + "\\edgeList.json";
        String nodePath = folderPath + "\\nodeList.json";
        String floorPath = folderPath + "\\floorList.json";
        String locationPath = folderPath + "\\locationList.json";
        String tagPath = folderPath + "\\tagList.json";

        try {
            ArrayList<Edge> edges = JsonParser.getEntityArrayList(edgePath, new TypeToken<List<Edge>>() {
            }.getType());
            edgeCollection = (EdgeArray) addArrayToCollection(edges, edgeCollection);

            ArrayList<Node> nodes = JsonParser.getEntityArrayList(nodePath, new TypeToken<List<Node>>() {
            }.getType());
            nodeCollection = (NodeArray) addArrayToCollection(nodes, nodeCollection);

            ArrayList<Floor> floors = JsonParser.getEntityArrayList(floorPath, new TypeToken<List<Floor>>() {
            }.getType());
            floorCollection = (FloorArray) addArrayToCollection(floors, floorCollection);

            ArrayList<Location> locations = JsonParser.getEntityArrayList(locationPath, new TypeToken<List<Location>>() {
            }.getType());
            locationCollection = (LocationArray) addArrayToCollection(locations, locationCollection);

            ArrayList<Location_Tag> location_tags = JsonParser.getEntityArrayList(tagPath, new TypeToken<List<Location_Tag>>() {
            }.getType());
            locationTagCollection = (Location_TagArray) addArrayToCollection(location_tags, locationTagCollection);

        } catch (FileNotFoundException e) {

            JOptionPane.showMessageDialog(getParent(),
                    "These folder hasn't got files from database or some files are missed.\n" +
                            "Load all files or check typo",
                    ResourceBundle.getBundle("strings").getString("error"),
                    JOptionPane.ERROR_MESSAGE);
        }

        JOptionPane.showMessageDialog(getParent(), "Jsons successfully loaded!");



        /*ArrayList<Floor> kek = floorCollection.getArrayList();

        int i = 0;
        for (Floor ed : floors) {
            System.out.println(ed.getFloors());
            i++;
        }

        System.out.println("Ilosc pięter: " + i);*/
    }

    private <T> HashSet<?> addArrayToCollection(ArrayList<T> entityArrayList, HashSet<Object> entityCollection) {

        for (T obj : entityArrayList)
            entityCollection.add(obj);

        return entityCollection;
    }


    private void createUIComponents() {

        nodesListModel = new DefaultListModel();
        locationsListModel = new DefaultListModel();
        edgesListModel = new DefaultListModel();

        nodesList = new JList(nodesListModel);
        nodesList.setCellRenderer(new JTextListRenderer());

        locationsList = new JList(locationsListModel);
        locationsList.setCellRenderer(new JTextListRenderer());

        edgesList = new JList(edgesListModel);
        edgesList.setCellRenderer(new JTextListRenderer());

        /*Example implementation:
         (Entity model class must have Override .toString method)
        nodesListModel.addElement(new NodeEntity(180,0,123,321,-1));*/

        nodesList.setModel(nodesListModel);

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
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        dataPanel = new JPanel();
        dataPanel.setLayout(new GridLayoutManager(1, 1, new Insets(10, 0, 10, 10), -1, -1, true, false));
        mainPanel.add(dataPanel, new GridConstraints(0, 1, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, 1, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tabbedPane1 = new JTabbedPane();
        Font tabbedPane1Font = this.$$$getFont$$$(null, Font.BOLD, 14, tabbedPane1.getFont());
        if (tabbedPane1Font != null) tabbedPane1.setFont(tabbedPane1Font);
        tabbedPane1.setTabLayoutPolicy(0);
        tabbedPane1.setTabPlacement(1);
        dataPanel.add(tabbedPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, 1, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab(ResourceBundle.getBundle("strings").getString("nodes"), panel1);
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane1.setViewportView(nodesList);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab(ResourceBundle.getBundle("strings").getString("locations"), panel2);
        final JScrollPane scrollPane2 = new JScrollPane();
        panel2.add(scrollPane2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, 1, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane2.setViewportView(locationsList);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab(ResourceBundle.getBundle("strings").getString("edges"), panel3);
        final JScrollPane scrollPane3 = new JScrollPane();
        panel3.add(scrollPane3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, 1, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane3.setViewportView(edgesList);
        mapPanel = new JPanel();
        mapPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 10, 0, 0), -1, -1));
        mainPanel.add(mapPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane4 = new JScrollPane();
        mapPanel.add(scrollPane4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        mapContainer = new JLabel();
        mapContainer.setHorizontalAlignment(0);
        mapContainer.setIcon(new ImageIcon(getClass().getResource("/images/samplemap.png")));
        mapContainer.setText("");
        scrollPane4.setViewportView(mapContainer);
        addButtonsPanel = new JPanel();
        addButtonsPanel.setLayout(new GridLayoutManager(1, 3, new Insets(10, 10, 10, 0), -1, -1));
        mainPanel.add(addButtonsPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addNode = new JButton();
        Font addNodeFont = this.$$$getFont$$$(null, Font.BOLD, 14, addNode.getFont());
        if (addNodeFont != null) addNode.setFont(addNodeFont);
        addNode.setIcon(new ImageIcon(getClass().getResource("/images/Plus_icon_small.png")));
        this.$$$loadButtonText$$$(addNode, ResourceBundle.getBundle("strings").getString("node"));
        addButtonsPanel.add(addNode, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addLocation = new JButton();
        Font addLocationFont = this.$$$getFont$$$(null, Font.BOLD, 14, addLocation.getFont());
        if (addLocationFont != null) addLocation.setFont(addLocationFont);
        addLocation.setIcon(new ImageIcon(getClass().getResource("/images/Plus_icon_small.png")));
        this.$$$loadButtonText$$$(addLocation, ResourceBundle.getBundle("strings").getString("location"));
        addButtonsPanel.add(addLocation, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addEdge = new JButton();
        Font addEdgeFont = this.$$$getFont$$$(null, Font.BOLD, 14, addEdge.getFont());
        if (addEdgeFont != null) addEdge.setFont(addEdgeFont);
        addEdge.setIcon(new ImageIcon(getClass().getResource("/images/Plus_icon_small.png")));
        this.$$$loadButtonText$$$(addEdge, ResourceBundle.getBundle("strings").getString("edge"));
        addButtonsPanel.add(addEdge, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkBoxesPanel = new JPanel();
        checkBoxesPanel.setLayout(new GridLayoutManager(1, 4, new Insets(0, 10, 10, 0), -1, -1));
        Font checkBoxesPanelFont = this.$$$getFont$$$(null, -1, -1, checkBoxesPanel.getFont());
        if (checkBoxesPanelFont != null) checkBoxesPanel.setFont(checkBoxesPanelFont);
        mainPanel.add(checkBoxesPanel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        allCheckBox = new JCheckBox();
        Font allCheckBoxFont = this.$$$getFont$$$(null, -1, 20, allCheckBox.getFont());
        if (allCheckBoxFont != null) allCheckBox.setFont(allCheckBoxFont);
        this.$$$loadButtonText$$$(allCheckBox, ResourceBundle.getBundle("strings").getString("all"));
        checkBoxesPanel.add(allCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nodesCheckBox = new JCheckBox();
        Font nodesCheckBoxFont = this.$$$getFont$$$(null, -1, 20, nodesCheckBox.getFont());
        if (nodesCheckBoxFont != null) nodesCheckBox.setFont(nodesCheckBoxFont);
        this.$$$loadButtonText$$$(nodesCheckBox, ResourceBundle.getBundle("strings").getString("nodes"));
        checkBoxesPanel.add(nodesCheckBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        locationCheckBox = new JCheckBox();
        Font locationCheckBoxFont = this.$$$getFont$$$(null, -1, 20, locationCheckBox.getFont());
        if (locationCheckBoxFont != null) locationCheckBox.setFont(locationCheckBoxFont);
        this.$$$loadButtonText$$$(locationCheckBox, ResourceBundle.getBundle("strings").getString("locations"));
        checkBoxesPanel.add(locationCheckBox, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        edgesCheckBox = new JCheckBox();
        Font edgesCheckBoxFont = this.$$$getFont$$$(null, -1, 20, edgesCheckBox.getFont());
        if (edgesCheckBoxFont != null) edgesCheckBox.setFont(edgesCheckBoxFont);
        this.$$$loadButtonText$$$(edgesCheckBox, ResourceBundle.getBundle("strings").getString("edges"));
        checkBoxesPanel.add(edgesCheckBox, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        return mainPanel;
    }
}
