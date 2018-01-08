# -*- coding: utf-8 -*-

import os

class SYSTEM:
    @staticmethod
    def hostname():
        return 'localhost'
    @staticmethod
    def dbname():
        return 'your-db-name'
    @staticmethod
    def dbuser():
        return 'your-db-user'
    @staticmethod
    def BASEPATH():
	return '/(path)/python-process/src/main/vcpf/'
    @staticmethod
    def LOGBASEPATH():
	return '/(path)/python-process/src/test/'


class CONFIG:
    @staticmethod
    def CONFIGFILE():
        return SYSTEM.BASEPATH() + 'var/conf/config.xml'

