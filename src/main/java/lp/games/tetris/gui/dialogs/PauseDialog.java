package lp.games.tetris.gui.dialogs;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import lp.games.tetris.core.Game;

public class PauseDialog extends Dialog<Void> {

    private static final String PAUSE_TEXT = "Pauza";
    private static final String CONTINUE_TEXT = "Pokračuj";

    public PauseDialog(Game game) {
        setContentText(PAUSE_TEXT);
        ButtonType continueButton = new ButtonType(CONTINUE_TEXT);
        getDialogPane().getButtonTypes().add(continueButton);
        setOnHidden(dialogEvent -> game.togglePause());
    }
}
