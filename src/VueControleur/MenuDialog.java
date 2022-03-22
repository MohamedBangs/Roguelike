package VueControleur;

import config.CheminVers;
import config.Dim_Enum;
import modele.plateau.Jeu;
import outils.PanelImageFond;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class MenuDialog extends JDialog implements Observer {
    private PanelImageFond contenaire;
    private JButton button[];
    private Dim_Enum.RetourMenu choix;
    private int nombreitem;
    private Jeu jeu;
    private  VueControleur ecranPrincipale;
    private boolean menuInitiale;
    public MenuDialog(Jeu j, VueControleur vueControleur){
        this.jeu=j;
        ecranPrincipale=vueControleur;
        this.menuInitiale=false;
        this.nombreitem=4;
        this.init();
    }
    public MenuDialog(Jeu j, VueControleur vueControleur, boolean menuInitiale){
        this.jeu=j;
        ecranPrincipale=vueControleur;
        this.menuInitiale=menuInitiale;
        this.nombreitem=2;
        this.init();
    }
    private void init(){
        Image image=this.chargeImage(CheminVers.images+"menuaccueil.jpg");
        this.contenaire=new PanelImageFond(image);
        this.setSize(new Dimension(Dim_Enum.largeurMenuDialog,Dim_Enum.hauteurMenuDialog));
        this.setLocationRelativeTo(null);
        this.setContentPane(this.contenaire);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.initMenu();
    }
    private void initMenu(){
        button=new JButton[nombreitem];
        if(this.menuInitiale){
            button[0]=new JButton("NOUVELLE PARTIE");
            button[1]=new JButton("QUITTER");
        }else{
            button[0]=new JButton("CONTINUER");
            button[1]=new JButton("REPRENDRE");
            button[2]=new JButton("NIVEAU");
            button[3]=new JButton("QUITTER");
        }

        this.contenaire.setLayout(new BorderLayout());
        this.contenaire.setBorder(BorderFactory.createRaisedBevelBorder());
        JPanel panel=new JPanel();
        panel.setLayout(new GridLayout(nombreitem,1));
        panel.setBorder(BorderFactory.createRaisedBevelBorder());
        panel.setBackground(Color.black);
        panel.setPreferredSize(new Dimension(Dim_Enum.widthbutton,Dim_Enum.heightbutton*4));
        for(int i=0;i<nombreitem;i++)
        {
            button[i].setPreferredSize(new Dimension(Dim_Enum.widthbutton,Dim_Enum.heightbutton));
            button[i].setFont(new Font("Zapfino", Font.PLAIN, 10));
            button[i].setForeground(Color.BLUE);
            button[i].setBackground(Color.black);
            panel.add(button[i]);
        }
        JLabel label =new JLabel("MENU PRINCIPAL");
        label.setFont(new Font("Zapfino", Font.PLAIN, 15));
        label.setForeground(Color.WHITE);
        Image image=this.chargeImage(CheminVers.images+"menuaccueil.jpg");
        PanelImageFond panellabel=new PanelImageFond(image);
        panellabel.add(label);
        this.contenaire.add(panellabel,BorderLayout.NORTH);
        this.contenaire.add(panel,BorderLayout.EAST);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                affichageMenu();
            }
        });
    }
    private Image chargeImage(String monFichierImage){
        Image image = null;
        try {
            image = ImageIO.read(new File(monFichierImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
    private void affichageMenu(){
        this.setVisible(true);
        for (int i=0;i<nombreitem;i++){
            button[i].addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent event)
                {
                    controleMenu(((JButton)event.getSource()).getText().toString());
                }

            });
        }
    }
    public void controleMenu(String strmenu){
        switch (strmenu){
            case "NOUVELLE PARTIE":
                this.retourNouvelleJeux();
                this.dispose();
                break;
            case "CONTINUER":
                this.retourJeux();
                this.dispose();
                break;
            case "REPRENDRE":
                this.retourJeux();
                this.jeu.reprendrePartie();
                this.dispose();
                break;
            case "QUITTER":
                this.retourJeux();
                this.dispose();
                ecranPrincipale.dispose();
                System.exit(0);
                break;
            default:
                //le niveaux
                this.retourJeux();
                this.dispose();
                ecranPrincipale.showMenu();
                break;
        }
    }
    private void retourJeux(){
        jeu.deleteObservers();
        jeu.addObserver(ecranPrincipale);
        jeu.setStopjeu(false);
    }
    private void retourNouvelleJeux(){
        jeu.deleteObservers();
        jeu.addObserver(ecranPrincipale);
        jeu.setGameover(false);
        jeu.setStopjeu(false);
        ecranPrincipale.setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {}
}
