package modele.plateau.entites.entitefille;

import config.Dim_Enum;
import modele.plateau.Jeu;
import modele.plateau.entites.entitemere.ComposantSalle;

public class CaseNormale extends ComposantSalle {
    public CaseNormale(Jeu _jeu) { super(_jeu); }

    @Override
    public Dim_Enum.EffetCase traversable() {
        return Dim_Enum.EffetCase.NORMAL;
    }


}
