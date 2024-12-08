CREATE TABLE IF NOT EXISTS topicos(
    id BIGINT AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL UNIQUE,
    mensaje  VARCHAR(350) NOT NULL UNIQUE,
    fecha_creacion DATE NOT NULL,
    estado VARCHAR(30) NOT NULL,
    autor_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,
    activo TINYINT,
    PRIMARY KEY(id),
    FOREIGN KEY(autor_id) REFERENCES usuarios(id),
    FOREIGN KEY(curso_id) REFERENCES cursos(id)
);