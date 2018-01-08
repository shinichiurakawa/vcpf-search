source ../env.sh

cat create_sequence.sql | grep "CREATE SEQUENCE" | sed 's/CREATE/DROP/' | cut -d' ' -f1-3 | sed 's/$/;/' | while read SQL_COMMAND
do
  psql -d ${DB_NAME} -c "${SQL_COMMAND}"
done
