# -*- coding: utf-8 -*-

from vcpf.dao.kmeans_dao import *
from vcpf.entity.kmeans_criteria import *
from vcpf.entity.morpheme import *
from vcpf.log.logger import LOG

import sys
from datetime import *
from time import *
from matplotlib import pyplot as plt
from sklearn import datasets
from sklearn.cluster import KMeans
from sklearn.metrics import pairwise_distances_argmin_min

class KmeansService:
	# 形態素解析結果を配列に変換する
	def _createKmeansData(self, max_morpheme_id, morpheme_list):
		idx = 0
		ret = []
		#print str(morpheme_list)
		for id in range(0,max_morpheme_id):
			morpheme_num = 0
			morpheme_id = id + 1
			if idx < len(morpheme_list):
				morpheme = morpheme_list[idx]
				if morpheme_id == morpheme.getMorphemeId():
					morpheme_num = morpheme.getNum()
					idx = idx + 1
			#ret.append([morpheme_id, morpheme_num])
			ret.append(morpheme_num)
		return ret

	def kmeans_proc(self,criteria):
		features = []
		self._all_morpheme_list = []
		self._kmeansDao = KmeansDao()
		max_id = self._kmeansDao.getMaxId(criteria.getSessionId())
		# データ取得
		scrap_id_list = self._kmeansDao.getScrapingIdList(criteria)
		for scrap_id in scrap_id_list:
			criteria.setScrapingId(scrap_id)
			morpheme_list = self._kmeansDao.getMorphemeList(criteria)
			kmeans_data = self._createKmeansData(max_id, morpheme_list)
			features.append(kmeans_data)
			self._all_morpheme_list.append([scrap_id,morpheme_list])
		# kmeans実行
		# クラスタリングする
		self.kmeans = KMeans(n_clusters=criteria.getNClusters())
		self.pred = self.kmeans.fit_predict(features)
		self.closest, _ = pairwise_distances_argmin_min(self.kmeans.cluster_centers_, features)
		# kmeansの結果を保存
		response = self._createResponse(scrap_id_list)
		self._kmeansDao.setResponse(criteria.getSessionId(),response)
	def _getMorphemeList(self,scraping_id):
		for morpheme_info in self._all_morpheme_list:
			if morpheme_info[0] == scraping_id:
				return morpheme_info[1]
		LOG.debug("KmeansService:_getMorphemeList [error]")
		return None
	def _createKeyword(self,scraping_id,morpheme_list):
		LOG.debug("KmeansService:_createKeyword [enter]")
		th = 5 
		m_list = []
		ret = ""
		# scraping_idの一致する形態素を抽出
		for morpheme in morpheme_list:
			if scraping_id == morpheme.getScrapingId():
				m_list.append(morpheme)
		sorted_list = sorted(m_list,reverse=True)
		# トップ5キーワードリストを作成
                for morpheme in sorted_list[0:th]:
                        if len(ret) > 0:
                                ret = ret + " | " + morpheme.getWord()
                        else:
                                ret = morpheme.getWord()
		LOG.debug("keyword = " + ret + "/ m len = " + str(len(m_list)))
		LOG.debug("KmeansService:_createKeyword [exit]")
                return ret
	def _createResponse(self,scrap_id_list):
		scraping_list = []
		centroid_list = []
		# scraping dataを取得

		for scrap_id in scrap_id_list:
			scraping_data = self._kmeansDao.getScrapingData(scrap_id)
			scraping_list.append(scraping_data)
		# 各scraping_dataにクラスタリング結果を設定
		for idx in range(len(self.pred)):
			cluster_id = self.pred[idx]
			scraping_data = scraping_list[idx]
			scraping_data.setClusterId(cluster_id)

		# 各クラスターの中心要素（セントロイド）を取得
		for scraping_data in scraping_list:
			for center_idx in self.closest:
				centroid_scrap_id = scrap_id_list[center_idx]
				if centroid_scrap_id == scraping_data.getScrapingId():
					morpheme_list = self._getMorphemeList(centroid_scrap_id)
					keyword = self._createKeyword(scraping_data.getScrapingId(),morpheme_list)
					centroid_list.append([self.pred[center_idx],keyword])
					break

		for scraping_data in scraping_list:
			for centroid in centroid_list:
				if centroid[0] == scraping_data.getClusterId():
					scraping_data.setKeyword(centroid[1])
					
		response = Response("OK",scraping_list)
		print "Response : " + response.createResponse()
		return response.createResponse()
