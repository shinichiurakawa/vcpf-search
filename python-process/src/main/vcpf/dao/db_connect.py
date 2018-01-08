# -*- coding: utf-8 -*-
import psycopg2
from vcpf.log.logger import LOG
from vcpf.utility.xml_config import *
from vcpf.const import *

class DBConnect:
	_dbname = None
	_hostname = None
	_username = None
	def __init__(self):
		LOG.debug("DBConnect :__init__ [enter]")
		cfg = XMLConfig(CONFIG.CONFIGFILE())
		self._dbname   = cfg.getVal('dbconfig/dbname')
		self._hostname = cfg.getVal('dbconfig/hostname')
		self._username = cfg.getVal('dbconfig/username')
		LOG.debug("DBConnect :__init__ [exit]")
	def connect(self):
		LOG.debug("DBConnect :connect [enter]")
		connect_info = "dbname=%s host=%s user=%s" % (self._dbname, self._hostname, self._username)
		LOG.debug("DBConnect :connect [exit]")
		return psycopg2.connect(connect_info)

