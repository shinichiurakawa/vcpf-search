
source ../env.sh

DATA_DIR=copy_data/

#ls ./${DATA_DIR} | grep ".tsv" | while read TSV_NAME
#do
#  CSV_NAME=`echo ${TSV_NAME} | sed 's/.tsv$/.csv/'`
#  sed 's/	/,/g' ./${DATA_DIR}${TSV_NAME} > ./${DATA_DIR}${CSV_NAME}
#done

ls ./${DATA_DIR} | grep ".csv" | while read CSV_NAME
do
  TABLE_NAME=`echo ${CSV_NAME} | cut -d. -f1`
  psql -U ${DB_USER} ${DB_NAME} -A -t -c "TRUNCATE TABLE ${TABLE_NAME};"
  IMPORT_FILE=`pwd`/${DATA_DIR}${CSV_NAME}
  psql -U ${DB_USER} ${DB_NAME} -A -t -c "\copy ${TABLE_NAME} FROM '${IMPORT_FILE}' WITH csv;"
#  rm ${IMPORT_FILE}
done



