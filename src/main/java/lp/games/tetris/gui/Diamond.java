package lp.games.tetris.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Diamond {

    public static ImageView createDiamond(double x, double y, double size, Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        imageView.setX(x * size);
        imageView.setY(y * size);
        return imageView;
    }

    private Diamond() {

    }
}
