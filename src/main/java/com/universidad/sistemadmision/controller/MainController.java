package com.universidad.sistemadmision.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.universidad.sistemadmision.model.Clave;
import com.universidad.sistemadmision.model.Identificador;
import com.universidad.sistemadmision.model.Respuesta;
import com.universidad.sistemadmision.model.Resultado;
import com.universidad.sistemadmision.service.DBFService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainController implements Initializable {

    @FXML
    private TextField txtRutaClaves;
    
    @FXML
    private TextField txtRutaIdentificadores;
    
    @FXML
    private TextField txtRutaRespuestas;
    
    @FXML
    private TextField txtPuntoCorrecta;
    
    @FXML
    private TextField txtPuntoIncorrecta;
    
    @FXML
    private TextField txtPuntoVacia;
    
    @FXML
    private Label lblEstadoClaves;
    
    @FXML
    private Label lblEstadoIdentificadores;
    
    @FXML
    private Label lblEstadoRespuestas;
    
    @FXML
    private Button btnCargarClaves;
    
    @FXML
    private Button btnCargarIdentificadores;
    
    @FXML
    private Button btnCargarRespuestas;
    
    @FXML
    private Button btnProcesar;
    
    @FXML
    private TableView<Resultado> tblResultados;
    
    @FXML
    private TableColumn<Resultado, String> colCodigo;
    
    @FXML
    private TableColumn<Resultado, String> colTema;
    
    @FXML
    private TableColumn<Resultado, Integer> colCorrectas;
    
    @FXML
    private TableColumn<Resultado, Integer> colIncorrectas;
    
    @FXML
    private TableColumn<Resultado, Integer> colVacias;
    
    @FXML
    private TableColumn<Resultado, Double> colPuntaje;
    
    @FXML
    private ComboBox<String> cmbOrdenar;
    
    private Stage primaryStage;
    private DBFService dbfService;
    private List<Clave> claves;
    private List<Identificador> identificadores;
    private List<Respuesta> respuestas;
    private ObservableList<Resultado> resultados;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dbfService = new DBFService();
        resultados = FXCollections.observableArrayList();
        
        // Configurar la tabla
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colTema.setCellValueFactory(new PropertyValueFactory<>("tema"));
        colCorrectas.setCellValueFactory(new PropertyValueFactory<>("correctas"));
        colIncorrectas.setCellValueFactory(new PropertyValueFactory<>("incorrectas"));
        colVacias.setCellValueFactory(new PropertyValueFactory<>("vacias"));
        colPuntaje.setCellValueFactory(new PropertyValueFactory<>("puntaje"));
        
        tblResultados.setItems(resultados);
        
        // Configurar el combo de ordenamiento
        cmbOrdenar.setItems(FXCollections.observableArrayList(
                "Puntaje (Mayor a Menor)", 
                "Puntaje (Menor a Mayor)", 
                "Código (A-Z)", 
                "Código (Z-A)", 
                "Tema (A-Z)", 
                "Tema (Z-A)"));
        cmbOrdenar.getSelectionModel().selectFirst();
        cmbOrdenar.setOnAction(e -> ordenarResultados());
        
        // Valores por defecto para los puntos
        txtPuntoCorrecta.setText("1.0");
        txtPuntoIncorrecta.setText("-0.25");
        txtPuntoVacia.setText("0.0");
        
        // Inicializar etiquetas de estado
        lblEstadoClaves.getStyleClass().add("estado-no-cargado");
        lblEstadoIdentificadores.getStyleClass().add("estado-no-cargado");
        lblEstadoRespuestas.getStyleClass().add("estado-no-cargado");
    }
    
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
    @FXML
    private void cargarClaves(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo CLAVES.dbf");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos DBF", "*.dbf"));
        File file = fileChooser.showOpenDialog(primaryStage);
        
        if (file != null) {
            try {
                claves = dbfService.cargarClaves(file.getAbsolutePath());
                txtRutaClaves.setText(file.getAbsolutePath());
                
                // Actualizar indicador visual
                lblEstadoClaves.setText("Cargado ✓");
                lblEstadoClaves.getStyleClass().remove("estado-no-cargado");
                lblEstadoClaves.getStyleClass().add("estado-cargado");
                
                mostrarAlerta(Alert.AlertType.INFORMATION, "Claves cargadas", 
                        "Se han cargado " + claves.size() + " claves correctamente.");
            } catch (IOException e) {
                // Mantener indicador de no cargado
                lblEstadoClaves.setText("Error al cargar");
                
                mostrarAlerta(Alert.AlertType.ERROR, "Error", 
                        "Error al cargar el archivo: " + e.getMessage());
            }
        }
    }
    
    @FXML
    private void cargarIdentificadores(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo IDENTIFICADOR.dbf");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos DBF", "*.dbf"));
        File file = fileChooser.showOpenDialog(primaryStage);
        
        if (file != null) {
            try {
                identificadores = dbfService.cargarIdentificadores(file.getAbsolutePath());
                txtRutaIdentificadores.setText(file.getAbsolutePath());
                
                // Actualizar indicador visual
                lblEstadoIdentificadores.setText("Cargado ✓");
                lblEstadoIdentificadores.getStyleClass().remove("estado-no-cargado");
                lblEstadoIdentificadores.getStyleClass().add("estado-cargado");
                
                mostrarAlerta(Alert.AlertType.INFORMATION, "Identificadores cargados", 
                        "Se han cargado " + identificadores.size() + " identificadores correctamente.");
            } catch (IOException e) {
                // Mantener indicador de no cargado
                lblEstadoIdentificadores.setText("Error al cargar");
                
                mostrarAlerta(Alert.AlertType.ERROR, "Error", 
                        "Error al cargar el archivo: " + e.getMessage());
            }
        }
    }
    
    @FXML
    private void cargarRespuestas(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo RESPUESTAS.dbf");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos DBF", "*.dbf"));
        File file = fileChooser.showOpenDialog(primaryStage);
        
        if (file != null) {
            try {
                respuestas = dbfService.cargarRespuestas(file.getAbsolutePath());
                txtRutaRespuestas.setText(file.getAbsolutePath());
                
                // Actualizar indicador visual
                lblEstadoRespuestas.setText("Cargado ✓");
                lblEstadoRespuestas.getStyleClass().remove("estado-no-cargado");
                lblEstadoRespuestas.getStyleClass().add("estado-cargado");
                
                mostrarAlerta(Alert.AlertType.INFORMATION, "Respuestas cargadas", 
                        "Se han cargado " + respuestas.size() + " respuestas correctamente.");
            } catch (IOException e) {
                // Mantener indicador de no cargado
                lblEstadoRespuestas.setText("Error al cargar");
                
                mostrarAlerta(Alert.AlertType.ERROR, "Error", 
                        "Error al cargar el archivo: " + e.getMessage());
            }
        }
    }
    
    @FXML
    private void procesarResultados(ActionEvent event) {
        if (claves == null || identificadores == null || respuestas == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Datos incompletos", 
                    "Debe cargar todos los archivos antes de procesar.");
            return;
        }
        
        try {
            double puntoPorCorrecta = Double.parseDouble(txtPuntoCorrecta.getText());
            double puntoPorIncorrecta = Double.parseDouble(txtPuntoIncorrecta.getText());
            double puntoPorVacia = Double.parseDouble(txtPuntoVacia.getText());
            
            List<Resultado> listaResultados = dbfService.calcularResultados(
                    claves, identificadores, respuestas, 
                    puntoPorCorrecta, puntoPorIncorrecta, puntoPorVacia);
            
            resultados.clear();
            resultados.addAll(listaResultados);
            
            // Ordenar según la selección actual
            ordenarResultados();
            
            mostrarAlerta(Alert.AlertType.INFORMATION, "Procesamiento completado", 
                    "Se han procesado " + resultados.size() + " resultados correctamente.");
            
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error en los datos", 
                    "Los valores de puntuación deben ser números válidos.");
        }
    }
    
    private void ordenarResultados() {
        String criterio = cmbOrdenar.getValue();
        
        if (criterio == null || resultados.isEmpty()) {
            return;
        }
        
        switch (criterio) {
            case "Puntaje (Mayor a Menor)":
                FXCollections.sort(resultados, (r1, r2) -> Double.compare(r2.getPuntaje(), r1.getPuntaje()));
                break;
            case "Puntaje (Menor a Mayor)":
                FXCollections.sort(resultados, (r1, r2) -> Double.compare(r1.getPuntaje(), r2.getPuntaje()));
                break;
            case "Código (A-Z)":
                FXCollections.sort(resultados, (r1, r2) -> r1.getCodigo().compareTo(r2.getCodigo()));
                break;
            case "Código (Z-A)":
                FXCollections.sort(resultados, (r1, r2) -> r2.getCodigo().compareTo(r1.getCodigo()));
                break;
            case "Tema (A-Z)":
                FXCollections.sort(resultados, (r1, r2) -> r1.getTema().compareTo(r2.getTema()));
                break;
            case "Tema (Z-A)":
                FXCollections.sort(resultados, (r1, r2) -> r2.getTema().compareTo(r1.getTema()));
                break;
        }
    }
    
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}