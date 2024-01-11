--aufgabe6--
begin;

--Studiengang--
CREATE TABLE Studiengang (
    Kuerzel VARCHAR(255) PRIMARY KEY,
    Name VARCHAR(255) NOT NULL
);

--gebaeude--
CREATE TABLE Gebaeude (
    Nummer SERIAL PRIMARY KEY,
    Name VARCHAR(255) NOT NULL
);

--veranstaltung, Typ als attribut angegeben, da sonst nirgends verwendet--
CREATE TABLE Veranstaltung (
	Name VARCHAR(255) primary key,
    Typ VARCHAR(255) CHECK(Typ IN ('Labor', 'Übung', 'Vorlesung')),
    Wochentag INTEGER,
    Startzeit TIME,
    Endzeit TIME,
    StudiengangKuerzel VARCHAR(255),
    Fachsemester INT,
    Haeufigkeit VARCHAR(255),
    FOREIGN KEY (StudiengangKuerzel) REFERENCES Studiengang(Kuerzel)
);

--Dozent--
CREATE TABLE Dozent (
    ID SERIAL PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Vorname VARCHAR(255) NOT NULL,
    Grad VARCHAR(255),
    Email VARCHAR(255) NOT null,
    VeranstaltungName varchar(255) REFERENCES Veranstaltung(Name)
);
--Erweiterung von Dozent um Daten welche auf Professoren und Mitarbeiter beschraenkt sind--
CREATE TABLE Professor_Mitarbeiter (
    ID INT PRIMARY KEY,
    Buero VARCHAR(255),
    Sprechzeiten VARCHAR(255),
    DozentID Integer REFERENCES Dozent(ID)
);
--raum, per gebaeudenummer mit gebaeude verknuepft--
CREATE TABLE Raum (
    Nummer SERIAL PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    GebaeudeNummer Integer,
    VeranstaltungName varchar(255),
    FOREIGN KEY (GebaeudeNummer) REFERENCES Gebaeude(Nummer),
    FOREIGN KEY (VeranstaltungName) REFERENCES Veranstaltung(Name)
);

--stundenplan, per foreign key mir Studiengang verknuepft--
CREATE TABLE Stundenplan (
    ID SERIAL PRIMARY KEY,
    StudiengangKuerzel VARCHAR(255),
    FOREIGN KEY (StudiengangKuerzel) REFERENCES Studiengang(Kuerzel)
);

--aufloesen von n:m beziehung--
CREATE TABLE Stundenplan_Veranstaltung (
    StundenplanID INT,
    VeranstaltungName VARCHAR(255),
    PRIMARY KEY (StundenplanID, VeranstaltungName),
    FOREIGN KEY (StundenplanID) REFERENCES Stundenplan(ID),
    FOREIGN KEY (VeranstaltungName) REFERENCES Veranstaltung(Name)
);

drop table if exists studiengang cascade;
drop table if exists dozent cascade;
drop table if exists professor_Mitarbeiter cascade;
drop table if exists gebaeude cascade;
drop table if exists raum cascade;
drop table if exists stundenplan_Veranstaltung cascade;
drop table if exists veranstaltung cascade;
drop table if exists stundenplan cascade;

commit;