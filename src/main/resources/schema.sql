create table if not exists schedule_range
(
    id          serial primary key,
    range_start time without time zone not null,
    range_end   time without time zone not null,
    fee         int                    not null
);