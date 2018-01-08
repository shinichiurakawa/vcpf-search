
TARGET_PATH=`pwd | sed 's/test\/dao/main\//'`
TEST_PY=get_reasonable_parking.py


set_position_azabu()
{
LATITUDE_MIN=35.65
LONGITUDE_MIN=139.73
LATITUDE_MAX=35.67
LONGITUDE_MAX=139.74
}

set_position_hakusan()
{
LATITUDE_MIN=35.70
LONGITUDE_MIN=139.75
LATITUDE_MAX=35.72
LONGITUDE_MAX=139.76
}

set_position_uehara()
{
LATITUDE_MIN=35.66
LONGITUDE_MIN=139.68
LATITUDE_MAX=35.67
LONGITUDE_MAX=139.69
}

set_reservation_a()
{
RESERVE_FROM="2017-04-03 12:01:00"
RESERVE_TO="2017-04-03 13:30:00"
}

set_reservation_b()
{
RESERVE_FROM="2017-04-03 5:00:00"
RESERVE_TO="2017-04-03 9:30:00"
}

show_criteria()
{
echo "b-box : ${LATITUDE_MIN}, ${LONGITUDE_MIN}, ${LATITUDE_MAX}, ${LONGITUDE_MAX}"
echo "time  : ${RESERVE_FROM} - ${RESERVE_TO}"
echo " ---------------"
}

create_search_py()
{
cat << EOF > ${TEST_PY}
# -*- coding: utf-8 -*-

import sys

sys.path.append('${TARGET_PATH}')
from smapa.service.search_parking_service import *
from smapa.entity.search_criteria import *
from smapa.entity.time_criteria import *
from smapa.log.logger import LOG

LOG.INIT()
LOG.debug("test : [enter]")

criteria = SearchCriteria()

# 位置条件 set_position_xxx()で設定されるb-box
pos_criteria = PositionCriteria(${LATITUDE_MIN},${LONGITUDE_MIN},${LATITUDE_MAX},${LONGITUDE_MAX})
criteria.setPositionCriteria(pos_criteria)

# 時間条件 2017-4-10 8:00 〜 2017-4-10 9:00 
time_criteria = TimeCriteria("${RESERVE_FROM}","${RESERVE_TO}")
time_from, time_to = time_criteria.getCriteria()
print "from:%s, to:%s" % (time_from,time_to)
criteria.setTimeCriteria(time_criteria)

# 
criteria.setSubParkingStatus(2)


searchService = SearchParkingService()
searchService.getParkingListOrderByCharge(criteria)

 
LOG.debug("test : [exit]")

EOF
}

# 空き時間検索条件、(a)12:01-13:30  (b)5:00-9:30
set_reservation_a
#set_reservation_b

# azabuかhakusanのどちらかを有効にする
#set_position_azabu
#set_position_hakusan
set_position_uehara

# daoのtest用pythonスクリプトの生成
create_search_py

# 設定されている検索条件を表示
show_criteria

# テストの実行
python ${TEST_PY}

rm ${TEST_PY}

