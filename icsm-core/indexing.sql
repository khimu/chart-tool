
use `faad_campaigns`

CREATE INDEX name_index ON campaigns(name)

use `faad_chart`

--alter table itunes_application change timestamp pull_date varchar (100);

--alter table itunes_application modify pull_date VARCHAR(100); 

--alter table itunes_application add column has_tag int(11);

DROP INDEX timestamp_index ON itunes_application
CREATE INDEX timestamp_index ON itunes_application(pull_date, price_type)

DROP INDEX app_by_app_id_index ON itunes_application
CREATE INDEX app_by_app_id_index ON itunes_application(itunes_app_id)

DROP INDEX old_apps_index ON itunes_application
CREATE INDEX old_apps_index ON itunes_application(dropped, ranking)

CREATE INDEX itunes_featured_app_index ON itunes_application(itunes_featured_app, already_targeted)
CREATE INDEX free_app_movement_index ON itunes_application(id, itunes_app_id)

DROP INDEX all_new_apps_index ON itunes_application
CREATE INDEX all_new_apps_index ON itunes_application(new_top, ranking)

DROP INDEX apps_by_rate_of_change_index ON itunes_application
CREATE INDEX apps_by_rate_of_change_index ON itunes_application(moved)

alter table itunes_application add column modified_on datetime;
alter table itunes_application add column created_on datetime;

alter table itunes_application add column already_targeted int(11);
alter table itunes_application add column itunes_featured_app int(11);
alter table itunes_application modify name VARCHAR(250); 

CREATE INDEX last_batch_index ON itunes_application(last_batch)
CREATE INDEX already_targeted_index ON itunes_application(itunes_app_id, already_targeted)

CREATE INDEX app_by_app_id_pull_date_index ON itunes_application(id, itunes_app_id, pull_date)

CREATE INDEX apps_by_name_index ON itunes_application(name, pull_date, ranking, price_type)
CREATE INDEX apps_by_company_name_index ON itunes_application(company_name, pull_date, ranking, price_type)


