package Algorithm;

public class TimeTableCellByNames {
    private int day;
    private int hour;
    private String classRoom;
    private String teacher;
    private String subject;

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getSubject() {
        return subject;
    }

    public TimeTableCellByNames(int day, int hour, String classRoom, String teacher, String subject)  {
        this.day = day;
        this.hour = hour;
        this.classRoom = classRoom;
        this.teacher = teacher;
        this.subject = subject;
    }
}
