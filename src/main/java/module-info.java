module com.example.noteapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.noteapp to javafx.fxml;
    exports com.example.noteapp;
}