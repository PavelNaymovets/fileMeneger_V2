package com.example.filemeneger_v2.client.changeWindow;

import javafx.stage.Stage;

/**
 * Класс StageChange описывае текущее открытое окно.
 *
 * Хранит: ссылку на текущее открытое окно в единественном экземпляре (применен паттер сингл тон).
 */
public class StageChange {
    private static StageChange stageChangeInstance;
    private Stage currentStage;
    private StageChange(){}

    public static StageChange getInstance() {
        if(stageChangeInstance == null) {
            stageChangeInstance = new StageChange();
        }

        return stageChangeInstance;
    }

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }
}
