import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Created by Alexander on 14.01.2017.
 */
public class Test {
    public static void main(String[] args) {
        JFrame frame = new JFrame("test");
        frame.setSize(300, 300);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBounds(10, 10, 50, 50);
        LineBorder border[] = {new LineBorder(Color.black, 1)};
        panel.setBorder(border[0]);
        panel.setLayout(null);
        frame.add(panel);

        JLabel label = new JLabel("Hello");
        label.setBounds(15,5,40,20);
        panel.add(label);

        JButton buttonAdd = new JButton("+");
        buttonAdd.setBounds(70, 10, 50, 50);
        buttonAdd.addActionListener(e -> {
            panel.setBorder(border[0] = new LineBorder(Color.black, border[0].getThickness() + 1));
        });
        frame.add(buttonAdd);

        JButton buttonSub = new JButton("-");
        buttonSub.setBounds(130, 10, 50, 50);
        buttonSub.addActionListener(e -> {
            panel.setBorder(border[0] = new LineBorder(Color.black, border[0].getThickness() - 1));
        });
        frame.add(buttonSub);

        frame.setVisible(true);
    }
}
