/*
SQL-script to generate the tables

All tables are named as

tvAnyTime_ + "Name of the corresponding table class of the BBC-API"

e.g.:
tvAnyTime_ProgramInformationTable
tvAnyTime_SegmentInformationTable
tvAnyTime_ContentReferencingTable

*/

-- table for development purposes
CREATE TABLE tvAnyTime_MetaInformation
(
   ID INTEGER AUTO_INCREMENT PRIMARY KEY,
   ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   contentReferencingTable BLOB,
   programInformationTable BLOB,
   programLocationTable BLOB
);

CREATE TABLE tvAnyTime_ProgramInformationTable
(
   ID INTEGER AUTO_INCREMENT PRIMARY KEY,
   ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   tablename VARCHAR(100),
   programInformationTable BLOB
);

CREATE TABLE tvAnyTime_SegmentInformationTable
(
   ID INTEGER AUTO_INCREMENT PRIMARY KEY,
   ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   filename VARCHAR(100),
   tablename VARCHAR(100),
   segmentInformationTable BLOB
);

CREATE TABLE tvAnyTime_ContentReferencingTable
(
   ID INTEGER AUTO_INCREMENT PRIMARY KEY,
   ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   tablename VARCHAR(100),
   contentReferencingTable BLOB
);