CREATE TABLE example_table
(
    id                    TEXT PRIMARY KEY,
    dates                 DATE[]        NOT NULL,
    timestamps            TIMESTAMP[]   NOT NULL,
    uuids                 UUID[]        NOT NULL,
    jsons                 JSONB[]       NOT NULL,
    date                  DATE          NOT NULL,
    timestamp             TIMESTAMP     NOT NULL,
    uuid                  UUID          NOT NULL,
    json                  JSONB         NOT NULL,
    offsettimestamp       timestamptz   NOT NULL,
    offsettimestamp_array timestamptz[] NOT NULL
);
