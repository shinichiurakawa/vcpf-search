# -*- coding: utf-8 -*-

import datetime
from datetime import timedelta

class Morpheme:
	def __init__(self,morpheme_id, scraping_id, word, num):
		self._morpheme_id = morpheme_id
		self._scraping_id = scraping_id
		self._word = word.strip().rstrip()
		self._num = num
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

