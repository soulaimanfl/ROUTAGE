/*
soulaiman filali 
ahmed tamani
*/

import java.awt.Color;
import java.util.*;


class Commutateur {
    String nom;
    Map<Commutateur, Integer> voisins;
    int x, y;
    Map<Commutateur, Integer> distances;
    Map<Commutateur, Commutateur> precedents;
    boolean marque;
    boolean estSelectionne;
    Color couleur;

    public Commutateur(String nom, int x, int y) {
        this.nom = nom;
        this.voisins = new HashMap<>();
        this.x = x;
        this.y = y;
        this.distances = new HashMap<>();
        this.precedents = new HashMap<>();
        this.marque = false;
        this.estSelectionne = false;
        this.couleur = Color.BLUE;
    }
    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

    public void ajouterVoisin(Commutateur voisin, int ponderation) {
        voisins.put(voisin, ponderation);
        voisin.voisins.put(this, ponderation);
    }

    public String calculerTableRoutage() {
        distances.clear();
        precedents.clear();


        for (Commutateur c : voisins.keySet()) {
            distances.put(c, voisins.get(c));
            precedents.put(c, this);
        }

        marque = true;

        while (true) {
            Commutateur prochain = null;
            int minDistance = Integer.MAX_VALUE;


            for (Commutateur c : voisins.keySet()) {
                if (!c.marque && distances.get(c) < minDistance) {
                    prochain = c;
                    minDistance = distances.get(c);
                }
            }

            if (prochain == null)
                break;

            prochain.marque = true;


            for (Commutateur voisin : prochain.voisins.keySet()) {
                if (!voisin.marque) {
                    int distanceAlternative = distances.get(prochain) + prochain.voisins.get(voisin);
                    if (distanceAlternative < distances.getOrDefault(voisin, Integer.MAX_VALUE)) {
                        distances.put(voisin, distanceAlternative);
                        precedents.put(voisin, prochain);
                    }
                }
            }
        }


        StringBuilder tableRoutage = new StringBuilder();
        tableRoutage.append("Table de routage pour le commutateur ").append(nom).append(":\n");
        for (Commutateur c : distances.keySet()) {
            tableRoutage.append("Vers ").append(c.nom).append(": Distance = ").append(distances.get(c)).append(", Passant par: ").append(precedents.get(c).nom).append("\n");
        }
        return tableRoutage.toString();
    }
}
