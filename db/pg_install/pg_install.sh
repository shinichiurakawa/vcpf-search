source ../env.sh

#
# PostgreSQLのインストール
#
proc_inst()
{
    tar xvzf  ${INST_FILE}
    cd ./${PKG_NAME}
    ./configure --prefix=${INST_TARGET_DIR}
    make
    sudo make install
}


#
# raizinユーザー作成
# DBインスタンス作成
#
create_db()
{
    # create db
#    cd /tmp/
#    /usr/local/postgresql-${VERSION}/bin/initdb -D ${PGDATA}
    echo "wait 4sec... (initdb)"
#    sleep 4
#    pg_ctl -D ${PGDATA} -l ${PGLOG} start
    echo "wait 5sec... (start postgresql)"
#    sleep 5
    # you must wait.
#    sudo -u ${MY_ACCOUNT} createuser -U ${MY_ACCOUNT} --createdb ${DB_USER}
#    sudo -u ${MY_ACCOUNT} createdb -U ${DB_USER} -E UTF8 ${DB_NAME}
    echo "/usr/local/postgresql-${VERSION}/bin/createuser --createdb ${DB_USER}"
    echo "/usr/local/postgresql-${VERSION}/bin/createdb -U ${DB_USER} -E UTF8 ${DB_NAME}"
}

#
# データファイル、ログを配置する場所は必ず新規作成するようにしている
#
check_dir()
{
    # create dbfile dir
    if [ ! -d ${PGDATA} ]; then
        sudo mkdir -p ${PGDATA}
        sudo chown -R ${MY_ACCOUNT} ${PGDATA}
    else
        echo "${PGDATA} is exist."
        exit 1
    fi
    # create logfile dir
    if [ ! -d ${PGLOG_DIR} ]; then
        sudo mkdir -p ${PGLOG_DIR}
        sudo chown -R ${MY_ACCOUNT} ${PGLOG_DIR}
    else
        echo "${PGLOG_DIR} is exist."
        exit 1
    fi
}

#
# インストールファイルの有無をチェック
#
check_install_file()
{
if [ ! -f ${INST_FILE} ];
then
        echo "no such file exists. "${INST_FILE}
	echo "https://www.postgresql.org/ftp/source/ から最新のpostgresql-x.y.z.tar.gzをダウンロード、"
	echo "../env.shのVERSION番号を修正してからsudo sh pg_inst.sh を実行してください。"
        exit 1
fi
}


# 1) 変数設定
#MY_ACCOUNT=yuyishid
INST_FILE=${PKG_NAME}.tar.gz

# 2) インストールファイルチェック
#check_install_file
#check_dir

# 3) PostgreSQLのインストール
#proc_inst

# 4) DBインスタンスの作成
create_db

