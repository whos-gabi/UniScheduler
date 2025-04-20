package unischedule.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import unischedule.model.StudentGroup;

/**
 * Serviciu pentru gestionarea grupelor de studenti
 */
public class StudentGroupService {
    private List<StudentGroup> studentGroups;
    private TreeSet<StudentGroup> sortedGroups;
    
    public StudentGroupService() {
        this.studentGroups = new ArrayList<>();
        this.sortedGroups = new TreeSet<>(Comparator.comparing(StudentGroup::getName));
    }
    
    public List<StudentGroup> getStudentGroups() {
        return studentGroups;
    }
    
    public void setStudentGroups(List<StudentGroup> studentGroups) {
        this.studentGroups = studentGroups;
        this.sortedGroups.clear();
        this.sortedGroups.addAll(studentGroups);
    }
    
    public void addStudentGroup(StudentGroup group) {
        studentGroups.add(group);
        sortedGroups.add(group);
    }
    
    public void removeStudentGroup(StudentGroup group) {
        studentGroups.remove(group);
        sortedGroups.remove(group);
    }
    
    public StudentGroup findGroupByName(String name) {
        return studentGroups.stream()
                .filter(g -> g.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
    
    public List<StudentGroup> findGroupsByYear(int year) {
        return studentGroups.stream()
                .filter(g -> g.getYear() == year)
                .collect(Collectors.toList());
    }
    
    public TreeSet<StudentGroup> getSortedGroups() {
        return sortedGroups;
    }
    
    public void displayAllGroups() {
        System.out.println("Lista grupelor de studenti:");
        for (StudentGroup group : sortedGroups) {
            System.out.println(group);
        }
    }
} 