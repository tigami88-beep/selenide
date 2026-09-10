
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
        open("URL_СТРАНИЦЫ_ФОРМЫ"); // Вставь реальный URL
    }

    @AfterEach
    void tearDown() {
        WebDriverRunner.closeWebDriver();
    }

    @Test
    void shouldSuccessfullySubmitCardDeliveryForm() {
        // 1. Город (случайный из JSON)
        String randomCity = CitiesLoader.getRandomCity();
        \$("#city").setValue(randomCity);

        // 2. Дата (через 3 дня)
        String validDate = getValidDate();
        \$("#date").setValue(validDate);

        // 3. ФИО
        \$("#fullName").setValue("Иван Петров");

        // 4. Телефон
        \$("#phone").setValue("+79000000000");

        // 5. Согласие (чекбокс)
        \$("#agreement").click();

        // 6. Кнопка «Забронировать»
        \$("#bookButton").click();

        // 7. Проверка загрузки (ждем появления элемента)
        \$("#loading").shouldBe(Condition.visible, Duration.ofSeconds(15));

        // 8. Проверка успеха
        \$("#success-modal").shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    private String getValidDate() {
        LocalDate date = LocalDate.now().plusDays(3);
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}