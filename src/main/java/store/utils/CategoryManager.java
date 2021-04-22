package store.utils;

import store.model.Category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CategoryManager {


    public static boolean isUnderCategory(String search, String category){

        List<Category> categories = getCategories();
        Category c = getCategory(category,categories);
        if(c==null) return false;
        return isUnderCategory(search,c);

    }

    static Category getCategory(String category, List<Category> list){

        if(list == null) return  null;
        if(list.isEmpty()) return  null;
        for(Category c : list){
            if(c.getTitle().equals(category)) return  c;
            Category x = getCategory(category,c.getChildren());
            if(x!=null) return  x;
        }
        return null;
    }

    static  boolean isUnderCategory(String search, Category category){
        if(search.equals(category.getTitle())) return true;
        if(category.getChildren()==null) return false;
        if(category.getChildren().isEmpty()) return false;
        for(Category c : category.getChildren()){
            if(isUnderCategory(search,c)) return true;
        }
        return false;
    }



    static List<Category> getCategories(){

        List<Category> list = new ArrayList<>();

        list.add(new Category("Informatique",List.of(
                new Category("Ordinateur Portable", List.of(
                        new Category("Pc Portable", List.of()),
                        new Category("UltraBook", List.of()),
                        new Category("Mac", List.of())
                )),
                new Category("Ordinateur de bureau", List.of(
                        new Category("Pc Portable", List.of()),
                        new Category("UltraBook", List.of()),
                        new Category("Mac", List.of())
                )),
                new Category("Tablettes Tactiles", List.of(
                        new Category("Pc Portable", List.of()),
                        new Category("UltraBook", List.of()),
                        new Category("Mac", List.of())
                )),
                new Category("Stockage", List.of(
                        new Category("Pc Portable", List.of()),
                        new Category("UltraBook", List.of()),
                        new Category("Mac", List.of())
                ))

        )));

        list.add(new Category("Mode",List.of(
                new Category("Mode Homme", List.of(
                        new Category("T-shirts, Polo, Chemises", List.of()),
                        new Category("Pulls, Gilets", List.of())
                )),
                new Category("Mode Femme", List.of(
                        new Category("Robes", List.of()),
                        new Category("Ensembles & Combinaisons", List.of())
                )),
                new Category("Garcons", List.of(
                        new Category("Chaussures", List.of()),
                        new Category("Vetements", List.of())
                ))
        )));

        list.add(new Category("Maison & Bureau",List.of(
                new Category("Electroménager", List.of(
                        new Category("Micro-ondes", List.of()),
                        new Category("Mixeurs & Blenders", List.of())
                )),
                new Category("Cuisine", List.of(
                        new Category("Casseroles & Poeles", List.of()),
                        new Category("Accessoires et ustensiles", List.of())
                )),
                new Category("Maison", List.of(
                        new Category("Meubles", List.of()),
                        new Category("Décoration de maison", List.of())
                ))
        )));

        list.add(new Category("Jardin & Plein air",List.of(
                new Category("Décor extérieur", List.of(
                        new Category("Tables d'extérieur", List.of()),
                        new Category("Chaises d'extérieur", List.of())
                )),
                new Category("Jardinage & entretien", List.of(
                        new Category("Outillage de jardin", List.of()),
                        new Category("Piscines", List.of())
                )),
                new Category("Grillades & Barbecues", List.of(
                        new Category("Barbecues à charbon", List.of()),
                        new Category("Barbecues électriques", List.of())
                ))
        )));

        return list;
    }
}