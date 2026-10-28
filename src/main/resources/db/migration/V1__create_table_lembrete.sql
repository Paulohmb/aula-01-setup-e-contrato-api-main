CREATE TABLE lembrete (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descricao VARCHAR(255),
    data_hora TIMESTAMP NOT NULL,
    prioridade VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    criado_em TIMESTAMP,
    atualizado_em TIMESTAMP
);
