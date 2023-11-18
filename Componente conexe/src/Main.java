import javax.swing.*;
import java.util.ArrayList;

public class Main {
    JFrame frame = new JFrame("Componente Conexe");

    Frame Graph=new Frame();

    public Main() {
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //add undo with ctrl+z
        Graph.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control Z"), "undo");
        Graph.getActionMap().put("undo", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                Graph.undo();
            }
        });

        frame.add(Graph);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
