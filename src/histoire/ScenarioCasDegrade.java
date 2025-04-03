package histoire;

import villagegaulois.Etal;
import personnages.Gaulois;

public class ScenarioCasDegrade {
	public static void main(String[] args) {
		Gaulois gaulois = new Gaulois("Gaulois",10);
		Etal etal = new Etal();
		etal.libererEtal();
		
		//Test acheteur null
		try {
			System.err.println("TEST 'etal.acheterProduit' : acheteur null");
			System.err.println("".equals(etal.acheterProduit(1, null)));
		} catch(IllegalStateException e) {
			System.err.println(true);
		} catch(Exception e) {
			System.err.println(false);
		}
		
		//Test quantite suffisante
		try {
			System.err.println("TEST 'etal.acheterProduit' : quantité d'achat insuffisante");
			etal.acheterProduit(0, gaulois);
		} catch(IllegalArgumentException e) {
			System.err.println(true);
		} catch(Exception e) {
			System.err.println(false);
		}
		
		//Test etal non occupe
		try {
			System.err.println("TEST 'etal.acheterProduit' : etal non-occupé");
			etal.acheterProduit(1, gaulois);
		} catch(IllegalStateException e) {
			System.err.println(true);
		} catch(Exception e) {
			System.err.println(false);
		}
		
		
		System.out.println("Fin du test");
	}
}
