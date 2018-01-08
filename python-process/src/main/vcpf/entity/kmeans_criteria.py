# -*- coding: utf-8 -*-

class KmeansCriteria:
	def __init__(self, session_id, n_clusters):
	    self._session_id = session_id
	    self._n_clusters = n_clusters
	def getNClusters(self):
		return self._n_clusters
	def getSessionId(self):
	    return self._session_id
	def getScrapingId(self):
	    return self._scrapingId
	def setScrapingId(self,scraping_id):
	    self._scrapingId = scraping_id

