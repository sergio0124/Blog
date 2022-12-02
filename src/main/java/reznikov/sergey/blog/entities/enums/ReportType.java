package reznikov.sergey.blog.entities.enums;

public enum ReportType {

    RACISM,
    NATIONALISM,
    COMMUNISM,
    CLEVER,
    STUPID,
    FAKE,
    OTHER;

    public static String getInRussian(ReportType type) {
        switch (type) {
            case RACISM:
                return "Расизм";
            case NATIONALISM:
                return "Нацизм";
            case COMMUNISM:
                return "Коммунизм";
            case FAKE:
                return "Клевета";
            case OTHER:
                return "Другое";
            case CLEVER:
                return "Умный";
            case STUPID:
                return "Тупой";
        }
        return "Другое";
    }


    public static ReportType fromRussian(String string){
        switch (string) {
            case "Расизм":
                return RACISM;
            case "Нацизм":
                return NATIONALISM;
            case "Коммунизм":
                return COMMUNISM;
            case "Клевета":
                return FAKE;
            case "Другое":
                return OTHER;
            case "Умный":
                return CLEVER;
            case "Тупой":
                return STUPID;
        }
        return OTHER;
    }
}
