SELECT * FROM emprestimo;
SELECT * FROM equipamento;
SELECT * FROM perfil;
SELECT * FROM usuario;
SELECT * FROM usuario_perfis;

INSERT INTO usuario(email, senha) VALUES('admin@email.com', '$2a$10$FjtQZMQuy8PhXP5AI0lEbe0f/8WnA8dI9Bg0f1jJtP0yU3c5tC06.');

INSERT INTO perfil(id, perfil) VALUES(1, 'ROLE_ADMINISTRADOR');
INSERT INTO perfil(id, perfil) VALUES(2, 'ROLE_VISITANTE');

INSERT INTO usuario_perfis(usuario_id, perfis_id) VALUES (1,1);