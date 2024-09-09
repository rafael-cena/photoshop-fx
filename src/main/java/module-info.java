module com.example.fotoxopfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;


    opens com.example.fotoxopfx to javafx.fxml;
    exports com.example.fotoxopfx;
}