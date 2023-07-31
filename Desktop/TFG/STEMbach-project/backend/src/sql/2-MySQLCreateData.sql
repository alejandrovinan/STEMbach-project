-- ----------------------------------------------------------------------------
-- Put here INSERT statements for inserting data required by the application
-- in the "paproject" database.
/*-------------------------------------------------------------------------------*/
INSERT INTO Faculty (name) VALUES ('Facultad de Informática');

# Password is stemcoordinatorpass
INSERT INTO stemcoordinator (NAME, SURNAME, SECONDSURNAME, ROLE, EMAIL, PASSWORD)
    VALUES ('Alejandro', 'Viñán', 'Bértoa',1, 'stemcoordinator@udc.es', '$2a$12$YZ/vjUo2KJeiQFMx1tPNbeQX8MwsXn6GxBYITK6QanU6MuaEUwYXm');
# Password is susana.ladra
INSERT INTO udcteacher (NAME, SURNAME, SECONDSURNAME,ROLE, EMAIL, DNI, PASSWORD, FACULTYID)
    VALUES ('Susana', 'Ladra', 'González', 0, 'susana.ladra@udc.es', 'desconocido', '$2a$12$cDI696kkYDDpAd3IRs65R.2Ryp4/8XrkJNHd7kVaVhle6AkXGxnkq', 1);

INSERT INTO centerstemcoordinator (name, surname, secondSurname, role, email, password)
    VALUES ('Pepe', 'López', 'López', 2, 'pepe.lope@gmail.es','$2a$12$YZ/vjUo2KJeiQFMx1tPNbeQX8MwsXn6GxBYITK6QanU6MuaEUwYXm' );

INSERT INTO school (name, location)
    VALUES ('Manuel Murguía', 'Arteixo, Acoruña');

INSERT INTO biennium (dateRange)
    VALUES ('2021-2023');