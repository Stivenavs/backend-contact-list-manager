-- =====================================================================
-- Contact Manager - Script de base de datos (PostgreSQL)
-- =====================================================================
-- Servidor : localhost
-- Puerto   : 5432
-- Base     : contactmanager
-- Esquema  : contactmanager
--
-- Orden de ejecucion:
--   1. Base de datos
--   2. Esquema
--   3. Tablas e indices (usr_user, cnt_contact)
--   4. Verificacion
--
-- El script se puede volver a ejecutar sin borrar datos: usa
-- IF NOT EXISTS en esquema, tablas e indices.
-- =====================================================================


-- =====================================================================
-- 1. BASE DE DATOS
-- =====================================================================
-- Ejecutar conectado a la base "postgres" con un usuario con permisos.
-- CREATE DATABASE no admite IF NOT EXISTS: si la base ya existe,
-- omite esta linea.
CREATE DATABASE contactmanager;

-- A partir de aqui hay que estar conectado a la base "contactmanager".
--   psql            : \c contactmanager
--   DBeaver/pgAdmin : cambiar la conexion activa a la base contactmanager


-- =====================================================================
-- 2. ESQUEMA
-- =====================================================================
CREATE SCHEMA IF NOT EXISTS contactmanager;


-- =====================================================================
-- 3. TABLAS
-- =====================================================================

-- ---------------------------------------------------------------------
-- 3.1 usr_user : usuarios
-- ---------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS contactmanager.usr_user  (
	user_id serial4 NOT NULL,
	document_type varchar(50) NOT NULL,
	document_number varchar(50) NOT NULL,
	first_name varchar(150) NOT NULL,
	last_name varchar NULL,
	email varchar(150) NOT NULL,
	phone varchar(30) NULL,
	birth_date date NULL,
	password_hash varchar(255) NOT NULL,
	avatar_url text NULL,
	status varchar(30) DEFAULT 'active'::character varying NOT NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,	
	CONSTRAINT usr_user_email_key UNIQUE (email),
	CONSTRAINT usr_user_pkey PRIMARY KEY (user_id)
);
CREATE INDEX idx_user_document ON contactmanager.usr_user USING btree (document_type, document_number);
CREATE INDEX idx_user_email ON contactmanager.usr_user USING btree (email);


-- ---------------------------------------------------------------------
-- 3.2 cnt_contact : contactos
-- ---------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS contactmanager.cnt_contact (
	contact_id serial4 NOT NULL,
	first_name varchar(150) NOT NULL,
	last_name varchar(150) NULL,
	phone varchar(30) NULL,
	email varchar(150) NOT NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	CONSTRAINT cnt_contact_pkey PRIMARY KEY (contact_id)
);

-- Si cnt_contact ya existe sin la restriccion de correo unico, agregarla
-- con la siguiente sentencia (falla si ya hay correos repetidos):
-- ALTER TABLE contactmanager.cnt_contact
--	ADD CONSTRAINT cnt_contact_email_key UNIQUE (email);

