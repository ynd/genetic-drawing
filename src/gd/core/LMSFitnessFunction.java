/*
 * Copyright 2008 Yann Nicolas Dauphin. All Rights Reserved.
 * Distributed under the terms of the BSD License.
 */
package gd.core;

import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

/**
 * Computes the fitness of a program as the Least-Mean-Sqare distance between 
 * the image it generates and the target image.
 * 
 * @author lokee
 */
public class LMSFitnessFunction
        extends FitnessFunction {

    private final GAConfiguration m_conf;
    private final int[] targetPixels;

    LMSFitnessFunction(GAConfiguration a_conf) {
        super();
        m_conf = a_conf;

        BufferedImage target = m_conf.getTarget();
        targetPixels = new int[target.getWidth() * target.getHeight()];
        PixelGrabber pg = new PixelGrabber(target, 0, 0, target.getWidth(),
                target.getHeight(), targetPixels, 0, target.getWidth());
        try {
            pg.grabPixels();
        } catch (InterruptedException ex) {
            Logger.getLogger(LMSFitnessFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected double evaluate(IChromosome a_chromosome) {
        BufferedImage generated = m_conf.getPhenotypeExpresser().express(a_chromosome);
        final int[] generatedPixels = new int[generated.getWidth() * generated.getHeight()];
        PixelGrabber pg = new PixelGrabber(generated, 0, 0, generated.getWidth(),
                generated.getHeight(), generatedPixels, 0, generated.getWidth());
        try {
            pg.grabPixels();
        } catch (InterruptedException ex) {
            Logger.getLogger(LMSFitnessFunction.class.getName()).log(Level.SEVERE, null, ex);
        }

        long sum = 0;
        for (int i = 0; i < generatedPixels.length && i < targetPixels.length; i++) {
            int c1 = targetPixels[i];
            int c2 = generatedPixels[i];

            int r = ((c1 >> 16) & 0xff) - ((c2 >> 16) & 0xff);
            int g = ((c1 >> 8) & 0xff) - ((c2 >> 8) & 0xff);
            int b = (c1 & 0xff) - (c2 & 0xff);

            sum += r * r + g * g + b * b;
        }

        return Math.sqrt((double) sum);
    }
}
