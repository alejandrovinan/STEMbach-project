DROP TABLE LeadsProject;
DROP TABLE Project;
DROP TABLE UDCTeacher;
DROP TABLE STEMCoordinator;
DROP TABLE Faculty;
DROP TABLE CenterHistory;
DROP TABLE School;
DROP TABLE CenterSTEMCoordinator;
DROP TABLE Biennium;

CREATE TABLE Faculty (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(60) COLLATE latin1_bin NOT NULL,
    CONSTRAINT FacultyPK PRIMARY KEY (id),
    CONSTRAINT FacultyNameUniqueKey UNIQUE (name)
) ENGINE = InnoDB;

CREATE INDEX FacultyIndexByName on Faculty (name);

CREATE TABLE UDCTeacher (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(60) COLLATE latin1_bin NOT NULL,
    surname VARCHAR(60) NOT NULL,
    secondSurname VARCHAR(60) NOT NULL,
    role TINYINT NOT NULL,
    email VARCHAR(60) NOT NULL,
    dni VARCHAR(60) NOT NULL,
    password VARCHAR(60) NOT NULL,
    facultyId BIGINT NOT NULL,
    CONSTRAINT UDCTeacherPK PRIMARY KEY (id),
    CONSTRAINT UDCteacherEmailUniqueKey UNIQUE (email),
    CONSTRAINT UDCteacherFacultyIdFK FOREIGN KEY (facultyId)
        REFERENCES Faculty (id)
) ENGINE = InnoDB;

CREATE INDEX UDCTeacherIndexByEmail ON UDCTeacher (email);

CREATE TABLE STEMCoordinator (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(60) COLLATE latin1_bin NOT NULL,
    surname VARCHAR(60) NOT NULL,
    secondSurname VARCHAR(60) NOT NULL,
    role TINYINT NOT NULL,
    email VARCHAR(60) NOT NULL,
    password VARCHAR(60) NOT NULL,
    CONSTRAINT STEMCoordinatorPK PRIMARY KEY (id),
    CONSTRAINT STEMCoordinatorEmailUniqueKey UNIQUE (email)
) ENGINE = InnoDB;

CREATE INDEX STEMCoordinatorIndexByEmail ON STEMCoordinator (email);

CREATE TABLE School (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(60) COLLATE latin1_bin NOT NULL,
    location VARCHAR(60) COLLATE latin1_bin NOT NULL,
    CONSTRAINT SchoolPK PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE CenterSTEMCoordinator (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(60) COLLATE latin1_bin NOT NULL,
    surname VARCHAR(60) NOT NULL,
    secondSurname VARCHAR(60) NOT NULL,
    role TINYINT NOT NULL,
    email VARCHAR(60) NOT NULL,
    password VARCHAR(60) NOT NULL,
    CONSTRAINT CenterSTEMCoordinatorPK PRIMARY KEY (id),
    CONSTRAINT CenterSTEMCoordinatorEmailUniqueKey UNIQUE (email)
) ENGINE = InnoDB;

CREATE INDEX CenterSTEMCoordinatorIndexByEmail ON CenterSTEMCoordinator (email);

CREATE TABLE CenterHistory (
    id BIGINT NOT NULL AUTO_INCREMENT,
    centerSTEMCoordinatorId BIGINT NOT NULL,
    schoolId BIGINT NOT NULL,
    startDate DATETIME NOT NULL,
    endDate DATETIME DEFAULT NULL,
    CONSTRAINT CenterHistoryPK PRIMARY KEY (id),
    CONSTRAINT centerSTEMCoordinatorIdFK FOREIGN KEY (centerSTEMCoordinatorId)
                           REFERENCES CenterSTEMCoordinator (id),
    CONSTRAINT schoolIdFK FOREIGN KEY (schoolId)
                           REFERENCES School (id)
) ENGINE = InnoDB;

CREATE TABLE Biennium (
    id BIGINT NOT NULL AUTO_INCREMENT,
    dateRange VARCHAR(20) NOT NULL,
    CONSTRAINT BienniumPK PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE Project (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(200) NOT NULL,
    observations VARCHAR(200),
    modality TINYINT NOT NULL,
    url VARCHAR(100),
    offerZone TINYINT NOT NULL,
    revised BIT NOT NULL,
    active BIT NOT NULL,
    maxGroups FLOAT NOT NULL,
    studentsPerGroup FLOAT NOT NULL,
    bienniumId BIGINT NOT NULL,
    udcTeacherId BIGINT NOT NULL,
    CONSTRAINT ProjectPK PRIMARY KEY (id),
    CONSTRAINT BienniumIdProjectFK FOREIGN KEY (bienniumId)
                     REFERENCES Biennium (id),
    CONSTRAINT UDCTeacherIdProjectFK FOREIGN KEY (udcTeacherId)
                     REFERENCES UDCTeacher (id)
) ENGINE = InnoDB;

CREATE TABLE LeadsProject (
    id BIGINT NOT NULL AUTO_INCREMENT,
    projectId BIGINT NOT NULL,
    udcTeacherId BIGINT NOT NULL,
    CONSTRAINT LeadsProjectPK PRIMARY KEY (id),
    CONSTRAINT ProjectIdLeadsProjectFK FOREIGN KEY (projectId)
                          REFERENCES Project (id),
    CONSTRAINT UDCTeacherIdLeadsProjectFK FOREIGN KEY (udcTeacherId)
                          REFERENCES UDCTeacher (id)
) ENGINE = InnoDB;

