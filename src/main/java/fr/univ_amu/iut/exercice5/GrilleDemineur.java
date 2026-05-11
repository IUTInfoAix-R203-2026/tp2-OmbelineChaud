package fr.univ_amu.iut.exercice5;

import java.util.ArrayList;
import java.util.List;

/**
 * Exercice 5 - Grille du démineur.
 *
 * <p>Prend en entrée une grille de caractères {@code ' '} et {@code '*'} (où {@code '*'} représente
 * une mine) et produit la même grille annotée : chaque case vide reçoit le nombre de mines dans ses
 * 8 cases voisines (ou reste un espace si aucune mine n'est adjacente).
 *
 * <p>Exemple :
 *
 * <pre>
 *     +-----+     +-----+
 *     | * * |     |1*3*1|
 *     |  *  |  →  |13*31|
 *     |  *  |     | 2*2 |
 *     |     |     | 111 |
 *     +-----+     +-----+
 * </pre>
 *
 * <p>Cet exercice mêle :
 *
 * <ul>
 *   <li>tableaux 2D irréguliers (chaque ligne est une {@link String})
 *   <li>gestion des bornes (cases au bord de la grille)
 *   <li>validation d'entrée (null, symboles, lignes de longueurs différentes)
 *   <li>{@code ApprovalTests} pour une grille de grande taille (voir les tests)
 * </ul>
 */
public class GrilleDemineur {

  private final List<String> grille;

  /**
   * Construit une grille à partir de sa représentation textuelle.
   *
   * @param grilleInitiale lignes de la grille
   * @throws IllegalArgumentException si la grille est {@code null}, contient un caractère autre que
   *     {@code ' '} ou {@code '*'}, ou si les lignes ont des longueurs différentes
   */
  public GrilleDemineur(List<String> grilleInitiale) {
    if (grilleInitiale == null) throw new IllegalArgumentException("grille vide");
    for (String ligne : grilleInitiale) {
      if (ligne == null) throw new IllegalArgumentException("Ligne nulle");
      if (ligne.length() != grilleInitiale.get(0).length())
        throw new IllegalArgumentException("Lignes de taille différentes");
      for (char c : ligne.toCharArray()) {
        if (c != ' ' && c != '*') throw new IllegalArgumentException("Symbole invalide" + c);
      }
    }
    // TODO exercice 5 : valider l'entrée puis stocker la grille.
    this.grille = grilleInitiale == null ? List.of() : List.copyOf(grilleInitiale);
  }

  private int compterMinesAdjacentes(int ligne, int colonne) {
    int hauteur = grille.size();
    int largeur = grille.get(0).length();
    int mines = 0;
    // on entre dans les lignes
    for (int i = -1; i <= 1; i++) {
      // on entre dans les colonnes
      for (int j = -1; j <= 1; j++) {
        if (i == 0 && j == 0) continue;
        int voisinLigne = ligne + i;
        int voisinColonne = colonne + j;
        if (voisinLigne >= 0
            && voisinLigne < hauteur
            && voisinColonne >= 0
            && voisinColonne < largeur
            && grille.get(voisinLigne).charAt(voisinColonne) == '*') mines++;
      }
    }
    return mines;
  }

  /**
   * Retourne la grille annotée : chaque case vide est remplacée par le nombre de mines adjacentes
   * (ou un espace si aucune), chaque mine reste un {@code '*'}.
   */
  public List<String> getRepresentationAnnotee() {
    List<String> resultat = new ArrayList<>(grille.size());
    if (grille.isEmpty()) return resultat;
    int hauteur = grille.size();
    int largeur = grille.get(0).length();
    // TODO exercice 5 : remplir resultat avec une ligne annotée par ligne d'entrée.
    //
    // Pour chaque case (ligne, col) :
    // - si c'est une mine ('*'), laisser '*'
    // - sinon compter les mines dans les 8 cases voisines (en gérant les bords)
    // - si le compte est > 0, écrire ce chiffre
    // - si le compte est 0, écrire un espace
    //
    // Astuce : une méthode privée compterMinesAdjacentes(int, int) facilite
    // la gestion des bords et rend le code testable.
    // on entre dans les lignes
    for (int i = 0; i < hauteur; i++) {
      String ligne = grille.get(i);
      StringBuilder ligneAnnotee = new StringBuilder(largeur);
      // on entre dans les colonnes
      for (int j = 0; j < largeur; j++) {
        char symbole = ligne.charAt(j);
        if (symbole == '*') ligneAnnotee.append('*');
        else {
          int minesAdjacentes = compterMinesAdjacentes(i, j);
          if (minesAdjacentes > 0) {
            char chiffre = (char) ('0' + minesAdjacentes);
            ligneAnnotee.append(chiffre);
          } else {
            ligneAnnotee.append(' ');
          }
        }
      }
      resultat.add(ligneAnnotee.toString());
    }
    return resultat;
  }
}
