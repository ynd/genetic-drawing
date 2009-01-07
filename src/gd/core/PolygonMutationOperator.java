/*
 * Copyright 2008 Yann Nicolas Dauphin. All Rights Reserved.
 * Distributed under the terms of the BSD License.
 */
package gd.core;

import java.awt.Polygon;
import java.util.List;
import java.util.Vector;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.IGeneticOperatorConstraint;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import org.jgap.RandomGenerator;
import org.jgap.impl.MutationOperator;

/**
 * Translates a polygon of each chromosome in a random direction.
 * @author lokee
 */
public class PolygonMutationOperator
        extends MutationOperator {

    public PolygonMutationOperator(final Configuration a_config,
            final int a_desiredMutationRate)
            throws InvalidConfigurationException {
        super(a_config, a_desiredMutationRate);
    }

    @Override
    public void operate(final Population a_population,
            final List a_candidateChromosomes) {
        if (a_population == null || a_candidateChromosomes == null) {
            // Population or candidate chromosomes list empty:
            // nothing to do.
            // -----------------------------------------------
            return;
        }
        if (getMutationRate() == 0 && getMutationRateCalc() == null) {
            // If the mutation rate is set to zero and dynamic mutation rate is
            // disabled, then we don't perform any mutation.
            // ----------------------------------------------------------------
            return;
        }
        GAConfiguration conf = (GAConfiguration) getConfiguration();
        // Determine the mutation rate. If dynamic rate is enabled, then
        // calculate it using the IUniversalRateCalculator instance.
        // Otherwise, go with the mutation rate set upon construction.
        // -------------------------------------------------------------
        RandomGenerator generator = conf.getRandomGenerator();
        // It would be inefficient to create copies of each Chromosome just
        // to decide whether to mutate them. Instead, we only make a copy
        // once we've positively decided to perform a mutation.
        // ----------------------------------------------------------------
        int size = Math.min(conf.getPopulationSize(),
                a_population.size());
        IGeneticOperatorConstraint constraint = conf.getJGAPFactory().getGeneticOperatorConstraint();
        for (int i = 0; i < size; i++) {
            IChromosome chrom = a_population.getChromosome(i);
            Gene[] genes = chrom.getGenes();
            IChromosome copyOfChromosome = null;
            // For each Chromosome in the population...
            // ----------------------------------------

            /* Find a polygon to mutate */
            int polygon = generator.nextInt(conf.getMaxPolygons());

            boolean mutate = (generator.nextInt(getMutationRate()) == 0);
            if (mutate) {
                // Verify that crossover allowed.
                // ------------------------------
                /**@todo move to base class, refactor*/
                if (constraint != null) {
                    List v = new Vector();
                    v.add(chrom);
                    if (!constraint.isValid(a_population, v, this)) {
                        continue;
                    }
                }
                // Now that we want to actually modify the Chromosome,
                // let's make a copy of it (if we haven't already) and
                // add it to the candidate chromosomes so that it will
                // be considered for natural selection during the next
                // phase of evolution. Then we'll set the gene's value
                // to a random value as the implementation of our
                // "mutation" of the gene.
                // ---------------------------------------------------
                if (copyOfChromosome == null) {
                    // ...take a copy of it...
                    // -----------------------
                    copyOfChromosome = (IChromosome) chrom.clone();
                    // ...add it to the candidate pool...
                    // ----------------------------------
                    a_candidateChromosomes.add(copyOfChromosome);
                    // ...then mutate all its genes...
                    // -------------------------------
                    genes = copyOfChromosome.getGenes();
                }

                Polygon poly = conf.getPhenotypeExpresser().expressPolygon(chrom, polygon);
                int dx = generator.nextInt(conf.getTarget().getWidth() / 8);
                int dy = generator.nextInt(conf.getTarget().getHeight() / 8);
                poly.translate(dx, dy);

                int pos =
                        GAInitialChromosomeFactory.getNumberOfColorGenesPerPolygon() +
                        polygon *
                        GAInitialChromosomeFactory.getNumberOfGenesPerPolygon();
                for (int p = 0; p < GAInitialChromosomeFactory.POINTS; p++){
                    if (poly.xpoints[p] > 0  && poly.xpoints[p] < conf.getTarget().getWidth()) {
                        genes[pos++].setAllele(new Integer(poly.xpoints[p]));
                    }
                    if (poly.ypoints[p] > 0  && poly.ypoints[p] < conf.getTarget().getWidth()) {
                        genes[pos++].setAllele(new Integer(poly.ypoints[p]));
                    }
                }
            }
        }
    }
}