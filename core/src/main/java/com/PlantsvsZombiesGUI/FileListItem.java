package com.PlantsvsZombiesGUI;

import com.badlogic.gdx.files.FileHandle;

public class FileListItem {
    public String name;
    public FileHandle file;

    public FileListItem(FileHandle file) {
        this.file = file;
        this.name = file.name();
    }

    public FileListItem(String name, FileHandle file) {
        this.name = name;
        this.file = file;
    }

    @Override
    public String toString() {
        return name;
    }
}