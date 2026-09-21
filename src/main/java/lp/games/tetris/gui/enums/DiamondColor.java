package lp.games.tetris.gui.enums;

import javafx.scene.image.Image;
import lombok.Getter;
import lp.games.tetris.common.ShapeType;

import java.util.Objects;

public enum DiamondColor {

    BRONZE(new Image(Objects.requireNonNull(DiamondColor.class.getClassLoader().getResourceAsStream("images/Diamonds/bronze.png")))),
    DARK_BLUE(new Image(Objects.requireNonNull(DiamondColor.class.getClassLoader().getResourceAsStream("images/Diamonds/darkblue.png")))),
    GOLD(new Image(Objects.requireNonNull(DiamondColor.class.getClassLoader().getResourceAsStream("images/Diamonds/gold.png")))),
    GREEN(new Image(Objects.requireNonNull(DiamondColor.class.getClassLoader().getResourceAsStream("images/Diamonds/green.png")))),
    LIGHT_BLUE(new Image(Objects.requireNonNull(DiamondColor.class.getClassLoader().getResourceAsStream("images/Diamonds/lightblue.png")))),
    ORANGE(new Image(Objects.requireNonNull(DiamondColor.class.getClassLoader().getResourceAsStream("images/Diamonds/orange.png")))),
    PINK(new Image(Objects.requireNonNull(DiamondColor.class.getClassLoader().getResourceAsStream("images/Diamonds/pink.png")))),
    PURPLE(new Image(Objects.requireNonNull(DiamondColor.class.getClassLoader().getResourceAsStream("images/Diamonds/purple.png")))),
    RED(new Image(Objects.requireNonNull(DiamondColor.class.getClassLoader().getResourceAsStream("images/Diamonds/red.png")))),
    SILVER(new Image(Objects.requireNonNull(DiamondColor.class.getClassLoader().getResourceAsStream("images/Diamonds/silver.png"))));

    @Getter
    private final Image image;

    DiamondColor(Image image) {
        this.image = image;
    }

    public static DiamondColor getDiamondColor(ShapeType shapeType) {
        return switch (shapeType) {
            case L -> LIGHT_BLUE;
            case J -> ORANGE;
            case T -> GOLD;
            case O -> DARK_BLUE;
            case I -> RED;
            case S -> GREEN;
            case Z -> PINK;
            case U -> PURPLE;
            case POINT -> BRONZE;
        };
    }
}
