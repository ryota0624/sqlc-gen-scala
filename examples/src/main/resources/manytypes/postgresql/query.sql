-- name: InsertExample :exec
INSERT INTO example_table (
    id,
    dates,
    timestamps,
    uuids,
    jsons,
    date,
    timestamp,
    uuid,
    json,
    offsettimestamp,
    offsettimestamp_array
) VALUES (
    $1,
    $2,
    $3,
    $4,
    $5 :: jsonb[],
    $6,
    $7,
    $8,
    $9 :: jsonb,
    $10,
    $11
);

-- name: SelectExample :one
SELECT * FROM example_table
WHERE id = $1;
