/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gd.core;

import org.jgap.*;
import org.jgap.gp.*;
import org.jgap.gp.function.*;
import org.jgap.gp.impl.*;
import org.jgap.gp.terminal.*;

/**
 * DrawingProblem ecapsulates the evolution of drawing programs.
 * 
 * @author lokee
 */
public class DrawingProblem
        extends GPProblem {

    public DrawingProblem(GPConfiguration conf)
            throws InvalidConfigurationException {
        super(conf);
    }

    /**
     * Generates an inital genotype for the drawing problem.
     *
     * @return an initial Genotype
     */
    @Override
    public GPGenotype create() throws InvalidConfigurationException {
        GPConfiguration conf = getGPConfiguration();
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
