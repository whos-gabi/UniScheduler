package unischedule.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import unischedule.model.Professor;

/**
 * Gestionarea profesorilor
 */
public class ProfessorService {
    private final TreeSet<Professor> sortedProfessors;

    public ProfessorService() {
        this.sortedProfessors = new TreeSet<>(Comparator.comparing(Professor::getName));
    }

    public Professor createProfessor(String id, String title, String firstName, String lastName, 
                                     String department, String email, String phone) {
        if (id != null && findProfessorById(id) != null) {
            System.out.println("Exista deja un profesor cu ID-ul: " + id);
            return null;
        }

        Professor professor = new Professor(title, firstName, lastName, email, department);
        professor.setId(id);
        professor.setPhone(phone);
        sortedProfessors.add(professor);
        return professor;
    }

   
    public Professor findProfessorById(String id) {
        return sortedProfessors.stream()
                .filter(p -> p.getId() != null && p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

  
    public List<Professor> findProfessorsByName(String name) {
        return sortedProfessors.stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }
    
    public Professor findProfessorByName(String name) {
        return sortedProfessors.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public List<Professor> findProfessorsByDepartment(String department) {
        return sortedProfessors.stream()
                .filter(p -> p.getDepartment().equalsIgnoreCase(department))
                .toList();
    }

  
    public Professor updateProfessorDetails(String id, String title, String firstName, String lastName,
                                          String department, String email, String phone) {
        Professor professor = findProfessorById(id);
        if (professor == null) {
            System.out.println("Nu exista profesor cu ID-ul: " + id);
            return null;
        }

        sortedProfessors.remove(professor);

        if (title != null) professor.setTitle(title);
        if (firstName != null) professor.setFirstName(firstName);
        if (lastName != null) professor.setLastName(lastName);
        if (department != null) professor.setDepartment(department);
        if (email != null) professor.setEmail(email);
        if (phone != null) professor.setPhone(phone);

        sortedProfessors.add(professor);
        return professor;
    }

    public boolean deleteProfessor(String id) {
        Professor professor = findProfessorById(id);
        if (professor == null) {
            System.out.println("Nu exista profesor cu ID-ul: " + id);
            return false;
        }
        return sortedProfessors.remove(professor);
    }


    public List<Professor> getAllProfessors() {
        return new ArrayList<>(sortedProfessors);
    }

    public void addProfessor(Professor professor) {
        sortedProfessors.add(professor);
    }
    

    public List<Professor> getProfessors() {
        return new ArrayList<>(sortedProfessors);
    }

 
    public void displayAllProfessors() {
        if (sortedProfessors.isEmpty()) {
            System.out.println("Nu exista profesori in sistem.");
            return;
        }

        System.out.println("Lista profesorilor:");
        int index = 1;
        for (Professor professor : sortedProfessors) {
            System.out.println(index + ". " + professor);
            index++;
        }
    }

    public void displayProfessorDetails(String id) {
        Professor professor = findProfessorById(id);
        if (professor == null) {
            System.out.println("Nu există profesor cu ID-ul: " + id);
            return;
        }

        System.out.println("Detalii profesor:");
        System.out.println("ID: " + professor.getId());
        if (professor.getTitle() != null) {
            System.out.println("Titlu: " + professor.getTitle());
            System.out.println("Prenume: " + professor.getFirstName());
            System.out.println("Nume: " + professor.getLastName());
        }
        System.out.println("Nume complet: " + professor.getName());
        System.out.println("Departament: " + professor.getDepartment());
        System.out.println("Email: " + professor.getEmail());
        System.out.println("Telefon: " + professor.getPhone());
        System.out.println("Intervale orare rezervate: " + professor.getBookedTimeSlots().size());
    }
} 