#!/bin/bash

for c in $(scrapy list)
do
  # rm three days old logs
  rm logs/${c}_$(date --date='-3 day' '+%Y%m%d-')*
  # run crawler for one site
  scrapy crawl $c #--logfile="logs/${c}_$(date '+%Y%m%d-%H%M').log" TODO fix log
done
sleep 1h
