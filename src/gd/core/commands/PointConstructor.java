/*
 * Copyright 2008 Yann N. Dauphin. All Rights Reserved.
 * Distributed under the terms of the BSD License.
 */
package gd.core.commands;

import gd.core.DrawingProblem.TerminalType;
import java.awt.Point;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

/**
 * GP Function that builds a java object of the class java.awt.Point.
 * 
 * @author lokee
 */
public class PointConstructor extends CommandGene {

    public PointConstructor(GPConfiguration a_conf)
            throws InvalidConfigurationException {
        super(a_conf, 2, Point.class, 0,
                new int[]{
                    TerminalType.WIDTH.intValue(),
                    TerminalType.HEIGHT.intValue()
                });
    }

    @Override
    public Object execute_object(ProgramChromosome a_chrom, int a_n, Object[] a_args) {
        int x = a_chrom.execute_int(a_n, 0, a_args);
        int y = a_chrom.execute_int(a_n, 1, a_args);

        return new Point(x, y);
    }

    @Override
    public Class getChildType(IGPProgram a_ind, int a_chromNum) {
        return CommandGene.IntegerClass;
    }

    @Override
    public String toString() {
        return "new Point(&1, &2)";
    }
}
