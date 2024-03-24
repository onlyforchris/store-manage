drop
database if exists water_store;
drop
user if exists 'water_store'@'%';
create
database water_store default character set utf8mb4 collate utf8mb4_unicode_ci;
use
water_store;
create
user 'water_store'@'%' identified by 'water_store_123456';
grant all privileges on water_store.* to
'water_store'@'%';
flush
privileges;