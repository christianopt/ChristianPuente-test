create database TestData
go
Use TestData
go

create table dbo.Persona (
personaId bigint Identity primary key,
nombre varchar(250), 
genero varchar(50),
edad int,
identificacion varchar(25),
direccion varchar(250), 
telefono varchar(25),
);
create table dbo.Cliente(
clienteId bigint primary key Foreign Key references dbo.Persona (personaId),
contrasena varchar(100), 
estado varchar(25)
);
create table dbo.Cuenta(
cuentaId bigint Identity primary key,
numeroCuenta varchar(150) not null, 
tipoCuenta varchar(50),
saldoInicial float, 
estado varchar(25),
clienteId bigint not null Foreign Key references dbo.Cliente (clienteId),
);
create table dbo.Movimientos(
movimientosId bigint Identity primary key,
fecha datetime default getdate(),
tipoMovimiento varchar(50), 
valor float, 
saldo float,
cuentaId bigint not null Foreign Key  references dbo.Cuenta (cuentaId),
estado varchar(25)
);
