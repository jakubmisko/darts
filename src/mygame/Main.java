/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.system.AppSettings;

/**
 *
 * @author jakub
 */
public class Main {
    public static void main(String[] args){
        Starter game = Starter.INSTANCE;
        AppSettings settings = new AppSettings(true);
        settings.setResolution(1280, 768);
        settings.setFrameRate(60);
        settings.setVSync(true);
        game.setSettings(settings);
        game.setShowSettings(false);
        game.start();
    }
}
