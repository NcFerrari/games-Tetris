package lp.games.tetris.core;

public class Output {

    private Output() {

    }

    public static String render(boolean[][] fields) {
        StringBuilder result = new StringBuilder();
        for (boolean[] rows : fields) {
            for (boolean field : rows) {
                result.append(field ? "#" : ".");
            }
            result.append("\n");
        }
        return result.toString();
    }
}
