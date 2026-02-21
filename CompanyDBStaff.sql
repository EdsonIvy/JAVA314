CREATE DATABASE IF NOT EXISTS companyDB;  -- Create database if it does not exist
USE companyDB;  -- Select the database

CREATE TABLE Staff (
  id CHAR(9) NOT NULL,        -- Staff ID (Primary Key)
  lastName VARCHAR(15),       -- Last name
  firstName VARCHAR(15),      -- First name
  mi CHAR(1),                 -- Middle initial
  address VARCHAR(20),        -- Street address
  city VARCHAR(20),           -- City
  state CHAR(2),              -- State (2 letters)
  telephone CHAR(10),         -- Phone number (10 digits)
  email VARCHAR(40),          -- Email address
  PRIMARY KEY (id)            -- Set id as Primary Key
);

-- Insert first staff record
INSERT INTO Staff (id, lastName, firstName, mi, address, city, state, telephone, email)
VALUES ('S001', 'Smith', 'John', 'A', '123 Main St', 'New York', 'NY', '1234567890', 'john.smith@example.com');

-- Insert second staff record
INSERT INTO Staff (id, lastName, firstName, mi, address, city, state, telephone, email)
VALUES ('S010', 'Smith', 'John', 'A', '123 Main St', 'New York', 'NY', '1234567890', 'john.smith@example.com');

USE companyDB;  -- Make sure we are using companyDB

SELECT * FROM Staff;  -- Display all records to verify in MySQL