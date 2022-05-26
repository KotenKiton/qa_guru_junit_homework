package guru.qa;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import guru.qa.domain.MenuItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class SearchTests {

    @BeforeAll
    static void setUp() {
        Configuration.holdBrowserOpen = true;
        Configuration.baseUrl = "https://www.mvideo.ru/";
        Configuration.browserSize = "1920x1080";

    }
    /////////////////////////// Практика с аннотацией @CValueSourse///////////////////////////
    // Аннотация ValueSourse дата провайдер. Она передаст каждый аругмент по очереди в наш тестовый метод.
    @ValueSource(strings = {
            "iPhone", // Это
            "Apple Watch" // и ЭТО
    })
    // Макрос который заменяет собой аргумент теста по его индексу
    // ParameterizedTest - когда у нас много разных входных данных но шаги не меняются.
    // Ветвление логики не просисходит.
    @ParameterizedTest(name = "Проверка поиска в Мвидео по слову {0}")
    void MvideoSearchTests(String testData) { // будет передано сюда!
        Selenide.open("https://www.mvideo.ru/");
        //Steps
        $(".input__field").setValue(testData);
        $(".search-icon > svg").click();
        //ождиаемый результат
        $$(".ng-star-inserted")
                .find(text(testData))
                .shouldBe(visible);
    }

    /////////////////////////// Практика с аннотацией @CsvSourse///////////////////////////
    // CsvSource позволяет работать с разными типами данных. Но всегда нужно указывать тип данных внизу.
    // String для строк, int для чисел, boolean тоже можно.
    @CsvSource(value = {
            "iPhone | Apple iPhone 11 64GB Black (MHDA3RU/A)", // с точки зрения Junit запятая это разделитель.
            "Apple Watch | Смарт-часы Apple Watch SE GPS 44mm Gold Aluminium/Starlight Sport"

            // Если в тексте должа быть запятая, надо использовать разделитель.
            // поставить запятую после массива. Написать delimiter = "|" и использовать | как запятую.
    },
            delimiter = '|'
    )
    @ParameterizedTest(name = "Проверка поиска в Мвидео по слову {0}, ожидаем результат: {1}")
    void MvideoSearchComplexTests(String testData, String expectedResult) { // будет передано сюда!
        Selenide.open("https://www.mvideo.ru/");
        // При первом запуске теста на "Холодной" машине тест может фейлится. Предполагаю,что из-за поп апа.
        // уточнить это. ( поп ап в центре экрана "Нажмите «Разрешить» в углу экрана и получите доступ к скидкам "
        //Steps
        $(".input__field").setValue(testData);
        $(".search-icon > svg").click();
        //Ождиаемый результат
        //$$ - запрашиваем все дочерние элементы
        $$(".ng-star-inserted")
                .find(text(expectedResult))
                .shouldBe(visible);
    }
    /////////////////////////// Практика с аннотацией @MethodSource///////////////////////////

    static Stream<Arguments> methodSourceExampleTest() {
        return Stream.of(
                Arguments.of("first string", List.of("One, Two, Three, Four, Five")),
                Arguments.of("second string", List.of(4000, 8000, 1500, 1600, 2300, 4200))
        );
    }
    // Как реализовать Методсорс для сайта мвидео?
    @MethodSource
    @ParameterizedTest(name = "Пример работы MethodSource")
    void methodSourceExampleTest(String first, List<Integer> second) {
        System.out.println(first + " Это и то: " + second);
    }
    /////////////////////////// Практика с аннотацией @EnumSource///////////////////////////

    @EnumSource(MenuItem.class)
    @ParameterizedTest
    void MvideoSearchCMenuTests(MenuItem testData) { // будет передано сюда!
        Selenide.open("https://www.mvideo.ru/");
        //Steps
        $(".input__field").setValue("Iphone");
        $(".search-icon > svg").click();
        //ождиаемый результат
        $$(".app-header")
                .find(Condition.text(testData.rusName))
                .click();

        Assertions.assertEquals(
                2,
                WebDriverRunner.getWebDriver().getWindowHandles().size()

        );

    }
        @AfterEach
    void close(){
        Selenide.closeWebDriver();
        }
}
