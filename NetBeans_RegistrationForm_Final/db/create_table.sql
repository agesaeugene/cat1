
CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    mobile TEXT NOT NULL,
    gender TEXT NOT NULL,
    dob TEXT NOT NULL,
    address TEXT NOT NULL
);
