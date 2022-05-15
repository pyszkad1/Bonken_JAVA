package bonken.gui;

import bonken.game.Card;
import bonken.game.Game;
import bonken.game.PlayerInterface;
import bonken.utils.Action;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GameView extends View{


    private Game game;

    private CardPane cardPane;
    private MinigameChoicePane minigameChoicePane;
    private OnlineTrickPane onlineTrickPane;
    private OfflineTrickPane offlineTrickPane;
    private StackPane centerPane;

    private BorderPane borderPane;
    private Rectangle blockingRec;

    private StackPane wholeScreen;

    public void showMinigameChoice() {
        minigameChoicePane.setVisible(true);
        if (onlineTrickPane == null) {
            offlineTrickPane.setVisible(false);
        } else {
            onlineTrickPane.setVisible(false);
        }
    }

    public void hideMinigameChoice() {
        minigameChoicePane.setVisible(false);
        if (onlineTrickPane == null) {
            offlineTrickPane.setVisible(true);
        } else {
            onlineTrickPane.setVisible(true);
        }
    }


    public GameView( MinigameChoicePane minigamePane, CardPane cardPane, OfflineTrickPane offlineTrickPane) {

        System.out.println("StartedGameView!! --------- :)");

        this.cardPane = cardPane;
        cardPane.setMaxWidth(1080);
        cardPane.setAlignment(Pos.BOTTOM_CENTER);
        cardPane.setPrefHeight(150);
        cardPane.getStyleClass().add("card-box");

        this.minigameChoicePane = minigamePane;
        minigamePane.setPrefHeight(500);

        this.offlineTrickPane = offlineTrickPane;

        centerPane = new StackPane();
        centerPane.getChildren().add(offlineTrickPane);
        centerPane.getChildren().add(minigamePane);

        borderPane = new BorderPane();
        borderPane.setCenter(centerPane);
        borderPane.setBottom(cardPane);
        borderPane.setMaxWidth(1080);

        blockingRec = new Rectangle(1080, 200);

        blockingRec.setFill(Color.TRANSPARENT);

        wholeScreen = new StackPane();
        wholeScreen.setAlignment(blockingRec, Pos.BOTTOM_CENTER) ;
        wholeScreen.getChildren().addAll(blockingRec, borderPane);

        hideMinigameChoice();

        Scene scene = new Scene(wholeScreen, 1080, 720);
        setScene(scene);
    }

    public GameView( MinigameChoicePane minigamePane, CardPane cardPane, OnlineTrickPane onlineTrickPane) {

        System.out.println("StartedGameView!! --------- :)");

        this.cardPane = cardPane;
        cardPane.setMaxWidth(1080);
        cardPane.setAlignment(Pos.BOTTOM_CENTER);
        cardPane.setPrefHeight(150);
        cardPane.getStyleClass().add("card-box");

        this.minigameChoicePane = minigamePane;
        minigamePane.setPrefHeight(500);

        this.onlineTrickPane = onlineTrickPane;

        centerPane = new StackPane();
        centerPane.getChildren().add(onlineTrickPane);
        centerPane.getChildren().add(minigamePane);

        borderPane = new BorderPane();
        borderPane.setCenter(centerPane);
        borderPane.setBottom(cardPane);
        borderPane.setMaxWidth(1080);

        blockingRec = new Rectangle(1080, 200);

        blockingRec.setFill(Color.TRANSPARENT);

        wholeScreen = new StackPane();
        wholeScreen.setAlignment(blockingRec, Pos.BOTTOM_CENTER) ;
        wholeScreen.getChildren().addAll(blockingRec, borderPane);

        hideMinigameChoice();

        Scene scene = new Scene(wholeScreen, 1080, 720);
        setScene(scene);
    }

    public void showBlockingRec() {
        blockingRec.toFront();
    }

    public void hideBlockingRec(){
        borderPane.toFront();
    }


}
