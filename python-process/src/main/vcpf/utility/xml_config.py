from xml.etree.ElementTree import *
import sys
import re

class XMLConfig():
    _dic = None
    _tree = None
    def __init__(self,confFile):
        try:
            self._tree = parse(confFile)
        except IOError as (errno,strerror):
            exit()
        except:
            exit()
    def getVal(self,key):
        v = self._tree.getroot().find('.//' + key + '/value').text
        t = self._tree.getroot().find('.//' + key + '/type').text
        if t == "float":
            return float(v)
        elif t == "int":
            return int(v)
        elif t == "text":
            return v
