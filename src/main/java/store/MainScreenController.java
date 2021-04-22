package store;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import store.model.CartItem;
import store.model.CartManager;
import store.model.Product;
import store.utils.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;


public class MainScreenController {

    @FXML
    private MenuBar menubar;

    @FXML
    private ResourceBundle resources;

    @FXML
    private FlowPane flowPane;
    @FXML
    private Label cartCount;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private StackPane cart;

    @FXML
    private RadioButton radioASC;
    @FXML
    private RadioButton radioDES;

    @FXML
    private CheckBox checkbox;

    CartManager cartManager = new CartManager();
    ArrayList<Product> currentList = new ArrayList<>();

    @FXML
    public void initialize() {

        menubar.getMenus().addAll(MenuGenerator.generateMenubar((category)->{
            filterUsingCategory(category);
            return null;
        }).getMenus());

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPadding(new Insets(10,10,10,10));

        flowPane.setMinWidth(1200);
        flowPane.setVgap(20);
        flowPane.setHgap(20);

        currentList.clear();
        currentList.addAll(ProductsManager.getListOfProducts());
        applyFilter();


        cart.setOnMouseClicked(e->{
            navigateToCart();
        });

        radioASC.setOnAction(e->{
            radioDES.setSelected(false);
            applyFilter();
        });
        radioDES.setOnAction(e->{
            radioASC.setSelected(false);
            applyFilter();
        });

        checkbox.setOnAction(e->{
                applyFilter();
        });

    }

    private void applyFilter(){
        List<Product> temp = new ArrayList<>();
        temp.addAll(currentList);
        if(radioASC.isSelected()){
            temp.sort(Product::compareTo);
        }else if(radioDES.isSelected()){
            temp.sort(Product::compareTo);
            Collections.reverse(temp);
        }

        if(checkbox.isSelected()){
            temp = ProductsManager.getListOfProductsWhichHasOffer(temp);
        }

        updateListOfProducts(temp);



    }
    private void updateListOfProducts(List<Product> list) {
        flowPane.getChildren().clear();
        for (Product product: list) {
            flowPane.getChildren().add(generateProductCard(product));
        }
    }

    private void filterUsingCategory(String category) {

        currentList.clear();
        currentList.addAll(ProductsManager.getListOfProducts(category));
        applyFilter();

    }

    public Pane generateProductCard(Product product){


        Pane pane = new Pane();

        pane.setPrefWidth(300);
        pane.setEffect(new DropShadow(20,Color.BLACK));

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_CENTER);

        Label title = new Label(product.getTitle());
        title.setFont(Font.font(18));
        vBox.getChildren().add(title);

        ImageView imageView  = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(300);
        vBox.getChildren().add(imageView);

        Text price = new Text("Price: "+product.getPrice()+" TND");

        price.setFont(Font.font(16));

        vBox.getChildren().add(price);


        if(product.hasOffer()){

            price.setStyle("-fx-strikethrough: true");
            Label offer = new Label("Offer: "+product.getOffer()+" %");
            offer.setFont(Font.font(20));
            Label newPrice = new Label("Price: "+product.getNewPrice()+" TND");
            price.setFont(Font.font(16));
            vBox.getChildren().addAll(offer,newPrice);
        }

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                Image image = new Image(product.getImageUrl());

                Platform.runLater(
                        new Runnable() {
                            @Override
                            public void run() {

                                imageView.setImage(image);

                            }
                        }
                );
            }
        });


        th.setDaemon(true);
        th.start();

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(vBox);
        HBox panex = new HBox();
        panex.setAlignment(Pos.CENTER);
        Button btn = new Button("Buy");
        btn.layoutXProperty().bind(panex.widthProperty().subtract(btn.widthProperty()).divide(2));
        panex.setStyle("-fx-background-color: rgba(255,85,34,0.49);");

        panex.setPrefWidth(300);
        panex.setOpacity(0);
        Button btnDetails = new Button("Details");
        panex.getChildren().add(btnDetails);
        stackPane.getChildren().add(panex);
        pane.getChildren().add(stackPane);
        panex.setOnMouseEntered(e -> {
          panex.setOpacity(1);
        });

        panex.setOnMouseExited(e -> {
            panex.setOpacity(0);

        });

        btnDetails.setOnMouseClicked(event->{

            navigateToProductDetails(product);

        });

        return pane;
    }

    private void navigateToProductDetails(Product product) {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("details.fxml"));

            DetailsController controller = new DetailsController();
            controller.setProduct(product);
            controller.setAddListener((productTitle,unitPrice,count) -> {
                cartManager.addItem(new CartItem(productTitle,unitPrice,count));
                cartCount.setText(""+ cartManager.getCount());
                return null;
            });



            fxmlLoader.setController(controller);

            root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Details: "+ product.getTitle());
            stage.setScene(new Scene(root, 900, 450));
            stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateToCart() {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("cart.fxml"));

            CartController controller = new CartController();
            controller.setCart(cartManager);
            controller.setPaymentListener((count,total)-> {
                afterPayment(count,total);
                return null;
            });

            fxmlLoader.setController(controller);

            root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Cart");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void afterPayment(Integer count, Float total) {
        cartManager.getItems().clear();
        cartCount.setText(""+ cartManager.getItemsCount());
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Successful Payment");
        a.setHeaderText("You just purchased "+ count + " items with a total of "+total);
        a.show();

    }


}

