# -*- coding: utf-8 -*-
from scraper import settings
from scraper.items import NewswebItem
import psycopg2
import scrapy

class BaseSpider(scrapy.Spider):

    def __init__(self):
        super(BaseSpider, self).__init__()
        conn_string = "host='localhost' dbname='%s' user='%s' password='%s'" % (
                settings.DB_NAME, settings.DB_USER, settings.DB_PASSWORD)
        self.conn = psycopg2.connect(conn_string)

    def get_url_xpath(self):
        pass

    def parse(self, response):
        for url in response.xpath(self.get_url_xpath()).extract():
            # Check if URL should be ignored (e.g., lead to some binary)
            skip = False
            for ie in settings.IGNORED_EXTENSIONS:
                if url.endswith('.' + ie):
                    skip = True
                    break
            if skip: continue

            # Check if URL is already in the DB
            cursor = self.conn.cursor()
            cursor.execute("SELECT COUNT(*) FROM crawl WHERE url = %s", (url,))
            cnt = cursor.fetchone()[0]
            cursor.close()
            if int(cnt) != 0:
              continue

            yield scrapy.Request(url.strip(), callback=self.parse_page)

    def parse_page(self, response):
        item = NewswebItem()
        item['url'] = response.url
        item['html'] = response.body
        return item
