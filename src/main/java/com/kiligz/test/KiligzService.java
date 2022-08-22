package com.kiligz.test;

public class KiligzService {
        String prefix;
        KiligzDao kiligzDao;

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public KiligzDao getKiligzDao() {
            return kiligzDao;
        }

        public void setKiligzDao(KiligzDao kiligzDao) {
            this.kiligzDao = kiligzDao;
        }

        public String queryName(String key) {
            return kiligzDao.queryName(prefix + key);
        }
    }