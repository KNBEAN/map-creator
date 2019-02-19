package gui;

import com.google.gson.reflect.TypeToken;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import data.JsonParser;
import data.dao.*;
import data.dao.interfaces.*;
import data.database.DatabaseManager;
import data.implementations.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindow extends JFrame {
    private JPanel mainPanel;
    private JButton addNode;
    private JButton addLocation;
    private JButton addEdge;
    private JCheckBox nodesCheckBox;
    private JCheckBox locationCheckBox;
    private JCheckBox edgesCheckBox;
    private JTabbedPane tabbedPane1;
    private JPanel addButtonsPanel;
    private JPanel checkBoxesPanel;
    private JPanel mapPanel;
    private JPanel dataPanel;
    private JList nodesList;
    private JList locationsList;
    private JList edgesList;
    private JPanel mapPaintPanel;
    private JSlider scaleSlider;
    private JLabel scaleSliderValue;
    private JButton scaleMinusButton;
    private JButton scalePlusButton;
    private JScrollPane scrollMap;
    private JPanel scalePanel;
    private JComboBox floorBox;
    private JPanel selectFloorPanel;
    private AddFloorWindow addFloorWindow;

    private DefaultListModel<Node> nodesListModel;
    private DefaultListModel<Location> locationsListModel;
    private DefaultListModel<Edge> edgesListModel;

    private EdgeDAO edgeDAO;
    private NodeDAO nodeDAO;
    private FloorDAO floorDAO;
    private LocationDAO locationDAO;
    private Location_TagDAO location_tagDAO;
    private Quick_Access_LocationDAO quick_access_locationDAO;

    private Floor selectedFloor;
    private DefaultComboBoxModel<Floor> floorComboBoxModel;

    public MainWindow(String title) {
        super(title);
        addMenuBar();
        setUpDatabase();
        $$$setupUI$$$();
        getContentPane().add(mainPanel);
        addListeners();
    }

    private void setUpDatabase() {
        DatabaseManager.createNewDatabase(null, false);
        DatabaseManager.createTables();
        floorDAO = new FloorDAOImp();
        edgeDAO = new EdgeDAOImp();
        nodeDAO = new NodeDAOImp();
        locationDAO = new LocationDAOImp();
        location_tagDAO = new Location_TagDAOImp();
        quick_access_locationDAO = new Quick_Access_LocationDAOImp();
    }

    private void addListeners() {
        scaleSlider.addChangeListener(e -> {
            scaleSliderValue.setText(String.valueOf(scaleSlider.getValue()) + "%");
            ImageIcon icon = ((PaintPanel) mapPaintPanel).getImageIcon();
            ((PaintPanel) mapPaintPanel).resizeImage(icon, scaleSlider.getValue());
            mapPaintPanel.repaint();
            mapPaintPanel.revalidate();
        });

        scalePlusButton.addActionListener(e -> {
            scaleSlider.setValue(scaleSlider.getValue() + 1);
        });

        scaleMinusButton.addActionListener(e -> {
            scaleSlider.setValue(scaleSlider.getValue() - 1);

        });

        mapPaintPanel.addMouseWheelListener(mouseEvent -> {
            if (mouseEvent.isControlDown() && mouseEvent.getWheelRotation() == -1) {
                scaleSlider.setValue(scaleSlider.getValue() + 5);
            } else if (mouseEvent.isControlDown() && mouseEvent.getWheelRotation() == 1) {
                scaleSlider.setValue(scaleSlider.getValue() - 5);
            }
        });

        MouseAdapter dragAdapter = getMouseDragAdapter(mapPaintPanel);

        mapPaintPanel.addMouseListener(dragAdapter);
        mapPaintPanel.addMouseMotionListener(dragAdapter);

        floorBox.addActionListener(e -> {

            selectedFloor = ((Floor) (floorComboBoxModel.getSelectedItem()));
            ImageIcon floorMap = new ImageIcon(selectedFloor.getImagePath());
            ((PaintPanel) mapPaintPanel).setImageIcon(floorMap);
            ((PaintPanel) mapPaintPanel).resizeImage(floorMap, scaleSlider.getValue());
            prepareGraphDataToPaint();
            mapPaintPanel.repaint();

        });

        nodesCheckBox.addItemListener(e -> {
            prepareGraphDataToPaint();
            mapPaintPanel.repaint();

        });

        locationCheckBox.addItemListener(e -> {
            prepareGraphDataToPaint();
            mapPaintPanel.repaint();

        });
        edgesCheckBox.addItemListener(e -> {
            prepareGraphDataToPaint();
            mapPaintPanel.repaint();

        });


    }

    private void prepareGraphDataToPaint() {
        List<Node> nodes = new ArrayList<>();
        List<LocationWithCoordinates> locationWithCoordinates = new ArrayList<>();
        List<EdgeWithCoordinates> edgeWithCoordinates = new ArrayList<>();

        if (nodesCheckBox.isSelected())
            nodes = nodeDAO.getAllNodesOnFloor(selectedFloor.getFloors());
        if (locationCheckBox.isSelected()) {
            List<Location> locations = locationDAO.getAllLocationsOnFloor(selectedFloor.getFloors());

            for (Location l : locations) {
                if (l.getId() >= 1) {
                    for (Node n : nodeDAO.getAllNodesOnFloor(selectedFloor.getFloors())) {
                        if (l.getId() == n.getLocationID())
                            locationWithCoordinates.add(new LocationWithCoordinates(n.getX(), n.getY()));
                    }
                }
            }
        }
        if (edgesCheckBox.isSelected()) {
            List<Edge> edges = edgeDAO.getAllEdgesOnFloor(selectedFloor.getFloors());

            for (Edge e : edges) {
                Node node = nodeDAO.getNode(e.getFrom());
                Node node1 = nodeDAO.getNode(e.getTo());

                edgeWithCoordinates.add(new EdgeWithCoordinates(node.getX(), node.getY(), node1.getX(), node1.getY(), e.getLength()));
            }
        }

        ((PaintPanel) mapPaintPanel).setGraphData(edgeWithCoordinates, nodes, locationWithCoordinates);
    }

    private MouseAdapter getMouseDragAdapter(JComponent component) {

        MouseAdapter mouseAdapter = new MouseAdapter() {

            Point point;

            @Override
            public void mousePressed(MouseEvent e) {
                point = new Point(e.getPoint());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (point != null) {
                    JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, component);
                    if (viewPort != null) {
                        int deltaX = point.x - e.getX();
                        int deltaY = point.y - e.getY();

                        Rectangle view = viewPort.getViewRect();
                        view.x += deltaX;
                        view.y += deltaY;

                        component.scrollRectToVisible(view);
                    }
                }
            }
        };
        return mouseAdapter;
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
                loadDaoToListModel();

                if (selectedFloor != null) {
                    nodesCheckBox.setSelected(true);
                    edgesCheckBox.setSelected(true);
                    locationCheckBox.setSelected(true);
                }
            } else
                System.out.println("Open command cancelled by user");

        });
        fileMenu.add(openItem);

        JMenu editMenu = new JMenu(ResourceBundle.getBundle("strings").getString("edit"));
        JMenu layersMenu = new JMenu(ResourceBundle.getBundle("strings").getString("layers"));
        JMenuItem showFloors = new JMenuItem(ResourceBundle.getBundle("strings").getString("show_floors"));
        showFloors.addActionListener(e -> {
            addFloorWindow = new AddFloorWindow(ResourceBundle.getBundle("strings").getString("floors_manager"));
            addFloorWindow.setVisible(true);
            addFloorWindow.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    updateFloors();
                    System.out.println("Add window closed");
                }
            });

        });

        layersMenu.add(showFloors);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(layersMenu);

        setJMenuBar(menuBar);
    }

    private void loadDaoToListModel() {

        for (Edge e : edgeDAO.getAllEdges())
            edgesListModel.addElement(e);

        for (Node n : nodeDAO.getAllNodes())
            nodesListModel.addElement(n);

        for (Location l : locationDAO.getAllLocations())
            locationsListModel.addElement(l);
    }


    private void loadJsonsFromFolder(String folderPath) {
        String edgePath = folderPath + ResourceBundle.getBundle("strings").getString("json_edge");
        String nodePath = folderPath + ResourceBundle.getBundle("strings").getString("json_node");
        String floorPath = folderPath + ResourceBundle.getBundle("strings").getString("json_floor");
        String locationPath = folderPath + ResourceBundle.getBundle("strings").getString("json_location");
        String tagPath = folderPath + ResourceBundle.getBundle("strings").getString("json_tag");
        String quickAccessPath = folderPath + ResourceBundle.getBundle("strings").getString("json_quick_access");

        try {

            ArrayList<Floor> floorArray = JsonParser.getEntityArrayList(floorPath, new TypeToken<List<Floor>>() {
            }.getType());
            System.out.println("Floor DAO");

            int floorDAOsize = floorDAO.getAllFloors().size();
            if (floorDAOsize < floorArray.size()) {
                for (Floor f : floorArray)
                    floorDAO.insert(f);
            }

            try {
                locationDAO.getLocation(-1).getName().equals("none");
            } catch (NullPointerException ex) {
                locationDAO.insert(new Location(-1, "none", null)); //For nodes without location_id
            }

            int locationDAOsize = locationDAO.getAllLocations().size();
            ArrayList<Location> locationsArray = JsonParser.getEntityArrayList(locationPath, new TypeToken<List<Location>>() {
            }.getType());
            System.out.println("Location DAO");
            if (locationDAOsize < locationsArray.size())
                locationDAO.insert(locationsArray);

            int nodeDAOsize = nodeDAO.getAllNodes().size();
            ArrayList<Node> nodeArray = JsonParser.getEntityArrayList(nodePath, new TypeToken<List<Node>>() {
            }.getType());
            System.out.println("Node DAO");
            if (nodeDAOsize < nodeArray.size())
                nodeDAO.insert(nodeArray);

            int locationTagDAOsize = location_tagDAO.getAllLocations_Tag().size();
            ArrayList<Location_Tag> locationTagArray = JsonParser.getEntityArrayList(tagPath, new TypeToken<List<Location_Tag>>() {
            }.getType());
            System.out.println("LocationTAG DAO");
            if (locationTagDAOsize < locationTagArray.size())
                location_tagDAO.insert(locationTagArray);

            int edgeDAOsize = edgeDAO.getAllEdges().size();
            ArrayList<Edge> edgeArray = JsonParser.getEntityArrayList(edgePath, new TypeToken<List<Edge>>() {
            }.getType());
            System.out.println("Edge DAO");
            if (edgeDAOsize < edgeArray.size())
                edgeDAO.insert(edgeArray);

            int quickAccesDAOsize = quick_access_locationDAO.getAllQuick_Access_Locations().size();
            ArrayList<Quick_Access_Location> quickAccessLocationArray = JsonParser.getEntityArrayList(quickAccessPath, new TypeToken<List<Quick_Access_Location>>() {
            }.getType());
            System.out.println("Quick DAO");
            if (quickAccesDAOsize < quickAccessLocationArray.size())
                quick_access_locationDAO.insert(quickAccessLocationArray);

            updateFloors();

            JOptionPane.showMessageDialog(getParent(), ResourceBundle.getBundle("strings").getString("json_success"));
        } catch (FileNotFoundException e) {

            JOptionPane.showMessageDialog(getParent(),
                    ResourceBundle.getBundle("strings").getString("json_error"),
                    ResourceBundle.getBundle("strings").getString("error"),
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    private void updateFloors() {

        try {
            floorComboBoxModel.removeAllElements();
        } catch (NullPointerException ex) {
        }
        for (Floor f : floorDAO.getAllFloors())
            if (f.getImagePath() != null) {
                floorComboBoxModel.addElement(f);
            }

    }

    private void createUIComponents() {

        nodesListModel = new DefaultListModel();
        locationsListModel = new DefaultListModel();
        edgesListModel = new DefaultListModel();
        floorComboBoxModel = new DefaultComboBoxModel();

        floorBox = new JComboBox(floorComboBoxModel);
        mapPaintPanel = new PaintPanel();

        nodesList = new JList(nodesListModel);
        nodesList.setCellRenderer(new JTextListRenderer());

        locationsList = new JList(locationsListModel);
        locationsList.setCellRenderer(new JTextListRenderer());

        edgesList = new JList(edgesListModel);
        edgesList.setCellRenderer(new JTextListRenderer());

        updateFloors();
        loadDaoToListModel();

        selectedFloor = ((Floor) (floorComboBoxModel.getSelectedItem()));

        ImageIcon startImage;
        try {
            startImage = new ImageIcon(((selectedFloor)).getImagePath());
        } catch (NullPointerException ex) {
            //Load some default map
            startImage = new ImageIcon(getClass().getResource("/images/samplemap.png"));
        }

        ((PaintPanel) mapPaintPanel).setImageIcon(startImage);
        mapPaintPanel.repaint();

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
        mainPanel.setLayout(new GridLayoutManager(3, 4, new Insets(0, 0, 0, 0), -1, -1));
        dataPanel = new JPanel();
        dataPanel.setLayout(new GridLayoutManager(1, 1, new Insets(10, 0, 10, 10), -1, -1));
        mainPanel.add(dataPanel, new GridConstraints(0, 3, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, 1, 1, null, null, null, 0, false));
        tabbedPane1 = new JTabbedPane();
        Font tabbedPane1Font = this.$$$getFont$$$(null, Font.BOLD, 14, tabbedPane1.getFont());
        if (tabbedPane1Font != null) tabbedPane1.setFont(tabbedPane1Font);
        tabbedPane1.setTabLayoutPolicy(0);
        tabbedPane1.setTabPlacement(1);
        dataPanel.add(tabbedPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(200, -1), new Dimension(200, 200), null, 0, false));
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
        mainPanel.add(mapPanel, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(800, -1), new Dimension(800, -1), null, 0, false));
        scrollMap = new JScrollPane();
        scrollMap.setHorizontalScrollBarPolicy(32);
        scrollMap.setVerticalScrollBarPolicy(22);
        mapPanel.add(scrollMap, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        scrollMap.setViewportView(mapPaintPanel);
        addButtonsPanel = new JPanel();
        addButtonsPanel.setLayout(new GridLayoutManager(1, 3, new Insets(10, 10, 10, 0), -1, -1));
        mainPanel.add(addButtonsPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addNode = new JButton();
        Font addNodeFont = this.$$$getFont$$$(null, Font.BOLD, 14, addNode.getFont());
        if (addNodeFont != null) addNode.setFont(addNodeFont);
        addNode.setIcon(new ImageIcon(getClass().getResource("/images/Plus_icon_small.png")));
        this.$$$loadButtonText$$$(addNode, ResourceBundle.getBundle("strings").getString("Node"));
        addButtonsPanel.add(addNode, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addLocation = new JButton();
        Font addLocationFont = this.$$$getFont$$$(null, Font.BOLD, 14, addLocation.getFont());
        if (addLocationFont != null) addLocation.setFont(addLocationFont);
        addLocation.setIcon(new ImageIcon(getClass().getResource("/images/Plus_icon_small.png")));
        this.$$$loadButtonText$$$(addLocation, ResourceBundle.getBundle("strings").getString("Location"));
        addButtonsPanel.add(addLocation, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addEdge = new JButton();
        Font addEdgeFont = this.$$$getFont$$$(null, Font.BOLD, 14, addEdge.getFont());
        if (addEdgeFont != null) addEdge.setFont(addEdgeFont);
        addEdge.setIcon(new ImageIcon(getClass().getResource("/images/Plus_icon_small.png")));
        this.$$$loadButtonText$$$(addEdge, ResourceBundle.getBundle("strings").getString("Edge"));
        addButtonsPanel.add(addEdge, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkBoxesPanel = new JPanel();
        checkBoxesPanel.setLayout(new GridLayoutManager(1, 3, new Insets(0, 10, 10, 0), -1, -1));
        Font checkBoxesPanelFont = this.$$$getFont$$$(null, -1, -1, checkBoxesPanel.getFont());
        if (checkBoxesPanelFont != null) checkBoxesPanel.setFont(checkBoxesPanelFont);
        mainPanel.add(checkBoxesPanel, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nodesCheckBox = new JCheckBox();
        Font nodesCheckBoxFont = this.$$$getFont$$$(null, -1, 20, nodesCheckBox.getFont());
        if (nodesCheckBoxFont != null) nodesCheckBox.setFont(nodesCheckBoxFont);
        this.$$$loadButtonText$$$(nodesCheckBox, ResourceBundle.getBundle("strings").getString("nodes"));
        checkBoxesPanel.add(nodesCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        locationCheckBox = new JCheckBox();
        Font locationCheckBoxFont = this.$$$getFont$$$(null, -1, 20, locationCheckBox.getFont());
        if (locationCheckBoxFont != null) locationCheckBox.setFont(locationCheckBoxFont);
        this.$$$loadButtonText$$$(locationCheckBox, ResourceBundle.getBundle("strings").getString("locations"));
        checkBoxesPanel.add(locationCheckBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        edgesCheckBox = new JCheckBox();
        Font edgesCheckBoxFont = this.$$$getFont$$$(null, -1, 20, edgesCheckBox.getFont());
        if (edgesCheckBoxFont != null) edgesCheckBox.setFont(edgesCheckBoxFont);
        this.$$$loadButtonText$$$(edgesCheckBox, ResourceBundle.getBundle("strings").getString("edges"));
        checkBoxesPanel.add(edgesCheckBox, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scalePanel = new JPanel();
        scalePanel.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(scalePanel, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        scaleSlider = new JSlider();
        scaleSlider.setMaximum(250);
        scaleSlider.setMinimum(40);
        scaleSlider.setMinorTickSpacing(0);
        scaleSlider.setPaintLabels(true);
        scaleSlider.setPaintTicks(false);
        scaleSlider.setPaintTrack(true);
        scaleSlider.setSnapToTicks(false);
        scaleSlider.setValue(100);
        scaleSlider.setValueIsAdjusting(false);
        scalePanel.add(scaleSlider, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scaleSliderValue = new JLabel();
        Font scaleSliderValueFont = this.$$$getFont$$$(null, Font.BOLD, 14, scaleSliderValue.getFont());
        if (scaleSliderValueFont != null) scaleSliderValue.setFont(scaleSliderValueFont);
        scaleSliderValue.setText("100%");
        scalePanel.add(scaleSliderValue, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scaleMinusButton = new JButton();
        Font scaleMinusButtonFont = this.$$$getFont$$$(null, Font.BOLD, 18, scaleMinusButton.getFont());
        if (scaleMinusButtonFont != null) scaleMinusButton.setFont(scaleMinusButtonFont);
        scaleMinusButton.setHideActionText(false);
        scaleMinusButton.setHorizontalAlignment(0);
        scaleMinusButton.setHorizontalTextPosition(0);
        scaleMinusButton.setText("-");
        scalePanel.add(scaleMinusButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(10, 15), null, 0, false));
        scalePlusButton = new JButton();
        Font scalePlusButtonFont = this.$$$getFont$$$(null, Font.BOLD, 16, scalePlusButton.getFont());
        if (scalePlusButtonFont != null) scalePlusButton.setFont(scalePlusButtonFont);
        scalePlusButton.setHideActionText(false);
        scalePlusButton.setHorizontalAlignment(0);
        scalePlusButton.setHorizontalTextPosition(0);
        scalePlusButton.setText("+");
        scalePlusButton.setVerticalAlignment(1);
        scalePlusButton.setVerticalTextPosition(1);
        scalePanel.add(scalePlusButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(5, 15), null, 0, false));
        selectFloorPanel = new JPanel();
        selectFloorPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(selectFloorPanel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        Font floorBoxFont = this.$$$getFont$$$(null, Font.BOLD, 14, floorBox.getFont());
        if (floorBoxFont != null) floorBox.setFont(floorBoxFont);
        selectFloorPanel.add(floorBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, Font.BOLD, 14, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        this.$$$loadLabelText$$$(label1, ResourceBundle.getBundle("strings").getString("Floor"));
        selectFloorPanel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        return mainPanel;
    }
}
