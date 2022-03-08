package com.faycel.algo;

import java.util.HashMap;
import java.util.Map;

/**
 * Résolution par programmation dynamique et récursivité du calcul de rendu de monnaie.
 * Une des problématiques rencontrées avec le paiement en espèces est le rendu de monnaie :
 *  comment rendre une somme donnée de façon optimale, c’est-à-dire avec le nombre 
 *  minimal de pièces et billets ? C’est un problème qui se pose à 
 *  chacun de nous quotidiennement, à fortiori aux caisses automatiques.
 *  
 *  Dans cet exercice, on vous demande d’essayer de trouver une solution optimale pour 
 *  rendre la monnaie dans un cas général.
 *  
 *  
 * @version 1.0
 * @author  Faycel Bouslahi
 */

public class AlgoRenduMonnaie {
	
	 /**
     * Système de monnaie (exemple de l'Euro : [1, 2, 5, 10, 20, 50, 100, 200, 500])
     */
    private long[] pieces;

    /**
     * Cache des résultats intermédiaires.
     */
    private Map<Long, Map<Integer, RenduMonnaie>> resultatsIntermediaires = new HashMap<>();

    /**
     * Constructeur
     */
    public AlgoRenduMonnaie(long[] pieces) {
        this.pieces = pieces;
    }

    /**
     * Méthode principale exécutant l'algorithme de rendu de monnaie.
     */
    public RenduMonnaie calculerRenduMonnaieOptimal(long montant) {
        initResultatsIntermediaires();
        return calculeMonnaie(montant);
    }

    /**
     * Structure de données renvoyée par l'algorithme et également utilisée pour les calculs intermédiaires.
     */
    public class RenduMonnaie {

        private final long montant;
        /**
         * Pour chaque piece du systeme monétaire, conserve le nombre minimal de pieces à rendre pour le montant donné.
         */
        private final int[] nbPiecesARendre;

        /**
         * Constructeur pour un montant à 0.
         */
        RenduMonnaie() {
            this.montant = 0;
            this.nbPiecesARendre = new int[pieces.length];
        }

        RenduMonnaie(long montant, RenduMonnaie precedent, int indexPiece) {
            this.montant = montant;
            int length = precedent.nbPiecesARendre.length;
            nbPiecesARendre = new int[length];
            System.arraycopy(precedent.nbPiecesARendre, 0, nbPiecesARendre, 0, length);
            nbPiecesARendre[indexPiece]++;
        }

        public int[] getNbPiecesARendre() {
            return nbPiecesARendre;
        }

        public long getMontant() {
            return montant;
        }

        public int nbPieces() {
            int nbPieces = 0;
            for (int i = 0; i < pieces.length; i++) {
                nbPieces += nbPiecesARendre[i];
            }
            return nbPieces;
        }

        public String toString() {
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < nbPiecesARendre.length; i++) {
                if (nbPiecesARendre[i] != 0) {
                    str.append(nbPiecesARendre[i]).append("x").append(pieces[i]).append("€ ");
                }
            }
            return str.toString();
        }
    }

    /**
     * Renvoie l'index de la pièce la plus proche d'un montant.
     */
    private Integer getIndexPieceMax(long montant) {
        Integer pieceMax = null;
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i] <= montant) {
                pieceMax = i;
            }
        }
        return pieceMax;
    }

    /**
     * Initialise le cache avec le 1er résultat : rendre un montant de zéro consiste à ne rendre aucune pièce.
     */
    private void initResultatsIntermediaires() {
        RenduMonnaie renduZero = new RenduMonnaie();
        Map<Integer, RenduMonnaie> zeroMap = new HashMap<>();
        for (int i = 0; i < pieces.length; i++) {
            zeroMap.put(i, renduZero);
        }
        resultatsIntermediaires.put(0L, zeroMap);
    }

    /**
     * Méthode appelée récursivement.
     */
    private RenduMonnaie calculeMonnaie(long montant) {
        Integer indexPieceMax = getIndexPieceMax(montant);
        if (indexPieceMax == null) {
        		return null;
        }
        resultatsIntermediaires.putIfAbsent(montant, new HashMap<>());
        RenduMonnaie meilleurRendu = null;
        int meilleurePiece = -1;
        for (int indexPiece = indexPieceMax; indexPiece >= 0; indexPiece--) {
            long nouveauMontant = montant - pieces[indexPiece];
            resultatsIntermediaires.putIfAbsent(nouveauMontant, new HashMap<>());
            RenduMonnaie renduOptimal = resultatsIntermediaires.get(nouveauMontant).get(indexPiece);
            if (renduOptimal == null) {
                renduOptimal = calculeMonnaie(nouveauMontant);
            }
            if (renduOptimal != null) {
                if ((meilleurRendu == null) || (meilleurRendu.nbPieces() > renduOptimal.nbPieces())) {
                    meilleurRendu = renduOptimal;
                    meilleurePiece = indexPiece;
                }
            }
        }
        if (meilleurRendu != null) {
            meilleurRendu = new RenduMonnaie(montant, meilleurRendu, meilleurePiece);
            resultatsIntermediaires.get(montant).put(meilleurePiece, meilleurRendu);
        }
        return meilleurRendu;
    }
}
