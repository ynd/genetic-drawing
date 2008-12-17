/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gd.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import org.jgap.gp.IGPProgram;

/**
 *
 * @author lokee
 */
public class DrawingGPProgramRunner {

    private DrawingGPConfiguration m_conf = null;

    public DrawingGPProgramRunner(DrawingGPConfiguration a_conf) {
        m_conf = a_conf;
    }

    public BufferedImage run(final IGPProgram a_subject) {
        BufferedImage target = m_conf.getTarget();
        BufferedImage generated = new BufferedImage(target.getWidth(),
                target.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics g2d = generated.getGraphics();
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, generated.getWidth(), generated.getHeight());
        a_subject.setApplicationData(g2d);

        a_subject.execute_void(0, null);

        return generated;
    }
}
