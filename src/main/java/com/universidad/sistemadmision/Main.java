package com.universidad.sistemadmision;

import com.universidad.sistemadmision.controller.SplashController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Cargar la pantalla splash
            FXMLLoader splashLoader = new FXMLLoader(getClass().getResource("/fxml/SplashView.fxml"));
            Parent splashRoot = splashLoader.load();
            
            // Configurar el controlador del splash
            SplashController splashController = splashLoader.getController();
            splashController.setSplashStage(primaryStage);
            
            // Set application icon
            primaryStage.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/images/logo.png")));
            
            // Configurar y mostrar la pantalla splash
            Scene splashScene = new Scene(splashRoot);
            splashScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
            
            primaryStage.setTitle("Sistema de Admisión Universitaria");
            primaryStage.setScene(splashScene);
            primaryStage.initStyle(StageStyle.DECORATED);
            primaryStage.centerOnScreen();
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}