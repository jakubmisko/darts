package mygame.core;

import mygame.score.Score;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapFont;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;

/**
 * test
 *
 * @author normenhansen
 */
public class Core extends AbstractAppState {

    private Node cross;
    private Spatial dartboard;
    private Spatial actualArrow;
    private Spatial[] arrows;
    private int count;
    private Score score;
    private ActionListener shoot, show;
    private Node rootNode;
    private AssetManager assetManager;
    private Camera cam;
    private AppSettings settings;
    private Node guiNode;
    private InputManager inputManager;
    private BitmapFont guiFont;
    private boolean visibleCross;

    public Core(SimpleApplication app, AppSettings settings, BitmapFont guiFont) {
        visibleCross = false;
        this.rootNode = app.getRootNode();
        this.assetManager = app.getAssetManager();
        this.cam = app.getCamera();
        this.guiNode = app.getGuiNode();
        this.inputManager = app.getInputManager();
        this.settings = settings;
        this.guiFont = guiFont;
        arrows = new Spatial[3];
        count = 0;
        shoot = new ActionListener() {
            public void onAction(String name, boolean isPressed, float tpf) {
                if (name.equals("Shoot") && !isPressed) {
                    throwArrow(actualArrow);
                }
            }
        };

        show = new ActionListener() {
            public void onAction(String name, boolean isPressed, float tpf) {
                if (visibleCross) {
                    guiNode.detachChild(cross);
                    visibleCross = false;
                } else {
                    guiNode.attachChild(cross);
                    visibleCross = true;
                }
            }
        };
    }


    private Spatial initArrow() {
        Spatial arrow = assetManager.loadModel("Models/sipka/sipka.j3o");
        arrowOnMouse(arrow);
        rootNode.attachChild(arrow);
        return arrow;
    }

    private void arrowOnMouse(Spatial arrow) {
        Vector3f pos = cam.getLocation();
        arrow.setLocalTranslation(pos.getX(), pos.getY() - 2, pos.getZ() - 10);
        Quaternion q = new Quaternion(cam.getRotation());
        //q = q.mult(-1);
        Quaternion roll180 = new Quaternion();
        roll180.fromAngleAxis(FastMath.PI, new Vector3f(1, 0, 0));
        q = q.multLocal(roll180);
        arrow.setLocalRotation(q);
    }

    private void initScene() {
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.White);
        Material matFloor = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matFloor.setColor("Color", ColorRGBA.Gray);
        Material matCeiling = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matCeiling.setColor("Color", ColorRGBA.Gray);
        //textures
        Texture wallTexture = assetManager.loadTexture("Textures/room_texture.jpg");
        mat.setTexture("ColorMap", wallTexture);
        Texture floorTexture = assetManager.loadTexture("Textures/floor_texture.jpg");
        matFloor.setTexture("ColorMap", floorTexture);
        Texture ceilingTexture = assetManager.loadTexture("Textures/ceiling_texture.jpg");
        matCeiling.setTexture("ColorMap", ceilingTexture);
        //walls
        Box lWall = new Box(1, 15, 15);
        Geometry geomL = new Geometry("Box", lWall);
        geomL.setMaterial(mat);
        geomL.setLocalTranslation(-19.0f, 0.0f, 0.0f);

        Box bWall = new Box(18, 13, 1);
        Geometry geomB = new Geometry("Box", bWall);
        geomB.setMaterial(mat);
        geomB.setLocalTranslation(0.0f, 0.0f, -14.0f);

        Box fWall = new Box(18, 13, 1);
        Geometry geom = new Geometry("Box", fWall);
        geom.setMaterial(mat);
        geom.setLocalTranslation(0.0f, 0.0f, 14.0f);

        Box rWall = new Box(1, 15, 15);
        Geometry geomR = new Geometry("Box", rWall);
        geomR.setMaterial(mat);
        geomR.setLocalTranslation(19.0f, 0.0f, 0.0f);
        //floor
        Box floor = new Box(18, 1, 15);
        Geometry geomF = new Geometry("Box", floor);
        geomF.setMaterial(matFloor);
        geomF.setLocalTranslation(0.0f, -14.0f, 0.0f);
        //ceiling
        Box ceiling = new Box(18, 1, 15);
        Geometry geomC = new Geometry("Box", ceiling);
        geomC.setMaterial(matCeiling);
        geomC.setLocalTranslation(0.0f, 14.0f, 0.0f);
        //board
        //Spatial dartboard = assetManager.loadModel("Models/board/board.j3o");
        dartboard = assetManager.loadModel("Models/mojterc5/mojterc5.j3o");
        dartboard.scale(2.0f);
        dartboard.setLocalTranslation(0.0f, 2.0f, -13.0f);
        Quaternion roll = new Quaternion();
        roll.fromAngleAxis(FastMath.PI / 2, new Vector3f(1, 0, 0));
        dartboard.setLocalRotation(roll);
        rootNode.attachChild(dartboard);
        actualArrow = initArrow();
        rootNode.attachChild(geom);
        rootNode.attachChild(geomL);
        rootNode.attachChild(geomB);
        rootNode.attachChild(geomR);
        rootNode.attachChild(geomF);
        rootNode.attachChild(geomC);
    }

    private void initLight() {
        //sun
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f).normalizeLocal());
        rootNode.addLight(sun);

    }

    private void initCross() {
        cross = new Node("cross");
        //cross
        Geometry geomv = new Geometry("crossv", new Box(0.25f, 2, 1));
        Geometry geomh = new Geometry("crossh", new Box(2, 0.25f, 1));
        cross.attachChild(geomv);
        cross.attachChild(geomh);
        Material mat3 = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat3.setColor("Color", ColorRGBA.Green);
        geomv.setMaterial(mat3);
        geomv.scale(5f);
        geomv.setLocalTranslation(settings.getWidth() / 2, settings.getHeight() / 2, 0);
        geomh.setMaterial(mat3);
        geomh.scale(5f);
        geomh.setLocalTranslation(settings.getWidth() / 2, settings.getHeight() / 2, 0);

        inputManager.addMapping("Show", new KeyTrigger(KeyInput.KEY_H));
        inputManager.addListener(show, "Show");
        inputManager.addMapping("Shoot",
                new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(shoot, "Shoot");
        //guiNode.attachChild(cross);
    }

    private void initCam() {
        Vector3f pos = new Vector3f(cam.getLocation());
        pos.setY(pos.getY() + 2);
        //pos.setX(pos.getX());
        cam.setLocation(pos);
        // flyCam.setMoveSpeed(20f);
    }

    private void throwArrow(Spatial arrow) {
        CollisionResults results = new CollisionResults();
        Ray ray = new Ray(cam.getLocation(), cam.getDirection());
        dartboard.collideWith(ray, results);
        if (results.size() > 0) {
            Vector3f dest = results.getCollision(0).getContactPoint();
            //while(dest.subtract(arrow.getLocalTranslation()).distance(arrow.getLocalTranslation()) > 0.5){
            if (count == 0) {
                clearBoard();
                actualArrow = initArrow();
                arrows[0] = arrow;
                arrows[0].setLocalTranslation(dest);
            } else if (count == 1) {
                actualArrow = initArrow();
                arrows[1] = arrow;
                arrows[1].setLocalTranslation(dest);
            } else {
                actualArrow = initArrow();
                arrows[2] = arrow;
                arrows[2].setLocalTranslation(dest);
                count = -1;
            }
            count++;
            score.update(results);

        }

    }

    private void clearBoard() {
        if (arrows[2] != null) {
            for (int i = 0; i < 3; i++) {
                arrows[i].removeFromParent();
                arrows[i] = null;
            }
        }
    }
    
    public void initApp() {
        initCross();
        initScene();
        initLight();
        initCam();
//        initScore();
        score = new Score(guiFont, guiNode, settings);

    }

    @Override
    public void update(float tpf) {
        arrowOnMouse(actualArrow);
    }

    @Override
    public void render(RenderManager rm) {
        //TODO: add render code
    }
}
