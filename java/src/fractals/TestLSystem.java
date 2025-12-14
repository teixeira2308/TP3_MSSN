package fractals;
import processing.core.PApplet;
import setup.IProcessing;

public class TestLSystem implements IProcessing {

    private Rule[] ruleset;
    private LSystem Ls;

    @Override
    public void setup(PApplet parent) {
        Rule ruleA = new Rule('A', "AB");
        Rule ruleB = new Rule('B', "AC");

        ruleset = new Rule[] {ruleA, ruleB};
        Ls = new LSystem("AC", ruleset);
    }

    @Override
    public void draw(PApplet parent, float dt) {}

    @Override
    public void keyPressed(PApplet parent) {}

    @Override
    public void mousePressed(PApplet parent) {
        System.out.println(Ls.getSequence());
        Ls.nextGen();
    }

    @Override
    public void mouseReleased(PApplet p) {

    }

    @Override
    public void mouseDragged(PApplet p) {

    }
}