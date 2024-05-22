create table public.cb_tbl_person
(
    name           varchar(200) not null,
    gender         varchar(10),
    age            integer,
    identification varchar(50)  not null,
    address        varchar(500),
    phone          varchar(50),
    id_person      serial
        constraint cb_tbl_person_pk
            primary key
);

comment on column public.cb_tbl_person.name is 'nombre personar';

comment on column public.cb_tbl_person.gender is 'genero persona';

comment on column public.cb_tbl_person.age is 'edad persona';

comment on column public.cb_tbl_person.identification is 'identificaion de la persona';

comment on column public.cb_tbl_person.address is 'domicilio de la persona';

comment on column public.cb_tbl_person.phone is 'telefono';

comment on column public.cb_tbl_person.id_person is 'identificador de registro';

comment on constraint cb_tbl_person_pk on public.cb_tbl_person is 'identificador unico';

alter table public.cb_tbl_person
    owner to "cloud-admin";

create table public.cb_tbl_client
(
    id_client   serial
        constraint cb_tbl_client_pk
            primary key,
    id_person   serial
        constraint cb_tbl_client_cb_tbl_person_id_fk
            references public.cb_tbl_person,
    password    varchar(100),
    state       varchar(10) not null,
    last_update timestamp,
    created     timestamp
);

comment on column public.cb_tbl_client.id_client is 'identificador de registro';

comment on constraint cb_tbl_client_cb_tbl_person_id_fk on public.cb_tbl_client is 'union table';

comment on column public.cb_tbl_client.password is 'clave usuario';

comment on column public.cb_tbl_client.state is 'estado cliente';

alter table public.cb_tbl_client
    owner to "cloud-admin";

create index cb_tbl_client_id_tbl_p_client_id_index
    on public.cb_tbl_client (id_client, id_client);

create table public.cb_tbl_account
(
    id_account      serial                   not null
        constraint cb_tbl_account_pk
            primary key,
    account_number  varchar(75)              not null,
    account_type    varchar(12)              not null,
    initial_balance numeric(15, 2) default 0 not null,
    state           varchar(10)              not null,
    id_client       integer                  not null
        constraint cb_tbl_account_cb_tbl_client_id_fk
            references public.cb_tbl_client
);

comment on column public.cb_tbl_account.account_number is 'numero de cuenta';

comment on column public.cb_tbl_account.account_type is 'tipo de cuenta';

comment on column public.cb_tbl_account.initial_balance is 'balance inicial';

comment on column public.cb_tbl_account.state is 'estado de la cuenta';

comment on column public.cb_tbl_account.id_client is 'identificador del cliente';

alter table public.cb_tbl_account
    owner to "cloud-admin";

create index cb_tbl_account_account_number_account_type_index
    on public.cb_tbl_account (account_number, account_type);

create index cb_tbl_account_id_client_index
    on public.cb_tbl_account (id_client);

create table public.cb_tbl_movements
(
    id_movement   serial                              not null
        constraint cb_tbl_movements_pk
            primary key,
    date_movement timestamp default CURRENT_TIMESTAMP not null,
    type_movement varchar(10)                         not null,
    amount        numeric(15, 2)                      not null,
    balance       numeric(15, 2)                      not null,
    id_account    integer                             not null
        constraint cb_tbl_movements_cb_tbl_account_id_fk
            references public.cb_tbl_account
);

comment on column public.cb_tbl_movements.id_movement is 'identificador unico';

comment on column public.cb_tbl_movements.date_movement is 'fecha de registro';

comment on column public.cb_tbl_movements.type_movement is 'tipo de movimiento';

comment on column public.cb_tbl_movements.amount is 'monto';

comment on column public.cb_tbl_movements.balance is 'saldo de cuenta';

comment on column public.cb_tbl_movements.id_account is 'identificador de cuenta';

alter table public.cb_tbl_movements
    owner to "cloud-admin";

create index cb_tbl_movements_tbl_cli_id_date_movement_index
    on public.cb_tbl_movements (id_account, date_movement);

create or replace view public.cb_vw_report_movements as
select cb_tbl_movements.id_movement,
       cb_tbl_movements.date_movement,
       cb_tbl_client.id_client,
       cb_tbl_person.name,
       cb_tbl_account.account_number,
       cb_tbl_account.account_type,
       cb_tbl_account.initial_balance,
       cb_tbl_account.state,
       cb_tbl_movements.type_movement,
       cb_tbl_movements.amount,
       cb_tbl_movements.balance
from public.cb_tbl_movements
         inner join public.cb_tbl_account on cb_tbl_movements.id_account = cb_tbl_account.id_account
         inner join public.cb_tbl_client on cb_tbl_account.id_client = cb_tbl_client.id_client
         inner join public.cb_tbl_person on cb_tbl_client.id_person = cb_tbl_person.id_person;
