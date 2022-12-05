package com.example.filemeneger_v2;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PanelController implements Initializable {
    @FXML
    public TableView<FileInfo> filesTable;
    @FXML
    public ComboBox<String> disksBox;
    @FXML
    public TextField pathField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<FileInfo, String> fileTypeColumn = new TableColumn<>("Тип");
        fileTypeColumn.setCellValueFactory(params -> new SimpleStringProperty(params.getValue().getType().getName()));
        fileTypeColumn.setPrefWidth(50);

        TableColumn<FileInfo, String> fileNameColumn = new TableColumn<>("Имя");
        fileNameColumn.setCellValueFactory(params -> new SimpleStringProperty(params.getValue().getFilename()));
        fileNameColumn.setPrefWidth(240);

        TableColumn<FileInfo, Long> fileSizeColumn = new TableColumn<>("Размер");
        fileSizeColumn.setCellValueFactory(params -> new SimpleObjectProperty<>(params.getValue().getSize()));
        fileSizeColumn.setCellFactory(column -> {
            return new TableCell<FileInfo, Long>() {
                @Override
                protected void updateItem(Long aLong, boolean b) {
                    super.updateItem(aLong, b);
                    if(aLong == null || b) {
                        setText(null);
                        setStyle("");
                    } else {
                        String text = String.format("%,d bytes", aLong);
                        if(aLong == -1) {
                            text = "[DIR]";
                        }
                        setText(text);
                    }
                }
            };
        });
        fileSizeColumn.setPrefWidth(120);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        TableColumn<FileInfo, String> dateTimeColumn = new TableColumn<>("Дата и время");
        dateTimeColumn.setCellValueFactory(params -> new SimpleStringProperty(params.getValue().getLastModified().format(dtf)));
        dateTimeColumn.setPrefWidth(120);

        filesTable.getColumns().addAll(fileTypeColumn, fileNameColumn, fileSizeColumn, dateTimeColumn);
        filesTable.getSortOrder().add(fileTypeColumn); //сортировка по типу файлов

        disksBox.getItems().clear();
        for (Path p : FileSystems.getDefault().getRootDirectories()) {
            disksBox.getItems().add(p.toString());
        }
        disksBox.getSelectionModel().select(0);

        updateList(Paths.get(".")); //папка из которой тянется список файлов. Если оставить ".", будут файлы и папки из корня проекта.
    }

    public void updateList(Path path) {
        try {
            pathField.setText(path.normalize().toAbsolutePath().toString());
            filesTable.getItems().clear();
            filesTable.getItems().addAll(Files.list(path).map(FileInfo::new).collect(Collectors.toList()));
            filesTable.sort();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Невозможно обновить список файлов", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void btnPathUpAction(ActionEvent actionEvent) {
        Path upperPath = Paths.get(pathField.getText()).getParent();
        if(upperPath !=null) {
            updateList(upperPath);
        }
    }

    public void selectDiskAction(ActionEvent actionEvent) {
        ComboBox<String> element = (ComboBox<String>) actionEvent.getSource();
        updateList(Paths.get(element.getSelectionModel().getSelectedItem()));
    }
}
