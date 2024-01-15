begin;
create type veranstaltungsTyp as enum('labor', 'uebung', 'vorlesung');

create table Studiengang(
	kuerzel varchar(255) primary key,
	name varchar(255) not null
);
create table Stundenplan(
	id int primary key,
	studiengangkuerzel varchar(255),
	foreign key (studiengangkuerzel) references studiengang(kuerzel)
);
create table Veranstaltung(
	id int primary key, -- man koennte auch einfach schreiben UNIQUE NOT NULL, bewirkt das Gleiche --
	wochentag int not null,
	startzeit date not null,
	endzeit date not null,
	haeufigkeit int not null,
	studiengang varchar(255) not null,
	fachsemester varchar(255), -- kein not null, da vielleicht fachsemester unabhaengig, haengt von echter implementierung ab --
	name varchar(255) not null,
	typ veranstaltungsTyp not null,
	foreign key (id) references stundenplan(id),
	foreign key (studiengang) references studiengang(kuerzel)
);
create table Gebaeude(
	nummer int primary key,
	name varchar(255)
);
create table Raum(
	nummer int primary key,
	name varchar(255),
	gebaeudeNummer int not null,
	foreign key (gebaeudeNummer) references Gebaeude(nummer),
	foreign key (nummer) references veranstaltung(id)
);
create table professor_mitarbeiter(
	id int primary key,
	buero int not null,
	sprechzeiten date not null
	-- foreign key (id) references dozent(id) --> oder. <--
);
create table dozent(
	id int primary key,
	name varchar(255) not null,
	vorname varchar(255) not null,
	akGrad varchar(255), -- kann ja auch keinen haben --
	email varchar(255) not null, -- nicht auf einen konkreten email typen spezifiziert, da vermutet dass, Rahmen des Projektes spraengen --
	professor_mitarbeiter int, -- null, wenn dozent kein professor_mitarbeiter ist. --
	foreign key (professor_mitarbeiter) references professor_mitarbeiter(id), --> entweder. <--
	foreign key (id) references veranstaltung(id)
);

rollback;
commit;

-- created types droppen (deleten): drop type veranstaltungsTyp;