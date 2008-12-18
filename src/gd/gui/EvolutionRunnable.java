/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gd.gui;

import gd.core.DrawingGPConfiguration;
import gd.core.DrawingGPProgramRunner;
import gd.core.DrawingProblem;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jgap.Configuration;
import org.jgap.InvalidConfigurationException;
import org.jgap.event.GeneticEvent;
import org.jgap.event.GeneticEventListener;
import org.jgap.event.IEventManager;
import org.jgap.gp.GPProblem;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.util.SystemKit;

/**
 * Class in charge of actually running the evolution process.
 * @author lokee
 */
public class EvolutionRunnable implements Runnable {

    private GeneticDrawingView m_view = null;

    public EvolutionRunnable(GeneticDrawingView a_view) {
        super();
        m_view = a_view;
    }

    public void run() {
        Configuration.reset();
        try {
            final DrawingGPConfiguration conf =
                    new DrawingGPConfiguration(m_view.getTargetImage());

            IEventManager eventManager = conf.getEventManager();
            eventManager.addEventListener(GeneticEvent.GPGENOTYPE_EVOLVED_EVENT,
                    new GeneticEventListener() {

                        /**
                         * Updates the chart in the main view.
                         * 
                         */
                        public void geneticEventFired(GeneticEvent a_firedEvent) {
                            GPGenotype genotype = (GPGenotype) a_firedEvent.getSource();
                            int evno = genotype.getGPConfiguration().getGenerationNr();

                            if (evno % 25 == 0 || evno == 1) {
                                double best = genotype.getFittestProgram().getFitnessValue();
                                JFreeChart chart = m_view.getChart();
                                XYSeriesCollection sc = (XYSeriesCollection) chart.getXYPlot().getDataset();
                                XYSeries series = sc.getSeries(0);
                                if (evno == 1) {
                                    series.clear();
                                }
                                series.add(evno, best);
                            }

                        }
                    });
            eventManager.addEventListener(GeneticEvent.GPGENOTYPE_NEW_BEST_SOLUTION,
                    new GeneticEventListener() {

                        private DrawingGPProgramRunner gpProgramRunner = new DrawingGPProgramRunner(conf);

                        /**
                         * Display best solution in fittestChromosomeView's mainPanel
                         */
                        public void geneticEventFired(GeneticEvent a_firedEvent) {
                            GPGenotype genotype = (GPGenotype) a_firedEvent.getSource();
                            IGPProgram best = genotype.getAllTimeBest();
                            BufferedImage image = gpProgramRunner.run(best);

                            Graphics g = m_view.getFittestDrawingView().getMainPanel().getGraphics();
                            g.drawImage(image, 0, 0, m_view.getFittestDrawingView());
                        }
                    });

            GPProblem problem = new DrawingProblem(conf);
            GPGenotype gp = problem.create();
            gp.setVerboseOutput(false);

            while (m_view.isEvolutionActivated()) {
                gp.evolve();
                gp.calcFitness();

                if (gp.getGPConfiguration().getGenerationNr() % 25 == 0) {
                    String freeMB = SystemKit.niceMemory(SystemKit.getFreeMemoryMB());
                    System.out.println("Evolving generation " + (gp.getGPConfiguration().getGenerationNr()) + ", memory free: " + freeMB + " MB");
                }
            }

            problem.showTree(gp.getAllTimeBest(), "media/best.png");
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
