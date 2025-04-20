package unischedule.model;
import java.util.ArrayList;
import java.util.List;

public class Curriculum {

    private List<CurriculumEntry> entries;

    public Curriculum() {
        this.entries = new ArrayList<>();
    }
    public Curriculum(List<CurriculumEntry> entries) {
        this.entries = entries;
    }

    public void addEntry(CurriculumEntry entry) {
        entries.add(entry);
    }

    public void removeEntry(CurriculumEntry entry) {
        entries.remove(entry);
    }

    public List<CurriculumEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<CurriculumEntry> entries) {
        this.entries = entries;
    }

    @Override
    public String toString() {
        return "Curriculum{" +
                "entries=" + entries +
                '}';
    }
}
