# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: http://doc.scrapy.org/topics/item-pipeline.html

import psycopg2
import slumber

from scraper import settings
from scrapy import log
from scrapy.exceptions import DropItem

class NewswebPipeline(object):
    def __init__(self):
        conn_string = "host='localhost' dbname='%s' user='%s' password='%s'" % (
                settings.DB_NAME, settings.DB_USER, settings.DB_PASSWORD)
        self.conn = psycopg2.connect(conn_string)
        self.api = slumber.API(settings.NEWSWEB_ENDPOINT)

    def process_item(self, item, spider):
        log.msg("In process_item", level=log.INFO)
        cursor = self.conn.cursor()
        url = item['url']
        html = item['html']
        cursor.execute("INSERT INTO crawl (url, html) VALUES (%s, %s)", (url, html))
        self.conn.commit()
        cursor.close()
        log.msg("Stored and closed", level=log.INFO)

        # Send the crawled article to NewsWeb application
        self.api.articles.post({"url": url, "html": html})
        log.msg("Sent article to the NewsWeb app.", level=log.INFO)
        return item
