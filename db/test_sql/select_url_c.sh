source ../env.sh
psql -d ${DB_NAME} -U interest -c "SELECT id,STATUS,substr(contents,1,20) FROM SCRAPING"
