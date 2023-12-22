select auftrnr, datum from auftrag a 
	where kundnr in 
		(select k.nr from kunde k where k.name = 'Fahrrad Shop')
;

select * from kunde k;

select auftrnr, datum from auftrag a 
	where kundnr = some
		(select k.nr from kunde k where k.name = 'Fahrrad Shop')
	;

select auftrnr, datum from auftrag a 
	where exists 
		(select k.nr from kunde k 
		where k.name = 'Fahrrad Shop' 
		and a.kundnr = k.nr
		)
;

select kundnr, count(auftrnr) as anzahl, min(datum) as von, max(datum) as bis 
	from auftrag a 
	group by kundnr 
	order by kundnr
;

select kundnr, count(auftrnr) as anzahl, min(datum) as von, max(datum) as bis 
	from auftrag a 
	group by kundnr 
	having count(auftrnr) = 1
	order by kundnr
;
	
select k.nr, k.name, a.auftrnr 
	from kunde k 
	left join auftrag a on a.kundnr  = k.nr 
	order by k.nr
;

select k.nr, k.name, a.auftrnr, p.name from kunde k 
	left join auftrag a on a.kundnr  = k.nr 
	join personal p on a.persnr = p.persnr
	order by k.nr
;

select k.name, sum(a2.gesamtpreis) from kunde k 
	join auftrag a on k.nr = a.kundnr
	join auftragsposten a2 on a.auftrnr = a2.auftrnr
	group by k.nr
	order by sum(a2.gesamtpreis) desc
	fetch first 1 row only
	;

with kundenSumme as (
	select k.name, sum(a2.gesamtpreis) from kunde k 
	join auftrag a on k.nr = a.kundnr
	join auftragsposten a2 on a.auftrnr = a2.auftrnr
	group by k.nr 
)
select * from kundenSumme 
order by sum desc
fetch first row only
;

create view kundenUmsatz as 
	with kundenSumme as (
	select k.name, sum(a2.gesamtpreis) from kunde k 
	join auftrag a on k.nr = a.kundnr
	join auftragsposten a2 on a.auftrnr = a2.auftrnr
	group by k.nr 
)
select * from kundenSumme 
order by sum desc
;

select * from kundenumsatz;

drop view if exists kundenUmsatz;