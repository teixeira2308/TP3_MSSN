package fractals;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessing;
import tools.SubPlot;

public class TreeFruitApp implements IProcessing {

    private LSystem Ls;
    private final double[] window = {-10, 10, 0, 15};
    private final float[] viewport = {0f, 0f, 1f, 1f};
    private final PVector startPos = new PVector(0, 0); // Ajuste a posição inicial
    private SubPlot plt;
    private Turtle turtle;

    @Override
    public void setup(PApplet parent) {
        plt = new SubPlot(window, viewport, parent.width, parent.height);
        Rule[] ruleset = new Rule[2];
        ruleset[0] = new Rule('F', "G[+F]-F");
        ruleset[1] = new Rule('G', "GG");

        Ls = new LSystem("F", ruleset);
        turtle = new Turtle(5, PApplet.radians(22.5f));
    }

    @Override
    public void draw(PApplet parent, float dt) {
        float[] bb = plt.getBoundingBox();
        parent.rect(bb[0], bb[1], bb[2], bb[3]);

        turtle.setPose(startPos, PApplet.radians(90), parent, plt);
        turtle.render(Ls, parent, plt);
    }

    @Override
    public void keyPressed(PApplet parent) {}

    @Override
    public void mousePressed(PApplet parent) {
        Ls.nextGen();
        turtle.scaling(0.5f);
        System.out.println("nova geracao: " + Ls.getGen());
        System.out.println("sequencia: " + Ls.getSequence());
    }

    @Override
    public void mouseReleased(PApplet p) {

    }

    @Override
    public void mouseDragged(PApplet p) {

    }
}