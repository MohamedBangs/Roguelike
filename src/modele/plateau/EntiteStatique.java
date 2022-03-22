package modele.plateau;

import config.Dim_Enum;

/**
 * Ne bouge pas (murs...)
 */
public interface EntiteStatique {
    public abstract Dim_Enum.EffetCase traversable();
}