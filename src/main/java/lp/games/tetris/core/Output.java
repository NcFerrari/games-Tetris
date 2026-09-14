package lp.games.tetris.core;

import java.util.List;

public class Output {

    private Output() {

    }

    public static String render(List<List<Boolean>> grid) {
        StringBuilder result = new StringBuilder();
        for (List<Boolean> row : grid) {
            for (boolean field : row) {
                result.append(field ? "#" : ".");
            }
            result.append("\n");
        }
        return result.toString();
    }
}
