import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.\$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    @BeforeEach
    void setUp() {
        // Замени URL_СТРАНИЦЫ_ФОРМЫ на реальный адрес страницы с формой
        open("URL_СТРАНИЦЫ_ФОРМЫ");
    }

    @AfterEach
    void tearDown() {
        WebDriverRunner.closeWebDriver();
    }

    @Test
    void shouldSuccessfullySubmitCardDeliveryForm() {
        // 1. Город (случайный административный центр из JSON)
        String randomCity = CitiesLoader.getRandomCity();
        \$("#city").setValue(randomCity);

        // 2. Дата (не ранее 3 дней от текущей)
        String validDate = getValidDate();
        \$("#date").setValue(validDate);

        // 3. ФИО (только русские буквы, дефисы, пробелы)
        \$("#fullName").setValue("Иван Петров");

        // 4. Телефон (11 цифр, + в начале)
        \$("#phone").setValue("+79000000000");

        // 5. Согласие с обработкой ПДн (чекбокс)
        \$("#agreement").click();

        // 6. Нажатие кнопки «Забронировать»
        \$("#bookButton").click();

        // 7. Проверка состояния загрузки (не более 15 секунд)
        // Элемент с индикатором загрузки должен появиться в DOM
        \$("#loading").shouldBe(Condition.visible, Duration.ofSeconds(15));

        // 8. Проверка появления всплывающего окна об успехе
        // Модальное окно с подтверждением бронирования
        \$("#success-modal").shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    private String getValidDate() {
        LocalDate date = LocalDate.now().plusDays(3);
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}