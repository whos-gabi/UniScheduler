package java.unischedule.model;

public class StudentGroup {
    private int year;
    private int series;
    private int groupNumber;
//    private

    private String getGroupName() {
        //{d}{d2}{d3}
        //{d} - year
        //{d2} - series
        //{d3} - group number
        return String.format("%d%d%d", year, series, groupNumber);
    }

    public StudentGroup(String name, int year, String major) {
        this.name = name;
        this.year = year;
        this.major = major;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public String getMajor() {
        return major;
    }

    @Override
    public String toString() {
        return "StudentGroup{" +
                "name='" + name + '\'' +
                ", year=" + year +
                ", major='" + major + '\'' +
                '}';
    }
}
