package unischedule.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import unischedule.model.Professor;

/**
 * Serviciu pentru gestionarea profesorilor
 */
public class ProfessorService {
    private List<Professor> professors;
    
    public ProfessorService() {
        this.professors = new ArrayList<>();
    }
    
    public List<Professor> getProfessors() {
        return professors;
    }
    
    public void setProfessors(List<Professor> professors) {
        this.professors = professors;
    }
    
    public void addProfessor(Professor professor) {
        professors.add(professor);
    }
    
    public void removeProfessor(Professor professor) {
        professors.remove(professor);
    }
    
    public Professor findProfessorByName(String name) {
        return professors.stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
    
    public List<Professor> findProfessorsByDepartment(String department) {
        return professors.stream()
                .filter(p -> p.getDepartment().equals(department))
                .collect(Collectors.toList());
    }
    
    public void displayAllProfessors() {
        System.out.println("Lista profesorilor:");
        for (Professor professor : professors) {
            System.out.println(professor);
        }
    }
} 