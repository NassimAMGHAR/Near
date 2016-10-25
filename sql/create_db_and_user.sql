CREATE USER near WITH
	LOGIN
	NOSUPERUSER
	CREATEDB
	NOCREATEROLE
	INHERIT
	REPLICATION
	CONNECTION LIMIT -1
	PASSWORD 'xxxxxx';

CREATE DATABASE near
    WITH
    OWNER = near
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;