package modele.plateau.entites.entitemere;

import modele.plateau.EntiteStatique;
import modele.plateau.Jeu;

public abstract class ComposantSalle implements EntiteStatique {
    protected Jeu jeu;
    public ComposantSalle(Jeu _jeu) {
        jeu = _jeu;
    }
}
