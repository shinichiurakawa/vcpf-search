source ./env.sh

pg_ctl start -D ${PGDATA} -l ${PGLOG}
