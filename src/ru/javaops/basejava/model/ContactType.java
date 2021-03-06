package ru.javaops.basejava.model;

public enum ContactType {
    PHONE("Тел."),
    MOBILE("Мобильный"),
    HOME_PHONE("Домашний тел."),
//    SKYPE("Skype"),
//    MAIL("Почта"),
//    LINKEDIN("Профиль LinkedIn"),
//    GITHUB("Профиль GitHub"),
//    STACKOVERFLOW("Профиль Stackoverflow"),
//    HOME_PAGE("Домашняя страница");

    SKYPE("Skype") {
        @Override
        public String toHtml0(String value) {
            return toLink("skype:" + value, value);
        }
    },
    MAIL("Почта") {
        @Override
        public String toHtml0(String value) {
//            return getTitle() + ": " + toLink("mailto:" + value, value);
            return toLink("mailto:" + value, value);
        }
    },
    LINKEDIN("Профиль LinkedIn") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    GITHUB("Профиль GitHub") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    STACKOVERFLOW("Профиль Stackoverflow") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    HOME_PAGE("Домашняя страница") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    };

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    //    protected String toHtml0(String value) {
//        return title + ": " + value;
//    }
    protected String toHtml0(String value) {
        return value;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }

    public String toLink(String href) {
        return toLink(href, title);
    }

    public static String toLink(String href, String title) {
        return "<a href='" + href + "'>" + title + "</a>";
    }
}