package ru.netology.quamid59.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.quamid59.data.DataGenerator;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.quamid59.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.quamid59.data.DataGenerator.Registration.getUser;
import static ru.netology.quamid59.data.DataGenerator.getRandomLogin;
import static ru.netology.quamid59.data.DataGenerator.getRandomPassword;

class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }


    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $("button[data-test-id=action-login]").click();
        $("h2.heading.heading_size_l.heading_theme_alfa-on-white").shouldHave(text("Личный кабинет"));
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $("button[data-test-id=action-login]").click();
        $("[data-test-id='error-notification'] [class='notification__content']").shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $("button[data-test-id=action-login]").click();
        $("[data-test-id='error-notification'] [class='notification__content']").shouldHave(Condition.exactText("Ошибка! " + "Пользователь заблокирован"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id='login'] input").setValue(wrongLogin);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button[data-test-id=action-login]").click();
        $("[data-test-id='error-notification'] [class='notification__content']").shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $("button[data-test-id=action-login]").click();
        $("[data-test-id='error-notification'] [class='notification__content']").shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }
}