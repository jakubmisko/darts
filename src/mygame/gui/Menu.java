package mygame.gui;

import com.jme3.app.state.AbstractAppState;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.listbox.builder.ListBoxBuilder;
import de.lessvoid.nifty.screen.DefaultScreenController;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.tools.Color;

/**
 * @author iamcreasy
 */
public class Menu extends AbstractAppState {

    private Nifty nifty;
    private Screen screen;
    private AssetManager assetManager;
    private AudioRenderer audioRenderer;
    private ViewPort guiViewPort;
    private InputManager inputManager;
    private NiftyJmeDisplay niftyDisplay;

    public Menu(AssetManager assetManager, InputManager inputManager, AudioRenderer audioRenderer, ViewPort guiViewPort) {
        this.assetManager = assetManager;
        this.audioRenderer = audioRenderer;
        this.guiViewPort = guiViewPort;
        this.inputManager = inputManager;
        niftyDisplay = new NiftyJmeDisplay(
                assetManager, inputManager, audioRenderer, guiViewPort);
    }

    public void initMenu() {


        nifty = niftyDisplay.getNifty();
        guiViewPort.addProcessor(niftyDisplay);
        //flyCam.setDragToRotate(true);

        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");

        // <screen>
        nifty.addScreen("Screen_ID", new ScreenBuilder("start screen") {
            {

                controller(new DefaultScreenController()); // Screen properties       

                // <layer>
                layer(new LayerBuilder("Layer_ID") {
                    {
                        childLayoutVertical(); // layer properties, add more...
                        backgroundColor(Color.WHITE);
                        // <panel>
                        panel(new PanelBuilder("Panel_Top") {
                            {
                                childLayoutCenter();
                                height("60%");
                                width("100%");
                                image(new ImageBuilder() {
                                    {
                                        filename("Interface/i_love_darts.jpg");
                                        visibleToMouse(false);
                                    }
                                });
                            }
                        });
                        panel(new PanelBuilder("Panel_Menu") {
                            {
                                childLayoutVertical();
                                // GUI elements

                                control(new ButtonBuilder("Button_ID", "New game") {
                                    {
                                        alignCenter();
                                        //valignCenter();

                                        height("15%");
                                        width("25%");
                                        visibleToMouse(true);
                                        //controller("mygame.gui.Ctrl");
                                        controller("mygame.gui.Ctrl");
                                        interactOnRelease("run()");
                                    }
                                });

                                control(new ButtonBuilder("Button_ID2", "High score") {
                                    {
                                        alignCenter();
                                        //valignCenter();
                                        height("15%");
                                        width("25%");
                                        controller("mygame.gui.Ctrl");
                                        interactOnRelease("score()");
                                    }
                                });
                                control(new ButtonBuilder("Button_ID3", "Rules") {
                                    {
                                        alignCenter();
                                        //valignCenter();
                                        height("15%");
                                        width("25%");
                                        controller("mygame.gui.Ctrl");
                                        interactOnRelease("rules()");
                                    }
                                });
                                control(new ButtonBuilder("QuitButton", "Exit") {
                                    {
                                        alignCenter();
                                        //valignCenter();
                                        height("15%");
                                        width("25%");
                                        controller("mygame.gui.Ctrl");
                                        interactOnClick("quit()");
                                    }
                                });
                            }
                        });
                        // </panel>
                    }
                });
                // </layer>
            }
        }.build(nifty));
        // </screen>
        nifty.addScreen("Screen_ID2", new ScreenBuilder("second screen") {
            {

                controller(new DefaultScreenController()); // Screen properties       

                // <layer>
                layer(new LayerBuilder("Layer") {
                    {
                        childLayoutVertical(); // layer properties, add more...
                        backgroundColor(Color.WHITE);
                        // <panel>
                        panel(new PanelBuilder("Panel_Top") {
                            {
                                childLayoutHorizontal();
                                height("20%");
                                width("100%");
                                image(new ImageBuilder() {
                                    {
                                        controller("mygame.gui.Ctrl");
                                        interactOnRelease("back()");
                                        filename("Interface/back.png");
                                        visibleToMouse(true);
                                    }
                                });

                                image(new ImageBuilder() {
                                    {
                                        filename("Interface/score.png");
                                        visibleToMouse(false);
                                    }
                                });

                            }
                        });
                        panel(new PanelBuilder("Panel_List") {
                            {
                                childLayoutVertical();
                                // GUI elements

                                control(new ListBoxBuilder("sc") {
                                    {
                                        
                                        alignCenter();
                                        displayItems(20);
                                        selectionModeSingle();
                                        showVerticalScrollbar();
                                        hideHorizontalScrollbar();
                                        width("60%");
                                    }
                                });


                            }
                        });
                        // </panel>
                    }
                });
                // </layer>
            }
        }.build(nifty));
        nifty.addScreen("Screen_ID3", new ScreenBuilder("rules") {
            {

                controller(new DefaultScreenController()); // Screen properties       

                // <layer>
                layer(new LayerBuilder("Layer") {
                    {
                        childLayoutVertical(); // layer properties, add more...
                        backgroundColor(Color.WHITE);
                        // <panel>
                        panel(new PanelBuilder("Panel_Top") {
                            {
                                childLayoutHorizontal();
                                height("10%");
                                width("100%");
                                image(new ImageBuilder() {
                                    {
                                        controller("mygame.gui.Ctrl");
                                        interactOnRelease("back()");
                                        filename("Interface/back.png");
                                        visibleToMouse(true);
                                    }
                                });

                                image(new ImageBuilder() {
                                    {
                                        filename("Interface/rulez.png");
                                        visibleToMouse(false);
                                    }
                                });

                            }
                        });
                        panel(new PanelBuilder("Panel_Text") {
                            {
                                childLayoutCenter();
                                alignCenter();
                                height("90%");
                                width("75%");

                                // add text
                                text(new TextBuilder() {
                                    {
                                        color(Color.BLACK);
                                        text("1. Aim of the game\n"
                                                + "\n"
                                                + "Each player starts with 501 points. The number of points collected while hitting a board with a dart is subtracted from the \ngiven player's points. The winner is the player who scores exactly 0 points that way.\n"
                                                + "It is a double out game, which means that players must hit a double that makes their score exactly zero to win the game.\n"
                                                + "\n2. Bust\n"
                                                + "\n"
                                                + "In case of a bust the player's score from the previous turn is restored. There is bust if one of the following events arise:\n"
                                                + "\n"
                                                + "    The player scores more points in the active turn, than his current score (subtracting would result in a negative score)\n"
                                                + "    The player has 1 point after subtracting (you cannot score 1 with double out)\n"
                                                + "    The player has 0 point after subtracting but violates the double-out rule \n"
                                                + "\n"
                                                + "3. End of the game\n"
                                                + "\n"
                                                + "Players continue playing until one of them scores 0 points in total. The player who does so, wins the game.\n"
                
                                                + "If none of the players gets to zero in 20 turns, the player with the lower point wins.\n"
      
                                                + "If the scores are equal after 20 turns, the game will continue for another possible 10 turns.\n"
     
                                                + "During these extra turns, the player who gets to zero obviously wins. A player with lower score any time after the 20th turn also wins the match.\n"
              
                                                + "If the scores are equal after 20+10 turns, the match will end in a draw. ");
                                        font("Interface/Fonts/Default.fnt");
                                        //wrap(true);
                                        height("100%");
                                        width("100%");
                                    }
                                });
                            }
                        });
                        // </panel>
                    }
                });
                // </layer>
            }
        }.build(nifty));
        nifty.gotoScreen("Screen_ID"); // start the screen
        //nifty.gotoScreen("Screen_ID2");
    }

    public void refresh() {
        nifty.gotoScreen("Screen_ID");
    }

    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
    }

    public void onStartScreen() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void onEndScreen() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    public void run() {
//        stateManager.detach(this);
//        core.initApp();
//        stateManager.attach(core);
//        System.out.println("ide");
//    }
    public Nifty getNifty() {
        return nifty;
    }
}