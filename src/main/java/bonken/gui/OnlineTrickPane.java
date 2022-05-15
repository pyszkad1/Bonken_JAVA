package bonken.gui;

import bonken.game.Position;
import bonken.net.Server;
import bonken.utils.Callable;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.TimerTask;
import java.util.logging.Logger;

public class OnlineTrickPane extends TrickPane {

    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    private OnlineStatusPane statusPane;

    public OnlineTrickPane(Position bottomPlayer, Callable showBlock, Callable hideBlock) {
        super(bottomPlayer, showBlock, hideBlock);
        statusPane = new OnlineStatusPane();
        setupCardPanes();
    }

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
        LOGGER.info("---------DRAWING TRICK---------");
        this.clear();


        Position currPosition = Position.values()[position];
        System.out.println(position + " is position");


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
            p.setBorder(testBorder);
            this.getChildren().add(p);
        }

        this.getChildren().add(statusPane);
        statusPane.setTranslateX(0);
        statusPane.setTranslateX(0);

    }

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