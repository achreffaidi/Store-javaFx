package store.model;

import java.util.ArrayList;
import java.util.List;

public class Category {
    String title;
    List<Category> children;
    public Category(String title, List<Category> children){
        this(title);
        this.children.addAll(children);
    }

    Category(String title){
        this.title  = title;
        children = new ArrayList<>();
    }

    void addCategory(Category category){
        children.add(category);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }
}
