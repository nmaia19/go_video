INSERT INTO usuario(criado_em, email, nome, senha, status) VALUES(current_timestamp() ,'admin@email.com', 'Administrador', '$2a$10$FjtQZMQuy8PhXP5AI0lEbe0f/8WnA8dI9Bg0f1jJtP0yU3c5tC06.', 'ATIVO');
INSERT INTO perfil(id, perfil) VALUES(1, 'ROLE_ADMINISTRADOR');
INSERT INTO usuario_perfis(usuario_id, perfis_id) VALUES (1,1);

INSERT INTO equipamento(criado_em, categoria, descricao, marca, modelo, status, url_foto) VALUES
	(current_timestamp(), 'Câmeras', 'Sensor X-Trans CMOS 5 HS de 5ª geração e X-Processor 5. Para fotógrafos como os cinematógrafos.', 'FUJIFILM' , 'Fuji X-H2S', 'DISPONÍVEL', 'https://fujifilm-x.com/wp-content/uploads/2022/05/sgew_x-h2s_thum-1.png'),
    (current_timestamp(), 'Câmeras', 'Câmera fotográfica com excelente ciência de cores.', 'FUJIFILM' , 'Fuji X-T30II', 'DISPONÍVEL', 'https://fujifilm-x.com/wp-content/uploads/2021/09/x-t30-ii_thum_bxyg.jpg'),
    (current_timestamp(), 'Lentes', 'Designed to capture images rich in perspective, this ultra wide-angle lens with its extreme angle of view is the ideal choice.', 'FUJINON' , 'XF14mmF2.8 R', 'DISPONÍVEL', 'https://fujifilm-x.com/wp-content/uploads/2016/10/1_thum_xf14mmf2-8-r.jpg'),
	(current_timestamp(), 'Lentes', 'High-performance cinema lens.', 'FUJINON' , 'MKX18-55mmT2.9', 'DISPONÍVEL', 'https://fujifilm-x.com/wp-content/uploads/2018/02/1_thum_mkx18-55mmt2-9.jpg'),
	(current_timestamp(), 'Acessórios', 'Stereo Microphone.', 'FUJIFILM' , 'MIC-ST1', 'DISPONÍVEL', 'https://fujifilm-x.com/wp-content/uploads/2019/10/stereo-microphone_thum.jpg');

