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
    private PVector[] vertices;  // pontos do triangulo
    private PVector pontoAtual;  // pt x
    private Random random;
    private int iteracoes;
    private final int maxIteracoes = 10000;

    private int[] cores = new int[3];
    private final int corPontoInicial = 0; // cor preta para o ponto inicial

    private boolean jogoComecou;

    @Override
    public void setup(PApplet p) {
        plt = new SubPlot(window, viewport, p.width, p.height);

        // inicializar vertices do triangula
        vertices = new PVector[3];
        vertices[0] = new PVector(2, 2);    // v A
        vertices[1] = new PVector(8, 2);    // v B
        vertices[2] = new PVector(5, 8);    // v C

        // o ponto inicial nao e mais definido aqui, mas sim em mousePressed
        pontoAtual = new PVector(0, 0);

        random = new Random();
        iteracoes = 0;

        // cores diferentes para cada vertice
        cores[0] = p.color(255, 0, 0);      // vermelho A
        cores[1] = p.color(0, 255, 0);      // verde B
        cores[2] = p.color(0, 0, 255);      // azul C

        p.background(255);
        jogoComecou = false; // O JOGO COMECA EM ESTADO DE ESPERA
    }

    @Override
    public void draw(PApplet p, float dt) {
        p.strokeWeight(5);
        for (int i = 0; i < vertices.length; i++) {
            float[] pixelCoord = plt.getPixelCoord(vertices[i].x, vertices[i].y);
            p.stroke(cores[i]);
            p.point(pixelCoord[0], pixelCoord[1]);
        }
        if (jogoComecou && iteracoes < maxIteracoes) {
            int iteracoesPorFrame = 2;
            for (int i = 0; i < iteracoesPorFrame && iteracoes < maxIteracoes; i++) {
                executarIteracao(p);
                iteracoes++;
            }
        }

        p.fill(0);
        p.textSize(16);
        p.text("jogo do caos", 20, 30);

        if (!jogoComecou) {
            p.text("clique na tela para ESCOLHER O PONTO inicial", 20, 70);
        } else {
            p.text("pressione 'espaco' para reiniciar", 20, 70);
        }

        if (iteracoes >= maxIteracoes) {
            p.text("completo! pressione 'espaco' para reiniciar", 20, 90);
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

    private void desenharPontoInicial(PApplet p) {
        float[] pixelCoord = plt.getPixelCoord(pontoAtual.x, pontoAtual.y);
        p.strokeWeight(5);
        p.stroke(corPontoInicial);
        p.point(pixelCoord[0], pixelCoord[1]);
    }

    @Override
    public void keyPressed(PApplet p) {
        // space para reiniciar
        if (p.key == ' ') {
            setup(p); // setup reseta jogoComecou = false
        }
    }

    @Override
    public void mousePressed(PApplet p) {
        // clique do mouse para definir um novo ponto inicial
        double[] worldCoord = plt.getWorldCoord(p.mouseX, p.mouseY);
        pontoAtual.x = (float) worldCoord[0];
        pontoAtual.y = (float) worldCoord[1];

        // reiniciar contador de interacoes
        iteracoes = 0;
        p.background(255);

        // ATIVAR O JOGO
        jogoComecou = true;

        // desenhar o novo ponto inicial imediatamente
        desenharPontoInicial(p);

        // verificar se ponto esta dent do triagulo
        if (!pontoDentroDoTriangulo(pontoAtual)) {
            p.println("ponto inicial fora do triangulo! pode nao gerar o padrao completo.");
        }
    }

    @Override
    public void mouseReleased(PApplet p) {

    }

    @Override
    public void mouseDragged(PApplet p) {

    }

    // metodo para verificar se um ponto esta dentro do triangulo
    private boolean pontoDentroDoTriangulo(PVector p) {
        PVector a = vertices[0];
        PVector b = vertices[1];
        PVector c = vertices[2];

        float areaTotal = areaTriangulo(a, b, c);

        float area1 = areaTriangulo(p, b, c);
        float area2 = areaTriangulo(a, p, c);
        float area3 = areaTriangulo(a, b, p);

        // tolerancia para erros de precisao
        return Math.abs(areaTotal - (area1 + area2 + area3)) < 0.001;
    }

    private float areaTriangulo(PVector a, PVector b, PVector c) {
        return Math.abs((a.x*(b.y-c.y) + b.x*(c.y-a.y) + c.x*(a.y-b.y))/2.0f);
    }
}