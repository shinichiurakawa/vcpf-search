import os
import logging
import logging.config
from vcpf.const import *
from vcpf.utility.xml_config import *

class LOG:
    @staticmethod
    def INIT():
        cfg = XMLConfig(CONFIG.CONFIGFILE())
        lgr = logging.getLogger(__name__)
        lgr.setLevel(cfg.getVal('logconfig/level'))
        hdr = logging.handlers.RotatingFileHandler( SYSTEM.LOGBASEPATH() + cfg.getVal('logconfig/path'),
            mode=cfg.getVal('logconfig/mode'),
            maxBytes=cfg.getVal('logconfig/size'),
            backupCount=cfg.getVal('logconfig/filenum'))
        hdr.setLevel(cfg.getVal('logconfig/level'))
        fmt = logging.Formatter(cfg.getVal('logconfig/format'))
        hdr.setFormatter(fmt)
        lgr.addHandler(hdr)

    @staticmethod
    def SetMode(logLV):
            logging.getLogger(__name__).setLevel(logLV)

    @staticmethod
    def debug(msg):
        logging.getLogger(__name__).debug(msg)


