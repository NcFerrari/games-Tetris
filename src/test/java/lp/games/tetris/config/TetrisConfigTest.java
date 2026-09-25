package lp.games.tetris.config;

import lp.games.tetris.core.GameSpeed;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TetrisConfigTest {

    @Test
    void defaultsAreUsedWhenNoFileExists(@TempDir Path folder) {
        TetrisConfig config = TetrisConfig.load(folder.resolve("nic.properties"));
        assertAll(
                () -> assertEquals(10, config.boardWidth()),
                () -> assertEquals(20, config.boardHeight()),
                () -> assertEquals(40, config.fieldSize()),
                () -> assertEquals(500, config.baseDelayMillis())
        );
    }

    @Test
    void userFileOverridesOnlyItsOwnKeys(@TempDir Path folder) throws IOException {
        Path file = write(folder, """
                board.width=6
                speed.baseDelayMillis=250
                """);
        TetrisConfig config = TetrisConfig.load(file);
        assertAll(
                () -> assertEquals(6, config.boardWidth(), "přepsaný klíč"),
                () -> assertEquals(20, config.boardHeight(), "nepřepsaný klíč zůstává výchozí"),
                () -> assertEquals(250, config.baseDelayMillis())
        );
    }

    @Test
    void millisecondsBecomeNanosecondsForTheGame(@TempDir Path folder) {
        GameSpeed speed = TetrisConfig.load(folder.resolve("nic.properties")).toGameSpeed();
        assertAll(
                () -> assertEquals(500_000_000L, speed.delayFor(0)),
                () -> assertEquals(100_000_000L, speed.delayFor(1_000))
        );
    }

    @Test
    void brokenValueIsReportedWithItsKey(@TempDir Path folder) throws IOException {
        Path file = write(folder, "board.width=deset\n");
        IllegalArgumentException failure = assertThrows(IllegalArgumentException.class, () -> TetrisConfig.load(file));
        assertEquals("Klíč board.width není číslo: deset", failure.getMessage());
    }

    @Test
    void nonsenseValuesAreRejected() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> config("board.width", "3"), "deska musí mít alespoň 4 sloupce"),
                () -> assertThrows(IllegalArgumentException.class, () -> config("field.size", "0"), "nulové políčko"),
                () -> assertThrows(IllegalArgumentException.class, () -> config("speed.minDelayMillis", "0"), "nulová prodleva"),
                () -> assertThrows(IllegalArgumentException.class, () -> config("speed.baseDelayMillis", "50"), "základní prodleva pod minimem")
        );
    }

    @Test
    void missingKeyIsReported() {
        Properties properties = validProperties();
        properties.remove("blink.count");
        IllegalArgumentException failure = assertThrows(IllegalArgumentException.class, () -> TetrisConfig.from(properties));
        assertEquals("V nastavení chybí klíč blink.count", failure.getMessage());
    }

    private TetrisConfig config(String key, String value) {
        Properties properties = validProperties();
        properties.setProperty(key, value);
        return TetrisConfig.from(properties);
    }

    private Properties validProperties() {
        Properties properties = new Properties();
        properties.setProperty("board.width", "10");
        properties.setProperty("board.height", "20");
        properties.setProperty("field.size", "40");
        properties.setProperty("speed.baseDelayMillis", "500");
        properties.setProperty("speed.minDelayMillis", "100");
        properties.setProperty("speed.stepMillis", "3");
        properties.setProperty("blink.count", "6");
        properties.setProperty("blink.durationMillis", "90");
        return properties;
    }

    private Path write(Path folder, String content) throws IOException {
        Path file = folder.resolve(TetrisConfig.FILE_NAME);
        Files.writeString(file, content, StandardCharsets.UTF_8);
        return file;
    }
}
