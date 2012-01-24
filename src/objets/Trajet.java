package objets;

import java.io.*;
import java.util.Calendar;
import java.util.Date;

import logiqueMetier.Serveur;

/**
 * Cette classe fait partie de l'application d'un système de réservation de
 * moyens de transport en commun.
 * 
 * Cette classe permet de stocker un Trajet. Un trajet est constitué d'une date
 * de départ et d'arrivée, d'un lieu de départ et d'arrivée, d'un véhicule, de
 * places restantes, d'un identifiant.
 * 
 * @author Ceschel Marvin and Bourdin Théo
 * @version 2011.12.04
 */

public class Trajet implements Serializable, Comparable {
    Calendar dateDepart;
    Calendar dateArrivee;
    Ville depart;
    Ville arrivee;
    Vehicule vehicule;
    int placesRestantes;
    int identifiant;

    /**
     * Crée une instance de trajet
     * 
     * @param dateDepart
     *            la date de départ
     * @param dateArrivee
     *            la date d'arrivée
     * @param depart
     *            le lieu de départ
     * @param arrivee
     *            le lieu d'arrivée
     * @param vehicule
     *            le véhicule
     * @param identifiant
     *            l'identifiant
     */
    public Trajet(Calendar dateDepart, Calendar dateArrivee, Ville depart,
            Ville arrivee, Vehicule vehicule, int identifiant) {
        this.dateDepart = dateDepart;
        this.dateArrivee = dateArrivee;
        this.depart = depart;
        this.arrivee = arrivee;
        this.vehicule = vehicule;
        this.placesRestantes = vehicule.getCapacite();
        this.identifiant = identifiant;
    }

    /**
     * Crée une instance de trajet
     * 
     * @param dateDepart
     *            la date de départ
     * @param dateArrivee
     *            la date d'arrivée
     * @param depart
     *            le lieu de départ
     * @param arrivee
     *            le lieu d'arrivée
     * @param vehicule
     *            le véhicule
     * @param identifiant
     *            l'identifiant
     * @param placesRestantes
     *            le nombre de places restantes
     */
    public Trajet(Calendar dateDepart, Calendar dateArrivee, Ville depart,
            Ville arrivee, Vehicule vehicule, int identifiant,
            int placesRestantes) {
        this.dateDepart = dateDepart;
        this.dateArrivee = dateArrivee;
        this.depart = depart;
        this.arrivee = arrivee;
        this.vehicule = vehicule;
        this.placesRestantes = placesRestantes;
        this.identifiant = identifiant;
    }

    /**
     * @return date de départ
     */
    public Calendar getDateDepart() {
        return dateDepart;
    }

    /**
     * @return date d'arrivée
     */
    public Calendar getDateArrivee() {
        return dateArrivee;
    }

    /**
     * @return lieu de départ
     */
    public Ville getDepart() {
        return depart;
    }

    /**
     * @return lieu d'arrivée
     */
    public Ville getArrivee() {
        return arrivee;
    }

    /**
     * @return véhicule du trajet
     */
    public Vehicule getVehicule() {
        return vehicule;
    }

    /**
     * @return nombre de places restantes
     */
    public int getPlacesRestantes() {
        return placesRestantes;
    }

    /**
     * @return identifiant du trajet
     */
    public int getIdentifiant() {
        return identifiant;
    }

    /**
     * @param dateDepart
     *            date de départ
     */
    public void setDateDepart(Calendar dateDepart) {
        this.dateDepart = dateDepart;
    }

    /**
     * @param dateArrivee
     *            date d'arrivée
     */
    public void setDateArrivee(Calendar dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    /**
     * @param depart
     *            lieu de départ
     */
    public void setDepart(Ville depart) {
        this.depart = depart;
    }

    /**
     * @param arrivee
     *            lieu d'arrivée
     */
    public void setArrivee(Ville arrivee) {
        this.arrivee = arrivee;
    }

    /**
     * @param vehicule
     *            véhicule du trajet
     */
    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    /**
     * @param placesRestantes
     *            places restantes
     */
    public void setPlacesRestantes(int placesRestantes) {
        this.placesRestantes = placesRestantes;
    }

    /**
     * @param identifiant
     *            identifiant du trajet
     */
    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    /**
     * Décrémente le nombre de places restantes
     * 
     * @param dec
     *            le décrémenteur
     */
    public void decPlacesRestantes(int dec) {
        this.placesRestantes -= dec;
    }

    /**
     * Vérifie qu'il reste des places pour ce trajet
     * 
     * @param placesVoulues
     *            nombre de places voulues
     * @return true s'il reste des places, false sinon
     */
    public boolean restePlaces(int placesVoulues) {
        return placesRestantes >= placesVoulues;
    }

    @Override
    public String toString() {
        int departMois = dateDepart.get(Calendar.MONTH) + 1;
        int arriveeMois = dateArrivee.get(Calendar.MONTH) + 1;

        return new StringBuffer().append("Voyage (id=").append(identifiant)
                .append(") de ").append(depart.getVille()).append(" à ").append(arrivee.getVille())
                .append(" en ").append(vehicule.getVehicule()).append(" (id=")
                .append(vehicule.getIdentifiant()).append("): \n\t")
                .append("Départ le ").append(dateDepart.get(Calendar.DATE))
                .append("/").append(departMois).append("/")
                .append(dateDepart.get(Calendar.YEAR)).append(" à ")
                .append(dateDepart.get(Calendar.HOUR_OF_DAY)).append(":")
                .append(dateDepart.get(Calendar.MINUTE)).append("\n\t")
                .append("Arrivée le ").append(dateArrivee.get(Calendar.DATE))
                .append("/").append(arriveeMois).append("/")
                .append(dateArrivee.get(Calendar.YEAR)).append(" à ")
                .append(dateArrivee.get(Calendar.HOUR_OF_DAY)).append(":")
                .append(dateArrivee.get(Calendar.MINUTE)).append("\n\t")
                .append("Il reste ").append(placesRestantes)
                .append(" places restantes").toString();
    }

    /**
     * retourner une ligne de texte contenant les informations utiles pour
     * reconstituer un trajet, séparé par des '#'
     * 
     * @return la ligne de texte terminée par un '\n'
     */
    public String print() {
        return new StringBuffer().append(Serveur.calendarToDate(dateDepart))
                .append("#").append(Serveur.calendarToTime(dateDepart))
                .append("#").append(Serveur.calendarToDate(dateArrivee))
                .append("#").append(Serveur.calendarToTime(dateArrivee))
                .append("#").append(depart.getIdentifiant()).append("#").append(arrivee.getIdentifiant())
                .append("#").append(vehicule.getIdentifiant()).append("#")
                .append(placesRestantes).append("#").append(identifiant)
                .append("#").append("\n").toString();
    }

    // compare deux trajets suivant leur date de départ
    @Override
    public int compareTo(Object t) {
        if (this.getDateDepart().after(((Trajet) t).getDateDepart()))
            return 1;
        else if (this.getDateDepart().before(((Trajet) t).getDateDepart()))
            return -1;
        else
            return 0;
    }
}