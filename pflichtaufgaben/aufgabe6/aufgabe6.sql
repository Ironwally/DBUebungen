--
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
    Email VARCHAR(255) NOT NULL
);

--Erweiterung von Dozent um Daten welche auf Professoren und Mitarbeiter beschraenkt sind--
CREATE TABLE Professor_Mitarbeiter (
    ID INT PRIMARY KEY,
    Buero VARCHAR(255),
    Sprechzeiten VARCHAR(255),
    FOREIGN KEY (ID) REFERENCES Dozent(ID)
);

CREATE TABLE Gebaeude (
    Nummer SERIAL PRIMARY KEY,
    Name VARCHAR(255) NOT NULL
);

CREATE TABLE Raum (
    Nummer SERIAL PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    GebaeudeNummer INT,
    FOREIGN KEY (GebaeudeNummer) REFERENCES Gebaeude(Nummer)
);

CREATE TABLE Veranstaltung (
    Name VARCHAR(255) PRIMARY KEY,
    Typ VARCHAR(255) CHECK(Typ IN ('Labor', 'Übung', 'Vorlesung')),
    Wochentag VARCHAR(255),
    Startzeit TIME,
    Endzeit TIME,
    Raum1 INT,
    Raum2 INT,
    Dozent1 INT,
    Dozent2 INT,
    StudiengangKuerzel VARCHAR(255),
    Fachsemester INT,
    Haeufigkeit VARCHAR(255),
    FOREIGN KEY (Raum1) REFERENCES Raum(Nummer),
    FOREIGN KEY (Raum2) REFERENCES Raum(Nummer),
    FOREIGN KEY (Dozent1) REFERENCES Dozent(ID),
    FOREIGN KEY (Dozent2) REFERENCES Dozent(ID),
    FOREIGN KEY (StudiengangKuerzel) REFERENCES Studiengang(Kuerzel)
);

CREATE TABLE Stundenplan (
    ID SERIAL PRIMARY KEY,
    StudiengangKuerzel VARCHAR(255),
    FOREIGN KEY (StudiengangKuerzel) REFERENCES Studiengang(Kuerzel)
);

CREATE TABLE Stundenplan_Veranstaltung (
    StundenplanID INT,
    VeranstaltungName VARCHAR(255),
    PRIMARY KEY (StundenplanID, VeranstaltungName),
    FOREIGN KEY (StundenplanID) REFERENCES Stundenplan(ID),
    FOREIGN KEY (VeranstaltungName) REFERENCES Veranstaltung(Name)
);
