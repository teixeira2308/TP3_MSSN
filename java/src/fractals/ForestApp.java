package fractals;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessing;
import tools.SubPlot;
import java.util.ArrayList;
import java.util.List;

public class ForestApp implements IProcessing {

    private final double[] window = {-15, 15, 0, 15};
    private final float[] viewport = {0f, 0f, 1f, 1f};
    private SubPlot plt;
    private List<Tree> forest;

    @Override
    public void setup(PApplet parent) {
        plt = new SubPlot(window, viewport, parent.width, parent.height);
        forest = new ArrayList<>();
    }

    @Override
    public void draw(PApplet parent, float dt) {
        float[] bb = plt.getBoundingBox();
        parent.rect(bb[0], bb[1], bb[2], bb[3]);

        for(Tree t : forest) {
            t.grow(dt);
            t.display(parent, plt);
        }
    }

    @Override
    public void keyPressed(PApplet parent) {}

    @Override
    public void mousePressed(PApplet parent) {

        double[] w = plt.getWorldCoord(parent.mouseX, parent.mouseY);
        PVector pos = new PVector((float) w[0], (float) w[1]);
        Tree tree;

        if(parent.random(100) < 50) {
            Rule[] ruleset = new Rule[1];
            ruleset[0] = new Rule('F', "FF+[+F-F-F]-[-F+F+F]");
            tree = new Tree("F", ruleset, pos, .4f,
                    PApplet.radians(22.5f), 3, 0.5f,
                    1f, parent);
        } else {
            Rule[] ruleset = new Rule[2];
            ruleset[0] = new Rule('X', "F+[[X]-X]-F[-FX]+X");
            ruleset[1] = new Rule('F', "FF");
            tree = new Tree("X", ruleset, pos, .4f,
                    PApplet.radians(22.5f), 3, 0.5f,
                    1f, parent);
        }

        forest.add(tree);
    }

    @Override
    public void mouseReleased(PApplet p) {

    }

    @Override
    public void mouseDragged(PApplet p) {

    }
}
