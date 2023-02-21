#!/bin/bash
ps -aux | grep xiaomiqiu | grep -v grep| awk '{print $2}' | xargs  kill -9
cd /root/linux_xiaomiqu
nohup ./xiaomiqiu -authtoken=bAe854993e6444e3925b24c7edcdd72A -log=xiaomiqiu.log -log-level=info start-all & > /dev/null 2>&1 &