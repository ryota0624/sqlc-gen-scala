CREATE TABLE example_table (
                               id SERIAL PRIMARY KEY,
                               dates DATE[] NOT NULL,         -- DATE 型の配列
                               timestamps TIMESTAMP[] NOT NULL, -- TIMESTAMP 型の配列
                               uuids UUID[] NOT NULL          -- UUID 型の配列
);
