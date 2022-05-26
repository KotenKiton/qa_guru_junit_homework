package guru.qa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Disabled("Причина по которой тест был отрублен + номер его в джире") // Отключает тест если нужно.
@DisplayName("String")
public class SimpleTest {
    // Аннотация @displayName интегрирована с алюром. тесты в нём будут красиво выглядеть.
    // public можно не писать. В junit есть возможность писать свои ассерты
    // - Аннотации @Test нужна чтобы пометить метод в классе,что он является тестом.( Junit будет его запусктаь)
    @DisplayName("Демонстрационный тест")
    @Test
    void firstTest() {
        Assertions.assertTrue(3 > 2, "check what 3 more 2"); // сообщение это третий аргумент.
        Assertions.assertFalse(3 < 2); // он может быть у любых ассертов.
        Assertions.assertEquals(1, 1); // Equals сравнение.
        Assertions.assertAll( // позваляет завернуть несколько ассертов в один и все будут выоплнены.
                () -> Assertions.assertTrue(3 > 2), // Софт ассерты.
                // Лямбда выражение ( лямбда замыкание)
                () -> Assertions.assertTrue(3 > 2)
        );
        //разница между асерт тру(фалс) и асерт алл в том,что если написать подряд 2 ассерта тру или файл и один упадет.
        // Остальные тоже упадут. Ассерт алл позволяет этого избежать.
    }
}