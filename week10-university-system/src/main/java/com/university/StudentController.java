package com.university;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class StudentController {

    @FXML private TextField regField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, String> colReg;
    @FXML private TableColumn<Student, String> colFirst;
    @FXML private TableColumn<Student, String> colLast;
    @FXML private Label statusLabel;
    @FXML private Button saveButton;

    private ObservableList<Student> studentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colReg.setCellValueFactory(cellData -> cellData.getValue().regNumberProperty());
        colFirst.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        colLast.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        studentTable.setItems(studentList);

        saveButton.disableProperty().bind(
            regField.textProperty().isEmpty()
            .or(firstNameField.textProperty().isEmpty())
            .or(lastNameField.textProperty().isEmpty())
        );

        loadStudents();
    }

    @FXML
    private void handleSaveStudent() {
        String reg = regField.getText();
        String fName = firstNameField.getText();
        String lName = lastNameField.getText();

        Task<Void> saveTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                String sql = "INSERT INTO students (registration_number, first_name, last_name, email) VALUES (?, ?, ?, ?)";
                try (Connection conn = DatabaseUtil.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, reg);
                    stmt.setString(2, fName);
                    stmt.setString(3, lName);
                    stmt.setString(4, reg.toLowerCase() + "@university.edu");
                    stmt.executeUpdate();
                }
                return null;
            }
        };

        saveTask.setOnSucceeded(e -> {
            statusLabel.setText("Student saved successfully!");
            regField.clear();
            firstNameField.clear();
            lastNameField.clear();
            loadStudents();
        });

        saveTask.setOnFailed(e -> {
            statusLabel.setText("Error saving: " + saveTask.getException().getMessage());
        });

        new Thread(saveTask).start();
    }

    private void loadStudents() {
        LoadStudentsTask task = new LoadStudentsTask();
        task.setOnSucceeded(e -> {
            studentList.setAll(task.getValue());
            statusLabel.setText("Loaded " + studentList.size() + " students from database.");
        });
        task.setOnFailed(e -> {
            statusLabel.setText("Failed to load: " + task.getException().getMessage());
        });
        new Thread(task).start();
    }
}