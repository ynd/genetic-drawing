/*
 * Copyright 2008 Yann N. Dauphin. All Rights Reserved.
 * Distributed under the terms of the BSD License.
 */
package gd.core;

import java.awt.image.BufferedImage;
import org.jgap.Chromosome;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.IntegerGene;

/**
 * Creates a suitable initial chromosome.
 * @author lokee
 */
public class GAInitialChromosomeFactory {

    public final static int POINTS = 5;

    public static IChromosome create(GAConfiguration a_conf)
            throws InvalidConfigurationException {
        BufferedImage target = a_conf.getTarget();
        IChromosome sample_chromosome = new Chromosome(a_conf);
        Gene[] sample_genes = new Gene[getGenomeSize(a_conf)];

        for (int i = 0; i < getGenomeSize(a_conf);) {
            /* Genes that encode the HSB Color */
            for (int c = 0; c < getNumberOfColorGenesPerPolygon() - 1; c++) {
                sample_genes[i] = new IntegerGene(a_conf, 0, 255);
                sample_genes[i++].setToRandomValue(a_conf.getRandomGenerator());
            }

            /* Gene that encodes the alpha value of the color */
            sample_genes[i] = new IntegerGene(a_conf, 0, 255);
            sample_genes[i++].setAllele(new Integer(0));


            /* Genes that encode the position of the points */
            for (int j = 0; j < POINTS; j++) {
                sample_genes[i] = new IntegerGene(a_conf, 0, target.getWidth());
                sample_genes[i++].setToRandomValue(a_conf.getRandomGenerator());
                sample_genes[i] = new IntegerGene(a_conf, 0, target.getHeight());
                sample_genes[i++].setToRandomValue(a_conf.getRandomGenerator());
            }
        }
        sample_chromosome.setGenes(sample_genes);
        return sample_chromosome;
    }

    public static int getGenomeSize(GAConfiguration a_conf) {
        return getNumberOfPointGenes(a_conf) + getNumberOfColorGenes(a_conf);
    }

    public static int getNumberOfPointGenes(GAConfiguration a_conf) {
        return a_conf.getMaxPolygons() * (getNumberOfGenesPerPoint() * POINTS);
    }

    public static int getNumberOfColorGenes(GAConfiguration a_conf) {
        return a_conf.getMaxPolygons() * getNumberOfColorGenesPerPolygon();
    }

    public static int getNumberOfGenesPerPolygon() {
        return getNumberOfColorGenesPerPolygon() + getNumberOfGenesPerPoint() * POINTS;
    }

    public static int getNumberOfColorGenesPerPolygon() {
        return 4;
    }

    public static int getNumberOfGenesPerPoint() {
        return 2;
    }
}
