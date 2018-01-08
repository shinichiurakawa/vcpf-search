source ../env.sh
 
psql -d ${DB_NAME} -c "ALTER USER testuser with unencrypted password 'interest'"
