# algorithm-interview-coding-tasks
 This repository is for my algorithm Coding interview tasks - Java
 
 | [Test Rendu la Monnaie - Java](https://github.com/FaycelBouslahi/algorithm-interview-coding-tasks/blob/main/src/com/faycel/algo/AlgoRenduMonnaie.java)  | 
|----------|

Une des problématiques rencontrées avec le paiement en espèces est le rendu de monnaie : comment rendre une somme donnée de façon optimale, c'est-à-dire avec le nombre minimal de pièces et billets ? C'est un problème qui se pose à chacun de nous quotidiennement, à fortiori aux caisses automatiques.

Dans cet exercice, on vous demande d’essayer de trouver une solution optimale pour rendre la monnaie, c’est-à-dire avec le nombre minimal de pièces. Le programme, lancé en ligne de commande, prend en paramètre :
-	la première valeur x représente le nombre de pièces qui compose le système
-	ensuite nous avons une liste de x valeurs représentant la valeur des pièces. Ces valeurs seront classées par ordre décroissant.
-	Les valeurs restantes représentent les montants dont on veut le calcul de rendu de monnaie optimal.

![Test Rendu la Monnaie - Java](https://zupimages.net/up/22/10/y3xo.gif)

**Test Unitaire** – Le test unitaire JUnit associé valide différents cas de tests (cas général).

```java
@Test
    public void monnaie_1_3_4_5_montant_6() {
        calculerMonnaie(new long[]{1, 3, 4, 5}, 6L, 2);
    }
```
**Console** – Résultat.

```console
Nombre de pièces de monnaie : 4 Le montant est :6
Nombre de pièces de monnaie de 1 € : 1
Nombre de pièces de monnaie de 5 € : 1
```
