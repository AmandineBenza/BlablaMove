CREATE DATABASE dbName
ON (
  NAME = BBM_DB1,
  FILENAME = '/BBM_DB1.mdf'
)
LOG ON (
  NAME = dbName_log,
  FILENAME = '/BBM_DB1.ldf'
)