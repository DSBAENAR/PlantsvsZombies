package com.PlantsvsZombiesGUI;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class NewFileDialog extends Dialog {
    private final TextField nameInput;
    private String result;

    public NewFileDialog(String title, Skin skin) {
        super(title, skin);

        // Add input field for folder name
        nameInput = new TextField("", skin);
        getContentTable().add("Enter folder name:").row();
        getContentTable().add(nameInput).fillX().expandX().row();

        // Add OK and Cancel buttons
        button("OK", true);
        button("Cancel", false);

        // Focus the input field
        getStage().setKeyboardFocus(nameInput);
    }

    @Override
    protected void result(Object object) {
        boolean success = (Boolean) object;
        if (success) {
            result = nameInput.getText();
        } else {
            result = null;
        }
    }

    public String getResult() {
        return result;
    }
}

