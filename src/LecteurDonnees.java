import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;



/**
 * Lecteur de cartes au format spectifié dans le sujet.
 * Les données sur les cases, robots puis incendies sont lues dans le fichier,
 * puis simplement affichées.
 * A noter: pas de vérification sémantique sur les valeurs numériques lues.
 *
 * Vous pouvez par exemple ajouter une méthode qui crée et retourne un objet
 * contenant toutes les données lues:
 *    public static DonneesSimulation creeDonnees(String fichierDonnees);
 * Et faire des méthode creeCase(), creeRobot(), ... qui lisent les données,
 * créent les objets adéquats et les ajoutent ds l'instance de
 * DonneesSimulation.
 */
public class LecteurDonnees {

	private static Scanner scanner;

	/**
	 * Constructeur prive; impossible d'instancier la classe depuis l'exterieur
	 * @param fichierDonnees nom du fichier a lire
	 */
	public LecteurDonnees(String fichierDonnees)
		throws FileNotFoundException {
			scanner = new Scanner(new File(fichierDonnees));
			scanner.useLocale(Locale.US);
		}

	public void fermer() {
		this.scanner.close();
	}

	/**
	 * Lit les donnees de la carte et cree l'objet Carte correspodant.
	 * @throws ExceptionFormatDonnees
	 */

	public Carte creerCarte() throws DataFormatException {
		ignorerCommentaires();
		try {
			int nbLignes = scanner.nextInt();
			int nbColonnes = scanner.nextInt();
			int tailleCases = scanner.nextInt();	// en m
			Carte carte = new Carte(nbLignes,nbColonnes,tailleCases);

			for (int lig = 0; lig < nbLignes; lig++) {
				for (int col = 0; col < nbColonnes; col++) {
					creerCase(lig, col,carte);
				}
			}
			return carte;

		} catch (NoSuchElementException e) {
			throw new DataFormatException("Format invalide. "
					+ "Attendu: nbLignes nbColonnes tailleCases");
		}
		// une ExceptionFormat levee depuis lireCase est remontee telle quelle
	}




	/**
	 * Lit les donnees d'une case et cree la case correspondante dans la carte.
	 */

	private void creerCase(int lig, int col,Carte m) throws DataFormatException {
		ignorerCommentaires();
		String chaineNature = new String();
		NatureTerrain nature;

		try {
			chaineNature = scanner.next();
			nature = NatureTerrain.valueOf(chaineNature);

			verifieLigneTerminee();

			m.setCase(lig,col,nature);

		} catch (NoSuchElementException e) {
			throw new DataFormatException("format de case invalide. "
					+ "Attendu: nature altitude [valeur_specifique]");
		}
	}


	/**
	 * Lit et cree les donnees des incendies.
	 * @param m
	 */

	public ArrayList<Incendie> creerIncendies(Carte m) throws DataFormatException {
		ignorerCommentaires();
		try {

			ArrayList<Incendie> fire = new ArrayList<Incendie>();
			int nbIncendies = scanner.nextInt();
			for (int i = 0; i < nbIncendies; i++) {
				fire.add(creerIncendie(i,m));
			}
			return fire;

		} catch (NoSuchElementException e) {
			throw new DataFormatException("Format invalide. "
					+ "Attendu: nbIncendies");
		}
	}


	/**
	 * Lit et cree les donnees du i-eme incendie.
	 * @param i, m
	 */
	public Incendie creerIncendie(int i,Carte m) throws DataFormatException {
		ignorerCommentaires();

		try {
			int lig = scanner.nextInt();
			int col = scanner.nextInt();
			int intensite = scanner.nextInt();
			if (intensite <= 0) {
				throw new DataFormatException("incendie " + i
						+ "nb litres pour eteindre doit etre > 0");
			}
			verifieLigneTerminee();
			Incendie inc = new Incendie(m.getCase(lig,col),intensite);
			return inc;


		} catch (NoSuchElementException e) {
			throw new DataFormatException("format d'incendie invalide. "
					+ "Attendu: ligne colonne intensite");
		}
	}


	/**
	 * Lit et cree les donnees des robots.
	 */
	public Robot[] creerRobots(Carte m) throws DataFormatException {
		ignorerCommentaires();
		try {
			int nbRobots = scanner.nextInt();
			Robot robots[] = new Robot[nbRobots];
			for (int i = 0; i < nbRobots; i++) {
				creerRobot(i,robots,m);
			}
			return robots;

		} catch (NoSuchElementException e) {
			throw new DataFormatException("Format invalide. "
					+ "Attendu: nbRobots");
		}
	}


	/**
	 * Lit et affiche les donnees du i-eme robot.
	 * @param i
	 */
	private void creerRobot(int i,Robot[] robots, Carte m) throws DataFormatException {
		ignorerCommentaires();

		try {
			int lig = scanner.nextInt();
			int col = scanner.nextInt();
			String type = scanner.next();

			// lecture eventuelle d'une vitesse du robot (entier)
			String s = scanner.findInLine("(\\d+)");	// 1 or more digit(s) ?
			// pour lire un flottant:    ("(\\d+(\\.\\d+)?)");

			if (s == null) {
				//valeur par defaut
			} else {
				int vitesse = Integer.parseInt(s);
			}
			verifieLigneTerminee();

			switch (type) {
				case "ROUES":
					robots[i] = new RobotRoues();
					break;
				case "CHENILLES":
					robots[i] = new RobotChenille();
					break;        
				case "DRONE":
					robots[i] = new Drone();
					break;
				case "PATTES":
					robots[i] = new RobotPattes();
					break;
				default:
					;
			}

            robots[i].setPosition(m.getCase(lig,col));
            robots[i].setCarte(m);
            robots[i].setBusy(false);

		} catch (NoSuchElementException e) {
			throw new DataFormatException("format de robot invalide. "
					+ "Attendu: ligne colonne type [valeur_specifique]");
		}
	}




	/** Ignore toute (fin de) ligne commencant par '#' */
	private void ignorerCommentaires() {
		while(scanner.hasNext("#.*")) {
			scanner.nextLine();
		}
	}

	/**
	 * Verifie qu'il n'y a plus rien a lire sur cette ligne (int ou float).
	 * @throws ExceptionFormatDonnees
	 */
	private void verifieLigneTerminee() throws DataFormatException {
		if (scanner.findInLine("(\\d+)") != null) {
			throw new DataFormatException("format invalide, donnees en trop.");
		}
	}
}
