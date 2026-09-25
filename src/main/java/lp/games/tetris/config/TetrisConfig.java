package lp.games.tetris.config;

import lp.games.tetris.core.GameSpeed;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.function.Function;
import java.util.concurrent.TimeUnit;

public record TetrisConfig(int boardWidth, int boardHeight, double fieldSize,
                           long baseDelayMillis, long minDelayMillis, long speedStepMillis,
                           int blinkCount, long blinkDurationMillis) {

    public static final String FILE_NAME = "tetris.properties";
    private static final String DEFAULTS_RESOURCE = "/" + FILE_NAME;

    private static final String BOARD_WIDTH = "board.width";
    private static final String BOARD_HEIGHT = "board.height";
    private static final String FIELD_SIZE = "field.size";
    private static final String BASE_DELAY = "speed.baseDelayMillis";
    private static final String MIN_DELAY = "speed.minDelayMillis";
    private static final String SPEED_STEP = "speed.stepMillis";
    private static final String BLINK_COUNT = "blink.count";
    private static final String BLINK_DURATION = "blink.durationMillis";

    private static final int MIN_BOARD_SIDE = 4;

    public TetrisConfig {
        requireAtLeast(BOARD_WIDTH, boardWidth, MIN_BOARD_SIDE);
        requireAtLeast(BOARD_HEIGHT, boardHeight, MIN_BOARD_SIDE);
        if (fieldSize <= 0) {
            throw new IllegalArgumentException(FIELD_SIZE + " musí být kladné, je " + fieldSize);
        }
        requireAtLeast(MIN_DELAY, minDelayMillis, 1);
        requireAtLeast(BASE_DELAY, baseDelayMillis, minDelayMillis);
        requireAtLeast(SPEED_STEP, speedStepMillis, 0);
        requireAtLeast(BLINK_COUNT, blinkCount, 0);
        requireAtLeast(BLINK_DURATION, blinkDurationMillis, 1);
    }

    /**
     * Načte nastavení ze souboru {@value #FILE_NAME} vedle aplikace. Chybějící klíče
     * (nebo chybějící celý soubor) se doplní z výchozího nastavení v jaru.
     */
    public static TetrisConfig load() {
        return load(Path.of(FILE_NAME));
    }

    public static TetrisConfig load(Path userFile) {
        Properties properties = new Properties();
        properties.putAll(readDefaults());
        properties.putAll(readUserFile(userFile));
        return from(properties);
    }

    public static TetrisConfig from(Properties properties) {
        return new TetrisConfig(
                intValue(properties, BOARD_WIDTH),
                intValue(properties, BOARD_HEIGHT),
                doubleValue(properties, FIELD_SIZE),
                longValue(properties, BASE_DELAY),
                longValue(properties, MIN_DELAY),
                longValue(properties, SPEED_STEP),
                intValue(properties, BLINK_COUNT),
                longValue(properties, BLINK_DURATION)
        );
    }

    public GameSpeed toGameSpeed() {
        return new GameSpeed(toNanos(baseDelayMillis), toNanos(minDelayMillis), toNanos(speedStepMillis));
    }

    private static long toNanos(long millis) {
        return TimeUnit.MILLISECONDS.toNanos(millis);
    }

    private static Properties readDefaults() {
        Properties defaults = new Properties();
        try (InputStream stream = TetrisConfig.class.getResourceAsStream(DEFAULTS_RESOURCE)) {
            if (stream == null) {
                throw new IllegalStateException("V jaru chybí výchozí nastavení " + DEFAULTS_RESOURCE);
            }
            defaults.load(stream);
        } catch (IOException e) {
            throw new IllegalStateException("Výchozí nastavení " + DEFAULTS_RESOURCE + " se nepodařilo načíst", e);
        }
        return defaults;
    }

    private static Properties readUserFile(Path userFile) {
        Properties userValues = new Properties();
        if (!Files.isReadable(userFile)) {
            return userValues;
        }
        try (Reader reader = Files.newBufferedReader(userFile, StandardCharsets.UTF_8)) {
            userValues.load(reader);
        } catch (IOException e) {
            throw new IllegalStateException("Soubor " + userFile.toAbsolutePath() + " se nepodařilo přečíst", e);
        }
        return userValues;
    }

    private static int intValue(Properties properties, String key) {
        long value = longValue(properties, key);
        if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Klíč " + key + " je mimo rozsah celého čísla: " + value);
        }
        return (int) value;
    }

    private static long longValue(Properties properties, String key) {
        return parse(properties, key, Long::parseLong);
    }

    private static double doubleValue(Properties properties, String key) {
        return parse(properties, key, Double::parseDouble);
    }

    private static <T extends Number> T parse(Properties properties, String key, Function<String, T> parser) {
        String value = properties.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("V nastavení chybí klíč " + key);
        }
        try {
            return parser.apply(value.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Klíč " + key + " není číslo: " + value, e);
        }
    }

    private static void requireAtLeast(String key, long value, long minimum) {
        if (value < minimum) {
            throw new IllegalArgumentException(key + " musí být alespoň " + minimum + ", je " + value);
        }
    }
}
