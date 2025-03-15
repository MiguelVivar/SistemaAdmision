package com.universidad.sistemadmision.controller;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SplashController {

    @FXML
    private ProgressBar progressBar;
    
    @FXML
    private Label statusLabel;
    
    private Stage splashStage;
    
    public void setSplashStage(Stage splashStage) {
        this.splashStage = splashStage;
    }
    
    public void initialize() {
        // Configurar la animación de la barra de progreso
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(progressBar.progressProperty(), 0)),
            new KeyFrame(Duration.seconds(0.5), e -> statusLabel.setText("Inicializando componentes..."), new KeyValue(progressBar.progressProperty(), 0.2)),
            new KeyFrame(Duration.seconds(1.0), e -> statusLabel.setText("Cargando recursos..."), new KeyValue(progressBar.progressProperty(), 0.4)),
            new KeyFrame(Duration.seconds(1.5), e -> statusLabel.setText("Preparando interfaz..."), new KeyValue(progressBar.progressProperty(), 0.6)),
            new KeyFrame(Duration.seconds(2.0), e -> statusLabel.setText("Casi listo..."), new KeyValue(progressBar.progressProperty(), 0.8)),
            new KeyFrame(Duration.seconds(2.5), e -> statusLabel.setText("¡Listo!"), new KeyValue(progressBar.progressProperty(), 1.0)),
            new KeyFrame(Duration.seconds(3.0), e -> loadMainScene())
        );
        timeline.setCycleCount(1);
        timeline.play();
    }
    
    private void loadMainScene() {
        try {
            // Cargar la escena principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            Parent root = loader.load();
            
            MainController controller = loader.getController();
            controller.setPrimaryStage(splashStage);
            
            // Configurar la escena principal
            Scene scene = new Scene(root, 850, 650);
            
            // Transición a la escena principal
            Platform.runLater(() -> {
                splashStage.setScene(scene);
                splashStage.setTitle("Sistema de Admisión Universitaria");
                splashStage.centerOnScreen();
                splashStage.setMaximized(false);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}