package VueControleur;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;


import config.CheminVers;
import config.Dim_Enum;
import modele.plateau.*;
import modele.plateau.entites.entitefille.*;
import outils.Chargeur;


/** Cette classe a deux fonctions :
 *  (1) Vue : proposer une représentation graphique de l'application (cases graphiques, etc.)
 *  (2) Controleur : écouter les évènements clavier et déclencher le traitement adapté sur le modèle (flèches direction, etc.))
 *
 */
public class VueControleur extends JFrame implements Observer {
    private Jeu jeu; // référence sur une classe de modèle : permet d'accéder aux données du modèle pour le rafraichissement, permet de communiquer les actions clavier (ou souris)

    private int sizeX; // taille de la grille affichée
    private int sizeY;


    private int pausePartie = 30; // période de changement de partie
    private int nombreInventaire=10;//nombreInventaire afficher

    // icones affichées dans la grille
    private ImageIcon icoHero;
    private ImageIcon icoCaseNormale;
    private ImageIcon icoMur;
    private ImageIcon icoColonne;
    private ImageIcon iconeCle;
    private ImageIcon iconePorteOuverte;
    private ImageIcon iconePorteFerme;
    private ImageIcon iconeCapsuleDeau;
    private ImageIcon iconeDalle;
    private ImageIcon iconeFeu;
    private ImageIcon iconeCoffre;
    private ImageIcon vide;
    private ImageIcon menu;
    private ImageIcon iconeheart;
    private ImageIcon dallepick;
    private ImageIcon iconecasseur;
    private ImageIcon iconeMurCassable;

    private JLabel[][] tabJLabel; // cases graphique (au moment du rafraichissement, chaque case va être associée à une icône, suivant ce qui est présent dans le modèle)

    //Les inventaires
    private JLabel[] tabInventaire;
    //LE MENU
    private JMenuBar menubar;
    private JMenu niveau;
    private JMenu scores;
    private JMenu apropros;

    private JMenuItem item1Apropo;
    private JMenuItem item1Mscore;
    private JRadioButtonMenuItem facile;
    private JRadioButtonMenuItem moyen;
    private JRadioButtonMenuItem difficile;
    private ButtonGroup groupeNiveau=new ButtonGroup();
    private JPanel contenaire;
    private JPanel contenaireInventaire;

    public VueControleur(Jeu _jeu) {
        sizeX = Dim_Enum.largeur;
        sizeY = Dim_Enum.longueur;
        jeu = _jeu;

        chargerLesIcones();
        placerLesComposantsGraphiques();
        this.ajouterEcouteurClavier();
        this.Menu();
    }


    private void ajouterEcouteurClavier() {
        addKeyListener(new KeyAdapter() { // new KeyAdapter() { ... } est une instance de classe anonyme, il s'agit d'un objet qui correspond au controleur dans MVC
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {  // on regarde quelle touche a été pressée
                    case KeyEvent.VK_LEFT : if (jeu.getHeros().gauche()) icoHero = chargerIcone(CheminVers.images+"mario_gauche.png") ; break;
                    case KeyEvent.VK_RIGHT : if ( jeu.getHeros().droite()) icoHero = chargerIcone(CheminVers.images+"mario_droite.png") ;break;
                    case KeyEvent.VK_DOWN : if(jeu.getHeros().bas()) icoHero = chargerIcone(CheminVers.images+"mario_bas.png") ; break;
                    case KeyEvent.VK_UP : if(jeu.getHeros().haut()) icoHero = chargerIcone(CheminVers.images+"mario_haut.png") ; break;
                    case KeyEvent.VK_SPACE : jeu.getHeros().sauter(); /*icoHero = chargerIcone(CheminVers.images+"mario_jump.png") ;*/ break;
                    case KeyEvent.VK_N : jeu.getHeros().useCapsule(); /*icoHero = chargerIcone(CheminVers.images+"mariowater.png") ;*/ break;

                }
            }
        });
    }


    private void chargerLesIcones() {
        icoHero = chargerIcone(CheminVers.images+"mario_droite.png");
        icoCaseNormale = chargerIcone(CheminVers.images+"normale.png");
        icoMur = chargerIcone(CheminVers.images+"mur.jpg");
        iconeCle= chargerIcone(CheminVers.images+"cle.jpg");
        iconePorteOuverte = chargerIcone(CheminVers.images+"porteouverte.png");
        iconePorteFerme= chargerIcone(CheminVers.images+"portefermer.jpg");
        iconeCapsuleDeau= chargerIcone(CheminVers.images+"capsuledeau.png");
        iconeDalle= chargerIcone(CheminVers.images+"dalle.png");
        iconeFeu= chargerIcone(CheminVers.images+"feu.jpg");
        iconeCoffre= chargerIcone(CheminVers.images+"coffre.jpg");
        vide= chargerIcone(CheminVers.images+"vide.jpeg");
        menu= chargerIcone(CheminVers.images+"menu.jpg");
        iconeheart= chargerIcone(CheminVers.images+"vie.png");
        dallepick = chargerIcone(CheminVers.images+"dallepick.png");
        iconecasseur= chargerIcone(CheminVers.images+"cassable.png");
        iconeMurCassable= chargerIcone(CheminVers.images+"murcassable.jpeg");
    }

    private ImageIcon chargerIcone(String urlIcone) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(urlIcone));
        } catch (IOException ex) {
            Logger.getLogger(VueControleur.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        return new ImageIcon(image);
    }

    private void initComposant(){
        JComponent grilleJLabels = new JPanel(new GridLayout(sizeY, sizeX)); // grilleJLabels va contenir les cases graphiques et les positionner sous la forme d'une grille

        tabJLabel = new JLabel[sizeX][jeu.getFinPartie()];

        for (int y = jeu.getDebutPartie(); y < jeu.getFinPartie(); y++) {
            for (int x = 0; x < sizeX; x++) {
                JLabel jlab = new JLabel();
                tabJLabel[x][y] = jlab; // on conserve les cases graphiques dans tabJLabel pour avoir un accès pratique à celles-ci (voir mettreAJourAffichage() )
                grilleJLabels.add(jlab);
            }
        }
        this.contenaire.add(grilleJLabels,BorderLayout.CENTER);
    }
    private void placerLesComposantsGraphiques() {
        this.setTitle("Roguelike");
        this.setSize(Dim_Enum.fenetreWidht, Dim_Enum.fenetreHeight);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // permet de terminer l'application à la fermeture de la fenêtre
        this.contenaire=new JPanel(new BorderLayout());
        this.contenaireInventaire=new JPanel(new GridLayout(1,this.nombreInventaire));
        this.setLayout(new BorderLayout());
        this.getContentPane().add(this.contenaireInventaire, BorderLayout.NORTH);
        this.getContentPane().add(this.contenaire, BorderLayout.CENTER);
        this.initComposant();
        this.initInventaireUI();
    }

    
    /**
     * Il y a une grille du côté du modèle ( jeu.getGrille() ) et une grille du côté de la vue (tabJLabel)
     */
    private void mettreAJourAffichage() {
        for (int x = 0; x < sizeX; x++) {
            for (int y = jeu.getDebutPartie(); y < jeu.getFinPartie(); y++) {
				EntiteStatique e = jeu.getEntite(x, y);
                if (e instanceof Mur) {
                    if(((Mur) e).getTypeMur()== Dim_Enum.EffetCase.MUR_CASSABLE)
                       tabJLabel[x][y].setIcon(iconeMurCassable);
                    else tabJLabel[x][y].setIcon(icoMur);
                }else if (e instanceof Cle) {
                    tabJLabel[x][y].setIcon(iconeCle);
                }else if (e instanceof CaseNormale) {
                    tabJLabel[x][y].setIcon(icoCaseNormale);
                }else if (e instanceof Capsule) {
                    tabJLabel[x][y].setIcon(iconeCapsuleDeau);
                }else if (e instanceof DalleInflammable) {
                    tabJLabel[x][y].setIcon(iconeDalle);
                }else if (e instanceof Feux) {
                    tabJLabel[x][y].setIcon(iconeFeu);
                }else if (e instanceof Coffres) {
                    tabJLabel[x][y].setIcon(iconeCoffre);
                }else if (e instanceof Vide) {
                    tabJLabel[x][y].setIcon(vide);
                }else if (e instanceof Casseur) {
                    tabJLabel[x][y].setIcon(iconecasseur);
                }else if (e instanceof DallePick) {
                    if(jeu.getDallepickeok())
                        tabJLabel[x][y].setIcon(dallepick);
                    else tabJLabel[x][y].setIcon(icoCaseNormale);
                }else if (e instanceof Porte) {
                    if(((Porte) e).getEtatPorte())
                        tabJLabel[x][y].setIcon(iconePorteFerme);
                    else
                        tabJLabel[x][y].setIcon(iconePorteOuverte);
                }
            }
        }
        tabJLabel[jeu.getHeros().getX()][jeu.getHeros().getY()].setIcon(icoHero);
        this.tabInventaire[0].setText(String.valueOf(this.jeu.getHeros().getInventaire().getNombreCle()));
        this.tabInventaire[2].setText(String.valueOf(this.jeu.getHeros().getInventaire().getNombreCapsule()));
        this.tabInventaire[4].setText(String.valueOf(this.jeu.getHeros().getInventaire().getVie()));
        this.tabInventaire[6].setText(String.valueOf(this.jeu.getHeros().getInventaire().getBonuscassable()));
        this.tabInventaire[8].setText("Niv "+String.valueOf(this.jeu.getNiveau()));
        this.tabInventaire[9].setText("Part "+String.valueOf(this.jeu.getPartie()));

    }
    public void setIconeGrille(int x, int y){
        this.tabJLabel[x][y].setIcon(icoCaseNormale);
    }

    private void changementPartie(Observable o){
        new BarreProgres(this.pausePartie, "Chargement partie "+jeu.getPartie());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                contenaire.removeAll();
                initComposant();
                contenaire.revalidate();
                mettreAJourAffichage();
                jeu.setProcessChangePartie(false);
                synchronized (o){
                    o.notify();
                }
            }
        });
    }
    private void changementNiveau(Observable o){
        new BarreProgres(this.pausePartie, "Chargement Niveau "+jeu.getNiveau());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                contenaire.removeAll();
                initComposant();
                contenaire.revalidate();
                mettreAJourAffichage();
                jeu.setProcessChangeNiveau(false);
                synchronized (o){
                    o.notify();
                }
            }
        });
    }
    @Override
    public void update(Observable o, Object arg) {
        //si changent de partie on met en wait le jeux et le reveil dans ;

        if(jeu.getProcessChangePartie()){
            this.changementPartie(o);
        }else if(jeu.getProcessChangeNiveau()){
            this.changementNiveau(o);
        }else if(jeu.getGameover()){
            //le joueur est mort on quite le jeu et affiche la page -> gameover
            this.gameOver();
            this.dispose();
        } else{
            mettreAJourAffichage();
        }


    }
   /* private void timerTremblementHero(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int nbtour=0;
                while (jeu.getHeros().getEntremblement()){
                    try {
                        if(nbtour%2==0)
                            tabJLabel[jeu.getHeros().getX()][jeu.getHeros().getY()].setIcon(icoHero);
                        else tabJLabel[jeu.getHeros().getX()][jeu.getHeros().getY()].setIcon(icoCaseNormale);
                        nbtour++;
                        if(nbtour>=5) jeu.getHeros().setEntremblement(false);
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }*/
    private void gameOver(){
        jeu.deleteObservers();
        jeu.addObserver(new GameOver(jeu,this));
    }
    private void initInventaireUI(){
        this.tabInventaire=new JLabel[this.nombreInventaire*2];
        JPanel []panels=new JPanel[this.nombreInventaire];
        for (int i=0;i<this.nombreInventaire*2;i++){
            this.tabInventaire[i]=new JLabel();
            this.tabInventaire[i].setForeground(Color.blue);
            this.tabInventaire[i].setFont(new Font("Verdana", Font.PLAIN, 25));

        }
        for (int i=0;i<this.nombreInventaire;i++){
            panels[i]=new JPanel();
            if(i<=3)
            panels[i].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.GRAY,Color.GRAY),
                    BorderFactory.createMatteBorder(2,1,2,1,Color.GRAY)));
        }

        this.tabInventaire[0].setText(String.valueOf(this.jeu.getHeros().getInventaire().getNombreCle()));
        this.tabInventaire[1].setIcon(this.iconeCle);
        this.tabInventaire[2].setText(String.valueOf(this.jeu.getHeros().getInventaire().getNombreCapsule()));
        this.tabInventaire[3].setIcon(this.iconeCapsuleDeau);
        this.tabInventaire[4].setText(String.valueOf(this.jeu.getHeros().getInventaire().getVie()));
        this.tabInventaire[5].setIcon(this.iconeheart);
        this.tabInventaire[8].setText("Niv "+String.valueOf(this.jeu.getNiveau()));
        this.tabInventaire[9].setText("Part "+String.valueOf(this.jeu.getPartie()));
        this.tabInventaire[6].setText(String.valueOf(this.jeu.getHeros().getInventaire().getBonuscassable()));
        this.tabInventaire[7].setIcon(this.iconecasseur);

        int j=-1;
        for (int i=0;i<this.nombreInventaire*2;i++){
            if(i%2==0) j++;
            panels[j].add(this.tabInventaire[i]);
        }
        for (int i=0;i<this.nombreInventaire;i++){
            this.contenaireInventaire.add(panels[i]);
        }
        JPanel panelMenu=new JPanel();
        panelMenu.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.GRAY,Color.GRAY),
                BorderFactory.createMatteBorder(2,1,2,1,Color.GRAY)));
        JLabel labelmenu=new JLabel(menu);
        panelMenu.add(labelmenu);
        labelmenu.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                jeu.setStopjeu(true);
                jeu.deleteObservers();
                changeVue();
            }
        });
        this.contenaireInventaire.add(panelMenu);

    }
    private void changeVue(){
        MenuDialog menuDialog=new MenuDialog(jeu,this);
        jeu.addObserver(menuDialog);
    }


    private void initMenu() {
        this.menubar=new JMenuBar();
        this.niveau =new JMenu("Niveau");
        this.scores =new JMenu("Meilleur Score");
        this.apropros=new JMenu("A Propos");
        this.item1Apropo=new JMenuItem("?");
        this.item1Mscore=new JMenuItem("Meilleur Scores");
        this.facile=new JRadioButtonMenuItem("Facile");
        this.difficile=new JRadioButtonMenuItem("Difficile");
        this.moyen=new JRadioButtonMenuItem("Moyen");
        this.groupeNiveau=new ButtonGroup();
    }
    public void showMenu(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                niveau.setSelected(true);
                niveau.doClick();
            }
        });
    }
    public void Menu()
    {
        this.initMenu();
        //LE MENUBAR
        ActionMenu actionmenu=new ActionMenu();
        this.facile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,KeyEvent.CTRL_DOWN_MASK));
        this.moyen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,KeyEvent.CTRL_DOWN_MASK));
        this.difficile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,KeyEvent.CTRL_DOWN_MASK));
        this.facile.addActionListener(actionmenu);
        this.moyen.addActionListener(actionmenu);
        this.difficile.addActionListener(actionmenu);

        this.facile.setSelected(true);
        this.groupeNiveau.add(this.facile);
        this.groupeNiveau.add(this.moyen);
        this.groupeNiveau.add(this.difficile);

        this.niveau.setMnemonic('N');
        this.niveau.add(this.facile);
        this.niveau.add(this.moyen);
        this.niveau.add(this.difficile);

        this.item1Apropo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,KeyEvent.CTRL_DOWN_MASK));
        this.item1Apropo.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {

                JOptionPane op=new JOptionPane();
                op.showMessageDialog(null, Chargeur.getTexte(CheminVers.apropos),
                        "A PROPOS",JOptionPane.INFORMATION_MESSAGE,
                        new ImageIcon(CheminVers.images+"mario_droite.png"));
            }
        });
        this.apropros.setMnemonic('A');
        this.apropros.add(this.item1Apropo);

        this.item1Mscore.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,KeyEvent.CTRL_DOWN_MASK));
        this.item1Mscore.addActionListener(new ActionListener()
        {
            JOptionPane op=new JOptionPane();
            public void actionPerformed(ActionEvent event)
            {
                op.showMessageDialog(null,Chargeur.getTexte(CheminVers.meilleurscore),
                        "MEILLEUR SCORE",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("mur.jpg"));
            }
        });
        this.scores.setMnemonic('S');
        this.scores.add(this.item1Mscore);

        this.menubar.add(this.niveau);
        this.menubar.add(this.scores);
        this.menubar.add(this.apropros);

        this.setJMenuBar(this.menubar);

    }
    class ActionMenu implements ActionListener
    {

        public void actionPerformed(ActionEvent event)
        {
            if (((JRadioButtonMenuItem)event.getSource())==facile)
            {
                jeu.getHeros().getInventaire().initVie(3);
            }
            else if (((JRadioButtonMenuItem)event.getSource())==moyen)
            {
                jeu.getHeros().getInventaire().initVie(2);
            }
            else if (((JRadioButtonMenuItem)event.getSource())==difficile)
            {
                jeu.getHeros().getInventaire().initVie(1);
            }
            else
            {
                /*valeur=36000;
                Revalider();*/
            }

        }

    }

}
