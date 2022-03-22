package VueControleur;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BarreProgres extends JFrame
{

    JProgressBar barre_progression;
    static final int MINIMUM=0;
    private int maximum;

    public BarreProgres(int maxi, String tittre)
    {
        this.maximum=maxi;
        this.barre_progression = new JProgressBar();
        this.barre_progression.setMinimum(MINIMUM);
        this.barre_progression.setMaximum(this.maximum);
        JLabel label=new JLabel(tittre);
        label.setForeground(Color.blue);
        label.setFont(new Font("Verdana", Font.PLAIN, 18));

        JPanel panel=new JPanel(new BorderLayout());
        panel.add(label,BorderLayout.NORTH);
        panel.add(this.barre_progression,BorderLayout.CENTER);

        this.setAlwaysOnTop(true);
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setContentPane(panel);
        this.pack();
        this.setVisible(true);
        for (int i = MINIMUM; i <= this.maximum; i++)
        {
            final int avancement=i;
            try
            {

                SwingUtilities.invokeLater(new Runnable( ) {
                    public void run( ) {
                        updateBar(avancement);
                    }
                });
                java.lang.Thread.sleep(100);
            } catch (InterruptedException e) {;}
        }

    }

    public void updateBar(int newValue)
    {
        this.barre_progression.setValue(newValue);
        if(newValue==this.maximum){
            this.dispose();
        }
    }
}
