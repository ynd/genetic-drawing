/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gd.core;

import java.awt.Color;
import java.awt.image.BufferedImage;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;

/**
 * Computes the fitness of a program as the Least-Mean-Sqare distance between 
 * the image it generates and the target image.
 * 
 * @author lokee
 */
public class LMSFitnessFunction
        extends GPFitnessFunction {

    private DrawingGPConfiguration m_conf = null;
    public DrawingGPProgramRunner programRunner = null;

    LMSFitnessFunction(DrawingGPConfiguration a_conf) {
        super();
        m_conf = a_conf;
        programRunner = new DrawingGPProgramRunner(m_conf);
    }

    protected double evaluate(final IGPProgram a_subject) {
        BufferedImage target = m_conf.getTarget();
        BufferedImage generated = programRunner.run(a_subject);

        double lms = 0.0d;
        for (int i = 0; i < target.getWidth(); i++) {
            for (int j = 0; j < target.getHeight(); j++) {
                Color c1 = new Color(target.getRGB(i, j));
                Color c2 = new Color(generated.getRGB(i, j));

                lms += Math.pow(c1.getRed() - c2.getRed(), 2) +
                        Math.pow(c1.getGreen() - c2.getGreen(), 2) +
                        Math.pow(c1.getBlue() - c2.getBlue(), 2);
            }
        }
        return Math.sqrt(lms);
    }
}
