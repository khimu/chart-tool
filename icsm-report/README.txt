AppFuse Web Services Archetype
--------------------------------------------------------------------------------
If you're reading this then you've created your new project using Maven and
icsm.  You have only created the shell of an AppFuse Java EE
application.  The project object model (pom) is defined in the file pom.xml.
The application is ready to serve up web services with Enunciate and CXF. The
pom.xml file is pre-defined with Hibernate as a persistence model. To change it,
please read http://appfuse.org/display/APF/Persistence.

To get started, please complete the following steps:

1. Download and install a MySQL 5.x database from 
   http://dev.mysql.com/downloads/mysql/5.0.html#downloads.

2. Run "mvn jetty:run" and view the application at http://localhost:8080.

3. More information can be found at:

   http://appfuse.org/display/APF/AppFuse+QuickStart
   
   
   Testable Query
   


DROP INDEX timestamp_index ON itunes_application
CREATE INDEX timestamp_index ON itunes_application(pull_date, price_type)

DROP INDEX all_new_apps_index ON itunes_application
CREATE INDEX all_new_apps_index ON itunes_application(new_top, ranking)
explain select * from itunes_application ia1 join (select id, max(pull_date) as latest_date from itunes_application where pull_date >= '2012-07-23T12:00:01+0000' and pull_date <= '2012-07-27T12:00:01+0000' and price_type= 'Free' group by itunes_app_id) as ia2 on ia1.id = ia2.id where new_top = 1 and ia1.ranking <= 50 group by ia1.itunes_app_id order by ia1.ranking asc

CREATE INDEX itunes_featured_app_index ON itunes_application(itunes_featured_app, already_targeted)
explain select distinct itunes_app_id from itunes_application where itunes_featured_app = 1 and already_targeted = 0 group by itunes_app_id
explain select distinct * from itunes_application where itunes_featured_app = 1 and already_targeted = 0 group by itunes_app_id

DROP INDEX free_app_movement_index ON itunes_application
CREATE INDEX free_app_movement_index ON itunes_application(id, itunes_app_id)
explain select * from itunes_application ia1 join (select id, sum(moved) as movement from itunes_application where pull_date <= '2012-07-15T12:00:01+0000' and pull_date >= '2012-07-15T12:00:01+0000' and price_type = 'Free' group by id) as ia2 where ia1.id = ia2.id and ia2.movement >= 20 and ia1.itunes_app_id in ('12312312') order by ranking asc
EXPLAIN select * from itunes_application ia1 join (select id, sum(moved) as movement from itunes_application where pull_date <= '2012-07-27T09:27:43-0700' and pull_date >= '2012-07-27T04:26:43-0700' and price_type = 'Free' group by id) as ia2 where ia1.id = ia2.id and ia2.movement >= 20 and ia1.itunes_app_id in ('542511686','542497845','497936366','544099783','536513070','530957474','518042655','530061728','499250722','389801252','523138125','480950048','526641427','516725417','524299475','376481969','538061254','381471023','539014219','521863802','534342529','429047995','538065388','332023892','357218860','333903271','542199873','324684580','284035177','539594893','535886823','469369175','541878076','467810884','506944493','454768104','512939461','310633997','514550808','451658773','295646461','284882215','376101648','304878510','529997671','304904838','509098112','420009108','524820876','363590051','467577200','541916493','526656012','341232718','316126557','397049430','527445936','290638154','284993459','371405542','517845106','504320207','525168034','403639508','282614216','479651754','382617920','332509635','525246631','322439990','469960709','352683833','421254504','403858572','542707716','323229106','502855954','454975562','478364212','509854030','372513032','440045374','457446957','321916506','527860351','297606951','364709193','377304531','511841016','530741872','470272134','307906541','488628250','387771637','322410766','485084223','368494609','418987775','284910350','510461758','444741112','528349318','293622097','490413078','510544616','542448158','471412333','480018989','409807569','494833223','454638411','530001625','429610587','438875956','302584613','494481220','305343404','532604210','359917414','422689480','530003802','439588219','414706506','284708449','335364882','414817704','447188370','428845974','288429040','469489347','448639966','284235722','355554941','379693831','430384234','314487667','298867247','406719683','472073867','511317727','456355158','394613472','284847138','491594085','421997825','333578095','428832518','527263742','479412611','327630330','420744028','371273282','390929278','519817714','518972315','418368641','470044625','375380948','305479724','283646709','466122094','453298978','302324249','514520676','284971959','463630399','477128284','443875901','488845680','453111583','388838723','521487551','293778748','530740587','336698281','404593641','490217893','284815942','534179666','535509415','473326170','416023011','469337564','469231420','526886897','384291546','505728417','415569164','477725474','474902001','311972587','498639341','315019111','426122669','427916203','297368629','405656544','466965151','342792525','504241451','291890420','421228047','331975235','455750367','502593713','383478287','489321253','544135693','494655448','509987785','307011863','388491656','342643402','327814326','447119634','526910989','310738695','445533979','314716233','436491861','331177714','399355755','401629893','411206394','370398167','297430070','288487321','331786748','422966028','482769629','440618267','492153073','354902315','282935706','527861551','525325300','483011479','339532909','515877714','307386350','366247306','346194763','376510438','311548709','500962489','417571834','437108143','526357562','542914732','447553564','284876795','304158842','308750436','408492483','503724967','535562583','406641429','300704847','305204535','477750637','396301854','541491242','398383945','339440515','407558537','470580739','457502693','521906541','355833469','375239755','461402734','313232441','514643583','405373266','317469184','455066445','420178541','340787494','393422300','423593206','290051590','542656871','409913910','525463029','338828953','452584114','529407501','443637419','405862075','507502198','281940292','522415871','309172177','300235330','506515857','500000336','442452787','377194688','476279186','300048137')  order by ranking asc

DROP INDEX app_by_app_id_index ON itunes_application
CREATE INDEX app_by_app_id_index ON itunes_application(itunes_app_id, pull_date, price_type)
explain select * from itunes_application where pull_date <= '2012-07-28T09:27:43-0700' and pull_date >= '2012-07-27T09:27:43-0700' and itunes_app_id = '506388040' and price_type != 'Free' order by pull_date desc

DROP INDEX old_apps_index ON itunes_application
CREATE INDEX old_apps_index ON itunes_application(dropped, ranking)
explain select * from itunes_application ia1 join (select id, max(pull_date) as latest_date from itunes_application where pull_date >= '2012-07-23T12:00:01+0000' and pull_date <= '2012-07-27T12:00:01+0000' and price_type= 'Free' group by itunes_app_id) as ia2 on ia1.id = ia2.id where dropped = 1 and ia1.ranking >= 200 group by ia1.itunes_app_id order by ia1.ranking asc

DROP INDEX apps_by_rate_of_change_index ON itunes_application
CREATE INDEX apps_by_rate_of_change_index ON itunes_application(moved)
explain select * from itunes_application ia1 join (select id, max(pull_date) as latest_date from itunes_application where pull_date >= '2012-07-23T12:00:01+0000' and pull_date <= '2012-07-27T12:00:01+0000' and price_type= 'Free' group by itunes_app_id) as ia2 on ia1.id = ia2.id where moved >= 20  group by ia1.itunes_app_id order by ia1.ranking asc


SET profiling=1;
SHOW PROFILES;

select ua.app_store_id from faad_user.applications ua join faad_reward.calendar cal where ua.application_id=cal.application_id

select distinct * from itunes_application ia1 join (select id from itunes_application where pull_date <= '2012-07-31T10:00:01-0700' and pull_date >= '2012-07-31T06:00:00-0700' and price_type = 'Free' group by id) as ia2 on ia1.id = ia2.id where moved >= 10 group by ia1.itunes_app_id order by ia1.ranking asc
select moved from itunes_application where pull_date <= '2012-07-31T10:00:01-0700' and pull_date >= '2012-07-31T06:00:00-0700' and price_type = 'Free' and itunes_app_id = '282614216'
select distinct * from itunes_application ia1 join (select id from itunes_application where pull_date <= '2012-07-31T10:00:01-0700' and pull_date >= '2012-07-31T09:00:00-0700' and price_type = 'Free' order by id limit 300) as ia2 on ia1.id = ia2.id order by ia1.ranking asc


