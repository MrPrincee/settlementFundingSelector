create index idx_instructions_request_id
    on settlement_instructions(request_id);

create index idx_requests_created_at
    on settlement_requests(created_at desc);