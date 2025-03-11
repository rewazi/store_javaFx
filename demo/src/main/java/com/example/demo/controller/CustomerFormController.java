package com.example.demo.controller;

import com.example.demo.model.entity.StoreUser;
import com.example.demo.service.FormService;
import com.example.demo.service.StoreUserService;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class CustomerFormController {

    private final StoreUserService storeUserService;
    private final FormService formService;

    @FXML
    private TextField tfFirstname;
    @FXML
    private TextField tfLastname;
    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField pfPassword;

    private StoreUser editingCustomer;

    public CustomerFormController(StoreUserService storeUserService, FormService formService) {
        this.storeUserService = storeUserService;
        this.formService = formService;
    }

    @FXML
    public void saveCustomer() {
        if (editingCustomer == null) {
            // Ограничение добавления новых пользователей только для администратора
            if (!hasAdminAccess()) {
                System.out.println("Access denied: у вас нет прав для выполнения данного действия.");
                return;
            }

            StoreUser newCustomer = new StoreUser();
            newCustomer.setFirstname(tfFirstname.getText());
            newCustomer.setLastname(tfLastname.getText());
            newCustomer.setUsername(tfUsername.getText());
            newCustomer.setPassword(pfPassword.getText());
            newCustomer.getRoles().add(StoreUserService.ROLES.CUSTOMER.toString());
            storeUserService.add(newCustomer);
        } else {
            editingCustomer.setFirstname(tfFirstname.getText());
            editingCustomer.setLastname(tfLastname.getText());
            editingCustomer.setUsername(tfUsername.getText());
            if (!pfPassword.getText().isEmpty()) {
                editingCustomer.setPassword(pfPassword.getText());
            }
            storeUserService.add(editingCustomer);
        }
        formService.loadCustomerTableForm();
    }

    @FXML
    public void goToCustomerTable() {
        formService.loadCustomerTableForm();
    }

    public void setEditingCustomer(StoreUser customer) {
        this.editingCustomer = customer;
        tfFirstname.setText(customer.getFirstname());
        tfLastname.setText(customer.getLastname());
        tfUsername.setText(customer.getUsername());
    }

    private boolean hasAdminAccess() {
        return StoreUserService.currentUser != null &&
                (StoreUserService.currentUser.getRoles().contains(StoreUserService.ROLES.ADMINISTRATOR.toString()) ||
                        StoreUserService.currentUser.getRoles().contains(StoreUserService.ROLES.MANAGER.toString()));
    }
}
