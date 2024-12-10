package com.PlantsvsZombiesGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class CustomFileChooser {
    private final Stage stage;
    private final Table mainTable;
    private final Table fileListTable;
    private final TextField pathField;
    private final TextField fileNameField;
    private FileHandle currentDirectory;
    private FileChooserListener listener;

    private final BitmapFont font;
    private final Label.LabelStyle labelStyle;
    private final TextField.TextFieldStyle textFieldStyle;
    private final TextButton.TextButtonStyle textButtonStyle;

    public interface FileChooserListener {
        void onFileSelected(FileHandle file);
    }

    public CustomFileChooser(Stage stage, SpriteBatch batch) {
        this.stage = stage;

        // Crear una fuente compartida
        font = new BitmapFont(); // Cambiar a una fuente personalizada si es necesario

        // Crear estilos reutilizables
        labelStyle = new Label.LabelStyle(font, Color.WHITE);

        textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = font;
        textFieldStyle.fontColor = Color.WHITE;

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;

        // Tabla principal
        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.setBackground(new CustomDrawable(Color.DARK_GRAY)); // Fondo sólido

        // Tabla de la ruta actual
        Label pathLabel = new Label("Current Path: ", labelStyle);
        pathField = new TextField("", textFieldStyle);
        pathField.setDisabled(true);

        Table pathTable = new Table();
        pathTable.add(pathLabel).left();
        pathTable.add(pathField).expandX().fillX();

        // Lista de archivos
        fileListTable = new Table();
        ScrollPane scrollPane = new ScrollPane(fileListTable);

        // Tabla para el nombre del archivo
        Label fileNameLabel = new Label("File Name: ", labelStyle);
        fileNameField = new TextField("", textFieldStyle);

        Table fileNameTable = new Table();
        fileNameTable.add(fileNameLabel).left();
        fileNameTable.add(fileNameField).expandX().fillX();

        // Botones de acción
        TextButton selectButton = new TextButton("Select", textButtonStyle);
        TextButton cancelButton = new TextButton("Cancel", textButtonStyle);

        selectButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                if (listener != null) {
                    String fileName = fileNameField.getText();
                    if (!fileName.isEmpty()) {
                        FileHandle selectedFile = Gdx.files.absolute(currentDirectory.path() + "/" + fileName);
                        listener.onFileSelected(selectedFile);
                        close();
                    }
                }
            }
        });

        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                close();
            }
        });

        Table buttonTable = new Table();
        buttonTable.add(selectButton).pad(5);
        buttonTable.add(cancelButton).pad(5);

        // Combinar todos los componentes
        mainTable.add(pathTable).expandX().fillX().pad(5).row();
        mainTable.add(scrollPane).expand().fill().pad(5).row();
        mainTable.add(fileNameTable).expandX().fillX().pad(5).row();
        mainTable.add(buttonTable).center().pad(5);

        stage.addActor(mainTable);

        // Directorio predeterminado
        setCurrentDirectory(Gdx.files.absolute(System.getProperty("user.home"))); // Inicio en el directorio del usuario
    }

    public void setCurrentDirectory(FileHandle directory) {
        currentDirectory = directory;
        pathField.setText(directory.path());
        populateFileList(directory.list());
    }

    private void populateFileList(FileHandle[] files) {
        fileListTable.clear();
        Array<FileHandle> sortedFiles = new Array<>(files);
        sortedFiles.sort((a, b) -> Boolean.compare(b.isDirectory(), a.isDirectory())); // Directories first

        for (FileHandle file : sortedFiles) {
            Label fileLabel = new Label(file.name(), labelStyle);
            fileLabel.setColor(file.isDirectory() ? Color.CYAN : Color.LIGHT_GRAY);

            fileLabel.addListener(new ClickListener() {
                @Override
                public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                    if (file.isDirectory()) {
                        setCurrentDirectory(file);
                    } else {
                        fileNameField.setText(file.name());
                    }
                }
            });

            fileListTable.add(fileLabel).left().expandX().fillX().pad(2).row();
        }
    }

    public void setListener(FileChooserListener listener) {
        this.listener = listener;
    }

    public void close() {
        mainTable.remove();
    }
}
