package lp.games.tetris.core;

public record GameSpeed(long baseDelayNanos, long minDelayNanos, long stepNanos) {

    private static final long DEFAULT_BASE_DELAY_NANOS = 500_000_000L;
    private static final long DEFAULT_MIN_DELAY_NANOS = 100_000_000L;
    private static final long DEFAULT_STEP_NANOS = 3_000_000L;

    public GameSpeed {
        if (minDelayNanos <= 0) {
            throw new IllegalArgumentException("Minimální prodleva musí být kladná, je " + minDelayNanos);
        }
        if (baseDelayNanos < minDelayNanos) {
            throw new IllegalArgumentException("Základní prodleva " + baseDelayNanos + " je menší než minimální " + minDelayNanos);
        }
        if (stepNanos < 0) {
            throw new IllegalArgumentException("Krok zrychlení nesmí být záporný, je " + stepNanos);
        }
    }

    public static GameSpeed defaultSpeed() {
        return new GameSpeed(DEFAULT_BASE_DELAY_NANOS, DEFAULT_MIN_DELAY_NANOS, DEFAULT_STEP_NANOS);
    }

    public long delayFor(int score) {
        return Math.max(minDelayNanos, baseDelayNanos - (long) score * stepNanos);
    }
}
