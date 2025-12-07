package fractals;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessing;
import tools.SubPlot;
import java.util.Random;

public class JogoDoCaosApp implements IProcessing {

    private final double[] window = {2, 8, 2, 8};
    private final float[] viewport = {0.1f, 0.1f, 0.8f, 0.8f};
    private SubPlot plt;
    private PVector[] vertices;
    private PVector pontoAtual;
    private Random random;
    private int iteracoes;
    private final int maxIteracoes = 10000;

    private int[] cores = new int[3];

    @Override
    public void setup(PApplet p) {
        plt = new SubPlot(window, viewport, p.width, p.height);

        vertices = new PVector[3];
        vertices[0] = new PVector(2, 2);
        vertices[1] = new PVector(8, 2);
        vertices[2] = new PVector(5, 8);

        pontoAtual = new PVector(5, 4);

        random = new Random();
        iteracoes = 0;

        cores[0] = p.color(255, 0, 0);
        cores[1] = p.color(0, 255, 0);
        cores[2] = p.color(0, 0, 255);

        p.background(255);
    }

    @Override
    public void draw(PApplet p, float dt) {
        p.strokeWeight(5);
        for (int i = 0; i < vertices.length; i++) {
            float[] pixelCoord = plt.getPixelCoord(vertices[i].x, vertices[i].y);
            p.stroke(cores[i]);
            p.point(pixelCoord[0], pixelCoord[1]);
        }

        int iteracoesPorFrame = 2;
        for (int i = 0; i < iteracoesPorFrame && iteracoes < maxIteracoes; i++) {
            executarIteracao(p);
            iteracoes++;
        }

        p.fill(0);
        p.textSize(16);
        p.text("jogo do caos", 20, 30);
        p.text("clique para reiniciar", 20, 70);

        if (iteracoes >= maxIteracoes) {
            p.text("completo! clique para reiniciar", 20, 90);
        }
    }

    private void executarIteracao(PApplet p) {
        int verticeIndex = random.nextInt(3);
        PVector verticeEscolhido = vertices[verticeIndex];

        pontoAtual.x = pontoAtual.x + 0.5f * (verticeEscolhido.x - pontoAtual.x);
        pontoAtual.y = pontoAtual.y + 0.5f * (verticeEscolhido.y - pontoAtual.y);

        float[] pixelCoord = plt.getPixelCoord(pontoAtual.x, pontoAtual.y);
        p.strokeWeight(3);
        p.stroke(cores[verticeIndex]);
        p.point(pixelCoord[0], pixelCoord[1]);
    }

    @Override
    public void keyPressed(PApplet p) {
        if (p.key == ' ') {
            setup(p);
        }
    }

    @Override
    public void mousePressed(PApplet p) {
        double[] worldCoord = plt.getWorldCoord(p.mouseX, p.mouseY);
        pontoAtual.x = (float) worldCoord[0];
        pontoAtual.y = (float) worldCoord[1];

        iteracoes = 0;
        p.background(255);

        if (!pontoDentroDoTriangulo(pontoAtual)) {
            p.println("Ponto inicial fora do triângulo! Pode não gerar o padrão completo.");
        }
    }

    @Override
    public void keyReleased(PApplet parent) {

    }

    private boolean pontoDentroDoTriangulo(PVector p) {
        PVector a = vertices[0];
        PVector b = vertices[1];
        PVector c = vertices[2];

        float areaTotal = areaTriangulo(a, b, c);

        float area1 = areaTriangulo(p, b, c);
        float area2 = areaTriangulo(a, p, c);
        float area3 = areaTriangulo(a, b, p);

        return Math.abs(areaTotal - (area1 + area2 + area3)) < 0.001;
    }

    private float areaTriangulo(PVector a, PVector b, PVector c) {
        return Math.abs((a.x*(b.y-c.y) + b.x*(c.y-a.y) + c.x*(a.y-b.y))/2.0f);
    }
}