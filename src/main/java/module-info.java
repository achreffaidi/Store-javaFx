module store {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.management;
    requires jHardware;
    requires org.controlsfx.controls;

    opens store to javafx.fxml, javafx.base, org.controlsfx.controls,javafx.reflect;

    exports store;
}