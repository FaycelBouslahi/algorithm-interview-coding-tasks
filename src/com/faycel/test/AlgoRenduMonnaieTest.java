package com.faycel.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import com.faycel.algo.AlgoRenduMonnaie;

class AlgoRenduMonnaieTest {

	@Test
    public void monnaie_2_5_10_montant_1() {
        calculerMonnaie(new long[]{2, 5, 10}, 1L, null);
    }

    @Test
    public void monnaie_2_5_10_montant_5() {
        calculerMonnaie(new long[]{2, 5, 10}, 5L, 1);
    }

    @Test
    public void monnaie_2_5_10_montant_6() {
        calculerMonnaie(new long[]{2, 5, 10}, 6L, 3);
    }

    @Test
    public void monnaie_2_5_10_montant_10() {
        calculerMonnaie(new long[]{2, 5, 10}, 10L, 1);
    }

    @Test
    public void monnaie_2_5_10_montant_37() {
        calculerMonnaie(new long[]{2, 5, 10}, 37L, 5);
    }

    @Test
    public void monnaie_1_3_4_montant_6() {
        calculerMonnaie(new long[]{1, 3, 4}, 6L, 2);
    }

    @Test
    @Ignore
    public void monnaie_1_2_5_10_20_50_100_200_500_montant_1989() {
        calculerMonnaie(new long[]{1, 2, 5, 10, 20, 50, 100, 200, 500}, 1989L, 11);
    }

    private void calculerMonnaie(long[] pieces, long montant, Integer nbPiecesAttendues) {
        AlgoRenduMonnaie algo = new AlgoRenduMonnaie(pieces);
        AlgoRenduMonnaie.RenduMonnaie rendu = algo.calculerRenduMonnaieOptimal(montant);

        if (nbPiecesAttendues == null) {
        	System.out.println("Nombre de pièces de monnaie : " + pieces.length + " Le montant est :" + montant);
        	System.out.println("Erreur : -1 ...");
            System.exit(1);
        }
        assertNotNull(rendu);
        System.out.println("Nombre de pièces de monnaie : " + pieces.length + " Le montant est :" + montant);
        for (int i = 0; i < pieces.length; i++) {
            if (rendu.getNbPiecesARendre()[i] != 0) {
                System.out.println("Nombre de pièces de monnaie" + pieces[i] + " € : " + rendu.getNbPiecesARendre()[i]);
            }
        }

        assertEquals(montant, rendu.getMontant());
        assertEquals(nbPiecesAttendues.intValue(), rendu.nbPieces());
    }


}
