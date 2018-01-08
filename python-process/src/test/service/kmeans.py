# -*- coding: utf-8 -*-

import sys

sys.path.append('/Users/shurakaw/IdeaProjects/forHackDay10th/python-process/src/main')
from vcpf.service.kmeans_service import *
from vcpf.entity.kmeans_criteria import *
from vcpf.log.logger import LOG

LOG.INIT()
LOG.debug("test : [enter]")

criteria = KmeansCriteria(1,7)

# 

kmeansService= KmeansService()
kmeansService.kmeans_proc(criteria)

LOG.debug("test : [exit]")

