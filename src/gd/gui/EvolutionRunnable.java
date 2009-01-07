/*
 * Copyright 2008 Yann N. Dauphin. All Rights Reserved.
 * Distributed under the terms of the BSD License.
 */
package gd.gui;

import gd.core.GAConfiguration;
import gd.core.GAInitialChromosomeFactory;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;

/**
 * Class in charge of actually running the evolution process.
 * @author lokee
 */
public class EvolutionRunnable implements Runnable {

    private GAConfiguration m_conf;
    private GeneticDrawingView m_view;
    private Genotype m_genotype;

    public EvolutionRunnable(GeneticDrawingView a_view, GAConfiguration a_conf) {
        super();
        m_conf = a_conf;
        m_view = a_view;
        m_genotype = null;
    }

    public EvolutionRunnable(GeneticDrawingView a_view, GAConfiguration a_conf,
            Genotype a_genotype) {
        super();
        m_conf = a_conf;
        m_view = a_view;
        m_genotype = a_genotype;
    }

    public void run() {
        try {
            JFreeChart chart = m_view.getChart();
            XYSeriesCollection sc = (XYSeriesCollection) chart.getXYPlot().getDataset();
            XYSeries series = sc.getSeries(0);
            series.clear();

            if (m_genotype == null) {
                int populationSize = m_conf.getPopulationSize();
                Population pop = new Population(m_conf, populationSize);
                for (int i = 0; i < populationSize; i++) {
                    pop.addChromosome(GAInitialChromosomeFactory.create(m_conf));
                }
                m_genotype = new Genotype(m_conf, pop);
            }

            while (m_view.isEvolutionActivated()) {
                m_genotype.evolve();

                if (m_conf.getGenerationNr() % 25 == 0) {
                    IChromosome best = m_genotype.getFittestChromosome();
                    series.add(m_conf.getGenerationNr(), best.getFitnessValue());

                    BufferedImage image =
                            m_conf.getPhenotypeExpresser().express(best);
                    Graphics g = m_view.getFittestDrawingView().getMainPanel().getGraphics();
                    g.drawImage(image, 0, 0, m_view.getFittestDrawingView());

                }
            }
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
