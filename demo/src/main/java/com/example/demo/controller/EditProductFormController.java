package com.example.demo.controller;

import com.example.demo.model.entity.Product;
import com.example.demo.model.entity.Brand;
import com.example.demo.service.FormService;
import com.example.demo.service.ProductService;
import com.example.demo.service.StoreUserService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import org.springframework.stereotype.Component;

@Component
public class EditProductFormController {

    private final FormService formService;
    private final ProductService productService;
    private Product editProduct;

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfReleaseYear;
    @FXML
    private TextField tfQuantity;
    @FXML
    private TextField tfCount; // Обязательно объявляем это поле
    @FXML
    private ListView<Brand> lvBrands; // Список брендов

    public EditProductFormController(FormService formService, ProductService productService) {
        this.formService = formService;
        this.productService = productService;
    }

    @FXML
    private void goEdit() {
        // Проверка на админские права
        if (!hasAdminAccess()) {
            System.out.println("Access denied: у вас нет прав для выполнения данного действия.");
            return;
        }

        editProduct.setName(tfName.getText());
        editProduct.setReleaseYear(Integer.parseInt(tfReleaseYear.getText()));
        int quantity = Integer.parseInt(tfQuantity.getText());
        editProduct.setQuantity(quantity);
        editProduct.setCount(quantity);  // Количество всегда равно количеству в наличии

        // Устанавливаем выбранный бренд
        Brand selectedBrand = lvBrands.getSelectionModel().getSelectedItem();
        if (selectedBrand != null) {
            editProduct.setBrand(selectedBrand);
        }

        productService.create(editProduct);
        formService.loadMainForm();
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }

    public void setEditProduct(Product product) {
        this.editProduct = product;
        tfName.setText(product.getName());
        tfReleaseYear.setText(String.valueOf(product.getReleaseYear()));
        tfQuantity.setText(String.valueOf(product.getQuantity()));
        tfCount.setText(String.valueOf(product.getCount()));
        // Если бренд уже установлен, выбрать его в списке
        lvBrands.getSelectionModel().select(product.getBrand());
    }

    private boolean hasAdminAccess() {
        return StoreUserService.currentUser != null &&
                (StoreUserService.currentUser.getRoles().contains(StoreUserService.ROLES.ADMINISTRATOR.toString()) ||
                        StoreUserService.currentUser.getRoles().contains(StoreUserService.ROLES.MANAGER.toString()));
    }
}
