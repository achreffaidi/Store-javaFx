/**
 * Copyright (c) 2019 Gluon
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *     * Neither the name of Gluon, any associated website, nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL GLUON BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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

