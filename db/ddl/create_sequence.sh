source ../env.sh

psql -U ${DB_USER} -d ${DB_NAME} -f ./create_sequence.sql
