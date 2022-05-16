package bonken;

import bonken.game.Position;
import bonken.gui.*;
import bonken.net.Client;
import bonken.net.Protocol;
import bonken.utils.Callable;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;

public class OnlineController {

    private Position myPosition;
    public Stage stage;
    public StartMenuView startMenuView;

    private String username;
    private MinigameChoicePane minigameChoicePane;
    private CardPane cardPane;
    private OnlineTrickPane trickPane;
    private GameView gameView;
    private OnlineEndGameView onlineEndGameView;
    private GameStartedView gameStartedView;
    private OnlineStatusPane onlineStatusPane;
    private OnlineScoreboardView onlineScoreboardView;

    private Controller controller;
    private Client client;
    private Callable onMinigameRequired;

    public OnlineController(Stage stage, Client client, StartMenuView startMenuView, Controller controller) {
        this.client = client;
        this.stage = stage;
        this.startMenuView = startMenuView;
        this.controller = controller;

        this.minigameChoicePane = new MinigameChoicePane( minigame -> {
            gameView.hideMinigameChoice();
            client.sendToServer(Protocol.MINIGAME,  String.valueOf(minigame.num));
        });
        this.cardPane = new CardPane(card -> {client.sendToServer(Protocol.CARD, card); cardPane.updateAfter(currentStringCardHand); trickPane.packUpTrick();  });
    }


    public void setMyPos(Position pos){
        this.myPosition = pos;
        this.onlineScoreboardView = new OnlineScoreboardView();

        this.onlineStatusPane = new OnlineStatusPane(onlineScoreboardView);
        this.trickPane = new OnlineTrickPane(myPosition, () -> gameView.showBlockingRec(), () -> gameView.hideBlockingRec(), onlineStatusPane);
        this.onlineEndGameView = new OnlineEndGameView(() -> { stage.setScene(startMenuView.getScene());}, () -> {stage.close(); this.close();});
        gameView = new GameView(minigameChoicePane, cardPane, trickPane);
    }

    public Position getMyPosition() {
        return myPosition;
    }

    private String[] currentStringCardHand;

    public void setCurrentStringCardHand(String[] currentStringCardHand) {
        this.currentStringCardHand = currentStringCardHand;
    }

    public void updateHand(String card) {
        ArrayList<String> handList = new ArrayList<>();
        for (String string : currentStringCardHand){
            handList.add(string);
        }

        for (String string: handList) {
            if (string.equals(card)){
                handList.remove(card);
            }
        }
        for (int i = 0; i < handList.size(); i++) {
            currentStringCardHand[i] = handList.get(i);
        }
    }

    private boolean showingGameView = false;

    public void updateGui(String stringFirstPlayer, String[] trick, String[] cardHand, String[] playableCards){
        int firstPlayer = Integer.valueOf(stringFirstPlayer);
        trickPane.update(firstPlayer, trick);
        Platform.runLater(() -> cardPane.updateBefore(cardHand, playableCards));
    }

    public void showCardHand(String[] cardHand) {
        Platform.runLater(() -> cardPane.updateAfter(cardHand));
    }

    public void updateTrickEnd(String firstPlayerAgain, String[] wholeTrick){
        int firstPlayer = Integer.valueOf(firstPlayerAgain);
        trickPane.updateOnTrickEnd(firstPlayer, wholeTrick);

    }

    public void start() {
        stage.setScene(startMenuView.getScene());
        stage.show();
    }

    public void showGameView() {
        if (!showingGameView){
        stage.setScene(gameView.getScene());
        stage.show();
        showingGameView = true;
        }
    }

    public void showGameStarted() {
        gameStartedView = new GameStartedView();
        Platform.runLater(() -> stage.setScene(gameStartedView.getScene()));
    }

    public void setPossibleMinigames(ArrayList<Integer> minigames) {
        minigameChoicePane.setAvailableMinigames(minigames);
    }

    public void showMiniGameChoiceView() {
        gameView.showMinigameChoice();
    }

    public void showEndGameScreen() {
        onlineEndGameView.show();
        stage.setScene(onlineEndGameView.getScene());
    }

    public void close() {
        if (trickPane != null) trickPane.killTimer();
        if (controller != null) controller.close();
    }

    public void updateStatus(String roundNum, String minigame) {
        onlineStatusPane.update(roundNum, minigame);
    }

    public void updateScoreboard(String[] players, String[] score) {
        onlineScoreboardView.update(players, score);
        onlineEndGameView.getScoreboard(players, score);
    }
}
