/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gd.core;

import java.awt.Image;

import org.jgap.*;
import org.jgap.gp.impl.*;

/**
 * Encapsulates the settings of the genetic algorithm.
 * @author lokee
 */
public class DrawingGPConfiguration
        extends GPConfiguration {

    protected Image target = null;

    public DrawingGPConfiguration(Image target) throws InvalidConfigurationException {
        this.target = target;
        this.setGPFitnessEvaluator(new DeltaGPFitnessEvaluator());
        this.setMaxInitDepth(4);
        this.setPopulationSize(1000);
        this.setMaxCrossoverDepth(8);
        //this.setFitnessFunction(new MathProblem.FormulaFitnessFunction());
        this.setStrictProgramCreation(true);

    }

    /**
     * Get the target image of the drawing problem.
     *
     * @return the value of target
     */
    public Image getTarget() {
        return target;
    }
}
