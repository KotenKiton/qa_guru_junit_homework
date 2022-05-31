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
        Configuration.baseUrl = "https://www.techport.ru/";
        Configuration.browserSize = "1920x1080";

    }

    /////////////////////////// Практика с аннотацией @CValueSourse///////////////////////////
    // Аннотация ValueSourse дата провайдер. Она передаст каждый аругмент по очереди в наш тестовый метод.
    @ValueSource(strings = {
            "Iphone", // Это
            "iphone 13 pro max" // и ЭТО
    })
    // Макрос который заменяет собой аргумент теста по его индексу
    // ParameterizedTest - когда у нас много разных входных данных но шаги не меняются. Ветвление логики не происходит.

    @ParameterizedTest(name = "Проверка поиска в Технопорте по слову {0}")
    void texPortSearchTests(String testData) { // будет передано сюда!
        open("");
        //Steps
        $("#desktop_search_input").click();
        $("#desktop_search_input").setValue(testData);
        $("#desktop_search_submit svg").click();
        //ождиаемый результат
        $$(".ellip")
                .find(Condition.text(testData))
                .shouldBe(visible);
    }

    /////////////////////////// Практика с аннотацией @CsvSourse///////////////////////////
    // CsvSource позволяет работать с разными типами данных. Но всегда нужно указывать тип данных внизу.
    // String для строк, int для чисел, boolean тоже можно.
    @CsvSource(value = {
            "Iphone | Смартфон Apple iPhone 13 (6,1\") 128GB Starlight", // с точки зрения Junit запятая это разделитель.
            "iphone 13 pro max | Смартфон Apple iPhone 13 Pro Max (6,7\") 256GB Sierra Blue"

            // Если в тексте должа быть запятая, надо использовать разделитель.
            // поставить запятую после массива. Написать delimiter = "|" и использовать | как запятую.
    },
            delimiter = '|'
    )
    @ParameterizedTest(name = "Проверка поиска в Технопорте по слову {0}, ожидаем результат: {1}")
    void texPortSearchComplexTests(String testData, String expectedResult) { // будет передано сюда!
        open("");
        //Steps
        $("#desktop_search_input").setValue(testData);
        $("#desktop_search_input").click();
        //Ождиаемый результат
        //$$ - запрашиваем все дочерние элементы
        $$(".ellip")
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

    @MethodSource
    @ParameterizedTest(name = "Пример работы MethodSource")
    void methodSourceExampleTest(String first, List<Integer> second) {
        System.out.println(first + " Это и то: " + second);
    }
    /////////////////////////// Практика с аннотацией @EnumSource///////////////////////////

    @EnumSource(MenuItem.class)
    @ParameterizedTest
    void texPortSearchMenuTests(MenuItem testData) { // будет передано сюда!
        open("");
        //Steps
        $("#desktop_search_input").click();
        $("#desktop_search_input").setValue("Iphone");
        $("#desktop_search_submit svg").click();
        //ождиаемый результат
        $$(".tcp-category-fast__text")
                .find(Condition.text(testData.rusName))
                .click();
        Assertions.assertEquals(
                1,
                WebDriverRunner.getWebDriver().getWindowHandles().size()
        );
    }

    @AfterEach
    void close() {
        Selenide.closeWebDriver();
    }
}
