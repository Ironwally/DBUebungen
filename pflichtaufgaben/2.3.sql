begin;
select count(*) from lieferung;
delete from lieferant ;
select count(*) from lieferung;
rollback;
select count(*) from lieferung;
commit;

--lieferung ist nach dem löschen von lieferant auch komplett gelöscht,
-- da liefernr in lieferung fremdschlüssel ist in beziehung mit lieferant nr
