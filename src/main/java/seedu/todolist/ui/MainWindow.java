package seedu.todolist.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.todolist.commons.core.Config;
import seedu.todolist.commons.core.GuiSettings;
import seedu.todolist.commons.events.ui.ExitAppRequestEvent;
import seedu.todolist.logic.Logic;
import seedu.todolist.model.UserPrefs;
import seedu.todolist.model.task.ReadOnlyTask;
import seedu.todolist.model.task.Status;
import seedu.todolist.storage.Storage;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart {

    private static final String ICON = "/images/taskit_logo.png";
    private static final String FXML = "MainWindow.fxml";
    public static final int MIN_HEIGHT = 600;
    public static final int MIN_WIDTH = 450;
    public static final String TAB_TASK_COMPLETE = "Completed";
    public static final String TAB_TASK_INCOMPLETE = "Incomplete";
    public static final String TAB_TASK_OVERDUE = "Overdue";

    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private TaskListPanel incompleteListPanel;
    private TaskListPanel completeTaskListPanel;
    private TaskListPanel overdueTaskListPanel;
    private ResultDisplay resultDisplay;
    private StatusBarFooter statusBarFooter;
    private CommandBox commandBox;
    private Config config;
    private UserPrefs userPrefs;
    private Storage storage;

    // Handles to elements of this Ui container
    private VBox rootLayout;
    private Scene scene;

    private String ToDoListName;

    @FXML
    private AnchorPane browserPlaceholder;

    @FXML
    private AnchorPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private AnchorPane taskListPanelPlaceholder;
    
    @FXML
    private AnchorPane completeTaskListPanelPlaceholder;
    
    @FXML
    private AnchorPane overdueTaskListPanelPlaceholder;

    @FXML
    private AnchorPane resultDisplayPlaceholder;

    @FXML
    private AnchorPane statusbarPlaceholder;
    
    @FXML
    private TabPane allTasksTabPane;


    public MainWindow() {
        super();
    }

    @Override
    public void setNode(Node node) {
        rootLayout = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    public static MainWindow load(Stage primaryStage, Config config, UserPrefs prefs, Logic logic, Storage storage) {

        MainWindow mainWindow = UiPartLoader.loadUiPart(primaryStage, new MainWindow());
        mainWindow.configure(config.getAppTitle(), config.getToDoListName(), config, prefs, logic, storage);
        return mainWindow;
    }

    private void configure(String appTitle, String ToDoListName, Config config, UserPrefs prefs,
                           Logic logic, Storage storage) {

        //Set dependencies
        this.logic = logic;
        this.ToDoListName = ToDoListName;
        this.config = config;
        this.userPrefs = prefs;
        this.storage = storage;

        //Configure the UI
        setTitle(appTitle);
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        scene = new Scene(rootLayout);
        primaryStage.setScene(scene);

        setAccelerators();
    }

    private void setAccelerators() {
        helpMenuItem.setAccelerator(KeyCombination.valueOf("F1"));
    }

    void fillInnerParts() {
        incompleteListPanel = TaskListPanel.load(primaryStage, getTaskListPlaceholder(),
                logic.getFilteredIncompleteTaskList(), Status.Type.Incomplete);
        completeTaskListPanel = TaskListPanel.load(primaryStage, getCompleteTaskListPlaceholder(),
                logic.getFilteredCompleteTaskList(), Status.Type.Complete);
        overdueTaskListPanel = TaskListPanel.load(primaryStage, getOverdueTaskListPlaceholder(),
                logic.getFilteredOverdueTaskList(), Status.Type.Overdue);
        resultDisplay = ResultDisplay.load(primaryStage, getResultDisplayPlaceholder());
        statusBarFooter = StatusBarFooter.load(primaryStage, getStatusbarPlaceholder(), config.getToDoListFilePath());
        commandBox = CommandBox.load(primaryStage, getCommandBoxPlaceholder(), resultDisplay, logic, storage, statusBarFooter);
    }

    private AnchorPane getCommandBoxPlaceholder() {
        return commandBoxPlaceholder;
    }

    private AnchorPane getStatusbarPlaceholder() {
        return statusbarPlaceholder;
    }

    private AnchorPane getResultDisplayPlaceholder() {
        return resultDisplayPlaceholder;
    }

    public AnchorPane getTaskListPlaceholder() {
        return taskListPanelPlaceholder;
    }
    
    public AnchorPane getCompleteTaskListPlaceholder() {
        return completeTaskListPanelPlaceholder;
    }
    
    public AnchorPane getOverdueTaskListPlaceholder() {
        return overdueTaskListPanelPlaceholder;
    }

    public void hide() {
        primaryStage.hide();
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the default size based on user preferences.
     */
    protected void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    private void setWindowMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    public GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
    }

    @FXML
    public void handleHelp() {
        HelpWindow helpWindow = HelpWindow.load(primaryStage);
        helpWindow.show();
    }

    public void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }
    
    @FXML
    public void OnTabSelectionChanged() {
        try {
            logic.setCurrentTab(allTasksTabPane.getSelectionModel().getSelectedItem().getText());
        } catch (NullPointerException ex) {
            //Default tab is incomplete tab
        }
    }

    public TaskListPanel getTaskListPanel(String currentTab) {
        
        switch (currentTab) {
        
        case TAB_TASK_COMPLETE :
            return this.completeTaskListPanel;
        
        case TAB_TASK_INCOMPLETE :
            return this.incompleteListPanel;
            
        case TAB_TASK_OVERDUE :
            return this.overdueTaskListPanel;
            
        default :
            assert false : "Tab panel " + currentTab + " does not exist";
            return null;
            
        }
    }

    public void loadTaskPage(ReadOnlyTask task) {
    }

    public void releaseResources() {
    }
}
