package com.PlantsvsZombiesGUI;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class GameSettingsDialog extends Dialog {
    private final TextField timeInput;      // Campo para ingresar el tiempo total
    private final TextField brainsInput;    // Campo para cerebros
    private final TextField sunsInput;      // Campo para soles
    private final Table contentTable;       // Tabla para organizar la UI

    private Integer timeResult;             // Resultado del tiempo
    private Integer brainsResult;           // Resultado de cerebros
    private Integer sunsResult;             // Resultado de soles

    private ResultListener resultListener;  // Listener para notificar resultados

    public interface ResultListener {
        void onResult(Integer time, Integer brains, Integer suns);
    }

    public GameSettingsDialog(String title, Skin skin) {
        super(title, skin);

        // Crear los campos de texto
        timeInput = new TextField("", skin);
        brainsInput = new TextField("", skin);
        sunsInput = new TextField("", skin);

        // Configurar la tabla
        contentTable = getContentTable();
        contentTable.add("Total match time (min):").left().row();
        contentTable.add(timeInput).fillX().expandX().padBottom(10).row();

        contentTable.add("Brains amount (zombies):").left().row();
        contentTable.add(brainsInput).fillX().expandX().padBottom(10).row();

        contentTable.add("Suns amount (plants):").left().row();
        contentTable.add(sunsInput).fillX().expandX().padBottom(10).row();

        // Agregar botones
        button("OK", true);
        button("Cancelar", false);
    }

    @Override
    public Dialog show(Stage stage) {
        Dialog dialog = super.show(stage);
        stage.setKeyboardFocus(timeInput); // Enfocar el primer campo
        return dialog;
    }

    @Override
    protected void result(Object object) {
        boolean success = (Boolean) object;
        if (success) {
            try {
                // Recuperar y validar las entradas
                timeResult = Integer.parseInt(timeInput.getText().trim());
                brainsResult = Integer.parseInt(brainsInput.getText().trim());
                sunsResult = Integer.parseInt(sunsInput.getText().trim());

                System.out.println("Tiempo: " + timeResult + " min");
                System.out.println("Cerebros: " + brainsResult);
                System.out.println("Soles: " + sunsResult);

                if (resultListener != null) {
                    resultListener.onResult(timeResult, brainsResult, sunsResult);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Entrada inválida. Asegúrate de usar números enteros.");
                timeResult = null;
                brainsResult = null;
                sunsResult = null;
            }
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    // Método para configurar el listener
    public void setResultListener(ResultListener listener) {
        this.resultListener = listener;
    }
}
