select * from teilestamm;

select * from teilestamm t where LOWER(bezeichnung) like '%city%';

select * from auftragsposten order by gesamtpreis desc
fetch first 1 row only;

select count(*) from kunde
	union all select count(*) from personal
	union all select count(*) from teilestamm;
	
select min(datum) as von, max(datum) as bis from auftrag;


select * from auftrag where auftrnr = 2; -- Auftrag 2

select name from kunde where nr = 3; -- Maier Ingrid

select name from personal p where persnr = 5; -- Johanna Koster

select name from personal p where persnr = (select vorgesetzt from personal p2 where persnr = 5); -- Maria Forter


select p.name, k.name from auftrag a, kunde k, personal p  
	where a.auftrnr = 2 
	and a.persnr = p.persnr 
	and a.kundnr = k.nr;
	
select name from personal p where persnr = (select vorgesetzt from personal p2, auftrag a 
	where a.auftrnr = 2 
	and p2.persnr = a.persnr
	);

select t.bezeichnung, t.teilnr as "t.teilnr", l.teilnr as "l.teilnr", l.bestand from lager l, teilestamm t  where t.teilnr = l.teilnr and bestand > 0 order by l.bestand asc;

select distinct * from lieferung l where bestellt > 0 order by teilnr desc;

select bezeichnung as Teilbezeichnung, teilnr as teilnr, preis as preis from teilestamm t where preis > 30;

select * from teilestruktur t where oberteilnr = 300001 and anzahl > 100 and einheit = 'CM' ;
