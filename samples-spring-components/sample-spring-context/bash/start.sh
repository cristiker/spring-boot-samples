#!/bin/sh
source  /etc/profile
#执行文件名
JAR_FILE="cms.jar"
#目录
TARGET_DIR="/docker/cms"
#pid文件
PID_FILE="cms.pid"
#输出目录
OUT_FILE="output.log"

TARGET_PID_FILE="$TARGET_DIR/$PID_FILE"
TARGET_JAR="$TARGET_DIR/$JAR_FILE"
ACTIVE="suzhou"

status() {
    if [ -f ${TARGET_PID_FILE} ]; then
        pid=`cat "$TARGET_PID_FILE"`
        if [ -e "/proc/$pid" ] ; then
            # process by this pid is running.
            # It may not be our pid, but that's what you get with just TARGET_PID_FILEs.
            return 0
        else
            return 2 # program is dead but pid file exists
        fi
    else
        return 3 # program is not running
    fi
}

start() {
    nohup java -jar ${TARGET_JAR} --spring.profiles.active=${ACTIVE} > ${TARGET_DIR}/${OUT_FILE} 2>&1 &
    echo $! > ${TARGET_PID_FILE}
}

stop() {
    # Try a few times to kill TERM the program
    if status; then
        pid=`cat "$TARGET_PID_FILE"`
        echo "Killing $name (pid $pid)"
        kill -9 ${pid}
        # Wait for it to exit.
        for i in `seq 1 3`; do
            echo "Waiting $name (pid $pid) to die..."
            status || break
            sleep 1
        done
        if status ; then
            echo "$name stop failed; still running."
        else
            echo "$name stopped."
            rm ${TARGET_PID_FILE}
        fi
    fi
}

case "$1" in
    'start')
        status
        code=$?
        if [ ${code} -eq 0 ]; then
            pid=$(cat ${TARGET_PID_FILE})
            echo -n -e "Application is already running under $pid \n"
        else
            start
        fi
    ;;
    'stop')
        stop ;;
    'status')
        status
        code=$?
        if [ ${code} -eq 0 ]; then
            echo -e "\t $name is running, process `cat $TARGET_PID_FILE`"
        else
            echo -e " \t $name is not running"
        fi
        exit ${code}
    ;;
    'restart')
        stop && start
    ;;
    *)
        echo "Usage: $0 {start|stop|status|restart}"
        exit 3
    ;;
esac
exit $?