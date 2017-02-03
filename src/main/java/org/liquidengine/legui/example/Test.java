package org.liquidengine.legui.example;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class Test {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 500, 500);
        frame.setLayout(null);

        JButton button = new JButton("JE");
        button.setBounds(10, 10, 100, 100);
        button.addActionListener(e -> System.out.println("HELLO"));
        button.setEnabled(false);
        button.addChangeListener(e -> System.out.println(e));
        button.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                System.out.println(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                System.out.println(e);
            }
        });
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println(e);
            }
        });

        frame.add(button);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
