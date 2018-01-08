CREATE TABLE SESSION (
 ID             int     PRIMARY KEY,    -- uniq ID
 start_time     time,   -- 判定開始時間
 end_time	time,	-- 判定終了時間
 KEYWORD	text,	-- 検索キーワード
 RESPONSE	text,	-- クラスタリング結果
 STATUS         int
);

CREATE TABLE SCRAPING (
 ID             int     PRIMARY KEY,    -- uniq ID
 SESSION_ID     int,                    -- SESSION.ID
 URL            text,                   -- 取得したSCRAPING.CONTENTSに対応するURL
 TITLE          text,                   -- 取得したTITLE
 CONTENTS       text,                    -- URLのテキスト全て/gitの場合は該当リポジトリのソースファイル全結合
 STATUS		int
);

CREATE TABLE DICTIONARY (
 ID             int     PRIMARY KEY,    -- uniq ID
 WORD           char(200),
 CASTEL_PART_ID int                     -- 対応する城パーツがあれば
);

CREATE TABLE MORPHEMEANALYSIS (
 SESSION_ID	int,			-- 
 SCRAPING_ID	int,			-- 保存したコンテンツID（目的変数）
 MORPHEME_ID	int,			-- 
 DICTIONARY_ID	int,			-- 
 WORD           char(200),		-- 単語の文字列
 COUNT		int,			-- 文字の数
 CASTEL_PART_ID int                     -- 対応する城パーツがあれば
);
CREATE UNIQUE INDEX MORPHEMEANALYSIS_INDEX ON MORPHEMEANALYSIS USING BTREE(SESSION_ID,SCRAPING_ID,DICTIONARY_ID);

CREATE TABLE KMEANS (
 ID     int     PRIMARY KEY,            -- uniq ID
 SCRAPING_ID    int,                    -- SCRAPING.ID
 SESSION_ID     int,                    -- SESSION.ID
 CENTROID_ID    int,                    -- セントロイドのID (KMEANS.CLUSTERの中にあるもの）
 CLUSTER        text
);

CREATE TABLE CASTLE_PART_DIC (
 ID     int     PRIMARY KEY,    -- uniq ID
 SESSION_ID     int,                    -- SESSION.ID
 PART_ID        int,            -- 城のパーツのID（どの絵に対応するのか？）
 TH             int,
 DIC_ID         int
);

CREATE TABLE CASTLE_CONSTRUCTION (
 ID     int     PRIMARY KEY, -- uniq ID
 SESSION_ID     int,                    -- SESSION.ID
 PART_ID        int             -- どの城パーツを使うのか
);

CREATE TABLE USERS (
 user_id int        PRIMARY KEY, -- uniq ID
 user_hash  varchar(50)
);

