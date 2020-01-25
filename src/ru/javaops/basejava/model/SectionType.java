package ru.javaops.basejava.model;

import ru.javaops.basejava.util.HtmlUtil;

public enum SectionType {
    OBJECTIVE("Позиция") {
        @Override
        public String toHtml(AbstractSection abstractSection) {
            return HtmlUtil.textSectionTopToHtml(abstractSection);
        }
    },
    PERSONAL("Личные качества") {
        @Override
        public String toHtml(AbstractSection abstractSection) {
            return HtmlUtil.textSectionToHtml(abstractSection);
        }
    },
    ACHIEVEMENT("Достижения") {
        @Override
        public String toHtml(AbstractSection abstractSection) {
            return HtmlUtil.listSectionToHtml(abstractSection);
        }
    },
    QUALIFICATIONS("Квалификация") {
        @Override
        public String toHtml(AbstractSection abstractSection) {
            return HtmlUtil.listSectionToHtml(abstractSection);
        }
    },
    EXPERIENCE("Опыт работы") {
        @Override
        public String toHtml(AbstractSection abstractSection) {
            return HtmlUtil.OrganizationSectionToHtml(abstractSection);
        }
    },
    EDUCATION("Образование") {
        @Override
        public String toHtml(AbstractSection abstractSection) {
            return HtmlUtil.OrganizationSectionToHtml(abstractSection);
        }
    };

    private String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String toHtml(AbstractSection abstractSection) {
        return null;
    }
}