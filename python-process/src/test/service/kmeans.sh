
TARGET_PATH=`pwd | sed 's/test\/service/main/'`
TEST_PY=kmeans.py

SESSION_ID=1
CLUSTER_NO=7

create_kmeans_py()
{
cat << EOF > ${TEST_PY}
# -*- coding: utf-8 -*-

import sys

sys.path.append('${TARGET_PATH}')
from vcpf.service.kmeans_service import *
from vcpf.entity.kmeans_criteria import *
from vcpf.log.logger import LOG

LOG.INIT()
LOG.debug("test : [enter]")

criteria = KmeansCriteria(${SESSION_ID},${CLUSTER_NO})

# 

kmeansService= KmeansService()
kmeansService.kmeans_proc(criteria)

LOG.debug("test : [exit]")

EOF
}


# daoのtest用pythonスクリプトの生成
create_kmeans_py


# テストの実行
python ${TEST_PY}

#rm ${TEST_PY}

