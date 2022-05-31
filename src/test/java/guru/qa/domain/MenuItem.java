package guru.qa.domain;

public enum MenuItem {
    AllPromotions("Крупная бытовая техника"), discount30("Встраиваемая бытовая техника");
    public final String rusName;

    MenuItem(String rusName) {
        this.rusName = rusName;
    }
}
