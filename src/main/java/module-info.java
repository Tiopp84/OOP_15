/*
 * File này khai báo module "bookingapp".
 * Nó nằm ở: src/main/java/bookingapp/
 */
module bookingapp {
    // Yêu cầu các module JavaFX (giữ nguyên)
    requires javafx.controls;
    requires javafx.fxml;

    // THAY ĐỔI 1: Yêu cầu module java.sql
    // Cần thiết để Java có thể kết nối với CSDL (SQLite)
    requires java.sql;

    // Mở package controller (giữ nguyên)
    opens bookingapp.controller to javafx.fxml;

    // Mở package chính (giữ nguyên)
    opens bookingapp to javafx.fxml;

    // THAY ĐỔI 2: Mở các package MỚI
    // Cần thiết để FXML hoặc các thư viện khác có thể truy cập
    opens bookingapp.model to javafx.fxml;
    opens bookingapp.db to javafx.fxml;
    opens bookingapp.util to javafx.fxml;
    exports bookingapp;
}

