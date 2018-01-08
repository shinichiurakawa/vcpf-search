source ../env.sh

cat create_table.sql | grep "CREATE TABLE" | sed 's/CREATE/DROP/' | sed 's/ (/;/' | while read SQL_COMMAND
do
  psql -d ${DB_NAME} -c "${SQL_COMMAND}"
done
