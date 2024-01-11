--aufgabe6--
--Studiengang--
CREATE TABLE Studiengang (
    Kuerzel VARCHAR(255) PRIMARY KEY,
    Name VARCHAR(255) NOT NULL
);

--Dozent--
CREATE TABLE Dozent (
    ID SERIAL PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Vorname VARCHAR(255) NOT NULL,
    Grad VARCHAR(255),
    Email VARCHAR(255) NOT null
    foreign key (ID) references Veranstaltung(ID)
);

--Erweiterung von Dozent um Daten welche auf Professoren und Mitarbeiter beschraenkt sind--
CREATE TABLE Professor_Mitarbeiter (
    ID INT PRIMARY KEY,
    Buero VARCHAR(255),
    Sprechzeiten VARCHAR(255),
    FOREIGN KEY (ID) REFERENCES Dozent(ID)
);
--gebaeude--
CREATE TABLE Gebaeude (
    Nummer SERIAL PRIMARY KEY,
    Name VARCHAR(255) NOT NULL
);
--raum, per gebaeudenummer mit gebaeude verknuepft--
CREATE TABLE Raum (
    Nummer SERIAL PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    GebaeudeNummer INT,
    FOREIGN KEY (GebaeudeNummer) REFERENCES Gebaeude(Nummer)
);
--veranstaltung, Typ als attribut angegeben, da sonst nirgends verwendet--
CREATE TABLE Veranstaltung (
    ID INTEGER PRIMARY KEY, --hinzugefuegt--
	Name VARCHAR(255),
    Typ VARCHAR(255) CHECK(Typ IN ('Labor', 'Übung', 'Vorlesung')),
    Wochentag INTEGER,
    Startzeit TIME,
    Endzeit TIME,
    StudiengangKuerzel VARCHAR(255),
    Fachsemester INT,
    Haeufigkeit VARCHAR(255),
    FOREIGN KEY (StudiengangKuerzel) REFERENCES Studiengang(Kuerzel)
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
