DO
$body$
BEGIN
    IF NOT EXISTS(
            SELECT *
            FROM pg_catalog.pg_user
            WHERE usename = 'fis_tm')
    THEN
        CREATE ROLE fis_tm WITH PASSWORD 'Fis!1234';
    END IF;
END
$body$;

ALTER ROLE fis_tm NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION LOGIN;
