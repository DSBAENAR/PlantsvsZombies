package com.PlantsvsZombiesGUI;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class NewFileDialog extends Dialog {
    private final TextField nameInput;
    private String result;

    public NewFileDialog(String title, Skin skin) {
        super(title, skin);

        // Crear un campo de texto para el nombre del directorio
        nameInput = new TextField("", skin);
        getContentTable().add("Enter folder name:").row();
        getContentTable().add(nameInput).fillX().expandX().row();

        // Agregar botones OK y Cancelar
        button("OK", true);
        button("Cancel", false);
    }

    @Override
    public Dialog show(Stage stage) {
        // Mostrar el diálogo normalmente
        Dialog dialog = super.show(stage);

        // Enfocar el campo de entrada para que el usuario pueda escribir
        stage.setKeyboardFocus(nameInput);

        return dialog;
    }

    @Override
    protected void result(Object object) {
        boolean success = (Boolean) object;
        if (success) {
            // Recuperar el texto ingresado
            result = nameInput.getText().trim();
            System.out.println("Texto ingresado: " + result); // Depuración
        } else {
            result = null;
            System.out.println("Operación cancelada");
        }
    }

    public String getResult() {
        return result;
    }
}
