/*
 * Copyright 2008 Yann N. Dauphin. All Rights Reserved.
 * Distributed under the terms of the BSD License.
 */
package gd.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import org.jgap.IChromosome;
import org.jgap.impl.IntegerGene;

/**
 * Class that can interpret the information encoded in the chromosomes.
 * @author lokee
 */
public class GAPhenotypeExpresser {

    private GAConfiguration m_conf;
    private BufferedImage m_generated;
    private Graphics m_graphics;

    public GAPhenotypeExpresser(GAConfiguration a_conf) {
        m_conf = a_conf;

        BufferedImage target = m_conf.getTarget();
        m_generated = new BufferedImage(target.getWidth(), target.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        m_graphics = m_generated.getGraphics();
    }

    public BufferedImage express(IChromosome a_chromosome) {
        m_graphics.setColor(Color.white);
        m_graphics.fillRect(0, 0, m_generated.getWidth(), m_generated.getHeight());
        for (int i = 0; i < m_conf.getMaxPolygons(); i++) {
            m_graphics.setColor(expressColor(a_chromosome, i));
            m_graphics.fillPolygon(expressPolygon(a_chromosome, i));
        }

        return m_generated;
    }

    /**
     * Returns an object representing the nth color encoded in the chromosome.
     */
    public Color expressColor(IChromosome a_chromosome, int a_n) {
        int pos = a_n * GAInitialChromosomeFactory.getNumberOfGenesPerPolygon();
        IntegerGene h = (IntegerGene) a_chromosome.getGene(pos);
        IntegerGene s = (IntegerGene) a_chromosome.getGene(pos + 1);
        IntegerGene b = (IntegerGene) a_chromosome.getGene(pos + 2);
        IntegerGene a = (IntegerGene) a_chromosome.getGene(pos + 3);
        Color c = Color.getHSBColor(h.intValue() / 255.0f,
                s.intValue() / 255.0f, b.intValue() / 255.0f);

        return new Color(c.getRed(), c.getGreen(), c.getBlue(), a.intValue());
    }

    /**
     * Returns an object representing the nth polygon encoded in the chromosome.
     */
    public Polygon expressPolygon(IChromosome a_chromosome, int a_n) {
        int[] xpoints = new int[GAInitialChromosomeFactory.POINTS];
        int[] ypoints = new int[GAInitialChromosomeFactory.POINTS];

        int pos = a_n * GAInitialChromosomeFactory.getNumberOfGenesPerPolygon()
                + GAInitialChromosomeFactory.getNumberOfColorGenesPerPolygon();
        for (int j = 0; j < GAInitialChromosomeFactory.POINTS; j++) {
            xpoints[j] = ((IntegerGene) a_chromosome.getGene(pos++)).intValue();
            ypoints[j] = ((IntegerGene) a_chromosome.getGene(pos++)).intValue();
        }
        
        return new Polygon(xpoints, ypoints, GAInitialChromosomeFactory.POINTS);
    }
}
