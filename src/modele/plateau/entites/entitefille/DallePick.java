package modele.plateau.entites.entitefille;

import config.Dim_Enum;
import modele.plateau.EntiteStatique;
import modele.plateau.Jeu;
import modele.plateau.entites.entitemere.ComposantSalle;

public class DallePick extends ComposantSalle {
    public DallePick(Jeu _jeu) { super(_jeu); }

    @Override
    public Dim_Enum.EffetCase traversable() {
        return Dim_Enum.EffetCase.DALLE_PICK;
    }
}
