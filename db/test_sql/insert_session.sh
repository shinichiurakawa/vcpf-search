source ../env.sh
psql -d ${DB_NAME} -U interest -c "INSERT INTO SESSION(ID) VALUES(1)"

