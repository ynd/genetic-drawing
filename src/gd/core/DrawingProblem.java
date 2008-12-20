/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gd.core;

import gd.core.commands.PointConstructor;
import gd.core.commands.DrawPolygon;
import gd.core.commands.ColorConstructor;
import gd.core.commands.PolygonConstructor;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPProblem;
import org.jgap.gp.function.SubProgram;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.NOP;
import org.jgap.gp.terminal.Terminal;

/**
 * DrawingProblem ecapsulates the evolution of drawing programs.
 * 
 * @author lokee
 */
public class DrawingProblem
        extends GPProblem {

    public DrawingProblem(GPConfiguration a_conf)
            throws InvalidConfigurationException {
        super(a_conf);
    }

    /**
     * Generates an inital genotype for the drawing problem.
     *
     * @return an initial Genotype
     */
    @Override
    public GPGenotype create() throws InvalidConfigurationException {
        DrawingGPConfiguration conf = (DrawingGPConfiguration) getGPConfiguration();

        Class[] retTypes = {CommandGene.VoidClass};
        Class[][] argTypes = {{}};
        CommandGene[][] nodeSets = {{
                new SubProgram(conf, new Class[]{CommandGene.VoidClass, CommandGene.VoidClass}),
                new SubProgram(conf, new Class[]{CommandGene.VoidClass, CommandGene.VoidClass, CommandGene.VoidClass}),
                new NOP(conf),
                new PointConstructor(conf),
                new PolygonConstructor(conf),
                new ColorConstructor(conf),
                new DrawPolygon(conf),
                new Terminal(conf, CommandGene.FloatClass, 0.0d, 1.0d),
                new Terminal(conf, CommandGene.IntegerClass, 0, conf.getTarget().getWidth(), true, TerminalType.WIDTH.intValue()),
                new Terminal(conf, CommandGene.IntegerClass, 0, conf.getTarget().getHeight(), true, TerminalType.HEIGHT.intValue()),}};
        int[] minDepth = new int[]{5};
        int[] maxDepth = null;
        int maxNodes = 5000;
        boolean[] fullMode = new boolean[]{false};

        return GPGenotype.randomInitialGenotype(conf, retTypes, argTypes, nodeSets,
                minDepth, maxDepth, maxNodes, fullMode, true);
    }

    /**
     * TerminalType helps differentiate the terminals that have different ranges.
     *
     * @author lokee
     */
    public enum TerminalType {

        WIDTH(1),
        HEIGHT(2),;
        private int value;

        public int intValue() {
            return this.value;
        }

        TerminalType(int value) {
            this.value = value;
        }
    }
}
