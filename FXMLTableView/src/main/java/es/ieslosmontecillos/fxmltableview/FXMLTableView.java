package es.ieslosmontecillos.fxmltableview;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.text.Format;
import java.util.Objects;

import static javafx.fxml.FXMLLoader.load;

public class FXMLTableView extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("FXML TableView Example");
        Pane root = (Pane) FXMLLoader.load(getClass().getResource("fxml_tableview.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class Person {
        private final SimpleStringProperty firstName = new SimpleStringProperty("");
        private final SimpleStringProperty lastName = new SimpleStringProperty("");
        private final SimpleStringProperty email = new SimpleStringProperty("");

        public Person() {
            this("", "", "");
        }

        public Person(String firstName, String lastName, String email) {
            this.firstName.set(firstName);
            this.lastName.set(lastName);
            this.email.set(email);
        }

        public String getFirstName() {
            return firstName.get();
        }

        public void setFirstName(String firstName) {
            this.firstName.set(firstName);
        }

        public String getLastName() {
            return lastName.get();
        }

        public void setLastName(String lastName) {
            this.lastName.set(lastName);
        }

        public String getEmail() {
            return email.get();
        }

        public void setEmail(String email) {
            this.email.set(email);
        }
    }

    public class FormattedTableCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
        private TextAlignment alignment;
        private Format format;

        public TextAlignment getAlignment() {
            return alignment;
        }

        public void setAlignment(TextAlignment alignment) {
            this.alignment = alignment;
        }

        public Format getFormat() {
            return format;
        }

        public void setFormat(Format format) {
            this.format = format;
        }

        @Override
        @SuppressWarnings("unchecked")
        public TableCell<S, T> call(TableColumn<S, T> p) {
            TableCell<S, T> cell = new TableCell<S, T>() {
                @Override
                public void updateItem(Object item, boolean empty) {
                    if (item == getItem()) {
                        return;
                    }
                    super.updateItem((T) item, empty);
                    if (item == null) {
                        super.setText(null);
                        super.setGraphic(null);
                    } else if (format != null) {
                        super.setText(format.format(item));
                    } else if (item instanceof Node) {
                        super.setText(null);
                        super.setGraphic((Node) item);
                    } else {
                        super.setText(item.toString());
                        super.setGraphic(null);
                    }
                }
            };
            cell.setTextAlignment(alignment);
            switch (alignment) {
                case CENTER:
                    cell.setAlignment(Pos.CENTER);
                    break;
                case RIGHT:
                    cell.setAlignment(Pos.CENTER_RIGHT);
                    break;
                default:
                    cell.setAlignment(Pos.CENTER_LEFT);
                    break;
            }
            return cell;
        }
    }
}