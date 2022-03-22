package modele.plateau.entites.entitemere;

import modele.plateau.EntiteStatique;
import modele.plateau.Jeu;

public abstract class Ramassable implements EntiteStatique {
    protected Jeu jeu;
    public Ramassable(Jeu _jeu) {
        jeu = _jeu;
    }
}
