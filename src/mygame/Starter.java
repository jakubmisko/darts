/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.SimpleApplication;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.ListBox;
import java.util.List;
import mygame.core.Core;
import mygame.gui.Menu;
import mygame.score.Database;
import mygame.score.Record;

/**
 *
 * @author jakub
 */
public class Starter extends SimpleApplication {

    private Database db;
    private Menu menu;
    private Core core;
    public static final Starter INSTANCE = new Starter();

    private Starter() {
        db = new Database();
    }

    @Override
    public void simpleInitApp() {
        flyCam.setDragToRotate(true);
        core = new Core(this, settings, guiFont);
        menu = new Menu(assetManager, inputManager, audioRenderer, guiViewPort);
        menu.initMenu();
        //core.initApp();
        stateManager.attach(menu);
        setDisplayFps(false); // to hide the FPS
        setDisplayStatView(false); // to hide the statistics 
    }

    public Database getDatabase() {
        return db;
    }

    public void startGame() {
        stateManager.detach(menu);
        stateManager.attach(core);
        flyCam.setDragToRotate(false);
        core.initApp();
    }

    public void showMenu() {
        stateManager.attach(menu);
        stateManager.detach(core);
        guiNode.detachAllChildren();
        flyCam.setDragToRotate(true);
        menu.initMenu();
    }

    public void stopGame() {
        this.stop();
    }

    public void showHighScores(Nifty nifty) {
        List<Record> scores = db.load();
        stateManager.detach(core);
        stateManager.attach(menu);
        nifty.gotoScreen("Screen_ID2");
        ListBox<String> list = nifty.getScreen("Screen_ID2").findNiftyControl("sc", ListBox.class);
        list.clear();
        for (Record r : scores) {
            list.addItem(r.getName() + " - " + r.getCount() + " throws");
        }
    }
}
