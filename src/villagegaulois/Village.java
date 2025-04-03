package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
    private String nom;
    private Chef chef;
    private Gaulois[] villageois;
    private int nbVillageois = 0;
    private Marche marche;

    private static class Marche{
        private Etal[] etals;
        
        private Marche(int nbEtal) {
            this.etals = new Etal[nbEtal];
            for(int i = 0; i < nbEtal; i++) {
            	etals[i] = new Etal();
            }
        }
        
        private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
        	Etal etal = this.etals[indiceEtal];
        	if(etal.isEtalOccupe()) {
        		etal.libererEtal();
        	}
        	etal.occuperEtal(vendeur, produit, nbProduit);
        }
        
        private int trouverEtalLibre() {
            for(int i =0 ; i < etals.length;i++) {
                if (etals[i].isEtalOccupe()) {
                }
                else {
                    return i;
                }
            }
            return -1;
            
        }
        
		private Etal[] trouverEtals(String produit) {
			int nbEtalsContenantProduit = 0;

			for (int i = 0; i < this.etals.length; i++) {
				Etal etal = this.etals[i];

				if (etal.isEtalOccupe() && etal.contientProduit(produit)) {
					nbEtalsContenantProduit++;
				}
			}

			int indiceEtalContenantProduit = 0;
			Etal[] etalsContenantProduit = new Etal[nbEtalsContenantProduit];

			for (int i = 0; i < this.etals.length; i++) {
				Etal etal = this.etals[i];

				if (etal.isEtalOccupe() && etal.contientProduit(produit)) {
					etalsContenantProduit[indiceEtalContenantProduit] = etal;
					indiceEtalContenantProduit++;
				}
			}

			return etalsContenantProduit;
		}
        
        Etal trouverVendeur(Gaulois gaulois) {
            for(int i = 0;i<etals.length;i++) {
                if(etals[i].getVendeur() == gaulois) {
                    return etals[i];
                }
            }
            return null;
        }
        
        String afficherMarche(){
            StringBuilder chaineVendeurs = new StringBuilder();
            int nbEtalVides = etals.length;
            for(int i = 0;i<etals.length;i++) {
                if(etals[i].isEtalOccupe()) {
                    chaineVendeurs.append(etals[i].afficherEtal());
                    nbEtalVides -= 1;
                }
            }
            if(nbEtalVides != 0) {
                chaineVendeurs.append("Il reste " + nbEtalVides + " etals non utilises dans le marche.\n");
            }
            return chaineVendeurs.toString();
        }
    }
    
    
    public Village(String nom, int nbVillageoisMaximum, int nbEtal) {
        this.nom = nom;
        villageois = new Gaulois[nbVillageoisMaximum];
        marche = new Marche(nbEtal);
    }

    public String getNom() {
        return nom;
    }

    public void setChef(Chef chef) {
        this.chef = chef;
    }

    public void ajouterHabitant(Gaulois gaulois) {
        if (nbVillageois < villageois.length) {
            villageois[nbVillageois] = gaulois;
            nbVillageois++;
        }
    }

    public Gaulois trouverHabitant(String nomGaulois) {
        if (nomGaulois.equals(chef.getNom())) {
            return chef;
        }
        for (int i = 0; i < nbVillageois; i++) {
            Gaulois gaulois = villageois[i];
            if (gaulois.getNom().equals(nomGaulois)) {
                return gaulois;
            }
        }
        return null;
    }

	public String afficherVillageois() throws VillageSansChefException {
		if(this.chef == null) {
			throw new VillageSansChefException();
		}
		
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
    
    public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
        StringBuilder installationVendeurs = new StringBuilder();
        installationVendeurs.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");

        int etalLibre = marche.trouverEtalLibre();

        if (etalLibre != -1) {
            installationVendeurs.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°" + (etalLibre +1) + ".\n");
            marche.etals[etalLibre].occuperEtal(vendeur, produit, nbProduit);
            this.marche.utiliserEtal(etalLibre, vendeur, produit, nbProduit);
        } else {
            installationVendeurs.append("Il n'y a plus de place\n");
        }
        return installationVendeurs.toString();
    }
    
    public String rechercherVendeursProduit(String produit) {
		StringBuilder messageBuilder = new StringBuilder();

		Etal[] etals = this.marche.trouverEtals(produit);

		switch (etals.length) {
		case 0:
			messageBuilder.append("Il n'y a pas de vendeur qui propose des " + produit + " au marché.\n");
			break;
		case 1:
			messageBuilder.append(
					"Seul le vendeur " + etals[0].getVendeur().getNom() + " propose des " + produit + " au marché.\n");
			break;
		default:
			messageBuilder.append("Les vendeurs qui proposent des " + produit + " sont :\n");

			for (Etal etal : etals) {
				messageBuilder.append(" - " + etal.getVendeur().getNom() + "\n");
			}

			break;
		}

		return messageBuilder.toString();
	}
    
    public Etal rechercherEtal(Gaulois vendeur) {
    	return this.marche.trouverVendeur(vendeur);
    }
    
    public String partirVendeur(Gaulois vendeur) {
    	Etal etal = rechercherEtal(vendeur);
    	if(etal == null) {
    		return null;
    	}
    	else {
    		return etal.libererEtal();
    	}
    }
    
    public String afficherMarche() {
    	return "Le marche du village \"" + this.getNom() + "\" possède plusieurs étals : \n" + this.marche.afficherMarche(); 
    }
}