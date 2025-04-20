# UniScheduler - University Timetable Generator
Proiect Pentru Materia PAOJ (Programare Avansata pe Obiecte in Java)

## Cerinta
## Termene de predare:
• Etapa I: saptamâna 14 – 20 aprilie  
• Etapa II: saptamâna 2 – 6 iunie

### Etapa I
1) Definirea sistemului  
   - [ ] Sa se creeze o lista pe baza temei alese cu cel putin 10 actiuni/interogari care se pot face în cadrul sistemului si o lista cu cel putin 8 tipuri de obiecte.
2) Implementare  
   Sa se implementeze în limbajul Java (versiunea 21) o aplicatie pe baza celor definite la primul punct.  
   Aplicatia va contine:  
   • clase simple cu atribute private / protected si metode de acces;  
   • cel putin 2 colectii diferite capabile sa gestioneze obiectele definite anterior (eg: List, Set, Map, etc.) dintre care cel putin una sa fie sortata.  
   Se vor folosi array-uri uni/bidimensionale în cazul în care nu se parcurg colectiile pana la data checkpoint-ului;  
   • utilizare mostenire pentru crearea de clase aditionale si utilizarea lor în cadrul colectiilor;  
   • cel putin o clasa serviciu care sa expuna operatiile sistemului;  
   • o clasa Main din care sunt facute apeluri catre servicii.

### Etapa II
1) Extindeti proiectul din prima etapa prin realizarea persistentei utilizând o baza de date relationala si JDBC.  
   Sa se realizeze servicii care sa expuna operatii de tip create, read, update si delete pentru cel putin 4 dintre clasele definite.   
   Se vor realiza servicii singleton generice pentru scrierea si citirea din baza de date.

2) Realizarea unui serviciu de audit  
   Se va realiza un serviciu care sa scrie într-un fisier de tip CSV de fiecare data când este executata una dintre actiunile descrise în prima etapa.   
   Structura fisierului: nume_actiune, timestamp

## Implementare

### Obiecte implementate
1. Professor
2. Course
3. OnlineCourse (extends Course)
4. Room
5. StudentGroup
6. TimeSlot
7. ScheduleEntry
8. Timetable

### Servicii implementate
1. ProfessorService - gestionarea profesorilor
2. RoomService - gestionarea salilor
3. StudentGroupService - gestionarea grupelor de studenti
4. TimetableService - gestionarea orarului

### Actiuni/Interogari implementate
1. Adaugare profesor nou in sistem
2. Cautare profesor dupa ID
3. Cautare profesori dupa nume
4. Cautare profesori dupa departament
5. Actualizare detalii profesor
6. Programare cursuri în orar
7. Verificare disponibilitate sala la o anumita ora
8. Generare orar pentru o grupa de studenți
9. Afisare cursuri ale unui profesor
10. Verificare suprapuneri pentru cursuri online pe aceeasi platforma


