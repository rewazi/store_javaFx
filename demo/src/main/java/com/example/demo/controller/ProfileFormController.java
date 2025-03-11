package com.example.demo.controller;

import com.example.demo.service.FormService;
import com.example.demo.service.StoreUserService;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.stereotype.Component;

@Component
public class ProfileFormController {

    @FXML private TextField tfFirstname;
    @FXML private TextField tfLastname;
    @FXML private TextField tfUsername;
    @FXML private PasswordField pfPassword;
    @FXML private ImageView imgAvatar;

    private FormService formService;

    private StoreUserService storeUserService;

    public ProfileFormController(FormService formService, StoreUserService storeUserService) {
        this.formService = formService;
        this.storeUserService = storeUserService;
    }

    @FXML
    private void loadAvatar() {
        // Логика для загрузки аватарки (например, диалоговое окно для выбора файла)
        // В этой части кода можно использовать стандартные JavaFX средства для загрузки изображений
        Image image = new Image("file:/path/to/avatar.jpg");  // Замените на путь к аватарке
        imgAvatar.setImage(image);
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }

    @FXML
    private void saveProfile() {
        // Получаем данные пользователя из полей формы
        String firstname = tfFirstname.getText();
        String lastname = tfLastname.getText();
        String username = tfUsername.getText();
        String password = pfPassword.getText();

        // Получаем текущего пользователя
        StoreUserService.currentUser.setFirstname(firstname);
        StoreUserService.currentUser.setLastname(lastname);
        StoreUserService.currentUser.setUsername(username);
        if (!password.isEmpty()) {
            StoreUserService.currentUser.setPassword(password);
        }

        // Обновляем информацию в базе данных
        storeUserService.add(StoreUserService.currentUser);

        // После сохранения данных, можно вернуться на главную страницу или в профиль
        // formService.loadMainForm(); // Замените на нужный вам метод для перехода
    }
}
