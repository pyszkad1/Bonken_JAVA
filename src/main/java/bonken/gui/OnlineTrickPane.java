package bonken.gui;

import bonken.game.Position;
import bonken.utils.Callable;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.TimerTask;
import java.util.logging.Logger;

/**
 * Class for showing trick in offline game.
 */
public class OnlineTrickPane extends TrickPane {

    private static final Logger LOGGER = Logger.getLogger(OnlineTrickPane.class.getName());
    public OnlineStatusPane statusPane;

    /**
     *
     * @param bottomPlayer position of player rotated according to his Position enum, default is North
     * @param showBlock call after playing a card
     * @param hideBlock call when the player is supposed to play
     * @param onlineStatusPane from onlineController
     */
    public OnlineTrickPane(Position bottomPlayer, Callable showBlock, Callable hideBlock, OnlineStatusPane onlineStatusPane) {
        super(bottomPlayer, showBlock, hideBlock);
        this.statusPane = onlineStatusPane;

        setupCardPanes();
    }

    /**
     * Update on receiving current trick.
     * @param firstPlayer
     * @param trick
     */
    public void update(int firstPlayer, String[] trick){
        if (!blocking){
            showBlock.call();
            blocking = true;
        }

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {if (blocking){
                    hideBlock.call();
                }; drawTrick(firstPlayer, trick);});
            }
        } , 3000);
    }

    private void drawTrick(int position, String[] trick) {
        LOGGER.info("Drawing trick.");
        this.clear();
        int currentPos = (4 + position - bottomPlayer.index)%4;

        Position currPosition = Position.values()[currentPos];

        for (int i = 0; i < trick.length; i++) {
            String card = trick[i];
            if(card == null) break;

            putCard(card, currPosition);

            currPosition = currPosition.next();
        }

        adjustPanePositions();
    }

    private void setupCardPanes() {
        cardPanes = new Pane[4];

        for (int i = 0; i < 4; i++) {
            Pane p = new Pane();
            cardPanes[i] = p;
            p.setMinHeight(cardHeight);
            p.setMinWidth(cardWidth);
            this.getChildren().add(p);
        }
        this.getChildren().add(statusPane);
        statusPane.setTranslateX(10);
        statusPane.setTranslateY(10);


    }

    /**
     * Updates on receiving the whole trick to draw the result.
     * @param firstPlayer
     * @param wholeTrick
     */
    public void updateOnTrickEnd(int firstPlayer, String[] wholeTrick) {
        showBlock.call();
        blocking = true;
        drawTrick(firstPlayer, wholeTrick);
    }

    private void putCard(String card, Position pos) {
        String image = this.getClass().getResource("/bonken/gui/cards/" + card).toExternalForm();
        ImageView imageView = new ImageView(new Image(image));
        imageView.getStyleClass().add("card");

        this.cardPanes[pos.index].getChildren().add(imageView);
    }

}
