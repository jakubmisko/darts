/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.xml.xpp3.Attributes;
import java.util.Properties;
import mygame.Starter;

/**
 *
 * @author jakub
 */
public class Ctrl implements Controller {

    private Attributes controlDefinitionAttributes;
    private Properties parameter;
    private Nifty nifty;
    private Screen screen;
    private Element element;

    public void onStartScreen() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void onEndScreen() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void run() {
        Starter.INSTANCE.startGame();
        nifty.removeScreen("Screen_ID");
    }

    public void quit() {
        Starter.INSTANCE.stopGame();
    }

    public void score() {
        Starter.INSTANCE.showHighScores(nifty);
    }

    public void back() {
        nifty.gotoScreen("Screen_ID");
    }
    
    public void rules(){
        nifty.gotoScreen("Screen_ID3");
    }

    public void bind(Nifty nifty, Screen screen, Element element, Properties parameter, Attributes controlDefinitionAttributes) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        this.nifty = nifty;
        this.screen = screen;
        this.element = element;
        this.parameter = parameter;
        this.controlDefinitionAttributes = controlDefinitionAttributes;
    }

    public void init(Properties parameter, Attributes controlDefinitionAttributes) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void onFocus(boolean getFocus) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean inputEvent(NiftyInputEvent inputEvent) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return true;
    }
}
