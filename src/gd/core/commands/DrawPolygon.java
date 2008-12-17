/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gd.core.commands;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Color;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

/**
 *
 * @author lokee
 */
public class DrawPolygon extends CommandGene {

    public DrawPolygon(GPConfiguration a_conf)
            throws InvalidConfigurationException {
        super(a_conf, 2, CommandGene.VoidClass);
    }

    @Override
    public void execute_void(ProgramChromosome a_chrom, int a_n, Object[] a_args) {
        Graphics2D g2d = getGraphics2D(a_chrom);
        Color color = (Color) a_chrom.execute_object(a_n, 0, a_args);
        Polygon polygon = (Polygon) a_chrom.execute_object(a_n, 1, a_args);

        g2d.setColor(color);
        g2d.fillPolygon(polygon);
    }

    @Override
    public Class getChildType(IGPProgram a_ind, int a_chromNum) {
        if (a_chromNum == 0) {
            return Color.class;
        }
        return Polygon.class;
    }

    public Graphics2D getGraphics2D(ProgramChromosome a_chrom) {
        if ((Graphics2D) a_chrom.getIndividual().getApplicationData() == null) {
            throw new UnsupportedOperationException("Application data not provided to individual.");
        }
        return (Graphics2D) a_chrom.getIndividual().getApplicationData();
    }

    @Override
    public String toString() {
        return "DrawPolygon(&1, &2);";
    }
}
