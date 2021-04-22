
package store;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import store.model.Product;
import store.utils.AddItemInterface;
import store.utils.MenuGenerator;

import java.io.IOException;
import java.util.ResourceBundle;

public class DetailsController {



    @FXML
    private ResourceBundle resources;

    @FXML
    private ImageView imageView;

    @FXML
    private Spinner<Integer> spinner;

    @FXML
    private Text total;
    @FXML
    private Text offer;
    @FXML
    private Text newPrice;
    @FXML
    private Text price;

    @FXML
    private Text description;

    @FXML
    private Label title;

    @FXML
    private Button btnAdd;



    private Product product;

    AddItemInterface<String,Float,Integer> callback;


    @FXML
    public void initialize() {

        initImage(product.getImageUrl());

        description.setText(product.getDescription());
        title.setText(product.getTitle());

        SpinnerValueFactory<Integer> valueFactoryFrom = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        spinner.setValueFactory(valueFactoryFrom);
        price.setText("Price: "+product.getPrice()+" TND");
        total.setText("Total: "+product.getNewPrice()*spinner.getValue());

        if(product.hasOffer()){
            offer.setVisible(true);
            newPrice.setVisible(true);

            offer.setText("Offer: "+product.getOffer()+" %");
            newPrice.setText("New Price: "+product.getNewPrice()+" TND");
            price.setStyle("-fx-strikethrough: true");
        }

        spinner.valueProperty().addListener(e -> {
            total.setText("Total: "+product.getNewPrice()*spinner.getValue());
        });

        btnAdd.setOnMouseClicked(e->{
            callback.apply(product.getTitle(),product.getNewPrice(),spinner.getValue());
            Stage stage = (Stage) btnAdd.getScene().getWindow();
            stage.close();
        });


    }


    public void setProduct(Product product){

        this.product = product;


    }

    public void setAddListener(AddItemInterface<String,Float,Integer> listener ){

        this.callback = listener;


    }

    private void initImage(String imageUrl) {




        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                Image image = new Image(imageUrl);

                Platform.runLater(
                        new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImage(image);
                                imageView.setFitWidth(400);
                                imageView.setPreserveRatio(true);

                            }
                        }
                );
            }
        });

        th.setDaemon(true);
        th.start();

    }


}

