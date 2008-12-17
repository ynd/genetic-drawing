/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gd.core.commands;

import java.awt.Point;
import java.awt.Polygon;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

/**
 *
 * @author lokee
 */
public class PolygonConstructor extends CommandGene {

    public PolygonConstructor(GPConfiguration a_conf) throws InvalidConfigurationException {
        super(a_conf, 5, Polygon.class);
    }

    @Override
    public Object execute_object(ProgramChromosome a_chrom, int a_n, Object[] a_args) {
        Polygon polygon = new Polygon();

        for (int i = 0; i < 5; i++) {
            Point p = (Point) a_chrom.execute_object(a_n, i, a_args);

            polygon.addPoint(p.x, p.y);
        }

        return polygon;
    }

    @Override
    public Class getChildType(IGPProgram a_ind, int a_chromNum) {
        return Point.class;
    }

    @Override
    public String toString() {
        return "new Polygon(&1, &2, &3, &4, &5)";
    }
}
