PRAGMA foreign_keys=OFF;
BEGIN TRANSACTION;

CREATE TABLE categoria(
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  nombre VARCHAR(30),
  descripcion TEXT
);

CREATE TABLE nivel(
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  nombre VARCHAR(30),
  descripcion TEXT
);

CREATE TABLE palabra(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre VARCHAR(30),
    imagen TEXT,
    descripcion TEXT
);

CREATE TABLE nivel_categoria(
  id_categoria INTEGER REFERENCES categoria,
  id_nivel INTEGER REFERENCES nivel,
  PRIMARY KEY(id_categoria,id_nivel)
);

CREATE TABLE nivel_palabra(
  id_palabra INTEGER REFERENCES palabra,
  id_nivel INTEGER REFERENCES nivel,
  PRIMARY KEY(id_palabra,id_nivel)
);

CREATE TABLE categoria_palabra(
  id_palabra INTEGER REFERENCES palabra,
  id_categoria INTEGER REFERENCES categoria,
  PRIMARY KEY(id_palabra,id_categoria)
);

COMMIT;