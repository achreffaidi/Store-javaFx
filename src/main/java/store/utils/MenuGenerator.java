package store.utils;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import store.model.Category;

public class MenuGenerator {

    private static MenuItem generateMenu(Category category, ClickMenuInterface<String> callBack){



        if(category.getChildren().isEmpty()){
            return new MenuItem(category.getTitle());
        }

        Menu menu = new Menu(category.getTitle());
        menu.setOnAction(e->{
            callBack.apply(menu.getText());
        });
        for(Category c : category.getChildren()){
            menu.getItems().add(generateMenu(c, callBack));
        }

        return menu;
    }


    public static MenuBar generateMenubar(ClickMenuInterface<String> callBack){

        MenuBar menuBar = new MenuBar();

        for(Category category : CategoryManager.getCategories()){
            Menu menu = (Menu) generateMenu(category,callBack);
            menu.setOnAction(e->{
                callBack.apply(menu.getText());
            });
            menuBar.getMenus().add(menu);
        }
        return menuBar;
    }





}
