/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package setup;

import aa.*;
import fractals.*;
import physics.FallingBodyApp;
import processing.core.PApplet;

public class Processing extends PApplet {

    private static IProcessing app;
    private int lastUpdateTime;

    public static void main(String[] args) {
        app = new MandelbrotColourApp();
        PApplet.main(Processing.class.getName());
    }

    @Override
    public void settings() {
        size(1280,720);
    }

    @Override
    public void setup() {
        app.setup(this);
        lastUpdateTime = millis();

    }
    @Override  
    public void draw() {
        int now = millis();
        float dt = (now - lastUpdateTime) / 1000.0f;
        app.draw(this, dt);
        lastUpdateTime = now;
    }
    @Override
    public void keyPressed() {
        app.keyPressed(this);
    }
    @Override
    public void mousePressed() {
        app.mousePressed(this);
    }
    @Override
    public void mouseReleased() {app.mouseReleased(this);}
    @Override
    public void mouseDragged() {app.mouseDragged(this);}
}
