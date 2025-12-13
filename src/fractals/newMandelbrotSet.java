package fractals;

import processing.core.PApplet;
import tools.Complex;
import tools.SubPlot;

public class newMandelbrotSet {
    private int niter;
    private int x0, y0;
    private int dimx, dimy;
    private int colorMethod = 1;

    public newMandelbrotSet(int niter, SubPlot plt) {
        this.niter = niter;
        float[] bb = plt.getBoundingBox();
        x0 = (int) bb[0];
        y0 = (int) bb[1];
        dimx = (int) bb[2];
        dimy = (int) bb[3];
    }

    public void setColorMethod(int colorMethod) {
        this.colorMethod = colorMethod % 7;
    }

    public void display(PApplet p, SubPlot plt) {
        p.colorMode(PApplet.HSB, 360, 100, 100);

        p.loadPixels();

        for (int xx = x0; xx < x0 + dimx; xx++) {
            for (int yy = y0; yy < y0 + dimy; yy++) {
                double[] cc = plt.getWorldCoord(xx, yy);
                Complex c = new Complex(cc);
                Complex x = new Complex();

                int i;
                for (i = 0; i < niter; i++) {
                    x.mult(x);
                    x.add(c);
                    if (x.norm() > 4) {
                        break;
                    }
                }
                int color;
                if (i == niter) color = p.color(0, 0, 0);
                else color = getColorByMethod(i, niter, p, c, xx - x0, yy - y0);
                p.pixels[yy * p.width + xx] = color;
            }
        }
        p.updatePixels();

        p.colorMode(PApplet.RGB, 255);
    }

    private int getColorByMethod(int iterations, int maxIter, PApplet p, Complex c, int pixelX, int pixelY) {
        float ratio = (float) iterations / maxIter;

        switch (colorMethod) {
            case 0:
                int gray = (int) (ratio * 255);
                return p.color(gray, gray, gray);
            case 1:
                float hue = ratio * 360;
                return p.color(hue, 100, 100);
            case 2:
                float redHue = ratio * 60;
                return p.color(redHue, 100, 100);
            case 3:
                float blueHue = 120 + ratio * 120;
                return p.color(blueHue, 100, 100);
            case 4:
                int band = iterations % 6;
                switch (band) {
                    case 0: return p.color(0, 100, 100);
                    case 1: return p.color(60, 100, 100);
                    case 2: return p.color(120, 100, 100);
                    case 3: return p.color(180, 100, 100);
                    case 4: return p.color(240, 100, 100);
                    default: return p.color(300, 100, 100);
                }
            case 5:
                float thermalHue = 240 - ratio * 180;
                if (thermalHue < 0) thermalHue += 360;
                return p.color(thermalHue, 100, 100);
            case 6:
                return (iterations % 2 == 0) ? p.color(0, 0, 100) : p.color(0, 0, 50);
            default:
                return p.color(ratio * 360, 100, 100);
        }
    }
}
