source ../env.sh
 
#psql -d ${DB_NAME} -U interest -c "SELECT id, word, castel_part_id FROM DICTIONARY WHERE word = '使用'"
#psql -d ${DB_NAME} -U interest -c "SELECT * FROM DICTIONARY"

#psql -d ${DB_NAME} -U interest -c "SELECT DISTINCT MORPHEME_ID from MORPHEMEANALYSIS WHERE SESSION_ID = 1 ORDER BY MORPHEME_ID ASC"
psql -d ${DB_NAME} -U interest -c "select MORPHEME_ID, WORD, SCRAPING_ID, COUNT from MORPHEMEANALYSIS WHERE SESSION_ID = 1 ORDER BY MORPHEME_ID DESC"
#psql -d ${DB_NAME} -U interest -c "select TITLE, CONTENTS from SCRAPING WHERE SESSION_ID = 1 AND ID = 5"

#psql -d ${DB_NAME} -U interest -c "SELECT * FROM SESSION_DICTIONARY"


#psql -d ${DB_NAME} -U interest -c "SELECT MAX(MORPHEME_ID) from MORPHEMEANALYSIS WHERE SESSION_ID = 1"
#psql -d ${DB_NAME} -U interest -c "SELECT MAX(MORPHEME_ID) from MORPHEMEANALYSIS"
