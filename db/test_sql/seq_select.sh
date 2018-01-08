source ../env.sh
 
psql -d ${DB_NAME} -U interest -c "SELECT NEXTVAL('SESSION_DICTIONARY_ID_SEQ')"

#psql -d ${DB_NAME} -U interest -c "SELECT setval('SESSION_DICTIONARY_ID_SEQ',1,false)"
