-- HSQLDB index creation script file, schema version 1
-- set to your schema
SET AUTOCOMMIT TRUE;

CREATE UNIQUE INDEX IDX_ENT_NAME ON PLANT_ENTITY (NAME);
CREATE UNIQUE INDEX IDX_REASON_NAME ON REASON (NAME);
CREATE UNIQUE INDEX IDX_MATL_NAME ON MATERIAL (NAME);
CREATE UNIQUE INDEX IDX_SOURCE_NAME ON DATA_SOURCE (NAME);
CREATE UNIQUE INDEX IDX_ER_SOURCE_ID ON EVENT_RESOLVER (SOURCE_ID);
CREATE UNIQUE INDEX IDX_UOM_SYMBOL ON UOM (SYMBOL);
CREATE UNIQUE INDEX IDX_SCHED_NAME ON WORK_SCHEDULE (NAME);
CREATE UNIQUE INDEX IDX_SHIFT_NAME_WS ON SHIFT (NAME, WS_KEY);
CREATE UNIQUE INDEX IDX_TEAM_NAME_WS ON TEAM (NAME, WS_KEY);
CREATE UNIQUE INDEX IDX_COLLECT_NAME ON COLLECTOR (NAME);
CREATE UNIQUE INDEX IDX_EVT_ENT_TYPE_START ON OEE_EVENT (ENT_KEY, EVENT_TYPE, START_TIME);