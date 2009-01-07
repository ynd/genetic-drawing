/*
 * Copyright 2008 Yann N. Dauphin. All Rights Reserved.
 * Distributed under the terms of the BSD License.
 */
package gd.core.commands;

import java.awt.Color;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

/**
 * GP Function that builds a java object of the class java.awt.Color.
 * 
 * @author lokee
 */
public class ColorConstructor extends CommandGene {

    public ColorConstructor(GPConfiguration a_conf) throws InvalidConfigurationException {
        super(a_conf, 4, Color.class);
    }

    @Override
    public Object execute_object(ProgramChromosome a_chrom, int a_n, Object[] a_args) {
        float r = a_chrom.execute_float(a_n, 0, a_args);
        float g = a_chrom.execute_float(a_n, 1, a_args);
        float b = a_chrom.execute_float(a_n, 2, a_args);
        float a = a_chrom.execute_float(a_n, 3, a_args);

        return new Color(r, g, b, a);
    }

    @Override
    public Class getChildType(IGPProgram a_ind, int a_chromNum) {
        return CommandGene.FloatClass;
    }

    @Override
    public String toString() {
        return "new Color(&1, &2, &3, &4)";
    }
}
