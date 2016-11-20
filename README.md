Dumping mysql database:

`mysqldump -h HOST -u MYSQL_USER_ROOT --password=USER_PASSWORD -C -Q -e --create-options DATABASE_NAME > moodle-database.sql`


Restoring mysql database:

`mysql -u MYSQL_USER_ROOT -h HOST -p DATABASE < moodle-database.sql`
