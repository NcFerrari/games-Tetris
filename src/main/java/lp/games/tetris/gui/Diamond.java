package lp.games.tetris.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Diamond {

    public static StackPane createDiamond(double x, double y, double size, Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);

        StackPane imageContainer = new StackPane(imageView);
        imageContainer.setLayoutX(x * size);
        imageContainer.setLayoutY(y * size);
        return imageContainer;
    }

    private Diamond() {

    }
}
