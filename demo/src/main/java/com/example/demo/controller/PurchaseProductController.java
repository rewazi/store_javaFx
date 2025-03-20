package com.example.demo.controller;

import com.example.demo.model.entity.Product;
import com.example.demo.service.ProductService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PurchaseProductController {

    private ProductService productService;
    private Product product;

    // Конструктор без параметров для работы с FXML
    public PurchaseProductController() {
        this.productService = null; // или можно оставить пустое поле, которое заполняется позже
    }

    // Конструктор с параметрами для инъекции зависимостей
    public PurchaseProductController(ProductService productService) {
        this.productService = productService;
    }

    // Метод для установки продукта в контроллер
    public void setProduct(Product product) {
        this.product = product;
    }

    // Метод для передачи зависимости ProductService вручную
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @FXML
    private TextField tfQuantity;

    // Метод для обработки покупки продукта
    @FXML
    private void purchaseProduct() {
        try {
            int quantityToBuy = Integer.parseInt(tfQuantity.getText());
            if (quantityToBuy <= 0) {
                // Введите корректное количество
                return;
            }

            if (product.getQuantity() >= quantityToBuy) {
                product.setQuantity(product.getQuantity() - quantityToBuy);
                productService.create(product);
                System.out.println("Товар куплен: " + product.getName());
            } else {
                System.out.println("Недостаточно товара в наличии.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Введите корректное количество.");
        }
    }

    @FXML
    private void closeDialog() {
        Stage stage = (Stage) tfQuantity.getScene().getWindow();
        stage.close();
    }
}
