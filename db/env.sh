#VERSION=9.6.4
VERSION=10.1

export PKG_NAME=postgresql-${VERSION}
export INST_TARGET_BASE=/usr/local/
export INST_TARGET_DIR=${INST_TARGET_BASE}${PKG_NAME}/

export DB_NAME=your-db-name
export DB_USER=your-db-user
export DB_PASSWORD=your-db-password
export MY_ACCOUNT='your-account'

export PATH=${PATH}:${INST_TARGET_DIR}bin
export LD_LIBRARY_PATH=${INST_TARGET_DIR}lib:${LD_LIBRARY_PATH}
export LD_LIBRARY_PATH=/usr/local/postgresql-${VERSION}/lib/:${LD_LIBRARY_PATH}
export PGDATA=${INST_TARGET_DIR}pg_data/${DB_NAME}/
export PGLOG_DIR=${INST_TARGET_DIR}pg_log/${DB_NAME}/
export PGLOG=${PGLOG_DIR}pgsql-`date +%Y%m%d`.log
