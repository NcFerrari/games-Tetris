package lp.games.tetris.gui;

import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class GameOverDialog extends Dialog<ButtonType> {

    public GameOverDialog(String newGameTextButton, String gameOverText, String closeGameText, Runnable onNewGame) {
        setContentText(gameOverText);
        ButtonType newGameButton = new ButtonType(newGameTextButton);
        ButtonType closeGameButton = new ButtonType(closeGameText);
        getDialogPane().getButtonTypes().addAll(newGameButton, closeGameButton);
        setResultConverter(buttonType -> buttonType);

        setOnHidden(evt -> {
            if (newGameButton.equals(getResult())) {
                onNewGame.run();
            } else {
                Platform.exit();
            }
        });
    }
}
