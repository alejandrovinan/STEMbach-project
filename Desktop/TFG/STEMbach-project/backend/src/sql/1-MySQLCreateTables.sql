DROP TABLE IF EXISTS LeadsProject;
DROP TABLE IF EXISTS Request;
DROP TABLE IF EXISTS Project;
DROP TABLE IF EXISTS RecordFile;
DROP TABLE IF EXISTS Judges;
DROP TABLE IF EXISTS Defense;
DROP TABLE IF EXISTS LeadsProjectInstance;
DROP TABLE IF EXISTS ProjectInstance;
DROP TABLE IF EXISTS UDCTeacher;
DROP TABLE IF EXISTS STEMCoordinator;
DROP TABLE IF EXISTS Faculty;
DROP TABLE IF EXISTS CenterHistory;
DROP TABLE IF EXISTS Student;
DROP TABLE IF EXISTS StudentGroup;
DROP TABLE IF EXISTS School;
DROP TABLE IF EXISTS CenterSTEMCoordinator;
DROP TABLE IF EXISTS Biennium;

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
    title VARCHAR(200) NOT NULL,
    description VARCHAR(800) NOT NULL,
    observations VARCHAR(800),
    modality TINYINT NOT NULL,
    url VARCHAR(100),
    offerZone TINYINT NOT NULL,
    revised BIT NOT NULL,
    active BIT NOT NULL,
    maxGroups FLOAT NOT NULL,
    studentsPerGroup FLOAT NOT NULL,
    bienniumId BIGINT NOT NULL,
    udcTeacherId BIGINT NOT NULL,
    assigned BIT NOT NULL,
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

CREATE TABLE StudentGroup (
    id BIGINT NOT NULL AUTO_INCREMENT,
    hasProject BIT NOT NULL,
    schoolId BIGINT NOT NULL,
    CONSTRAINT StudentGroupPK PRIMARY KEY (id),
    CONSTRAINT SchoolIdStudentGroupFK FOREIGN KEY (schoolId)
                   REFERENCES School (id)
) ENGINE = InnoDB;

CREATE TABLE Student (
     id BIGINT NOT NULL AUTO_INCREMENT,
     name VARCHAR(60) COLLATE latin1_bin NOT NULL,
     surname VARCHAR(60) NOT NULL,
     secondSurname VARCHAR(60) NOT NULL,
     role TINYINT NOT NULL,
     studentGroupId BIGINT,
     schoolId BIGINT NOT NULL,
     dni VARCHAR(60) NOT NULL,
     CONSTRAINT StudentPK PRIMARY KEY (id),
     CONSTRAINT UniqueDniStudent UNIQUE (dni),
     CONSTRAINT StudentGroupIdStudent FOREIGN KEY (studentGroupId)
                     REFERENCES StudentGroup (id),
     CONSTRAINT SchoolIdStudent FOREIGN KEY (schoolId)
                     REFERENCES School(id)
) ENGINE = InnoDB;

CREATE TABLE Request (
    id BIGINT NOT NULL AUTO_INCREMENT,
    requestDate DATETIME NOT NULL,
    status VARCHAR(10) NOT NULL,
    studentGroupId BIGINT NOT NULL,
    projectId BIGINT NOT NULL,
    CONSTRAINT RequestPK PRIMARY KEY (id),
    CONSTRAINT ProjectIdRequest FOREIGN KEY (projectId)
                     REFERENCES Project(id),
    CONSTRAINT StudentGroupIdRequest FOREIGN KEY (studentGroupId)
                     REFERENCES StudentGroup(id)
) ENGINE = InnoDB;

CREATE TABLE ProjectInstance (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    description VARCHAR(800) NOT NULL,
    observations VARCHAR(800),
    modality TINYINT NOT NULL,
    url VARCHAR(100),
    offerZone TINYINT NOT NULL,
    active BIT NOT NULL,
    bienniumId BIGINT NOT NULL,
    udcTeacherId BIGINT NOT NULL,
    studentGroupId BIGINT NOT NULL,
    CONSTRAINT ProjectInstancePK PRIMARY KEY (id),
    CONSTRAINT BienniumProjectInstanceFK FOREIGN KEY (bienniumId)
                            REFERENCES Biennium(id),
    CONSTRAINT UdcTeacherProjectInstanceFK FOREIGN KEY (udcTeacherId)
                             REFERENCES UDCTeacher(id),
    CONSTRAINT StudentGroupIdProjectInstanceFK FOREIGN KEY (studentGroupId)
                             REFERENCES StudentGroup(id)
) ENGINE = InnoDB;

CREATE TABLE LeadsProjectInstance (
    id BIGINT NOT NULL AUTO_INCREMENT,
    projectInstanceId BIGINT NOT NULL,
    udcTeacherId BIGINT NOT NULL,
    CONSTRAINT LeadsProjectInstancePK PRIMARY KEY (id),
    CONSTRAINT ProjectInstanceLeadsProjectInstanceFK FOREIGN KEY (projectInstanceId)
                                  REFERENCES ProjectInstance(id),
    CONSTRAINT UdcTeacherLeadsProjectInstanceFK FOREIGN KEY (udcTeacherId)
                                  REFERENCES UDCTeacher(id)
) ENGINE = InnoDB;

CREATE TABLE Defense (
     id BIGINT NOT NULL AUTO_INCREMENT,
     projectInstanceId BIGINT NOT NULL,
     place VARCHAR(300),
     date DATETIME NOT NULL,
     mark DECIMAL(11, 2),
     observations VARCHAR(500),
     CONSTRAINT DefensePK PRIMARY KEY (id),
     CONSTRAINT DefenseProjectInstanceFK FOREIGN KEY (projectInstanceId)
                     REFERENCES ProjectInstance (id)
) ENGINE = InnoDB;

CREATE TABLE RecordFile (
    id BIGINT NOT NULL AUTO_INCREMENT,
    fileName VARCHAR(255),
    fileData LONGBLOB NOT NULL,
    fileSize BIGINT NOT NULL,
    uploadDate DATETIME NOT NULL,
    fileType VARCHAR(30) NOT NULL,
    defenseId BIGINT NOT NULL,
    CONSTRAINT RecordFilePK PRIMARY KEY (id),
    CONSTRAINT RecordFileDefenseFK FOREIGN KEY (defenseId)
                        REFERENCES Defense (id)
) ENGINE = InnoDB;

CREATE TABLE Judges (
    id BIGINT NOT NULL AUTO_INCREMENT,
    udcTeacherId BIGINT NOT NULL,
    defenseId BIGINT NOT NULL,
    CONSTRAINT JudgesPK PRIMARY KEY (id),
    CONSTRAINT JudgesUdcTeacherFK FOREIGN KEY (udcTeacherId)
                    REFERENCES UDCTeacher (id),
    CONSTRAINT JudgesDefenseFK FOREIGN KEY (defenseId)
                    REFERENCES Defense (id)
) ENGINE = InnoDB;

