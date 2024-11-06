CREATE DATABASE paperlessdb;
CREATE TABLE document (
                          id SERIAL PRIMARY KEY,
                          title VARCHAR(255),
                          content TEXT,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);