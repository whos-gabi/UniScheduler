## Cerinte proiect:  

Fiecare student va lucra la un proiect individual. Proiectul este structurat în mai multe etape.  
Conditia de punctare a proiectelor:  
• sa nu prezinte erori de compilare;  
• sa se implementeze cerintele date.  

### Tema proiectului:
Sistem de gestiune a cursurilor universitare.

### Etapa I  
1) Definirea sistemului  
   Sa se creeze o lista pe baza temei alese cu cel putin 10 actiuni/interogari care se pot face în cadrul sistemului si o lista cu cel putin 8 tipuri de obiecte.  
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
 