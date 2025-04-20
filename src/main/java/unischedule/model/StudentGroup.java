package unischedule.model;

public class StudentGroup {
    private int year;
    private int series;
    private int groupNumber;

    StudentGroup(int year, int series, int groupNumber) {
        this.year = year;
        this.series = series;
        this.groupNumber = groupNumber;
    }

    public StudentGroup(String name) {
        String[] parts = name.split("\\D+");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid group name format");
        }
        this.year = Integer.parseInt(parts[0]);
        this.series = Integer.parseInt(parts[1]);
        this.groupNumber = Integer.parseInt(parts[2]);
    }


    private String getGroupName() {
        return String.format("%d%d%d", year, series, groupNumber);
    }

    public int getYear() {
        return year;
    }
    public int getSeries() {
        return series;
    }
    public int getGroupNumber() {
        return groupNumber;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public void setSeries(int series) {
        this.series = series;
    }
    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }
}

