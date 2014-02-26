import java.awt.*;

import javax.swing.*;

public class guidemo {

    public static void main(String[] args) {
        new guidemo();
    }

    public guidemo() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException ex) {
                } catch (InstantiationException ex) {
                } catch (IllegalAccessException ex) {
                } catch (UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new LayoutPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }

        });
    }

    protected  class LayoutPane extends JPanel {

        public LayoutPane() {
            JButton button = new JButton("Find Word");
            JLabel label = new JLabel("Word:");
            JTextField field = new JTextField(12);

            setLayout(new GridBagLayout());

            JPanel fieldPane = new JPanel(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(2, 2, 2, 2);

            fieldPane.add(label, gbc);
            gbc.gridx++;
            fieldPane.add(field, gbc);

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.NORTH;
            gbc.weighty = 1;
            add(fieldPane, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.weighty = 0;
            gbc.anchor = GridBagConstraints.CENTER;

            add(button, gbc);    
        }        
    }
}