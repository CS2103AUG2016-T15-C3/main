package seedu.todolist.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.todolist.commons.core.LogsCenter;
import seedu.todolist.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.todolist.model.task.ReadOnlyTask;

/*
 * Panel containing notifications
 */

public class NotificationPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(NotificationPanel.class);
    private static final String FXML = "NotificationPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyTask> notificationListView;

    public NotificationPanel() {
        super();
    }

    @Override
    public void setNode(Node node) {
        panel = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    public static NotificationPanel load(Stage primaryStage, AnchorPane notificationPlaceholder,
                                       ObservableList<ReadOnlyTask> taskList) {
        NotificationPanel notificationPanel =
                UiPartLoader.loadUiPart(primaryStage, notificationPlaceholder, new NotificationPanel());
        notificationPanel.configure(taskList);
        return notificationPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> taskList) {
        setConnections(taskList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        notificationListView.setItems(taskList);
        notificationListView.setCellFactory(listView -> new NotificationListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        notificationListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                raise(new TaskPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            notificationListView.scrollTo(index);
            notificationListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class NotificationListViewCell extends ListCell<ReadOnlyTask> {

        public NotificationListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(TaskCard.load(task, getIndex() + 1).getLayout());
            }
        }
    }
}
