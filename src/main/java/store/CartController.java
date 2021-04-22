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

import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import store.model.CartItem;
import store.model.CartManager;
import store.utils.AddItemInterface;
import store.utils.DoPaymentInterface;

import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class CartController {



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
    private VBox item1;

    @FXML
    private Pane item2;

    @FXML
    private TableView<CartItem> tableView;

    @FXML
    private Button btnGoPayment;

    @FXML
    private Button btnPay;
    @FXML
    private SplitPane splitPane;



    private CartManager cartManager;

    DoPaymentInterface<Integer,Float> callback;


    @FXML
    public void initialize() {

        splitPane.lookupAll(".split-pane-divider").stream()
                .forEach(div ->  div.setMouseTransparent(true) );
        splitPane.setOnDragDetected(
              e ->  splitPane.setDividerPositions(new double[]{0.5})
        );

         final ObservableList<CartItem> data =
                FXCollections.observableArrayList(cartManager.getItems());

        TableColumn<CartItem,String> nameCol = new TableColumn("Name");
        nameCol.setMinWidth(100);

        nameCol.setCellValueFactory(features -> new ReadOnlyObjectProperty<String>() {
            @Override
            public void addListener(InvalidationListener invalidationListener) {

            }

            @Override
            public void removeListener(InvalidationListener invalidationListener) {

            }

            @Override
            public void addListener(ChangeListener<? super String> changeListener) {

            }

            @Override
            public void removeListener(ChangeListener<? super String> changeListener) {

            }

            @Override
            public String get() {
                return features.getValue().titleProperty().get();
            }

            @Override
            public Object getBean() {
                return null;
            }

            @Override
            public String getName() {
                return null;
            }
        });

        TableColumn<CartItem,Float> unitPriceCol = new TableColumn("unitPrice");
        unitPriceCol.setMinWidth(100);
        unitPriceCol.setCellValueFactory(features -> new ReadOnlyObjectProperty<Float>() {
            @Override
            public void addListener(InvalidationListener invalidationListener) {

            }

            @Override
            public void removeListener(InvalidationListener invalidationListener) {

            }

            @Override
            public void addListener(ChangeListener<? super Float> changeListener) {

            }

            @Override
            public void removeListener(ChangeListener<? super Float> changeListener) {

            }

            @Override
            public Float get() {
                return features.getValue().unitPriceProperty().get();
            }

            @Override
            public Object getBean() {
                return null;
            }

            @Override
            public String getName() {
                return null;
            }
        });

        TableColumn<CartItem,Integer> countCol = new TableColumn("count");
        countCol.setMinWidth(100);
        countCol.setCellValueFactory(features -> new ReadOnlyObjectProperty<Integer>() {
            @Override
            public void addListener(InvalidationListener invalidationListener) {

            }

            @Override
            public void removeListener(InvalidationListener invalidationListener) {

            }

            @Override
            public void addListener(ChangeListener<? super Integer> changeListener) {

            }

            @Override
            public void removeListener(ChangeListener<? super Integer> changeListener) {

            }

            @Override
            public Integer get() {
                return features.getValue().countProperty().get();
            }

            @Override
            public Object getBean() {
                return null;
            }

            @Override
            public String getName() {
                return null;
            }
        });

        TableColumn<CartItem,Float> priceCol = new TableColumn("totalPrice");
        priceCol.setMinWidth(100);
        priceCol.setCellValueFactory(features -> new ReadOnlyObjectProperty<Float>() {
            @Override
            public void addListener(InvalidationListener invalidationListener) {

            }

            @Override
            public void removeListener(InvalidationListener invalidationListener) {

            }

            @Override
            public void addListener(ChangeListener<? super Float> changeListener) {

            }

            @Override
            public void removeListener(ChangeListener<? super Float> changeListener) {

            }

            @Override
            public Float get() {
                return features.getValue().totalPriceProperty().get();
            }

            @Override
            public Object getBean() {
                return null;
            }

            @Override
            public String getName() {
                return null;
            }
        });

        TableColumn<CartItem, CartItem> unfriendCol = new TableColumn<>("remove");
        unfriendCol.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        unfriendCol.setCellFactory(param -> new TableCell<CartItem, CartItem>() {
            private final Button deleteButton = new Button("x");

            @Override
            protected void updateItem(CartItem cartItem, boolean empty) {
                super.updateItem(cartItem, empty);

                if (cartItem == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(deleteButton);
                deleteButton.setOnAction(
                        event -> {
                            getTableView().getItems().remove(cartItem);
                            cartManager.getItems().remove(cartItem);
                        }
                );
            }
        });


        tableView.setItems(data);

        tableView.getColumns().clear();
        tableView.getColumns().addAll(nameCol,unitPriceCol,countCol,priceCol,unfriendCol);

        tableView.setEditable(false);

        btnGoPayment.setOnMouseClicked(e->{
            total.setText("Total = "+cartManager.getTotal()+" TND");
            animateToPayment();
        });

        btnPay.setOnMouseClicked(e->{
            callback.apply(cartManager.getItems().size(),cartManager.getTotal());
            Stage stage = (Stage) btnPay.getScene().getWindow();
            stage.close();
        });


    }

    void animateToPayment(){
        Timer animTimer = new Timer();
        animTimer.scheduleAtFixedRate(new TimerTask() {
            int i=0;

            @Override
            public void run() {
                if (i<=95) {
                    item1.setMaxWidth(item1.getWidth()-5);
                    item1.setMinWidth(item1.getWidth()-5);
                    item2.setMinWidth(item2.getWidth()+5);
                } else {
                    this.cancel();
                }
                i++;
            }

        }, 300, 25);
    }


    public void setCart(CartManager cartManager){

        this.cartManager = cartManager;


    }

    public void setPaymentListener(DoPaymentInterface<Integer,Float> listener ){

        this.callback = listener;

    }



}

