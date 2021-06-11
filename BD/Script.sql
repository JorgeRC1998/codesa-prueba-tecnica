create table PT_ROLES(
  id number(9) not null,
  nombre varchar2(25) not null,
  fecha_creacion date,
  activo char(1),
  primary key (id)
);

create table PT_USUARIOS(
  id number(9) not null,
  id_rol number(9) not null,
  nombre varchar2(50) not null,
  fecha_creacion date,
  activo char(1),
  primary key (id),
  foreign key (id_rol) references PT_ROLES (id)
);

alter table PT_ROLES
add constraint CK_PT_ROLES_ACTIVO
check (activo in ('S', 's', 'N', 'n'));

alter table PT_USUARIOS
add constraint CK_PT_USUARIOS_ACTIVO
check (activo in ('S', 's', 'N', 'n'));

create sequence seq_roles
    start with 1
    increment by 1
    minvalue 1 
    maxvalue 99
    nocycle;
    
create sequence seq_usuarios
    start with 1
    increment by 1
    minvalue 1 
    maxvalue 9999
    nocycle;

select seq_roles.currval from dual;
select seq_roles.nextval from dual;
select seq_usuarios.currval from dual;
select seq_usuarios.nextval from dual;

insert into PT_ROLES values (seq_roles.currval, 'ADMINISTRADOR', '06-06-2021', 'S');
insert into PT_ROLES values (seq_roles.nextval, 'AUDITOR', '06-06-2021', 'S');
insert into PT_ROLES values (seq_roles.nextval, 'AUXILIAR', '06-06-2021', 'S');
insert into PT_ROLES values (seq_roles.nextval, 'CONTADOR', '06-06-2021', 'S');
commit;

insert into PT_USUARIOS values (seq_usuarios.nextval, 1, 'Prueba1', '06-06-2021', 'S');
insert into PT_USUARIOS values (seq_usuarios.nextval, 1, 'Prueba2', '06-06-2021', 'S');
commit;

select * from pt_roles;
select * from pt_usuarios;
truncate TABLE pt_usuarios;
select * from pt_usuarios where nombre like '%Jor%';
