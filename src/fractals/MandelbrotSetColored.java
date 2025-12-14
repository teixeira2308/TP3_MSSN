package fractals;

import processing.core.PApplet;
import tools.Complex;
import tools.SubPlot;

public class MandelbrotSetColored {
    private int niter;
    private int x0, y0;
    private int dimx, dimy;

    public MandelbrotSetColored(int niter, SubPlot plt) {
        this.niter = niter;
        float[] bb = plt.getBoundingBox();
        x0 = (int) bb[0];
        y0 = (int) bb[1];
        dimx = (int) bb[2];
        dimy = (int) bb[3];
    }

    public void display(PApplet p, SubPlot plt) {
        p.loadPixels();

        for (int xx = x0;  xx < x0 + dimx;  xx++) {
            for (int yy = y0;  yy < y0 + dimy;  yy++) {
                double[] cc = plt.getWorldCoord(xx, yy);
                Complex c = new Complex(cc);
                Complex z = new Complex();

                int i;
                for (i = 0; i < niter; i++) {
                    z.mult(z).add(c);
                    if (z.norm() > 2) break;
                }
                int color;

                if (i == niter) {
                    color = p.color(0);
                } else {
                    float hue = p.map(i, 0, niter, 0, 255);
                    color = p.color(hue, 255, 255);
                }
                p.pixels[yy * p.width + xx] = color;
            }
        }
        p.updatePixels();
    }
}
