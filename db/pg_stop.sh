source ./env.sh

pg_ctl stop -D ${PGDATA} -m fast
