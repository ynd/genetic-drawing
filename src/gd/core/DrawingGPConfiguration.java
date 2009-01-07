/*
 * Copyright 2008 Yann N. Dauphin. All Rights Reserved.
 * Distributed under the terms of the BSD License.
 */
package gd.core;

import java.awt.image.BufferedImage;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.impl.DeltaGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.TournamentSelector;

/**
 * Encapsulates the settings of the genetic algorithm.
 * 
 * @author lokee
 */
public class DrawingGPConfiguration
        extends GPConfiguration {

    protected BufferedImage m_target = null;

    public DrawingGPConfiguration(BufferedImage a_target) throws InvalidConfigurationException {
        this.m_target = a_target;
        this.setSelectionMethod(new TournamentSelector(3));
        this.setGPFitnessEvaluator(new DeltaGPFitnessEvaluator());
        this.setFitnessFunction(new LMSFitnessFunction(this));
        this.setPopulationSize(5);
        this.setStrictProgramCreation(false);
        this.setCrossoverProb(0.3f);
        this.setReproductionProb(0.7f);
        this.setNewChromsPercent(0.1f);
        this.setMutationProb(0.9f);
        this.setMaxCrossoverDepth(100);
    }

    /**
     * Get the target image of the drawing problem.
     *
     * @return the value of target
     */
    public BufferedImage getTarget() {
        return m_target;
    }
}
