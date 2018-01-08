# -*- coding: utf-8 -*-

import datetime
from datetime import timedelta

class Scraping:
	def __init__(self,id, session_id, url, title):
		self._id = id
		self._session_id = session_id
		self._url = url
		self._title = title
	def getMorphemeId(self):
		return self._morpheme_id
	def getScrapingId(self):
		return self._scraping_id
	def getWord(self):
		return self._word
	def getNum(self):
		return self._num
	def __lt__(self, other):
		# self < other
		return self.getNum() < other.getNum()

