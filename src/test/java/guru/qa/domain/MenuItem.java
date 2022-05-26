package guru.qa.domain;

public enum MenuItem {
    AllPromotions("Все акции"), discount30(" Скидки до 30%");
    public final String rusName;

    MenuItem(String rusName) {
        this.rusName = rusName;
    }
}
