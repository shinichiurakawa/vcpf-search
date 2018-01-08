source ../env.sh
#psql -d ${DB_NAME} -U interest -c "SELECT substr(RESPONSE,1,100),STATUS FROM SESSION"
psql -d ${DB_NAME} -U interest -c "SELECT RESPONSE,STATUS FROM SESSION"
