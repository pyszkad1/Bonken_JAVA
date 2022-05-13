package bonken.game;

import bonken.utils.Callable;
import javafx.application.Platform;

import java.util.*;

public class Game {
    PlayerInterface[] players;
    Deck deck;
    ArrayList<Integer> minigames = new ArrayList<>(12);
    ScoreBoard scoreBoard;
    public boolean gameEnded;
    public ArrayList<Round> rounds;
    private int gameCounter;

    private Callable onGameEnd;

    Timer timer;



    public ArrayList<Integer> getMinigames() {
        return minigames;
    }

    public PlayerInterface[] getPlayers() {
        return players;
    }

    public Round getCurrentRound() {
        return this.rounds.get(rounds.size() - 1);
    }

    public int getGameCounter(){
        return  gameCounter;
    }

    public Game(PlayerInterface[] players, Callable onGameEnd) {
        for (int i = 0; i < 12; i++) {
            minigames.add(Integer.valueOf(i));
        }
        this.onGameEnd = onGameEnd;
        this.players = players;

        Random random = new Random();
        int startingPlayer = 0 ;// random.nextInt( 4);

        players[startingPlayer].setHisTurn(true);

        rounds = new ArrayList<>();
        scoreBoard = new ScoreBoard(players);
        deck = new Deck();
        gameCounter = 0;
    }


    public void startRound() {
        deck.shuffle();
        Round round = new Round(this, deck, minigames, players);
        rounds.add(round);
        System.out.println("--------------------------------------------------------------------------------------------");
        round.playRound(() -> finishRound());
    }

    private void finishRound()
    {
        System.out.println(scoreBoard.toString());
        timer = new Timer();

        if(++gameCounter == 1) {
            gameEnded = true;
            System.out.println("End of Game");
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> onGameEnd.call());
                }
            } , 3100);

            return;
        }

        startRound();
    }

}
