package com.example.demo.controller;

import com.example.demo.model.entity.Product;
import com.example.demo.service.FormService;
import com.example.demo.service.ProductService;
import com.example.demo.service.StoreUserService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MainFormController implements Initializable {

    private FormService formService;
    private ProductService productService;
    private void showPurchaseDialog(Product product) {
        try {
            // Загружаем FXML и создаем контроллер
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/purchase/purchaseProductForm.fxml"));
            Parent root = loader.load();

            // Получаем контроллер после загрузки FXML
            PurchaseProductController controller = loader.getController();

            // Передаем зависимость вручную
            controller.setProduct(product);
            controller.setProductService(productService);

            // Создаем и показываем модальное окно
            Stage stage = new Stage();
            stage.setTitle("Покупка товара");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);  // Делает окно модальным
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка при загрузке FXML: " + e.getMessage());
        }
    }


    @FXML
    private VBox vbMainFormRoot;
    @FXML
    private TableView<Product> tvListProducts;
    @FXML
    private TableColumn<Product, String> tcId;
    @FXML
    private TableColumn<Product, String> tcName;
    @FXML
    private TableColumn<Product, String> tcBrand;
    @FXML
    private TableColumn<Product, String> tcReleaseYear;
    @FXML
    private TableColumn<Product, String> tcQuantity;
    @FXML
    private TableColumn<Product, String> tcCount;
    @FXML
    private HBox hbEditProduct;

    public MainFormController(FormService formService, ProductService productService) {
        this.formService = formService;
        this.productService = productService;
    }

    @FXML
    private void showEditProductForm() {
        formService.loadEditProductForm(tvListProducts.getSelectionModel().getSelectedItem());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Добавляем меню в начало VBox
        vbMainFormRoot.getChildren().add(0, formService.loadMenuForm());

        tvListProducts.setItems(productService.getListProducts());
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcBrand.setCellValueFactory(cellData -> {
            Product product = cellData.getValue();
            if (product.getBrand() == null) {
                return new SimpleStringProperty("");
            }
            return new SimpleStringProperty(product.getBrand().getName());
        });
        tcReleaseYear.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
        tcQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tcCount.setCellValueFactory(new PropertyValueFactory<>("count"));

        // Обработка двойного клика по товару
        tvListProducts.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {  // Если кликнули дважды
                Product selectedProduct = tvListProducts.getSelectionModel().getSelectedItem();
                if (selectedProduct != null) {
                    showPurchaseDialog(selectedProduct);
                }
            }
        });
    }
}