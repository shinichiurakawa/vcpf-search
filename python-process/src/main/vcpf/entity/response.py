# -*- coding: utf-8 -*-

from vcpf.entity.kmeans_criteria import *
from vcpf.entity.response_data import *

import datetime
from datetime import timedelta

class Response:
	def __init__(self,status,data_list):
		self.status = status
		self.keyword = ""
		self.data_list = data_list
	def getStatus(self):
		return "\"status\":\"" + self.status + "\""
	def setOkStatus(self):
		self.status = "OK"
	def getKeyword(self):
		return "\"keyword\":\"" + self.keyword + "\""
	def getDataList(self):
		ret = ""
		for data in self.data_list:
			if len(ret) > 0:
				ret = ret + "," + data.getResponseString()
			else:
				ret = data.getResponseString()
		return "[" + ret + "]"
	def createResponse(self):
		res = ""
		res = "{" + self.getStatus() + "," + self.getKeyword() + "," + self.getDataList() + "}"
		return res
		

