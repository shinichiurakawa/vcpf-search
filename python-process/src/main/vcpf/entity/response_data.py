# -*- coding: utf-8 -*-

from vcpf.entity.morpheme import *

import datetime
from datetime import timedelta

class ResponseData:
	def __init__(self, scraping_id, url, title):
	        self.scraping_id = scraping_id
		self.url = url
		self.title = title
	def setClusterId(self, cluster_id):
		self.cluster_id = cluster_id
	def getClusterId(self):
		return self.cluster_id
	def getScrapingId(self):
		return self.scraping_id
	def setKeyword(self,keyword):
		self.keyword = keyword
	def getResponseString(self):
		ret = "[\"" + self.keyword + "\",\"" + self.url + "\",\"" + self.title + "\"]"
		return ret
		
