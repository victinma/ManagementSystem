create database bdsistema

use bdsistema

create table usuarios(
nome_USUARIO varchar(40) not null primary key,
senha_USUARIO varchar(12) not null,
permissao_USUARIO int not null
);

create table clientes(
cpf_CLIENTE varchar(11) not null primary key,
nome_CLIENTE varchar(120) not null,
dataNascimento_CLIENTE varchar(20) not null,
endereco_CLIENTE varchar(150) not null,
bairro_CLIENTE varchar(60) not null,
cidade_CLIENTE varchar(60) not null, 
telefone_CLIENTE varchar(11) not null,
email_CLIENTE varchar(150) not null,
foto_CLIENTE varchar(200),
status_CLIENTE int not null
) ;

create table produtos(
codigo_PRODUTO int not null AUTO_INCREMENT primary key,
nome_PRODUTO varchar(100) not null,
descricao_PRODUTO text,
quantidadeEstoque_PRODUTO int not null,
valorCompra_PRODUTO float not null,
valorVenda_PRODUTO float not null,
foto_PRODUTO varchar(200),
codigoCategoria_PRODUTO int not null
);

create table categorias(
codigo_CATEGORIA int not null AUTO_INCREMENT primary key,
nome_CATEGORIA varchar(40) not null
);
