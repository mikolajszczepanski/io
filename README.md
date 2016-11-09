# Dokumentacja #
https://drive.google.com/drive/folders/0BxZZtvtO5aBFU3hCSjZ6QlZ1djA?usp=sharing

# README #

### Projekt IO ###
System zarządzania wydatkami w Java.

### Wymagane komponenty ###

* Eclipse( UWAGA należy zmienić kodowanie z Cp1250 na UTF-8 , Window->preferences (wyszukaj encoding) )
* Mysql ( polecam w pakiecie XAMPP )

1. Kopiujemy całe repozytorium do workspace 
2. Uruchamiamy eclipse
3. Importujemy projekt Maven z tego co dodaliśmy (PPM (na Project Explorer) ->Import->Import->Maven->Existing maven project), przechodzimy wizard'a 
4. Zmieniamy java compiler na jre8 w ustawieniach projektu.
5. Zaciągamy zależnośći PPM na naszym projekcie->Maven->Update Project
6. Czekamy aż wszystkie lib'y się załadują
7. Uruchamamy XAMPP->włączamy Apache i Mysql, klikamy na admin w Mysql (przechodzimy na localhost/phpmyadmin)
8. Tworzymy nową baze io_db
9. Importujemy obraz bazy z projektu ( io_db.sql ) lub ściagamy z repo
10. Gotowe

### Używane biblioteki/frameworki backend ###

 * Spark [http://sparkjava.com/](http://sparkjava.com/)
 * Freemarker [http://freemarker.org/](http://freemarker.org/)
 * Connector/J [http://dev.mysql.com/doc/connector-j/5.1/en/](http://dev.mysql.com/doc/connector-j/5.1/en/)


### Używane biblioteki/frameworki frontend ###

 * Bootstrap [http://getbootstrap.com/](http://getbootstrap.com/)
 * Material Design for Bootstrap [http://fezvrasta.github.io/bootstrap-material-design/](http://fezvrasta.github.io/bootstrap-material-design/)
 * Jquery [https://jquery.com/](https://jquery.com/)


### Komendy ###

 * git init
 * git remote add origin https://github.com/mikolajszczepanski/io.git
 * git pull https://github.com/mikolajszczepanski/io.git master
