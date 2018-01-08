
TARGET_PATH=`pwd | sed 's/test\/dao/main\/vcpf\/service\//'`
TEST_PY=kmeans.py


create_kmeans_py()
{
cat << EOF > ${TEST_PY}
# -*- coding: utf-8 -*-

import sys

sys.path.append('${TARGET_PATH}')
from vcpf.dao.kmeans_parking_dao import *
from vcpf.entity.kmeans_criteria import *
from vcpf.log.logger import LOG

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


kmeansDao = SearchParkingDao()
p_list = kmeansDao.kmeansParking(criteria)
n = 1
for p_info in p_list:
	print "-----(%d)[%d]----" % (n,p_info.getSubParkingNo())
	print "parking name : %s" % p_info.getParkingName()
	print "parking address: %s" % p_info.getParkingAddress()
	print "parking latitude : %f" % p_info.getLatitude()
	print "parking longitude : %f" % p_info.getLongitude()
	n = n + 1

 
LOG.debug("test : [exit]")

# 名前付きカーソル・名前なしカーソル
# http://qiita.com/knoguchi/items/3d5631505b3f08fa37cc

EOF
}


# daoのtest用pythonスクリプトの生成
create_kmeans_py

# 設定されている検索条件を表示
show_criteria

# テストの実行
python ${TEST_PY}

rm ${TEST_PY}

