package fractals;

import processing.core.PApplet;
import setup.IProcessing;
import tools.SubPlot;

public class MandelbrotColourApp implements IProcessing {
    private double[] window = {-2,2,-2,2};
    private float[] viewport = {0,0,1,1};
    private SubPlot plt;
    private newMandelbrotSet ms;
    private int mx0, mx1, my0, my1;
    private boolean isDragging = false;

    private int currentMethod = 1;
    private int maxIterations = 100;


    @Override
    public void setup(PApplet p) {
        plt = new SubPlot(window, viewport, p.width, p.height);
        ms = new newMandelbrotSet(maxIterations, plt);
        ms.setColorMethod(currentMethod);
    }

    @Override
    public void draw(PApplet p, float dt) {
        ms.setColorMethod(currentMethod);
        ms.display(p, plt);

        if (isDragging) {
            showSelection(p);
        }
        showInfo(p);
    }

    @Override
    public void keyPressed(PApplet p) {
        if (p.key >= '0' && p.key <= '6') {
            currentMethod = p.key - '0';
        } else if (p.keyCode == PApplet.UP) {
            maxIterations += 50;
            if (maxIterations > 500) maxIterations = 500;
            ms = new newMandelbrotSet(maxIterations, plt);
        } else if (p.keyCode == PApplet.DOWN) {
            maxIterations -= 50;
            if (maxIterations < 50) maxIterations = 50;
            ms = new newMandelbrotSet(maxIterations, plt);
        }
        else if (p.key == 'r') {
            window[0] = -2;
            window[1] = 2;
            window[2] = -2;
            window[3] = 2;
            plt = new SubPlot(window, viewport, p.width, p.height);
            ms = new newMandelbrotSet(maxIterations, plt);
        }
    }

    @Override
    public void mousePressed(PApplet p) {
        mx0 = mx1 = p.mouseX;
        my0 = my1 = p.mouseY;
        isDragging = true;
    }

    @Override
    public void mouseReleased(PApplet p) {
        if (isDragging && (mx0 != mx1 || my0 != my1)) {
            double[] xy0 = plt.getWorldCoord(mx0, my0);
            double[] xy1 = plt.getWorldCoord(p.mouseX, p.mouseY);

            double xmin = Math.min(xy0[0], xy1[0]);
            double xmax = Math.max(xy0[0], xy1[0]);
            double ymin = Math.min(xy0[1], xy1[1]);
            double ymax = Math.max(xy0[1], xy1[1]);

            if (xmax - xmin > 0.01 && ymax - ymin > 0.01) {
                window[0] = xmin;
                window[1] = xmax;
                window[2] = ymin;
                window[3] = ymax;
                plt = new SubPlot(window, viewport, p.width, p.height);
                ms = new newMandelbrotSet(maxIterations, plt);
            }
        }
        isDragging = false;
    }

    @Override
    public void mouseDragged(PApplet p) {
        mx1 = p.mouseX;
        my1 = p.mouseY;
    }

    private void showSelection(PApplet p) {
        p.pushStyle();
        p.noFill();
        p.stroke(255, 255, 0);
        p.strokeWeight(2);
        p.rect(mx0, my0, mx1 - mx0, my1 - my0);
        p.popStyle();
    }

    private void showInfo(PApplet p) {
        p.pushStyle();
        p.fill(255);
        p.textSize(14);
    }

}