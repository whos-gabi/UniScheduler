package unischedule.model;

import unischedule.model.enums.DisciplineType;
import unischedule.model.enums.EvaluationForm;


public class CurriculumEntry extends CurriculumEntryHours {
    private DisciplineType disciplineType;  // DF, DC, DS
    private EvaluationForm evaluationForm;  // E, V
    private String name;
    private int credits;
    
    public CurriculumEntry() {
    }
    
    public CurriculumEntry(CurriculumEntryHours hours, DisciplineType disciplineType, EvaluationForm evaluationForm, String name, int credits) {
        super(hours.getLectureHours(), hours.getSeminarHours(), hours.getLabHours(), hours.getProjectHours());
        this.disciplineType = disciplineType;
        this.evaluationForm = evaluationForm;
        this.name = name;
        this.credits = credits;
    }
    public DisciplineType getDisciplineType() {
        return disciplineType;
    }

    public EvaluationForm getEvaluationForm() {
        return evaluationForm;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    @Override
    public String toString() {
        return "CurriculumEntry{" +
                "disciplineType=" + disciplineType +
                ", evaluationForm=" + evaluationForm +
                ", name='" + name + '\'' +
                ", credits=" + credits +
                '}';
    }       
}
