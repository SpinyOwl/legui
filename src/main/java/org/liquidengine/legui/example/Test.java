package org.liquidengine.legui.example;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Shcherbin Alexander on 10/24/2016.
 */
public class Test {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);

        frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("`frame - mouseClicked");
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("`frame - mousePressed");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("`frame - mouseReleased");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("`frame - mouseEntered");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println("`frame - mouseExited");
            }
        });
//        frame.addComponentListener();
        JButton button = new JButton();
        button.setBounds(100, 100, 200, 200);
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("button - mouseClicked");
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("button - mousePressed");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("button - mouseReleased");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("button - mouseEntered");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println("button - mouseExited");
            }
        });
        frame.add(button);

        frame.setVisible(true);

    }
}
