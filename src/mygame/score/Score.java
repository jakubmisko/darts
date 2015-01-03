/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.score;

import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import mygame.Starter;
import mygame.gui.NameInput;

/**
 *
 * @author jakub
 */
public class Score {
    private int scoreP1, scoreP2, throwP1, throwP2;
    private BitmapText player1, player2, newRound;
    private BitmapText status, legP1, legP2;
    private int throwNum, leg1, leg2;
    private boolean isDouble, isTriple;

    public Score(BitmapFont guiFont, Node guiNode, AppSettings settings) {
        throwP1 = 0;
        throwP2 = 0;
        leg1 = 0;
        leg2 = 0;
        scoreP1 = 501;
        scoreP2 = 501;
        throwNum = 1;
        player1 = new BitmapText(guiFont, false);
        player1.setSize(30);
        player1.setText("Player 1:\n" + scoreP1);
        player1.setLocalTranslation(300 - player1.getLineWidth(), settings.getHeight() - player1.getLineHeight(), 0);
        player2 = new BitmapText(guiFont, false);
        player2.setSize(30);
        player2.setText("Player 2:\n" + scoreP2);
        player2.setLocalTranslation(settings.getWidth() - 300, settings.getHeight() - player2.getLineHeight(), 0);
        status = new BitmapText(guiFont);
        status.setSize(25);
        status.setText("Hit:");
        status.setLocalTranslation(settings.getWidth() / 2 - 30, settings.getHeight() - status.getLineHeight(), 0);
        legP1 = new BitmapText(guiFont, false);
        legP1.setSize(30);
        legP1.setText("Legs: 0");
        legP1.setLocalTranslation(10, settings.getHeight() / 2, 0);
        legP2 = new BitmapText(guiFont, false);
        legP2.setSize(30);
        legP2.setText("Legs: 0");
        legP2.setLocalTranslation(settings.getWidth() - 100, settings.getHeight() / 2, 0);
        newRound = new BitmapText(guiFont, false);
        newRound.setSize(45);
        newRound.setText("New round");
        newRound.setColor(ColorRGBA.Red);
        newRound.setLocalTranslation(settings.getWidth() / 2 - 100, settings.getHeight() / 2, 0);
        isDouble = false;
        isTriple = false;
        guiNode.attachChild(player1);
        guiNode.attachChild(player2);
        guiNode.attachChild(status);
        guiNode.attachChild(legP1);
        guiNode.attachChild(legP2);
        //guiNode.attachChild(newRound);


    }

    public void update(CollisionResults result) {
        int value = 0;
        String hit = result.getCollision(0).getGeometry().getName();
        if (!hit.equals("board1") || hit.length() != 6) {

            String tmp = result.getCollision(2).getGeometry().getName();
            if (tmp.equals("board1")) {
                tmp = "00";
                hit = "00";
            }
            hit = parse(hit);
            if (hit.equals("double")) {
                isDouble = true;
                value = Integer.parseInt(parse(tmp)) * 2;
            } else if (hit.equals("triple")) {
                isTriple = true;
                value = Integer.parseInt(parse(tmp)) * 3;
            } else {
                value = Integer.parseInt(hit);
            }

        }
        if (throwNum > 3) {
            throwP2++;
            if (scoreP2 - value == 0 || scoreP2 - value == 1 || scoreP2 - value < 0) {
                if (isDouble || value == 25 || value == 50) {

                    legP2.setText("Legs: " + ++leg2);
                    reset();
                    player2.setText("Player2 Won round");
                    checkWin(leg2, 2);
                } else {
                    player2.setText("Player2:\nGoing bust\n" + scoreP2);

                }
                player1.setColor(ColorRGBA.White);
                player2.setColor(ColorRGBA.Red);
            } else {
                scoreP2 -= value;
                player2.setText("Player2:\n" + scoreP2);
                player1.setColor(ColorRGBA.White);
                player2.setColor(ColorRGBA.Yellow);
            }

        } else {
            throwP1++;
            if (scoreP1 - value == 0 || scoreP1 - value == 1 || scoreP1 - value < 0) {
                if (isDouble || value == 25 || value == 50) {
                    legP1.setText("Legs: " + ++leg1);
                    reset();
                    player1.setText("Player1 Won round");
                    checkWin(leg1, 1);
                } else {
                    player1.setText("Player1:\nGoing bust\n" + scoreP1);

                }
                player1.setColor(ColorRGBA.Red);
                player2.setColor(ColorRGBA.White);
            } else {
                scoreP1 -= value;
                player1.setText("Player1:\n" + scoreP1);
                player2.setColor(ColorRGBA.White);
                player1.setColor(ColorRGBA.Yellow);
            }
        }
        updateStatus(value, throwNum);
        throwNum++;

        if (throwNum == 7) {
            throwNum = 1;
        }
    }

    public void updateStatus(int value, int throwNum) {
        int number;
        if (throwNum == 4 || throwNum == 1) {
            status.setText("Hit:");
            number = 1;
        } else if (throwNum > 4) {
            number = throwNum - 3;
        } else {
            number = throwNum;
        }
        String s = status.getText() + "\n" + number + " : " + value;
        if (isDouble) {
            s += " (2x" + (value / 2) + ")";
        } else if (isTriple) {
            s += " (3x" + (value / 3) + ")";
        }

        status.setText(s);

        isDouble = false;
        isTriple = false;
    }

    public void checkWin(int leg, int player){
        int count = player == 1 ? throwP1 : throwP2;
        if(leg == 5){
            NameInput in = new NameInput(count, Starter.INSTANCE.getDatabase());
            in.start();
        }
    }
    public void reset() {
        scoreP1 = 501;
        scoreP2 = 501;
        player1.setText("Pleyer 1\n501");
        player2.setText("Pleyer 2\n501");
        status.setText("Hit:");
        throwNum = 0;
    }

    public int getScoreP1() {
        return scoreP1;
    }

    public int getScoreP2() {
        return scoreP2;
    }

    private String parse(String score) {
        return score.substring(0, score.length() - 1);
    }
}
