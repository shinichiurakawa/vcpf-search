source ../env.sh
psql -d ${DB_NAME} -U interest -c "SELECT URL,STATUS FROM SCRAPING"
