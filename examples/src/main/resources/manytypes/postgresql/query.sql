-- name: InsertExample :exec
INSERT INTO example_table (dates, timestamps, uuids)
VALUES ($1, $2, $3);

-- name: SelectExample :one
SELECT * FROM example_table
WHERE id = $1;
