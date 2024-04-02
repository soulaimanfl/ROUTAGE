/*
soulaiman filali 
ahmed tamani
*/

import java.util.*;


class Reseau {
    List<Commutateur> commutateurs;
    List<Machine> machines;

    public Reseau() {
        this.commutateurs = new ArrayList<>();
        this.machines = new ArrayList<>();
    }

    public void ajouterCommutateur(Commutateur commutateur) {
        commutateurs.add(commutateur);
    }

    public void ajouterMachine(Machine machine) {
        machines.add(machine);
    }

    public void genererGrapheAleatoire() {
        Random random = new Random();
        for (Commutateur commutateur : commutateurs) {
            while (commutateur.voisins.size() < 2) {
                Commutateur voisin = commutateurs.get(random.nextInt(commutateurs.size()));
                if (!commutateur.equals(voisin) && !commutateur.voisins.containsKey(voisin)) {
                    int ponderation = random.nextInt(10) + 1; // Pondération aléatoire entre 1 et 10
                    commutateur.ajouterVoisin(voisin, ponderation);
                }
            }
        }
    }

    // Implémentation de l'algorithme de Dijkstra pour calculer le chemin le plus court
    // entre le commutateur source et le commutateur destination
    // Retourne la liste des commutateurs formant le chemin le plus court
    public List<Commutateur> calculerCheminPlusCourt(Commutateur source, Commutateur destination) {
        Map<Commutateur, Integer> distance = new HashMap<>();
        Map<Commutateur, Commutateur> precedents = new HashMap<>();
        Set<Commutateur> visite = new HashSet<>();

        for (Commutateur commutateur : commutateurs) {
            distance.put(commutateur, Integer.MAX_VALUE);
            precedents.put(commutateur, null);
        }

        distance.put(source, 0);

        while (!visite.contains(destination)) {
            Commutateur actuel = null;
            int distanceMin = Integer.MAX_VALUE;
            for (Commutateur c : commutateurs) {
                if (!visite.contains(c) && distance.get(c) < distanceMin) {
                    actuel = c;
                    distanceMin = distance.get(c);
                }
            }

            if (actuel == null) break;

            visite.add(actuel);

            for (Map.Entry<Commutateur, Integer> entry : actuel.voisins.entrySet()) {
                Commutateur voisin = entry.getKey();
                int poids = entry.getValue();
                if (!visite.contains(voisin)) {
                    int alt = distance.get(actuel) + poids;
                    if (alt < distance.get(voisin)) {
                        distance.put(voisin, alt);
                        precedents.put(voisin, actuel);
                    }
                }
            }
        }

        // Reconstruction du chemin le plus court à partir des précédents
        List<Commutateur> cheminPlusCourt = new ArrayList<>();
        Commutateur commutateur = destination;
        while (precedents.containsKey(commutateur)) {
            cheminPlusCourt.add(commutateur);
            commutateur = precedents.get(commutateur);
        }
        cheminPlusCourt.add(source);

        // Inverser le chemin car il a été construit à partir de la destination
        Collections.reverse(cheminPlusCourt);

        return cheminPlusCourt;
    }
}
