CREATE TABLE IF NOT EXISTS Patients (
    PatientID       BIGSERIAL PRIMARY KEY,
    PatientName     VARCHAR(255) NOT NULL,
    DOB             DATE NOT NULL,
    Gender          VARCHAR(20) NOT NULL,
    Address         VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS Doctors (
   DoctorID        BIGSERIAL PRIMARY KEY,
   DoctorName      VARCHAR(255) NOT NULL,
   PhoneNumber     VARCHAR(50) NOT NULL,
   Department      VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS Appointments (
    AppointmentID   BIGSERIAL PRIMARY KEY,
    PatientID       BIGINT NOT NULL,
    DoctorID        BIGINT NOT NULL,
    ApptTime        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    Status          VARCHAR(50) NOT NULL,

    CONSTRAINT fk_appointments_patient
        FOREIGN KEY (PatientID)
            REFERENCES Patients(PatientID)
            ON DELETE CASCADE,

    CONSTRAINT fk_appointments_doctor
        FOREIGN KEY (DoctorID)
            REFERENCES Doctors(DoctorID)
            ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Prescriptions (
    PrescriptionID      BIGSERIAL PRIMARY KEY,
    PrescriptionDate    DATE NOT NULL,
    PatientID           BIGINT NOT NULL,
    PrescriptionName    VARCHAR(100) NOT NULL,
    Dose                VARCHAR(50) NOT NULL,
    Quantity            INTEGER NOT NULL,
    Refills             SMALLINT NOT NULL DEFAULT 0,
    PrescriptionStatus  VARCHAR(10) NOT NULL CHECK(PrescriptionStatus IN ('Processing','Filled', 'Completed')),

    CONSTRAINT fk_prescriptions_patient
        FOREIGN KEY (PatientID)
            REFERENCES Patients(PatientID)
            ON DELETE CASCADE

);


CREATE TABLE IF NOT EXISTS MedicalRecords (
    PatientID       BIGINT PRIMARY KEY,
    DoctorID        BIGINT NOT NULL,
    PrescriptionID  BIGINT NOT NULL,
    VisitDate       DATE NOT NULL,

    CONSTRAINT fk_records_patient
        FOREIGN KEY (PatientID)
            REFERENCES Patients(PatientID)
            ON DELETE CASCADE,

    CONSTRAINT fk_records_doctor
        FOREIGN KEY (DoctorID)
            REFERENCES Doctors(DoctorID)
            ON DELETE CASCADE,

    CONSTRAINT fk_records_prescription
        FOREIGN KEY (PrescriptionID)
            REFERENCES Prescriptions(PrescriptionID)
            ON DELETE CASCADE
);