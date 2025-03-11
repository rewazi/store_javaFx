package com.example.demo.controller;

import com.example.demo.service.FormService;
import com.example.demo.service.StoreUserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MenuFormController implements Initializable {

    private final FormService formService;

    @FXML private Menu menuProducts;
    @FXML private Menu menuCustomers;
    @FXML private Menu menuAdministrator;
    @FXML private Menu menuUser;

    @FXML private MenuItem showProductFormMenu;
    @FXML private MenuItem showBrandFormMenu;
    @FXML private MenuItem showCustomerFormMenu;
    @FXML private MenuItem showCustomerTableMenu;
    @FXML private MenuItem showAdministratorMenuItem;
    @FXML private MenuItem logoutMenuItem;
    @FXML private MenuItem profileMenuItem;

    public MenuFormController(FormService formService) {
        this.formService = formService;
    }

    @FXML private void showProductForm() {
        formService.loadNewProductForm();
    }

    @FXML private void showBrandForm() {
        formService.loadBrandForm();
    }

    @FXML private void showCustomerForm() {
        formService.loadCustomerForm();
    }

    @FXML private void showCustomerTable() {
        formService.loadCustomerTableForm();
    }
    @FXML
    private void showProfileForm() {
        formService.loadProfileForm();  // Загружаем форму редактирования профиля
    }

    @FXML private void logout() {
        StoreUserService.currentUser = null;
        formService.loadLoginForm();
    }

    private void initMenuVisibility() {
        if (StoreUserService.currentUser != null) {
            if (StoreUserService.currentUser.getRoles().contains(StoreUserService.ROLES.ADMINISTRATOR.toString())) {
                menuProducts.setVisible(true);
                menuCustomers.setVisible(true);
                menuAdministrator.setVisible(true);
                menuUser.setVisible(true);
                showProductFormMenu.setVisible(true);
                showBrandFormMenu.setVisible(true);
                showCustomerFormMenu.setVisible(true);
                showCustomerTableMenu.setVisible(true);
                showAdministratorMenuItem.setVisible(true);
                logoutMenuItem.setVisible(true);
                profileMenuItem.setVisible(true);
            } else if (StoreUserService.currentUser.getRoles().contains(StoreUserService.ROLES.MANAGER.toString())) {
                menuProducts.setVisible(true);
                menuCustomers.setVisible(true);
                menuAdministrator.setVisible(false);
                menuUser.setVisible(true);
                showProductFormMenu.setVisible(true);
                showBrandFormMenu.setVisible(true);
                showCustomerFormMenu.setVisible(true);
                showCustomerTableMenu.setVisible(true);
                showAdministratorMenuItem.setVisible(false);
                logoutMenuItem.setVisible(true);
                profileMenuItem.setVisible(true);
            } else if (StoreUserService.currentUser.getRoles().contains(StoreUserService.ROLES.CUSTOMER.toString())) {
                menuProducts.setVisible(false);
                menuCustomers.setVisible(false);
                menuAdministrator.setVisible(false);
                menuUser.setVisible(true);
                showProductFormMenu.setVisible(false);
                showBrandFormMenu.setVisible(false);
                showCustomerFormMenu.setVisible(false);
                showCustomerTableMenu.setVisible(false);
                showAdministratorMenuItem.setVisible(false);
                logoutMenuItem.setVisible(true);
                profileMenuItem.setVisible(true);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initMenuVisibility();
    }
}
