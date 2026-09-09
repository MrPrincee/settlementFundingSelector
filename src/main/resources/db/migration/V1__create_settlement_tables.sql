create table settlement_requests (
    id uuid primary key,
    available_balance numeric(19, 2) not null,
    total_settlement_consumed numeric(19, 2) not null,
    total_expected_fee numeric(19, 2) not null,
    created_at timestamp with time zone not null
);


create table settlement_instructions (
    id bigserial primary key,
    request_id uuid not null,
    instruction_reference varchar(255) not null,
    instruction_amount numeric(19, 2) not null,
    expected_fee numeric(19, 2) not null,
    selected boolean not null,

    constraint fk_instruction_request foreign key (request_id) references
        settlement_requests(id) on delete cascade
);