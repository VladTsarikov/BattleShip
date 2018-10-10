package webdriver.enums;

public enum ElementAttributeName {

    CLASS("class"),
    ID("id"),
    HREF("href");

    private String attributeName;

    ElementAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getName() {
        return attributeName;
    }
}
