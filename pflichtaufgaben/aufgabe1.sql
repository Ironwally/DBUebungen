--1.1--
select * from teilestamm;
--1.2--
select * from teilestamm t where LOWER(bezeichnung) like '%city%';
--1.3--
select * from auftragsposten order by gesamtpreis desc
fetch first 1 row only; --or-- limit 1;
--1.4--
select count(*) from kunde
	union all select count(*) from personal
	union all select count(*) from teilestamm;
--1.5--	
select min(datum) as von, max(datum) as bis from auftrag;
--1.6--
select * from auftrag where auftrnr = 2; -- Auftrag 2
select name from kunde where nr = 3; -- Maier Ingrid
select name from personal p where persnr = 5; -- Johanna Koster
select name from personal p where persnr = (select vorgesetzt from personal p2 where persnr = 5); -- Maria Forter
--or--
select p.name as personal, k.name as kunde, p2.name as vorgesetzter 
	from auftrag a, kunde k, personal p, personal p2 
	where 
		p.persnr = a.persnr 
		and k.nr = a.kundnr
		and p2.persnr = p.vorgesetzt;
--or--
select p.name as personal, k.name as kunde, p2.name from auftrag a 
join kunde k on k.nr = a.kundnr  
join personal p on p.persnr = a.persnr
join personal p2 on p2.persnr = p.vorgesetzt;
--or--
select p.name, k.name from auftrag a, kunde k, personal p  
	where a.auftrnr = 2 
	and a.persnr = p.persnr 
	and a.kundnr = k.nr;
--or--
select name from personal p where persnr = (select vorgesetzt from personal p2, auftrag a 
	where a.auftrnr = 2 
	and p2.persnr = a.persnr
	);
--1.7--
select t.bezeichnung, t.teilnr as "t.teilnr", l.teilnr as "l.teilnr", l.bestand from lager l, teilestamm t  
    where t.teilnr = l.teilnr 
        and bestand > 0 
    order by l.bestand asc
    ;
--or--
select * from lager l where l.bestand>0 order by l.bestand asc;
--1.8--
select distinct * from lieferung l where bestellt > 0 order by teilnr desc;
--or--
select distinct * from lieferung l
	order by teilnr desc;
--1.9--
select bezeichnung as Teilbezeichnung, teilnr as teilnr, preis as preis from teilestamm t where preis > 30;
--1.10--
select * from teilestruktur t where oberteilnr = 300001 and anzahl > 100 and einheit = 'CM' ;
