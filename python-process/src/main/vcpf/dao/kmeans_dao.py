# -*- coding: utf-8 -*-

from vcpf.dao.db_connect import *
from vcpf.entity.kmeans_criteria import *
from vcpf.entity.response import *
from vcpf.entity.morpheme import *
from vcpf.log.logger import LOG

import sys
from datetime import *
from time import *

#
# in : b-box, time
# out: 駐車場情報（リスト）
#

class KmeansDao:
	def getMaxId(self, session_id):
		db = DBConnect()
		conn = db.connect()
		cur = conn.cursor()
		
		LOG.debug("getKmeansDao:getMaxId[enter]")
		db = DBConnect()
		conn = db.connect()
		cur = conn.cursor()
		
		sql = "SELECT MAX(MORPHEME_ID) from MORPHEMEANALYSIS WHERE SESSION_ID = " + str(session_id)
		
		LOG.debug("getMaxId :sql = %s" % sql)
		cur.execute(sql)
		r = cur.fetchone()
		cur.close()
		conn.close()
		LOG.debug("getKmeansDao:getMaxId[exit]")
		return int(r[0])
	def getScrapingIdList(self, criteria):
		LOG.debug("getKmeansDao:getScrapingIdList[enter]")
		scraping_id_list = []
		db = DBConnect()
		conn = db.connect()
		cur = conn.cursor()

		sql = "SELECT DISTINCT SCRAPING_ID from MORPHEMEANALYSIS WHERE SESSION_ID = " + str(criteria.getSessionId()) + " ORDER BY SCRAPING_ID ASC"

		LOG.debug("morpheme get:sql = %s" % sql)
		cur.execute(sql)
		result_lst = cur.fetchall()
		for r in result_lst:
			id = int(r[0])
			scraping_id_list.append(id)
		cur.close()
		conn.close()
		LOG.debug("getKmeansDao:getScrapingIdList[exit]")
		return scraping_id_list

	def getMorphemeList(self, criteria):
		LOG.debug("getKmeansDao:getMorphemeList [enter]")
		morpheme_list = []
		db = DBConnect()
		conn = db.connect()
		cur = conn.cursor()

		sql = "select MORPHEME_ID, SCRAPING_ID, WORD, COUNT from MORPHEMEANALYSIS WHERE SESSION_ID = " + str(criteria.getSessionId()) + " AND SCRAPING_ID = " + str(criteria.getScrapingId()) + " ORDER BY MORPHEME_ID ASC"

		LOG.debug("morpheme get:sql = %s" % sql)
		cur.execute(sql)
		result_lst = cur.fetchall()
		#print "result_lst = " + str(result_lst)
		for r in result_lst:
			#print "morp(" + str(r[0]) + " , " + str(r[1]) + ")"
			m = Morpheme(r[0],r[1],r[2],r[3])
			morpheme_list.append(m)
		cur.close()
		conn.close()
		LOG.debug("getKmeansDao:getMorphemeList [exit]")
		return morpheme_list

	def getScrapingData(self, scraping_id):
		LOG.debug("getKmeansDao:getScrapingData[enter]")
		db = DBConnect()
		conn = db.connect()
		cur = conn.cursor()

		sql = "select ID, URL, TITLE from SCRAPING WHERE ID = " + str(scraping_id)

		LOG.debug("scraping get:sql = %s" % sql)
		cur.execute(sql)
		r = cur.fetchone()
		session_data = ResponseData(r[0],r[1],r[2])
		cur.close()
		conn.close()
		LOG.debug("getKmeansDao:getScrapingData [exit]")
		return session_data
		
	def setResponse(self, session_id, response):
		LOG.debug("getKmeansDao:setResponse[enter]")
		db = DBConnect()
		conn = db.connect()
		cur = conn.cursor()

		sql = "UPDATE SESSION SET RESPONSE=%s, STATUS=10 WHERE ID=%s;"

		cur.execute(sql,(response,str(session_id)))
		conn.commit()
		cur.close()
		conn.close()
		LOG.debug("getKmeansDao:setResponse [exit]")
		
		

