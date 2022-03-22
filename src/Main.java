
import VueControleur.VueControleur;
import modele.plateau.Jeu;

import javax.swing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        Jeu jeu = new Jeu();
        
        VueControleur vc = new VueControleur(jeu);

        jeu.addObserver(vc);
        
        vc.setVisible(true);
        jeu.start();
    }
}
