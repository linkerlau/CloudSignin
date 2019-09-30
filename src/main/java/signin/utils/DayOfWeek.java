package signin.utils;

public enum DayOfWeek {
    Mon("周一",1), Tue("周二",2),
    Wed("周三", 3), Thu("周四", 4),
    Fri("周五",5), Sat("周六", 6),
    Sun("周日",7);

    private String name;
    private int index;

    DayOfWeek(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            if (dayOfWeek.getIndex() == index) {
                return dayOfWeek.name;
            }
        }
        return "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
