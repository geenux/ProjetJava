package objets;

/**
 * 
 * @author
 * @version 1.0
 */
public class Bateau extends Vehicule {
    /**
     * Contructeur par défaut. Permet de construire un Vehicule avec un id.
     * 
     * @param
     * @return
     */
    public Bateau(int prix, String vehicule, int capacite, int identifiant) {
        super(prix, vehicule, TypeVehicule.BATEAU, capacite, identifiant);
        classes.add(new ClassesRepas("Premiere_classe", 10));
        classes.add(new ClassesRepas("Standard", 5));
        repas.add(new ClassesRepas("petit_dejeuner", 5));
        repas.add(new ClassesRepas("dejeuner", 10));
        repas.add(new ClassesRepas("dener", 10));
        repas.add(new ClassesRepas("vegetarien", 5));
        repas.add(new ClassesRepas("viande", 5));
        repas.add(new ClassesRepas("poisson", 5));
    }

    public Bateau(int prix, String vehicule, int identifiant) {
        this(prix, vehicule, 1000, identifiant);
    }
    
    public Bateau(String vehicule, int identifiant) {
        this(200, vehicule, 1000, identifiant);
    }

    public Bateau(String vehicule, int capacite, int identifiant) {
        this(200, vehicule, capacite, identifiant);
    }
    
    public boolean avecCouchette() {
        return true;
    }
}
