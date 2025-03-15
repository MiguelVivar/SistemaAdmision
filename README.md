# Sistema de Admisión Universitaria

![Logo](src/main/resources/images/logo.png)

## Descripción

El Sistema de Admisión Universitaria es una aplicación de escritorio desarrollada en Java con JavaFX que permite procesar y evaluar los resultados de exámenes de admisión universitaria. La aplicación está diseñada para cargar archivos DBF que contienen las claves de respuestas, identificadores de estudiantes y las respuestas proporcionadas, para luego calcular y mostrar los resultados de cada postulante.

## Características

- Interfaz gráfica intuitiva y amigable con pantalla de carga (splash screen)
- Carga de archivos DBF (CLAVES.dbf, IDENTIFICADOR.dbf, RESPUESTAS.dbf)
- Configuración de puntuación personalizada para respuestas correctas, incorrectas y vacías
- Cálculo automático de resultados y puntajes
- Visualización de resultados en una tabla ordenable
- Exportación de resultados (próximamente)

## Requisitos del Sistema

- Java 11 o superior
- Maven 3.6 o superior (para compilación)

## Dependencias

- JavaFX 17.0.2
- JavaDBF 1.13.1 (para manejo de archivos DBF)

## Instalación

1. Clona este repositorio:
   ```
   git clone https://github.com/tu-usuario/sistema-admision.git
   ```

2. Navega al directorio del proyecto:
   ```
   cd sistema-admision
   ```

3. Ejecuta la aplicación:
   ```
   powershell -Command "& '(ruta del proyecto)' javafx:run"
   ```

Alternativamente, puedes ejecutar la aplicación desde tu IDE favorito importando el proyecto como un proyecto Maven.

## Uso

1. Al iniciar la aplicación, se mostrará una pantalla de carga (splash screen).
2. En la pantalla principal, carga los archivos DBF necesarios:
   - Archivo de Claves (CLAVES.dbf): Contiene las respuestas correctas para cada tema.
   - Archivo de Identificadores (IDENTIFICADOR.dbf): Contiene la información de los postulantes.
   - Archivo de Respuestas (RESPUESTAS.dbf): Contiene las respuestas proporcionadas por los postulantes.
3. Configura los puntos asignados por respuesta correcta, incorrecta y vacía.
4. Haz clic en "Procesar Resultados" para calcular los puntajes.
5. Los resultados se mostrarán en la tabla inferior, donde podrás ordenarlos según diferentes criterios.

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── universidad/
│   │           └── sistemadmision/
│   │               ├── controller/
│   │               │   ├── MainController.java
│   │               │   └── SplashController.java
│   │               ├── model/
│   │               │   ├── Clave.java
│   │               │   ├── Identificador.java
│   │               │   ├── Respuesta.java
│   │               │   └── Resultado.java
│   │               ├── service/
│   │               │   └── DBFService.java
│   │               └── Main.java
│   └── resources/
│       ├── css/
│       │   └── styles.css
│       ├── fxml/
│       │   ├── MainView.fxml
│       │   └── SplashView.fxml
│       └── images/
│           ├── banner.png
│           ├── check.png
│           ├── hero.jpg
│           └── logo.png
```

## Contribución

Si deseas contribuir a este proyecto, por favor:

1. Haz un fork del repositorio
2. Crea una rama para tu funcionalidad (`git checkout -b feature/nueva-funcionalidad`)
3. Realiza tus cambios y haz commit (`git commit -am 'Añadir nueva funcionalidad'`)
4. Sube tus cambios (`git push origin feature/nueva-funcionalidad`)
5. Crea un Pull Request

## Equipo de Desarrollo

III Ciclo "A" 2024-I Universidad Nacional "San Luis Gonzaga"

- Miguel Vivar - [GitHub](https://github.com/MiguelVivar)
- Mario Muñoz - [GitHub](https://github.com/ChuchiPr)
- Angielina Soto - [GitHub](https://github.com/Rinvinvin) 
- Luis Mitma - [GitHub](https://github.com/Elextranjero1942) 
- Juan Ttito - [GitHub](https://github.com/juanttito1003) 
- Rodrido Conislla - [GitHub](https://github.com/Rodri2505) 
- Antony Palomino - [GitHub](https://github.com/DaPcxD) 