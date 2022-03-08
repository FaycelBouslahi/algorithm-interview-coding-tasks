package com.faycel.algo;

import java.util.HashMap;
import java.util.Map;

/**
 * R�solution par programmation dynamique et r�cursivit� du calcul de rendu de monnaie.
 * Une des probl�matiques rencontr�es avec le paiement en esp�ces est le rendu de monnaie :
 *  comment rendre une somme donn�e de fa�on optimale, c�est-�-dire avec le nombre 
 *  minimal de pi�ces et billets ? C�est un probl�me qui se pose � 
 *  chacun de nous quotidiennement, � fortiori aux caisses automatiques.
 *  
 *  Dans cet exercice, on vous demande d�essayer de trouver une solution optimale pour 
 *  rendre la monnaie dans un cas g�n�ral.
 *  
 *  
 * @version 1.0
 * @author  Faycel Bouslahi
 */

public class AlgoRenduMonnaie {
	
	 /**
     * Syst�me de monnaie (exemple de l'Euro : [1, 2, 5, 10, 20, 50, 100, 200, 500])
     */
    private long[] pieces;

    /**
     * Cache des r�sultats interm�diaires.
     */
    private Map<Long, Map<Integer, RenduMonnaie>> resultatsIntermediaires = new HashMap<>();

    /**
     * Constructeur
     */
    public AlgoRenduMonnaie(long[] pieces) {
        this.pieces = pieces;
    }

    /**
     * M�thode principale ex�cutant l'algorithme de rendu de monnaie.
     */
    public RenduMonnaie calculerRenduMonnaieOptimal(long montant) {
        initResultatsIntermediaires();
        return calculeMonnaie(montant);
    }

    /**
     * Structure de donn�es renvoy�e par l'algorithme et �galement utilis�e pour les calculs interm�diaires.
     */
    public class RenduMonnaie {

        private final long montant;
        /**
         * Pour chaque piece du systeme mon�taire, conserve le nombre minimal de pieces � rendre pour le montant donn�.
         */
        private final int[] nbPiecesARendre;

        /**
         * Constructeur pour un montant � 0.
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
                    str.append(nbPiecesARendre[i]).append("x").append(pieces[i]).append("� ");
                }
            }
            return str.toString();
        }
    }

    /**
     * Renvoie l'index de la pi�ce la plus proche d'un montant.
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
     * Initialise le cache avec le 1er r�sultat : rendre un montant de z�ro consiste � ne rendre aucune pi�ce.
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
     * M�thode appel�e r�cursivement.
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
