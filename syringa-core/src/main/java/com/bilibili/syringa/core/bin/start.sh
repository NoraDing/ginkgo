#!/bin/bash
[[ -f /etc/profile ]] && source /etc/profile
set -e -u

readonly APP_NAME=syringa
readonly APP_CLASS=com.bilibili.syringa.core.SyringaApplication

readonly CUR_DIR=$(cd "$(dirname "$0")";pwd)
readonly APP_HOME="$(dirname ${CUR_DIR})"
readonly LOG_DIR=/data/log/${APP_NAME}
readonly LAUNCH_DATETIME=$(date +%Y%m%d_%H%m%S)
readonly LAUNCH_LOG=${LOG_DIR}/launch.log
readonly GC_LOG=${LOG_DIR}/gc.log

# Determine memory provision
MEM_SIZE="4G"
total_mem() { cat /proc/meminfo | grep MemTotal | awk '{print $2}' ; }
[[ $(total_mem 2>/dev/null || echo 0) -gt 16777216 ]] && MEM_SIZE="8G"

# Assemble options
## JAVA_OPTS
JAVA_OPTS="  -Dfile.encoding=utf-8"
JAVA_OPTS+=" -XX:+UseG1GC"
JAVA_OPTS+=" -XX:ParallelGCThreads=24"
JAVA_OPTS+=" -XX:ConcGCThreads=16 "
JAVA_OPTS+=" -XX:InitiatingHeapOccupancyPercent=45"
JAVA_OPTS+=" -XX:NewRatio=2"
JAVA_OPTS+=" -XX:SurvivorRatio=4"
JAVA_OPTS+=" -XX:MaxTenuringThreshold=15"
JAVA_OPTS+=" -XX:-UseAdaptiveSizePolicy"
JAVA_OPTS+=" -Xms${MEM_SIZE} -Xmx${MEM_SIZE}"
JAVA_OPTS+=" -XX:+HeapDumpOnOutOfMemoryError"
JAVA_OPTS+=" -XX:HeapDumpPath=${LOG_DIR}/${APP_NAME}_${LAUNCH_DATETIME}.hprof"
JAVA_OPTS+=" -XX:+PrintGCDetails"
JAVA_OPTS+=" -Xloggc:${GC_LOG}"
JAVA_OPTS+=" -XX:+PrintGCTimeStamps"
JAVA_OPTS+=" -Djava.security.egd=file:/dev/./urandom"
JAVA_OPTS+=" -XX:OnOutOfMemoryError=${APP_HOME}/bin/stop.sh"
## CLASSPATH
CLASSPATH="${APP_HOME}/conf:${APP_HOME}"
for file in $(ls ${APP_HOME}/lib/*.jar); do CLASSPATH+=":$file" ; done
JAVA_OPTS+=" -cp $CLASSPATH"

# Fire!
ln -snf ${LOG_DIR} ${APP_HOME}/log || true
export DEPLOY_ENV=uat
export ZONE=sh001
export DISCOVERY_ZONE=sh001
exec java ${JAVA_OPTS} ${APP_CLASS}