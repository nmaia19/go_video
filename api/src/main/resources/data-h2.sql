INSERT INTO usuario(criado_em, email, nome, senha, status) VALUES
    (current_timestamp() ,'admin@email.com', 'Administrador', '$2a$10$FjtQZMQuy8PhXP5AI0lEbe0f/8WnA8dI9Bg0f1jJtP0yU3c5tC06.', 'ATIVO'),
    (current_timestamp() ,'colaborador@email.com', 'Colaborador', '$2a$10$FjtQZMQuy8PhXP5AI0lEbe0f/8WnA8dI9Bg0f1jJtP0yU3c5tC06.', 'ATIVO');

INSERT INTO perfil(id, perfil) VALUES
    (1, 'ROLE_ADMINISTRADOR'),
    (2, 'ROLE_COLABORADOR');

INSERT INTO usuario_perfis(usuario_id, perfis_id) VALUES
    (1,1),
    (2,2);